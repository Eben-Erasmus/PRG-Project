package ui.buttons;

import javax.swing.*;

public class AccountOrders {
    public AccountOrders() {
        //Opening window
        JFrame ui = new JFrame("My GUI");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(800, 600);

        //Container for the Jframe
        JPanel panel = new JPanel();
        panel.setLayout(null);
        ui.add(panel);

        //Jlabel for orders
        JLabel OrdersLabel = new JLabel();
        OrdersLabel.setBounds(10, 10, 200, 25);
        OrdersLabel.setText("Orders:");
        panel.add(OrdersLabel);

        //Makes UI visible
        ui.setVisible(true);
    }
}
