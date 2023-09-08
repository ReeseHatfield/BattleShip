import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class Driver {
    private static ConcurrentHashMap<String, String> userData = new ConcurrentHashMap<>();  // To store user data

    public Driver() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8001), 0);
        createEndpoints(server);
        server.setExecutor(null);
        server.start();
        try {
            System.out.println("Server started. Your local network IP address: " + getLocalNetworkIP());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private HttpServer createEndpoints(HttpServer server) {
        server.createContext("/main", new BattleShipHandler());
        return server;
    }

    public static void main(String[] args) throws IOException {
        new Driver();
    }

    static class BattleShipHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String clientIP = httpExchange.getRemoteAddress().getAddress().getHostAddress();
            String method = httpExchange.getRequestMethod();

            if ("POST".equals(method)) {
                // Handle POST request
                InputStream is = httpExchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuilder requestBody = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                reader.close();

                userData.put(clientIP, requestBody.toString());  // Store the received data

                String res = "Data stored successfully";
                httpExchange.sendResponseHeaders(200, res.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            } else if ("GET".equals(method)) {
                // Handle GET request
                String res = userData.getOrDefault(clientIP, "0,0,0,0");  // Return the stored data
                httpExchange.sendResponseHeaders(200, res.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            }
        }
    }

    public static String getLocalNetworkIP() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                    return inetAddress.getHostAddress();
                }
            }
        }
        return "Unable to find local network IP";
    }
}
