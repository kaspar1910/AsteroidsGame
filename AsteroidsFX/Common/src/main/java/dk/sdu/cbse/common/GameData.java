package dk.sdu.cbse.common;

public class GameData {

    private int displayWidth = 800;
    private int displayHeight = 600;
    private final GameKeys keys = new GameKeys();

    public int getDisplayWidth() {
        return displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public GameKeys getKeys() {
        return keys;
    }
}