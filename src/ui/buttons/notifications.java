package ui.buttons;

import javax.swing.*;
import java.awt.*;
import javax.swing.JRadioButton;

public class notifications {
    JFrame frame = new JFrame("My GUI");

    public notifications(){
        JRadioButton();

        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout(10,10));
        frame.setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        frame.pack();// is used to automatically adjust the size of a JFrame to fit its contents

    }

    private void JRadioButton() {

        JRadioButton inApp = new JRadioButton();
        JRadioButton push = new JRadioButton();
        JRadioButton email = new JRadioButton();

        JButton select = new JButton("OK");
        JLabel L1 = new JLabel("Preferred Delivery Mode: ");

        inApp.setText("In App Notifications");
        push.setText("Push Notifications");
        email.setText("Email Notifications");

        inApp.setBounds(20, 80, 200, 40);
        push.setBounds(20, 160, 200, 40);
        email.setBounds(20, 240, 200, 40);
        select.setBounds(20, 300, 200, 40);
        L1.setBounds(20, 40, 200, 40);
        frame.add(inApp);
        frame.add(push);
        frame.add(email);
        frame.add(L1);
        frame.add(select);

        //grouping the buttons as part of one unit
        ButtonGroup G1 = new ButtonGroup();
        G1.add(inApp);
        G1.add(push);
        G1.add(email);

        frame.setSize(800, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
