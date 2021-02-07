package Sprites;


import Game.Game;
import Game.ID;
import Sprites.Enemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static Game.repeated.*;

public class EnemyWaves {

    private List<Enemy> enemies;
    private Integer numberOfEnemies;
    private int enemySpeed;

    private int ENEMY_WIDTH=38;
    private int ENEMY_HEIGHT=38;
    private int ENEMY_X=0;
    private int ENEMY_Y=30;


    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Integer getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public void decreaseNumberOfEnemies() {
        numberOfEnemies--;
    }

    public EnemyWaves(int level) {
        enemies = new ArrayList<>();
        for(int i=0; i<level*2; i++) {
            for (int j = 0; j < level*2; j++) {
                enemies.add(new Enemy(ENEMY_WIDTH, ENEMY_HEIGHT=38, ENEMY_X + 32 * j, ENEMY_Y + 32 * i, ID.Enemy));
            }
        }
        numberOfEnemies=level*2*level*2;
        enemySpeed=1;
    }

    public void draw(Graphics g, Game Game) {
        for (Enemy enemy : enemies) {
            if (enemy.getVisibility())
                enemy.draw(g,Game);
            if (enemy.getBomb().getVisibility())
                enemy.getBomb().draw(g, Game);
        }
    }

    public boolean reachedTheGround() {
        for(Enemy enemy: enemies) {
            if (enemy.getVisibility() && enemy.getY() + enemy.getHeight() > 500) {
                return true;
            }
        }
        return false;
    }

    public void fixStatus() {
        for(Enemy enemy : enemies) {
            if(enemy.getObjectState()) {
                enemy.setAlmostDied(true);
                enemy.setObjectState(false);
            }
            else if(enemy.almostDied) {
                enemy.dead();
                enemy.setAlmostDied(false);
            }
            else if(enemy.getVisibility())
                enemy.move();
        }
    }

    public void bombMove() {
        for(Enemy enemy: enemies) {
            if(enemy.bomb.getVisibility()) {
                enemy.bomb.move();
            }
        }
    }

    public void shooting() {
        for(Enemy enemy: enemies) {
            enemy.tryToShoot();
        }
    }

    public void accelerateIfNeeded(int level) {
        boolean b=false;

        if(numberOfEnemies==(level*2*level*2)/2) {
            enemySpeed = 2;
            b = true;
        }

        if(numberOfEnemies==(level*2*level*2)/4) {
            enemySpeed = 3;
            b = true;
        }

        if(b) {
            for (Enemy enemy : enemies) {
                if (enemy.getVelX() > 0) enemy.setVelX(enemySpeed);
                else enemy.setVelX(-enemySpeed);
            }
        }
    }

    public void turnAroundIfHitTheWall() {
        for(Enemy enemy: enemies) {
            if(enemy.getX()>BOARD_WIDTH-ENEMY_WIDTH) {
                for(Enemy enemyReversed : enemies) {
                    enemyReversed.setVelX(-enemySpeed);
                    enemyReversed.setY(enemyReversed.getY()+15);
                }
                return;
            }

            if(enemy.getX()<0) {
                for(Enemy enemyReversed : enemies) {
                    enemyReversed.setVelX(enemySpeed);
                    enemyReversed.setY(enemyReversed.getY()+15+15);
                }
                return;
            }
        }
    }
}