import java.sql.*;

public class UserInspector {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hello?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "root";
        String password = "1234";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT email, password, role FROM users";
                try (PreparedStatement p = conn.prepareStatement(sql);
                     ResultSet rs = p.executeQuery()) {
                    System.out.println("---- USER CREDENTIALS ----");
                    while (rs.next()) {
                        System.out.println("Email: " + rs.getString("email") + 
                                           " | Password: " + rs.getString("password") + 
                                           " | Role: " + rs.getString("role"));
                    }
                    System.out.println("--------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
