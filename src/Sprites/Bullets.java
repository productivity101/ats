package Sprites;

import Game.GameObjectsManager;
import Game.ID;

public class Bullets extends GameObjectsManager {


    public Bullets(int height, int width, int x, int y, ID id) {
        super(height, width, x, y, id);
    }

    public void move() {
        if (y <= 0)
            this.dead();
        x += velX;
        y += velY;
    }

    public static class Missiles extends Bullets {
        private final int MISSILE_SPEED = 7;
        private final int MISSILE_WIDTH = 2;
        private final int MISSILE_HEIGHT = 8;

        Missiles(int height, int width, int x, int y, ID id) {
            super(height, width, x, y, id);
            loadImage("missile.png");
            width = MISSILE_WIDTH;
            height = MISSILE_HEIGHT;
            velY = -MISSILE_SPEED;
        }

    }

    public static class Bombs extends Bullets {
        private final int BOMB_SPEED = 3;
        private final int BOMB_WIDTH = 8;
        private final int BOMB_HEIGHT = 24;

        Bombs(int height, int width, int x, int y, ID id) {
            super(height, width, x, y, id);
            loadImage("banana.png");
            width = BOMB_WIDTH;
            height = BOMB_HEIGHT;
            velY = BOMB_SPEED;
        }

    }

}
