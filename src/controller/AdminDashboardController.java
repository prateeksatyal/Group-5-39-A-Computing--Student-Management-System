package controller;

import dao.AttendanceDAO;
import dao.CourseDAO;
import dao.StudentDAO;
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
import view.AdminDashboard;
import view.AdminDashboard.VectorIcon;
import view.StudentManagementFrame;
import view.CourseManagementFrame;
import view.AttendanceSummaryFrame;
import view.AcademicPerformanceFrame;
import view.GradeComputationFrame;
import view.GenerateResultFrame;
import view.DownloadResultFrame;
import view.EditProfileFrame;
import view.ViewStudentProfile;

public class AdminDashboardController {

    private final AdminDashboard view;
    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    public AdminDashboardController(AdminDashboard view) {
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
        setupNavigation();
        setupInteractiveEffects();
        LogoutController.wireLogout(view, view.getLogoutButton());
    }

    // ── Live Dashboard Stats ──────────────────────────────────────────────────

    private void loadDashboardStats() {
        try {
            // Card 1: Active Students count
            List<StudentData> students = studentDAO.getAllStudents();
            view.getCard1NumLabel().setText(String.format("%,d", students.size()));

            // Card 2: Total Courses count
            List<CourseData> courses = courseDAO.getAllCourses();
            view.getCard2NumLabel().setText(String.valueOf(courses.size()));

            // Card 3: Average Attendance rate
            List<AttendanceData> allAttendance = attendanceDAO.getAllAttendance();
            if (!allAttendance.isEmpty()) {
                double avg = allAttendance.stream()
                    .mapToDouble(AttendanceData::getAttendancePercentage)
                    .average()
                    .orElse(0.0);
                view.getCard3NumLabel().setText(String.format("%.0f%%", avg));
            } else {
                view.getCard3NumLabel().setText("N/A");
            }

            // Card 4: Total Enrollments
            List<CourseData> enrollments = courseDAO.getAssignedCourses();
            view.getCard4NumLabel().setText(String.valueOf(enrollments.size()));

        } catch (Exception e) {
            System.err.println("AdminDashboardController.loadDashboardStats error: " + e.getMessage());
        }
    }

    // ── Sidebar Navigation ────────────────────────────────────────────────────

