package Sprites;

import Game.GameObjectsManager;
import Game.ID;
import Game.Shooterino;

import static Game.repeated.BOARD_WIDTH;

public class Player extends GameObjectsManager implements Shooterino {
    private static final int PLAYER_WIDTH = 46;
    private final int PLAYER_SPEED = 2;

    private final Bullets missile;

    //Constructor
    public Player(int height, int width, int x, int y, ID id) {
    	//Call parent constructor
        super(height, width, x, y, id);
        //Set images for both players based on ID
        if (id == ID.Player) loadImage("player.png");
        else loadImage("player2.png");
        //Instantiate missile for player
        missile = new Bullets.Missiles(8, 2, x, y, ID.Missles);
        missile.dead();
    }

    //Getter for PLAYER_SPEED
    public int getSpeed() {
        return PLAYER_SPEED;
    }

    //Getter for missile
    public Bullets getMissile() {
        return missile;
    }

    //Function to revive players and spawn them
    public void revive() {
        if (id == ID.Player) loadImage("player.png");
        else loadImage("player2.png");
        setObjectState(false);
        if (id == ID.Player) x = BOARD_WIDTH / 2 - 180;
        else x = BOARD_WIDTH / 2;
    }

    //Function to move player's missile
    public void missileMove() {
        if (missile.getVisibility()) {
            missile.move();
        }
    }

    //Function to shoot missile
    public void shootMyself() {
        missile.setX(x + 18);
        missile.setY(y);
        missile.setVisibility(true);
    }

    //Function to move the player sprite
    public void move() {
        if (x > BOARD_WIDTH - PLAYER_WIDTH)
            x = BOARD_WIDTH - PLAYER_WIDTH;
        else if (x < 0)
            x = 0;
        else
            x += velX;
        y += velY;
    }

    //Overloaded function to increase player speed as they progress to other levels
    public void move(Integer spid) {
        if (x > BOARD_WIDTH - PLAYER_WIDTH)
            x = BOARD_WIDTH - PLAYER_WIDTH;
        else if (x < 0) {
            x = 0;
        } else {
//            x += velX + (spid/2);
//            y += velY + (spid/2);
            x += velX * (spid / 2);
            y += velY * (spid / 2);
        }
    }

    // when level = 3, increment the speed by overloading the move function

}
