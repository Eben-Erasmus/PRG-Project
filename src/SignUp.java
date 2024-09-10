import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SignUp
{
    public SignUp()
    {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
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

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(10, 80, 80, 25);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setBounds(100, 80, 165, 25);
        panel.add(confirmPasswordText);

        JButton LoginButton = new JButton("Sign Up");
        LoginButton.setBounds(10, 110, 80, 25);
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                String password = passwordText.getText();
                String confirmPassword = confirmPasswordText.getText();

                if (username.equals("admin") && password.equals("admin") && confirmPassword.equals("admin")) {
                    System.out.println("Sign Up successful");

                    new Login();
                    frame.dispose();
                } else {
                    System.out.println("Sign Up failed");
                }
            }
        });
        panel.add(LoginButton);

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
                    confirmPasswordText.requestFocus();
                }
            }
        });

        confirmPasswordText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    System.out.println(passwordText.getText());
                    LoginButton.doClick();
                }
            }
        });

        frame.setVisible(true);
    }
}
