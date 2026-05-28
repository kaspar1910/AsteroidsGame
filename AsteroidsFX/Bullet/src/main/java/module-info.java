import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.commonBullet.BulletSPI;

module Bullet {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;

    exports dk.sdu.cbse.bullet;

    provides BulletSPI with dk.sdu.cbse.bullet.BulletMaker;
    provides IEntityProcessorService with dk.sdu.cbse.bullet.BulletMovementSystem;
}