package ui.buttons;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ChangePassword {
    public ChangePassword(String username) {
        //Opening window
        JFrame ui = new JFrame("Change Password");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(800, 600);

        //Container for the JFrame
        JPanel panel = new JPanel();
        panel.setLayout(null);
        ui.add(panel);

        //JLabel for Account Details
        JLabel accountDetailsLabel = new JLabel("Change Password:");
        accountDetailsLabel.setBounds(10, 10, 200, 25);
        panel.add(accountDetailsLabel);

        //New Password JLabel and JField
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(10, 50, 100, 25);
        panel.add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(120, 50, 150, 25);
        panel.add(newPasswordField);

        //Confirm Password JLabel and JField
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(10, 90, 120, 25);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(120, 90, 150, 25);
        panel.add(confirmPasswordField);

        //Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(10, 130, 260, 30);
        submitButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            //Update the password
            if (updatePassword(username, newPassword, confirmPassword)) {
                JOptionPane.showMessageDialog(ui, "Password updated successfully.");
            } else {
                JOptionPane.showMessageDialog(ui, "Password update failed. Please try again.");
            }
        });
        panel.add(submitButton);

        //Make UI visible
        ui.setVisible(true);
    }

    //Method to update the password in the database
    public static boolean updatePassword(String username, String newPassword, String confirmPassword) {
        // Check if the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!");
            return false;
        }

        //Hash the new password
        String hashedPassword = hashPassword(newPassword);
        if (hashedPassword == null) {
            JOptionPane.showMessageDialog(null, "Failed to hash the password.");
            return false;
        }

        //Update the password in the database
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String dbPassword = ""; // Database password
        String query = "UPDATE user SET password = ? WHERE username = ?;";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            //Set the hashed password and username
            stmt.setString(1, hashedPassword);
            stmt.setString(2, username);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update the password. User not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error. Please try again.");
            return false;
        }
    }

    // Method to hash the password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}