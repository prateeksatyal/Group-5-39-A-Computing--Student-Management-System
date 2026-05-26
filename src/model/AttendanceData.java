/*
 * Decompiled with CFR 0.152.
 */
package model;

public class AttendanceData {
    private int attendanceId;
    private String studentId;
    private String studentName;
    private String courseName;
    private double attendancePercentage;
    private int totalClasses;
    private int attendedClasses;
    private String attendanceDate;

    public AttendanceData() {
    }

    public AttendanceData(int attendanceId, String studentId, String studentName, String courseName, double attendancePercentage, int totalClasses, int attendedClasses, String attendanceDate) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseName = courseName;
        this.attendancePercentage = attendancePercentage;
        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;
        this.attendanceDate = attendanceDate;
    }

    public int getAttendanceId() {
        return this.attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
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

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getAttendancePercentage() {
        return this.attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public int getTotalClasses() {
        return this.totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getAttendedClasses() {
        return this.attendedClasses;
    }

    public void setAttendedClasses(int attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    public String getAttendanceDate() {
        return this.attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}
