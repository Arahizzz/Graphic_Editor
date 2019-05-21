import javax.swing.*;
import java.awt.*;

public class PickedColor extends JButton {
    private Color color;

    public PickedColor(Color color, String text) {
        super();
        this.color = color;
        this.setPreferredSize(new Dimension(80, 100));
        setText(text);
        setVerticalTextPosition(BOTTOM);
        setVerticalAlignment(BOTTOM);
        setHorizontalTextPosition(CENTER);
        setHorizontalAlignment(CENTER);
        setContentAreaFilled(false);
        setFocusPainted(false);
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
        g.setColor(color);
        g.fillRect(10, 10, 60, 60);
    }
}