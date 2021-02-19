package Game;

import Sprites.Bullets.Missiles;

import javax.swing.*;
import java.awt.*;

public abstract class GameObjectsManager {


    //only be accessed by the object that inherit the game object
    protected int x, y;
    //Create id to know that Game Object (enum)
    protected ID id;
    //Create variable to control speed in X and Y direction
    protected int velX, velY;
    protected int width;
    protected int height;
    private Image image;
    private boolean ObjectState;
    private boolean visible;
    private Missiles missile;

    //Parameterize Constructor
    public GameObjectsManager(int height, int width, int x, int y, ID id) {
        this.height = height;
        this.width = width;

        this.x = x;
        this.y = y;
        this.id = id;
        this.visible = true;
        this.ObjectState = false;
    }

    public abstract void move();

    public void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        this.image = ii.getImage();
    }

    public void explosion() {
        loadImage("explosion.png");
        setObjectState(true);
    }

    public Rectangle getBoundary() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public boolean collisionWith(GameObjectsManager object) {
        return this.getBoundary().intersects(object.getBoundary());
    }

    public void draw(Graphics graphics, Game board) {
        graphics.drawImage(this.image, this.x, this.y, this.width, this.height, board);
    }

    public void dead() {
        visible = false;
    }

    public boolean getVisibility() {
        return visible;
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    public boolean getObjectState() {
        return ObjectState;
    }

    public void setObjectState(boolean state) {
        this.ObjectState = state;
    }

    //getters
    public int getX() {
        return x;
    }

    //Create getters and setters methods
    //setters
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
