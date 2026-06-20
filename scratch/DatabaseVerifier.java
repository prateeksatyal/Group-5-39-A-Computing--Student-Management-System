import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseVerifier {

    private static final String URL = "jdbc:mysql://localhost:3306/hello?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.println("--- CONNECTION TO DB hello ESTABLISHED ---");

                // Execute Table Verification sequentially
                auditUsers(conn);
                auditStudents(conn);
                auditCourses(conn);
                auditEnrollments(conn);
                auditAttendance(conn);
                auditMarks(conn);
                auditResults(conn);

                System.out.println("\n--- DATABASE INTEGRITY AUDIT COMPLETE ---");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void auditUsers(Connection conn) throws SQLException {
        System.out.println("\n==================================================");
        System.out.println("1. AUDITING TABLE: users");
        System.out.println("==================================================");

        // Metadata
        printTableSchema(conn, "users");

        // Row count
        int rowCount = getRowCount(conn, "users");
        System.out.println("Row Count: " + rowCount);

        // Null/Empty check
        int nullOrEmptyCount = getQueryCount(conn, "SELECT COUNT(*) FROM users WHERE full_name IS NULL OR username IS NULL OR email IS NULL OR password IS NULL OR role IS NULL OR TRIM(full_name) = '' OR TRIM(email) = ''");
        System.out.println("Null or Empty Fields Count: " + nullOrEmptyCount);
        if (nullOrEmptyCount > 0) {
            System.out.println("  [ALERT] Empty usernames/emails exist in users table!");
        }

        // Duplicate Emails
        int duplicateEmails = getQueryCount(conn, "SELECT COUNT(*) FROM (SELECT email FROM users GROUP BY email HAVING COUNT(*) > 1) AS t");
        System.out.println("Duplicate Emails Count: " + duplicateEmails);

        // Invalid Email Formats
        int invalidEmails = getQueryCount(conn, "SELECT COUNT(*) FROM users WHERE email NOT LIKE '%@%.%'");
        System.out.println("Malformed Emails Count: " + invalidEmails);
        if (invalidEmails > 0) {
            System.out.println("  [ALERT] Malformed emails exist in users table!");
        }
    }

    private static void auditStudents(Connection conn) throws SQLException {
        System.out.println("\n==================================================");
        System.out.println("2. AUDITING TABLE: students");
        System.out.println("==================================================");

        printTableSchema(conn, "students");
        int rowCount = getRowCount(conn, "students");
        System.out.println("Row Count: " + rowCount);

        // Duplicate IDs
        int duplicateIds = getQueryCount(conn, "SELECT COUNT(*) FROM (SELECT student_id FROM students GROUP BY student_id HAVING COUNT(*) > 1) AS t");
        System.out.println("Duplicate Student IDs Count: " + duplicateIds);

        // Duplicate Emails
        int duplicateEmails = getQueryCount(conn, "SELECT COUNT(*) FROM (SELECT email FROM students GROUP BY email HAVING COUNT(*) > 1) AS t");
        System.out.println("Duplicate Emails Count: " + duplicateEmails);

        // Invalid Email Formats
        int invalidEmails = getQueryCount(conn, "SELECT COUNT(*) FROM students WHERE email NOT LIKE '%@%.%'");
        System.out.println("Malformed Emails Count: " + invalidEmails);

        // Invalid Phone Formats (Phone must be digits)
        int invalidPhones = getQueryCount(conn, "SELECT COUNT(*) FROM students WHERE phone_number REGEXP '[^0-9]' OR LENGTH(phone_number) < 7");
        System.out.println("Malformed Phone Numbers Count (non-numeric or <7 digits): " + invalidPhones);

        // Orphaned User accounts (User exists as Student but no Student profile, or vice versa)
        int orphanUsers = getQueryCount(conn, "SELECT COUNT(*) FROM students s LEFT JOIN users u ON s.email = u.email AND u.role = 'Student' WHERE u.email IS NULL");
        System.out.println("Orphaned Students (No user account login matches email): " + orphanUsers);
        
        int orphanLogins = getQueryCount(conn, "SELECT COUNT(*) FROM users u LEFT JOIN students s ON u.email = s.email WHERE u.role = 'Student' AND s.email IS NULL");
        System.out.println("Orphaned Student Logins (User login exists but no Student profile matches email): " + orphanLogins);
    }

    private static void auditCourses(Connection conn) throws SQLException {
        System.out.println("\n==================================================");
        System.out.println("3. AUDITING TABLE: courses");
        System.out.println("==================================================");

        printTableSchema(conn, "courses");
        int rowCount = getRowCount(conn, "courses");
        System.out.println("Row Count: " + rowCount);

        // Duplicate Course Codes
        int duplicateCodes = getQueryCount(conn, "SELECT COUNT(*) FROM (SELECT course_code FROM courses GROUP BY course_code HAVING COUNT(*) > 1) AS t");
        System.out.println("Duplicate Course Codes Count: " + duplicateCodes);
    }

    private static void auditEnrollments(Connection conn) throws SQLException {
        System.out.println("\n==================================================");
        System.out.println("4. AUDITING TABLE: course_enrollments");
        System.out.println("==================================================");

        printTableSchema(conn, "course_enrollments");
        int rowCount = getRowCount(conn, "course_enrollments");
        System.out.println("Row Count: " + rowCount);

        // Duplicate Enrollments
        int duplicateEnrollments = getQueryCount(conn, "SELECT COUNT(*) FROM (SELECT student_id, course_id, semester FROM course_enrollments GROUP BY student_id, course_id, semester HAVING COUNT(*) > 1) AS t");
        System.out.println("Duplicate Enrollments (Same student + course + semester): " + duplicateEnrollments);

        // Orphaned Student References
        int orphanedStudents = getQueryCount(conn, "SELECT COUNT(*) FROM course_enrollments ce LEFT JOIN students s ON ce.student_id = s.student_id WHERE s.student_id IS NULL");
        System.out.println("Orphaned Enrollment Student References (student_id does not exist): " + orphanedStudents);

        // Orphaned Course References
        int orphanedCourses = getQueryCount(conn, "SELECT COUNT(*) FROM course_enrollments ce LEFT JOIN courses c ON ce.course_id = c.course_id WHERE c.course_id IS NULL");
        System.out.println("Orphaned Enrollment Course References (course_id does not exist): " + orphanedCourses);
    }

    private static void auditAttendance(Connection conn) throws SQLException {
        System.out.println("\n==================================================");
        System.out.println("5. AUDITING TABLE: attendance");
        System.out.println("==================================================");

        printTableSchema(conn, "attendance");
        int rowCount = getRowCount(conn, "attendance");
        System.out.println("Row Count: " + rowCount);

        // Invalid attendance records (attended > total)
        int invalidAttendance = getQueryCount(conn, "SELECT COUNT(*) FROM attendance WHERE attended_classes > total_classes OR total_classes <= 0 OR attended_classes < 0");
        System.out.println("Invalid Attendance Classes Counts (attended > total or values < 0): " + invalidAttendance);

        // Malformed Date String formats
        int invalidDates = getQueryCount(conn, "SELECT COUNT(*) FROM attendance WHERE attendance_date NOT REGEXP '^[0-9]{4}-[0-9]{2}-[0-9]{2}$'");
        System.out.println("Malformed Dates (Not matching yyyy-MM-dd): " + invalidDates);

        // Orphaned Student references
        int orphanedStudents = getQueryCount(conn, "SELECT COUNT(*) FROM attendance a LEFT JOIN students s ON a.student_id = s.student_id WHERE s.student_id IS NULL");
        System.out.println("Orphaned Attendance Student References (student_id does not exist): " + orphanedStudents);
    }

    private static void auditMarks(Connection conn) throws SQLException {
        System.out.println("\n==================================================");
        System.out.println("6. AUDITING TABLE: marks");
        System.out.println("==================================================");

        printTableSchema(conn, "marks");
        int rowCount = getRowCount(conn, "marks");
        System.out.println("Row Count: " + rowCount);

        // Invalid Marks (negative or out-of-bounds)
        int invalidMarks = getQueryCount(conn, "SELECT COUNT(*) FROM marks WHERE assignment_marks < 0 OR assignment_marks > 100 OR exam_marks < 0 OR exam_marks > 100 OR marks < 0 OR marks > 200");
        System.out.println("Invalid Marks Boundaries (negative or >100 per component): " + invalidMarks);

        // Duplicate marks record per term/course/section/student
        int duplicateMarks = getQueryCount(conn, "SELECT COUNT(*) FROM (SELECT student_id, term, course_name, section_name FROM marks GROUP BY student_id, term, course_name, section_name HAVING COUNT(*) > 1) AS t");
        System.out.println("Duplicate Marks Records (Same student + term + course + section): " + duplicateMarks);

        // Orphaned Student references
        int orphanedStudents = getQueryCount(conn, "SELECT COUNT(*) FROM marks m LEFT JOIN students s ON m.student_id = s.student_id WHERE s.student_id IS NULL");
        System.out.println("Orphaned Marks Student References (student_id does not exist): " + orphanedStudents);
    }

    private static void auditResults(Connection conn) throws SQLException {
        System.out.println("\n==================================================");
        System.out.println("7. AUDITING TABLE: results");
        System.out.println("==================================================");

        printTableSchema(conn, "results");
        int rowCount = getRowCount(conn, "results");
        System.out.println("Row Count: " + rowCount);

        // Invalid GPA (GPA < 0 or > 4.0)
        int invalidGpas = getQueryCount(conn, "SELECT COUNT(*) FROM results WHERE gpa < 0.0 OR gpa > 4.0");
        System.out.println("Invalid GPAs Count (GPA < 0 or > 4.0): " + invalidGpas);

        // Duplicate results
        int duplicateResults = getQueryCount(conn, "SELECT COUNT(*) FROM (SELECT student_id, term, course_name FROM results GROUP BY student_id, term, course_name HAVING COUNT(*) > 1) AS t");
        System.out.println("Duplicate Results (Same student + term + course): " + duplicateResults);

        // Orphaned Student references
        int orphanedStudents = getQueryCount(conn, "SELECT COUNT(*) FROM results r LEFT JOIN students s ON r.student_id = s.student_id WHERE s.student_id IS NULL");
        System.out.println("Orphaned Results Student References (student_id does not exist): " + orphanedStudents);
    }

    private static void printTableSchema(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("--- Table Schema for " + tableName + " ---");
        
        // Primary Keys
        List<String> pks = new ArrayList<>();
        try (ResultSet pkRs = meta.getPrimaryKeys("hello", null, tableName)) {
            while (pkRs.next()) {
                pks.add(pkRs.getString("COLUMN_NAME"));
            }
        }
        System.out.println("Primary Keys: " + pks);

        // Foreign Keys
        List<String> fks = new ArrayList<>();
        try (ResultSet fkRs = meta.getImportedKeys("hello", null, tableName)) {
            while (fkRs.next()) {
                String fkCol = fkRs.getString("FKCOLUMN_NAME");
                String pkTab = fkRs.getString("PKTABLE_NAME");
                String pkCol = fkRs.getString("PKCOLUMN_NAME");
                fks.add(fkCol + " references " + pkTab + "(" + pkCol + ")");
            }
        }
        System.out.println("Foreign Keys: " + fks);

        // Columns
        System.out.print("Columns: ");
        try (ResultSet colRs = meta.getColumns("hello", null, tableName, "%")) {
            while (colRs.next()) {
                System.out.print(colRs.getString("COLUMN_NAME") + " (" + colRs.getString("TYPE_NAME") + "), ");
            }
        }
        System.out.println();
    }

    private static int getRowCount(Connection conn, String tableName) throws SQLException {
        return getQueryCount(conn, "SELECT COUNT(*) FROM " + tableName);
    }

    private static int getQueryCount(Connection conn, String query) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
