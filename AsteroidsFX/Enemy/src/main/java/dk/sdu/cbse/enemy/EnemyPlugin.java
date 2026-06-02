package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.common.World;
import javafx.scene.paint.Color;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Enemy();

        enemy.setX(gameData.getDisplayWidth() * 0.25);
        enemy.setY(gameData.getDisplayHeight() * 0.25);
        enemy.setRadius(10);
        enemy.setRotation(0);
        enemy.setColor(Color.RED);
        enemy.setType("ENEMY");

        enemy.setColliderCoordinates(
                -10, -8, 14, 0, -10, 8
        );

        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}