import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbAlter {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hello?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "root";
        String password = "1234";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {
                
                // Add assignment_marks and exam_marks columns if they don't exist
                try {
                    stmt.executeUpdate("ALTER TABLE marks ADD COLUMN assignment_marks DOUBLE DEFAULT 0");
                    System.out.println("Added assignment_marks column.");
                } catch (Exception e) {
                    System.out.println("assignment_marks column might already exist: " + e.getMessage());
                }

                try {
                    stmt.executeUpdate("ALTER TABLE marks ADD COLUMN exam_marks DOUBLE DEFAULT 0");
                    System.out.println("Added exam_marks column.");
                } catch (Exception e) {
                    System.out.println("exam_marks column might already exist: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
