package Sprites;


import Game.Game;
import Game.ID;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Game.repeated.BOARD_WIDTH;

public class EnemyWaves {

	//List to store Enemy instances
    private final List<Enemy> enemies;
    private int enemySpeed;

    private final int ENEMY_WIDTH = 38;
    private int ENEMY_HEIGHT = 38;
    private final int ENEMY_X = 0;
    private final int ENEMY_Y = 30;
    private final int level = 0;


    public EnemyWaves(int level) {
        enemies = new ArrayList<>();
        //Loops for each row, row number increases with level
        for (int i = 0; i < level; i++) {
        	//Add enemy for each column, column number also increases with each level
            for (int j = 0; j < level * 3; j++) {
                enemies.add(new Enemy(ENEMY_WIDTH, ENEMY_HEIGHT = 38, ENEMY_X + 32 * j, ENEMY_Y + 32 * i, ID.Enemy));
            }
        }
        enemySpeed = 1;
    }

    //Getter for list of enemies
    public List<Enemy> getEnemies() {
        return enemies;
    }

    //Returns number of active enemies
    public Integer getNumberOfEnemies() {
        int count = 0;
        for (Enemy e : enemies) {
            if (e.getVisibility()) count++;
        }
        return count;
    }

    public void draw(Graphics g, Game Game) {
        for (Enemy enemy : enemies) {
            if (enemy.getVisibility())
                enemy.draw(g, Game);
            //Draw bombs if enemy made a shot
            if (enemy.getBomb().getVisibility())
                enemy.getBomb().draw(g, Game);
        }
    }

    //Function to check if enemy reached the ground
    public boolean reachedTheGround() {
        for (Enemy enemy : enemies) {
            if (enemy.getVisibility() && enemy.getY() + enemy.getHeight() > 500) {
                return true;
            }
        }
        return false;
    }

    //Change status of enemy
    public void fixStatus() {
        for (Enemy enemy : enemies) {
        	//ObjectState is true after explosion is displayed
            if (enemy.getObjectState()) {
            	//Set enemy to almost dead
                enemy.setAlmostDied(true);
                enemy.setObjectState(false);
            } else if (enemy.almostDied) {
                enemy.dead();
                enemy.setAlmostDied(false);
            } else if (enemy.getVisibility())
                enemy.move();
        }
    }

    //Move the enemy's bomb
    public void bombMove() {
        for (Enemy enemy : enemies) {
            if (enemy.bomb.getVisibility()) {
                enemy.bomb.move();
            }
        }
    }

    //Loop through each enemy in list and call the shoot function
    public void shooting() {
        for (Enemy enemy : enemies) {
            enemy.shootMyself();
        }
    }

    //Function to increase speed of enemy if certain number of enemy are dead
    public void accelerateIfNeeded(int level) {
        boolean b = false;

        //If half the enemies are killed, increase speed to 2
        if (getNumberOfEnemies() == (level * 2 * level * 2) / 2) {
            enemySpeed = 2;
            b = true;
        }
        
        //If 1/4 of the enemies are killed, increase speed to 3
        if (getNumberOfEnemies() == (level * 2 * level * 2) / 4) {
            enemySpeed = 3;
            b = true;
        }

        //Loop through list of enemy and update their speed
        if (b) {
            for (Enemy enemy : enemies) {
                if (enemy.getVelX() > 0) enemy.setVelX(enemySpeed);
                else enemy.setVelX(-enemySpeed);
            }
        }
    }

    //Function to check if enemy has hit the border of the game
    public void turnAroundIfHitTheWall() {
        for (Enemy enemy : enemies) {
            if (enemy.getX() > BOARD_WIDTH - ENEMY_WIDTH) {
            	//Set enemy to move left
                for (Enemy enemyReversed : enemies) {
                    enemyReversed.setVelX(-enemySpeed);
                    enemyReversed.setY(enemyReversed.getY() + 15);
                }
                return;
            }

            if (enemy.getX() < 0) {
            	//Set enemy to move right
                for (Enemy enemyReversed : enemies) {
                    enemyReversed.setVelX(enemySpeed);
                    enemyReversed.setY(enemyReversed.getY() + 15 + 15);
                }
                return;
            }
        }
    }
}