package ui.buttons;

import javax.swing.*;
import java.awt.*;

public class theme {
    JFrame frame = new JFrame("My GUI");

    public theme(){
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

        JRadioButton dark = new JRadioButton();
        JRadioButton light = new JRadioButton();


        JButton select = new JButton("OK");
        JLabel L1 = new JLabel("Preferred theme ");


        dark.setText("Dark Theme");
        light.setText("Light Theme");


        dark.setBounds(20, 80, 240, 40);
        light.setBounds(20, 160, 200, 40);

        select.setBounds(20, 240, 200, 40);
        L1.setBounds(20, 40, 200, 40);

        frame.add(dark);
        frame.add(light);

        frame.add(L1);
        frame.add(select);

        //grouping the buttons as part of one unit
        ButtonGroup G1 = new ButtonGroup();
        G1.add(dark);
        G1.add(light);

        frame.setSize(800, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
