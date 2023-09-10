import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Client {
    private static String lastData = "";
    private static String xCoord = "0";
    private static String yCoord = "0";
    private static String uid = UUID.randomUUID().toString();  // Generate a UID for this client

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the server IP: ");
        String serverIP = scanner.nextLine();
        int serverPort = 8001;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    // GET request to fetch data
                    URL getUrl = new URL("http://" + serverIP + ":" + serverPort + "/main?" + uid);
                    HttpURLConnection getCon = (HttpURLConnection) getUrl.openConnection();
                    getCon.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(getCon.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Check if new data is received
                    if (!lastData.equals(response.toString())) {
                        lastData = response.toString();
                        System.out.println("New data received: " + response.toString());

                        // Read new coordinates from user
                        System.out.println("Enter your new X coordinate: ");
                        xCoord = scanner.nextLine();
                        System.out.println("Enter your new Y coordinate: ");
                        yCoord = scanner.nextLine();

                        /*
                         POST new data
                         Format for data:
                         UID;x,y,0;
                         That zero is reserved for now, will be used to check win condition
                        */

                        String postData = uid + ";" + xCoord + "," + yCoord + ",0";
                        URL postUrl = new URL("http://" + serverIP + ":" + serverPort + "/main");
                        HttpURLConnection postCon = (HttpURLConnection) postUrl.openConnection();
                        postCon.setRequestMethod("POST");

                        postCon.setDoOutput(true);
                        DataOutputStream wr = new DataOutputStream(postCon.getOutputStream());
                        wr.writeBytes(postData);
                        wr.flush();
                        wr.close();

                        postCon.getResponseCode();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);  // Check every second
    }
}
