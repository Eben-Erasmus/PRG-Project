import ui.AdminUI;
import ui.SellerUI;
import ui.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Login
{
    public Login()
    {
        JFrame frame = new JFrame("My GUI");
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

        JLabel success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(110, 80, 90, 25);
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String username = usernameText.getText();
                String password = passwordText.getText();
                String hashedPassword = hashPassword(password);

                if (doesUsernameExist(username))
                {
                    if (hashedPassword.equals(getPassword(username)))
                    {
                        if (getRole(username).equals("admin")) {
                            new AdminUI(username);
                            frame.dispose();
                        }
                        else if (getRole(username).equals("seller"))
                        {
                            success.setText("Login successful");
                            new SellerUI(username);
                            frame.dispose();
                        }
                        else if (getRole(username).equals("customer"))
                        {
                            success.setText("Login successful");
                            new UI(username);
                            frame.dispose();
                        }
                        else
                        {
                            success.setText("ui.buttons.Account Banned!");
                        }
                    }
                    else
                    {
                        success.setText("Password is incorrect");
                    }
                }
                else
                {
                    success.setText("Username does not exist");
                }
            }
        });
        panel.add(loginButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(110, 110, 90, 25);
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
            return false;
        }
        return exists;
    }

    public String getPassword(String username)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";
        String query = "SELECT password FROM user WHERE username = ?";
        String storedHashedPassword = "";

        try
        {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                storedHashedPassword = rs.getString("Password");
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return storedHashedPassword;
    }

    public String getRole(String username)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";
        String query = "SELECT role FROM user WHERE username = ?";
        String role = "";

        try
        {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                role = rs.getString("Role");
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return role;
    }

    private String hashPassword(String password)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
