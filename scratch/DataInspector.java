import java.sql.*;

public class DataInspector {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hello?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "root";
        String password = "1234";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                DatabaseMetaData metaData = conn.getMetaData();
                
                System.out.println("--- MARKS INDEXES ---");
                try (ResultSet rs = metaData.getIndexInfo("hello", null, "marks", false, false)) {
                    while (rs.next()) {
                        System.out.println("Index: " + rs.getString("INDEX_NAME") + " | Column: " + rs.getString("COLUMN_NAME"));
                    }
                }
                
                System.out.println("\n--- RESULTS INDEXES ---");
                try (ResultSet rs = metaData.getIndexInfo("hello", null, "results", false, false)) {
                    while (rs.next()) {
                        System.out.println("Index: " + rs.getString("INDEX_NAME") + " | Column: " + rs.getString("COLUMN_NAME"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
