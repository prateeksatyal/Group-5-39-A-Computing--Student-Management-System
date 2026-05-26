package controller;

import dao.CourseDAO;
import dao.StudentDAO;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.Objects;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.CourseData;
import model.StudentData;
import view.AdminDashboard;
import view.CourseManagementFrame;
import view.EnrollCourseFrame;
import view.ViewAssignedCourseFrame;

public class CourseController {
    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private CourseManagementFrame managementView;
    private EnrollCourseFrame enrollView;
    private ViewAssignedCourseFrame assignedView;

    public CourseController(CourseManagementFrame managementView) {
        this.managementView = managementView;
        
        // pack, enable resizing, and center natively
        this.managementView.setResizable(true);
        this.managementView.pack();
        this.managementView.setLocationRelativeTo(null);
        
        this.loadCoursesToTable();
        this.managementView.getAddButton().addActionListener(new OpenAddCourseListener());
        this.managementView.getUpdateButton().addActionListener(new OpenUpdateCourseListener());
        this.managementView.getDeleteButton().addActionListener(new DeleteCourseListener());
        this.managementView.getRefreshButton().addActionListener(e -> this.loadCoursesToTable());
        this.managementView.getBackButton().addActionListener(new BackToAdminDashboardListener());
        this.managementView.getDashboardButton().addActionListener(new BackToAdminDashboardListener());
        this.managementView.getEnrollStudentButton().addActionListener(new OpenEnrollFrameListener());
        this.managementView.getViewAssignedButton().addActionListener(new OpenAssignedFrameListener());
        
        // Students nav
        this.managementView.getStudentsButton().addActionListener(e -> {
            this.managementView.dispose();
            EventQueue.invokeLater(() -> {
                view.StudentManagementFrame frame = new view.StudentManagementFrame();
                new StudentController(frame);
                frame.setVisible(true);
            });
        });
        
        // Attendance nav
        this.managementView.getAttendanceButton().addActionListener(e -> {
            this.managementView.dispose();
            EventQueue.invokeLater(() -> {
                view.AttendanceSummaryFrame frame = new view.AttendanceSummaryFrame();
                new AttendanceController(frame);
                frame.setVisible(true);
            });
        });
        
        LogoutController.wireLogout(this.managementView, this.managementView.getLogoutButton());
        this.setupLiveSearch();
    }


    public CourseController(EnrollCourseFrame enrollView) {
        this.enrollView = enrollView;
        
        // Pack and center natively
        this.enrollView.pack();
        this.enrollView.setLocationRelativeTo(null);
        
        this.setupEnrollListeners();
    }

    public CourseController(ViewAssignedCourseFrame assignedView) {
        this.assignedView = assignedView;
        
        // Pack and center natively
        this.assignedView.pack();
        this.assignedView.setLocationRelativeTo(null);
        
        this.setupAssignedListeners();
    }

