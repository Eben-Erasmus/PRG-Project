import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

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
        LoginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String username = usernameText.getText();
                String password = passwordText.getText();
                String confirmPassword = confirmPasswordText.getText();

                if (doesUsernameExist(username))
                    System.out.println("Username exists!");

                if (username.equals("admin") && password.equals("admin") && confirmPassword.equals("admin")) {
                    System.out.println("Sign Up successful");

                    new Login();
                    frame.dispose();
                } else
                    System.out.println("Sign Up failed");
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

    public boolean doesUsernameExist(String username)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";

        try
        {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                exists = rs.getInt(1) > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false; // Handle exception and return false assuming non-existent username
        }
        return exists;
    }

    public boolean checkEmail(String email)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/user";
        String user = "intellij";
        String password = "";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try
        {
            // Connect to the database
            conn = DriverManager.getConnection(url, user, password);

            // Prepare a SQL statement to check username existence
            String query = "SELECT COUNT(*) FROM users WHERE email = ?";
            stmt = conn.prepareStatement(query);

            // Set the username parameter
            stmt.setString(1, email);

            // Execute the query and get the result set
            rs = stmt.executeQuery();

            // Check if there are any results (meaning username exists)
            if (rs.next() && rs.getInt(1) > 0)
            {
                return true; // Username exists
            } else
            {
                return false; // Username doesn't exist
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false; // Handle exception and return false assuming non-existent username
        } finally
        {
            // Close resources in a finally block to ensure proper cleanup
            try
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
