/*
 * Decompiled with CFR 0.152.
 */
package model;

public class CourseData {
    private int courseId;
    private String courseName;
    private String courseCode;
    private int creditHours;
    private String assignedTeacher;
    private String semester;
    private String studentId;
    private String studentName;

    public CourseData() {
    }

    public CourseData(int courseId, String courseName, String courseCode, int creditHours, String assignedTeacher, String semester) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.creditHours = creditHours;
        this.assignedTeacher = assignedTeacher;
        this.semester = semester;
    }

    public CourseData(int courseId, String courseName, String courseCode, int creditHours, String assignedTeacher, String semester, String studentId, String studentName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.creditHours = creditHours;
        this.assignedTeacher = assignedTeacher;
        this.semester = semester;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getCreditHours() {
        return this.creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getAssignedTeacher() {
        return this.assignedTeacher;
    }

    public void setAssignedTeacher(String assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }

    public String getSemester() {
        return this.semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
