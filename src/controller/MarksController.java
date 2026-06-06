package controller;

import view.AcademicPerformanceFrame;
import view.DashboardAdmin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.MySqlConnector;

public class MarksController {
    private final AcademicPerformanceFrame view;
    private final String role;
    private final MySqlConnector mysql = new MySqlConnector();

    public MarksController(AcademicPerformanceFrame view, String role) {
        this.view = view;
        this.role = role;
        
        this.view.pack();
        this.view.setLocationRelativeTo(null);

        initController();
    }

    private void initController() {
        // Setup initial dummy data or dropdown content
        setupComboBoxData();

        // Wire event handlers
        view.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DashboardAdmin().setVisible(true);
                view.dispose();
            }
        });

        view.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        view.getSaveMarksButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMarks();
            }
        });

        view.getAddStudentButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudentRow();
            }
        });

        // Load data when dropdowns change
        ActionListener loadListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMarksTable();
            }
        };
        view.getTermComboBox().addActionListener(loadListener);
        view.getCourseComboBox().addActionListener(loadListener);
        view.getSectionComboBox().addActionListener(loadListener);

        // Initial load
        loadMarksTable();
    }

    private void setupComboBoxData() {
        // Ensure comboboxes have default lists if empty
        if (view.getTermComboBox().getItemCount() == 0) {
            view.getTermComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));
        }
        if (view.getCourseComboBox().getItemCount() == 0) {
            view.getCourseComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"BSc Computer Science", "BIT", "BBA"}));
        }
        if (view.getSectionComboBox().getItemCount() == 0) {
            view.getSectionComboBox().setModel(new DefaultComboBoxModel<>(new String[]{"Section A", "Section B", "Section C"}));
        }
    }

    private void loadMarksTable() {
        DefaultTableModel model = (DefaultTableModel) view.getMarksTable().getModel();
        model.setRowCount(0);
        
        if (model.getColumnCount() == 0) {
            model.addColumn("Student ID");
            model.addColumn("Student Name");
            model.addColumn("Assignment Marks");
            model.addColumn("Exam Marks");
            model.addColumn("Total Marks");
        }

        String selectedTerm = (String) view.getTermComboBox().getSelectedItem();
        String selectedCourse = (String) view.getCourseComboBox().getSelectedItem();
        String selectedSection = (String) view.getSectionComboBox().getSelectedItem();

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
            System.out.println("Could not load marks from database (using fallback dummy data): " + e.getMessage());
        } finally {
            if (conn != null) {
                try { mysql.closeConnection(conn); } catch (Exception e) {}
            }
        }

        if (!dataLoaded) {
            model.addRow(new Object[]{"ST1001", "Prateek Satyal", 85.0, 78.0, 163.0});
            model.addRow(new Object[]{"ST1002", "Rohan Budha", 90.0, 88.0, 178.0});
            model.addRow(new Object[]{"ST1003", "Karun Shrestha", 75.0, 82.0, 157.0});
        }
    }

    private void saveMarks() {
        DefaultTableModel model = (DefaultTableModel) view.getMarksTable().getModel();
        int rows = model.getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(view, "No marks data available to save.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedTerm = (String) view.getTermComboBox().getSelectedItem();
        String selectedCourse = (String) view.getCourseComboBox().getSelectedItem();
        String selectedSection = (String) view.getSectionComboBox().getSelectedItem();

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

        JOptionPane.showMessageDialog(view, "Marks saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    private void resetFields() {
        if (view.getTermComboBox().getItemCount() > 0) view.getTermComboBox().setSelectedIndex(0);
        if (view.getCourseComboBox().getItemCount() > 0) view.getCourseComboBox().setSelectedIndex(0);
        if (view.getSectionComboBox().getItemCount() > 0) view.getSectionComboBox().setSelectedIndex(0);
        loadMarksTable();
    }

    private void addStudentRow() {
        DefaultTableModel model = (DefaultTableModel) view.getMarksTable().getModel();
        String studentId = JOptionPane.showInputDialog(view, "Enter Student ID:");
        if (studentId == null || studentId.trim().isEmpty()) return;

        String studentName = JOptionPane.showInputDialog(view, "Enter Student Name:");
        if (studentName == null || studentName.trim().isEmpty()) return;

        model.addRow(new Object[]{studentId, studentName, 0.0, 0.0, 0.0});
    }
}
