package Game;

//Imports
import Sprites.Enemy;
import Sprites.EnemyWaves;
import Sprites.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static Game.repeated.*;

// JPanel Container for the game
public class Game extends JPanel {
    public static final int DELAY = 18; //Delay the game thead
    public static final Color VERY_DARK_GREEN = new Color(0, 102, 0); // Green border for the game
    public static STATE State = STATE.MENU; //Menu state
    Image img = Toolkit.getDefaultToolkit().getImage("background.jpg"); //Main background of game level1
    Image img2 = Toolkit.getDefaultToolkit().getImage("level2.jpg"); //Main background of  game level2
    Image img3 = Toolkit.getDefaultToolkit().getImage("level3.jpg"); //Main background of  game level3
    Image img4 = Toolkit.getDefaultToolkit().getImage("level4.jpg"); //Main background of  game level4
    Image img5 = Toolkit.getDefaultToolkit().getImage("level5.jpg"); //Main background of  game level5
    Image img6 = Toolkit.getDefaultToolkit().getImage("level6.jpg"); //Main background of  game level6
    Image retry = Toolkit.getDefaultToolkit().getImage("retry_icon.png"); //Retry
    Image cross = Toolkit.getDefaultToolkit().getImage("cross_icon.png"); // Cross
    private final Menu menu;
    private Player player; //Player object for player 1
    private Player player2; //Player object for player 2
    private EnemyWaves enemyWave; //Enemywave object for enemy
    private final int START_X = 500; //Starting X axis of Players
    private final int START_Y = 560; //Starting Y axis of Players
    private final int PLAYER_WIDTH = 46; //Width of Player
    private final int PLAYER_HEIGHT = 32; //Height of Player
    private int SCORE = 0; //Initialized score to zero
    private boolean inGame; //Track game object status
    private Integer lives, lives2; //Track player lives
    private Integer level; //Game Level
    private boolean gameOver; //Check game status
    private String message;   //message for the end of a game
    protected JButton enter; // Jbutton

    Game() {
        //If game is started initialize these variables
        inGame = true;
        lives = 3;
        lives2 = 3;
        level = 2;
        // Initialise the game objects
        player = new Player(PLAYER_HEIGHT, PLAYER_WIDTH, START_X, START_Y, ID.Player);//Play1 set position
        player2 = new Player(PLAYER_HEIGHT, PLAYER_WIDTH, START_X - 180, START_Y, ID.Player2); //Play2 set position
        enemyWave = new EnemyWaves(level); //enemy
        menu = new Menu();


        addKeyListener(new KAdapter());     //for Key events
        addMouseListener(new MouseInput()); // for Mouse event
        setFocusable(true);
        setBackground(new Color(168, 219, 127, 116)); //Set the color of background at start up menu


    }

    // Get level
    public Integer getLevel() {
        return this.level;
    }
    // Start the thread
    @Override
    public void addNotify() {
        super.addNotify();
        Thread animator = new Thread(this::run);
        animator.start();
    }

    public void run() {
        // Game Rendering
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        //Loop while under level 6 and not game over yet
        while (level < 7 && !gameOver) {
        	//Loop for each level
            while (inGame) {
                repaint();
                //If in GAME state, run animation cycle
                if (State == STATE.GAME) {
                    animationCycle();       //mechanics of a game
                }
                timeDiff = System.currentTimeMillis() - beforeTime;
                sleep = DELAY - timeDiff;

                if (sleep < 0) {
                    sleep = 2;
                }
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                beforeTime = System.currentTimeMillis();
            }
            //Game
            gameOver();
            //Setup for next level
            player = new Player(PLAYER_HEIGHT, PLAYER_WIDTH, START_X, START_Y, ID.Player);
            player2 = new Player(PLAYER_HEIGHT, PLAYER_WIDTH, START_X - 180, START_Y, ID.Player2);
            enemyWave = new EnemyWaves(level);
            inGame = true;
        }
        gameOver();
    }

