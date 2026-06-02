package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IPostEntityProcessorService;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.commonAsteroid.Asteroid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionSystem implements IPostEntityProcessorService {

    private final ScoreClient scoreClient;

    private static final String PLAYER = "PLAYER";
    private static final String BULLET = "BULLET";
    private static final String ASTEROID = "ASTEROID";
    private static final String ENEMY = "ENEMY";

    private final Set<Entity> entitiesToRemove = new HashSet<>();

    public CollisionSystem() {
        this.scoreClient = new ScoreClient();
    }


    @Override
    public void Process(GameData gameData, World world) {
        entitiesToRemove.clear();

        List<Entity> entities = new ArrayList<>(world.getEntities());

        for (int firstIndex = 0; firstIndex < entities.size(); firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < entities.size(); secondIndex++) {
                Entity first = entities.get(firstIndex);
                Entity second = entities.get(secondIndex);

                if (collides(first, second)) {
                    handleCollision(first, second);
                    handleCollision(second, first);
                }
            }
        }

        removeMarkedEntities(world);
    }

    private void handleCollision(Entity first, Entity second) {
        if (isPlayerBullet(first) && second instanceof Asteroid asteroid) {
            entitiesToRemove.add(first);
            asteroid.setShouldSplit(true);
            scoreClient.addPoints(10);
        }

        if (isType(first, PLAYER) && isType(second, ASTEROID)) {
            entitiesToRemove.add(first);
            System.out.println("GAME OVER");
        }

        if (isPlayerBullet(first) && isType(second, ENEMY)) {
            entitiesToRemove.add(first);
            entitiesToRemove.add(second);
        }

        if (isType(first, PLAYER) && isEnemyBullet(second)) {
            entitiesToRemove.add(first);
            entitiesToRemove.add(second);
            System.out.println("GAME OVER");
        }
    }

    private boolean isPlayerBullet(Entity entity) {
        return isType(entity, BULLET) && PLAYER.equals(entity.getOwner());
    }

    private boolean isEnemyBullet(Entity entity) {
        return isType(entity, BULLET) && ENEMY.equals(entity.getOwner());
    }

    private boolean isType(Entity entity, String type) {
        return type.equals(entity.getType());
    }

    private boolean collides(Entity first, Entity second) {
        double distanceX = first.getX() - second.getX();
        double distanceY = first.getY() - second.getY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        return distance < first.getRadius() + second.getRadius();
    }

    private void removeMarkedEntities(World world) {
        for (Entity entity : entitiesToRemove) {
            world.removeEntity(entity);
        }
    }
}