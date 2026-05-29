package model;

public class StudentData {

    private String studentId;
    private String fullName;
    private String email;
    private String program;
    private String yearLevel;
    private String course;
    private String address;
    private String phoneNumber;
    private String gender;

    public StudentData() {
    }

    public StudentData(String studentId, String fullName, String email, String program, String yearLevel, String course, String address, String phoneNumber, String gender) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.program = program;
        this.yearLevel = yearLevel;
        this.course = course;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
