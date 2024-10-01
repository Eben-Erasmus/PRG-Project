import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class test2 {
    public static void main(String[] args) {
        String password = "";
        String hash = hashPassword(password);
        System.out.println(hash);
    }

    private static String hashPassword(String password) {
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
