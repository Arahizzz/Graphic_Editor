import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

public interface Wrapper {
    Shape getShape();

    void paint(Graphics2D graphics2D);

    void move(int x, int y);

    void reSize(int newW, int newH);
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
    public void move(int x, int y) {
        rectangularShape.setFrame(rectangularShape.getX() + x, rectangularShape.getY() + y,
                rectangularShape.getWidth(), rectangularShape.getHeight());
    }

    @Override
    public void reSize(int newW, int newH) {
        rectangularShape.setFrame(rectangularShape.getX(), rectangularShape.getY(), newW, newH);
    }
}

class LineWrapper extends ShapeWrapper {
    private Line2D line;

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

    @Override
    public void reSize(int newW, int newH) {
        //Resize of line is forbidden
    }
}

class PencilWrapper extends ShapeWrapper {
    private Path2D path;

    public PencilWrapper(Path2D path, Color borderColor, Color fillColor) {
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

    @Override
    public void reSize(int newW, int newH) {
        //Resize of pencil is forbidden
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

    @Override
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
