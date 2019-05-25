import javax.swing.*;
import java.awt.*;

public class PickedColor extends JComponent {
    private Color color;
    private String text;

    public PickedColor(Color color, String text) {
        super();
        this.color = color;
        this.setPreferredSize(new Dimension(80, 100));
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 22, getHeight() - 10);
        g2d.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
        g2d.setColor(color);
        g2d.fillRect(10, 10, 60, 60);
    }
}