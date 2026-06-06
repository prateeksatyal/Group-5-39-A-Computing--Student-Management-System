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
import model.CourseData;

public class CourseDAO {
    private final MySqlConnector mysql = new MySqlConnector();

    public boolean insertCourse(CourseData course) {
        String sql = "INSERT INTO courses (course_name, course_code, credit_hours, assigned_teacher, semester) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setInt(3, course.getCreditHours());
            pstmt.setString(4, course.getAssignedTeacher());
            pstmt.setString(5, course.getSemester());
            int affectedRows = pstmt.executeUpdate();
            boolean bl = affectedRows > 0;
            this.closeResources(conn, pstmt, null);
            return bl;
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in insertCourse: " + e.getMessage());
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

    public boolean updateCourse(CourseData course) {
        String sql = "UPDATE courses SET course_name = ?, course_code = ?, credit_hours = ?, assigned_teacher = ?, semester = ? WHERE course_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setInt(3, course.getCreditHours());
            pstmt.setString(4, course.getAssignedTeacher());
            pstmt.setString(5, course.getSemester());
            pstmt.setInt(6, course.getCourseId());
            int affectedRows = pstmt.executeUpdate();
            boolean bl = affectedRows > 0;
            this.closeResources(conn, pstmt, null);
            return bl;
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in updateCourse: " + e.getMessage());
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

    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, courseId);
            int affectedRows = pstmt.executeUpdate();
            boolean bl = affectedRows > 0;
            this.closeResources(conn, pstmt, null);
            return bl;
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in deleteCourse: " + e.getMessage());
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

    public List<CourseData> getAllCourses() {
        String sql = "SELECT * FROM courses ORDER BY course_code ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CourseData> list = new ArrayList<CourseData>();
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CourseData course = new CourseData(rs.getInt("course_id"), rs.getString("course_name"), rs.getString("course_code"), rs.getInt("credit_hours"), rs.getString("assigned_teacher"), rs.getString("semester"));
                list.add(course);
            }
            this.closeResources(conn, pstmt, rs);
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in getAllCourses: " + e.getMessage());
                this.closeResources(conn, pstmt, rs);
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, rs);
                throw throwable;
            }
        }
        return list;
    }

    public List<CourseData> searchCourse(String query) {
        String sql = "SELECT * FROM courses WHERE course_name LIKE ? OR course_code LIKE ? ORDER BY course_code ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CourseData> list = new ArrayList<CourseData>();
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            String wildcard = "%" + query + "%";
            pstmt.setString(1, wildcard);
            pstmt.setString(2, wildcard);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CourseData course = new CourseData(rs.getInt("course_id"), rs.getString("course_name"), rs.getString("course_code"), rs.getInt("credit_hours"), rs.getString("assigned_teacher"), rs.getString("semester"));
                list.add(course);
            }
            this.closeResources(conn, pstmt, rs);
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in searchCourse: " + e.getMessage());
                this.closeResources(conn, pstmt, rs);
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, rs);
                throw throwable;
            }
        }
        return list;
    }

    public boolean enrollStudentInCourse(CourseData enrollment) {
        String sql = "INSERT INTO course_enrollments (course_id, course_name, course_code, student_id, student_name, semester, assigned_teacher) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, enrollment.getCourseId());
            pstmt.setString(2, enrollment.getCourseName());
            pstmt.setString(3, enrollment.getCourseCode());
            pstmt.setString(4, enrollment.getStudentId());
            pstmt.setString(5, enrollment.getStudentName());
            pstmt.setString(6, enrollment.getSemester());
            pstmt.setString(7, enrollment.getAssignedTeacher());
            int affectedRows = pstmt.executeUpdate();
            boolean bl = affectedRows > 0;
            this.closeResources(conn, pstmt, null);
            return bl;
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in enrollStudentInCourse: " + e.getMessage());
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

    public boolean isStudentEnrolled(String studentId, int courseId) {
        String sql = "SELECT COUNT(*) FROM course_enrollments WHERE student_id = ? AND course_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setInt(2, courseId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                boolean bl = rs.getInt(1) > 0;
                this.closeResources(conn, pstmt, rs);
                return bl;
            }
            this.closeResources(conn, pstmt, rs);
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in isStudentEnrolled: " + e.getMessage());
                this.closeResources(conn, pstmt, rs);
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, rs);
                throw throwable;
            }
        }
        return false;
    }

    public List<CourseData> getAssignedCourses() {
        String sql = "SELECT * FROM course_enrollments ORDER BY student_id ASC, course_code ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CourseData> list = new ArrayList<CourseData>();
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CourseData enrollment = new CourseData(rs.getInt("course_id"), rs.getString("course_name"), rs.getString("course_code"), 0, rs.getString("assigned_teacher"), rs.getString("semester"), rs.getString("student_id"), rs.getString("student_name"));
                list.add(enrollment);
            }
            this.closeResources(conn, pstmt, rs);
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in getAssignedCourses: " + e.getMessage());
                this.closeResources(conn, pstmt, rs);
            }
            catch (Throwable throwable) {
                this.closeResources(conn, pstmt, rs);
                throw throwable;
            }
        }
        return list;
    }

    public List<CourseData> searchAssignedCourses(String query) {
        String sql = "SELECT * FROM course_enrollments WHERE student_id LIKE ? OR student_name LIKE ? OR course_code LIKE ? OR course_name LIKE ? ORDER BY student_id ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CourseData> list = new ArrayList<CourseData>();
        try {
            conn = this.mysql.openConnection();
            pstmt = conn.prepareStatement(sql);
            String wildcard = "%" + query + "%";
            pstmt.setString(1, wildcard);
            pstmt.setString(2, wildcard);
            pstmt.setString(3, wildcard);
            pstmt.setString(4, wildcard);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CourseData enrollment = new CourseData(rs.getInt("course_id"), rs.getString("course_name"), rs.getString("course_code"), 0, rs.getString("assigned_teacher"), rs.getString("semester"), rs.getString("student_id"), rs.getString("student_name"));
                list.add(enrollment);
            }
            this.closeResources(conn, pstmt, rs);
        }
        catch (SQLException e) {
            try {
                System.err.println("SQL Error in searchAssignedCourses: " + e.getMessage());
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
