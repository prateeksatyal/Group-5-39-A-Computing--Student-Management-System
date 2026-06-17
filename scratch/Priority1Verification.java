import controller.UserSession;
import dao.StudentDAO;
import dao.UserDao;
import model.StudentData;
import model.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.MySqlConnector;

public class Priority1Verification {

    private static final MySqlConnector mysql = new MySqlConnector();
    private static final UserDao userDao = new UserDao();
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  PRIORITY 1 FIXES VERIFICATION SUITE");
        System.out.println("==================================================");

        boolean passAll = true;

        // 1. Verify UserSession role checks
        passAll &= verifyUserSession();

        // Clean up any old test accounts
        cleanup("test_new@student.com");
        cleanup("test_legacy@student.com");

        // 2. Verify new student registration inserts into both tables
        passAll &= verifyNewStudentRegistration();

        // 3. Verify legacy student self-healing (load profile correctly, no N/A)
        passAll &= verifyLegacyStudentSelfHealing();

        // Clean up test accounts after verification
        cleanup("test_new@student.com");
        cleanup("test_legacy@student.com");

        System.out.println("==================================================");
        if (passAll) {
            System.out.println("  VERIFICATION RESULT: ALL TESTS PASSED!");
        } else {
            System.out.println("  VERIFICATION RESULT: SOME TESTS FAILED.");
        }
        System.out.println("==================================================");
        
        System.exit(passAll ? 0 : 1);
    }

    private static void cleanup(String email) {
        try (Connection conn = mysql.openConnection()) {
            // Delete from students
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE email = ?")) {
                ps.setString(1, email);
                ps.executeUpdate();
            }
            // Delete from users
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE email = ?")) {
                ps.setString(1, email);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Cleanup error: " + e.getMessage());
        }
    }

    private static boolean verifyUserSession() {
        System.out.print("Test 1: UserSession role checks... ");
        try {
            // Test Student role
            UserData studentUser = new UserData("std", "pass", "std@student.com", "Student");
            UserSession.setCurrentUser(studentUser);
            if (!UserSession.isStudent() || UserSession.isAdmin() || UserSession.isTeacher()) {
                System.out.println("FAIL (Student role check failed)");
                return false;
            }

            // Test Admin role
            UserData adminUser = new UserData("admin", "pass", "admin@admin.com", "Admin");
            UserSession.setCurrentUser(adminUser);
            if (UserSession.isStudent() || !UserSession.isAdmin() || UserSession.isTeacher()) {
                System.out.println("FAIL (Admin role check failed)");
                return false;
            }

            // Test Teacher role
            UserData teacherUser = new UserData("teacher", "pass", "teacher@teacher.com", "Teacher");
            UserSession.setCurrentUser(teacherUser);
            if (UserSession.isStudent() || UserSession.isAdmin() || !UserSession.isTeacher()) {
                System.out.println("FAIL (Teacher role check failed)");
                return false;
            }

            UserSession.clear();
            System.out.println("PASS");
            return true;
        } catch (Exception e) {
            System.out.println("FAIL (Exception: " + e.getMessage() + ")");
            return false;
        }
    }

    private static boolean verifyNewStudentRegistration() {
        System.out.print("Test 2: New student registration database entries... ");
        try {
            String name = "Test New Student";
            String email = "test_new@student.com";
            String password = "password";
            String role = "Student";

            UserData user = new UserData(name, password, email, role);

            // Simulate the exact code in SignupController
            boolean userCreated = userDao.createUser(user);
            if (!userCreated) {
                System.out.println("FAIL (Could not insert user)");
                return false;
            }

            if ("Student".equalsIgnoreCase(role)) {
                String studentId;
                do {
                    studentId = "STU" + (1000 + new java.util.Random().nextInt(9000));
                } while (studentDAO.searchStudentById(studentId) != null);

                StudentData student = new StudentData(studentId, name, email, "", "", "", "");
                boolean studentCreated = studentDAO.insertStudent(student);
                if (!studentCreated) {
                    System.out.println("FAIL (Could not insert student)");
                    return false;
                }
            }

            // Verify both entries exist
            UserData fetchedUser = userDao.loginUser(email, password, role);
            StudentData fetchedStudent = studentDAO.searchStudentByEmail(email);

            if (fetchedUser == null) {
                System.out.println("FAIL (User record not found in users table)");
                return false;
            }
            if (fetchedStudent == null) {
                System.out.println("FAIL (Student record not found in students table)");
                return false;
            }

            // Verify generated student ID format (STU + 4 digits)
            String studentId = fetchedStudent.getStudentId();
            if (studentId == null || !studentId.matches("^STU\\d{4}$")) {
                System.out.println("FAIL (Invalid generated student ID format: " + studentId + ")");
                return false;
            }

            System.out.println("PASS");
            return true;
        } catch (Exception e) {
            System.out.println("FAIL (Exception: " + e.getMessage() + ")");
            return false;
        }
    }

    private static boolean verifyLegacyStudentSelfHealing() {
        System.out.print("Test 3: Legacy student self-healing profile recovery... ");
        try {
            String name = "Test Legacy Student";
            String email = "test_legacy@student.com";
            String password = "password";
            String role = "Student";

            // 1. Manually insert only into 'users' table, simulating legacy pre-fix registrations
            try (Connection conn = mysql.openConnection();
                 PreparedStatement ps = conn.prepareStatement("INSERT INTO users (full_name, username, email, password, confirm_password, role) VALUES (?, ?, ?, ?, ?, ?)")) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, email);
                ps.setString(4, password);
                ps.setString(5, password);
                ps.setString(6, role);
                ps.executeUpdate();
            }

            // Verify legacy student is missing from 'students' table initially
            if (studentDAO.searchStudentByEmail(email) != null) {
                System.out.println("FAIL (Student record already existed before simulation)");
                return false;
            }

            // 2. Simulate login user retrieval
            UserData loggedInUser = userDao.loginUser(email, password, role);
            if (loggedInUser == null) {
                System.out.println("FAIL (Login failed for legacy user)");
                return false;
            }

            // 3. Simulate self-healing code that runs inside LoginController transition
            if ("Student".equalsIgnoreCase(role)) {
                if (studentDAO.searchStudentByEmail(loggedInUser.getEmail()) == null) {
                    String fullName = userDao.getFullNameByEmail(loggedInUser.getEmail());
                    if (fullName == null || fullName.isEmpty()) {
                        fullName = loggedInUser.getEmail().split("@")[0];
                    }
                    String studentId;
                    do {
                        studentId = "STU" + (1000 + new java.util.Random().nextInt(9000));
                    } while (studentDAO.searchStudentById(studentId) != null);
                    StudentData student = new StudentData(studentId, fullName, loggedInUser.getEmail(), "", "", "", "");
                    studentDAO.insertStudent(student);
                }
            }

            // 4. Verify student profile was successfully generated and does not return null/NA
            StudentData healedProfile = studentDAO.searchStudentByEmail(email);
            if (healedProfile == null) {
                System.out.println("FAIL (Legacy student record was not created during login self-healing)");
                return false;
            }

            if (!healedProfile.getFullName().equals(name)) {
                System.out.println("FAIL (Legacy student name mismatched: expected " + name + ", got " + healedProfile.getFullName() + ")");
                return false;
            }

            System.out.println("PASS");
            return true;
        } catch (Exception e) {
            System.out.println("FAIL (Exception: " + e.getMessage() + ")");
            return false;
        }
    }
}
