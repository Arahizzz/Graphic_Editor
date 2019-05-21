import javax.swing.*;
import java.awt.*;

public class Picture extends JPanel {
    private Dimension picSize;

    public Picture(Dimension picSize) {
        this.picSize = picSize;
    }

    public Picture(int width, int height) {
        this(new Dimension(width, height));
    }

    @Override
    public Dimension getPreferredSize() {
        return picSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
    }
}
