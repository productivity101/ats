package Game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class MouseInputForGameOver implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        // Shape playButton = new Rectangle((BOARD_WIDTH-10)/2-100, BOARD_HEIGHT/2+80, 60,60);
        // Retry Menu
        if (mx >= Game.WIDTH / 2 + 300 && mx <= Game.WIDTH / 2 + 500)
        {
            if (my >= 200 && my <= 250)
            {
                //Pressed Play Button
                Game.State = Game.STATE.GAME;
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
