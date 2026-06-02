package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.commonBullet.BulletSPI;
import java.util.Random;
import java.util.ServiceLoader;

public class EnemyMovementSystem implements IEntityProcessorService {

    private static final double ROTATION_SPEED = 3.0;
    private static final double MOVEMENT_SPEED = 1.0;
    private static final long SHOOT_DELAY = 1_000_000_000L;
    private static final long DIRECTION_CHANGE_DELAY = 800_000_000L;

    private final Random random = new Random();
    private final BulletSPI bulletService;

    private long lastShotTime = System.nanoTime();
    private long lastDirectionChangeTime = System.nanoTime();
    private double rotationDirection = 1.0;

    public EnemyMovementSystem() {
        bulletService = loadBulletService();
    }

    @Override
    public void Process(GameData gameData, World world) {
        long now = System.nanoTime();

        updateRandomDirection(now);

        for (Entity enemy : world.getEntitiesByClass(Enemy.class)) {
            rotate(enemy);
            move(enemy);
            shoot(enemy, world, now);
            ifEnemyLeavesScreen(enemy, gameData);
        }
    }

    private BulletSPI loadBulletService() {
        ModuleLayer pluginLayer = EnemyMovementSystem.class.getModule().getLayer();

        for (BulletSPI service : ServiceLoader.load(pluginLayer, BulletSPI.class)) {
            return service;
        }

        return null;
    }

    private void updateRandomDirection(long now) {
        if (now - lastDirectionChangeTime < DIRECTION_CHANGE_DELAY) {
            return;
        }

        rotationDirection = -1.0 + random.nextDouble() * 2.0;
        lastDirectionChangeTime = now;
    }

    private void rotate(Entity enemy) {
        enemy.setRotation(enemy.getRotation() + ROTATION_SPEED * rotationDirection);
    }

    private void move(Entity enemy) {
        double radians = Math.toRadians(enemy.getRotation());

        enemy.setX(enemy.getX() + Math.cos(radians) * MOVEMENT_SPEED);
        enemy.setY(enemy.getY() + Math.sin(radians) * MOVEMENT_SPEED);
    }

    private void shoot(Entity enemy, World world, long now) {
        if (now - lastShotTime < SHOOT_DELAY) {
            return;
        }

        bulletService.createBullet(enemy, world);
        lastShotTime = now;
    }

    private void ifEnemyLeavesScreen(Entity enemy, GameData gameData) {
        if (enemy.getX() < 0) {
            enemy.setX(gameData.getDisplayWidth());
        }

        if (enemy.getX() > gameData.getDisplayWidth()) {
            enemy.setX(0);
        }

        if (enemy.getY() < 0) {
            enemy.setY(gameData.getDisplayHeight());
        }

        if (enemy.getY() > gameData.getDisplayHeight()) {
            enemy.setY(0);
        }
    }
}