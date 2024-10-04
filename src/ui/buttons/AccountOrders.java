package ui.buttons;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountOrders {
    public AccountOrders() {
        JFrame ui = new JFrame("Orders");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        ui.add(panel);

        JLabel ordersLabel = new JLabel("Orders:");
        ordersLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(ordersLabel, BorderLayout.NORTH);

        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(ordersPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadOrders(ordersPanel);

        ui.setVisible(true);
    }

    private void loadOrders(JPanel ordersPanel) {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database"; // Adjust the URL, user, and password as necessary
        String user = "intellij";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String selectSQL = "SELECT item_name, quantity, description, total_price FROM orders";

            PreparedStatement pstmt = conn.prepareStatement(selectSQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                float totalPrice = rs.getFloat("total_price");

                JPanel orderItemPanel = new JPanel();
                orderItemPanel.setLayout(new BoxLayout(orderItemPanel, BoxLayout.Y_AXIS));

                JLabel itemLabel = new JLabel("Item: " + itemName);
                JLabel quantityLabel = new JLabel("Quantity: " + quantity);
                JLabel descriptionLabel = new JLabel("Description: " + description);
                JLabel totalPriceLabel = new JLabel("Total Price: $" + totalPrice);

                orderItemPanel.add(itemLabel);
                orderItemPanel.add(quantityLabel);
                orderItemPanel.add(descriptionLabel);
                orderItemPanel.add(totalPriceLabel);

                ordersPanel.add(orderItemPanel);
            }

            ordersPanel.revalidate();
            ordersPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}