import javax.swing.*;
import java.awt.Graphics;

/**
 * Created by Ishraq on 5/31/2016.
 */
public class Renderer extends JPanel {
    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FlappyBird.flappyBird.repaint(g);
    }
}
