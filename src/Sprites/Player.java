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
        super(height, width, x, y, id);
        if (id == ID.Player) loadImage("player.png");
        else loadImage("player2.png");
        missile = new Bullets.Missiles(8, 2, x, y, ID.Missles);
        missile.dead();
    }

    public int getSpeed() {
        return PLAYER_SPEED;
    }

    public Bullets getMissile() {
        return missile;
    }

    public void revive() {
        if (id == ID.Player) loadImage("player.png");
        else loadImage("player2.png");
        setObjectState(false);
        if (id == ID.Player) x = BOARD_WIDTH / 2 - 180;
        else x = BOARD_WIDTH / 2;
    }

    public void missileMove() {
        if (missile.getVisibility()) {
            missile.move();
        }
    }

    public void shootMyself() {
        missile.setX(x + 18);
        missile.setY(y);
        missile.setVisibility(true);
    }

    public void move() {
        if (x > BOARD_WIDTH - PLAYER_WIDTH)
            x = BOARD_WIDTH - PLAYER_WIDTH;
        else if (x < 0)
            x = 0;
        else
            x += velX;
        y += velY;
    }

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