    private void setupNavigation() {
        // Dashboard Button — refresh live stats
        view.getDashboardButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Dashboard button clicked — refreshing stats");
            loadDashboardStats();
        });

        // Students Management Button
        view.getStudentsButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Students button clicked");
            view.dispose();
            EventQueue.invokeLater(() -> {
                try {
                    StudentManagementFrame frame = new StudentManagementFrame();
                    new controller.StudentController(frame);
                    frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error opening Students Management:\n" + ex.getMessage(),
                        "Navigation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        // Courses Management Button
        view.getCoursesButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Courses button clicked");
            view.dispose();
            EventQueue.invokeLater(() -> {
                try {
                    CourseManagementFrame frame = new CourseManagementFrame();
                    new controller.CourseController(frame);
                    frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error opening Course Management:\n" + ex.getMessage(),
                        "Navigation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        // Attendance Management Button
        view.getAttendanceButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Attendance button clicked");
            view.dispose();
            EventQueue.invokeLater(() -> {
                try {
                    AttendanceSummaryFrame frame = new AttendanceSummaryFrame();
                    new controller.AttendanceController(frame);
                    frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error opening Attendance Management:\n" + ex.getMessage(),
                        "Navigation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        // Academic Performance Button
        view.getPerformanceButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Academic Performance button clicked");
            view.dispose();
            EventQueue.invokeLater(() -> {
                try {
                    AcademicPerformanceFrame frame = new AcademicPerformanceFrame("Admin");
                    new controller.MarksController(frame, "Admin");
                    frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error opening Academic Performance:\n" + ex.getMessage(),
                        "Navigation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        // Grade Computation Button
        view.getGradeButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Grade Computation button clicked");
            view.dispose();
            EventQueue.invokeLater(() -> {
                try {
                    GradeComputationFrame frame = new GradeComputationFrame();
                    new MarksController(frame);
                    frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error opening Grade Computation:\n" + ex.getMessage(),
                        "Navigation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        // Result Generation Button
        view.getResultButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Result Generation button clicked");
            view.dispose();
            EventQueue.invokeLater(() -> {
                try {
                    GenerateResultFrame frame = new GenerateResultFrame();
                    new ResultController(frame);
                    frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error opening Result Generation:\n" + ex.getMessage(),
                        "Navigation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        // Reports/Export Button
        view.getReportsButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Reports Export button clicked");
            view.dispose();
            EventQueue.invokeLater(() -> {
                try {
                    DownloadResultFrame frame = new DownloadResultFrame();
                    new ResultController(frame);
                    frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error opening Reports Export:\n" + ex.getMessage(),
                        "Navigation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        // Profile Button — open ViewStudentProfile for admin
        view.getProfileButton().addActionListener(e -> {
            System.out.println("[AdminDashboard] Profile button clicked");
            view.dispose();
            EventQueue.invokeLater(() -> {
                try {
                    ViewStudentProfile profileView = new ViewStudentProfile();
                    new ViewStudentProfileController(profileView);
                    profileView.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error opening Profile:\n" + ex.getMessage(),
                        "Navigation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }

    // ── Interactive Effects (hover, focus, active menu) ───────────────────────

    private void setupInteractiveEffects() {
        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getStudentsButton(),
            view.getCoursesButton(),
            view.getAttendanceButton(),
            view.getPerformanceButton(),
            view.getGradeButton(),
            view.getResultButton(),
            view.getReportsButton(),
            view.getProfileButton(),
            view.getLogoutButton()
        };

        for (JButton btn : buttons) {
            btn.addActionListener(e -> setActiveMenuItem(btn));
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

    private void setupMenuIcons() {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        view.getTitleLabel().setText("SMS");
        view.getTitleLabel().setIcon(new VectorIcon("hamburger", 20, whiteColor));
        view.getTitleLabel().setIconTextGap(12);
        view.getDashboardButton().setText("Dashboard");
        view.getDashboardButton().setIconTextGap(12);
        view.getStudentsButton().setText("Students Management");
        view.getStudentsButton().setIconTextGap(12);
        view.getCoursesButton().setText("Courses Management");
        view.getCoursesButton().setIconTextGap(12);
        view.getAttendanceButton().setText("Attendance Management");
        view.getAttendanceButton().setIconTextGap(12);
        view.getPerformanceButton().setText("Academic Performance");
        view.getPerformanceButton().setIconTextGap(12);
        view.getGradeButton().setText("Grade Computation");
        view.getGradeButton().setIconTextGap(12);
        view.getResultButton().setText("Result Generation");
        view.getResultButton().setIconTextGap(12);
        view.getReportsButton().setText("Reports Export");
        view.getReportsButton().setIconTextGap(12);
        view.getProfileButton().setText("Profile");
        view.getProfileButton().setIconTextGap(12);
        view.getLogoutButton().setText("Logout");
        view.getLogoutButton().setIconTextGap(12);
        
        setActiveMenuItem(view.getDashboardButton());

        view.getCard1IconLabel().setText("");
        view.getCard1IconLabel().setIcon(new VectorIcon("students", 40, activeColor));
        view.getCard2IconLabel().setText("");
        view.getCard2IconLabel().setIcon(new VectorIcon("courses", 40, activeColor));
        view.getCard3IconLabel().setText("");
        view.getCard3IconLabel().setIcon(new VectorIcon("attendance", 40, activeColor));
        view.getCard4IconLabel().setText("");
        view.getCard4IconLabel().setIcon(new VectorIcon("reports", 40, activeColor));
    }

    private void setActiveMenuItem(JButton activeBtn) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        Color activeBg = new Color(243, 227, 225);
        Color normalColor = new Color(11, 27, 226);
        Color normalBg = new Color(224, 242, 248);
        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getStudentsButton(),
            view.getCoursesButton(),
            view.getAttendanceButton(),
            view.getPerformanceButton(),
            view.getGradeButton(),
            view.getResultButton(),
            view.getReportsButton(),
            view.getProfileButton(),
            view.getLogoutButton()
        };
        String[] types = new String[]{"dashboard", "students", "courses", "attendance", "performance", "grade", "result", "reports", "profile", "logout"};
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
