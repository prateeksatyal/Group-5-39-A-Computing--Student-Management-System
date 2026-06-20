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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import model.AttendanceData;
import model.CourseData;
import view.AcademicPerformanceFrame;
import view.AttendanceSummaryFrame;
import view.CourseManagementFrame;
import view.GradeComputationFrame;
import view.GenerateResultFrame;
import view.StudentManagementFrame;
import view.TeacherDashboard;
import view.ViewAssignedCourseFrame;
import view.ViewStudentProfile;

/**
 * MVC Controller for TeacherDashboard.
 * Wires all sidebar navigation, hover effects, logout, and live stat cards.
 * All logic previously inside TeacherDashboard constructor is now here.
 */
public class TeacherDashboardController {

    private final TeacherDashboard view;
    private final CourseDAO courseDAO = new CourseDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    public TeacherDashboardController(TeacherDashboard view) {
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
        loadDashboardStats();
        wireSidebarNavigation();
        setupInteractiveEffects();
        LogoutController.wireLogout(view, view.getLogoutButton());
    }

    // ── Live Dashboard Stats ──────────────────────────────────────────────────

    private void loadDashboardStats() {
        try {
            // Card 1: Total enrolled students (unique)
            List<CourseData> enrollments = courseDAO.getAssignedCourses();
            long uniqueStudents = enrollments.stream()
                .map(CourseData::getStudentId)
                .filter(id -> id != null && !id.isEmpty())
                .distinct()
                .count();
            view.getCard1NumLabel().setText(String.valueOf(uniqueStudents));

            // Card 2: Total active courses
            List<CourseData> courses = courseDAO.getAllCourses();
            view.getCard2NumLabel().setText(String.valueOf(courses.size()));

            // Card 3: Average attendance rate across all students
            List<AttendanceData> allAttendance = attendanceDAO.getAllAttendance();
            if (!allAttendance.isEmpty()) {
                double avg = allAttendance.stream()
                    .mapToDouble(AttendanceData::getAttendancePercentage)
                    .average()
                    .orElse(0.0);
                view.getCard3NumLabel().setText(String.format("%.1f%%", avg));
            } else {
                view.getCard3NumLabel().setText("N/A");
            }

            // Card 4: Class average GPA placeholder (no marks DAO yet)
            view.getCard4NumLabel().setText("—");

        } catch (Exception e) {
            System.err.println("TeacherDashboardController.loadDashboardStats error: " + e.getMessage());
        }
    }

    // ── Sidebar Navigation ────────────────────────────────────────────────────

