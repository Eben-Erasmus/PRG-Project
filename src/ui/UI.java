package ui;

import ui.buttons.Account;
import ui.buttons.Cart;
import ui.buttons.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UI {

    public UI(String username) {
//        super("My GUI");
        JFrame ui = new JFrame("My GUI");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setSize(760, 690);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        ui.add(panel);

        JLabel userLabel = new JLabel("Logged in as: " + username);
        userLabel.setBounds(10, 40, 165, 25);
        panel.add(userLabel);

        JLabel roleLabel = new JLabel("Normal");
        roleLabel.setBounds(10, 60, 165, 25);
        panel.add(roleLabel);

        JTextField searchBar = new JTextField(20);
        searchBar.setBounds(10, 10, 165, 25);
        panel.add(searchBar);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(180, 10, 120, 25);
        searchButton.addActionListener(e -> System.out.println("Search: " + searchBar.getText()));
        panel.add(searchButton);

        JButton accountButton = new JButton("Account");
        accountButton.setBounds(305, 10, 120, 25);
        accountButton.addActionListener(e ->
        {
            new Account(username);
        });
        panel.add(accountButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setBounds(430, 10, 120, 25);
        settingsButton.addActionListener(e -> {
            new Settings();
        });
        panel.add(settingsButton);

        JButton cartButton = new JButton("Cart");
        cartButton.setBounds(555, 10, 120, 25);
        cartButton.addActionListener(e -> {
            new Cart();
        });
        panel.add(cartButton);

/*        searchBar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchButton.doClick();
                }
            }
        });*/

/*        JPanel panelWithItems = new JPanel();
        panelWithItems.setBounds(10, 100, 680, 200);
        panelWithItems.setLayout(null);
        ImageIcon image = DisplayImage();
        JLabel Picture = new JLabel();
        Picture.setIcon(image);
        Picture.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        panelWithItems.add(Picture);
        panel.add(panelWithItems);*/

        /*JPanel panelWithItems = new JPanel();
        panelWithItems.setBounds(10, 100, 680, 200);
        panelWithItems.setLayout(new BoxLayout(panelWithItems, BoxLayout.Y_AXIS));
        panel.add(panelWithItems);*/

//        JPanel ItemPanel = new JPanel();

       /* JScrollPane scrollPane = new JScrollPane(panel2);
        scrollPane.setBounds(10, 100, 680, 200);
        panel.add(scrollPane);*/


/*        JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
        scrollBar.setBounds(690, 100, 17, 200);
        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                panelWithItems.setLocation(panelWithItems.getX(), -e.getValue());
            }
        });
        panel.add(scrollBar);*/

/*        ImageIcon image = DisplayImage();
        if (image != null) {
            JLabel picture = new JLabel();
            picture.setIcon(image);
            picture.setBounds(10, 320, image.getIconWidth(), image.getIconHeight());
            panel.add(picture);
        } else {
            System.out.println("Image could not be loaded.");
        }*/
        JPanel panelWithItems = new JPanel();
        panelWithItems.setLayout(new GridLayout(0, 2, 10, 10));

        // Wrap panelWithItems in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(panelWithItems);
        scrollPane.setBounds(10, 100, 740, 550);
        panel.add(scrollPane);

        loadProducts(panelWithItems, null);

        searchButton.addActionListener(e -> {
            String searchTerm = searchBar.getText();
            loadProducts(panelWithItems, searchTerm);
        });

        searchBar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchButton.doClick();
                }
            }
        });

        ui.setVisible(true);
    }


    public ImageIcon DisplayImage() {
        try {
            BufferedImage img = ImageIO.read(new File("/home/eben/Pictures/Screenshots/Screenshot from 2024-10-01 23-00-12.png"));
            ImageIcon icon = new ImageIcon(img);

            return icon;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadProducts(JPanel panelWithItems, String query) {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        // Clear previous items
        panelWithItems.removeAll();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String selectSQL = "SELECT product_name, description, price, photo FROM product";

            // Modify SQL query if there is a search term
            if (query != null && !query.trim().isEmpty()) {
                selectSQL += " WHERE product_name LIKE ?";
            }

            PreparedStatement pstmt = conn.prepareStatement(selectSQL);

            // Set the search parameter if there is a query
            if (query != null && !query.trim().isEmpty()) {
                pstmt.setString(1, "%" + query + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("product_name");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                byte[] imgBytes = rs.getBytes("photo");

                // Create a panel for each product
                JPanel productPanel = new JPanel();
                productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS)); // Vertical layout within each product

                // Add image
                if (imgBytes != null) {
                    ImageIcon imageIcon = new ImageIcon(imgBytes);
                    JLabel imageLabel = new JLabel(imageIcon);
                    productPanel.add(imageLabel);
                }

                // Add product details
                JLabel productLabel = new JLabel(productName + " - " + description + " - $" + price);
                productPanel.add(productLabel);

                // Add quantity label and spinner
                JLabel quantityLabel = new JLabel("Quantity:");
                productPanel.add(quantityLabel);

                JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // default 1, min 1, max 100
                productPanel.add(quantitySpinner);

                // Add "Add to Cart" button
                JButton addToCartButton = new JButton("Add to Cart");
                addToCartButton.addActionListener(e -> {
                    int quantity = (int) quantitySpinner.getValue();
                    addToCart(productName, quantity, description, price);
                    System.out.println(productName + " added to cart with quantity: " + quantity);
                });
                productPanel.add(addToCartButton);

                // Add product panel to the main panel
                panelWithItems.add(productPanel);

                System.out.println("Loaded product: " + productName);
            }

            // Force the panel to refresh after adding products
            panelWithItems.revalidate();
            panelWithItems.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Test() {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        try (
                Connection conn = DriverManager.getConnection(url, user, password)) {
            String selectSQL = "SELECT photo FROM Product WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(selectSQL);
            pstmt.setInt(1, 1);  // Replace 1 with the desired user id

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("photo");
                // Save the image back to a file
                FileOutputStream fos = new FileOutputStream("retrieved_image.jpg");
                fos.write(imgBytes);
                fos.close();

                System.out.println("Image retrieved and saved successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart(String item_name, int quantity, String description, double price) {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String insertSQL = "INSERT INTO cart (item_name, quantity, description, price, photo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            // Set name
            pstmt.setString(1, item_name);

            // Set price
            pstmt.setInt(2, quantity);

            // Set description
            pstmt.setString(3, description);

            // Set price
            pstmt.setDouble(4, price);


            // Set photo as BLOB
            File image = new File("/home/eben/Pictures/Screenshots/Screenshot from 2024-10-01 23-00-12.png");
            FileInputStream fis = new FileInputStream(image);
            pstmt.setBinaryStream(5, fis, (int) image.length());

            // Execute the insert statement
            pstmt.executeUpdate();
            System.out.println("Image inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
