package ui;

import ui.buttons.Account;
import ui.buttons.Cart;
import ui.buttons.Settings;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UI
{
    String username;

    public UI(String username)
    {
//        super("My GUI");
        JFrame ui = new JFrame("My GUI");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        ui.add(panel);

        JLabel userLabel = new JLabel("Logged in as: " + username);
        userLabel.setBounds(10, 40, 165, 25);
        panel.add(userLabel);

        JLabel roleLabel = new JLabel("Normal");
        roleLabel.setBounds(10, 60, 165, 25);
        panel.add(roleLabel);

        JTextField searchBar = new JTextField(20);
        searchBar.setBounds(10, 10, 165, 25);
        panel.add(searchBar);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(180, 10, 120, 25);
        searchButton.addActionListener(e -> System.out.println("Search: " + searchBar.getText()));
        panel.add(searchButton);

        JButton accountButton = new JButton("Account");
        accountButton.setBounds(305, 10, 120, 25);
        accountButton.addActionListener(e -> {
            new Account(username);}
        );
        panel.add(accountButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setBounds(430, 10, 120, 25);
        settingsButton.addActionListener(e -> {new Settings();});
        panel.add(settingsButton);

        JButton cartButton = new JButton("Cart");
        cartButton.setBounds(555, 10, 120, 25);
        cartButton.addActionListener(e -> {
            new Cart();
        });
        panel.add(cartButton);

        searchBar.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    searchButton.doClick();
                }
            }
        });

        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setBounds(680, 10, 10, 180);
        panel.add(scrollBar);

        ui.setVisible(true);
    }
}
