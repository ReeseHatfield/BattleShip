import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public class ErrorHandler {
    public static void handleError(Exception e){
        if (e instanceof MalformedURLException) {
            System.out.println("The URL is malformed: " + e.getMessage());
            System.out.println("Ensure endpoint, port, and UUID are correct and confirm server is live");

        } else if (e instanceof ProtocolException) {
            System.out.println("Protocol exception: " + e.getMessage());
            System.out.println("Ensure server is live");

        } else if (e instanceof IOException) {
            System.out.println("I/O exception: " + e.getMessage());
            System.out.println("Ensure server port is not busy");

        } else {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        e.printStackTrace();
    }
}
