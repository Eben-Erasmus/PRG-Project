import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main
{
    public static void main(String[] args) {
        new Login();

        /*String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        int cart_id = 3;
        String item_name = "test3";
        int quantity = 9;
        String description = "test3 product description";
        double price = 70.00;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String insertSQL = "INSERT INTO cart (cart_id, item_name, quantity, description, price, photo) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            // Set id
            pstmt.setInt(1, cart_id);

            // Set name
            pstmt.setString(2, item_name);

            // Set price
            pstmt.setInt(3, quantity);

            // Set description
            pstmt.setString(4, description);

            // Set price
            pstmt.setDouble(5, price);


            // Set photo as BLOB
            File image = new File("/home/eben/Pictures/Screenshots/Screenshot from 2024-10-01 23-00-12.png");
            FileInputStream fis = new FileInputStream(image);
            pstmt.setBinaryStream(6, fis, (int) image.length());

            // Execute the insert statement
            pstmt.executeUpdate();
            System.out.println("Image inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";

        int product_id = 16;
        String product_name = "test16";
        String description = "test2 product description";
        double price = 70.00;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String insertSQL = "INSERT INTO product (product_id, product_name, description, price, photo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            // Set id
            pstmt.setInt(1, product_id);

            // Set name
            pstmt.setString(2, product_name);

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