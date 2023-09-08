import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private static String lastPlayer = "";
    private static String myIP = "";
    private static String xCoord = "0";
    private static String yCoord = "0";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the server IP: ");
        String serverIP = scanner.nextLine();  // Replace with the actual IP address
        int serverPort = 8001;

        // Separate thread for user input
        new Thread(() -> {
            while (true) {
                System.out.println("Enter your X coordinate: ");
                xCoord = scanner.nextLine();
                System.out.println("Enter your Y coordinate: ");
                yCoord = scanner.nextLine();
            }
        }).start();

        // Timer task for GET and POST requests
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    // GET request to fetch data
                    URL getUrl = new URL("http://" + serverIP + ":" + serverPort + "/main");
                    HttpURLConnection getCon = (HttpURLConnection) getUrl.openConnection();
                    getCon.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(getCon.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    String[] data = response.toString().split(",");
                    myIP = data[0];  // The first value is the client's IP as recognized by the server

                    // Check if another player has played
                    if (!lastPlayer.equals(data[0])) {
                        lastPlayer = data[0];
                        System.out.println("New data received: " + response.toString());

                        // POST new data only if the last player is not me
                        if (!lastPlayer.equals(myIP)) {
                            String postData = xCoord + "," + yCoord + ",0";
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);  // Check every second
    }
}
