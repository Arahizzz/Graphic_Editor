import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedHashSet;

public class Picture extends JPanel {
    private Dimension picSize;
    LinkedHashSet<Shape> shapes = new LinkedHashSet<>();

    public Picture(Dimension picSize) {
        this.picSize = picSize;
    }

    public Picture(int width, int height) {
        this(new Dimension(width, height));
        eraser();
    }

    private void lineDrawer() {
        MouseAdapter adapter = new MouseAdapter() {
            int x0;
            int y0;
            Line2D line;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x0 = e.getX();
                y0 = e.getY();
                line = new Line2D.Double(x0, y0, e.getX(), e.getY());
                shapes.add(line);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseMoved(e);
                shapes.remove(line);
                line = new Line2D.Double(x0, y0, e.getX(), e.getY());
                shapes.add(line);
                repaint();
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private void eraser() {
        repaint();
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                Point2D.Double point = new Point2D.Double(e.getX(), e.getY());
                for (Shape shape : shapes) {
                    if (shape.contains(point)) {
                        shapes.remove(shape);
                        revalidate();
                        repaint();
                        break;
                    }
                }
            }
        };
        addMouseListener(adapter);
    }

    @Override
    public Dimension getPreferredSize() {
        return picSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Shape shape : shapes) {
            g2d.draw(shape);
        }
    }
}
