import dao.*;
import model.*;
import database.MySqlConnector;
import java.sql.*;
import java.util.*;

public class StressTestRunner {

    private static final MySqlConnector mysql = new MySqlConnector();
    private static final StudentDAO studentDAO = new StudentDAO();
    private static final CourseDAO courseDAO = new CourseDAO();

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("         STRESS AND PERFORMANCE TEST RUNNER");
        System.out.println("==================================================");

        try {
            System.out.println("Cleaning previous stress test leftovers...");
            cleanupStressData();

            System.out.println("\n[Phase 1] Generating Bulk Test Data...");
            long startInsert = System.currentTimeMillis();
            insertBulkData();
            long insertDuration = System.currentTimeMillis() - startInsert;
            System.out.println(String.format("  Successfully generated bulk data in %d ms.", insertDuration));

            System.out.println("\n[Phase 2] Measuring Table Query and Load Latencies...");
            measureLoadTimes();

            System.out.println("\n[Phase 3] Cleaning up stress test data...");
            cleanupStressData();
            System.out.println("  Clean up complete.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==================================================");
    }

    private static void insertBulkData() throws SQLException {
        try (Connection conn = mysql.openConnection()) {
            conn.setAutoCommit(false);

            // 1. Insert 100 Students
            String studentSql = "INSERT INTO students (student_id, full_name, email, phone_number, course, gender, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String userSql = "INSERT INTO users (full_name, username, email, password, confirm_password, role) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement psStu = conn.prepareStatement(studentSql);
                 PreparedStatement psUser = conn.prepareStatement(userSql)) {
                
                for (int i = 1; i <= 100; i++) {
                    String id = String.format("STRESS_ST_%03d", i);
                    String name = "Stress Student " + i;
                    String email = "stress_st_" + i + "@example.com";
                    String phone = String.format("9800000%03d", i);

                    psStu.setString(1, id);
                    psStu.setString(2, name);
                    psStu.setString(3, email);
                    psStu.setString(4, phone);
                    psStu.setString(5, "BSc Computer Science");
                    psStu.setString(6, i % 2 == 0 ? "Male" : "Female");
                    psStu.setString(7, "Stress Address " + i);
                    psStu.addBatch();

                    psUser.setString(1, name);
                    psUser.setString(2, email);
                    psUser.setString(3, email);
                    psUser.setString(4, "password");
                    psUser.setString(5, "password");
                    psUser.setString(6, "Student");
                    psUser.addBatch();
                }
                psStu.executeBatch();
                psUser.executeBatch();
            }

            // 2. Insert 50 Courses
            String courseSql = "INSERT INTO courses (course_id, course_name, course_code, credit_hours, assigned_teacher, semester) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psCourse = conn.prepareStatement(courseSql)) {
                for (int i = 1; i <= 50; i++) {
                    psCourse.setInt(1, 9000 + i);
                    psCourse.setString(2, "Stress Course " + i);
                    psCourse.setString(3, String.format("STR%03d", i));
                    psCourse.setInt(4, (i % 4) + 2);
                    psCourse.setString(5, "Stress Teacher " + i);
                    psCourse.setString(6, "Fall 2026");
                    psCourse.addBatch();
                }
                psCourse.executeBatch();
            }

