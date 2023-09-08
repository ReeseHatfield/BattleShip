import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class Driver {
    public Driver() throws IOException {
        // Bind to 0.0.0.0 to make the server accessible from any IP address
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8001), 0);

        createEndpoints(server);

        server.setExecutor(null);
        server.start();

        // Print the IP address of the server
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("Server started. Your current IP address: " + ip.getHostAddress());
        } catch (UnknownHostException e) {
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
}
