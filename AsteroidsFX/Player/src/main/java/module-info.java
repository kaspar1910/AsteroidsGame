import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.commonBullet.BulletSPI;

module Player {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;

    exports dk.sdu.cbse.player;

    uses BulletSPI;

    provides IGamePluginService with dk.sdu.cbse.player.PlayerPlugin;
    provides IEntityProcessorService with dk.sdu.cbse.player.PlayerMovementSystem;
}