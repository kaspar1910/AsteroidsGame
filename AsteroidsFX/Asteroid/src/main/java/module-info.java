import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.IGamePluginService;

module Asteroid {
    requires Common;
    requires CommonAsteroid;
    requires javafx.graphics;

    exports dk.sdu.cbse.asteroid;

    provides IGamePluginService with dk.sdu.cbse.asteroid.AsteroidPlugin;
    provides IEntityProcessorService with dk.sdu.cbse.asteroid.AsteroidProcessor;
}