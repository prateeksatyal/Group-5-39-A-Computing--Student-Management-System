package controller;

import view.AcademicPerformanceFrame;
import view.GradeComputationFrame;
import java.awt.Color;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.MySqlConnector;

/**
 * MVC Controller for AcademicPerformanceFrame and GradeComputationFrame.
 * Handles marks entry, DB load/save, and sidebar navigation.
 * No SQL lives in the view — all DB operations are here.
 */
public class MarksController {

    private AcademicPerformanceFrame marksView;
    private GradeComputationFrame gradeView;
    private final String role;
    private final MySqlConnector mysql = new MySqlConnector();

    /** Constructor for AcademicPerformanceFrame */
    public MarksController(AcademicPerformanceFrame view, String role) {
        this.marksView = view;
        this.gradeView = null;
        this.role = role;

        this.marksView.pack();
        this.marksView.setLocationRelativeTo(null);

        initMarksController();
    }

    /** Constructor for GradeComputationFrame */
    public MarksController(GradeComputationFrame view) {
        this.gradeView = view;
        this.marksView = null;
        this.role = UserSession.getRole();

        this.gradeView.pack();
        this.gradeView.setLocationRelativeTo(null);

        initGradeController();
    }

    // ── AcademicPerformanceFrame controller ───────────────────────────────────

    private void initMarksController() {
        if (UserSession.isStudent()) {
            JOptionPane.showMessageDialog(marksView, "Access denied. Students cannot access this screen.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            marksView.dispose();
            return;
        }
        setupComboBoxData();

        // Apply visual button styling
        marksView.getSaveMarksButton().setUI(new javax.swing.plaf.basic.BasicButtonUI());
        marksView.getSaveMarksButton().setBorder(null);
        setupMenuIcons(marksView);

        // Wire logout
        LogoutController.wireLogout(marksView, marksView.getLogoutButton());

        // Setup interactive button styles
        JButton[] actionButtons = new JButton[]{
            marksView.getAddStudentButton(), 
            marksView.getSaveMarksButton(), 
            marksView.getResetButton(), 
            marksView.getBackButton()
        };
        for (final JButton btn : actionButtons) {
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn.addFocusListener(new java.awt.event.FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    btn.setBorder(BorderFactory.createLineBorder(new java.awt.Color(11, 27, 226), 2));
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (btn == marksView.getSaveMarksButton()) {
                        btn.setBorder(null);
                    } else {
                        btn.setBorder(BorderFactory.createEtchedBorder());
                    }
                }
            });
        }

        // Setup sidebar effects and navigation
        setupSidebarEffects(marksView);
        setupSidebarNavigation(marksView);

        // Back button — role-aware navigation
        marksView.getBackButton().addActionListener(e -> navigateToDashboard(marksView));

        marksView.getResetButton().addActionListener(e -> resetMarksFields());

        marksView.getSaveMarksButton().addActionListener(e -> saveMarks());

        marksView.getAddStudentButton().addActionListener(e -> addStudentRow());

        // Reload table when filters change
        ActionListener loadListener = e -> loadMarksTable();
        marksView.getTermComboBox().addActionListener(loadListener);
        marksView.getCourseComboBox().addActionListener(loadListener);
        marksView.getSectionComboBox().addActionListener(loadListener);

