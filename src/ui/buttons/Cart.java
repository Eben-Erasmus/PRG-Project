package ui.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Cart {
    private Connection connect() {
        // Database connection
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private List<Object[]> fetchCartItems() {
        List<Object[]> items = new ArrayList<>();
        String query = "SELECT cart_id, item_name, quantity, description, price, photo FROM cart";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("cart_id");
                row[1] = rs.getString("item_name");
                row[2] = rs.getInt("quantity");
                row[3] = rs.getString("description");
                row[4] = rs.getFloat("price");

                // Convert InputStream to ImageIcon
                InputStream is = rs.getBinaryStream("photo");
                if (is != null) {
                    BufferedImage image = ImageIO.read(is);
                    ImageIcon imageIcon = new ImageIcon(image);
                    row[5] = imageIcon;
                } else {
                    row[5] = null;
                }

                items.add(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error processing image: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public Cart() {

        JFrame frame = new JFrame("Cart");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Fetch items from the database
        List<Object[]> cartItems = fetchCartItems();


        String[] columnNames = {"Cart ID", "Item Name", "Quantity", "Description", "Price", "Photo"};

        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 5) return ImageIcon.class;
                return Object.class;
            }
        };

        // Add rows to the model
        for (Object[] row : cartItems) {
            model.addRow(row);
        }

        // Create a JTable to display the items
        JTable table = new JTable(model);
        table.setRowHeight(100);

        // Add a scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(table);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Add a button to refresh the cart
        JButton refreshButton = new JButton("Refresh Cart");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch updated items
                List<Object[]> updatedItems = fetchCartItems();
                // Clear the existing table data
                model.setRowCount(0);
                // Add updated rows
                for (Object[] row : updatedItems) {
                    model.addRow(row);
                }
            }
        });

        // Add components to the panel
        panel.add(refreshButton, BorderLayout.SOUTH);

        // Add the panel to the frame
        frame.add(panel);

        // Set the frame visibility to true
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cart());
    }
}
