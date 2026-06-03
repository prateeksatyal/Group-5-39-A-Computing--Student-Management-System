package dao;

import database.MySqlConnector;
import model.ResultData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for result operations.
 * Follows the same pattern as StudentDAO, AttendanceDAO, CourseDAO.
 * All SQL is here — no SQL in controllers or views.
 */
public class ResultDAO {

    private final MySqlConnector mysql = new MySqlConnector();

    // ── INSERT / UPDATE (UPSERT) ─────────────────────────────────────────────

    /**
     * Inserts or updates a result record.
     * Since there is no UNIQUE constraint in the DB, we do a SELECT first to avoid duplicate rows.
     */
    public boolean saveResult(ResultData data) {
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = mysql.openConnection();
            String checkSql = "SELECT id FROM results WHERE student_id = ? AND term = ? AND course_name = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, data.getStudentId());
            checkStmt.setString(2, data.getTerm());
            checkStmt.setString(3, data.getCourse());
            rs = checkStmt.executeQuery();

            int rows;
            if (rs.next()) {
                int id = rs.getInt("id");
                String updateSql = "UPDATE results SET student_name = ?, gpa = ?, overall_grade = ?, percentage = ? "
                                 + "WHERE id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, data.getStudentName());
                pstmt.setDouble(2, data.getGpa());
                pstmt.setString(3, data.getGrade());
                pstmt.setDouble(4, data.getPercentage());
                pstmt.setInt(5, id);
                rows = pstmt.executeUpdate();
            } else {
                String insertSql = "INSERT INTO results (student_id, student_name, term, course_name, gpa, overall_grade, percentage) "
                                 + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setString(1, data.getStudentId());
                pstmt.setString(2, data.getStudentName());
                pstmt.setString(3, data.getTerm());
                pstmt.setString(4, data.getCourse());
                pstmt.setDouble(5, data.getGpa());
                pstmt.setString(6, data.getGrade());
                pstmt.setDouble(7, data.getPercentage());
                rows = pstmt.executeUpdate();
            }
            closeResources(conn, pstmt, rs);
            if (checkStmt != null) {
                try { checkStmt.close(); } catch (SQLException ex) {}
            }
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error in saveResult: " + e.getMessage());
            closeResources(conn, pstmt, rs);
            if (checkStmt != null) {
                try { checkStmt.close(); } catch (SQLException ex) {}
            }
            return false;
        }
    }

    // ── SELECT ALL ────────────────────────────────────────────────────────────

    public List<ResultData> getAllResults() {
        String sql = "SELECT * FROM results ORDER BY student_id ASC, term ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ResultData> list = new ArrayList<>();
        try {
            conn = mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            closeResources(conn, pstmt, rs);
        } catch (SQLException e) {
            System.err.println("SQL Error in getAllResults: " + e.getMessage());
            closeResources(conn, pstmt, rs);
        }
        return list;
    }

    // ── SELECT BY STUDENT ID ──────────────────────────────────────────────────

    public List<ResultData> getResultsByStudentId(String studentId) {
        String sql = "SELECT * FROM results WHERE student_id = ? ORDER BY term ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ResultData> list = new ArrayList<>();
        try {
            conn = mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            closeResources(conn, pstmt, rs);
        } catch (SQLException e) {
            System.err.println("SQL Error in getResultsByStudentId: " + e.getMessage());
            closeResources(conn, pstmt, rs);
        }
        return list;
    }

    // ── SELECT BY TERM ────────────────────────────────────────────────────────

    public List<ResultData> getResultsByTerm(String term) {
        String sql = "SELECT * FROM results WHERE term = ? ORDER BY student_id ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ResultData> list = new ArrayList<>();
        try {
            conn = mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, term);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            closeResources(conn, pstmt, rs);
        } catch (SQLException e) {
            System.err.println("SQL Error in getResultsByTerm: " + e.getMessage());
            closeResources(conn, pstmt, rs);
        }
        return list;
    }

    // ── SELECT BY STUDENT + TERM ──────────────────────────────────────────────

    public List<ResultData> getResultsByStudentAndTerm(String studentId, String term) {
        String sql = "SELECT * FROM results WHERE student_id = ? AND term = ? ORDER BY course_name ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ResultData> list = new ArrayList<>();
        try {
            conn = mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, term);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            closeResources(conn, pstmt, rs);
        } catch (SQLException e) {
            System.err.println("SQL Error in getResultsByStudentAndTerm: " + e.getMessage());
            closeResources(conn, pstmt, rs);
        }
        return list;
    }

    // ── SEARCH ────────────────────────────────────────────────────────────────

    public List<ResultData> searchResults(String query) {
        String sql = "SELECT * FROM results WHERE student_id LIKE ? OR student_name LIKE ? ORDER BY student_id ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ResultData> list = new ArrayList<>();
        try {
            conn = mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            String wildcard = "%" + query + "%";
            pstmt.setString(1, wildcard);
            pstmt.setString(2, wildcard);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            closeResources(conn, pstmt, rs);
        } catch (SQLException e) {
            System.err.println("SQL Error in searchResults: " + e.getMessage());
            closeResources(conn, pstmt, rs);
        }
        return list;
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    public boolean deleteResult(int resultId) {
        String sql = "DELETE FROM results WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, resultId);
            int rows = pstmt.executeUpdate();
            closeResources(conn, pstmt, null);
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error in deleteResult: " + e.getMessage());
            closeResources(conn, pstmt, null);
            return false;
        }
    }

    // ── REPORT: Generate marks-based results for a term ───────────────────────

    /**
     * Reads all marks for a given term/course/section and returns computed ResultData rows.
     * Used by GenerateResultFrame via ResultController.
     */
    public List<ResultData> generateResultsFromMarks(String term, String course, String section) {
        String sql = "SELECT m.student_id, s.full_name, m.term, m.course_name, m.section_name, "
                   + "       m.marks, m.percentage, m.grade_point, m.grade "
                   + "FROM marks m JOIN students s ON m.student_id = s.student_id "
                   + "WHERE m.term = ? AND m.course_name = ? AND m.section_name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ResultData> list = new ArrayList<>();
        try {
            conn = mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, term);
            pstmt.setString(2, course);
            pstmt.setString(3, section);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                double total      = rs.getDouble("marks");
                double percentage = rs.getDouble("percentage");
                double gpa        = rs.getDouble("grade_point");
                String grade      = rs.getString("grade");
                double assignment = total * 0.5;
                double exam       = total * 0.5;
                String status     = percentage >= 40.0 ? "Pass" : "Fail";
                String today      = new java.text.SimpleDateFormat("yyyy-MM-dd")
                                        .format(new java.util.Date());

                list.add(new ResultData(
                    0,
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    term, course, section,
                    assignment, exam, total,
                    percentage, gpa, grade, status, today
                ));
            }
            closeResources(conn, pstmt, rs);
        } catch (SQLException e) {
            System.err.println("SQL Error in generateResultsFromMarks: " + e.getMessage());
            closeResources(conn, pstmt, rs);
        }
        return list;
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────

    private ResultData mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String studentId = rs.getString("student_id");
        String studentName = rs.getString("student_name");
        String term = rs.getString("term");
        String course = rs.getString("course_name");
        double gpa = rs.getDouble("gpa");
        String grade = rs.getString("overall_grade");
        double percentage = rs.getDouble("percentage");
        
        java.sql.Timestamp ts = rs.getTimestamp("created_at");
        String generatedDate = ts != null 
                             ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(ts)
                             : new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

        double totalMarks = (percentage / 100.0) * 200.0;
        double assignmentMarks = totalMarks * 0.5;
        double examMarks = totalMarks * 0.5;
        String status = percentage >= 40.0 ? "Pass" : "Fail";

        return new ResultData(
            id,
            studentId,
            studentName,
            term,
            course,
            "Section A", // Default section
            assignmentMarks,
            examMarks,
            totalMarks,
            percentage,
            gpa,
            grade,
            status,
            generatedDate
        );
    }

    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null)    { try { rs.close();    } catch (SQLException e) { System.err.println("Error closing RS: " + e.getMessage()); } }
        if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { System.err.println("Error closing PS: " + e.getMessage()); } }
        if (conn != null)  { try { mysql.closeConnection(conn); } catch (SQLException e) { System.err.println("Error closing conn: " + e.getMessage()); } }
    }
}
