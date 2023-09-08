import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Driver {
    public static void main(String[] args) throws IOException {
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/main", new BattleShipHandler());
        server.setExecutor(null);
        server.start();

        // bruh
        // http://localhost:8000/main

    }

    static class BattleShipHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String res = "RESPONSE COORDS";

            int statusCode = 200;

            httpExchange.sendResponseHeaders(statusCode, res.length());

            OutputStream os = httpExchange.getResponseBody();
            os.write(res.getBytes().length);
            os.close();
        }
    }
}
