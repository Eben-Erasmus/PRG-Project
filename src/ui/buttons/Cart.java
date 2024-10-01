package ui.buttons;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane; // Import for popup dialogs
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cart {

    public Cart() {


        // Create the main frame
        JFrame frame = new JFrame("Cart");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the button
        JPanel panel = new JPanel();











        // Add the panel to the frame
        frame.add(panel);

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}
