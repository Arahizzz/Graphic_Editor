
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedHashSet;

public class Picture extends JPanel {
    private Dimension picSize;
    LinkedHashSet<Shape> shapes = new LinkedHashSet<>();
    MouseAdapter adapter = new MouseAdapter() {
    };

    public Picture(Dimension picSize) {
        this.picSize = picSize;
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    public Picture(int width, int height) {
        this(new Dimension(width, height));
    }

    public void lineDrawer() {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
        MouseAdapter adapter = new MouseAdapter() {
            int x0;
            int y0;
            Line2D line;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x0 = e.getX();
                y0 = e.getY();
                line = new Line2D.Double(x0, y0, x0, y0);
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
        this.adapter = adapter;
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void eraser() {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                Rectangle2D.Double area = new Rectangle2D.Double(e.getX() - 7, e.getY() - 7, 7, 7);
                Shape[] asArray = shapes.toArray(new Shape[0]);
                for (int i = asArray.length - 1; i >= 0; i--) {
                    if (asArray[i].intersects(area)) {
                        shapes.remove(asArray[i]);
                        repaint();
                        break;
                    }
                }
            }
        };
        this.adapter = adapter;
        addMouseListener(adapter);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void rectangle() {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
        MouseAdapter adapter = new MouseAdapter() {
            int x0;
            int y0;
            Rectangle2D rectangle;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x0 = e.getX();
                y0 = e.getY();
                rectangle = new Rectangle2D.Double(x0, y0, 0, 0);
                shapes.add(rectangle);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x1 = e.getX();
                int y1 = e.getY();
                super.mouseMoved(e);
                shapes.remove(rectangle);
                if (x0 <= x1 && y0 <= y1)
                    rectangle = new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0);
                else if (x0 > x1 && y0 > y1)
                    rectangle = new Rectangle2D.Double(x1, y1, x0 - x1, y0 - y1);
                else if (x0 > x1)
                    rectangle = new Rectangle2D.Double(x1, y0, x0 - x1, y1 - y0);
                else
                    rectangle = new Rectangle2D.Double(x0, y1, x1 - x0, y0 - y1);
                shapes.add(rectangle);
                repaint();
            }
        };
        this.adapter = adapter;
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void ellipse() {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
        MouseAdapter adapter = new MouseAdapter() {
            int x0;
            int y0;
            Ellipse2D ellipse;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x0 = e.getX();
                y0 = e.getY();
                ellipse = new Ellipse2D.Double(x0, y0, 0, 0);
                shapes.add(ellipse);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x1 = e.getX();
                int y1 = e.getY();
                super.mouseMoved(e);
                shapes.remove(ellipse);
                if (x0 <= x1 && y0 <= y1)
                    ellipse = new Ellipse2D.Double(x0, y0, x1 - x0, y1 - y0);
                else if (x0 > x1 && y0 > y1)
                    ellipse = new Ellipse2D.Double(x1, y1, x0 - x1, y0 - y1);
                else if (x0 > x1)
                    ellipse = new Ellipse2D.Double(x1, y0, x0 - x1, y1 - y0);
                else
                    ellipse = new Ellipse2D.Double(x0, y1, x1 - x0, y0 - y1);
                shapes.add(ellipse);
                repaint();
            }
        };
        this.adapter = adapter;
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void fill() {

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
