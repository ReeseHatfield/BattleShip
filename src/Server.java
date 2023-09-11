import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static ConcurrentHashMap<String, String> userData = new ConcurrentHashMap<>();  // To store user data

    public Server() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", Settings.PORT_NUMBER), 0);
        createEndpoints(server);
        server.setExecutor(null);
        server.start();
        try {
            System.out.println("Server started. Your local network IP address: " + Utils.getLocalNetworkIP());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private HttpServer createEndpoints(HttpServer server) {
        server.createContext(Settings.SERVER_ENDPOINT, new BattleShipHandler());
        return server;
    }

    public static void main(String[] args) throws IOException {
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