import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class SignUp
{
    public SignUp()
    {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 800));

        Border blackLine = BorderFactory.createLineBorder(Color.pink);
        panel.setBorder(blackLine);

        panel.setLayout(null);
        frame.add(panel);

        JLabel firstNameLabel = new JLabel("First Name *");
        firstNameLabel.setBounds(10, 20, 140, 25);
        panel.add(firstNameLabel);

        JTextField firstNameText = new JTextField(20);
        firstNameText.setBounds(170, 20, 165, 25);
        panel.add(firstNameText);

        JLabel lastNameLabel = new JLabel("last Name *");
        lastNameLabel.setBounds(10, 50, 140, 25);
        panel.add(lastNameLabel);

        JTextField lastNameText = new JTextField(20);
        lastNameText.setBounds(170, 50, 165, 25);
        panel.add(lastNameText);

        JLabel emailLabel = new JLabel("Email *");
        emailLabel.setBounds(10, 80, 140, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(20);
        emailText.setBounds(170, 80, 165, 25);
        panel.add(emailText);

        JLabel usernameLabel = new JLabel("Username *");
        usernameLabel.setBounds(10, 110, 140, 25);
        panel.add(usernameLabel);

        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(170, 110, 165, 25);
        panel.add(usernameText);

        JLabel passwordLabel = new JLabel("Password *");
        passwordLabel.setBounds(10, 140, 140, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(170, 140, 165, 25);
        panel.add(passwordText);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password *");
        confirmPasswordLabel.setBounds(10, 170, 140, 25);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setBounds(170, 170, 165, 25);
        panel.add(confirmPasswordText);

        JLabel dateOfBirthLabel = new JLabel("Date of Birth *");
        dateOfBirthLabel.setBounds(10, 200, 140, 25);
        panel.add(dateOfBirthLabel);

        JTextField dateOfBirthText = new JTextField("YYYY-MM-DD", 20);
        dateOfBirthText.setBounds(170, 200, 165, 25);
        panel.add(dateOfBirthText);

        ((AbstractDocument) dateOfBirthText.getDocument()).setDocumentFilter(new DocumentFilter()
        {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
            {
                if (!string.matches("\\d") || offset >= 10)
                    return;
                String currentText = dateOfBirthText.getText();
                if (currentText.charAt(offset) == '-')
                    offset++;
                super.replace(fb, offset, 1, string, attr);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException
            {
                if (offset >= 10 || length != 1)
                    return;
                String currentText = dateOfBirthText.getText();
                if (currentText.charAt(offset) == '-')
                    offset--;
                char placeholder = getPlaceholderChar(offset);
                super.replace(fb, offset, 1, String.valueOf(placeholder), null);
                dateOfBirthText.setCaretPosition(offset);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
            {
                if (!text.matches("\\d") || offset >= 10)
                    return;
                String currentText = dateOfBirthText.getText();
                if (currentText.charAt(offset) == '-')
                    offset++;
                super.replace(fb, offset, 1, text, attrs);
            }
        });

        dateOfBirthText.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                int pos = dateOfBirthText.getCaretPosition();
                if (pos == 4 || pos == 7)
                    dateOfBirthText.setCaretPosition(pos + 1);
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    int pos = dateOfBirthText.getCaretPosition();
                    if (pos > 0)
                    {
                        if (dateOfBirthText.getText().charAt(pos - 1) == '-')
                            dateOfBirthText.setCaretPosition(pos - 1);
                    }
                }
            }
        });

        dateOfBirthText.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (dateOfBirthText.getText().equals("YYYY-MM-DD"))
                {
                    dateOfBirthText.setText("");
                    dateOfBirthText.setForeground(Color.BLACK);
                }
                dateOfBirthText.setCaretPosition(0);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (dateOfBirthText.getText().isEmpty())
                {
                    dateOfBirthText.setForeground(Color.GRAY);
                    dateOfBirthText.setText("YYYY-MM-DD");
                }
            }
        });

        JLabel genderLabel = new JLabel("Gender *");
        genderLabel.setBounds(10, 230, 140, 25);
        panel.add(genderLabel);

        JComboBox<Gender> genderComboBox = new JComboBox<>(Gender.values());
        genderComboBox.setBounds(170, 230, 165, 25);
        panel.add(genderComboBox);

        JLabel addressLabel = new JLabel("Address *");
        addressLabel.setBounds(10, 260, 140, 25);
        panel.add(addressLabel);

        JTextField addressText = new JTextField(20);
        addressText.setBounds(170, 260, 165, 25);
        panel.add(addressText);

        JLabel phoneNumberLabel = new JLabel("Phone Number *");
        phoneNumberLabel.setBounds(10, 290, 140, 25);
        panel.add(phoneNumberLabel);

        JTextField phoneNumberText = new JTextField("+264 8", 20);
        phoneNumberText.setBounds(170, 290, 165, 25);
        panel.add(phoneNumberText);

        ((AbstractDocument) phoneNumberText.getDocument()).setDocumentFilter(new DocumentFilter()
        {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
            {
                if (offset < 6 || !string.matches("\\d*") || (phoneNumberText.getText().length() + string.length() - 5) > 9)
                    return;
                super.insertString(fb, offset, string, attr);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException
            {
                if (offset < 6)
                    return;
                super.remove(fb, offset, length);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
            {
                if (offset < 6 || !text.matches("\\d*") || (phoneNumberText.getText().length() + text.length() - 5) > 9)
                    return;
                super.replace(fb, offset, length, text, attrs);
            }
        });

        JButton LoginButton = new JButton("Sign Up");
        LoginButton.setBounds(10, 320, 90, 25);
        LoginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String firstName = firstNameText.getText();
                String lastName = lastNameText.getText();
                String email = emailText.getText();
                String username = usernameText.getText();
                String password = passwordText.getText();
                String confirmPassword = confirmPasswordText.getText();
                String dateOfBirth = dateOfBirthText.getText();
                Gender gender = (Gender) genderComboBox.getSelectedItem();
                String address = addressText.getText();
                String phoneNumber = phoneNumberText.getText();
                String hashedPassword = hashPassword(password);

                if (firstName.isEmpty())
                    System.out.println("First Name is required!");
                else if (lastName.isEmpty())
                    System.out.println("Last Name is required!");
                else if (email.isEmpty())
                    System.out.println("Email is required!");
                else if (username.isEmpty())
                    System.out.println("Username is required!");
                else if (password.isEmpty())
                    System.out.println("Password is required!");
                else if (confirmPassword.isEmpty())
                    System.out.println("Confirm Password is required!");
                else if (dateOfBirth.equals("YYYY-MM-DD"))
                    System.out.println("Date of Birth is required!");
                else if (address.isEmpty())
                    System.out.println("Address is required!");
                else if (phoneNumber.equals("+264 8"))
                    System.out.println("Phone Number is required!");
                else if (doesUsernameExist(username))
                    System.out.println("Username exists!");
                else if (doesEmailExist(email))
                    System.out.println("Email exists!");
                else if (!password.equals(confirmPassword))
                    System.out.println("Passwords do not match!");
                else if (!isValidDate(dateOfBirth))
                    System.out.println("Invalid date of birth.");
                else if (phoneNumber.length() != 14)
                    System.out.println("Invalid phone number.");
                else
                {
                    String db_url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
                    String db_user = "intellij";
                    String db_password = "";
                    String query = "INSERT INTO user (first_name, last_name, email, username, password, date_of_birth, gender, address, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    try
                    {
                        Connection conn = DriverManager.getConnection(db_url, db_user, db_password);
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, firstName);
                        stmt.setString(2, lastName);
                        stmt.setString(3, email);
                        stmt.setString(4, username);
                        stmt.setString(5, hashedPassword);
                        stmt.setString(6, dateOfBirth);
                        stmt.setString(7, gender.toString());
                        stmt.setString(8, address);
                        stmt.setString(9, phoneNumber);

                        int rowsInserted = stmt.executeUpdate();
                        if (rowsInserted > 0)
                            System.out.println("A new user was inserted successfully!");
                    }
                    catch (SQLException ex)
                    {
                        ex.printStackTrace();
                    }

                    new Login();
                    frame.dispose();
                }
            }
        });
        panel.add(LoginButton);

        firstNameText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    lastNameText.requestFocus();
            }
        });

        lastNameText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    emailText.requestFocus();
            }
        });

        emailText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    usernameText.requestFocus();
            }
        });

        usernameText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    passwordText.requestFocus();
            }
        });

        passwordText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    confirmPasswordText.requestFocus();
            }
        });

        confirmPasswordText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    dateOfBirthText.requestFocus();
            }
        });

        dateOfBirthText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    genderComboBox.requestFocus();
            }
        });

        addressText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    phoneNumberText.requestFocus();
            }
        });

        phoneNumberText.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    LoginButton.doClick();
            }
        });

        frame.setVisible(true);
    }

    private char getPlaceholderChar(int offset)
    {
        if (offset < 4) return 'Y';
        if (offset < 7) return 'M';
        return 'D';
    }

    public boolean doesUsernameExist(String username)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";

        try
        {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                exists = rs.getInt(1) > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return exists;
    }

    public boolean doesEmailExist(String email)
    {
        String url = "jdbc:mysql://windhoek.erasmus.na:3306/ecommerce_database";
        String user = "intellij";
        String password = "";
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";

        try
        {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                exists = rs.getInt(1) > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return exists;
    }

    public boolean isValidDate(String date)
    {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
            return false;

        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        if (year < 1900)
            return false;

        if (month < 1 || month > 12)
            return false;

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year))
            daysInMonth[1] = 29;

        return day > 0 && day <= daysInMonth[month - 1];
    }

    private boolean isLeapYear(int year)
    {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

    }

    private String hashPassword(String password)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
