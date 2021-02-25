package Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        /*
        public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 300, 200, 200, 50);
        public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 300, 300, 200, 50);
        public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 300, 400, 200, 50);
        */

        // Play Button in Menu
        if (mx >= Game.WIDTH / 2 + 300 && mx <= Game.WIDTH / 2 + 500) {
            if (my >= 200 && my <= 250) {
                //Pressed Play Button
                Game.State = Game.STATE.GAME;
            }
        }

        // Help Button in Menu
        if (mx >= Game.WIDTH / 2 + 300 && mx <= Game.WIDTH / 2 + 500) {
            if (my >= 300 && my <= 350) {
                //Pressed Help Button
                Game.State = Game.STATE.GAME;
            }
        }

        // Quit Button in Menu
        if (mx >= Game.WIDTH / 2 + 300 && mx <= Game.WIDTH / 2 + 500 && Game.State == Game.STATE.MENU) {
            if (my >= 400 && my <= 450) {
                //Pressed Quit Button
                Game.State = Game.STATE.QUIT;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
