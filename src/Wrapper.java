import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

public interface Wrapper {
    Shape getShape();

    void paint(Graphics2D graphics2D);

    void move(int x, int y);

    void resizeHandler(MouseEvent e, Side side);

    boolean contains(Point2D point);

    Tuple<Mode, Side> getMode(MouseEvent e);
}

abstract class ShapeWrapper implements Wrapper {
    private Color borderColor;
    private Color fillColor;

    public ShapeWrapper(Color borderColor, Color fillColor) {
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    abstract public Shape getShape();

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(fillColor);
        g2d.fill(getShape());
        g2d.setColor(borderColor);
        g2d.draw(getShape());
    }
}

class RectangularShapeWrapper extends ShapeWrapper {
    private RectangularShape rectangularShape;

    public RectangularShapeWrapper(RectangularShape rectangularShape, Color borderColor, Color fillColor) {
        super(borderColor, fillColor);
        this.rectangularShape = rectangularShape;
    }

    public RectangularShape getShape() {
        return rectangularShape;
    }

    @Override
    public boolean contains(Point2D point) {
        return rectangularShape.contains(point);
    }

    @Override
    public void move(int x, int y) {
        rectangularShape.setFrame(rectangularShape.getX() + x, rectangularShape.getY() + y,
                rectangularShape.getWidth(), rectangularShape.getHeight());
    }

    public void reSize(int newW, int newH) {
        rectangularShape.setFrame(rectangularShape.getX(), rectangularShape.getY(), newW, newH);
    }

    @Override
    public void resizeHandler(MouseEvent e, Side side) {
        final int x = getShape().getBounds().x;
        final int y = getShape().getBounds().y;
        if (side == Side.SOUTH_EAST)
            reSize(e.getX() - x, e.getY() - y);
        else if (side == Side.SOUTH_WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), e.getY() - y);
            move(e.getX() - x, 0);
        } else if (side == Side.NORTH_WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), getShape().getBounds().height + y - e.getY());
            move(e.getX() - x, e.getY() - y);
        } else if (side == Side.NORTH_EAST) {
            reSize(e.getX() - x, getShape().getBounds().height + y - e.getY());
            move(0, e.getY() - y);
        } else if (side == Side.NORTH) {
            reSize(getShape().getBounds().width, getShape().getBounds().height + y - e.getY());
            move(0, e.getY() - y);
        } else if (side == Side.SOUTH)
            reSize(getShape().getBounds().width, e.getY() - y);
        else if (side == Side.WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), getShape().getBounds().height);
            move(e.getX() - x, 0);
        } else if (side == Side.EAST)
            reSize(e.getX() - x, getShape().getBounds().height);
    }

    public Tuple<Mode, Side> getMode(MouseEvent e) {
        Rectangle2D area = new Rectangle2D.Double(e.getX() - 10, e.getY() - 10, 20, 20);
        final int x = e.getX();
        final int y = e.getY();
        return getModeSideTuple(area, x, y, rectangularShape);
    }

    public static Tuple<Mode, Side> getModeSideTuple(Rectangle2D area, int x, int y, RectangularShape rectangularShape) {
        BasicStroke stroke = new BasicStroke(8);
        Mode mode;
        Side side = Side.NONE;
        Point2D.Double point = new Point2D.Double(x, y);
        Line2D.Double north = new Line2D.Double(rectangularShape.getX(), rectangularShape.getY(), rectangularShape.getX() + rectangularShape.getWidth(), rectangularShape.getY());
        Line2D.Double south = new Line2D.Double(rectangularShape.getX(), rectangularShape.getY() + rectangularShape.getHeight(), rectangularShape.getX() + rectangularShape.getWidth(), rectangularShape.getY() + rectangularShape.getHeight());
        Line2D.Double west = new Line2D.Double(rectangularShape.getX(), rectangularShape.getY(), rectangularShape.getX(), rectangularShape.getY() + rectangularShape.getHeight());
        Line2D.Double east = new Line2D.Double(rectangularShape.getX() + rectangularShape.getWidth(), rectangularShape.getY(), rectangularShape.getX() + rectangularShape.getWidth(), rectangularShape.getY() + rectangularShape.getHeight());
        Point2D.Double nort_west = new Point2D.Double(rectangularShape.getMinX(), rectangularShape.getMinY());
        Point2D.Double nort_east = new Point2D.Double(rectangularShape.getMaxX(), rectangularShape.getMinY());
        Point2D.Double south_west = new Point2D.Double(rectangularShape.getMinX(), rectangularShape.getMaxY());
        Point2D.Double south_east = new Point2D.Double(rectangularShape.getMaxX(), rectangularShape.getMaxY());
        if (area.contains(nort_west)) {
            side = Side.NORTH_WEST;
            mode = Mode.RESIZE;
        } else if (area.contains(nort_east)) {
            side = Side.NORTH_EAST;
            mode = Mode.RESIZE;
        } else if (area.contains(south_west)) {
            side = Side.SOUTH_WEST;
            mode = Mode.RESIZE;
        } else if (area.contains(south_east)) {
            side = Side.SOUTH_EAST;
            mode = Mode.RESIZE;
        } else if (stroke.createStrokedShape(north).contains(point)) {
            side = Side.NORTH;
            mode = Mode.RESIZE;
        } else if (stroke.createStrokedShape(south).contains(point)) {
            side = Side.SOUTH;
            mode = Mode.RESIZE;
        } else if (stroke.createStrokedShape(west).contains(point)) {
            side = Side.WEST;
            mode = Mode.RESIZE;
        } else if (stroke.createStrokedShape(east).contains(point)) {
            side = Side.EAST;
            mode = Mode.RESIZE;
        } else
            mode = Mode.MOVE;
        return new Tuple<>(mode, side);
    }
}

