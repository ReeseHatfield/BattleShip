import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Client {
    private static String lastData = "";
    private static String xCoord = "0";
    private static String yCoord = "0";

    // Generate a UID for this client
    // This is generated at runtime for each client,
    // so it is still unique per client
    private static String uid = UUID.randomUUID().toString();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //create GUI

        System.out.println("Enter the server IP: ");
        String serverIP = scanner.nextLine();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    // GET request to fetch data
                    URL getUrl = new URL("http://" + serverIP + ":" + Settings.PORT_NUMBER + Settings.SERVER_ENDPOINT + "?" + uid);
                    HttpURLConnection getCon = (HttpURLConnection) getUrl.openConnection();
                    getCon.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(getCon.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //Update board here


                    // Check if new data is received
                    if (!lastData.equals(response.toString())) {
                        lastData = response.toString();
                        System.out.println("New data received: " + response);

                        // Read new coordinates from user
                        System.out.println("Enter your new X coordinate: ");
                        xCoord = scanner.nextLine();
                        System.out.println("Enter your new Y coordinate: ");
                        yCoord = scanner.nextLine();

                        /*
                         POST new data
                         Format for data:
                         "UID;x,y,0"
                         That zero is reserved for now, will be used to check win condition
                        */

                        String postData = uid + ";" + xCoord + "," + yCoord + ",0";
                        URL postUrl = new URL("http://" + serverIP + ":" + Settings.PORT_NUMBER + Settings.SERVER_ENDPOINT);
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
                catch (Exception e) {
                    ErrorHandler.handleError(e);
                }
            }
        }, 0, 1000);  // Check every second
    }
}
