package utils;

public class Settings {
    // Run server on Port 8001
    public static final int PORT_NUMBER;

    // On success
    public static final int HTTP_SUCCESS_CODE;

    public static final String SERVER_ENDPOINT;

    static {
        PORT_NUMBER = 8001;
        HTTP_SUCCESS_CODE = 200;
        SERVER_ENDPOINT = "/main";
    }
}
