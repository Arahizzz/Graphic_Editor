import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class TextInsert extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JSpinner spinner1;
    private JEditorPane editorPane1;
    private Picture picture;

    public TextInsert(Picture picture) {
        this.picture = picture;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        Font[] allFonts = ge.getAllFonts();
        String[] fontnames = Arrays.stream(allFonts).map(Font::getName).toArray(String[]::new);
        comboBox1.setModel(new DefaultComboBoxModel(fontnames));
        spinner1.setModel(new SpinnerNumberModel(24, 1, 250, 15));
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorPane1.setFont(new Font((String) comboBox1.getSelectedItem(), Font.PLAIN, (Integer) spinner1.getValue()));
            }
        });

        spinner1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                editorPane1.setFont(new Font((String) comboBox1.getSelectedItem(), Font.PLAIN, (Integer) spinner1.getValue()));
            }
        });
    }

    private void onOK() {
        Font font = new Font((String) comboBox1.getSelectedItem(), Font.PLAIN, (Integer) spinner1.getValue());
        TextWrapper wrapper = new TextWrapper(editorPane1.getText(), font, 20, 100);
        picture.addWrapper(wrapper);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(Picture picture) {
        TextInsert dialog = new TextInsert(picture);
        dialog.pack();
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
