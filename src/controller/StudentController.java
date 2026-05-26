/*
 * Decompiled with CFR 0.152.
 */
package controller;

import dao.StudentDAO;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.StudentData;
import view.AddStudentFrame;
import view.AdminDashboard;
import view.StudentManagementFrame;
import view.UpdateStudentFrame;

public class StudentController {
    private final StudentDAO studentDAO = new StudentDAO();
    private final StudentManagementFrame managementView;
    private AddStudentFrame addView;
    private UpdateStudentFrame updateView;

    public StudentController(StudentManagementFrame managementView) {
        this.managementView = managementView;
        
        // pack, enable resizing, and center natively
        this.managementView.setResizable(true);
        this.managementView.pack();
        this.managementView.setLocationRelativeTo(null);
        
        // Enforce Role hard stops
        if (!UserSession.isAdmin()) {
            this.managementView.getAddButton().setEnabled(false);
            this.managementView.getUpdateButton().setEnabled(false);
            this.managementView.getDeleteButton().setEnabled(false);
            this.managementView.getAddButton().setToolTipText("Only administrators can add student records.");
            this.managementView.getUpdateButton().setToolTipText("Only administrators can update student records.");
            this.managementView.getDeleteButton().setToolTipText("Only administrators can delete student records.");
            
            // Disable sidebar buttons unauthorized for non-admins
            this.managementView.getCoursesButton().setEnabled(false);
            this.managementView.getGradeComputationButton().setEnabled(false);
            this.managementView.getResultGenerationButton().setEnabled(false);
            this.managementView.getReportsExportButton().setEnabled(false);
        }
        
        this.loadStudentsToTable();
        this.managementView.getAddButton().addActionListener(new OpenAddFrameListener());
        this.managementView.getUpdateButton().addActionListener(new OpenUpdateFrameListener());
        this.managementView.getDeleteButton().addActionListener(new DeleteStudentFromTableListener());
        this.managementView.getRefreshButton().addActionListener(e -> this.loadStudentsToTable());
        this.managementView.getBackButton().addActionListener(new BackToAdminDashboardListener());
        this.managementView.getDashboardButton().addActionListener(new BackToAdminDashboardListener());
        
        // Wire sidebar navigation
        this.managementView.getAttendanceButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                this.managementView.dispose();
                EventQueue.invokeLater(() -> {
                    view.AttendanceSummaryFrame frame = new view.AttendanceSummaryFrame();
                    new AttendanceController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this.managementView, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.managementView.getAcademicPerformanceButton().addActionListener(e -> {
            String role = UserSession.isAdmin() ? "Admin" : (UserSession.isTeacher() ? "Teacher" : "Student");
            this.managementView.dispose();
            EventQueue.invokeLater(() -> {
                view.AcademicPerformanceFrame frame = new view.AcademicPerformanceFrame(role);
                new MarksController(frame, role);
                frame.setVisible(true);
            });
        });
        
        LogoutController.wireLogout(this.managementView, this.managementView.getLogoutButton());
        this.setupLiveSearch();
    }

    public void loadStudentsToTable() {
        List<StudentData> students = this.studentDAO.getAllStudents();
        this.renderTable(students);
        this.updateStudentCountBadge(students.size());
    }

    private void renderTable(List<StudentData> students) {
        DefaultTableModel model = (DefaultTableModel)this.managementView.getStudentsTable().getModel();
        model.setRowCount(0);
        for (StudentData s : students) {
            model.addRow(new Object[]{s.getStudentId(), s.getFullName(), s.getEmail(), s.getCourse(), s.getGender(), s.getAddress()});
        }
    }

    private void updateStudentCountBadge(int count) {
        this.managementView.getTotalStudentsLabel().setText(String.format("%,d", count));
    }

    private void setupLiveSearch() {
        final JTextField searchField = this.managementView.getSearchField();
        searchField.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                this.filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.filterTable();
            }

            private void filterTable() {
                String query = searchField.getText().trim();
                if (query.isEmpty() || "Search Name or ID...".equals(query)) {
                    StudentController.this.loadStudentsToTable();
                } else {
                    List<StudentData> results = StudentController.this.studentDAO.searchStudents(query);
                    StudentController.this.renderTable(results);
                }
            }
        });
    }

    private void saveStudent() {
        if (this.addView == null) {
            return;
        }
        String id = this.addView.getStudentIdField().getText().trim();
        String name = this.addView.getFullNameField().getText().trim();
        String email = this.addView.getEmailField().getText().trim();
        String phone = this.addView.getPhoneField().getText().trim();
        String course = (String)this.addView.getCourseComboBox().getSelectedItem();
        String gender = (String)this.addView.getGenderComboBox().getSelectedItem();
        String address = this.addView.getAddressArea().getText().trim();
        if (id.isEmpty() || "Enter Student ID".equals(id) || name.isEmpty() || "Enter Full Name".equals(name) || email.isEmpty() || "Enter Email Address".equals(email) || phone.isEmpty() || "Enter Phone Number".equals(phone) || "Select Course".equals(course) || "Select Gender".equals(gender) || address.isEmpty() || "Enter Address".equals(address)) {
            JOptionPane.showMessageDialog(this.addView, "Please fill in all information fields!", "Validation Error", 2);
            return;
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this.addView, "Please enter a valid email address format!", "Validation Error", 2);
            return;
        }
        if (!phone.matches("\\d+") || phone.length() < 7 || phone.length() > 15) {
            JOptionPane.showMessageDialog(this.addView, "Please enter a valid numeric phone number (7 to 15 digits)!", "Validation Error", 2);
            return;
        }
        if (this.studentDAO.searchStudentById(id) != null) {
            JOptionPane.showMessageDialog(this.addView, "A student with ID '" + id + "' already exists. Student ID must be unique!", "Duplication Error", 0);
            return;
        }
        StudentData student = new StudentData(id, name, email, phone, course, gender, address);
        boolean success = this.studentDAO.insertStudent(student);
        if (success) {
            JOptionPane.showMessageDialog(this.addView, "Student record successfully added to database!", "Registration Success", 1);
            this.clearAddForm();
            this.loadStudentsToTable();
        } else {
            JOptionPane.showMessageDialog(this.addView, "Critical database insertion error occurred!", "Database Error", 0);
        }
    }

    private void clearAddForm() {
        if (this.addView == null) {
            return;
        }
        this.addView.getStudentIdField().setText("Enter Student ID");
        this.addView.getStudentIdField().setForeground(new Color(128, 128, 128));
        this.addView.getStudentIdField().setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 1));
        this.addView.getFullNameField().setText("Enter Full Name");
        this.addView.getFullNameField().setForeground(new Color(128, 128, 128));
        this.addView.getFullNameField().setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 1));
        this.addView.getEmailField().setText("Enter Email Address");
        this.addView.getEmailField().setForeground(new Color(128, 128, 128));
        this.addView.getEmailField().setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 1));
        this.addView.getPhoneField().setText("Enter Phone Number");
        this.addView.getPhoneField().setForeground(new Color(128, 128, 128));
        this.addView.getPhoneField().setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 1));
        this.addView.getCourseComboBox().setSelectedIndex(0);
        this.addView.getGenderComboBox().setSelectedIndex(0);
        this.addView.getAddressArea().setText("Enter Address");
        this.addView.getAddressArea().setForeground(new Color(128, 128, 128));
    }

    private void searchStudent() {
        if (this.updateView == null) {
            return;
        }
        String searchId = this.updateView.getSearchIdField().getText().trim();
        if (searchId.isEmpty() || "Enter Student ID to Search...".equals(searchId)) {
            JOptionPane.showMessageDialog(this.updateView, "Please enter a valid Student ID to search!", "Search Error", 2);
            return;
        }
        StudentData s = this.studentDAO.searchStudentById(searchId);
        if (s != null) {
            this.updateView.getFullNameField().setText(s.getFullName());
            this.updateView.getFullNameField().setForeground(new Color(0, 0, 0));
            this.updateView.getEmailField().setText(s.getEmail());
            this.updateView.getEmailField().setForeground(new Color(0, 0, 0));
            this.updateView.getPhoneField().setText(s.getPhoneNumber());
            this.updateView.getPhoneField().setForeground(new Color(0, 0, 0));
            this.updateView.getCourseComboBox().setSelectedItem(s.getCourse());
            this.updateView.getGenderComboBox().setSelectedItem(s.getGender());
            this.updateView.getAddressArea().setText(s.getAddress());
            this.updateView.getAddressArea().setForeground(new Color(0, 0, 0));
        } else {
            JOptionPane.showMessageDialog(this.updateView, "No student record found with ID '" + searchId + "'!", "Search Result", 0);
        }
    }

    private void updateStudent() {
        if (this.updateView == null) {
            return;
        }
        String id = this.updateView.getSearchIdField().getText().trim();
        String name = this.updateView.getFullNameField().getText().trim();
        String email = this.updateView.getEmailField().getText().trim();
        String phone = this.updateView.getPhoneField().getText().trim();
        String course = (String)this.updateView.getCourseComboBox().getSelectedItem();
        String gender = (String)this.updateView.getGenderComboBox().getSelectedItem();
        String address = this.updateView.getAddressArea().getText().trim();
        if (id.isEmpty() || "Enter Student ID to Search...".equals(id)) {
            JOptionPane.showMessageDialog(this.updateView, "Please load a student record first by searching their ID!", "Update Error", 2);
            return;
        }
        if (name.isEmpty() || "Enter Full Name".equals(name) || email.isEmpty() || "Enter Email Address".equals(email) || phone.isEmpty() || "Enter Phone Number".equals(phone) || "Select Course".equals(course) || "Select Gender".equals(gender) || address.isEmpty() || "Enter Address".equals(address)) {
            JOptionPane.showMessageDialog(this.updateView, "Please fill in all editable information fields!", "Validation Error", 2);
            return;
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this.updateView, "Please enter a valid email address format!", "Validation Error", 2);
            return;
        }
        if (!phone.matches("\\d+") || phone.length() < 7 || phone.length() > 15) {
            JOptionPane.showMessageDialog(this.updateView, "Please enter a valid numeric phone number (7 to 15 digits)!", "Validation Error", 2);
            return;
        }
        StudentData student = new StudentData(id, name, email, phone, course, gender, address);
        boolean success = this.studentDAO.updateStudent(student);
        if (success) {
            JOptionPane.showMessageDialog(this.updateView, "Student record updated successfully!", "Update Success", 1);
            this.loadStudentsToTable();
        } else {
            JOptionPane.showMessageDialog(this.updateView, "Could not update the student record in database. Verify ID exists.", "Database Error", 0);
        }
    }

    private void deleteStudent() {
        if (this.updateView == null) {
            return;
        }
        String id = this.updateView.getSearchIdField().getText().trim();
        if (id.isEmpty() || "Enter Student ID to Search...".equals(id)) {
            JOptionPane.showMessageDialog(this.updateView, "Please specify a Student ID to delete!", "Deletion Error", 2);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this.updateView, "Are you sure you want to permanently delete student '" + id + "' from database?", "Confirm Deletion", 0, 2);
        if (choice == 0) {
            boolean success = this.studentDAO.deleteStudent(id);
            if (success) {
                JOptionPane.showMessageDialog(this.updateView, "Student record successfully deleted!", "Deletion Success", 1);
                this.resetUpdateForm();
                this.loadStudentsToTable();
            } else {
                JOptionPane.showMessageDialog(this.updateView, "No student record deleted. Verify Student ID exists.", "Database Error", 0);
            }
        }
    }

    private void resetUpdateForm() {
        if (this.updateView == null) {
            return;
        }
        this.updateView.getSearchIdField().setText("Enter Student ID to Search...");
        this.updateView.getSearchIdField().setForeground(new Color(128, 128, 128));
        this.updateView.getFullNameField().setText("Enter Full Name");
        this.updateView.getFullNameField().setForeground(new Color(128, 128, 128));
        this.updateView.getEmailField().setText("Enter Email Address");
        this.updateView.getEmailField().setForeground(new Color(128, 128, 128));
        this.updateView.getPhoneField().setText("Enter Phone Number");
        this.updateView.getPhoneField().setForeground(new Color(128, 128, 128));
        this.updateView.getCourseComboBox().setSelectedIndex(0);
        this.updateView.getGenderComboBox().setSelectedIndex(0);
        this.updateView.getAddressArea().setText("Enter Address");
        this.updateView.getAddressArea().setForeground(new Color(128, 128, 128));
    }

    class OpenAddFrameListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (StudentController.this.addView == null || !StudentController.this.addView.isDisplayable()) {
                StudentController.this.addView = new AddStudentFrame();
                StudentController.this.addView.getSaveButton().addActionListener(evt -> StudentController.this.saveStudent());
                StudentController.this.addView.getClearButton().addActionListener(evt -> StudentController.this.clearAddForm());
                StudentController.this.addView.getBackButton().addActionListener(evt -> {
                    StudentController.this.addView.dispose();
                    StudentController.this.addView = null;
                });
                
                // Pack and center natively
                StudentController.this.addView.pack();
                StudentController.this.addView.setLocationRelativeTo(null);
            }
            StudentController.this.addView.setVisible(true);
            StudentController.this.addView.toFront();
        }
    }

    class OpenUpdateFrameListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow;
            if (StudentController.this.updateView == null || !StudentController.this.updateView.isDisplayable()) {
                StudentController.this.updateView = new UpdateStudentFrame();
                StudentController.this.updateView.getSearchButton().addActionListener(evt -> StudentController.this.searchStudent());
                StudentController.this.updateView.getUpdateButton().addActionListener(evt -> StudentController.this.updateStudent());
                StudentController.this.updateView.getDeleteButton().addActionListener(evt -> StudentController.this.deleteStudent());
                StudentController.this.updateView.getResetButton().addActionListener(evt -> StudentController.this.resetUpdateForm());
                StudentController.this.updateView.getBackButton().addActionListener(evt -> {
                    StudentController.this.updateView.dispose();
                    StudentController.this.updateView = null;
                });
                
                // Pack and center natively
                StudentController.this.updateView.pack();
                StudentController.this.updateView.setLocationRelativeTo(null);
            }
            if ((selectedRow = StudentController.this.managementView.getStudentsTable().getSelectedRow()) != -1) {
                String prefillId = (String)StudentController.this.managementView.getStudentsTable().getValueAt(selectedRow, 0);
                StudentController.this.updateView.getSearchIdField().setText(prefillId);
                StudentController.this.updateView.getSearchIdField().setForeground(new Color(0, 0, 0));
                StudentController.this.searchStudent();
            }
            StudentController.this.updateView.setVisible(true);
            StudentController.this.updateView.toFront();
        }
    }

    class DeleteStudentFromTableListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = StudentController.this.managementView.getStudentsTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(StudentController.this.managementView, "Please select a student from the table first!", "Deletion Error", 2);
                return;
            }
            String id = (String)StudentController.this.managementView.getStudentsTable().getValueAt(selectedRow, 0);
            int choice = JOptionPane.showConfirmDialog(StudentController.this.managementView, "Are you sure you want to permanently delete student '" + id + "' from database?", "Confirm Deletion", 0, 2);
            if (choice == 0) {
                boolean success = StudentController.this.studentDAO.deleteStudent(id);
                if (success) {
                    JOptionPane.showMessageDialog(StudentController.this.managementView, "Student record successfully deleted!", "Deletion Success", 1);
                    StudentController.this.loadStudentsToTable();
                } else {
                    JOptionPane.showMessageDialog(StudentController.this.managementView, "Failed to delete student record from database.", "Database Error", 0);
                }
            }
        }
    }

    class BackToAdminDashboardListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            StudentController.this.managementView.dispose();
            if (StudentController.this.addView != null) {
                StudentController.this.addView.dispose();
            }
            if (StudentController.this.updateView != null) {
                StudentController.this.updateView.dispose();
            }
            EventQueue.invokeLater(() -> {
                if (UserSession.isAdmin()) {
                    AdminDashboard adminView = new AdminDashboard();
                    new AdminDashboardController(adminView);
                    adminView.setResizable(true);
                    adminView.pack();
                    adminView.setLocationRelativeTo(null);
                    adminView.setVisible(true);
                } else if (UserSession.isTeacher()) {
                    view.TeacherDashboard teacherView = new view.TeacherDashboard();
                    new TeacherDashboardController(teacherView);
                    teacherView.setResizable(true);
                    teacherView.pack();
                    teacherView.setLocationRelativeTo(null);
                    teacherView.setVisible(true);
                } else {
                    view.StudentDashboard studentView = new view.StudentDashboard();
                    new StudentDashboardController(studentView);
                    studentView.setResizable(true);
                    studentView.pack();
                    studentView.setLocationRelativeTo(null);
                    studentView.setVisible(true);
                }
            });
        }
    }
}
