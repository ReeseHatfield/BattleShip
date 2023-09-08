import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Driver {
    public Driver() throws IOException {
        // Bind to 0.0.0.0 to make the server accessible from any IP address
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8002), 0);

        createEndpoints(server);

        server.setExecutor(null);
        server.start();
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
