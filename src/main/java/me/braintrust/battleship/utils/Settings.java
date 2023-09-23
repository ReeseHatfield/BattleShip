package me.braintrust.battleship.utils;

public class Settings {

    public static final int PORT_NUMBER = 8001;
    public static final int HTTP_SUCCESS_CODE = 200;
    public static final String SERVER_ENDPOINT = "/main";
    public static final int GAME_WIDTH_PX = 1400;
    public static final int GAME_HEIGHT_PX = 760;

    private Settings() {
        throw new UnsupportedOperationException("Settings should not be instantiated!");
    }
}
