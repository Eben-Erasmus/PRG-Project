package ui.buttons;
import javax.swing.*;
import java.sql.*;

public class Account {
    public static void main(String[] args) {
        String username = "Thunder075";

        //Opening window//
        JFrame ui = new JFrame("My GUI");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(300, 200);

        //Container for the Jframe//
        JPanel panel = new JPanel();
        panel.setLayout(null);
        ui.add(panel);

        //Jlabel for  First Name//
        JLabel first_namerLabel = new JLabel();
        first_namerLabel.setBounds(10, 20, 80, 25);
        first_namerLabel.setText("First_Name:");
        panel.add(first_namerLabel);

        String first_name = getFirstName(username);
        JLabel first_nameLabel = new JLabel();
        first_nameLabel.setBounds(80, 20, 80, 25);
        first_nameLabel.setText(first_name);
        panel.add(first_nameLabel);
        //Jlabel for  First Name//

        //Jlabel for Last Name//
        JLabel last_namerLabel = new JLabel();
        last_namerLabel.setBounds(10, 40, 80, 25);
        last_namerLabel.setText("Last_Name:");
        panel.add(last_namerLabel);

        String last_name = getLastName(username);
        JLabel last_nameLabel = new JLabel();
        last_nameLabel.setBounds(80, 40, 80, 25);
        last_nameLabel.setText(last_name);
        panel.add(last_nameLabel);
        //Jlabel for Last Name//

        //Jlabel for  Email//
        JLabel emailrLabel = new JLabel();
        emailrLabel.setBounds(10, 60, 80, 25);
        emailrLabel.setText("Email:");
        panel.add(emailrLabel);

        String email = getEmail(username);
        JLabel emailLabel = new JLabel();
        emailLabel.setBounds(80, 60, 80, 25);
        emailLabel.setText(email);
        panel.add(emailLabel);
        //Jlabel for  Email//


        //AccSecurity Button//
        JButton AccSecurity = new JButton("Security");
        AccSecurity.setBounds(100, 120, 100, 30);
        AccSecurity.addActionListener(e ->
        {
            System.out.println("Hi");
        });
        panel.add(AccSecurity);
        //AccSecurity Button//

        //Orders Button//
        JButton Orders = new JButton("Orders");
        Orders.setBounds(100, 160, 100, 30);
        Orders.addActionListener(e ->
        {
            System.out.println("Hi");
        });
        panel.add(Orders);
        //Orders Button//


        //Makes UI visible//
        ui.setVisible(true);
    }

    //Method to get First Name from database//
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
    //Method to get First Name from database//

    //Method to get Last Name from database//
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
    //Method to get Last Name from database//

    //Method to get Email from database//
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
    //Method to get Email from database//
}