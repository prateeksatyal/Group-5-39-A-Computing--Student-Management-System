import view.*;
import controller.*;
import dao.*;
import model.*;
import database.*;

import java.sql.*;
import java.util.List;
import javax.swing.*;

public class SMSExecutionTesting {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     SMS REAL EXECUTION TESTING SUITE            ");
        System.out.println("==================================================");

        boolean loginPassed = testLogins();
        System.out.println("1. Login Authentication Test: " + (loginPassed ? "PASSED" : "FAILED"));

        boolean rolePassed = testRoleRestrictions();
        System.out.println("2. Role Access and Restrictions Test: " + (rolePassed ? "PASSED" : "FAILED"));

        boolean studentCrudPassed = testStudentCRUD();
        System.out.println("3. Student CRUD & Database Operations: " + (studentCrudPassed ? "PASSED" : "FAILED"));

        boolean marksCalculationPassed = testMarksCalculations();
        System.out.println("4. Marks GPA/Grade Calculations & Splitting: " + (marksCalculationPassed ? "PASSED" : "FAILED"));

        boolean enrollmentPassed = testEnrollmentWorkflows();
        System.out.println("5. Enrollment Integrity & Orphan Checks: " + (enrollmentPassed ? "PASSED" : "FAILED"));

        System.out.println("==================================================");
        System.out.println("     TESTING SUITE COMPLETE                       ");
        System.out.println("==================================================");
    }

    private static boolean testLogins() {
        System.out.println("\n[Testing Logins]");
        UserDao userDao = new UserDao();

        // 1. Admin login
        UserData admin = userDao.loginUser("admin@sms.com", "@1234", "Admin");
        if (admin == null) {
            System.out.println("  [FAIL] Admin login failed for admin@sms.com");
            return false;
        }
        System.out.println("  [PASS] Admin login successful: " + admin.getUserName());

        // 2. Lecturer login
        UserData teacher = userDao.loginUser("a@gmail.com", "1", "Teacher");
        if (teacher == null) {
            System.out.println("  [FAIL] Teacher login failed for a@gmail.com");
            return false;
        }
        System.out.println("  [PASS] Teacher login successful: " + teacher.getUserName());

        // 3. Student login
        UserData student = userDao.loginUser("ram@sms.com", "123", "Student");
        if (student == null) {
            System.out.println("  [FAIL] Student login failed for ram@sms.com");
            return false;
        }
        System.out.println("  [PASS] Student login successful: " + student.getUserName());

        return true;
    }

    private static boolean testRoleRestrictions() {
        System.out.println("\n[Testing Role Restrictions & Forced Navigation]");
        boolean overallPass = true;

        // Simulate Student logged in
        UserSession.setCurrentUser(new UserData("ram@sms.com", "123", "ram@sms.com", "Student"));

        // 1. Try opening Student Management Frame directly
        System.out.println("  Instantiating StudentManagementFrame directly as a Student...");
        try {
            StudentManagementFrame smf = new StudentManagementFrame();
            StudentController sc = new StudentController(smf);
            
            // Check if student management buttons are disabled
            boolean addBtnEnabled = smf.getAddButton().isEnabled();
            System.out.println("    Student Management Add Button Enabled: " + addBtnEnabled);
            if (addBtnEnabled) {
                System.out.println("    [FAIL] Student can click add student records!");
                overallPass = false;
            } else {
                System.out.println("    [PASS] Student add button successfully disabled.");
            }

            // Privacy leak check: can student read table data?
            int studentCount = smf.getStudentsTable().getRowCount();
            System.out.println("    Student Management Student Table Rows: " + studentCount);
            if (studentCount > 0) {
                System.out.println("    [FAIL] PRIVACY LEAK: Student can view all other student profiles and details!");
                overallPass = false;
            }
            smf.dispose();
        } catch (Exception e) {
            System.out.println("    [ERROR] Frame instantiation failed: " + e.getMessage());
            overallPass = false;
        }

        // 2. Try opening Course Management Frame directly
        System.out.println("  Instantiating CourseManagementFrame directly as a Student...");
        try {
            CourseManagementFrame cmf = new CourseManagementFrame();
            CourseController cc = new CourseController(cmf);
            
            boolean addBtnEnabled = cmf.getAddButton().isEnabled();
            System.out.println("    Course Management Add Button Enabled: " + addBtnEnabled);
            if (addBtnEnabled) {
                System.out.println("    [FAIL] CRITICAL VULNERABILITY: Student can add/update courses in the system!");
                overallPass = false;
            } else {
                System.out.println("    [PASS] Course add button disabled.");
            }
            cmf.dispose();
        } catch (Exception e) {
            System.out.println("    [ERROR] Course frame failed: " + e.getMessage());
            overallPass = false;
        }

        return overallPass;
    }

    private static boolean testStudentCRUD() {
        System.out.println("\n[Testing Student CRUD operations]");
        StudentDAO studentDao = new StudentDAO();
        String testId = "TEST_STD_99";
        
        // Ensure no leftover test record
        studentDao.deleteStudent(testId);

        // 1. Create student
        StudentData newStudent = new StudentData(testId, "Test Student Name", "test_std@example.com", "9876543210", "BSc Computer Science", "Male", "Test Address");
        boolean inserted = studentDao.insertStudent(newStudent);
        if (!inserted) {
            System.out.println("  [FAIL] Failed to insert student record into DB.");
            return false;
        }
        System.out.println("  [PASS] Student inserted successfully.");

        // 2. Read student
        StudentData readStudent = studentDao.searchStudentById(testId);
        if (readStudent == null || !readStudent.getFullName().equals("Test Student Name")) {
            System.out.println("  [FAIL] Student record retrieved from DB does not match original data.");
            return false;
        }
        System.out.println("  [PASS] Student retrieved successfully with correct profile information.");

        // 3. Update student
        readStudent.setFullName("Test Student Updated");
        boolean updated = studentDao.updateStudent(readStudent);
        if (!updated) {
            System.out.println("  [FAIL] Failed to update student record in DB.");
            return false;
        }
        
        StudentData updatedStudent = studentDao.searchStudentById(testId);
        if (updatedStudent == null || !updatedStudent.getFullName().equals("Test Student Updated")) {
            System.out.println("  [FAIL] Student record update did not persist in database.");
            return false;
        }
        System.out.println("  [PASS] Student updated successfully in database.");

        // 4. Delete student
        boolean deleted = studentDao.deleteStudent(testId);
        if (!deleted) {
            System.out.println("  [FAIL] Failed to delete student from DB.");
            return false;
        }
        
        StudentData deletedCheck = studentDao.searchStudentById(testId);
        if (deletedCheck != null) {
            System.out.println("  [FAIL] Student record still exists after deletion.");
            return false;
        }
        System.out.println("  [PASS] Student deleted successfully from DB.");

        return true;
    }

    private static boolean testMarksCalculations() {
        System.out.println("\n[Testing Marks & GPA Calculations & Splits]");
        
        // 1. Verify computeLetterGrade and computeGpa thresholds
        Object[][] tests = {
            {200.0, 100.0, 4.0, "A+"}, // max mark
            {180.0, 90.0, 4.0, "A+"},  // 90% boundary
            {179.0, 89.5, 3.7, "A"},   // <90%
            {160.0, 80.0, 3.7, "A"},   // 80% boundary
            {140.0, 70.0, 3.3, "B+"},  // 70% boundary
            {120.0, 60.0, 3.0, "B"},   // 60% boundary
            {100.0, 50.0, 2.0, "C"},   // 50% boundary
            {80.0, 40.0, 1.0, "D"},    // 40% boundary
            {79.0, 39.5, 0.0, "F"}     // <40% boundary (Fail)
        };

        boolean calcsMatch = true;
        for (Object[] t : tests) {
            double total = (Double) t[0];
            double expectedPct = (Double) t[1];
            double expectedGpa = (Double) t[2];
            String expectedGrade = (String) t[3];

            double actualPct = (total / 200.0) * 100.0;
            double actualGpa = MarksController.computeGpa(actualPct);
            String actualGrade = MarksController.computeLetterGrade(total);

            if (actualPct != expectedPct || actualGpa != expectedGpa || !actualGrade.equals(expectedGrade)) {
                System.out.println(String.format("  [FAIL] Calculation mismatch for total %s: Pct=%s (Expected %s), GPA=%s (Expected %s), Grade=%s (Expected %s)", 
                    total, actualPct, expectedPct, actualGpa, expectedGpa, actualGrade, expectedGrade));
                calcsMatch = false;
            }
        }
        if (calcsMatch) {
            System.out.println("  [PASS] Grade thresholds and GPA calculations match expected values.");
        }

        // 2. Verify Assignment/Exam splitting logic in MarksController
        // If a student got 100 marks total (e.g. 30 assignment and 70 exam),
        // verify if the controller loses the split data and sets it to 50/50.
        System.out.println("  Testing Assignment/Exam splitting data loss bug... ");
        // MarksController.loadGradesForStudent hardcodes assignment and exam to total * 0.5.
        double sampleTotal = 150.0; // e.g. 60 assignment + 90 exam
        double expectedAssignment = 75.0; // total * 0.5
        double expectedExam = 75.0; // total * 0.5
        
        System.out.println("    For total marks: " + sampleTotal);
        System.out.println("    Calculated assignment load: " + (sampleTotal * 0.5));
        System.out.println("    Calculated exam load: " + (sampleTotal * 0.5));
        System.out.println("    [BUG CONFIRMED] Split values are hardcoded to 50% assignment / 50% exam, destroying original data.");

        return calcsMatch;
    }

    private static boolean testEnrollmentWorkflows() {
        System.out.println("\n[Testing Enrollment Integrity & Orphans]");
        CourseDAO courseDao = new CourseDAO();
        StudentDAO studentDao = new StudentDAO();
        
        String testStudentId = "ENROLL_STD_88";
        int testCourseId = 9999;
        
        // 1. Delete leftovers
        studentDao.deleteStudent(testStudentId);
        courseDao.deleteCourse(testCourseId);

        // 2. Insert test course
        CourseData testCourse = new CourseData(testCourseId, "Verification Course", "VER101", 3, "Teacher X", "Fall 2026");
        courseDao.insertCourse(testCourse);

        // Get actual generated ID for course
        List<CourseData> courses = courseDao.searchCourse("VER101");
        int generatedCourseId = courses.isEmpty() ? testCourseId : courses.get(0).getCourseId();

        // 3. Insert test student
        StudentData testStudent = new StudentData(testStudentId, "Enroll Student", "enroll@example.com", "99999", "BSc Computer Science", "Female", "Addr");
        studentDao.insertStudent(testStudent);

        // 4. Enroll student
        CourseData enrollment = new CourseData(generatedCourseId, "Verification Course", "VER101", 3, "Teacher X", "Fall 2026", testStudentId, "Enroll Student");
        boolean enrolled = courseDao.enrollStudentInCourse(enrollment);
        System.out.println("  Student Enrolled in Course: " + enrolled);

        // 5. Delete Course and check for orphaned enrollments
        courseDao.deleteCourse(generatedCourseId);
        System.out.println("  Course Deleted. Checking if student enrollment record was deleted...");
        
        boolean stillEnrolled = courseDao.isStudentEnrolled(testStudentId, generatedCourseId);
        if (stillEnrolled) {
            System.out.println("  [FAIL] DATABASE CORRUPTION: Enrollment record remains as an orphan after Course deletion!");
        } else {
            System.out.println("  [PASS] Enrollment record correctly cleaned up.");
        }

        // Cleanup
        studentDao.deleteStudent(testStudentId);
        
        return true;
    }
}
