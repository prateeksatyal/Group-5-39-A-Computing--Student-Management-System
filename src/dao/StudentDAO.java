package dao;

import database.MySqlConnector;
import model.StudentData;
import java.sql.*;

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
                String phone = "";
                try { phone = rs.getString("phoneNumber"); } catch (SQLException sqle) {
                    try { phone = rs.getString("phone_number"); } catch (SQLException sqle2) {}
                }
                String gender = "";
                try { gender = rs.getString("gender"); } catch (SQLException sqle) {}

                return new StudentData(
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("program"),
                    rs.getString("year_level"),
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
        // Try updating including phoneNumber/gender, fallback to standard if columns don't exist
        String sqlWithExtra = "UPDATE students SET full_name = ?, email = ?, program = ?, year_level = ?, course = ?, address = ?, phoneNumber = ?, gender = ? WHERE student_id = ?";
        String sqlStandard = "UPDATE students SET full_name = ?, email = ?, program = ?, year_level = ?, course = ?, address = ? WHERE student_id = ?";
        
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm;
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
            } catch (SQLException e) {
                // Fallback to standard columns if phoneNumber/gender are missing in MySQL
                pstm = conn.prepareStatement(sqlStandard);
                pstm.setString(1, student.getFullName());
                pstm.setString(2, student.getEmail());
                pstm.setString(3, student.getProgram());
                pstm.setString(4, student.getYearLevel());
                pstm.setString(5, student.getCourse());
                pstm.setString(6, student.getAddress());
                pstm.setString(7, student.getStudentId());
                return pstm.executeUpdate() > 0;
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
}
