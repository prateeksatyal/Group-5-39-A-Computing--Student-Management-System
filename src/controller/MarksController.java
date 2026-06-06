package controller;

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
 * MVC Controller for AcademicPerformanceFrame.
 * Handles marks entry UI, JTable loading, and sidebar navigation.
 */
public class MarksController {

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
        String sql = "SELECT m.student_id, s.full_name, m.assignment, m.exam, m.total " +
                     "FROM marks m JOIN students s ON m.student_id = s.student_id " +
                     "WHERE m.term = ? AND m.course = ? AND m.section = ?";
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, term);
            pstm.setString(2, course);
            pstm.setString(3, section);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("student_id"),
                    rs.getString("full_name"),
                    rs.getDouble("assignment"),
                    rs.getDouble("exam"),
                    rs.getDouble("total")
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
        try {
            conn = mysql.openConnection();
            String sql = "INSERT INTO marks (student_id, term, course, section, assignment, exam, total) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE assignment = ?, exam = ?, total = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            for (int i = 0; i < rows; i++) {
                String studentId = (String) model.getValueAt(i, 0);
                double assignment = Double.parseDouble(model.getValueAt(i, 2).toString());
                double exam = Double.parseDouble(model.getValueAt(i, 3).toString());
                double total = assignment + exam;
                model.setValueAt(total, i, 4);

                pstm.setString(1, studentId);
                pstm.setString(2, term);
                pstm.setString(3, course);
                pstm.setString(4, section);
                pstm.setDouble(5, assignment);
                pstm.setDouble(6, exam);
                pstm.setDouble(7, total);

                pstm.setDouble(8, assignment);
                pstm.setDouble(9, exam);
                pstm.setDouble(10, total);
                pstm.addBatch();
            }
            pstm.executeBatch();
        } catch (Exception e) {
            System.out.println("saveMarks error: " + e.getMessage());
        } finally {
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

    private void navigateToDashboard(JFrame source) {
        source.dispose();
    }
}
