import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.commonBullet.BulletSPI;

module Enemy {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;

    exports dk.sdu.cbse.enemy;

    uses BulletSPI;

    provides IGamePluginService with dk.sdu.cbse.enemy.EnemyPlugin;
    provides IEntityProcessorService with dk.sdu.cbse.enemy.EnemyMovementSystem;
}