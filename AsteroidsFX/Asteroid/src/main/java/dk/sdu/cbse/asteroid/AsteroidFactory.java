package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.commonAsteroid.Asteroid;
import java.util.Random;
import javafx.scene.paint.Color;

public final class AsteroidFactory {

    public static final int MAX_SPLIT_LEVEL = 2;

    private static final double OUTSIDE_MARGIN = 80.0;
    private static final double MIN_SPEED = 0.8;
    private static final double MAX_SPEED = 2.0;

    private AsteroidFactory() {
    }

    public static Asteroid createRandomAsteroid(GameData gameData, Random random) {
        Asteroid asteroid = createBaseAsteroid(36.0, 0, random);

        placeOutsideScreen(asteroid, gameData, random);
        aimAtRandomMapPosition(asteroid, gameData, random);

        return asteroid;
    }

    public static Asteroid createSplitAsteroid(Asteroid source, double angleOffset, Random random) {
        double newRadius = source.getRadius() / 2.0;

        Asteroid asteroid = createBaseAsteroid(
                newRadius,
                source.getSplitLevel() + 1,
                random
        );

        asteroid.setX(source.getX());
        asteroid.setY(source.getY());

        double baseAngle = Math.atan2(source.getVelocityY(), source.getVelocityX());
        double splitAngle = baseAngle + Math.toRadians(angleOffset);
        double speed = getSpeed(source);

        asteroid.setVelocityX(Math.cos(splitAngle) * speed);
        asteroid.setVelocityY(Math.sin(splitAngle) * speed);

        return asteroid;
    }

    private static Asteroid createBaseAsteroid(double radius, int splitLevel, Random random) {
        Asteroid asteroid = new Asteroid();

        asteroid.setRadius(radius);
        asteroid.setRotation(random.nextDouble() * 360.0);
        asteroid.setColor(Color.GRAY);
        asteroid.setType("ASTEROID");
        asteroid.setSplitLevel(splitLevel);
        asteroid.setShouldSplit(false);
        asteroid.setColliderCoordinates(createAsteroidShape(radius, random));

        return asteroid;
    }

    private static void placeOutsideScreen(Asteroid asteroid, GameData gameData, Random random) {
        int side = random.nextInt(4);

        if (side == 0) {
            asteroid.setX(-OUTSIDE_MARGIN);
            asteroid.setY(random.nextDouble() * gameData.getDisplayHeight());
        }

        if (side == 1) {
            asteroid.setX(gameData.getDisplayWidth() + OUTSIDE_MARGIN);
            asteroid.setY(random.nextDouble() * gameData.getDisplayHeight());
        }

        if (side == 2) {
            asteroid.setX(random.nextDouble() * gameData.getDisplayWidth());
            asteroid.setY(-OUTSIDE_MARGIN);
        }

        if (side == 3) {
            asteroid.setX(random.nextDouble() * gameData.getDisplayWidth());
            asteroid.setY(gameData.getDisplayHeight() + OUTSIDE_MARGIN);
        }
    }

    private static void aimAtRandomMapPosition(Asteroid asteroid, GameData gameData, Random random) {
        double targetX = random.nextDouble() * gameData.getDisplayWidth();
        double targetY = random.nextDouble() * gameData.getDisplayHeight();

        double directionX = targetX - asteroid.getX();
        double directionY = targetY - asteroid.getY();
        double length = Math.sqrt(directionX * directionX + directionY * directionY);

        if (length == 0) {
            length = 1;
        }

        double speed = MIN_SPEED + random.nextDouble() * (MAX_SPEED - MIN_SPEED);

        asteroid.setVelocityX(directionX / length * speed);
        asteroid.setVelocityY(directionY / length * speed);
    }

    private static double getSpeed(Asteroid asteroid) {
        double speed = Math.sqrt(
                asteroid.getVelocityX() * asteroid.getVelocityX()
                        + asteroid.getVelocityY() * asteroid.getVelocityY()
        );

        return Math.max(speed, 1.2);
    }

    private static double[] createAsteroidShape(double radius, Random random) {
        int points = 9;
        double[] coordinates = new double[points * 2];

        for (int i = 0; i < points; i++) {
            double angle = Math.toRadians((360.0 / points) * i);
            double roughRadius = radius * (0.75 + random.nextDouble() * 0.35);

            coordinates[i * 2] = Math.cos(angle) * roughRadius;
            coordinates[i * 2 + 1] = Math.sin(angle) * roughRadius;
        }

        return coordinates;
    }
}