    /*
     * Setup the GUI for the game
     */
    @Override
    public void paintComponent(Graphics g) {

        if (State == STATE.GAME) {
            super.paintComponent(g);
            if (getLevel() ==1) g.drawImage(img, 0, 0, null); // background image
            if (getLevel()==2 ) g.drawImage(img2, 0, 0, null); // background image
            if (getLevel()==3 ) g.drawImage(img3, 0, 0, null); // background image
            if (getLevel()==4 ) g.drawImage(img4, 0, 0, null); // background image
            if (getLevel()==5 ) g.drawImage(img5, 0, 0, null); // background image
            if (getLevel()==6 ) g.drawImage(img6, 0, 0, null); // background image
            Font font = new Font("Roboto", Font.PLAIN, 18);
            g.setColor(Color.white);
            g.setFont(font); //set font
            //set starting position of player
            g.drawString("Player 1 Lives: " + lives.toString(), BOARD_WIDTH - 220, 25);
            g.drawString("Player 2 Lives: " + lives2.toString(), BOARD_WIDTH - 220, 55);
            //Live score board
            g.drawString("Enemies Left: " + enemyWave.getNumberOfEnemies().toString(), 28, 25);
            g.drawString("LEVEL: " + level, BOARD_WIDTH / 2 - 50, 25);
            g.drawString("Score: " + SCORE, BOARD_WIDTH / 2 - 48, 55);

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

        } else if (State == STATE.MENU) {
            menu.render(g);
            super.paintComponent(g);
            g.setColor(Color.green); // background image
        } else if (State == STATE.PAUSE) {
            // Need to change the pause menu
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null); // background image

            Font font = new Font("Roboto", Font.PLAIN, 18);
            g.setColor(Color.white);
            g.setFont(font);

            //To display player lives, number of enemies remaining, level, and score
            g.drawString("Player 1 Lives: " + lives.toString(), BOARD_WIDTH - 220, 25);
            g.drawString("Player 2 Lives: " + lives2.toString(), BOARD_WIDTH - 220, 55);
            g.drawString("Enemies Left: " + enemyWave.getNumberOfEnemies().toString(), 28, 25);
            g.drawString("LEVEL: " + level, BOARD_WIDTH / 2 - 50, 25);
            g.drawString("Score: " + SCORE, BOARD_WIDTH / 2 - 48, 55);
            font = new Font("Roboto", Font.PLAIN, 32);
            g.setFont(font);
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
            g.setColor(Color.white);
            g.drawString("Pause ", BOARD_WIDTH / 2 - 50, 150);

        } else if (State == STATE.QUIT) {
            System.exit(0);
        }


    }

    //Function for game mechanics
    private void animationCycle() {
    	//If no enemies left
        if (enemyWave.getNumberOfEnemies() <= 0) {
        	//Set level to be over and increment level
            inGame = false;
            level += 1;
            message = "Congratulations. You Won!";
        }

        //If player was shot by enemy bomb
        if (player.getObjectState()) {
        	//Decrement lives
            lives--;
            //Revive if there are lives remaining
            if (lives != 0) player.revive();
            else {
            	//Set gameOver if no lives remaining
                inGame = false;
                gameOver = true;
                message = "Game Over!";
            }
        } else if (player2.getObjectState()) {
            //Decrement lives for player2
            lives2--;
            if (lives2 != 0) player2.revive();
            else {
                inGame = false;
                gameOver = true;
                message = "Game Over!";
            }
        }

        //Set gameOver if enemy reaches the ground
        if (enemyWave.reachedTheGround()) {
            inGame = false;
            gameOver = true;
            message = "Game Over!";
        }
        //Movement for level 1
        if (getLevel() == 1) {
            player.move();
            player.missileMove();
            player2.move();
            player2.missileMove();
        }
        //Movement for levels after 1, player has increased speed at certain stages
        if (getLevel() > 1) {
            player.move(getLevel());
            player.missileMove();
            player2.move(getLevel());
            player2.missileMove();
        }

        //Update enemy and check for collisions
        enemyWaveMove();
        collisionMissileEnemies();
        collisionBombPlayer();
    }

    //Mechanics for enemy, moving, shooting, when they hit the wall etc.
    private void enemyWaveMove() {
        enemyWave.fixStatus();
        enemyWave.bombMove();
        enemyWave.shooting();
        enemyWave.accelerateIfNeeded(level);
        enemyWave.turnAroundIfHitTheWall();
    }

    //Function to check if player missiles collide with enemy
    private void collisionMissileEnemies() {
    	//If player shot a missile
        if (player.getMissile().getVisibility()) {
        	//Check if collision for each enemy in enemyWave
            for (Enemy enemy : enemyWave.getEnemies())
            	//If missile hit enemy
                if (enemy.getVisibility() && player.getMissile().collisionWith(enemy)) {
                	//Make enemy explode, set missile visible to false, increment score
                    enemy.explosion();
                    player.getMissile().dead();
                    SCORE++;
                }
        }
        //If player2 shot a missile
        if (player2.getMissile().getVisibility()) {
        	//Check if collision for each enemy in enemyWave
            for (Enemy enemy : enemyWave.getEnemies())
            	//If missile hit enemy
                if (enemy.getVisibility() && player2.getMissile().collisionWith(enemy)) {
                	//Make enemy explode, set missile visible to false, increment score
                    enemy.explosion();
                    player2.getMissile().dead();
                    SCORE++;
                }
        }
    }

    //Function to check if enemy bomb collide with player
    private void collisionBombPlayer() {
    	//Loop for each enemy in enemyWave
        for (Enemy enemy : enemyWave.getEnemies()) {
        	//If bomb hits player
            if (enemy.getBomb().getVisibility() && enemy.getBomb().collisionWith(player)) {
            	//Make player explode, set bomb visible to false
                player.explosion();
                enemy.getBomb().dead();
            } else if (enemy.getBomb().getVisibility() && enemy.getBomb().collisionWith(player2)) {
            	//Make player2 explode, set bomb visible to false
                player2.explosion();
                enemy.getBomb().dead();
            }
        }
    }

    //Function for displaying game over screen, shows final score
    private void gameOver (){
        addMouseListener(new MouseInput()); // for Mouse event
        Shape replayButton = new Rectangle((BOARD_WIDTH - 10) / 2 - 100, BOARD_HEIGHT / 2 + 80, 60, 60);
        Shape quitButton = new Rectangle((BOARD_WIDTH - 10) / 2 + 80, BOARD_HEIGHT / 2 + 80, 60, 60);
        Graphics g = this.getGraphics();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //Background image for different level
        if (getLevel() ==1) g.drawImage(img, 0, 0, null); // background image
        if (getLevel()==2 ) g.drawImage(img2, 0, 0, null); // background image
        if (getLevel()==3 ) g.drawImage(img3, 0, 0, null); // background image
        if (getLevel()==4 ) g.drawImage(img4, 0, 0, null); // background image
        if (getLevel()==5 ) g.drawImage(img5, 0, 0, null); // background image
        if (getLevel()==6 ) g.drawImage(img6, 0, 0, null); // background image
        Font font = new Font("Roboto", Font.BOLD, 30);
        FontMetrics ft = this.getFontMetrics(font);
        enter = new JButton("Quit");
        enter.setBounds(145, 283, 135, 25);
        add(enter);
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message, (BOARD_WIDTH - ft.stringWidth(message)) / 2, BOARD_HEIGHT / 2);
        g.drawString("Final Score: " + SCORE, (BOARD_WIDTH - 10) / 2 - 100, BOARD_HEIGHT / 2 + 50);
        g.drawImage(retry, (BOARD_WIDTH - 10) / 2 - 100, BOARD_HEIGHT / 2 + 80, null);
        g.drawImage(cross, (BOARD_WIDTH - 10) / 2 + 70, BOARD_HEIGHT / 2 + 70, null);
        g2d.draw(replayButton);
        g2d.draw(quitButton);


    }

