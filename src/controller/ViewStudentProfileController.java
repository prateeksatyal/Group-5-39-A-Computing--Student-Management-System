package controller;

import dao.StudentDAO;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.StudentData;
import model.UserData;
import view.AdminDashboard;
import view.AttendanceSummaryFrame;
import view.EditProfileFrame;
import view.Login;
import view.Logout;
import view.StudentDashboard;
import view.TeacherDashboard;
import view.ViewAssignedCourseFrame;
import view.ViewStudentProfile;
import javax.swing.ImageIcon;

/**
 * MVC Controller for ViewStudentProfile.
 * Loads profile data from DAO, wires sidebar navigation, hover effects, and logout.
 */
public class ViewStudentProfileController {

    private final ViewStudentProfile view;
    private final StudentDAO studentDAO = new StudentDAO();

    public ViewStudentProfileController(ViewStudentProfile view) {
        this.view = view;
        
        // Use native NetBeans layout sizing and center on screen
        this.view.pack();
        this.view.setLocationRelativeTo(null);
        
        loadProfileData();
        setupMenuIcons();
        wireSidebarListeners();
        wireSidebarHoverEffects();
        wireLogout();
    }

    // ── Data Loading ─────────────────────────────────────────────────────────

    private void loadProfileData() {
        UserData user = UserSession.getCurrentUser();
        if (user == null) return;

        // getUserName() now returns the login email (fixed in UserDao.loginUser)
        String loginEmail = user.getUserName();

        // Primary lookup: find student record by their registered email
        StudentData student = studentDAO.searchStudentByEmail(loginEmail);

        // Fallback: in case email == student_id (some setups use ID as login)
        if (student == null) {
            student = studentDAO.searchStudentById(loginEmail);
        }

        if (student != null) {
            view.getIdValueLabel().setText(student.getStudentId());
            view.getNameValueLabel().setText(student.getFullName());
            view.getEmailValueLabel().setText(student.getEmail());
            view.getPhoneValueLabel().setText(
                (student.getPhoneNumber() != null && !student.getPhoneNumber().isEmpty())
                    ? student.getPhoneNumber() : "N/A");
            view.getCourseValueLabel().setText(
                (student.getCourse() != null && !student.getCourse().isEmpty())
                    ? student.getCourse() : "N/A");
            view.getGenderValueLabel().setText(
                (student.getGender() != null && !student.getGender().isEmpty())
                    ? student.getGender() : "N/A");
            view.getAddressValueLabel().setText(
                (student.getAddress() != null && !student.getAddress().isEmpty())
                    ? student.getAddress() : "N/A");
        } else {
            // No student record found — show whatever the users table gave us
            view.getIdValueLabel().setText("N/A");
            view.getNameValueLabel().setText(user.getEmail()); // email is the best identifier
            view.getEmailValueLabel().setText(user.getEmail());
            view.getPhoneValueLabel().setText("N/A");
            view.getCourseValueLabel().setText("N/A");
            view.getGenderValueLabel().setText("N/A");
            view.getAddressValueLabel().setText("N/A");
        }
    }


    // ── Menu Icons (moved from view to controller) ───────────────────────────

    private void setupMenuIcons() {
        try {
            view.getDashboardButton().setIcon(new ImageIcon(getClass().getResource("/images/home.png")));
            view.getProfileButton().setIcon(new ImageIcon(getClass().getResource("/images/user.png")));
            view.getAttendanceButton().setIcon(new ImageIcon(getClass().getResource("/images/attendance.png")));
            view.getCoursesButton().setIcon(new ImageIcon(getClass().getResource("/images/course.png")));
            view.getResultsButton().setIcon(new ImageIcon(getClass().getResource("/images/result.png")));
            view.getLogoutButton().setIcon(new ImageIcon(getClass().getResource("/images/logout.png")));
        } catch (Exception e) {
            // icons not found — ignore
        }
    }

    // ── Sidebar Navigation Listeners ─────────────────────────────────────────

    private void wireSidebarListeners() {
        // Dashboard
        view.getDashboardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    if (UserSession.isAdmin()) {
                        AdminDashboard adminView = new AdminDashboard();
                        new AdminDashboardController(adminView);
                        adminView.setResizable(true);
                        adminView.pack();
                        adminView.setLocationRelativeTo(null);
                        adminView.setVisible(true);
                    } else if (UserSession.isTeacher()) {
                        TeacherDashboard teacherView = new TeacherDashboard();
                        new TeacherDashboardController(teacherView);
                        teacherView.setResizable(true);
                        teacherView.pack();
                        teacherView.setLocationRelativeTo(null);
                        teacherView.setVisible(true);
                    } else {
                        StudentDashboard studentView = new StudentDashboard();
                        new StudentDashboardController(studentView);
                        studentView.setResizable(true);
                        studentView.pack();
                        studentView.setLocationRelativeTo(null);
                        studentView.setVisible(true);
                    }
                });
            }
        });

        // Attendance
        view.getAttendanceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    AttendanceSummaryFrame f = new AttendanceSummaryFrame();
                    AttendanceController ac = new AttendanceController(f);
                    if (UserSession.isStudent()) {
                        String studentId = resolveStudentId();
                        if (studentId != null && !studentId.isEmpty()) {
                            ac.searchAttendance(studentId);
                        }
                        f.getMarkSaveButton().setEnabled(false);
                        f.getMarkSaveButton().setToolTipText("Only teachers and admins can mark attendance.");
                        f.getMarkClearButton().setEnabled(false);
                        f.getMarkStudentIdField().setEnabled(false);
                        f.getMarkStudentNameField().setEnabled(false);
                        f.getMarkCourseCombo().setEnabled(false);
                        f.getMarkTotalClassesField().setEnabled(false);
                        f.getMarkAttendedField().setEnabled(false);
                        f.getMarkDateField().setEnabled(false);
                    }
                    f.setVisible(true);
                });
            }
        });

        // Courses
        view.getCoursesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    ViewAssignedCourseFrame f = new ViewAssignedCourseFrame();
                    new CourseController(f);
                    f.setVisible(true);
                });
            }
        });

        // Profile (current page — no action needed)
        view.getProfileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Already on profile
            }
        });

        // Results
        view.getResultsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.ViewResultFrame f = new view.ViewResultFrame();
                    new ResultController(f);
                    f.setVisible(true);
                });
            }
        });
    }

    /** Returns the logged-in student's student_id (not email). */
    private String resolveStudentId() {
        try {
            UserData user = UserSession.getCurrentUser();
            if (user == null) return "";
            StudentData s = studentDAO.searchStudentByEmail(user.getUserName());
            if (s != null) return s.getStudentId();
            return user.getUserName(); // fallback
        } catch (Exception e) {
            return "";
        }
    }

    // ── Logout ───────────────────────────────────────────────────────────────

    private void wireLogout() {
        view.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserSession.clearSession();
                view.dispose();
                EventQueue.invokeLater(() -> {
                    Logout logoutView = new Logout();
                    new LogoutController(logoutView);
                    logoutView.setVisible(true);
                });
            }
        });
    }

    // ── Hover Effects ─────────────────────────────────────────────────────────

    private void wireSidebarHoverEffects() {
        javax.swing.JButton[] btns = {
            view.getDashboardButton(),
            view.getAttendanceButton(),
            view.getCoursesButton(),
            view.getResultsButton()
        };
        for (javax.swing.JButton btn : btns) {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setContentAreaFilled(true);
                    btn.setBackground(new Color(40, 55, 70));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setContentAreaFilled(false);
                }
            });
        }
    }
}
