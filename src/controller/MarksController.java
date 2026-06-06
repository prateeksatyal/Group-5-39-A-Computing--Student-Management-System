package controller;

import view.GradeComputationFrame;
import view.AcademicPerformanceFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.MySqlConnector;

/**
 * MVC Controller for GradeComputationFrame.
 * Handles student grade loading, GPA calculations, and sidebar navigation.
 */
public class MarksController {

    private GradeComputationFrame gradeView;
    private final String role;
    private final MySqlConnector mysql = new MySqlConnector();

    /** Stub constructor for AcademicPerformanceFrame to satisfy compilation on this branch */
    public MarksController(AcademicPerformanceFrame view, String role) {
        this.role = role;
    }

    /** Constructor for GradeComputationFrame */
    public MarksController(GradeComputationFrame view) {
        this.gradeView = view;
        this.role = UserSession.getCurrentUser() != null ? UserSession.getCurrentUser().getRole() : "Student";

        this.gradeView.pack();
        this.gradeView.setLocationRelativeTo(null);

        initGradeController();
    }

    private void initGradeController() {
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

        // Populate term combo-box
        if (gradeView.getTermComboBox().getItemCount() == 0) {
            gradeView.getTermComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));
        }

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
            btn.addActionListener(e -> view.setActiveMenuItem(btn));
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
        view.getDashboardButton().addActionListener(e -> navigateToDashboard(view));
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

        String sql = "SELECT m.student_id, s.full_name, m.course_name, m.marks "
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
                double assignment = total * 0.5;
                double exam = total * 0.5;
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
                model.setValueAt(computeLetterGrade(marks), i, 6);
            }
        }

        // Guard against division by zero for student grade averages
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

    public static double computeGpa(double percentage) {
        if (percentage >= 90) return 4.0;
        if (percentage >= 80) return 3.7;
        if (percentage >= 70) return 3.3;
        if (percentage >= 60) return 3.0;
        if (percentage >= 50) return 2.0;
        if (percentage >= 40) return 1.0;
        return 0.0;
    }

    private void navigateToDashboard(JFrame source) {
        source.dispose();
    }
}
