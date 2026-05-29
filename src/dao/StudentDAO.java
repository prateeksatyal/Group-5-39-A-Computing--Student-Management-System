package dao;

import database.MySqlConnector;
import model.StudentData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private final MySqlConnector mysql = new MySqlConnector();

    public StudentData searchStudentById(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, studentId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String program = "";
                try { program = rs.getString("program"); } catch (SQLException sqle) {}
                
                String yearLevel = "";
                try { yearLevel = rs.getString("year_level"); } catch (SQLException sqle) {}

                String phone = "";
                try { phone = rs.getString("phone_number"); } catch (SQLException sqle) {
                    try { phone = rs.getString("phoneNumber"); } catch (SQLException sqle2) {}
                }

                String gender = "";
                try { gender = rs.getString("gender"); } catch (SQLException sqle) {}

                return new StudentData(
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    program,
                    yearLevel,
                    rs.getString("course"),
                    rs.getString("address"),
                    phone,
                    gender
                );
            }
        } catch (Exception e) {
            System.out.println("Error in searchStudentById: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { mysql.closeConnection(conn); } catch (Exception e) {}
            }
        }
        return null;
    }

    public boolean updateStudent(StudentData student) {
        String sqlFull = "UPDATE students SET full_name = ?, email = ?, course = ?, address = ?, phone_number = ?, gender = ? WHERE student_id = ?";
        String sqlWithExtra = "UPDATE students SET full_name = ?, email = ?, program = ?, year_level = ?, course = ?, address = ?, phone_number = ?, gender = ? WHERE student_id = ?";
        
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm;
            try {
                // Try executing with actual table schema (7 columns + student_id)
                pstm = conn.prepareStatement(sqlFull);
                pstm.setString(1, student.getFullName());
                pstm.setString(2, student.getEmail());
                pstm.setString(3, student.getCourse());
                pstm.setString(4, student.getAddress());
                pstm.setString(5, student.getPhoneNumber());
                pstm.setString(6, student.getGender());
                pstm.setString(7, student.getStudentId());
                return pstm.executeUpdate() > 0;
            } catch (SQLException e) {
                // Fallback to older schemas if columns mismatch
                try {
                    pstm = conn.prepareStatement(sqlWithExtra);
                    pstm.setString(1, student.getFullName());
                    pstm.setString(2, student.getEmail());
                    pstm.setString(3, student.getProgram());
                    pstm.setString(4, student.getYearLevel());
                    pstm.setString(5, student.getCourse());
                    pstm.setString(6, student.getAddress());
                    pstm.setString(7, student.getPhoneNumber());
                    pstm.setString(8, student.getGender());
                    pstm.setString(9, student.getStudentId());
                    return pstm.executeUpdate() > 0;
                } catch (SQLException ex) {
                    System.out.println("All update attempts failed: " + ex.getMessage());
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in updateStudent: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try { mysql.closeConnection(conn); } catch (Exception e) {}
            }
        }
    }

    public List<StudentData> getAllStudents() {
        List<StudentData> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String program = "";
                try { program = rs.getString("program"); } catch (SQLException sqle) {}
                
                String yearLevel = "";
                try { yearLevel = rs.getString("year_level"); } catch (SQLException sqle) {}

                String phone = "";
                try { phone = rs.getString("phone_number"); } catch (SQLException sqle) {
                    try { phone = rs.getString("phoneNumber"); } catch (SQLException sqle2) {}
                }

                String gender = "";
                try { gender = rs.getString("gender"); } catch (SQLException sqle) {}

                list.add(new StudentData(
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    program,
                    yearLevel,
                    rs.getString("course"),
                    rs.getString("address"),
                    phone,
                    gender
                ));
            }
        } catch (Exception e) {
            System.out.println("Error in getAllStudents: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { mysql.closeConnection(conn); } catch (Exception e) {}
            }
        }
        return list;
    }

    public boolean insertStudent(StudentData student) {
        String sqlFull = "INSERT INTO students (student_id, full_name, email, phone_number, course, gender, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlWithExtra = "INSERT INTO students (student_id, full_name, email, program, year_level, course, address, phone_number, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm;
            try {
                // Try active database schema (no program/year_level columns)
                pstm = conn.prepareStatement(sqlFull);
                pstm.setString(1, student.getStudentId());
                pstm.setString(2, student.getFullName());
                pstm.setString(3, student.getEmail());
                pstm.setString(4, student.getPhoneNumber());
                pstm.setString(5, student.getCourse());
                pstm.setString(6, student.getGender());
                pstm.setString(7, student.getAddress());
                return pstm.executeUpdate() > 0;
            } catch (SQLException e) {
                // Fallback to table schema that includes program/year_level columns
                try {
                    pstm = conn.prepareStatement(sqlWithExtra);
                    pstm.setString(1, student.getStudentId());
                    pstm.setString(2, student.getFullName());
                    pstm.setString(3, student.getEmail());
                    pstm.setString(4, student.getProgram() != null ? student.getProgram() : "");
                    pstm.setString(5, student.getYearLevel() != null ? student.getYearLevel() : "");
                    pstm.setString(6, student.getCourse());
                    pstm.setString(7, student.getAddress());
                    pstm.setString(8, student.getPhoneNumber());
                    pstm.setString(9, student.getGender());
                    return pstm.executeUpdate() > 0;
                } catch (SQLException ex) {
                    System.out.println("All insert attempts failed: " + ex.getMessage());
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in insertStudent: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try { mysql.closeConnection(conn); } catch (Exception e) {}
            }
        }
    }

    /**
     * Deletes a student and all their related data in a single transaction.
     * Order: course_enrollments → attendance → students (FK-safe cascading).
     * Rolls back fully if any step fails.
     */
    public boolean deleteStudent(String studentId) {
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            conn.setAutoCommit(false); // begin transaction

            // Step 1: remove course enrollments
            try (PreparedStatement p1 = conn.prepareStatement(
                    "DELETE FROM course_enrollments WHERE student_id = ?")) {
                p1.setString(1, studentId);
                p1.executeUpdate();
            }

            // Step 2: remove attendance records
            try (PreparedStatement p2 = conn.prepareStatement(
                    "DELETE FROM attendance WHERE student_id = ?")) {
                p2.setString(1, studentId);
                p2.executeUpdate();
            }

            // Step 3: remove the student record itself
            int rows;
            try (PreparedStatement p3 = conn.prepareStatement(
                    "DELETE FROM students WHERE student_id = ?")) {
                p3.setString(1, studentId);
                rows = p3.executeUpdate();
            }

            conn.commit(); // all 3 succeeded
            return rows > 0;

        } catch (Exception e) {
            System.err.println("Error in deleteStudent (cascade): " + e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (Exception rb) {}
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    mysql.closeConnection(conn);
                } catch (Exception e) {}
            }
        }
    }


    public List<StudentData> searchStudents(String query) {
        List<StudentData> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE student_id LIKE ? OR full_name LIKE ?";
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, "%" + query + "%");
            pstm.setString(2, "%" + query + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String program = "";
                try { program = rs.getString("program"); } catch (SQLException sqle) {}
                
                String yearLevel = "";
                try { yearLevel = rs.getString("year_level"); } catch (SQLException sqle) {}

                String phone = "";
                try { phone = rs.getString("phone_number"); } catch (SQLException sqle) {
                    try { phone = rs.getString("phoneNumber"); } catch (SQLException sqle2) {}
                }

                String gender = "";
                try { gender = rs.getString("gender"); } catch (SQLException sqle) {}

                list.add(new StudentData(
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    program,
                    yearLevel,
                    rs.getString("course"),
                    rs.getString("address"),
                    phone,
                    gender
                ));
            }
        } catch (Exception e) {
            System.out.println("Error in searchStudents: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { mysql.closeConnection(conn); } catch (Exception e) {}
            }
        }
        return list;
    }

    /**
     * Looks up a student record by their email address.
     * Used by ViewStudentProfileController after student login
     * (UserSession stores email as the login identifier).
     */
    public StudentData searchStudentByEmail(String email) {
        String sql = "SELECT * FROM students WHERE email = ?";
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String program = "";
                try { program = rs.getString("program"); } catch (SQLException sqle) {}

                String yearLevel = "";
                try { yearLevel = rs.getString("year_level"); } catch (SQLException sqle) {}

                String phone = "";
                try { phone = rs.getString("phone_number"); } catch (SQLException sqle) {
                    try { phone = rs.getString("phoneNumber"); } catch (SQLException sqle2) {}
                }

                String gender = "";
                try { gender = rs.getString("gender"); } catch (SQLException sqle) {}

                return new StudentData(
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    program,
                    yearLevel,
                    rs.getString("course"),
                    rs.getString("address"),
                    phone,
                    gender
                );
            }
        } catch (Exception e) {
            System.out.println("Error in searchStudentByEmail: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { mysql.closeConnection(conn); } catch (Exception e) {}
            }
        }
        return null;
    }
}

