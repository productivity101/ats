package Game;

import java.awt.*;

import static Game.repeated.BOARD_WIDTH;

public class Menu {

    public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 300, 200, 200, 50);
    public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 300, 300, 200, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 300, 400, 200, 50);

    public void render(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        Font font = new Font("arial", Font.BOLD, 50);
        graphics.setFont(font);
        graphics.setColor(Color.white);
        graphics.drawString("MONKEY INVADERS", BOARD_WIDTH / 2 - 225, 100);


        Font fnt1 = new Font("arial", Font.BOLD, 30);
        graphics.setFont(fnt1);
        graphics.drawString("Play", playButton.x + 70, playButton.y + 35);
        g2d.draw(playButton);
        graphics.drawString("Help", helpButton.x + 70, helpButton.y + 35);
        g2d.draw(helpButton);
        graphics.drawString("Quit", quitButton.x + 70, quitButton.y + 35);
        g2d.draw(quitButton);


    }

}
