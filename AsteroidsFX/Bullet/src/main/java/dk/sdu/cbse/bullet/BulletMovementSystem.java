package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.World;

public class BulletMovementSystem implements IEntityProcessorService {

    @Override
    public void Process(GameData gameData, World world) {
        for (Entity bullet : world.getEntitiesByClass(Bullet.class)) {
            moveBullet(bullet);
            removeBulletIfOutsideScreen(bullet, gameData, world);
        }
    }

    private void moveBullet(Entity bullet) {
        bullet.setX(bullet.getX() + bullet.getVelocityX());
        bullet.setY(bullet.getY() + bullet.getVelocityY());
    }

    private void removeBulletIfOutsideScreen(Entity bullet, GameData gameData, World world) {
        if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth()) {
            world.removeEntity(bullet);
            return;
        }

        if (bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
            world.removeEntity(bullet);
        }
    }
}
