package controller;

import dao.AttendanceDAO;
import dao.CourseDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.AttendanceData;
import model.CourseData;
import model.StudentData;
import model.UserData;
import view.AttendanceSummaryFrame;
import view.DownloadResultFrame;
import view.StudentDashboard;
import view.StudentDashboard.VectorIcon;
import view.ViewAssignedCourseFrame;
import view.ViewResultFrame;
import view.ViewStudentProfile;

/**
 * MVC Controller for StudentDashboard.
 * Wires all sidebar navigation, live stat cards, and personalised welcome.
 * All logic previously inside StudentDashboard constructor is now here.
 * Student privacy: stat cards filter by the logged-in student_id only.
 */
public class StudentDashboardController {

    private final StudentDashboard view;
    private final CourseDAO courseDAO = new CourseDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final dao.StudentDAO studentDAO = new dao.StudentDAO();

    public StudentDashboardController(StudentDashboard view) {
        this.view = view;

        // Window Opened focus requester
        this.view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                view.getContentPane().setFocusable(true);
                view.getContentPane().requestFocusInWindow();
            }
        });

        setupMenuIcons();
        loadPersonalisedStats();
        wireSidebarNavigation();
        setupInteractiveEffects();
        LogoutController.wireLogout(view, view.getLogoutButton());
    }

    // ── Personalised Stats (scoped to logged-in student) ─────────────────────

    private void loadPersonalisedStats() {
        try {
            UserData user = UserSession.getCurrentUser();
            if (user == null) return;

            // Look up student record by email (session stores email as username)
            String loginEmail = user.getUserName();
            StudentData student = studentDAO.searchStudentByEmail(loginEmail);
            if (student == null) student = studentDAO.searchStudentById(loginEmail);

            // Personalised welcome
            if (student != null) {
                view.getWelcomeLabel().setText("Welcome, " + student.getFullName() + "!");
            }

            // Card 1: enrolled courses count for this student
            String studentId = (student != null) ? student.getStudentId() : loginEmail;
            List<CourseData> myCourses = courseDAO.getAssignedCourses();
            long myCoursesCount = myCourses.stream()
                .filter(c -> studentId.equals(c.getStudentId()))
                .count();
            view.getCard3NumLabel().setText(String.valueOf(myCoursesCount));

            // Card 2: personal attendance rate
            List<AttendanceData> myAttendance = attendanceDAO.searchAttendanceByStudentId(studentId);
            if (!myAttendance.isEmpty()) {
                double avg = myAttendance.stream()
                    .mapToDouble(AttendanceData::getAttendancePercentage)
                    .average()
                    .orElse(0.0);
                view.getCard2NumLabel().setText(String.format("%.0f%%", avg));
            } else {
                view.getCard2NumLabel().setText("N/A");
            }

        } catch (Exception e) {
            System.err.println("StudentDashboardController.loadPersonalisedStats error: " + e.getMessage());
        }
    }

    // ── Sidebar Navigation ────────────────────────────────────────────────────

    private void wireSidebarNavigation() {
        // Dashboard (refresh)
        view.getDashboardButton().addActionListener(e -> loadPersonalisedStats());

        // View Profile
        view.getViewProfileButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                ViewStudentProfile profileView = new ViewStudentProfile();
                new ViewStudentProfileController(profileView);
                profileView.setVisible(true);
            });
        });

        // Attendance Summary — student sees ONLY their own records
        view.getAttendanceButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                AttendanceSummaryFrame f = new AttendanceSummaryFrame();
                // Wire controller — the Mark Attendance panel is present but
                // students cannot save because their role check will block DB writes.
                // The table auto-filters by their student ID on load.
                AttendanceController ac = new AttendanceController(f);
                // Pre-filter to student's own ID
                String studentId = resolveStudentId();
                if (studentId != null && !studentId.isEmpty()) {
                    ac.searchAttendance(studentId);
                }
                f.setVisible(true);
                // Disable the Mark Attendance save button for students
                f.getMarkSaveButton().setEnabled(false);
                f.getMarkSaveButton().setToolTipText("Only teachers and admins can mark attendance.");
            });
        });

        // Enrolled Courses
        view.getEnrolledCoursesButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                ViewAssignedCourseFrame f = new ViewAssignedCourseFrame();
                new CourseController(f);
                f.setVisible(true);
            });
        });

        // View Results — navigate to ViewResultFrame
        view.getViewResultsButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                ViewResultFrame vrFrame = new ViewResultFrame();
                new ResultController(vrFrame);
                vrFrame.setVisible(true);
            });
        });

        // Download Result — navigate to DownloadResultFrame
        view.getDownloadResultButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                DownloadResultFrame dlFrame = new DownloadResultFrame();
                new ResultController(dlFrame);
                dlFrame.setVisible(true);
            });
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

    private void setupMenuIcons() {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        view.getTitleLabel().setText("SMS");
        view.getTitleLabel().setIcon(new VectorIcon("hamburger", 20, whiteColor));
        view.getTitleLabel().setIconTextGap(12);
        view.getDashboardButton().setText("Dashboard");
        view.getDashboardButton().setIconTextGap(12);
        view.getViewProfileButton().setText("View Profile");
        view.getViewProfileButton().setIconTextGap(12);
        view.getAttendanceButton().setText("Attendance Summary");
        view.getAttendanceButton().setIconTextGap(12);
        view.getEnrolledCoursesButton().setText("Enrolled Courses");
        view.getEnrolledCoursesButton().setIconTextGap(12);
        view.getViewResultsButton().setText("View Results");
        view.getViewResultsButton().setIconTextGap(12);
        view.getDownloadResultButton().setText("Download Result");
        view.getDownloadResultButton().setIconTextGap(12);
        view.getLogoutButton().setText("Logout");
        view.getLogoutButton().setIconTextGap(12);
        
        setActiveMenuItem(view.getDashboardButton());

        view.getCard1IconLabel().setText("");
        view.getCard1IconLabel().setIcon(new VectorIcon("profile", 40, activeColor));
        view.getCard2IconLabel().setText("");
        view.getCard2IconLabel().setIcon(new VectorIcon("attendance", 40, activeColor));
        view.getCard3IconLabel().setText("");
        view.getCard3IconLabel().setIcon(new VectorIcon("courses", 40, activeColor));
        view.getCard4IconLabel().setText("");
        view.getCard4IconLabel().setIcon(new VectorIcon("result", 40, activeColor));
    }

    private void setupInteractiveEffects() {
        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getViewProfileButton(),
            view.getAttendanceButton(),
            view.getEnrolledCoursesButton(),
            view.getViewResultsButton(),
            view.getDownloadResultButton(),
            view.getLogoutButton()
        };

        for (JButton btn : buttons) {
            if (btn != view.getLogoutButton()) {
                btn.addActionListener(e -> setActiveMenuItem(btn));
            }
            addHoverAndFocusEffects(btn);
        }
    }

    private void addHoverAndFocusEffects(final JButton btn) {
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                btn.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                btn.setBorder(null);
            }
        });

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.getBackground().equals(new Color(224, 242, 248))) {
                    btn.setBackground(new Color(200, 235, 245));
                } else if (btn.getBackground().equals(new Color(243, 227, 225))) {
                    btn.setBackground(new Color(233, 212, 209));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.getBackground().equals(new Color(200, 235, 245))) {
                    btn.setBackground(new Color(224, 242, 248));
                } else if (btn.getBackground().equals(new Color(233, 212, 209))) {
                    btn.setBackground(new Color(243, 227, 225));
                }
            }
        });
    }

    private void setActiveMenuItem(JButton activeBtn) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        Color activeBg = new Color(243, 227, 225);
        Color normalColor = new Color(11, 27, 226);
        Color normalBg = new Color(224, 242, 248);
        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getViewProfileButton(),
            view.getAttendanceButton(),
            view.getEnrolledCoursesButton(),
            view.getViewResultsButton(),
            view.getDownloadResultButton(),
            view.getLogoutButton()
        };
        String[] types = new String[]{"dashboard", "profile", "attendance", "courses", "result", "reports", "logout"};
        for (int i = 0; i < buttons.length; ++i) {
            JButton btn = buttons[i];
            String type = types[i];
            if (btn == activeBtn) {
                btn.setBackground(activeBg);
                btn.setForeground(activeColor);
                btn.setContentAreaFilled(true);
                btn.setOpaque(true);
                btn.setIcon(new VectorIcon(type, 28, whiteColor));
                continue;
            }
            btn.setBackground(normalBg);
            btn.setForeground(normalColor);
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setIcon(new VectorIcon(type, 28, activeColor));
        }
    }
}
