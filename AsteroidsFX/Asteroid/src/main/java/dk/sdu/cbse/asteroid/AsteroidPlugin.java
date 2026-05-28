package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.commonAsteroid.Asteroid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private static final int START_ASTEROIDS = 3;

    private final Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < START_ASTEROIDS; i++) {
            world.addEntity(AsteroidFactory.createRandomAsteroid(gameData, random));
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        List<Entity> asteroids = new ArrayList<>(world.getEntitiesByClass(Asteroid.class));

        for (Entity asteroid : asteroids) {
            world.removeEntity(asteroid);
        }
    }
}