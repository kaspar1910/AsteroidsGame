import dk.sdu.cbse.common.IPostEntityProcessorService;

module Collision {
    requires Common;
    requires CommonAsteroid;
    requires java.net.http;

    exports dk.sdu.cbse.collision;

    provides IPostEntityProcessorService with dk.sdu.cbse.collision.CollisionSystem;
}