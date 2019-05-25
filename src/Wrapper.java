import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public interface Wrapper {
    Shape getShape();

    void paint(Graphics2D graphics2D);
}

class ShapeWrapper implements Wrapper {
    private Shape shape;
    private Color borderColor;
    private Color fillColor;

    public ShapeWrapper(Shape shape, Color borderColor, Color fillColor) {
        this.shape = shape;
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
    public Shape getShape() {
        return shape;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(fillColor);
        g2d.fill(shape);
        g2d.setColor(borderColor);
        g2d.draw(shape);
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
