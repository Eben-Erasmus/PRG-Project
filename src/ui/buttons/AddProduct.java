package ui.buttons;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddProduct
{
    private File selectedImageFile;

    public AddProduct()
    {
        JFrame frame = new JFrame("My GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 200));

        Border blackLine = BorderFactory.createLineBorder(Color.pink);
        panel.setBorder(blackLine);

        panel.setLayout(null);
        frame.add(panel);

        JLabel NameLabel = new JLabel("Product Name:");
        NameLabel.setBounds(10, 20, 130, 25);
        panel.add(NameLabel);

        JTextField NameText = new JTextField(20);
        NameText.setBounds(140, 20, 165, 25);
        panel.add(NameText);

        JLabel DescriptionLabel = new JLabel("Description:");
        DescriptionLabel.setBounds(10, 50, 130, 25);
        panel.add(DescriptionLabel);

        JTextField DescriptionText = new JTextField(20);
        DescriptionText.setBounds(140, 50, 165, 25);
        panel.add(DescriptionText);

        JLabel PriceLabel = new JLabel("Price:");
        PriceLabel.setBounds(10, 80, 130, 25);
        panel.add(PriceLabel);

        JTextField PriceText = new JTextField(20);
        PriceText.setBounds(140, 80, 165, 25);
        panel.add(PriceText);

        JButton selectImageButton = new JButton("Select Image");
        selectImageButton.setBounds(100, 110, 165, 25);
        selectImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                System.out.println("Selected image: " + selectedImageFile.getAbsolutePath());
            }
        });
        panel.add(selectImageButton);

        JButton addProductButton = new JButton("Add Product");
        addProductButton.setBounds(100, 140, 165, 25);
        addProductButton.addActionListener(e -> {
            String product_name = NameText.getText();
            String description = DescriptionText.getText();
            double price = Double.parseDouble(PriceText.getText());

            if (selectedImageFile != null) {
                addProduct(product_name, description, price, selectedImageFile);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an image.");
            }
        });
        panel.add(addProductButton);

        frame.setVisible(true);
    }

    public void addProduct(String product_name, String description, double price, File imageFile) {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String insertSQL = "INSERT INTO product (product_name, description, price, photo) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            pstmt.setString(1, product_name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);

            FileInputStream fis = new FileInputStream(imageFile);
            pstmt.setBinaryStream(4, fis, (int) imageFile.length());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Product added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
