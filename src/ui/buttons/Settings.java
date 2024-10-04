package ui.buttons;

import javax.swing.*;
import java.awt.*;

public class Settings {
    public Settings(String username) {

        JFrame frame = new JFrame("Settings");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        // Account Information Button
        JButton accountInformation= new JButton("Account Information");
        accountInformation.setFocusable(false);//the box around the button is removed
        accountInformation.setBounds( 20, 50, 400, 40);
        accountInformation.setFont(new Font("Arial", Font.PLAIN, 16));
        accountInformation.setMargin(new Insets(10, 10, 10, 10));
        accountInformation.addActionListener(e -> {
            new Account(username);
        });
        panel.add(accountInformation);

        // Notifications Button
        JButton notifications= new JButton("Notifications");
        notifications.setFocusable(false);//the box around the button is removed
        notifications.setBounds( 20, 140, 400, 40);
        notifications.setFont(new Font("Arial", Font.PLAIN, 16));
        notifications.setMargin(new Insets(10, 10, 10, 10));
        notifications.addActionListener(e -> {
            new notifications();
        });
        panel.add(notifications);


        // Payment Method Button
        JButton paymentMethod= new JButton("Payment Method");
        paymentMethod.setFocusable(false);//the box around the button is removed
        paymentMethod.setBounds( 20, 230, 400, 40);
        paymentMethod.setFont(new Font("Arial", Font.PLAIN, 16));
        paymentMethod.setMargin(new Insets(10, 10, 10, 10));
        paymentMethod.addActionListener(e -> {
            new paymentMethod();
        });
        panel.add(paymentMethod);


        // Theme Button
        JButton theme= new JButton("Theme");
        theme.setFocusable(false);//the box around the button is removed
        theme.setBounds( 20, 320, 400, 40);
        theme.setFont(new Font("Arial", Font.PLAIN, 16));
        theme.setMargin(new Insets(10, 10, 10, 10));
        theme.addActionListener(e -> {
            new theme();
        });
        panel.add(theme);
        frame.setVisible(true);
    }
}

