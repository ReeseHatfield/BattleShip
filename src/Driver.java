import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.Enumeration;

public class Driver {
    public Driver() throws IOException {
        // Bind to 0.0.0.0 to make the server accessible from any IP address
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8001), 0);

        createEndpoints(server);

        server.setExecutor(null);
        server.start();

        // Print the local network IP address of the server
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
            String res = "RESPONSE COORDS";

            int statusCode = 200;

            httpExchange.sendResponseHeaders(statusCode, res.length());

            OutputStream os = httpExchange.getResponseBody();
            os.write(res.getBytes());
            os.close();
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
