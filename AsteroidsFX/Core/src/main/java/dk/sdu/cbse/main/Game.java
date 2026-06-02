package dk.sdu.cbse.main;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameKeys;
import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.common.IPostEntityProcessorService;
import dk.sdu.cbse.common.World;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Pane gameWindow = new Pane();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();

    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessorService> entityProcessorServices;
    private final List<IPostEntityProcessorService> postEntityProcessorServices;

    public Game(
            List<IGamePluginService> gamePluginServices,
            List<IEntityProcessorService> entityProcessorServices,
            List<IPostEntityProcessorService> postEntityProcessorServices
    ) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessorServices = entityProcessorServices;
        this.postEntityProcessorServices = postEntityProcessorServices;
    }

    public void start(Stage window) {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(gameWindow);
        registerInput(scene);

        for (IGamePluginService plugin : gamePluginServices) {
            plugin.start(gameData, world);
        }

        window.setScene(scene);
        window.setTitle("AsteroidsFX");
        window.show();
    }

    public void render() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        };

        timer.start();
    }

    private void update() {
        for (IEntityProcessorService processor : entityProcessorServices) {
            processor.Process(gameData, world);
        }

        for (IPostEntityProcessorService processor : postEntityProcessorServices) {
            processor.Process(gameData, world);
        }
    }

    private void draw() {
        removeDeletedEntities();
        drawCurrentEntities();
    }

    private void removeDeletedEntities() {
        for (Entity entity : polygons.keySet()) {
            if (!world.getEntities().contains(entity)) {
                Polygon polygon = polygons.remove(entity);
                gameWindow.getChildren().remove(polygon);
            }
        }
    }

    private void drawCurrentEntities() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);

            if (polygon == null) {
                polygon = new Polygon(entity.getColliderCoordinates());
                polygon.setFill(entity.getColor());

                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }

            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
            polygon.setFill(entity.getColor());
        }
    }

    private void registerInput(Scene scene) {
        scene.setOnKeyPressed(event -> setKey(event.getCode(), true));
        scene.setOnKeyReleased(event -> setKey(event.getCode(), false));
    }

    private void setKey(KeyCode keyCode, boolean pressed) {
        if (keyCode == KeyCode.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, pressed);
        }

        if (keyCode == KeyCode.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, pressed);
        }

        if (keyCode == KeyCode.UP) {
            gameData.getKeys().setKey(GameKeys.UP, pressed);
        }

        if (keyCode == KeyCode.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, pressed);
        }

        if (keyCode == KeyCode.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, pressed);
        }
    }
}