import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditorWindow {
    private JPanel actions;
    private JPanel mainPanel;
    private Picture picture;
    private JScrollPane scrollPane;
    private JButton pasteButton;
    private JPanel intstruments;
    private JPanel instPanel;
    private JButton selectButton;
    private Colors colors;
    private JButton customButton;
    private PickedColor pickedColor1;
    private PickedColor pickedColor2;
    private EditorWindow editorWindow1;
    private JFrame frame = new JFrame("EditorWindow");
    private JPanel shapes;
    private JToggleButton pencil;
    private JToggleButton fill;
    private JToggleButton erase;
    private JToggleButton line;
    private JToggleButton rectangle;
    private JToggleButton triangle;
    private JToggleButton circle;
    private JToggleButton select;
    private JScrollPane instScroll;
    private JToggleButton pentagon;
    private JToggleButton star;
    private JPanel picPanel;
    private JMenuBar menuBar = new JMenuBar();

    public EditorWindow() {
        initButtons();
        initMenubar();
        setUpScroll();
    }

    private void save(File file) {
        BufferedImage image = new BufferedImage(picture.getWidth(), picture.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        try {
            Graphics2D graphics2D = image.createGraphics();
            picture.paintComponent(graphics2D);
            ImageIO.write(image, "png", file);
        } catch (IOException log) {
            log.printStackTrace();
        }
    }

    private void loadFile(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            picture.loadPic(image);
        } catch (IOException log) {
            log.printStackTrace();
        }
    }

    private void setUpScroll() {
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        instScroll.getHorizontalScrollBar().setUnitIncrement(16);
        instScroll.getVerticalScrollBar().setUnitIncrement(16);
    }

    private void initMenubar(){
        JMenu file = new JMenu("File");
        JMenu options = new JMenu("Options");

        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem resize = new JMenuItem("Change size");

        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        resize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));

        resize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResizePicture.main(picture);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png");
                fileChooser.setFileFilter(filter);
                if (fileChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
                    save(fileChooser.getSelectedFile());
                }
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png");
                fileChooser.setFileFilter(filter);
                if (fileChooser.showDialog(null, "Open") == JFileChooser.APPROVE_OPTION) {
                    loadFile(fileChooser.getSelectedFile());
                }
            }
        });

        file.add(open);
        file.add(save);
        options.add(resize);

        menuBar.add(file);
        menuBar.add(options);

        frame.setJMenuBar(menuBar);
    }

    private void initButtons() {

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(pencil);
        buttonGroup.add(fill);
        buttonGroup.add(erase);
        buttonGroup.add(line);
        buttonGroup.add(rectangle);
        buttonGroup.add(circle);
        buttonGroup.add(selectButton);
        buttonGroup.add(select);
        buttonGroup.add(triangle);
        buttonGroup.add(pentagon);
        buttonGroup.add(star);

        customButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Color newColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);
                    if (newColor != null)
                        pickedColor1.setColor(newColor);
                } else {
                    Color newColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);
                    if (newColor != null)
                        pickedColor2.setColor(newColor);
                }
            }
        });

        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.lineDrawer();
            }
        });

        erase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.eraser();
            }
        });

        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.ellipse();
            }
        });

        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.rectangle();
            }
        });

        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.selection();
            }
        });

        fill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.fill();
            }
        });

        pencil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.Pencil();
            }
        });

        triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.triangle();
            }
        });

        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextInsert.main(picture);
            }
        });

        pentagon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.pentagon();
            }
        });

        star.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.star();
            }
        });

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        EditorWindow window = new EditorWindow();
        JFrame frame = window.frame;
        frame.setContentPane(window.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200, 850);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        pickedColor1 = new PickedColor(Color.BLACK, "Color 1");
        pickedColor2 = new PickedColor(Color.WHITE, "Color 2");
        colors = new Colors(pickedColor1, pickedColor2);
        picture = new Picture(1000, 600, pickedColor1, pickedColor2);
    }
}
