package Game;

import Sprites.Bullets.Missiles;

import javax.swing.*;
import java.awt.*;

public abstract class GameObjectsManager {


    //x and y coordinates, only be accessed by the object that inherit the game object
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

    //Abstract function for Player and Enemy to implement their different movement
    public abstract void move();

    //Function to load image for object
    public void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        this.image = ii.getImage();
    }
    
    //Function to display explosion when collided with bullet
    public void explosion() {
        loadImage("explosion.png");
        setObjectState(true);
    }

    //Return object boundary
    public Rectangle getBoundary() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    //Function to check for collision with another object
    public boolean collisionWith(GameObjectsManager object) {
        return this.getBoundary().intersects(object.getBoundary());
    }

    public void draw(Graphics graphics, Game board) {
        graphics.drawImage(this.image, this.x, this.y, this.width, this.height, board);
    }

    //Function to set object as dead
    public void dead() {
        visible = false;
    }

    //Getter for object visibility
    public boolean getVisibility() {
        return visible;
    }

    //Setter for visible
    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    //Getter for ObjectState, true after displaying explosion
    public boolean getObjectState() {
        return ObjectState;
    }

    //Setter for ObjectState
    public void setObjectState(boolean state) {
        this.ObjectState = state;
    }

    //Getters and setters for x and y coordinates
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    
    //Getters and setters for ID, and speed in x and y coordinates
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

    
    //Getters and setters for height and width
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
