package dk.sdu.cbse.common;

public class GameKeys {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int SPACE = 4;

    private static final int KEY_COUNT = 5;

    private final boolean[] keys = new boolean[KEY_COUNT];
    private final boolean[] previousKeys = new boolean[KEY_COUNT];

    public void setKey(int key, boolean pressed) {
        if (!isValidKey(key)) {
            return;
        }

        keys[key] = pressed;
    }

    public boolean isDown(int key) {
        if (!isValidKey(key)) {
            return false;
        }

        return keys[key];
    }

    public boolean isPressed(int key) {
        if (!isValidKey(key)) {
            return false;
        }

        return keys[key] && !previousKeys[key];
    }

    public void update() {
        for (int i = 0; i < KEY_COUNT; i++) {
            previousKeys[i] = keys[i];
        }
    }

    private boolean isValidKey(int key) {
        return key >= 0 && key < KEY_COUNT;
    }
}