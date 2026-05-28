package dk.sdu.cbse.commonAsteroid;

import dk.sdu.cbse.common.Entity;

public class Asteroid extends Entity {

    private int splitLevel;
    private boolean shouldSplit;

    public int getSplitLevel() {
        return splitLevel;
    }

    public void setSplitLevel(int splitLevel) {
        this.splitLevel = splitLevel;
    }

    public boolean shouldSplit() {
        return shouldSplit;
    }

    public void setShouldSplit(boolean shouldSplit) {
        this.shouldSplit = shouldSplit;
    }
}