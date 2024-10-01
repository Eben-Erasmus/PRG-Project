package ui.buttons;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.*;

public class Account {
    public Account() {
        String username = "Thunder075";

        //Opening window
        JFrame ui = new JFrame("My GUI");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(300, 200);

        //Container for the Jframe
        JPanel panel = new JPanel();
        panel.setLayout(null);
        ui.add(panel);

        //Jlabel for  Account Details
        JLabel accountDetailsLabel = new JLabel();
        accountDetailsLabel.setBounds(10, 10, 100, 25);
        accountDetailsLabel.setText("Account Details:");
        panel.add(accountDetailsLabel);
        //Jlabel for  Account Details

        //Jlabel for  First Name
        JLabel first_namerLabel = new JLabel();
        first_namerLabel.setBounds(10, 40, 80, 25);
        first_namerLabel.setText("First_Name:");
        panel.add(first_namerLabel);

        String first_name = getFirstName(username);
        JLabel first_nameLabel = new JLabel();
        first_nameLabel.setBounds(80, 40, 150, 25);
        first_nameLabel.setText(first_name);
        panel.add(first_nameLabel);

        Border FirstLine = BorderFactory.createLineBorder(Color.black);
        first_nameLabel.setBorder(FirstLine);
        //Jlabel for  First Name

        //Jlabel for Last Name
        JLabel last_namerLabel = new JLabel();
        last_namerLabel.setBounds(10, 75, 80, 25);
        last_namerLabel.setText("Last_Name:");
        panel.add(last_namerLabel);

        String last_name = getLastName(username);
        JLabel last_nameLabel = new JLabel();
        last_nameLabel.setBounds(80, 75, 150, 25);
        last_nameLabel.setText(last_name);
        panel.add(last_nameLabel);

        Border LastLine = BorderFactory.createLineBorder(Color.black);
        last_nameLabel.setBorder(LastLine);
        //Jlabel for Last Name

        //Jlabel for  Email
        JLabel emailrLabel = new JLabel();
        emailrLabel.setBounds(10, 110, 80, 25);
        emailrLabel.setText("Email:");
        panel.add(emailrLabel);

        String email = getEmail(username);
        JLabel emailLabel = new JLabel();
        emailLabel.setBounds(80, 110, 150, 25);
        emailLabel.setText(email);
        panel.add(emailLabel);

        Border EmailLine = BorderFactory.createLineBorder(Color.black);
        emailLabel.setBorder(EmailLine);
        //Jlabel for  Email


        //ChangePassword Button
        JButton ChangePassword = new JButton("Change Password");
        ChangePassword.setBounds(10, 150, 200, 30);
        ChangePassword.addActionListener(e ->
        {
            new ChangePassword();
        });
        panel.add(ChangePassword);
        //ChangePassword Button

        //Orders Button
        JButton Orders = new JButton("Orders");
        Orders.setBounds(10, 190, 200, 30);
        Orders.addActionListener(e ->
        {
            new AccountOrders();
        });
        panel.add(Orders);
        //Orders Button


        //Makes UI visible
        ui.setVisible(true);
    }

    //Method to get First Name from database
    public static String getFirstName(String username) {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";
        String query = "SELECT first_name FROM user WHERE username = ?";
        String first_name = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                first_name = rs.getString("first_name");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return first_name;
    }
    //Method to get First Name from database

    //Method to get Last Name from database
    public static String getLastName(String username)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";
        String query = "SELECT last_name FROM user WHERE username = ?";
        String last_name = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                last_name = rs.getString("last_name");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return last_name;
    }
    //Method to get Last Name from database

    //Method to get Email from database
    public static String getEmail(String username)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";
        String query = "SELECT email FROM user WHERE username = ?";
        String email = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                email = rs.getString("email");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return email;
    }
    //Method to get Email from database
}