package Game;

import javax.swing.*;


public class run extends JFrame{

    public run() {
        initUI();
    }

    private void initUI() {
        this.add(new Game());

         int BOARD_HEIGHT=630;
          int BOARD_WIDTH=800;

        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setResizable(false);

        setTitle("MonkeyInvaders");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new run();
    }
}