    public void saveCourse() {
        int credit;
        String name = JOptionPane.showInputDialog(this.managementView, "Enter Course Name:", "Add Course", -1);
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        String code = JOptionPane.showInputDialog(this.managementView, "Enter Course Code (e.g. CS101):", "Add Course", -1);
        if (code == null || code.trim().isEmpty()) {
            return;
        }
        String creditStr = JOptionPane.showInputDialog(this.managementView, "Enter Credit Hours (1 to 6):", "Add Course", -1);
        if (creditStr == null || creditStr.trim().isEmpty()) {
            return;
        }
        String teacher = JOptionPane.showInputDialog(this.managementView, "Enter Assigned Teacher Name:", "Add Course", -1);
        if (teacher == null || teacher.trim().isEmpty()) {
            return;
        }
        String semester = (String)JOptionPane.showInputDialog(this.managementView, "Select Semester:", "Add Course", 3, null, new String[]{"Fall 2026", "Spring 2026", "Summer 2026", "Winter 2026"}, "Fall 2026");
        if (semester == null) {
            return;
        }
        try {
            credit = Integer.parseInt(creditStr.trim());
            if (credit <= 0 || credit > 6) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.managementView, "Credit hours must be a positive integer between 1 and 6!", "Validation Error", 0);
            return;
        }
        List<CourseData> existing = this.courseDAO.searchCourse(code.trim());
        for (CourseData c : existing) {
            if (!c.getCourseCode().equalsIgnoreCase(code.trim())) continue;
            JOptionPane.showMessageDialog(this.managementView, "A course with code '" + code.trim() + "' already exists!", "Duplicate Error", 0);
            return;
        }
        CourseData course = new CourseData(0, name.trim(), code.trim().toUpperCase(), credit, teacher.trim(), semester);
        boolean success = this.courseDAO.insertCourse(course);
        if (success) {
            JOptionPane.showMessageDialog(this.managementView, "Course added successfully!", "Success", 1);
            this.loadCoursesToTable();
        } else {
            JOptionPane.showMessageDialog(this.managementView, "Database error adding course!", "Database Error", 0);
        }
    }

    public void updateCourse() {
        CourseData course;
        boolean success;
        int credit;
        int selectedRow = this.managementView.getCoursesTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this.managementView, "Please select a course from the table to update first!", "Selection Error", 2);
            return;
        }
        int courseId = (Integer)this.managementView.getCoursesTable().getValueAt(selectedRow, 0);
        String oldName = (String)this.managementView.getCoursesTable().getValueAt(selectedRow, 1);
        String oldCode = (String)this.managementView.getCoursesTable().getValueAt(selectedRow, 2);
        int oldCredit = (Integer)this.managementView.getCoursesTable().getValueAt(selectedRow, 3);
        String oldTeacher = (String)this.managementView.getCoursesTable().getValueAt(selectedRow, 4);
        String name = JOptionPane.showInputDialog(this.managementView, "Modify Course Name:", oldName);
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        String code = JOptionPane.showInputDialog(this.managementView, "Modify Course Code:", oldCode);
        if (code == null || code.trim().isEmpty()) {
            return;
        }
        String creditStr = JOptionPane.showInputDialog(this.managementView, "Modify Credit Hours:", String.valueOf(oldCredit));
        if (creditStr == null || creditStr.trim().isEmpty()) {
            return;
        }
        String teacher = JOptionPane.showInputDialog(this.managementView, "Modify Assigned Teacher:", oldTeacher);
        if (teacher == null || teacher.trim().isEmpty()) {
            return;
        }
        String semester = (String)JOptionPane.showInputDialog(this.managementView, "Select Semester:", "Update Course", 3, null, new String[]{"Fall 2026", "Spring 2026", "Summer 2026", "Winter 2026"}, "Fall 2026");
        if (semester == null) {
            return;
        }
        try {
            credit = Integer.parseInt(creditStr.trim());
            if (credit <= 0 || credit > 6) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.managementView, "Credit hours must be a positive integer between 1 and 6!", "Validation Error", 0);
            return;
        }
        if (!code.trim().equalsIgnoreCase(oldCode)) {
            List<CourseData> existing = this.courseDAO.searchCourse(code.trim());
            for (CourseData c : existing) {
                if (!c.getCourseCode().equalsIgnoreCase(code.trim())) continue;
                JOptionPane.showMessageDialog(this.managementView, "A course with code '" + code.trim() + "' already exists!", "Duplicate Error", 0);
                return;
            }
        }
        if (success = this.courseDAO.updateCourse(course = new CourseData(courseId, name.trim(), code.trim().toUpperCase(), credit, teacher.trim(), semester))) {
            JOptionPane.showMessageDialog(this.managementView, "Course updated successfully!", "Success", 1);
            this.loadCoursesToTable();
        } else {
            JOptionPane.showMessageDialog(this.managementView, "Database error updating course!", "Database Error", 0);
        }
    }

    public void deleteCourse() {
        int selectedRow = this.managementView.getCoursesTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this.managementView, "Please select a course from the table to delete first!", "Selection Error", 2);
            return;
        }
        int courseId = (Integer)this.managementView.getCoursesTable().getValueAt(selectedRow, 0);
        String code = (String)this.managementView.getCoursesTable().getValueAt(selectedRow, 2);
        int choice = JOptionPane.showConfirmDialog(this.managementView, "Are you sure you want to permanently delete course '" + code + "'? This will also remove all student enrollments under this course!", "Confirm Deletion", 0, 2);
        if (choice == 0) {
            boolean success = this.courseDAO.deleteCourse(courseId);
            if (success) {
                JOptionPane.showMessageDialog(this.managementView, "Course successfully deleted!", "Success", 1);
                this.loadCoursesToTable();
            } else {
                JOptionPane.showMessageDialog(this.managementView, "Database error deleting course!", "Database Error", 0);
            }
        }
    }

    public void loadCoursesToTable() {
        if (this.managementView == null) {
            return;
        }
        List<CourseData> list = this.courseDAO.getAllCourses();
        this.renderManagementTable(list);
        this.managementView.getTotalCoursesLabel().setText(String.valueOf(list.size()));
    }

    private void renderManagementTable(List<CourseData> list) {
        DefaultTableModel model = (DefaultTableModel)this.managementView.getCoursesTable().getModel();
        model.setRowCount(0);
        for (CourseData c : list) {
            model.addRow(new Object[]{c.getCourseId(), c.getCourseName(), c.getCourseCode(), c.getCreditHours(), c.getAssignedTeacher()});
        }
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
                if (query.isEmpty() || "Search Course Name or Code...".equals(query)) {
                    CourseController.this.loadCoursesToTable();
                } else {
                    List<CourseData> results = CourseController.this.courseDAO.searchCourse(query);
                    CourseController.this.renderManagementTable(results);
                }
            }
        });
    }

    private void setupEnrollListeners() {
        this.enrollView.getStudentIdField().addFocusListener(new FocusAdapter(){

            @Override
            public void focusLost(FocusEvent e) {
                CourseController.this.lookupStudentName();
            }
        });
        this.enrollView.getEnrollButton().addActionListener(e -> this.enrollStudent());
        this.enrollView.getClearButton().addActionListener(e -> this.clearEnrollForm());
        this.enrollView.getBackButton().addActionListener(e -> this.enrollView.dispose());
        this.populateCoursesComboBox(this.enrollView.getCourseComboBox());
    }

    private void lookupStudentName() {
        String studentId = this.enrollView.getStudentIdField().getText().trim();
        if (studentId.isEmpty() || "Enter Student ID".equals(studentId)) {
            this.enrollView.getStudentNameField().setText("Auto-loaded Student Name");
            this.enrollView.getStudentNameField().setForeground(Color.GRAY);
            return;
        }
        StudentData s = this.studentDAO.searchStudentById(studentId);
        if (s != null) {
            this.enrollView.getStudentNameField().setText(s.getFullName());
            this.enrollView.getStudentNameField().setForeground(Color.BLACK);
        } else {
            this.enrollView.getStudentNameField().setText("No Student Record Found!");
            this.enrollView.getStudentNameField().setForeground(Color.RED);
        }
    }

    private void populateCoursesComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Select Course");
        List<CourseData> courses = this.courseDAO.getAllCourses();
        for (CourseData c : courses) {
            comboBox.addItem(c.getCourseCode() + " - " + c.getCourseName());
        }
    }

    public void enrollStudent() {
        String studentId = this.enrollView.getStudentIdField().getText().trim();
        String studentName = this.enrollView.getStudentNameField().getText().trim();
        String selectedCourse = (String)this.enrollView.getCourseComboBox().getSelectedItem();
        String semester = (String)this.enrollView.getSemesterComboBox().getSelectedItem();
        if (studentId.isEmpty() || "Enter Student ID".equals(studentId) || studentName.isEmpty() || "Auto-loaded Student Name".equals(studentName) || "No Student Record Found!".equals(studentName) || selectedCourse == null || "Select Course".equals(selectedCourse) || semester == null || "Select Semester".equals(semester)) {
            JOptionPane.showMessageDialog(this.enrollView, "Please fill in all enrollment fields correctly!", "Validation Error", 2);
            return;
        }
        String courseCode = selectedCourse.split(" - ")[0].trim();
        List<CourseData> list = this.courseDAO.searchCourse(courseCode);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this.enrollView, "Course not found in database!", "Error", 0);
            return;
        }
        CourseData course = list.get(0);
        if (this.courseDAO.isStudentEnrolled(studentId, course.getCourseId())) {
            JOptionPane.showMessageDialog(this.enrollView, "Student '" + studentName + "' is already enrolled in course '" + course.getCourseCode() + "'!", "Duplication Error", 0);
            return;
        }
        CourseData enrollment = new CourseData(course.getCourseId(), course.getCourseName(), course.getCourseCode(), 0, course.getAssignedTeacher(), semester, studentId, studentName);
        boolean success = this.courseDAO.enrollStudentInCourse(enrollment);
        if (success) {
            JOptionPane.showMessageDialog(this.enrollView, "Student enrolled in course successfully!", "Success", 1);
            this.clearEnrollForm();
        } else {
            JOptionPane.showMessageDialog(this.enrollView, "Database error during student course enrollment!", "Database Error", 0);
        }
    }

    private void clearEnrollForm() {
        this.enrollView.getStudentIdField().setText("Enter Student ID");
        this.enrollView.getStudentIdField().setForeground(Color.GRAY);
        this.enrollView.getStudentNameField().setText("Auto-loaded Student Name");
        this.enrollView.getStudentNameField().setForeground(Color.GRAY);
        this.enrollView.getCourseComboBox().setSelectedIndex(0);
        this.enrollView.getSemesterComboBox().setSelectedIndex(0);
    }

    private void setupAssignedListeners() {
        this.assignedView.getRefreshButton().addActionListener(e -> this.loadAssignedCourses());
        this.assignedView.getBackButton().addActionListener(e -> this.assignedView.dispose());
        this.setupAssignedLiveSearch();
        this.loadAssignedCourses();
    }

    public void loadAssignedCourses() {
        if (this.assignedView == null) {
            return;
        }
        List<CourseData> list = this.courseDAO.getAssignedCourses();
        this.renderAssignedTable(list);
    }

    private void renderAssignedTable(List<CourseData> list) {
        DefaultTableModel model = (DefaultTableModel)this.assignedView.getAssignedTable().getModel();
        model.setRowCount(0);
        for (CourseData d : list) {
            model.addRow(new Object[]{d.getStudentId(), d.getStudentName(), d.getCourseName(), d.getCourseCode(), d.getSemester(), d.getAssignedTeacher()});
        }
    }

    private void setupAssignedLiveSearch() {
        final JTextField searchField = this.assignedView.getSearchField();
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
                if (query.isEmpty() || "Search Student ID, Name or Code...".equals(query)) {
                    CourseController.this.loadAssignedCourses();
                } else {
                    List<CourseData> results = CourseController.this.courseDAO.searchAssignedCourses(query);
                    CourseController.this.renderAssignedTable(results);
                }
            }
        });
    }

    private class OpenAddCourseListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CourseController.this.saveCourse();
        }
    }

    private class OpenUpdateCourseListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CourseController.this.updateCourse();
        }
    }

    private class DeleteCourseListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CourseController.this.deleteCourse();
        }
    }

    private class BackToAdminDashboardListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CourseController.this.managementView.dispose();
            if (CourseController.this.enrollView != null) {
                CourseController.this.enrollView.dispose();
            }
            if (CourseController.this.assignedView != null) {
                CourseController.this.assignedView.dispose();
            }
            EventQueue.invokeLater(() -> {
                if (UserSession.isAdmin()) {
                    AdminDashboard admin = new AdminDashboard();
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

    private class OpenEnrollFrameListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (CourseController.this.enrollView == null || !CourseController.this.enrollView.isDisplayable()) {
                CourseController.this.enrollView = new EnrollCourseFrame();
                new CourseController(CourseController.this.enrollView);
            }
            CourseController.this.populateCoursesComboBox(CourseController.this.enrollView.getCourseComboBox());
            CourseController.this.enrollView.setVisible(true);
            CourseController.this.enrollView.toFront();
        }
    }

    private class OpenAssignedFrameListener
    implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (CourseController.this.assignedView == null || !CourseController.this.assignedView.isDisplayable()) {
                CourseController.this.assignedView = new ViewAssignedCourseFrame();
                new CourseController(CourseController.this.assignedView);
            }
            CourseController.this.assignedView.setVisible(true);
            CourseController.this.assignedView.toFront();
        }
    }
}
