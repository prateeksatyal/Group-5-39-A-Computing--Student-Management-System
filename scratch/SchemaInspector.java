import java.sql.*;

public class SchemaInspector {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hello?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "root";
        String password = "1234";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                System.out.println("Connection successful!");
                DatabaseMetaData metaData = conn.getMetaData();
                String[] types = {"TABLE"};
                try (ResultSet rs = metaData.getTables("hello", null, "%", types)) {
                    while (rs.next()) {
                        String tableName = rs.getString("TABLE_NAME");
                        System.out.println("\nTable: " + tableName);
                        try (ResultSet cols = metaData.getColumns("hello", null, tableName, "%")) {
                            while (cols.next()) {
                                System.out.println("  - " + cols.getString("COLUMN_NAME") + " (" + cols.getString("TYPE_NAME") + ")");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
