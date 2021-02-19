package Sprites;

import Game.GameObjectsManager;
import Game.ID;
import Game.Shooterino;

import java.util.Random;

public class Enemy extends GameObjectsManager implements Shooterino {
    public Bullets.Bombs bomb;
    public boolean almostDied;     //for proper projection of explosion
    private final int ENEMY_WIDTH = 38;
    private final int ENEMY_HEIGHT = 38;
    private final Random rand;

    //Constructor
    public Enemy(int height, int width, int x, int y, ID id) {
        super(height, width, x, y, id);
        rand = new Random();
        loadImage("enemy.png");
        velX = 1;
        almostDied = false;
        bomb = new Bullets.Bombs(24, 8, 0, 0, id);
        bomb.dead();
    }

    public Bullets.Bombs getBomb() {
        return bomb;
    }

    public void shootMyself() {
        int random = rand.nextInt() % 400;
        if (random == 1 && !this.bomb.getVisibility() && this.getVisibility()) {
            this.bomb.setX(this.x + (ENEMY_WIDTH / 2));
            this.bomb.setY(this.y + ENEMY_HEIGHT);
            this.bomb.setVisibility(true);
        }
    }

    public void setAlmostDied(boolean almostDied) {
        this.almostDied = almostDied;
    }

    public void move() {
        x += velX;
        y += velY;
    }

}
