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
    public boolean isTurn = false;
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
                    //System.out.println("IM PRINTING EVERY SECOND");
                    // GET request to fetch data
    //                    URL getUrl = new URL(
    //                            "http://" + serverIP + ":" + Settings.PORT_NUMBER + Settings.SERVER_ENDPOINT + "?" + uid);
    //                    HttpURLConnection getCon = (HttpURLConnection) getUrl.openConnection();
    //                    getCon.setRequestMethod("GET");
    //
    //                    BufferedReader in = new BufferedReader(new InputStreamReader(getCon.getInputStream()));
    //                    String inputLine;
    //                    StringBuilder response = new StringBuilder();
    //
    //                    while ((inputLine = in.readLine()) != null) {
    //                        response.append(inputLine);
    //                    }
    //                    in.close();

                    //System.out.println("GET: " + response);

                    // Update board here
                    // check other strings for game setup so two players connect
                    /*if (!response.toString().equals("No data from other client")) {
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

                        boolean validInput = false;
                        while (!validInput) {
                            *//*
                             * System.out.println("Enter your new X coordinate: ");
                             * xCoord = scanner.nextInt();
                             * System.out.println("Enter your new Y coordinate: ");
                             * yCoord = scanner.nextInt();
                             *//*

                            validInput = ((0 <= xCoord && xCoord <= 9) && (0 <= yCoord && yCoord <= 9));

                            if (!validInput) {
                                System.out.println("Invalid input, must be between 0 and 9");
                            }

                        }
                        // Read new coordinates from user

                        board.oPanelList.get(yCoord * 10 + xCoord).setBackground(Color.BLACK);*/
                        //postData(xCoord, yCoord, board);
                    //}
    //                    } else {
    //                        postData(0, 0, board);
    //                    }
                } catch (Exception e) {
                    ErrorHandler.handleError(e);
                }
            }
        }, 0, 1000); // Check every second
    }

    public void postData(int xCoord, int yCoord, int didHit, int didLose, int endTurn, Board board) {

        try {
            /*
             * POST new data
             * Format for data:
             * "UID;x,y,0"
             * That zero is reserved for now, will be used to check win condition
             */
            String postData = this.uid + ";" + xCoord + "," + yCoord + "," + didHit + "," + didLose + "," + endTurn;

            URL postUrl = new URL("http://" + serverIP + ":" + Settings.PORT_NUMBER + Settings.SERVER_ENDPOINT);
            HttpURLConnection postCon = (HttpURLConnection) postUrl.openConnection();
            postCon.setRequestMethod("POST");

            postCon.setDoOutput(true);
            //System.out.println("PRINTING HERE!!!!\n" + postCon.getOutputStream() + "\n\n");
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

    //        if (!isTurn) {
    //            return;
    //        }
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




        //Player 1 gets their turn and Player 2 waits for Player 1
        if (response.toString().equals("No data from other client") && !lastData.equals(response.toString())) {
            lastData = response.toString();
            isTurn = true;
            postData(10,10,0,0,0,board);
            return;
        }
        int xCoord = Integer.parseInt(response.toString().split(",")[0]);
        int yCoord = Integer.parseInt(response.toString().split(",")[1]);
        int didHit = Integer.parseInt(response.toString().split(",")[2]);
        int didLose = Integer.parseInt(response.toString().split(",")[3]);
        int endTurn = Integer.parseInt(response.toString().split(",")[4]);

        

        if (response.toString().equals(lastData)) {
            return;
        }
        lastData = response.toString();


        System.out.println("MADE IT HERE");


        //If junk data, return

        if(xCoord < 10){
            if (board.playerBoard.get(yCoord * 10 + xCoord).hit()) {//If true a ship was hit
                if (board.getHealth() < 1) {
                    postData(10,10,1,1,1,board);
                    board.losingMenu();
                    return;
                }
                else {
                    postData(10,10,1,0,1,board);
                    return;
                }
            }
        }

        //Check didLose -> close window and open winning menu
        if (didLose == 1) {
            board.winningMenu();
        }

        if (didHit == 1) {
            board.otherBoard.get(lastShotY * 10 + lastShotX).setBackground(Color.RED);
        }
        //Check didHit -> Update RIGHT board with red square instead of black square


        //Check endTurn last so board gets properly updated before buttons are enabled
        //System.out.println("thing:" + Arrays.toString(response.toString().split(",")));
        if(endTurn == 1) {
            isTurn = true;
        }

    }
    }
