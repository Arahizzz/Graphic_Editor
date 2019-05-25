import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private JToggleButton text;
    private JToggleButton erase;
    private JToggleButton zoom;
    private JToggleButton picker;
    private JToggleButton line;
    private JToggleButton rectangle;
    private JToggleButton button4;
    private JToggleButton circle;
    private JToggleButton select;
    private JMenuBar menuBar = new JMenuBar();

    public EditorWindow() {
        initButtons();
        initMenubar();
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
        buttonGroup.add(picker);
        buttonGroup.add(text);
        buttonGroup.add(zoom);
        buttonGroup.add(line);
        buttonGroup.add(rectangle);
        buttonGroup.add(circle);
        buttonGroup.add(selectButton);
        buttonGroup.add(select);

        customButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(SwingUtilities.isLeftMouseButton(e))
                    CustomColor.main(pickedColor1);
                else
                    CustomColor.main(pickedColor2);
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
                picture.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
    }

    private void flattenButtons() {
        pasteButton.setContentAreaFilled(false);
        pasteButton.setFocusPainted(false);
        pencil.setContentAreaFilled(false);
        pencil.setFocusPainted(false);
        fill.setContentAreaFilled(false);
        fill.setFocusPainted(false);
        erase.setContentAreaFilled(false);
        erase.setFocusPainted(false);
        picker.setContentAreaFilled(false);
        picker.setFocusPainted(false);
        text.setContentAreaFilled(false);
        text.setFocusPainted(false);
        zoom.setContentAreaFilled(false);
        zoom.setFocusPainted(false);
        selectButton.setContentAreaFilled(false);
        selectButton.setFocusPainted(false);
        customButton.setContentAreaFilled(false);
        customButton.setFocusPainted(false);
        customButton.setPreferredSize(new Dimension(80, 100));
        line.setContentAreaFilled(false);
        line.setFocusPainted(false);
        circle.setContentAreaFilled(false);
        circle.setFocusPainted(false);
        rectangle.setContentAreaFilled(false);
        rectangle.setFocusPainted(false);
        button4.setContentAreaFilled(false);
        button4.setFocusPainted(false);
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
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        pickedColor1 = new PickedColor(Color.BLACK, "Color 1");
        pickedColor2 = new PickedColor(Color.WHITE, "Color 2");
        colors = new Colors(pickedColor1, pickedColor2);
        picture = new Picture(600, 400, pickedColor1, pickedColor2);
    }
}
