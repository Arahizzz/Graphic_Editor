import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public interface Wrapper {
    Shape getShape();

    void paint(Graphics2D graphics2D);

    void move(int x, int y);
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

    abstract public void move(int x, int y);
}

class RectangularShapeWrapper extends ShapeWrapper {
    RectangularShape rectangularShape;

    public RectangularShapeWrapper(RectangularShape rectangularShape, Color borderColor, Color fillColor) {
        super(borderColor, fillColor);
        this.rectangularShape = rectangularShape;
    }

    public RectangularShape getShape() {
        return rectangularShape;
    }

    @Override
    public void move(int x, int y) {
        rectangularShape.setFrame(rectangularShape.getX() + x, rectangularShape.getY() + y,
                rectangularShape.getWidth(), rectangularShape.getHeight());
    }
}

class LineWrapper extends ShapeWrapper {
    Line2D line;

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
    public void move(int x, int y) {
        line.setLine(line.getX1() + x, line.getY1() + y, line.getX2() + x, line.getY2() + y);
    }
}

class PathWrapper extends ShapeWrapper {
    Path2D path;

    public PathWrapper(Path2D path, Color borderColor, Color fillColor) {
        super(borderColor, fillColor);
        this.path = path;
    }

    @Override
    public Path2D getShape() {
        return path;
    }

    @Override
    public void move(int x, int y) {
        AffineTransform affineTransform = AffineTransform.getTranslateInstance(x, y);
        path = (Path2D) affineTransform.createTransformedShape(path);
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
