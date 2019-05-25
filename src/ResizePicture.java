import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResizePicture extends JDialog {
    private Picture picture;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner spinner1;
    private JSpinner spinner2;

    public ResizePicture(Picture picture) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.picture = picture;

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

        Dimension dimension = picture.getPreferredSize();
        SpinnerNumberModel model1 = new SpinnerNumberModel(dimension.width, 1, 999999, 100);
        SpinnerNumberModel model2 = new SpinnerNumberModel(dimension.height, 1, 999999, 100);

        spinner1.setModel(model1);
        spinner2.setModel(model2);
    }

    private void onOK() {
        picture.setPicSize((Integer) spinner1.getValue(), (Integer) spinner2.getValue());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(Picture picture) {
        ResizePicture dialog = new ResizePicture(picture);
        dialog.pack();
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