class LineWrapper extends ShapeWrapper {
    private Line2D line;
    private static BasicStroke stroke = new BasicStroke(8);

    public LineWrapper(Line2D line, Color borderColor) {
        super(borderColor, null);
        this.line = line;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(getBorderColor());
        g2d.draw(line);
    }

    public Line2D getShape() {
        return line;
    }

    @Override
    public boolean contains(Point2D point) {
        return stroke.createStrokedShape(line).contains(point);
    }

    @Override
    public void move(int x, int y) {
        line.setLine(line.getX1() + x, line.getY1() + y, line.getX2() + x, line.getY2() + y);
    }

    @Override
    public void resizeHandler(MouseEvent e, Side side) {
        if (side == Side.POINT1)
            line.setLine(e.getPoint(), line.getP2());
        else
            line.setLine(line.getP1(), e.getPoint());
    }

    @Override
    public Tuple<Mode, Side> getMode(MouseEvent e) {
        Rectangle2D area = new Rectangle2D.Double(e.getX() - 10, e.getY() - 10, 20, 20);
        Mode mode = Mode.MOVE;
        Side side = Side.NONE;
        if (area.contains(line.getP1())) {
            mode = Mode.RESIZE;
            side = Side.POINT1;
        } else if (area.contains(line.getP2())) {
            mode = Mode.RESIZE;
            side = Side.POINT2;
        }
        return new Tuple<>(mode, side);
    }
}

class PencilWrapper extends ShapeWrapper {
    private Path2D path;
    private static BasicStroke stroke = new BasicStroke(8);

    public PencilWrapper(Path2D path, Color borderColor, Color fillColor) {
        super(borderColor, fillColor);
        this.path = path;
    }

    @Override
    public Path2D getShape() {
        return path;
    }

    @Override
    public boolean contains(Point2D point) {
        return stroke.createStrokedShape(path).contains(point);
    }

    @Override
    public void move(int x, int y) {
        AffineTransform affineTransform = AffineTransform.getTranslateInstance(x, y);
        path = (Path2D) affineTransform.createTransformedShape(path);
    }

    @Override
    public void resizeHandler(MouseEvent e, Side side) {
    }

    @Override
    public void setFillColor(Color fillColor) {
    }

    @Override
    public Tuple<Mode, Side> getMode(MouseEvent e) {
        //You can't resize pencil
        return new Tuple<>(Mode.MOVE, Side.NONE);
    }
}

class TriangleWrapper extends ShapeWrapper {
    Path2D figure = new Path2D.Double();
    Triangle triangle;

    public TriangleWrapper(Triangle triangle, Color borderColor, Color fillColor) {
        super(borderColor, fillColor);
        this.triangle = triangle;
    }

    @Override
    public void paint(Graphics2D g2d) {
        figure.reset();
        figure.moveTo(triangle.getX(), triangle.getY());
        figure.lineTo(triangle.getX(), triangle.getY() + triangle.getHeight());
        figure.lineTo(triangle.getX() + triangle.getWidth(), triangle.getY() + triangle.getHeight());
        figure.closePath();

        g2d.setColor(getFillColor());
        g2d.fill(figure);
        g2d.setColor(getBorderColor());
        g2d.draw(figure);
    }

    @Override
    public void move(int x, int y) {
        triangle.setX(triangle.getX() + x);
        triangle.setY(triangle.getY() + y);
    }

