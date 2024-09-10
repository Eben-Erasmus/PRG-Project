import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login
{
    public Login()
    {
        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        //       panel.setBounds(110, 50, 250, 150);
        panel.setPreferredSize(new Dimension(300, 200));

        Border blackLine = BorderFactory.createLineBorder(Color.pink);
        panel.setBorder(blackLine);

        panel.setLayout(null);
        frame.add(panel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 20, 80, 25);
        panel.add(usernameLabel);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(100, 20, 165, 25);
        panel.add(usernameText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(110, 80, 80, 25);
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String username = usernameText.getText();
                String password = passwordText.getText();

                if (username.equals("admin") && password.equals("admin"))
                {
//                    success.setText("Login successful");
//                    UI ui = new UI();
//                    ui.setVisible(true);
                    new UI();
                    frame.dispose();
                }
                else
                {
                    success.setText("Login failed");
                }
            }
        });
        panel.add(loginButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(110, 110, 80, 25);
        signUpButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new SignUp();
                frame.dispose();
            }
        });
        panel.add(signUpButton);

        usernameText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    System.out.println(usernameText.getText());
                    passwordText.requestFocus();
                }
            }
        });

        passwordText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    System.out.println(passwordText.getText());
                    loginButton.doClick();
                }
            }
        });

        frame.setVisible(true);
    }
/*        final int[] count = {0};
        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Number of clicks: 0");

        JButton button = new JButton("Click me");
        button.setBackground(Color.WHITE);
//        button.setSize(1000, 1000);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                count[0]++;
                label.setText("Number of clicks: " + count[0]);
            }
        });



        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);
        panel.setBounds(0, 0, 300, 300);
        panel.setSize(1000, 1000);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("My GUI");
        frame.pack();
        frame.setVisible(true);
//        frame.setSize(900, 900);
    }
*/
}
