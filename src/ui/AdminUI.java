package ui;

import ui.buttons.Account;
import ui.buttons.AddProduct;
import ui.buttons.Cart;
import ui.buttons.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminUI
{
    public AdminUI(String username)
    {
        JFrame ui = new JFrame("My GUI");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(760, 690);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        ui.add(panel);

        JLabel userLabel = new JLabel("Logged in as: " + username);
        userLabel.setBounds(10, 40, 165, 25);
        panel.add(userLabel);

        JLabel roleLabel = new JLabel("Admin");
        roleLabel.setBounds(10, 60, 165, 25);
        panel.add(roleLabel);

        JTextField searchBar = new JTextField(20);
        searchBar.setBounds(10, 10, 165, 25);
        panel.add(searchBar);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(180, 10, 120, 25);
        panel.add(searchButton);

        JButton accountButton = new JButton("Account");
        accountButton.setBounds(305, 10, 120, 25);
        accountButton.addActionListener(e -> {new Account(username);});
        panel.add(accountButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setBounds(430, 10, 120, 25);
        settingsButton.addActionListener(e -> {new Settings(username);});
        panel.add(settingsButton);

        JButton cartButton = new JButton("Cart");
        cartButton.setBounds(555, 10, 120, 25);
        cartButton.addActionListener(e -> {new Cart();});
        panel.add(cartButton);

        JButton addProductButton = new JButton("Add Product");
        addProductButton.setBounds(180, 50, 120, 25);
        addProductButton.addActionListener(e -> {new AddProduct();});
        panel.add(addProductButton);


        JPanel panelWithItems = new JPanel();
        panelWithItems.setLayout(new GridLayout(0, 2, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panelWithItems);
        scrollPane.setBounds(10, 100, 740, 550);
        panel.add(scrollPane);

        loadProducts(panelWithItems, null);

        searchButton.addActionListener(e ->
        {
            String searchTerm = searchBar.getText();
            loadProducts(panelWithItems, searchTerm);
        });

        searchBar.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    searchButton.doClick();
                }
            }
        });

        ui.setVisible(true);
    }

    private void loadProducts(JPanel panelWithItems, String query)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        panelWithItems.removeAll();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String selectSQL = "SELECT product_name, description, price, photo FROM product";

            if (query != null && !query.trim().isEmpty())
            {
                selectSQL += " WHERE product_name LIKE ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(selectSQL);

            if (query != null && !query.trim().isEmpty())
            {
                pstmt.setString(1, "%" + query + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                String productName = rs.getString("product_name");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                byte[] imgBytes = rs.getBytes("photo");

                JPanel productPanel = new JPanel();
                productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS)); // Vertical layout within each product

                if (imgBytes != null)
                {
                    ImageIcon imageIcon = new ImageIcon(imgBytes);
                    JLabel imageLabel = new JLabel(imageIcon);
                    productPanel.add(imageLabel);
                }

                JLabel productLabel = new JLabel(productName + " - " + description + " - $" + price);
                productPanel.add(productLabel);

                JLabel quantityLabel = new JLabel("Quantity:");
                productPanel.add(quantityLabel);

                JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
                productPanel.add(quantitySpinner);

                JButton addToCartButton = new JButton("Add to Cart");
                addToCartButton.addActionListener(e ->
                {
                    int quantity = (int) quantitySpinner.getValue();
                    addToCart(productName, quantity, description, price, imgBytes);
                    System.out.println(productName + " added to cart with quantity: " + quantity);
                });
                productPanel.add(addToCartButton);

                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(e ->
                {
                    removeProduct(productName);
                    loadProducts(panelWithItems, query);
                });
                productPanel.add(removeButton);

                panelWithItems.add(productPanel);

                System.out.println("Loaded product: " + productName);
            }

            panelWithItems.revalidate();
            panelWithItems.repaint();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void removeProduct(String productName)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String deleteSQL = "DELETE FROM product WHERE product_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteSQL);
            pstmt.setString(1, productName);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(productName + " removed from database.");
            } else {
                System.out.println("No product found with name: " + productName);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addToCart(String item_name, int quantity, String description, double price, byte[] imgBytes)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password))
        {
            String insertSQL = "INSERT INTO cart (item_name, quantity, description, price, photo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            pstmt.setString(1, item_name);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, description);
            pstmt.setDouble(4, price);

            if (imgBytes != null)
            {
                pstmt.setBytes(5, imgBytes); // Store the image bytes from the product
            }
            else
            {
                pstmt.setNull(5, java.sql.Types.BLOB); // In case there is no image
            }

            pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
