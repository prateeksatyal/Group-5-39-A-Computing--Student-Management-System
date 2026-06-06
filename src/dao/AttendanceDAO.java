/*
 * Decompiled with CFR 0.152.
 */
package dao;

import database.MySqlConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.AttendanceData;

public class AttendanceDAO {
    private final MySqlConnector mysql = new MySqlConnector();

    public boolean insertAttendance(AttendanceData data) {
        String sql = "INSERT INTO attendance (student_id, student_name, course_name, attendance_percentage, total_classes, attended_classes, attendance_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, data.getStudentId());
            pstmt.setString(2, data.getStudentName());
            pstmt.setString(3, data.getCourseName());
            pstmt.setDouble(4, data.getAttendancePercentage());
            pstmt.setInt(5, data.getTotalClasses());
            pstmt.setInt(6, data.getAttendedClasses());
            pstmt.setString(7, data.getAttendanceDate());
            int affectedRows = pstmt.executeUpdate();
            boolean bl = affectedRows > 0;
            this.closeResources(conn, pstmt, null);
            return bl;
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in insertAttendance: " + e.getMessage());
                boolean bl = false;
                this.closeResources(conn, pstmt, null);
                return bl;
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, null);
                throw throwable;
            }
        }
    }

    public boolean updateAttendance(AttendanceData data) {
        String sql = "UPDATE attendance SET student_id = ?, student_name = ?, course_name = ?, attendance_percentage = ?, total_classes = ?, attended_classes = ?, attendance_date = ? WHERE attendance_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, data.getStudentId());
            pstmt.setString(2, data.getStudentName());
            pstmt.setString(3, data.getCourseName());
            pstmt.setDouble(4, data.getAttendancePercentage());
            pstmt.setInt(5, data.getTotalClasses());
            pstmt.setInt(6, data.getAttendedClasses());
            pstmt.setString(7, data.getAttendanceDate());
            pstmt.setInt(8, data.getAttendanceId());
            int affectedRows = pstmt.executeUpdate();
            boolean bl = affectedRows > 0;
            this.closeResources(conn, pstmt, null);
            return bl;
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in updateAttendance: " + e.getMessage());
                boolean bl = false;
                this.closeResources(conn, pstmt, null);
                return bl;
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, null);
                throw throwable;
            }
        }
    }

    public boolean deleteAttendance(int attendanceId) {
        String sql = "DELETE FROM attendance WHERE attendance_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, attendanceId);
            int affectedRows = pstmt.executeUpdate();
            boolean bl = affectedRows > 0;
            this.closeResources(conn, pstmt, null);
            return bl;
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in deleteAttendance: " + e.getMessage());
                boolean bl = false;
                this.closeResources(conn, pstmt, null);
                return bl;
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, null);
                throw throwable;
            }
        }
    }

    public List<AttendanceData> getAllAttendance() {
        String sql = "SELECT * FROM attendance ORDER BY student_id ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<AttendanceData> list = new ArrayList<AttendanceData>();
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AttendanceData data = new AttendanceData(rs.getInt("attendance_id"), rs.getString("student_id"), rs.getString("student_name"), rs.getString("course_name"), rs.getDouble("attendance_percentage"), rs.getInt("total_classes"), rs.getInt("attended_classes"), rs.getString("attendance_date"));
                list.add(data);
            }
            this.closeResources(conn, pstmt, rs);
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in getAllAttendance: " + e.getMessage());
                this.closeResources(conn, pstmt, rs);
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, rs);
                throw throwable;
            }
        }
        return list;
    }

    public List<AttendanceData> searchAttendanceByStudentId(String studentId) {
        String sql = "SELECT * FROM attendance WHERE student_id LIKE ? OR student_name LIKE ? ORDER BY student_id ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<AttendanceData> list = new ArrayList<AttendanceData>();
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            String wildcard = "%" + studentId + "%";
            pstmt.setString(1, wildcard);
            pstmt.setString(2, wildcard);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AttendanceData data = new AttendanceData(rs.getInt("attendance_id"), rs.getString("student_id"), rs.getString("student_name"), rs.getString("course_name"), rs.getDouble("attendance_percentage"), rs.getInt("total_classes"), rs.getInt("attended_classes"), rs.getString("attendance_date"));
                list.add(data);
            }
            this.closeResources(conn, pstmt, rs);
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in searchAttendanceByStudentId: " + e.getMessage());
                this.closeResources(conn, pstmt, rs);
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, rs);
                throw throwable;
            }
        }
        return list;
    }

    public List<AttendanceData> getAttendanceByCourse(String courseName) {
        String sql = "SELECT * FROM attendance WHERE course_name = ? ORDER BY student_id ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<AttendanceData> list = new ArrayList<AttendanceData>();
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, courseName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AttendanceData data = new AttendanceData(rs.getInt("attendance_id"), rs.getString("student_id"), rs.getString("student_name"), rs.getString("course_name"), rs.getDouble("attendance_percentage"), rs.getInt("total_classes"), rs.getInt("attended_classes"), rs.getString("attendance_date"));
                list.add(data);
            }
            this.closeResources(conn, pstmt, rs);
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in getAttendanceByCourse: " + e.getMessage());
                this.closeResources(conn, pstmt, rs);
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, rs);
                throw throwable;
            }
        }
        return list;
    }

    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            }
            catch (SQLException e) {
                System.err.println("Error closing PreparedStatement: " + e.getMessage());
            }
        }
        if (conn != null) {
            try {
                this.mysql.closeConnection(conn);
            }
            catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }
    }
}
