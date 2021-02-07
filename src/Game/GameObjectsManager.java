package Game;

import javax.swing.*;
import java.awt.*;

public abstract class GameObjectsManager {


    //only be accessed by the object that inherit the game object
    protected int x,y;
    //Create id to know that Game Object (enum)
    protected ID id;
    //Create variable to control speed in X and Y direction
    protected int velX, velY;
    private Image image;
    protected int width;
    protected int height;
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

    //Create getters and setters methods
    //setters
    public void setX(int x){
        this.x =x;
    }
    public void setY(int y){
        this.y =y;
    }
    public void setId(ID id){
        this.id =id;
    }
    public void setVelX(int velX){
        this.velX = velX;
    }
    public void setVelY(int velY){
        this.velY = velY;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public void setWidth(int width){
        this.width = width;
    }
    //getters
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public ID getId(){
        return id;
    }
    public int getVelX(){
        return velX;
    }
    public int getVelY(){
        return velY;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }




    public void move() {
        x += velX;
        y += velY;
    }

}
