package Game;



import Sprites.Enemy;
import Sprites.EnemyWaves;
import Sprites.Player;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Game.repeated.*;


public class Game extends JPanel {
    public static final int DELAY = 18;
    Image img = Toolkit.getDefaultToolkit().getImage("background.jpg");
    private Menu menu;
    private Player player;
    private Player player2;
    private EnemyWaves enemyWave;
    public static final Color VERY_DARK_GREEN = new Color(0,102,0);

    private int START_X=500;
    private int START_Y=560;
    private int PLAYER_WIDTH=46;
    private int PLAYER_HEIGHT=32;
    private int SCORE = 0;

    private boolean inGame;
    private Integer lives, lives2;

    private Integer level;
    private boolean gameOver;

    private String message;     //message for the end of a game

    public static enum STATE{
        MENU,
        GAME,
        PAUSE
    };

    public static STATE State = STATE.MENU;


    Game() {

            inGame = true;
            lives = 3;
            lives2 = 3;

            level = 1;

            player = new Player(PLAYER_HEIGHT, PLAYER_WIDTH, START_X, START_Y, ID.Player);
            player2 = new Player(PLAYER_HEIGHT, PLAYER_WIDTH, START_X - 180, START_Y, ID.Player2);
            enemyWave = new EnemyWaves(level);
            menu = new Menu();


            addKeyListener(new KAdapter());     //for Key events
            addMouseListener(new MouseInput()); // for Mouse event
            setFocusable(true);
            setBackground(new Color(168, 219, 127, 116));
        

    }


    @Override
    public void addNotify() {

            super.addNotify();

            Thread animator = new Thread(this::run);
            animator.start();


    }


    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        while(level < 4 && !gameOver) {
            while(inGame) {
                repaint();
                animationCycle();       //mechanics of a game

                timeDiff = System.currentTimeMillis() - beforeTime;
                sleep = DELAY - timeDiff;

                if(sleep<0) {
                    sleep = 2;
                }
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                beforeTime=System.currentTimeMillis();
            }
            gameOver();
            player=new Player(PLAYER_HEIGHT, PLAYER_WIDTH,START_X, START_Y,ID.Player);
            player2=new Player(PLAYER_HEIGHT, PLAYER_WIDTH,START_X-180, START_Y,ID.Player2);
            enemyWave = new EnemyWaves(level);

            
            inGame = true;
        }
        gameOver();
    }


    @Override
    public void paintComponent(Graphics g) {
        if (State == STATE.GAME) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null); // background image

            Font font = new Font("Roboto", Font.PLAIN, 18);
            g.setColor(Color.white);
            g.setFont(font);

            g.drawString("Player 1 Lives: " + lives.toString(), BOARD_WIDTH - 220, 25);
            g.drawString("Player 2 Lives: " + lives2.toString(), BOARD_WIDTH - 220, 55);
            g.drawString("Enemies Left: " + enemyWave.getNumberOfEnemies().toString(), 28, 25);
            g.drawString("Score: " + SCORE, BOARD_WIDTH/2-50, 25);

            g.setColor(VERY_DARK_GREEN);
            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);

            player.draw(g, this);
            if (player.getMissile().getVisibility())
                player.getMissile().draw(g, this);

            //create
            player2.draw(g, this);
            if (player2.getMissile().getVisibility())
                player2.getMissile().draw(g, this);

            enemyWave.draw(g, this);

        }else if(State == STATE.MENU){
            menu.render(g);
            super.paintComponent(g);
            g.setColor(Color.green); // background image
        }else if(State == STATE.PAUSE){

        }


    }

    private void animationCycle() {
        if(enemyWave.getNumberOfEnemies()<=0) {
            inGame=false;
            level += 1;
            message = "Congratulations. You Won!";
        }

        if(player.getObjectState()) {
            lives--;
            if(lives!=0) player.revive();
            else {
                inGame=false;
                gameOver = true;
                message = "Game Over!";
            }
        }else if (player2.getObjectState()){
            lives2--;
            if(lives2!=0) player2.revive();
            else {
                inGame=false;
                gameOver = true;
                message = "Game Over!";
            }
        }

        if(enemyWave.reachedTheGround()) {
            inGame=false;
            gameOver = true;
            message="Game Over!";
        }

        player.move();
        player.missileMove();
        player2.move();
        player2.missileMove();
        enemyWaveMove();
        collisionMissileEnemies();
        collisionBombPlayer();
    }


    private void enemyWaveMove() {
        enemyWave.fixStatus();
        enemyWave.bombMove();
        enemyWave.shooting();
        enemyWave.accelerateIfNeeded(level);
        enemyWave.turnAroundIfHitTheWall();
    }

    private void collisionMissileEnemies() {
        if(player.getMissile().getVisibility()) {
            for (Enemy enemy : enemyWave.getEnemies())
                if(enemy.getVisibility() && player.getMissile().collisionWith(enemy)) {
                    enemy.explosion();
                    player.getMissile().dead();
                    SCORE++;
                }
        }
        if(player2.getMissile().getVisibility()) {
            for (Enemy enemy : enemyWave.getEnemies())
                if(enemy.getVisibility() && player2.getMissile().collisionWith(enemy)) {
                    enemy.explosion();
                    player2.getMissile().dead();
                    SCORE++;
                }
        }
    }

    private void collisionBombPlayer() {
        for(Enemy enemy : enemyWave.getEnemies()) {
            if (enemy.getBomb().getVisibility() && enemy.getBomb().collisionWith(player)) {
                player.explosion();
                enemy.getBomb().dead();
            }else if (enemy.getBomb().getVisibility()&& enemy.getBomb().collisionWith(player2)) {
                player2.explosion();
                enemy.getBomb().dead();
            }
        }
    }


    private void gameOver() {
        Graphics g = this.getGraphics();
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null); // background image

        Font font = new Font("Roboto", Font.BOLD, 30);
        FontMetrics ft = this.getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message, (BOARD_WIDTH-ft.stringWidth(message))/2, BOARD_HEIGHT/2);
        g.drawString("Final Score: "+SCORE, (BOARD_WIDTH-10)/2-100, BOARD_HEIGHT/2+50);
    }

    private class KAdapter extends KeyAdapter {

    	public void keyPressed(KeyEvent e) {
            if (State == STATE.GAME) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_A) player.setVelX(-player.getSpeed());
                if (key == KeyEvent.VK_D) player.setVelX(player.getSpeed());
                if (key == KeyEvent.VK_W && !player.getMissile().getVisibility()) {
                    player.shoot();
                }
                if (key == KeyEvent.VK_LEFT) player2.setVelX(-player2.getSpeed());
                if (key == KeyEvent.VK_RIGHT) player2.setVelX(player2.getSpeed());
                if (key == KeyEvent.VK_UP && !player2.getMissile().getVisibility()) {
                    player2.shoot();
                }
            }
            else if(State == STATE.MENU){

            }
        }

    	public void keyReleased(KeyEvent e) {
            if (State == STATE.GAME) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_A) player.setVelX(0);
                if (key == KeyEvent.VK_D) player.setVelX(0);
                if (key == KeyEvent.VK_LEFT) player2.setVelX(0);
                if (key == KeyEvent.VK_RIGHT) player2.setVelX(0);
            }else if(State == STATE.MENU){

            }
        }



    }



}
