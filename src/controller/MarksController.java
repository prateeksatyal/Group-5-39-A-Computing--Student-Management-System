package controller;

import view.GradeComputationFrame;
import view.AcademicPerformanceFrame;
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
 * MVC Controller for AcademicPerformanceFrame & GradeComputationFrame.
 * Handles marks entry UI, student grade loading, GPA calculations, and sidebar navigation.
 */
public class MarksController {

    private GradeComputationFrame gradeView;
    private AcademicPerformanceFrame marksView;
    private final String role;
    private final MySqlConnector mysql = new MySqlConnector();

    /** Constructor for AcademicPerformanceFrame */
    public MarksController(AcademicPerformanceFrame view, String role) {
        this.marksView = view;
        this.role = role;

        this.marksView.pack();
        this.marksView.setLocationRelativeTo(null);

        initMarksController();
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

    private void initMarksController() {
        setupComboBoxData();

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

        // Back button
        marksView.getBackButton().addActionListener(e -> navigateToDashboard(marksView));

        marksView.getResetButton().addActionListener(e -> resetMarksFields());

        marksView.getSaveMarksButton().addActionListener(e -> saveMarks());

        marksView.getAddStudentButton().addActionListener(e -> addStudentRow());

        // Reload table when filters change (optimized refresh latency)
        ActionListener loadListener = e -> loadMarksTable();
        marksView.getTermComboBox().addActionListener(loadListener);
        marksView.getCourseComboBox().addActionListener(loadListener);
        marksView.getSectionComboBox().addActionListener(loadListener);

        // Initial data load
        loadMarksTable();
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

    private void setupSidebarNavigation(AcademicPerformanceFrame view) {
        view.getDashboardButton().addActionListener(e -> navigateToDashboard(view));
    }

    private void setupComboBoxData() {
        if (marksView.getTermComboBox().getItemCount() == 0) {
            marksView.getTermComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));
        }
        if (marksView.getCourseComboBox().getItemCount() == 0) {
            marksView.getCourseComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"BSc Computer Science", "BIT", "BBA"}));
        }
        if (marksView.getSectionComboBox().getItemCount() == 0) {
            marksView.getSectionComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"Section A", "Section B", "Section C"}));
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

    private void loadMarksTable() {
        DefaultTableModel model = (DefaultTableModel) marksView.getMarksTable().getModel();
        model.setRowCount(0);
        
        if (model.getColumnCount() == 0) {
            model.addColumn("Student ID");
            model.addColumn("Student Name");
            model.addColumn("Assignment");
            model.addColumn("Exam");
            model.addColumn("Total");
        }

        String selectedTerm = (String) marksView.getTermComboBox().getSelectedItem();
        String selectedCourse = (String) marksView.getCourseComboBox().getSelectedItem();
        String selectedSection = (String) marksView.getSectionComboBox().getSelectedItem();

        boolean dataLoaded = false;
        String sql = "SELECT m.student_id, s.full_name, m.marks "
                   + "FROM marks m JOIN students s ON m.student_id = s.student_id "
                   + "WHERE m.term = ? AND m.course_name = ? AND m.section_name = ?";
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, selectedTerm);
            pstm.setString(2, selectedCourse);
            pstm.setString(3, selectedSection);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                double total = rs.getDouble("marks");
                double assignment = total * 0.5;
                double exam = total * 0.5;
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

    private void saveMarks() {
        DefaultTableModel model = (DefaultTableModel) marksView.getMarksTable().getModel();
        int rows = model.getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(marksView, "No marks data available to save.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedTerm = (String) marksView.getTermComboBox().getSelectedItem();
        String selectedCourse = (String) marksView.getCourseComboBox().getSelectedItem();
        String selectedSection = (String) marksView.getSectionComboBox().getSelectedItem();

        Connection conn = null;
        PreparedStatement checkPstm = null;
        PreparedStatement insertPstm = null;
        PreparedStatement updatePstm = null;
        try {
            conn = mysql.openConnection();
            String checkSql = "SELECT id FROM marks WHERE student_id = ? AND term = ? AND course_name = ? AND section_name = ?";
            String insertSql = "INSERT INTO marks (student_id, student_name, course_name, term, section_name, marks, grade, grade_point, percentage) "
                             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String updateSql = "UPDATE marks SET student_name = ?, marks = ?, grade = ?, grade_point = ?, percentage = ? "
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

                checkPstm.setString(1, studentId);
                checkPstm.setString(2, selectedTerm);
                checkPstm.setString(3, selectedCourse);
                checkPstm.setString(4, selectedSection);

                int existingId = -1;
                try (ResultSet rs = checkPstm.executeQuery()) {
                    if (rs.next()) {
                        existingId = rs.getInt("id");
                    }
                }

                if (existingId != -1) {
                    updatePstm.setString(1, studentName);
                    updatePstm.setDouble(2, total);
                    updatePstm.setString(3, grade);
                    updatePstm.setDouble(4, gpa);
                    updatePstm.setDouble(5, percentage);
                    updatePstm.setInt(6, existingId);
                    updatePstm.executeUpdate();
                } else {
                    insertPstm.setString(1, studentId);
                    insertPstm.setString(2, studentName);
                    insertPstm.setString(3, selectedCourse);
                    insertPstm.setString(4, selectedTerm);
                    insertPstm.setString(5, selectedSection);
                    insertPstm.setDouble(6, total);
                    insertPstm.setString(7, grade);
                    insertPstm.setDouble(8, gpa);
                    insertPstm.setDouble(9, percentage);
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

    private void navigateToDashboard(JFrame source) {
        source.dispose();
    }
}