            // 3. Insert 500 Enrollments
            String enrollSql = "INSERT INTO course_enrollments (course_id, course_name, course_code, student_id, student_name, semester, assigned_teacher) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psEnroll = conn.prepareStatement(enrollSql)) {
                for (int i = 1; i <= 500; i++) {
                    int courseIndex = (i % 50) + 1;
                    int studentIndex = (i % 100) + 1;

                    psEnroll.setInt(1, 9000 + courseIndex);
                    psEnroll.setString(2, "Stress Course " + courseIndex);
                    psEnroll.setString(3, String.format("STR%03d", courseIndex));
                    psEnroll.setString(4, String.format("STRESS_ST_%03d", studentIndex));
                    psEnroll.setString(5, "Stress Student " + studentIndex);
                    psEnroll.setString(6, "Fall 2026");
                    psEnroll.setString(7, "Stress Teacher " + courseIndex);
                    psEnroll.addBatch();
                }
                psEnroll.executeBatch();
            }

            // 4. Insert 500 Attendance Records
            String attSql = "INSERT INTO attendance (student_id, student_name, course_name, attendance_percentage, total_classes, attended_classes, attendance_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psAtt = conn.prepareStatement(attSql)) {
                for (int i = 1; i <= 500; i++) {
                    int studentIndex = (i % 100) + 1;
                    psAtt.setString(1, String.format("STRESS_ST_%03d", studentIndex));
                    psAtt.setString(2, "Stress Student " + studentIndex);
                    psAtt.setString(3, "Stress Course " + ((i % 50) + 1));
                    psAtt.setDouble(4, 80.0);
                    psAtt.setInt(5, 30);
                    psAtt.setInt(6, 24);
                    psAtt.setString(7, "2026-06-20");
                    psAtt.addBatch();
                }
                psAtt.executeBatch();
            }

            // 5. Insert 500 Marks Records
            String marksSql = "INSERT INTO marks (student_id, student_name, course_name, term, section_name, marks, grade, grade_point, percentage, assignment_marks, exam_marks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psMarks = conn.prepareStatement(marksSql)) {
                for (int i = 1; i <= 500; i++) {
                    int studentIndex = (i % 100) + 1;
                    psMarks.setString(1, String.format("STRESS_ST_%03d", studentIndex));
                    psMarks.setString(2, "Stress Student " + studentIndex);
                    psMarks.setString(3, "Stress Course " + ((i % 50) + 1));
                    psMarks.setString(4, "Term 1");
                    psMarks.setString(5, "Section A");
                    psMarks.setDouble(6, 150.0);
                    psMarks.setString(7, "B+");
                    psMarks.setDouble(8, 3.3);
                    psMarks.setDouble(9, 75.0);
                    psMarks.setDouble(10, 60.0);
                    psMarks.setDouble(11, 90.0);
                    psMarks.addBatch();
                }
                psMarks.executeBatch();
            }

            conn.commit();
        }
    }

    private static void measureLoadTimes() throws SQLException {
        // Query Students Table
        long start = System.currentTimeMillis();
        int count = 0;
        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM students");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) count++;
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(String.format("  Loaded %d Student rows in %d ms (Target: <2000 ms).", count, duration));

        // Query Enrollments Table
        start = System.currentTimeMillis();
        count = 0;
        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM course_enrollments");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) count++;
        }
        duration = System.currentTimeMillis() - start;
        System.out.println(String.format("  Loaded %d Enrollment rows in %d ms (Target: <2000 ms).", count, duration));

        // Query Marks Table
        start = System.currentTimeMillis();
        count = 0;
        try (Connection conn = mysql.openConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM marks");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) count++;
        }
        duration = System.currentTimeMillis() - start;
        System.out.println(String.format("  Loaded %d Marks rows in %d ms (Target: <2000 ms).", count, duration));
    }

    private static void cleanupStressData() throws SQLException {
        try (Connection conn = mysql.openConnection()) {
            conn.setAutoCommit(false);
            
            // Delete from all tables based on stress pattern
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("DELETE FROM course_enrollments WHERE student_id LIKE 'STRESS_ST_%'");
                st.executeUpdate("DELETE FROM attendance WHERE student_id LIKE 'STRESS_ST_%'");
                st.executeUpdate("DELETE FROM marks WHERE student_id LIKE 'STRESS_ST_%'");
                st.executeUpdate("DELETE FROM results WHERE student_id LIKE 'STRESS_ST_%'");
                st.executeUpdate("DELETE FROM students WHERE student_id LIKE 'STRESS_ST_%'");
                st.executeUpdate("DELETE FROM users WHERE username LIKE 'stress_st_%'");
                st.executeUpdate("DELETE FROM courses WHERE course_id >= 9000");
            }
            conn.commit();
        }
    }
}
