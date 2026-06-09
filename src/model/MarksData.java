package model;

public class MarksData {
    private int id;
    private String studentId;
    private String studentName;
    private String courseName;
    private String term;
    private String sectionName;
    private double marks;
    private String grade;
    private double gradePoint;
    private double percentage;

    public MarksData(int id, String studentId, String studentName, String courseName, String term, String sectionName, double marks, String grade, double gradePoint, double percentage) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseName = courseName;
        this.term = term;
        this.sectionName = sectionName;
        this.marks = marks;
        this.grade = grade;
        this.gradePoint = gradePoint;
        this.percentage = percentage;
    }

    public MarksData(String studentId, String studentName, String courseName, String term, String sectionName, double marks, String grade, double gradePoint, double percentage) {
        this(0, studentId, studentName, courseName, term, sectionName, marks, grade, gradePoint, percentage);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public double getGradePoint() { return gradePoint; }
    public void setGradePoint(double gradePoint) { this.gradePoint = gradePoint; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
}
