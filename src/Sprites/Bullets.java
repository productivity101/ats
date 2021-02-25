package Sprites;

import Game.GameObjectsManager;
import Game.ID;

public class Bullets extends GameObjectsManager {

	//Constructor
    public Bullets(int height, int width, int x, int y, ID id) {
        super(height, width, x, y, id);
    }

    //Move bullet
    public void move() {
    	//If bullet is out of screen, set visible to false
        if (y <= 0)
            this.dead();
        x += velX;
        y += velY;
    }

    //Bullets subclass for Player
    public static class Missiles extends Bullets {
    	//Set attributes for missiles
        private final int MISSILE_SPEED = 7;
        private final int MISSILE_WIDTH = 2;
        private final int MISSILE_HEIGHT = 8;

        Missiles(int height, int width, int x, int y, ID id) {
        	//Call parent constructor
            super(height, width, x, y, id);
            //Load image for missiles and set speed, x and y coordinates
            loadImage("missile.png");
            width = MISSILE_WIDTH;
            height = MISSILE_HEIGHT;
            velY = -MISSILE_SPEED;
        }

    }

    //Bombs subclass for Enemy
    public static class Bombs extends Bullets {
    	//Set attributes for bomb
        private final int BOMB_SPEED = 3;
        private final int BOMB_WIDTH = 8;
        private final int BOMB_HEIGHT = 24;

        Bombs(int height, int width, int x, int y, ID id) {
        	//Call parent constructor
            super(height, width, x, y, id);
            //Load image for bomb and set speed, x and y coordinates
            loadImage("banana.png");
            width = BOMB_WIDTH;
            height = BOMB_HEIGHT;
            velY = BOMB_SPEED;
        }

    }

}
