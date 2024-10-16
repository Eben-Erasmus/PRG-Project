package ui.buttons;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Cart {
    // Database connection
    private Connection connect() {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Fetch items from the shopping cart
    private List<Object[]> fetchCartItems() {
        List<Object[]> items = new ArrayList<>();
        String query = "SELECT cart_id, item_name, quantity, description, price, photo FROM cart";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("cart_id");
                row[1] = rs.getString("item_name");
                row[2] = rs.getInt("quantity");
                row[3] = rs.getString("description");
                row[4] = rs.getFloat("price");

                InputStream is = rs.getBinaryStream("photo");
                if (is != null) {
                    BufferedImage image = ImageIO.read(is);
                    ImageIcon imageIcon = new ImageIcon(image);
                    row[5] = imageIcon;
                } else {
                    row[5] = null;
                }

                row[6] = "Remove";
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

    // Remove an item from the cart
    private void removeFromCart(int cartId) {
        String deleteQuery = "DELETE FROM cart WHERE cart_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, cartId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Item removed from cart.");
            } else {
                JOptionPane.showMessageDialog(null, "Item not found in cart.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error removing item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Insert order details into the database
    private void insertOrder(String cardNumber, String expDate, String cvv, String address, float total) {
        String insertQuery = "INSERT INTO orders (card_number, expire_date, cvv, address, total_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cardNumber); // Changed to setString for VARCHAR
            pstmt.setString(2, expDate);
            pstmt.setString(3, cvv);
            pstmt.setString(4, address);
            pstmt.setFloat(5, total);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertOrderItems(); // Save individual items to the orders table
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Insert order items associated with the order
    private void insertOrderItems() {
        List<Object[]> items = fetchCartItems();
        String insertItemsQuery = "INSERT INTO orders (item_name, quantity, description) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertItemsQuery)) {
            for (Object[] item : items) {
                pstmt.setString(1, (String) item[1]); // item_name
                pstmt.setInt(2, (int) item[2]); // quantity
                pstmt.setString(3, (String) item[3]); // description
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting order items: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void clearCart() {
        String deleteQuery = "DELETE FROM cart";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(deleteQuery);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cart has been cleared.");
            } else {
                JOptionPane.showMessageDialog(null, "Cart is already empty.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error clearing cart: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Checkout items in the cart
    private void checkout() {
        List<Object[]> items = fetchCartItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Your cart is empty. Cannot proceed to checkout.");
            return;
        }

        // Calculate total amount
        float total = 0;
        for (Object[] item : items) {
            total += (float) item[4] * (int) item[2]; // price * quantity
        }



        // Show card details and delivery address dialog
        JPanel checkoutPanel = new JPanel(new GridLayout(0, 1));

        JTextField cardNumberField = new JTextField();
        JTextField expDateField = new JTextField();
        JTextField cvvField = new JTextField();
        JTextField addressField = new JTextField();

        checkoutPanel.add(new JLabel("Card Number:"));
        checkoutPanel.add(cardNumberField);
        checkoutPanel.add(new JLabel("Expiration Date (MM/YY):"));
        checkoutPanel.add(expDateField);
        checkoutPanel.add(new JLabel("CVV:"));
        checkoutPanel.add(cvvField);
        checkoutPanel.add(new JLabel("Delivery Address:"));
        checkoutPanel.add(addressField);

        int option = JOptionPane.showConfirmDialog(null, checkoutPanel, "Enter Card Details and Delivery Address", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String cardNumber = cardNumberField.getText();
            String expDate = expDateField.getText();
            String cvv = cvvField.getText();
            String address = addressField.getText();

            // Insert order into the database
            insertOrder(cardNumber, expDate, cvv, address, total);
            JOptionPane.showMessageDialog(null, "Total amount due: $" + total + "\nPurchase confirmed!\nDelivery Address: " + address);

        }
    }



    // Refresh the cart
    private void refreshCart(DefaultTableModel model) {
        List<Object[]> updatedItems = fetchCartItems();
        model.setRowCount(0);
        if (updatedItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items in the cart.");
        } else {
            for (Object[] row : updatedItems) {
                model.addRow(row);
            }
        }
    }


    // Constructor
    public Cart() {
        JFrame frame = new JFrame("Cart");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        List<Object[]> cartItems = fetchCartItems();

        String[] columnNames = {"Cart ID", "Item Name", "Quantity", "Description", "Price", "Photo", "Remove"};

        final DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 5) return ImageIcon.class;
                if (column == 6) return String.class;
                return Object.class;
            }
        };

        for (Object[] row : cartItems) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(100);

        table.getColumn("Remove").setCellRenderer(new ButtonRenderer());
        table.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox(), model));

        JScrollPane tableScrollPane = new JScrollPane(table);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Cart");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCart(model);
            }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
                clearCart();
                refreshCart(model);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(checkoutButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cart());
    }

    // Custom Button Renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Remove" : value.toString());
            return this;
        }
    }

    // Custom Button Editor
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private DefaultTableModel model;
        private JTable table;
        private int row;

        public ButtonEditor(JCheckBox checkBox, DefaultTableModel model) {
            super(checkBox);
            this.model = model;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            label = (value == null) ? "Remove" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if (model.getRowCount() > 0 && row >= 0 && row < model.getRowCount()) {
                    int cartId = (int) model.getValueAt(row, 0);
                    removeFromCart(cartId);
                    refreshCart(model);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid row selected or no items in the cart.");
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
