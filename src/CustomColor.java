import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CustomColor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private ColorShower colorShower1;
    private JLabel errorLabel;
    private PickedColor pickedColor;

    public CustomColor(PickedColor pickedColor) {
        this.pickedColor = pickedColor;
        errorLabel.setVisible(false);

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

        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                check();
            }

            private void check(){
                if(textField1.getText().length()==6){
                    try{
                        colorShower1.setColor(Color.decode("#"+textField1.getText()));
                        errorLabel.setVisible(false);
                    }catch (NumberFormatException e){
                        errorLabel.setVisible(true);
                    }
                }
                else if(textField1.getText().length()>6)
                    errorLabel.setVisible(true);
            }
        });
    }

    private void onOK() {
        pickedColor.setColor(Color.decode("#"+textField1.getText()));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(PickedColor pickedColor) {
        CustomColor dialog = new CustomColor(pickedColor);
        dialog.pack();
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        colorShower1 = new ColorShower(Color.WHITE);
    }
}
