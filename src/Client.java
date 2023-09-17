import utils.ErrorHandler;
import utils.Settings;

import java.awt.*;
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
    public static void main(String[] args) {
        new ServerSelect();
    }

    private static String lastData = "";
    private static int xCoord = 0;
    private static int yCoord = 0;
    private String serverIP;
    private boolean pause = true;

    // Generate a UID for this client
    // This is generated at runtime for each client,
    // so it is still unique per client
    private final static String uid = UUID.randomUUID().toString();

    public Client(String ip) {
        Scanner scanner = new Scanner(System.in);
        this.serverIP = ip;

        Backend backend = new Backend(scanner);
        // create GUI here
        Board board = new Board(this);

        for (Ship s : backend.getShips()) {
            for (Point p : s.points) {
                HittableButton button = board.playerPanelList.get(p.y * 10 + p.x);
                board.setHealth(board.getHealth() + 1);
                button.setBackground(Color.GREEN);
                button.setShipStatus(true);

            }
        }

        // System.out.println("Enter the server IP: ");
        // scanner.nextLine();
        // serverIP = scanner.nextLine();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    // GET request to fetch data
                    URL getUrl = new URL(
                            "http://" + serverIP + ":" + Settings.PORT_NUMBER + Settings.SERVER_ENDPOINT + "?" + uid);
                    HttpURLConnection getCon = (HttpURLConnection) getUrl.openConnection();
                    getCon.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(getCon.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Update board here
                    // check other strings for game setup so two players connect
                    if (!response.toString().equals("No data from other client")) {
                        System.out.println(response.toString());

                        // Expected string UUID;x,y,didHit,didWin
                        String[] parts = response.toString().split(",");
                        int responseX = Integer.parseInt(parts[0]);
                        int responseY = Integer.parseInt(parts[1]);

                        // Win before more shooting
                        if (Integer.parseInt(parts[3]) == 1) {
                            board.winningMenu();
                        }

                        // No win, so check if hit
                        // if hit, send back hit without firing request
                        if (Integer.parseInt(parts[2]) == 1) { // Change this to check hit status
                            // POST with hit
                            // if xCoord || yCoord > 9 do not fire anything
                            String postData = uid + ";" + "10" + "," + ",10" + ",1" + ",0";
                        }

                        if (xCoord < 9 && yCoord < 9) {
                            HittableButton button = board.playerPanelList.get(responseY * 10 + responseX);
                            if (button.hit()) {
                                board.setHealth(board.getHealth() - 1);
                            }
                        }
                    }

                    // Check if new data is received
                    if (!lastData.equals(response.toString()) && board.getHealth() != 0) {
                        lastData = response.toString();
                        System.out.println("New data received: " + response);

                        while (pause) {
                        }
                        pause = true;

                        boolean validInput = false;
                        while (!validInput) {
                            /*
                             * System.out.println("Enter your new X coordinate: ");
                             * xCoord = scanner.nextInt();
                             * System.out.println("Enter your new Y coordinate: ");
                             * yCoord = scanner.nextInt();
                             */

                            validInput = ((0 <= xCoord && xCoord <= 9) && (0 <= yCoord && yCoord <= 9));

                            if (!validInput) {
                                System.out.println("Invalid input, must be between 0 and 9");
                            }

                        }
                        // Read new coordinates from user

                        board.oPanelList.get(yCoord * 10 + xCoord).setBackground(Color.BLACK);
                        postData(xCoord, yCoord, board);
                    } else {
                        postData(0, 0, board);
                    }
                } catch (Exception e) {
                    ErrorHandler.handleError(e);
                }
            }
        }, 0, 1000); // Check every second
    }

    public void postData(int xCoord, int yCoord, Board board) {

        try {
            pause = false;

            /*
             * POST new data
             * Format for data:
             * "UID;x,y,0"
             * That zero is reserved for now, will be used to check win condition
             */
            String postData = this.uid + ";" + xCoord + "," + yCoord + ",0";
            if (board.getHealth() == 0) {

                postData += ",1";
                board.losingMenu();
            } else {
                postData += ",0";
            }
            URL postUrl = new URL("http://" + serverIP + ":" + Settings.PORT_NUMBER + Settings.SERVER_ENDPOINT);
            HttpURLConnection postCon = (HttpURLConnection) postUrl.openConnection();
            postCon.setRequestMethod("POST");

            postCon.setDoOutput(true);
            System.out.println("PRINTING HERE!!!!\n" + postCon.getOutputStream() + "\n\n");
            DataOutputStream wr = new DataOutputStream(postCon.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();

            postCon.getResponseCode();
        } catch (Exception e) {
            ErrorHandler.handleError(e);
        }
    }
}
