package ui.buttons;

import javax.swing.*;
import java.awt.*;

public class paymentMethod {
    public paymentMethod(){

        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout(10,10));
        frame.setLocationRelativeTo(null);
        //setResizable(false); This ensures that the window cannot be resized


        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        frame.pack();// is used to automatically adjust the size of a JFrame to fit its contents
        frame.setVisible(true);
    }
}
