package dk.sdu.cbse.player;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.common.World;
import javafx.scene.paint.Color;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = new Player();

        //sets in middle of screen
        player.setX(gameData.getDisplayWidth() / 2.0);
        player.setY(gameData.getDisplayHeight() / 2.0);
        player.setRadius(8);
        player.setRotation(0);
        player.setColor(Color.WHITE);
        player.setType("PLAYER");

        //x and y coords to define points that represent a triangle
        player.setColliderCoordinates(-8, -6, 12, 0, -8, 6);


        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}