//    public void giveactionListener(ActionListener a) {
//        enter.addActionListener(a);
//    }



//enum for different states of the game
    public enum STATE {
        MENU,
        GAME,
        PAUSE,
        QUIT
    }

    //To listen for keyboard input
    private class KAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
        	//If game is currently active
            if (State == STATE.GAME) {
            	//Check for keys entered to move players or shoot missiles
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_A) player.setVelX(-player.getSpeed());
                if (key == KeyEvent.VK_D) player.setVelX(player.getSpeed());
                if (key == KeyEvent.VK_W && !player.getMissile().getVisibility()) {
                    player.shootMyself();
                }
                
                //For player2
                if (key == KeyEvent.VK_LEFT) player2.setVelX(-player2.getSpeed());
                if (key == KeyEvent.VK_RIGHT) player2.setVelX(player2.getSpeed());
                if (key == KeyEvent.VK_UP && !player2.getMissile().getVisibility()) {
                    player2.shootMyself();
                }
                
                //If "P" key pressed, pause the game
                if (key == KeyEvent.VK_P) {
                    State = STATE.PAUSE;
                }
            //If game is paused
            } else if (State == STATE.PAUSE) {
                int key = e.getKeyCode();
                //Resume game if "P" key is pressed again
                if (key == KeyEvent.VK_P) {
                    State = STATE.GAME;
                }
            } else if (State == STATE.MENU) {
            	//Do nothing in menu
            }
        }

        //Check for when a key is released
        public void keyReleased(KeyEvent e) {
        	//If game is active
            if (State == STATE.GAME) {
                int key = e.getKeyCode();
                //Stop players from moving in a certain direction if key released
                if (key == KeyEvent.VK_A) player.setVelX(0);
                if (key == KeyEvent.VK_D) player.setVelX(0);
                if (key == KeyEvent.VK_LEFT) player2.setVelX(0);
                if (key == KeyEvent.VK_RIGHT) player2.setVelX(0);
            } else if (State == STATE.MENU) {
            	//Do nothing in menu
            }
        }


    }


}
