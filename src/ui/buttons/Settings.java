package ui.buttons;


import javax.swing.*;
import java.awt.*;

public class Settings {

    private JFrame frame;
    private JPanel panel;
    private JButton button1, button2, button3, button4, button5;

    public Settings() {
        settingsButtons();
    }

    private void settingsButtons() {
        frame = new JFrame();
        frame.setTitle("Settings");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        //setResizable(false); This ensures that the window cannot be resized
        panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);
        frame.setLayout(new BorderLayout(10,10));

        panel.setPreferredSize(new Dimension(800, 250));

        button1 = new JButton("Account Information");
        button1.setFocusable(false);//the box around the button is removed
        button1.setIconTextGap(10);
        button1.setFont(new Font("Arial", Font.PLAIN, 16));
        button1.setMargin(new Insets(10, 10, 10, 10));
        button1.setPreferredSize(new Dimension(200, 100));


        button2 = new JButton("Notifications");
        button2.setFocusable(false);//the box around the button is removed
        button2.setIconTextGap(10);
        button2.setFont(new Font("Arial", Font.PLAIN, 16));
        button2.setMargin(new Insets(10, 10, 10, 10));
        button2.setPreferredSize(new Dimension(200, 100));

        button3 = new JButton("Payment Method");
        button3.setFocusable(false);//the box around the button is removed
        button3.setIconTextGap(10);
        button3.setFont(new Font("Arial", Font.PLAIN, 16));
        button3.setMargin(new Insets(10, 10, 10, 10));
        button3.setPreferredSize(new Dimension(200, 100));


        button4 = new JButton("Wishlist");
        button4.setFocusable(false);//the box around the button is removed
        button4.setIconTextGap(10);
        button4.setFont(new Font("Arial", Font.PLAIN, 16));
        button4.setMargin(new Insets(10, 10, 10, 10));
        button4.setPreferredSize(new Dimension(200, 100));


        button5 = new JButton("Theme");
        button5.setFocusable(false);//the box around the button is removed
        button5.setIconTextGap(10);
        button5.setFont(new Font("Arial", Font.PLAIN, 16));
        button5.setMargin(new Insets(10, 10, 10, 10));
        button5.setPreferredSize(new Dimension(200, 100));

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}