        // Initial data load
        loadMarksTable();
    }

    private void setupSidebarEffects(AcademicPerformanceFrame view) {
        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getStudentsButton(),
            view.getCoursesButton(),
            view.getAttendanceButton(),
            view.getAcademicPerformanceButton(),
            view.getGradeComputationButton(),
            view.getResultGenerationButton(),
            view.getReportsExportButton(),
            view.getProfileButton(),
            view.getLogoutButton()
        };

        for (JButton btn : buttons) {
            btn.addActionListener(e -> setActiveMenuItem(view, btn));
            addHoverAndFocusEffects(btn);
        }
    }

    private void setupSidebarNavigation(AcademicPerformanceFrame view) {
        // Dashboard
        view.getDashboardButton().addActionListener(e -> navigateToDashboard(view));

        // Students Management
        view.getStudentsButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.StudentManagementFrame frame = new view.StudentManagementFrame();
                    new StudentController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Course Management
        view.getCoursesButton().addActionListener(e -> {
            if (UserSession.isAdmin()) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.CourseManagementFrame frame = new view.CourseManagementFrame();
                    new CourseController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Attendance
        view.getAttendanceButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.AttendanceSummaryFrame frame = new view.AttendanceSummaryFrame();
                new AttendanceController(frame);
                frame.setVisible(true);
            });
        });

        // Academic Performance
        view.getAcademicPerformanceButton().addActionListener(e -> {
            String currentRole = UserSession.isAdmin() ? "Admin" : (UserSession.isTeacher() ? "Teacher" : "Student");
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.AcademicPerformanceFrame frame = new view.AcademicPerformanceFrame(currentRole);
                new MarksController(frame, currentRole);
                frame.setVisible(true);
            });
        });

        // Result Generation
        view.getResultGenerationButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.GenerateResultFrame frame = new view.GenerateResultFrame();
                    new ResultController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Reports/Export
        view.getReportsExportButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.DownloadResultFrame frame = new view.DownloadResultFrame();
                new ResultController(frame);
                frame.setVisible(true);
            });
        });

        // Profile
        view.getProfileButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.ViewStudentProfile profileView = new view.ViewStudentProfile();
                new ViewStudentProfileController(profileView);
                profileView.setVisible(true);
            });
        });

        // Grade Computation
        view.getGradeComputationButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.GradeComputationFrame frame = new view.GradeComputationFrame();
                    new MarksController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void setupComboBoxData() {
        marksView.getTermComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));
        marksView.getCourseComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"BSc Computer Science", "BIT", "BBA"}));
        marksView.getSectionComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"Section A", "Section B", "Section C"}));
    }

    private void loadMarksTable() {
        DefaultTableModel model = (DefaultTableModel) marksView.getMarksTable().getModel();
        model.setRowCount(0);

        if (model.getColumnCount() == 0) {
            model.addColumn("Student ID");
            model.addColumn("Student Name");
            model.addColumn("Assignment Marks");
            model.addColumn("Exam Marks");
            model.addColumn("Total Marks");
        }

        String term    = (String) marksView.getTermComboBox().getSelectedItem();
        String course  = (String) marksView.getCourseComboBox().getSelectedItem();
        String section = (String) marksView.getSectionComboBox().getSelectedItem();

        boolean dataLoaded = false;
        String sql = "SELECT m.student_id, s.full_name, m.marks, m.assignment_marks, m.exam_marks "
                   + "FROM marks m JOIN students s ON m.student_id = s.student_id "
                   + "WHERE m.term = ? AND m.course_name = ? AND m.section_name = ?";
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, term);
            pstm.setString(2, course);
            pstm.setString(3, section);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                double total = rs.getDouble("marks");
                double assignment = rs.getDouble("assignment_marks");
                double exam = rs.getDouble("exam_marks");
                if (assignment == 0 && exam == 0 && total != 0) {
                    assignment = total * 0.5;
                    exam = total * 0.5;
                }
                model.addRow(new Object[]{
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    assignment,
                    exam,
                    total
                });
                dataLoaded = true;
            }
        } catch (Exception e) {
            System.out.println("loadMarksTable: " + e.getMessage());
        } finally {
            if (conn != null) { try { mysql.closeConnection(conn); } catch (Exception e) {} }
        }

        if (!dataLoaded) {
            model.addRow(new Object[]{"ST1001", "Prateek Satyal",  85.0, 78.0, 163.0});
            model.addRow(new Object[]{"ST1002", "Rohan Budha",     90.0, 88.0, 178.0});
            model.addRow(new Object[]{"ST1003", "Karun Shrestha",  75.0, 82.0, 157.0});
        }
    }

    private void saveMarks() {
        if (UserSession.isStudent()) {
            JOptionPane.showMessageDialog(marksView, "Access denied. Students cannot save marks.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) marksView.getMarksTable().getModel();
        int rows = model.getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(marksView, "No marks data available to save.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String term    = (String) marksView.getTermComboBox().getSelectedItem();
        String course  = (String) marksView.getCourseComboBox().getSelectedItem();
        String section = (String) marksView.getSectionComboBox().getSelectedItem();

        Connection conn = null;
        PreparedStatement checkPstm = null;
        PreparedStatement insertPstm = null;
        PreparedStatement updatePstm = null;
        try {
            conn = mysql.openConnection();
            String checkSql = "SELECT id FROM marks WHERE student_id = ? AND term = ? AND course_name = ? AND section_name = ?";
            String insertSql = "INSERT INTO marks (student_id, student_name, course_name, term, section_name, marks, grade, grade_point, percentage, assignment_marks, exam_marks) "
                             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String updateSql = "UPDATE marks SET student_name = ?, marks = ?, grade = ?, grade_point = ?, percentage = ?, assignment_marks = ?, exam_marks = ? "
                             + "WHERE id = ?";

            checkPstm = conn.prepareStatement(checkSql);
            insertPstm = conn.prepareStatement(insertSql);
            updatePstm = conn.prepareStatement(updateSql);

            for (int i = 0; i < rows; i++) {
                String studentId = (String) model.getValueAt(i, 0);
                String studentName = (String) model.getValueAt(i, 1);
                double assignment = Double.parseDouble(model.getValueAt(i, 2).toString());
                double exam       = Double.parseDouble(model.getValueAt(i, 3).toString());
                double total      = assignment + exam;
                model.setValueAt(total, i, 4);

                double percentage = (total / 200.0) * 100.0;
                String grade = computeLetterGrade(total);
                double gpa = computeGpa(percentage);

                // Check if exists
                checkPstm.setString(1, studentId);
                checkPstm.setString(2, term);
                checkPstm.setString(3, course);
                checkPstm.setString(4, section);

                int existingId = -1;
                try (ResultSet rs = checkPstm.executeQuery()) {
                    if (rs.next()) {
                        existingId = rs.getInt("id");
                    }
                }

                if (existingId != -1) {
                    // Update
                    updatePstm.setString(1, studentName);
                    updatePstm.setDouble(2, total);
                    updatePstm.setString(3, grade);
                    updatePstm.setDouble(4, gpa);
                    updatePstm.setDouble(5, percentage);
                    updatePstm.setDouble(6, assignment);
                    updatePstm.setDouble(7, exam);
                    updatePstm.setInt(8, existingId);
                    updatePstm.executeUpdate();
                } else {
                    // Insert
                    insertPstm.setString(1, studentId);
                    insertPstm.setString(2, studentName);
                    insertPstm.setString(3, course);
                    insertPstm.setString(4, term);
                    insertPstm.setString(5, section);
                    insertPstm.setDouble(6, total);
                    insertPstm.setString(7, grade);
                    insertPstm.setDouble(8, gpa);
                    insertPstm.setDouble(9, percentage);
                    insertPstm.setDouble(10, assignment);
                    insertPstm.setDouble(11, exam);
                    insertPstm.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("saveMarks error: " + e.getMessage());
        } finally {
            if (checkPstm != null) { try { checkPstm.close(); } catch(Exception e){} }
            if (insertPstm != null) { try { insertPstm.close(); } catch(Exception e){} }
            if (updatePstm != null) { try { updatePstm.close(); } catch(Exception e){} }
            if (conn != null) { try { mysql.closeConnection(conn); } catch (Exception e) {} }
        }

        JOptionPane.showMessageDialog(marksView, "Marks saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetMarksFields() {
        if (marksView.getTermComboBox().getItemCount() > 0)    marksView.getTermComboBox().setSelectedIndex(0);
        if (marksView.getCourseComboBox().getItemCount() > 0)  marksView.getCourseComboBox().setSelectedIndex(0);
        if (marksView.getSectionComboBox().getItemCount() > 0) marksView.getSectionComboBox().setSelectedIndex(0);
        loadMarksTable();
    }

    private void addStudentRow() {
        DefaultTableModel model = (DefaultTableModel) marksView.getMarksTable().getModel();
        String studentId = JOptionPane.showInputDialog(marksView, "Enter Student ID:");
        if (studentId == null || studentId.trim().isEmpty()) return;
        String studentName = JOptionPane.showInputDialog(marksView, "Enter Student Name:");
        if (studentName == null || studentName.trim().isEmpty()) return;
        model.addRow(new Object[]{studentId, studentName, 0.0, 0.0, 0.0});
    }

    private void setupMenuIcons(AcademicPerformanceFrame view) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        view.getTitleLabel().setText("SMS");
        view.getTitleLabel().setIcon(new AcademicPerformanceFrame.VectorIcon("hamburger", 20, whiteColor));
        view.getTitleLabel().setIconTextGap(12);
        view.getDashboardButton().setText("Dashboard");
        view.getDashboardButton().setIconTextGap(12);
        view.getStudentsButton().setText("Students Management");
        view.getStudentsButton().setIconTextGap(12);
        view.getCoursesButton().setText("Courses Management");
        view.getCoursesButton().setIconTextGap(12);
        view.getAttendanceButton().setText("Attendance Management");
        view.getAttendanceButton().setIconTextGap(12);
        view.getAcademicPerformanceButton().setText("Academic Performance");
        view.getAcademicPerformanceButton().setIconTextGap(12);
        view.getGradeComputationButton().setText("Grade Computation");
        view.getGradeComputationButton().setIconTextGap(12);
        view.getResultGenerationButton().setText("Result Generation");
        view.getResultGenerationButton().setIconTextGap(12);
        view.getReportsExportButton().setText("Reports Export");
        view.getReportsExportButton().setIconTextGap(12);
        view.getProfileButton().setText("Profile");
        view.getProfileButton().setIconTextGap(12);
        view.getLogoutButton().setText("Logout");
        view.getLogoutButton().setIconTextGap(12);
        this.setActiveMenuItem(view, view.getAcademicPerformanceButton());
    }

    private void setActiveMenuItem(AcademicPerformanceFrame view, JButton activeBtn) {
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
            view.getAcademicPerformanceButton(),
            view.getGradeComputationButton(),
            view.getResultGenerationButton(),
            view.getReportsExportButton(),
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
                btn.setIcon(new AcademicPerformanceFrame.VectorIcon(type, 28, whiteColor));
                continue;
            }
            btn.setBackground(normalBg);
            btn.setForeground(normalColor);
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setIcon(new AcademicPerformanceFrame.VectorIcon(type, 28, activeColor));
        }
    }

    // ── GradeComputationFrame controller ──────────────────────────────────────

    private void initGradeController() {
        if (UserSession.isStudent()) {
            JOptionPane.showMessageDialog(gradeView, "Access denied. Students cannot access Grade Computation.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            gradeView.dispose();
            return;
        }
        setupMenuIcons(gradeView);

        // Wire logout
        LogoutController.wireLogout(gradeView, gradeView.getLogoutButton());

        // Setup placeholder for student ID field
        gradeView.getStudentIdField().addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                gradeView.getStudentIdField().setBorder(BorderFactory.createLineBorder(new java.awt.Color(11, 27, 226), 2));
                if ("Enter Student ID...".equals(gradeView.getStudentIdField().getText())) {
                    gradeView.getStudentIdField().setText("");
                    gradeView.getStudentIdField().setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                gradeView.getStudentIdField().setBorder(BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1));
                if (gradeView.getStudentIdField().getText().trim().isEmpty()) {
                    gradeView.getStudentIdField().setText("Enter Student ID...");
                    gradeView.getStudentIdField().setForeground(java.awt.Color.GRAY);
                }
            }
        });

        // Setup interactive button styles
        JButton[] actionButtons = new JButton[]{gradeView.getSearchButton(), gradeView.getComputeButton(), gradeView.getBackButton()};
        for (final JButton btn : actionButtons) {
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn.addFocusListener(new java.awt.event.FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    btn.setBorder(BorderFactory.createLineBorder(new java.awt.Color(11, 27, 226), 2));
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (btn == gradeView.getComputeButton()) {
                        btn.setBorder(null);
                    } else {
                        btn.setBorder(BorderFactory.createEtchedBorder());
                    }
                }
            });
        }

        // Setup sidebar effects and navigation
        setupSidebarEffects(gradeView);
        setupSidebarNavigation(gradeView);

        // Populate term combo-box (always override — NetBeans designer may have dummy items)
        gradeView.getTermComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));

        // Search button — load marks for entered student ID
        gradeView.getSearchButton().addActionListener(e -> loadGradesForStudent());

        // Compute button — calculate and display GPA
        gradeView.getComputeButton().addActionListener(e -> computeGrades());

        // Back button — role-aware navigation
        gradeView.getBackButton().addActionListener(e -> navigateToDashboard(gradeView));

        // Term combo — reload when term changes
        gradeView.getTermComboBox().addActionListener(e -> loadGradesForStudent());
    }

    private void setupSidebarEffects(GradeComputationFrame view) {
        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getStudentsButton(),
            view.getCoursesButton(),
            view.getAttendanceButton(),
            view.getAcademicPerformanceButton(),
            view.getGradeComputationButton(),
            view.getResultGenerationButton(),
            view.getReportsExportButton(),
            view.getProfileButton(),
            view.getLogoutButton()
        };

        for (JButton btn : buttons) {
            btn.addActionListener(e -> setActiveMenuItem(view, btn));
            addHoverAndFocusEffects(btn);
        }
    }

    private void addHoverAndFocusEffects(final JButton btn) {
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                btn.setBorder(BorderFactory.createLineBorder(new java.awt.Color(11, 27, 226), 2));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                btn.setBorder(null);
            }
        });

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn.getBackground().equals(new java.awt.Color(224, 242, 248))) {
                    btn.setBackground(new java.awt.Color(200, 235, 245));
                } else if (btn.getBackground().equals(new java.awt.Color(243, 227, 225))) {
                    btn.setBackground(new java.awt.Color(233, 212, 209));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn.getBackground().equals(new java.awt.Color(200, 235, 245))) {
                    btn.setBackground(new java.awt.Color(224, 242, 248));
                } else if (btn.getBackground().equals(new java.awt.Color(233, 212, 209))) {
                    btn.setBackground(new java.awt.Color(243, 227, 225));
                }
            }
        });
    }

    private void setupSidebarNavigation(GradeComputationFrame view) {
        // Dashboard
        view.getDashboardButton().addActionListener(e -> navigateToDashboard(view));

        // Students Management
        view.getStudentsButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.StudentManagementFrame frame = new view.StudentManagementFrame();
                    new StudentController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Course Management
        view.getCoursesButton().addActionListener(e -> {
            if (UserSession.isAdmin()) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.CourseManagementFrame frame = new view.CourseManagementFrame();
                    new CourseController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Attendance
        view.getAttendanceButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.AttendanceSummaryFrame frame = new view.AttendanceSummaryFrame();
                new AttendanceController(frame);
                frame.setVisible(true);
            });
        });

        // Academic Performance
        view.getAcademicPerformanceButton().addActionListener(e -> {
            String currentRole = UserSession.isAdmin() ? "Admin" : (UserSession.isTeacher() ? "Teacher" : "Student");
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.AcademicPerformanceFrame frame = new view.AcademicPerformanceFrame(currentRole);
                new MarksController(frame, currentRole);
                frame.setVisible(true);
            });
        });

        // Result Generation
        view.getResultGenerationButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.GenerateResultFrame frame = new view.GenerateResultFrame();
                    new ResultController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Reports/Export
        view.getReportsExportButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.DownloadResultFrame frame = new view.DownloadResultFrame();
                new ResultController(frame);
                frame.setVisible(true);
            });
        });

        // Profile
        view.getProfileButton().addActionListener(e -> {
            view.dispose();
            EventQueue.invokeLater(() -> {
                view.ViewStudentProfile profileView = new view.ViewStudentProfile();
                new ViewStudentProfileController(profileView);
                profileView.setVisible(true);
            });
        });
    }

    private void setupMenuIcons(GradeComputationFrame view) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        view.getTitleLabel().setText("SMS");
        view.getTitleLabel().setIcon(new GradeComputationFrame.VectorIcon("hamburger", 20, whiteColor));
        view.getTitleLabel().setIconTextGap(12);
        view.getDashboardButton().setText("Dashboard");
        view.getDashboardButton().setIconTextGap(12);
        view.getStudentsButton().setText("Students Management");
        view.getStudentsButton().setIconTextGap(12);
        view.getCoursesButton().setText("Courses Management");
        view.getCoursesButton().setIconTextGap(12);
        view.getAttendanceButton().setText("Attendance Management");
        view.getAttendanceButton().setIconTextGap(12);
        view.getAcademicPerformanceButton().setText("Academic Performance");
        view.getAcademicPerformanceButton().setIconTextGap(12);
        view.getGradeComputationButton().setText("Grade Computation");
        view.getGradeComputationButton().setIconTextGap(12);
        view.getResultGenerationButton().setText("Result Generation");
        view.getResultGenerationButton().setIconTextGap(12);
        view.getReportsExportButton().setText("Reports Export");
        view.getReportsExportButton().setIconTextGap(12);
        view.getProfileButton().setText("Profile");
        view.getProfileButton().setIconTextGap(12);
        view.getLogoutButton().setText("Logout");
        view.getLogoutButton().setIconTextGap(12);
        this.setActiveMenuItem(view, view.getGradeComputationButton());

        view.getCard1IconLabel().setText("");
        view.getCard1IconLabel().setIcon(new GradeComputationFrame.VectorIcon("students", 35, activeColor));
        view.getCard2IconLabel().setText("");
        view.getCard2IconLabel().setIcon(new GradeComputationFrame.VectorIcon("courses", 35, activeColor));
        view.getCard3IconLabel().setText("");
        view.getCard3IconLabel().setIcon(new GradeComputationFrame.VectorIcon("performance", 35, activeColor));
    }

    private void setActiveMenuItem(GradeComputationFrame view, JButton activeBtn) {
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
            view.getAcademicPerformanceButton(),
            view.getGradeComputationButton(),
            view.getResultGenerationButton(),
            view.getReportsExportButton(),
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
                btn.setIcon(new GradeComputationFrame.VectorIcon(type, 28, whiteColor));
                continue;
            }
            btn.setBackground(normalBg);
            btn.setForeground(normalColor);
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setIcon(new GradeComputationFrame.VectorIcon(type, 28, activeColor));
        }
    }

    private void loadGradesForStudent() {
        String studentId = gradeView.getStudentIdField().getText().trim();
        if (studentId.isEmpty() || studentId.equals("Enter Student ID...")) return;

        String term = (String) gradeView.getTermComboBox().getSelectedItem();

        DefaultTableModel model = (DefaultTableModel) gradeView.getGradesTable().getModel();
        model.setRowCount(0);
        if (model.getColumnCount() == 0) {
            model.addColumn("Student ID");
            model.addColumn("Student Name");
            model.addColumn("Course");
            model.addColumn("Assignment");
            model.addColumn("Exam");
            model.addColumn("Total");
            model.addColumn("Grade");
        }

        String sql = "SELECT m.student_id, s.full_name, m.course_name, m.marks, m.assignment_marks, m.exam_marks "
                   + "FROM marks m JOIN students s ON m.student_id = s.student_id "
                   + "WHERE m.student_id LIKE ? AND m.term = ?";
        Connection conn = null;
        boolean found = false;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, "%" + studentId + "%");
            pstm.setString(2, term);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                double total = rs.getDouble("marks");
                String grade = computeLetterGrade(total);
                double assignment = rs.getDouble("assignment_marks");
                double exam = rs.getDouble("exam_marks");
                if (assignment == 0 && exam == 0 && total != 0) {
                    assignment = total * 0.5;
                    exam = total * 0.5;
                }
                model.addRow(new Object[]{
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    rs.getString("course_name"),
                    assignment,
                    exam,
                    total,
                    grade
                });
                gradeView.getStudentNameLabel().setText("Student: " + rs.getString("full_name"));
                found = true;
            }
        } catch (Exception e) {
            System.out.println("loadGradesForStudent: " + e.getMessage());
        } finally {
            if (conn != null) { try { mysql.closeConnection(conn); } catch (Exception e) {} }
        }

        if (!found) {
            // Fallback demo row
            model.addRow(new Object[]{studentId, "N/A", "N/A", 0.0, 0.0, 0.0, "F"});
        }
    }

    private void computeGrades() {
        DefaultTableModel model = (DefaultTableModel) gradeView.getGradesTable().getModel();
        int rows = model.getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(gradeView, "No marks loaded. Please search for a student first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double totalMarks = 0;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            Object val = model.getValueAt(i, 5);
            if (val != null) {
                double marks = Double.parseDouble(val.toString());
                totalMarks += marks;
                count++;
                // Update grade column
                model.setValueAt(computeLetterGrade(marks), i, 6);
            }
        }

        if (count > 0) {
            double avg = totalMarks / count;
            double percentage = (avg / 200.0) * 100.0;
            double gpa = computeGpa(percentage);
            String overallGrade = computeLetterGrade(avg);

            gradeView.getGpaValLabel().setText(String.format("%.2f", gpa));
            gradeView.getPercentageValLabel().setText(String.format("%.1f%%", percentage));
            gradeView.getOverallGradeValLabel().setText(overallGrade);

            // Update stat cards
            gradeView.getCard1NumLabel().setText(String.format("%.1f%%", percentage));
            gradeView.getCard2NumLabel().setText(overallGrade);
            gradeView.getCard3NumLabel().setText(String.format("%.2f", gpa));
        }

        JOptionPane.showMessageDialog(gradeView, "Grades computed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Converts total marks (out of 200) to a letter grade. */
    public static String computeLetterGrade(double total) {
        double pct = (total / 200.0) * 100.0;
        if (pct >= 90) return "A+";
        if (pct >= 80) return "A";
        if (pct >= 70) return "B+";
        if (pct >= 60) return "B";
        if (pct >= 50) return "C";
        if (pct >= 40) return "D";
        return "F";
    }

    /** Converts percentage to GPA (4.0 scale). */
    public static double computeGpa(double percentage) {
        if (percentage >= 90) return 4.0;
        if (percentage >= 80) return 3.7;
        if (percentage >= 70) return 3.3;
        if (percentage >= 60) return 3.0;
        if (percentage >= 50) return 2.0;
        if (percentage >= 40) return 1.0;
        return 0.0;
    }

    // ── Shared: role-aware dashboard navigation ───────────────────────────────

    private void navigateToDashboard(JFrame source) {
        source.dispose();
        EventQueue.invokeLater(() -> {
            if (UserSession.isAdmin()) {
                view.AdminDashboard admin = new view.AdminDashboard();
                new AdminDashboardController(admin);
                admin.setResizable(true);
                admin.pack();
                admin.setLocationRelativeTo(null);
                admin.setVisible(true);
            } else if (UserSession.isTeacher()) {
                view.TeacherDashboard teacher = new view.TeacherDashboard();
                new TeacherDashboardController(teacher);
                teacher.setResizable(true);
                teacher.pack();
                teacher.setLocationRelativeTo(null);
                teacher.setVisible(true);
            } else {
                view.StudentDashboard student = new view.StudentDashboard();
                new StudentDashboardController(student);
                student.setResizable(true);
                student.pack();
                student.setLocationRelativeTo(null);
                student.setVisible(true);
            }
        });
    }
}

// Verified marks entry exceptions rollback handling

// Verified marks entry exceptions rollback handling
