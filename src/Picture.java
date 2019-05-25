import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

public class Picture extends JPanel {
    public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

    private PickedColor pickedColor1;
    private PickedColor pickedColor2;
    private Dimension picSize;
    private LinkedList<Wrapper> wrappers = new LinkedList<>();
    private MouseAdapter adapter;

    public Picture(Dimension picSize, PickedColor pickedColor1, PickedColor pickedColor2) {
        this.picSize = picSize;
        this.pickedColor1 = pickedColor1;
        this.pickedColor2 = pickedColor2;
        setBackground(Color.WHITE);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    public Picture(int width, int height, PickedColor pickedColor1, PickedColor pickedColor2) {
        this(new Dimension(width, height), pickedColor1, pickedColor2);
    }

    public void lineDrawer() {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
        MouseAdapter adapter = new MouseAdapter() {
            private int x0;
            private int y0;
            private Line2D line;
            private ShapeWrapper wrapper;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x0 = e.getX();
                y0 = e.getY();
                line = new Line2D.Double(x0, y0, x0, y0);
                wrapper = new ShapeWrapper(line, pickedColor1.getColor(), pickedColor2.getColor());
                wrappers.add(wrapper);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseMoved(e);
                wrappers.removeLast();
                line = new Line2D.Double(x0, y0, e.getX(), e.getY());
                wrapper = new ShapeWrapper(line, pickedColor1.getColor(), pickedColor2.getColor());
                wrappers.add(wrapper);
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
            private BasicStroke stroke = new BasicStroke(1);

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                Rectangle2D.Double area = new Rectangle2D.Double(e.getX() - 10, e.getY() - 10, 20, 20);
                Iterator<Wrapper> shapeIterator = wrappers.descendingIterator();
                Wrapper wrapper;
                Shape shape;
                while (shapeIterator.hasNext()) {
                    wrapper = shapeIterator.next();
                    shape = wrapper.getShape();
                    if (shape.getClass() == Path2D.Double.class) {
                        shape = stroke.createStrokedShape(shape);
                    }
                    if (shape.intersects(area)) {
                        wrappers.remove(wrapper);
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
            private int x0;
            private int y0;
            private Rectangle2D rectangle;
            private ShapeWrapper wrapper;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x0 = e.getX();
                y0 = e.getY();
                rectangle = new Rectangle2D.Double(x0, y0, 1, 1);
                wrapper = new ShapeWrapper(rectangle, pickedColor1.getColor(), pickedColor2.getColor());
                wrappers.add(wrapper);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x1 = e.getX();
                int y1 = e.getY();
                super.mouseMoved(e);
                wrappers.removeLast();
                if (x0 <= x1 && y0 <= y1) {
                    rectangle = new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0);
                    wrapper = new ShapeWrapper(rectangle, pickedColor1.getColor(), pickedColor2.getColor());
                } else if (x0 > x1 && y0 > y1) {
                    rectangle = new Rectangle2D.Double(x1, y1, x0 - x1, y0 - y1);
                    wrapper = new ShapeWrapper(rectangle, pickedColor1.getColor(), pickedColor2.getColor());
                } else if (x0 > x1) {
                    rectangle = new Rectangle2D.Double(x1, y0, x0 - x1, y1 - y0);
                    wrapper = new ShapeWrapper(rectangle, pickedColor1.getColor(), pickedColor2.getColor());
                } else {
                    rectangle = new Rectangle2D.Double(x0, y1, x1 - x0, y0 - y1);
                    wrapper = new ShapeWrapper(rectangle, pickedColor1.getColor(), pickedColor2.getColor());
                }
                wrappers.add(wrapper);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (Math.abs(e.getX() - x0) < 2 || Math.abs(e.getY() - y0) < 2)
                    wrappers.removeLast();
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
            private int x0;
            private int y0;
            private Ellipse2D ellipse;
            private ShapeWrapper wrapper;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x0 = e.getX();
                y0 = e.getY();
                ellipse = new Ellipse2D.Double(x0, y0, 1, 1);
                wrappers.addLast(new ShapeWrapper(ellipse, pickedColor1.getColor(), pickedColor2.getColor()));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x1 = e.getX();
                int y1 = e.getY();
                super.mouseMoved(e);
                wrappers.removeLast();
                if (x0 <= x1 && y0 <= y1) {
                    ellipse = new Ellipse2D.Double(x0, y0, x1 - x0, y1 - y0);
                    wrapper = new ShapeWrapper(ellipse, pickedColor1.getColor(), pickedColor2.getColor());
                } else if (x0 > x1 && y0 > y1) {
                    ellipse = new Ellipse2D.Double(x1, y1, x0 - x1, y0 - y1);
                    wrapper = new ShapeWrapper(ellipse, pickedColor1.getColor(), pickedColor2.getColor());
                } else if (x0 > x1) {
                    ellipse = new Ellipse2D.Double(x1, y0, x0 - x1, y1 - y0);
                    wrapper = new ShapeWrapper(ellipse, pickedColor1.getColor(), pickedColor2.getColor());
                } else {
                    ellipse = new Ellipse2D.Double(x0, y1, x1 - x0, y0 - y1);
                    wrapper = new ShapeWrapper(ellipse, pickedColor1.getColor(), pickedColor2.getColor());
                }
                wrappers.add(wrapper);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (Math.abs(e.getX() - x0) < 2 || Math.abs(e.getY() - y0) < 2)
                    wrappers.removeLast();
            }
        };
        this.adapter = adapter;
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void fill() {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
        MouseAdapter adapter = new MouseAdapter() {
            private BasicStroke stroke = new BasicStroke(1);

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                Rectangle2D.Double area = new Rectangle2D.Double(e.getX() - 10, e.getY() - 10, 20, 20);
                Iterator<Wrapper> shapeIterator = wrappers.descendingIterator();
                Wrapper wrapper;
                Shape shape;
                while (shapeIterator.hasNext()) {
                    wrapper = shapeIterator.next();
                    if (wrapper.getClass() == ShapeWrapper.class) {
                        shape = wrapper.getShape();
                        ShapeWrapper shapeWrapper = (ShapeWrapper) wrapper;
                        if (shape.getClass() == Path2D.Double.class) {
                            shape = stroke.createStrokedShape(shape);
                        }
                        if (shape.intersects(area)) {
                            if (SwingUtilities.isLeftMouseButton(e))
                                shapeWrapper.setBorderColor(pickedColor1.getColor());
                            else {
                                if (shapeWrapper.getShape().getClass() != Path2D.Double.class)
                                    shapeWrapper.setFillColor(pickedColor2.getColor());
                            }
                            repaint();
                            break;
                        }
                    }
                }
            }
        };
        this.adapter = adapter;
        addMouseListener(adapter);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void Pencil() {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
        MouseAdapter adapter = new MouseAdapter() {
            private int x;
            private int y;
            private LinkedList<Integer> xs;
            private LinkedList<Integer> ys;
            Graphics2D graphics2D = (Graphics2D) getGraphics();

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x = e.getX();
                y = e.getY();
                xs = new LinkedList<>();
                ys = new LinkedList<>();
                graphics2D.setColor(pickedColor1.getColor());
                graphics2D.drawLine(x, y, x, y);
                xs.add(x);
                ys.add(y);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Integer[] xArray = xs.toArray(new Integer[0]);
                Integer[] yArray = ys.toArray(new Integer[0]);
                Path2D path = new Path2D.Double();
                path.moveTo(xArray[0], yArray[0]);
                for (int i = 1; i < xArray.length; i++) {
                    path.lineTo(xArray[i], yArray[i]);
                }
                wrappers.add(new ShapeWrapper(path, pickedColor1.getColor(), TRANSPARENT_COLOR));
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                graphics2D.drawLine(x, y, e.getX(), e.getY());
                xs.add(e.getX());
                ys.add(e.getY());
                x = e.getX();
                y = e.getY();
            }
        };

        this.adapter = adapter;
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }


    @Override
    public Dimension getPreferredSize() {
        return picSize;
    }

    public void setPicSize(Dimension dimension) {
        picSize = dimension;
        revalidate();
    }

    public void setPicSize(int width, int height) {
        setPicSize(new Dimension(width, height));
    }

    public void loadPic(BufferedImage image) {
        setPicSize(image.getWidth(), image.getHeight());
        wrappers.add(new imageWrapper(image, 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Wrapper wrapper : wrappers) {
            wrapper.paint(g2d);
        }
    }
}

