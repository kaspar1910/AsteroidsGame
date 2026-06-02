package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.commonAsteroid.Asteroid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidProcessor implements IEntityProcessorService {

    private static final int MAX_ASTEROIDS = 8;
    private static final double SPAWN_CHANCE = 0.01;
    private static final double REMOVE_MARGIN = 180.0;

    private final Random random = new Random();

    @Override
    public void Process(GameData gameData, World world) {
        handleSplitAsteroids(world);
        spawnAsteroids(gameData, world);
        moveAsteroids(gameData, world);
    }

    private void handleSplitAsteroids(World world) {
        List<Entity> asteroids = new ArrayList<>(world.getEntitiesByClass(Asteroid.class));

        for (Entity entity : asteroids) {
            Asteroid asteroid = (Asteroid) entity;

            if (asteroid.shouldSplit()) {
                splitOrRemoveAsteroid(asteroid, world);
            }
        }
    }

    private void splitOrRemoveAsteroid(Asteroid asteroid, World world) {
        world.removeEntity(asteroid);

        if (asteroid.getSplitLevel() >= AsteroidFactory.MAX_SPLIT_LEVEL) {
            return;
        }

        world.addEntity(AsteroidFactory.createSplitAsteroid(asteroid, -35.0, random));
        world.addEntity(AsteroidFactory.createSplitAsteroid(asteroid, 35.0, random));
    }

    private void spawnAsteroids(GameData gameData, World world) {
        int asteroidCount = world.getEntitiesByClass(Asteroid.class).size();

        if (asteroidCount >= MAX_ASTEROIDS) {
            return;
        }

        if (random.nextDouble() < SPAWN_CHANCE) {
            world.addEntity(AsteroidFactory.createRandomAsteroid(gameData, random));
        }
    }

    private void moveAsteroids(GameData gameData, World world) {
        List<Entity> asteroids = new ArrayList<>(world.getEntitiesByClass(Asteroid.class));

        for (Entity asteroid : asteroids) {
            asteroid.setX(asteroid.getX() + asteroid.getVelocityX());
            asteroid.setY(asteroid.getY() + asteroid.getVelocityY());
            asteroid.setRotation(asteroid.getRotation() + 0.5);

            removeIfFarOutsideScreen(asteroid, gameData, world);
        }
    }

    private void removeIfFarOutsideScreen(Entity asteroid, GameData gameData, World world) {
        boolean outsideLeft = asteroid.getX() < -REMOVE_MARGIN;
        boolean outsideRight = asteroid.getX() > gameData.getDisplayWidth() + REMOVE_MARGIN;
        boolean outsideTop = asteroid.getY() < -REMOVE_MARGIN;
        boolean outsideBottom = asteroid.getY() > gameData.getDisplayHeight() + REMOVE_MARGIN;

        if (outsideLeft || outsideRight || outsideTop || outsideBottom) {
            world.removeEntity(asteroid);
        }
    }
}