    private void wireSidebarNavigation() {
        // Dashboard (current page — refresh stats)
        view.getDashboardButton().addActionListener(e -> loadDashboardStats());

        // View Profile
        view.getViewProfileButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                ViewStudentProfile profileView = new ViewStudentProfile();
                new ViewStudentProfileController(profileView);
                profileView.setVisible(true);
            });
        });

        // Student Manager — Teacher can VIEW students (read-only display via StudentManagementFrame)
        view.getStudentManagerButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                StudentManagementFrame frame = new StudentManagementFrame();
                new StudentController(frame);
                frame.setVisible(true);
            });
        });

        // Marks Module
        view.getMarksButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.AcademicPerformanceFrame frame = new view.AcademicPerformanceFrame("Teacher");
                new MarksController(frame, "Teacher");
                frame.setVisible(true);
            });
        });

        // Grade Computation
        view.getGradeButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                GradeComputationFrame frame = new GradeComputationFrame();
                new MarksController(frame);
                frame.setVisible(true);
            });
        });

        // Generate Results
        view.getGenerateResultButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                GenerateResultFrame frame = new GenerateResultFrame();
                new ResultController(frame);
                frame.setVisible(true);
            });
        });

        // Assigned Courses
        view.getAssignedCoursesButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                ViewAssignedCourseFrame frame = new ViewAssignedCourseFrame();
                new CourseController(frame);
                frame.setVisible(true);
            });
        });
    }

    // ── UI Behavior and Custom Icons ──────────────────────────────────────────

    private void setupMenuIcons() {
        try {
            view.getDashboardButton().setIcon(new ImageIcon(view.getClass().getResource("/images/nav_Dashboard.png")));
            view.getViewProfileButton().setIcon(new ImageIcon(view.getClass().getResource("/images/nav_Profile.png")));
            view.getStudentManagerButton().setIcon(new ImageIcon(view.getClass().getResource("/images/nav_Student.png")));
            view.getMarksButton().setIcon(new ImageIcon(view.getClass().getResource("/images/nav_Grade.png")));
            view.getGradeButton().setIcon(new ImageIcon(view.getClass().getResource("/images/nav_Grade.png")));
            view.getGenerateResultButton().setIcon(new ImageIcon(view.getClass().getResource("/images/nav_Result.png")));
            view.getAssignedCoursesButton().setIcon(new ImageIcon(view.getClass().getResource("/images/nav_Courses.png")));
            view.getLogoutButton().setIcon(new ImageIcon(view.getClass().getResource("/images/nav_Logout.png")));
        } catch (Exception exception) {
            // ignore
        }

        // Card icons
        try {
            view.getCard1IconLabel().setIcon(new ImageIcon(view.getClass().getResource("/images/card_Students.png")));
            view.getCard2IconLabel().setIcon(new ImageIcon(view.getClass().getResource("/images/card_Courses.png")));
            view.getCard3IconLabel().setIcon(new ImageIcon(view.getClass().getResource("/images/card_Attendance.png")));
            view.getCard4IconLabel().setIcon(new ImageIcon(view.getClass().getResource("/images/card_Grade.png")));
        } catch (Exception exception) {
            // ignore
        }

        // Set up initial active state highlighting
        setActiveMenuItem(view.getDashboardButton());
    }

    private void setupInteractiveEffects() {
        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getViewProfileButton(),
            view.getStudentManagerButton(),
            view.getMarksButton(),
            view.getGradeButton(),
            view.getGenerateResultButton(),
            view.getAssignedCoursesButton(),
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
                if (!btn.getBackground().equals(new Color(243, 227, 225))) {
                    btn.setContentAreaFilled(true);
                    btn.setBackground(new Color(50, 70, 90));
                }
                btn.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 1));
            }

            @Override
            public void focusLost(FocusEvent e) {
                btn.setBorder(null);
                if (!btn.getBackground().equals(new Color(243, 227, 225))) {
                    btn.setContentAreaFilled(false);
                    btn.setBackground(new Color(28, 39, 50));
                }
            }
        });

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!btn.getBackground().equals(new Color(243, 227, 225))) {
                    btn.setContentAreaFilled(true);
                    btn.setBackground(new Color(40, 55, 70));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!btn.getBackground().equals(new Color(243, 227, 225))) {
                    if (!btn.isFocusOwner()) {
                        btn.setContentAreaFilled(false);
                        btn.setBackground(new Color(28, 39, 50));
                    } else {
                        btn.setBackground(new Color(50, 70, 90));
                    }
                }
            }
        });
    }

    private void setActiveMenuItem(JButton activeBtn) {
        Color activeBg = new Color(243, 227, 225);
        Color activeFg = new Color(11, 27, 226);
        Color normalFg = Color.WHITE;

        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getViewProfileButton(),
            view.getStudentManagerButton(),
            view.getMarksButton(),
            view.getGradeButton(),
            view.getGenerateResultButton(),
            view.getAssignedCoursesButton(),
            view.getLogoutButton()
        };

        for (JButton btn : buttons) {
            if (btn == activeBtn) {
                btn.setBackground(activeBg);
                btn.setForeground(activeFg);
                btn.setContentAreaFilled(true);
                btn.setOpaque(true);
            } else {
                btn.setBackground(new Color(28, 39, 50));
                btn.setForeground(normalFg);
                btn.setContentAreaFilled(false);
                btn.setOpaque(false);
            }
        }
    }
}
