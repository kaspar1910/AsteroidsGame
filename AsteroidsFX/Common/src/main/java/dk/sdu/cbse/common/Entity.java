package dk.sdu.cbse.common;

import java.util.Arrays;
import java.util.UUID;
import javafx.scene.paint.Color;

/**
 * Base class for all game objects in the world.
 */
public class Entity {

    private final UUID id = UUID.randomUUID();

    private double x;
    private double y;

    private double velocityX;
    private double velocityY;
    private double radius;
    private double rotation;
    private double[] polygonCoordinates;

    private Color color;
    private String type;
    private String owner = "NONE";

    public UUID getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {

        this.radius = radius;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double[] getColliderCoordinates() {
        return Arrays.copyOf(polygonCoordinates, polygonCoordinates.length);
    }

    public void setColliderCoordinates(double... polygonCoordinates) {
        this.polygonCoordinates = Arrays.copyOf(polygonCoordinates, polygonCoordinates.length);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}