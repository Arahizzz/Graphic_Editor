import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Colors extends JPanel {
    private PickedColor pickedColor1;
    private PickedColor pickedColor2;

    public Colors(PickedColor pickedColor1, PickedColor pickedColor2) {
        this.pickedColor1 = pickedColor1;
        this.pickedColor2 = pickedColor2;
        setLayout(new GridLayout(3, 10));
        createButton(Color.BLACK);
        createButton(Color.white);
        createButton(Color.gray);
        createButton(Color.decode("#ABABAB"));
        createButton(Color.decode("#770013"));
        createButton(Color.decode("#8E5B36"));
        createButton(Color.red);
        createButton(Color.pink);
        createButton(Color.decode("#D68C0E"));
        createButton(Color.decode("#FFB71A"));
        createButton(Color.yellow);
        createButton(Color.decode("#FFE668"));
        createButton(Color.GREEN);
        createButton(Color.decode("#9CFF00"));
        createButton(Color.blue);
        createButton(Color.cyan);
        createButton(Color.decode("#5070FF"));
        createButton(Color.decode("#B913FF"));
    }

    private void createButton(Color color) {
        ColorButton button = new ColorButton(color);
        button.setPreferredSize(new Dimension(32, 32));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isLeftMouseButton(e))
                    pickedColor1.setColor(color);
                else
                    pickedColor2.setColor(color);
            }
        });
        add(button);
    }

    private class ColorButton extends JButton {
        private Color color;

        public ColorButton(Color color) {
            super();
            this.color = color;
        }

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        public void setColor(Color color) {
            this.color = color;
            repaint();
        }

        public Color getColor() {
            return color;
        }
    }
}
