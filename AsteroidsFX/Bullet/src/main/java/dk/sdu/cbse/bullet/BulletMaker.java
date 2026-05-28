package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.commonBullet.BulletSPI;
import javafx.scene.paint.Color;

public class BulletMaker implements BulletSPI {

    private static final double BULLET_SPEED = 7.0;

    @Override
    public void createBullet(Entity shooter, World world) {
        Entity bullet = new Bullet();
        double radians = Math.toRadians(shooter.getRotation());

        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setVelocityX(Math.cos(radians) * BULLET_SPEED);
        bullet.setVelocityY(Math.sin(radians) * BULLET_SPEED);
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(3);
        bullet.setColor(Color.YELLOW);
        bullet.setType("BULLET");
        bullet.setOwner(shooter.getType());

        bullet.setColliderCoordinates(-2, -2, 4, 0, 2, 2);

        world.addEntity(bullet);
    }
}