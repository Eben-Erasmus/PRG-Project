package ui.buttons;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cart {

    public static void main(String[] args) {

        // Create the main frame
        JFrame frame = new JFrame("Online Shopping");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the button
        JPanel panel = new JPanel();

        // Create the Cart button
        JButton cartButton = new JButton("Cart");

        // Add action listener to the button
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Add the button to the panel
        panel.add(cartButton);

        // Add the panel to the frame
        frame.add(panel);

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}
