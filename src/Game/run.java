package Game;

import javax.swing.*;

import static Game.repeated.BOARD_HEIGHT;
import static Game.repeated.BOARD_WIDTH;

public class run extends JFrame {

    public run() {
        initUI();
    }

    public static void main(String[] args) {

        new run();
    }

    /*
     * UI for the game
     */
    private void initUI() {
        this.add(new Game());

        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setResizable(false);

        setTitle("MonkeyInvaders");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
