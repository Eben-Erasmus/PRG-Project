package ui.buttons;

import javax.swing.*;
import java.awt.*;

public class Settings {

    private JPanel panel;
    private JButton accountInformation, notifications, paymentMethod, wishlist, theme;

    public Settings() {

        // Account Information Button
        JButton accountInformation= new JButton("Account Information");
        accountInformation.setFocusable(false);//the box around the button is removed
        accountInformation.setBounds( 20, 200, 400, 40);
        accountInformation.setFont(new Font("Arial", Font.PLAIN, 16));
        accountInformation.setMargin(new Insets(10, 10, 10, 10));
        accountInformation.addActionListener(e -> {
            new accountInformation();
        });
        panel.add(accountInformation);

        // Notifications Button
        JButton notifications= new JButton("Notifications");
        notifications.setFocusable(false);//the box around the button is removed
        notifications.setBounds( 20, 200, 400, 40);
        notifications.setFont(new Font("Arial", Font.PLAIN, 16));
        notifications.setMargin(new Insets(10, 10, 10, 10));
        notifications.addActionListener(e -> {
            new notifications();
        });
        panel.add(notifications);


        // Payment Method Button
        JButton paymentMethod= new JButton("Account Information");
        paymentMethod.setFocusable(false);//the box around the button is removed
        paymentMethod.setBounds( 20, 200, 400, 40);
        paymentMethod.setFont(new Font("Arial", Font.PLAIN, 16));
        paymentMethod.setMargin(new Insets(10, 10, 10, 10));
        paymentMethod.addActionListener(e -> {
            new paymentMethod();
        });
        panel.add(paymentMethod);

        // Wishlist Button
        JButton wishlist= new JButton("Account Information");
        wishlist.setFocusable(false);//the box around the button is removed
        wishlist.setBounds( 20, 200, 400, 40);
        wishlist.setFont(new Font("Arial", Font.PLAIN, 16));
        wishlist.setMargin(new Insets(10, 10, 10, 10));
        wishlist.addActionListener(e -> {
            new wishlist();
        });
        panel.add(wishlist);

        // Theme Button
        JButton theme= new JButton("Account Information");
        theme.setFocusable(false);//the box around the button is removed
        theme.setBounds( 20, 200, 400, 40);
        theme.setFont(new Font("Arial", Font.PLAIN, 16));
        theme.setMargin(new Insets(10, 10, 10, 10));
        theme.addActionListener(e -> {
            new theme();
        });
        panel.add(theme);

    }

}
