package ui.buttons;

import javax.swing.*;

public class ChangePassword {
    public ChangePassword() {

        //Opening window
        JFrame ui = new JFrame("My GUI");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(300, 200);

        //Container for the Jframe
        JPanel panel = new JPanel();
        panel.setLayout(null);
        ui.add(panel);

        //Jlabel and JText for  New Password
        JLabel NewPasswordLabel = new JLabel();
        NewPasswordLabel.setBounds(10, 10, 200, 25);
        NewPasswordLabel.setText("New Password:");
        panel.add(NewPasswordLabel);

        JTextField NewPassText = new JTextField(20);
        NewPassText.setBounds(10, 50, 165, 25);
        panel.add(NewPassText);
        //Jlabel and JText for  New Password

        //Jlabel and JText for  Confirm Password
        JLabel ConfirmPassLabel = new JLabel();
        ConfirmPassLabel.setBounds(10, 80, 200, 25);
        ConfirmPassLabel.setText("Confirm Password:");
        panel.add(ConfirmPassLabel);

        JTextField ConfirmText = new JTextField(20);
        ConfirmText.setBounds(10, 120, 165, 25);
        panel.add(ConfirmText);
        //Jlabel and JText for  Confirm Password

        //Makes UI visible
        ui.setVisible(true);
        }
}
