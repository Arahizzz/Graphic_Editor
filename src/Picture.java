import javax.swing.*;
import java.awt.*;

public class Picture extends JPanel {
    private Dimension picSize;

    public Picture(Dimension picSize) {
        this.picSize = picSize;
        setBackground(Color.decode("A8BFBA"));
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
        Graphics2D g2d = (Graphics2D) g;

    }
}
