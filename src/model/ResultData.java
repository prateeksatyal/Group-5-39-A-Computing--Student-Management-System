package model;

/**
 * Model class representing a generated student result record.
 * Maps to the `results` database table.
 * Follows the same pattern as StudentData, AttendanceData, CourseData.
 */
public class ResultData {

    private int resultId;
    private String studentId;
    private String studentName;
    private String term;
    private String course;
    private String section;
    private double assignmentMarks;
    private double examMarks;
    private double totalMarks;
    private double percentage;
    private double gpa;
    private String grade;
    private String status;       // Pass / Fail
    private String generatedDate;

    public ResultData() {
    }

    public ResultData(int resultId, String studentId, String studentName,
                      String term, String course, String section,
                      double assignmentMarks, double examMarks, double totalMarks,
                      double percentage, double gpa, String grade,
                      String status, String generatedDate) {
        this.resultId       = resultId;
        this.studentId      = studentId;
        this.studentName    = studentName;
        this.term           = term;
        this.course         = course;
        this.section        = section;
        this.assignmentMarks = assignmentMarks;
        this.examMarks      = examMarks;
        this.totalMarks     = totalMarks;
        this.percentage     = percentage;
        this.gpa            = gpa;
        this.grade          = grade;
        this.status         = status;
        this.generatedDate  = generatedDate;
    }

    public int getResultId()             { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public String getStudentId()             { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName()             { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getTerm()           { return term; }
    public void setTerm(String term)  { this.term = term; }

    public String getCourse()              { return course; }
    public void setCourse(String course)   { this.course = course; }

    public String getSection()              { return section; }
    public void setSection(String section)  { this.section = section; }

    public double getAssignmentMarks()                     { return assignmentMarks; }
    public void setAssignmentMarks(double assignmentMarks) { this.assignmentMarks = assignmentMarks; }

    public double getExamMarks()                 { return examMarks; }
    public void setExamMarks(double examMarks)   { this.examMarks = examMarks; }

    public double getTotalMarks()                  { return totalMarks; }
    public void setTotalMarks(double totalMarks)   { this.totalMarks = totalMarks; }

    public double getPercentage()                  { return percentage; }
    public void setPercentage(double percentage)   { this.percentage = percentage; }

    public double getGpa()           { return gpa; }
    public void setGpa(double gpa)   { this.gpa = gpa; }

    public String getGrade()              { return grade; }
    public void setGrade(String grade)    { this.grade = grade; }

    public String getStatus()              { return status; }
    public void setStatus(String status)   { this.status = status; }

    public String getGeneratedDate()                    { return generatedDate; }
    public void setGeneratedDate(String generatedDate)  { this.generatedDate = generatedDate; }
}
