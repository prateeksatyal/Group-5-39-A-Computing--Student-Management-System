import java.sql.*;

public class ConstraintInspector {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hello?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "root";
        String password = "1234";

        System.out.println("==================================================");
        System.out.println("          DATABASE FOREIGN KEY AUDIT");
        System.out.println("==================================================");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT TABLE_NAME, COLUMN_NAME, CONSTRAINT_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME " +
                             "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                             "WHERE REFERENCED_TABLE_SCHEMA = 'hello'";
                
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    
                    boolean found = false;
                    while (rs.next()) {
                        found = true;
                        System.out.println("Table: " + rs.getString("TABLE_NAME"));
                        System.out.println("  Column: " + rs.getString("COLUMN_NAME"));
                        System.out.println("  Constraint Name: " + rs.getString("CONSTRAINT_NAME"));
                        System.out.println("  Referenced Table: " + rs.getString("REFERENCED_TABLE_NAME"));
                        System.out.println("  Referenced Column: " + rs.getString("REFERENCED_COLUMN_NAME"));
                        System.out.println();
                    }
                    if (!found) {
                        System.out.println("No foreign key constraints defined in INFORMATION_SCHEMA!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==================================================");
    }
}
