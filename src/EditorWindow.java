import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public EditorWindow() {
        initButtons();
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
