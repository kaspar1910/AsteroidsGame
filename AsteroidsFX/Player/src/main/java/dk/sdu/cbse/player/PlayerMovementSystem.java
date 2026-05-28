package dk.sdu.cbse.player;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameKeys;
import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.commonBullet.BulletSPI;
import java.util.ServiceLoader;

public class PlayerMovementSystem implements IEntityProcessorService {

    private static final double ROTATION_SPEED = 4.0;
    private static final double MOVEMENT_SPEED = 3.0;

    private final BulletSPI bulletService;

    public PlayerMovementSystem() {
        bulletService = loadBulletService();
    }

    @Override
    public void Process(GameData gameData, World world) {
        for (Entity player : world.getEntitiesByClass(Player.class)) {
            move(player, gameData);
            shoot(player, gameData, world);
            ifPlayerLeavesScreen(player, gameData);
        }
    }

    private BulletSPI loadBulletService() {
        for (BulletSPI service : ServiceLoader.load(BulletSPI.class)) {
            return service;
        }

        return null;
    }

    private void move(Entity player, GameData gameData) {
        if (gameData.getKeys().isDown(GameKeys.LEFT)) {
            player.setRotation(player.getRotation() - ROTATION_SPEED);
        }

        if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
            player.setRotation(player.getRotation() + ROTATION_SPEED);
        }

        if (gameData.getKeys().isDown(GameKeys.UP)) {
            double radians = Math.toRadians(player.getRotation());

            player.setX(player.getX() + Math.cos(radians) * MOVEMENT_SPEED);
            player.setY(player.getY() + Math.sin(radians) * MOVEMENT_SPEED);
        }

        if (gameData.getKeys().isDown(GameKeys.DOWN)) {
            double radians = Math.toRadians(player.getRotation());

            player.setX(player.getX() - Math.cos(radians) * MOVEMENT_SPEED);
            player.setY(player.getY() - Math.sin(radians) * MOVEMENT_SPEED);
        }
    }

    private void shoot(Entity player, GameData gameData, World world) {
        if (!gameData.getKeys().isPressed(GameKeys.SPACE)) {
            return;
        }

        bulletService.createBullet(player, world);
    }

    private void ifPlayerLeavesScreen(Entity player, GameData gameData) {
        if (player.getX() < 0) {
            player.setX(gameData.getDisplayWidth());
        }

        if (player.getX() > gameData.getDisplayWidth()) {
            player.setX(0);
        }

        if (player.getY() < 0) {
            player.setY(gameData.getDisplayHeight());
        }

        if (player.getY() > gameData.getDisplayHeight()) {
            player.setY(0);
        }
    }
}