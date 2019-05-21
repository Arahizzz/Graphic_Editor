import javax.swing.*;

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
    private JFrame frame = new JFrame("EditorWindow");

    public EditorWindow() {
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
    }
}
