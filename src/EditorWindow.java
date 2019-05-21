import javax.swing.*;
import java.awt.*;

public class EditorWindow {
    private JPanel actions;
    private JPanel mainPanel;
    private Picture picture;
    private JScrollPane scrollPane;
    private JPanel intstruments;
    private JButton pasteButton;
    private JButton pencil;
    private JButton fill;
    private JButton erase;
    private JButton picker;
    private JButton textButton;
    private JButton zoom;
    private JButton selectButton;
    private Colors colors;
    private JButton customButton;
    private PickedColor pickedColor1;
    private PickedColor pickedColor2;
    private JFrame frame = new JFrame("EditorWindow");

    public EditorWindow() {
        initButtons();
    }

    private void initButtons() {
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
        textButton.setContentAreaFilled(false);
        textButton.setFocusPainted(false);
        zoom.setContentAreaFilled(false);
        zoom.setFocusPainted(false);
        selectButton.setContentAreaFilled(false);
        selectButton.setFocusPainted(false);
        customButton.setContentAreaFilled(false);
        customButton.setFocusPainted(false);
        customButton.setPreferredSize(new Dimension(80, 100));
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
        picture = new Picture(600, 400);
        colors = new Colors();
        pickedColor1 = new PickedColor(Color.white, "Color 1");
        pickedColor2 = new PickedColor(Color.black, "Color 2");
    }
}
