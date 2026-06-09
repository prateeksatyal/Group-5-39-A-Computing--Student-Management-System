package dao;

import database.MySqlConnector;
import model.MarksData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {
    private final MySqlConnector mysql = new MySqlConnector();

    /**
     * Loads marks data for a term, course, and section.
     */
    public List<MarksData> getMarks(String term, String course, String section) {
        String sql = "SELECT m.id, m.student_id, s.full_name, m.course_name, m.term, m.section_name, m.marks, m.grade, m.grade_point, m.percentage "
                   + "FROM marks m JOIN students s ON m.student_id = s.student_id "
                   + "WHERE m.term = ? AND m.course_name = ? AND m.section_name = ?";
        List<MarksData> list = new ArrayList<>();
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, term);
            pstm.setString(2, course);
            pstm.setString(3, section);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(new MarksData(
                        rs.getInt("id"),
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("course_name"),
                        rs.getString("term"),
                        rs.getString("section_name"),
                        rs.getDouble("marks"),
                        rs.getString("grade"),
                        rs.getDouble("grade_point"),
                        rs.getDouble("percentage")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getMarks: " + e.getMessage());
        }
        return list;
    }

    /**
     * Loads marks data for a specific student and term.
     */
    public List<MarksData> getMarksByStudent(String studentId, String term) {
        String sql = "SELECT m.id, m.student_id, s.full_name, m.course_name, m.term, m.section_name, m.marks, m.grade, m.grade_point, m.percentage "
                   + "FROM marks m JOIN students s ON m.student_id = s.student_id "
                   + "WHERE m.student_id LIKE ? AND m.term = ?";
        List<MarksData> list = new ArrayList<>();
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, "%" + studentId + "%");
            pstm.setString(2, term);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    list.add(new MarksData(
                        rs.getInt("id"),
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("course_name"),
                        rs.getString("term"),
                        rs.getString("section_name"),
                        rs.getDouble("marks"),
                        rs.getString("grade"),
                        rs.getDouble("grade_point"),
                        rs.getDouble("percentage")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getMarksByStudent: " + e.getMessage());
        }
        return list;
    }

    /**
     * Saves a marks record (performs insert or update check).
     */
    public boolean saveMark(MarksData data) {
        String checkSql = "SELECT id FROM marks WHERE student_id = ? AND term = ? AND course_name = ? AND section_name = ?";
        String insertSql = "INSERT INTO marks (student_id, student_name, course_name, term, section_name, marks, grade, grade_point, percentage) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE marks SET student_name = ?, marks = ?, grade = ?, grade_point = ?, percentage = ? WHERE id = ?";

        try (Connection conn = mysql.openConnection()) {
            int existingId = -1;
            try (PreparedStatement checkPstm = conn.prepareStatement(checkSql)) {
                checkPstm.setString(1, data.getStudentId());
                checkPstm.setString(2, data.getTerm());
                checkPstm.setString(3, data.getCourseName());
                checkPstm.setString(4, data.getSectionName());
                try (ResultSet rs = checkPstm.executeQuery()) {
                    if (rs.next()) {
                        existingId = rs.getInt("id");
                    }
                }
            }

            if (existingId != -1) {
                try (PreparedStatement updatePstm = conn.prepareStatement(updateSql)) {
                    updatePstm.setString(1, data.getStudentName());
                    updatePstm.setDouble(2, data.getMarks());
                    updatePstm.setString(3, data.getGrade());
                    updatePstm.setDouble(4, data.getGradePoint());
                    updatePstm.setDouble(5, data.getPercentage());
                    updatePstm.setInt(6, existingId);
                    return updatePstm.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement insertPstm = conn.prepareStatement(insertSql)) {
                    insertPstm.setString(1, data.getStudentId());
                    insertPstm.setString(2, data.getStudentName());
                    insertPstm.setString(3, data.getCourseName());
                    insertPstm.setString(4, data.getTerm());
                    insertPstm.setString(5, data.getSectionName());
                    insertPstm.setDouble(6, data.getMarks());
                    insertPstm.setString(7, data.getGrade());
                    insertPstm.setDouble(8, data.getGradePoint());
                    insertPstm.setDouble(9, data.getPercentage());
                    return insertPstm.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in saveMark: " + e.getMessage());
            return false;
        }
    }
}
