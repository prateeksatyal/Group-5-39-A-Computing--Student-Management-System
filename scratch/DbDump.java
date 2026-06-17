import java.sql.*;

public class DbDump {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hello?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "root";
        String password = "1234";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String[] tables = {"course_enrollments", "attendance", "marks", "courses", "students", "users"};
                for (String tbl : tables) {
                    System.out.println("=== " + tbl.toUpperCase() + " ===");
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery("SELECT * FROM " + tbl)) {
                        ResultSetMetaData md = rs.getMetaData();
                        int cols = md.getColumnCount();
                        for (int i = 1; i <= cols; i++) {
                            System.out.print(md.getColumnName(i) + "\t");
                        }
                        System.out.println();
                        while (rs.next()) {
                            for (int i = 1; i <= cols; i++) {
                                System.out.print(rs.getString(i) + "\t");
                            }
                            System.out.println();
                        }
                    } catch (Exception e) {
                        System.out.println("Error reading table " + tbl + ": " + e.getMessage());
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