    @Override
    public void resizeHandler(MouseEvent e, Side side) {
        final int x = getShape().getBounds().x;
        final int y = getShape().getBounds().y;
        if (side == Side.SOUTH_EAST)
            reSize(e.getX() - x, e.getY() - y);
        else if (side == Side.SOUTH_WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), e.getY() - y);
            move(e.getX() - x, 0);
        } else if (side == Side.NORTH_WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), getShape().getBounds().height + y - e.getY());
            move(e.getX() - x, e.getY() - y);
        } else if (side == Side.NORTH_EAST) {
            reSize(e.getX() - x, getShape().getBounds().height + y - e.getY());
            move(0, e.getY() - y);
        } else if (side == Side.NORTH) {
            reSize(getShape().getBounds().width, getShape().getBounds().height + y - e.getY());
            move(0, e.getY() - y);
        } else if (side == Side.SOUTH)
            reSize(getShape().getBounds().width, e.getY() - y);
        else if (side == Side.WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), getShape().getBounds().height);
            move(e.getX() - x, 0);
        } else if (side == Side.EAST)
            reSize(e.getX() - x, getShape().getBounds().height);
    }

    public void reSize(int newW, int newH) {
        triangle.setFrame(triangle.getX(), triangle.getY(), newW, newH);
    }

    @Override
    public boolean contains(Point2D point) {
        return figure.contains(point);
    }

    @Override
    public Tuple<Mode, Side> getMode(MouseEvent e) {
        Rectangle2D area = new Rectangle2D.Double(e.getX() - 10, e.getY() - 10, 20, 20);
        final int x = e.getX();
        final int y = e.getY();
        return RectangularShapeWrapper.getModeSideTuple(area, x, y, figure.getBounds());
    }

    @Override
    public Path2D getShape() {
        return figure;
    }
}


class imageWrapper implements Wrapper {
    private BufferedImage image;
    private int x;
    private int y;
    private Rectangle2D.Double bounds;

    public imageWrapper(BufferedImage image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        bounds = new Rectangle2D.Double(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public Shape getShape() {
        return bounds;
    }

    @Override
    public void paint(Graphics2D graphics2D) {
        graphics2D.drawImage(image, x, y, null);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
        bounds = new Rectangle2D.Double(this.x, this.y, image.getWidth(), image.getHeight());
    }

    public void reSize(int newW, int newH) {
        class Resizer extends SwingWorker<BufferedImage, Void> {

            @Override
            protected void done() {
                try {
                    image = get();
                    bounds = new Rectangle2D.Double(x, y, image.getWidth(), image.getHeight());
                } catch (InterruptedException i) {
                } catch (ExecutionException e) {
                }
            }

            @Override
            protected BufferedImage doInBackground() throws Exception {
                Image tmp = image.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2d = dimg.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                return dimg;
            }
        }
        Resizer resizer = new Resizer();
        resizer.execute();
    }

    @Override
    public void resizeHandler(MouseEvent e, Side side) {
        final int x = getShape().getBounds().x;
        final int y = getShape().getBounds().y;
        if (side == Side.SOUTH_EAST)
            reSize(e.getX() - x, e.getY() - y);
        else if (side == Side.SOUTH_WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), e.getY() - y);
            move(e.getX() - x, 0);
        } else if (side == Side.NORTH_WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), getShape().getBounds().height + y - e.getY());
            move(e.getX() - x, e.getY() - y);
        } else if (side == Side.NORTH_EAST) {
            reSize(e.getX() - x, getShape().getBounds().height + y - e.getY());
            move(0, e.getY() - y);
        } else if (side == Side.NORTH) {
            reSize(getShape().getBounds().width, getShape().getBounds().height + y - e.getY());
            move(0, e.getY() - y);
        } else if (side == Side.SOUTH)
            reSize(getShape().getBounds().width, e.getY() - y);
        else if (side == Side.WEST) {
            reSize(getShape().getBounds().width + x - e.getX(), getShape().getBounds().height);
            move(e.getX() - x, 0);
        } else if (side == Side.EAST)
            reSize(e.getX() - x, getShape().getBounds().height);
    }

    @Override
    public boolean contains(Point2D point) {
        return bounds.contains(point);
    }

    @Override
    public Tuple<Mode, Side> getMode(MouseEvent e) {
        Rectangle2D area = new Rectangle2D.Double(e.getX() - 10, e.getY() - 10, 20, 20);
        final int x = e.getX();
        final int y = e.getY();
        return RectangularShapeWrapper.getModeSideTuple(area, x, y, bounds);
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class TextWrapper extends ShapeWrapper {
    private String text;
    private Font font;
    private int x;
    private int y;
    private Rectangle2D.Double bounds = new Rectangle2D.Double(0, 0, 0, 0);

    public TextWrapper(String text, Font font, int x, int y) {
        super(Color.BLACK, Color.WHITE);
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
    }

    public TextWrapper() {
        super(Color.BLACK, Color.WHITE);
        this.text = "";
        this.font = Font.decode(Font.SERIF);
        this.x = 5;
        this.y = 5;
    }

    @Override
    public Shape getShape() {
        return bounds;
    }

    @Override
    public void paint(Graphics2D graphics2D) {
        FontMetrics metrics = graphics2D.getFontMetrics(font);
        bounds = new Rectangle2D.Double(x, y - metrics.getHeight(), metrics.stringWidth(text), metrics.getHeight());

        graphics2D.setColor(getBorderColor());
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void resizeHandler(MouseEvent e, Side side) {
    }

    @Override
    public boolean contains(Point2D point) {
        return bounds.contains(point);
    }

    @Override
    public Tuple<Mode, Side> getMode(MouseEvent e) {
        return new Tuple<>(Mode.MOVE, Side.NONE);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
