package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.commonAsteroid.Asteroid;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollisionSystemTest {

    @Test
    public void playerBulletHitsAsteroid() {
        GameData gameData = new GameData();
        World world = new World();
        CollisionSystem collisionSystem = new CollisionSystem();

        Entity bullet = new Entity();
        bullet.setType("BULLET");
        bullet.setOwner("PLAYER");
        bullet.setX(100);
        bullet.setY(100);
        bullet.setRadius(5);

        Asteroid asteroid = new Asteroid();
        asteroid.setType("ASTEROID");
        asteroid.setX(100);
        asteroid.setY(100);
        asteroid.setRadius(20);

        world.addEntity(bullet);
        world.addEntity(asteroid);

        collisionSystem.Process(gameData, world);

        assertFalse(world.getEntities().contains(bullet));
        assertTrue(world.getEntities().contains(asteroid));
        assertTrue(asteroid.shouldSplit());
    }
}