import utils.ErrorHandler;
import utils.Settings;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        new ServerSelect();
    }

    private static String lastData = "";
    private String serverIP;
    public volatile boolean isTurn = false;
    public int lastShotX = 0;
    public int lastShotY = 0;

    private Board board = new Board(this);

    // Generate a UID for this client
    // This is generated at runtime for each client,
    // so it is still unique per client
    private final static String uid = UUID.randomUUID().toString();

    public Client(String ip) {
        Scanner scanner = new Scanner(System.in);
        this.serverIP = ip;

        Backend backend = new Backend(scanner);
        // create GUI here

        for (Ship s : backend.getShips()) {
            for (Point p : s.points) {
                HittableButton button = board.playerBoard.get(p.y * 10 + p.x);
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
                    playGame();
                } catch (Exception e) {
                    ErrorHandler.handleError(e);
                }
            }
        }, 0, 500); // Check 1/2 second
    }

    public void postData(int xCoord, int yCoord, int didHit, int didLose, int endTurn, Board board) {

        try {
            String postData = this.uid + ";" + xCoord + "," + yCoord + "," + didHit + "," + didLose + "," + endTurn;

            URL postUrl = new URL("http://" + serverIP + ":" + Settings.PORT_NUMBER + Settings.SERVER_ENDPOINT);
            HttpURLConnection postCon = (HttpURLConnection) postUrl.openConnection();
            postCon.setRequestMethod("POST");

            postCon.setDoOutput(true);
            // System.out.println("PRINTING HERE!!!!\n" + postCon.getOutputStream() +
            // "\n\n");
            DataOutputStream wr = new DataOutputStream(postCon.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();

            postCon.getResponseCode();
        } catch (Exception e) {
            ErrorHandler.handleError(e);
        }
    }

    public void playGame() throws Exception {

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
        System.out.println("GET: " + response);
        System.out.println(isTurn);

        // Player 1 gets their turn and Player 2 waits for Player 1
        if (response.toString().equals("No data from other client") && !lastData.equals(response.toString())) {
            lastData = response.toString();
            isTurn = true;
            postData(10, 10, 0, 0, 0, board); // only case to post 10s
            return;
        }
        int xCoord = Integer.parseInt(response.toString().split(",")[0]);
        int yCoord = Integer.parseInt(response.toString().split(",")[1]);
        int didHit = Integer.parseInt(response.toString().split(",")[2]);
        int didLose = Integer.parseInt(response.toString().split(",")[3]);
        int endTurn = Integer.parseInt(response.toString().split(",")[4]);

        if (endTurn == 1) {
            isTurn = true;
        }

        // Client did not hit a ship, other player posted 12
        // Receiving 12 means post endTurn = 1
        if (xCoord == 12) {
            isTurn = false;
            postData(10, 10, 0, 0, 1, board);
        }

        /*
         * if (xCoord == 10) {
         * isTurn = false;
         * }
         */
        // if (response.toString().equals(lastData)) {
        // return;
        // }
        lastData = response.toString();

        System.out.println("MADE IT HERE");

        // If junk data, return

        if (xCoord < 10) {
            if (board.playerBoard.get(yCoord * 10 + xCoord).hit()) {// If true a ship was hit
                if (board.getHealth() < 1) {
                    postData(11, 11, 1, 1, 1, board); // Hit cases post 11s
                    board.losingMenu();
                    isTurn = false;
                    return;
                } else {
                    postData(11, 11, 1, 0, 1, board); // Hit cases post 11s
                    isTurn = false;
                    return;
                }
            } else
                postData(12, 12, 0, 0, 0, board); // No Hit posts 12
        }

        // Check didLose -> close window and open winning menu
        if (didLose == 1) {
            board.winningMenu();
        }

        if (didHit == 1) {
            board.otherBoard.get(lastShotY * 10 + lastShotX).setBackground(Color.RED);
        }
        // Check didHit -> Update RIGHT board with red square instead of black square

        // Check endTurn last so board gets properly updated before buttons are enabled
        // System.out.println("thing:" +
        // Arrays.toString(response.toString().split(",")));

    }
}
