import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import exceptions.InvalidMoveException;
import utils.ErrorHandler;
import utils.Settings;
import utils.Utils;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

/*
    Server.java
    A simple HTTP server for exchanging battleship coordinates across
    multiple different clients
    *technically* a utility class since the reference to its state memory position
    never changes. It is NOT just a utility class, don't worry if IDE
    says to not instantiate it
 */

public class Server {

    // Thead-safe hashmap of <UUID, dataPassed>
    // Could probably get away without thread safety, but it's easier if it is
    private static final ConcurrentHashMap<String, String> userData = new ConcurrentHashMap<>();

    /*
        Server Constructor:
        Creates an instance of this HTTP server
        Contains a private static handler class
     */
    public Server() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0",
                Settings.PORT_NUMBER), 0);
        server.createContext(Settings.SERVER_ENDPOINT, new BattleShipHandler());
        server.setExecutor(null);
        server.start();
        try {
            System.out.println("Server started. Your local network IP address: " + Utils.getLocalNetworkIP());
        } catch (SocketException e) {
            ErrorHandler.handleError(e);
        }
    }

    public static void main(String[] args) throws IOException {
        // IDE may complain about this, don't worry about it
        new Server();
    }

    static class BattleShipHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String method = httpExchange.getRequestMethod();

            if ("POST".equals(method)) {
                handlePOST(httpExchange);
            } else if ("GET".equals(method)) {
                handleGET(httpExchange);
            }
        }

        private static void handleGET(HttpExchange httpExchange) throws IOException {
            String uid = httpExchange.getRequestURI().getQuery();
            String otherClientData = "No data from other client";
            for (String otherUID : userData.keySet()) {
                if (!otherUID.equals(uid)) {
                    otherClientData = userData.get(otherUID);
                    break;
                }
            }
            httpExchange.sendResponseHeaders(Settings.HTTP_SUCCESS_CODE, otherClientData.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(otherClientData.getBytes());
            os.close();
        }

        private static void handlePOST(HttpExchange httpExchange) throws IOException {

            InputStream is = httpExchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder requestBody = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            reader.close();

            String[] parts = requestBody.toString().split(";");
            if (parts.length == 2) {
                String uid = parts[0];
                String data = parts[1];
                userData.put(uid, data);  // Store the received data
            }
            else{
                throw new InvalidMoveException("Error: message cannot contain `;`");
            }

            String res = "Data stored successfully";
            httpExchange.sendResponseHeaders(Settings.HTTP_SUCCESS_CODE, res.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(res.getBytes());
            os.close();
        }
    }

}