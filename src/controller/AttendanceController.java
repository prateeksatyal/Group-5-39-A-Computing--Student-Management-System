package controller;

import dao.AttendanceDAO;
import dao.CourseDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.AttendanceData;
import model.CourseData;
import view.AdminDashboard;
import view.AttendanceSummaryFrame;
import view.StudentManagementFrame;
import view.CourseManagementFrame;
import view.AcademicPerformanceFrame;

public class AttendanceController {
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final AttendanceSummaryFrame view;

    public AttendanceController(AttendanceSummaryFrame view) {
        this.view = view;
        
        // enable resizing and center natively (no pack() to avoid vertical layout shrinking)
        this.view.setResizable(true);
        this.view.setLocationRelativeTo(null);

        // Dynamically override table columns to include all 8 columns
        DefaultTableModel model = (DefaultTableModel) this.view.getAttendanceTable().getModel();
        model.setColumnIdentifiers(new Object[]{
            "Attendance ID", "Student ID", "Student Name", "Course", "Total Classes", "Attended", "Attendance %", "Date"
        });

        // Move constructor styling overrides
        this.view.getExportButton().setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.view.getExportButton().setBorder(null);
        if (this.view.getMarkSaveButton() != null) {
            this.view.getMarkSaveButton().setUI(new javax.swing.plaf.basic.BasicButtonUI());
            this.view.getMarkSaveButton().setBorder(null);
        }

        // Enforce Role hard stops
        if (!UserSession.isAdmin()) {
            this.view.getCoursesButton().setEnabled(false);
            this.view.getGradeComputationButton().setEnabled(false);
            this.view.getResultGenerationButton().setEnabled(false);
            this.view.getReportsExportButton().setEnabled(false);
        }

        // Setup menu icons
        this.setupMenuIcons();

        // Populate both course filter combo and mark-attendance course combo from DB
        loadCourseNamesForCombo();
        this.loadAttendanceToTable();
        this.view.getRefreshButton().addActionListener(e -> this.loadAttendanceToTable());
        this.view.getExportButton().addActionListener(e -> this.exportAttendanceSummary());
        this.view.getCourseComboBox().addActionListener(new CourseFilterListener());

        // Wire Save Attendance button
        this.view.getMarkSaveButton().addActionListener(e -> this.handleSaveAttendance());
        // Wire Clear button on mark panel
        this.view.getMarkClearButton().addActionListener(e -> this.clearMarkForm());
        // Auto-lookup student name when leaving the Student ID field
        this.view.getMarkStudentIdField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                markStudentNameLookup();
            }
        });

        // Setup field placeholders
        this.setupPlaceholder(this.view.getMarkStudentIdField(), "Enter Student ID");
        this.setupPlaceholder(this.view.getMarkTotalClassesField(), "e.g. 30");
        this.setupPlaceholder(this.view.getMarkAttendedField(), "e.g. 25");

        setupSidebarNavigation();
        setupSidebarEffects();
        LogoutController.wireLogout(view, view.getLogoutButton());
    }

    private void setupSidebarNavigation() {
        // Back & Dashboard navigate to respective dashboards based on role
        ActionListener backListener = e -> {
            this.view.dispose();
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
        };
        this.view.getBackButton().addActionListener(backListener);
        this.view.getDashboardButton().addActionListener(backListener);

        // Student Management
        this.view.getStudentsButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                this.view.dispose();
                EventQueue.invokeLater(() -> {
                    StudentManagementFrame frame = new StudentManagementFrame();
                    new controller.StudentController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this.view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Course Management
        this.view.getCoursesButton().addActionListener(e -> {
            if (UserSession.isAdmin()) {
                this.view.dispose();
                EventQueue.invokeLater(() -> {
                    CourseManagementFrame frame = new CourseManagementFrame();
                    new controller.CourseController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this.view, "Access denied. Only admins can access Course Management.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Attendance Management (current frame)
        this.view.getAttendanceButton().addActionListener(e -> {
            // Already on attendance summary
        });

        // Academic Performance
        this.view.getAcademicPerformanceButton().addActionListener(e -> {
            if (UserSession.isStudent()) {
                JOptionPane.showMessageDialog(this.view, "Access denied. Students cannot access Academic Performance.", "Security Alert", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String role = UserSession.isAdmin() ? "Admin" : "Teacher";
            this.view.dispose();
            EventQueue.invokeLater(() -> {
                AcademicPerformanceFrame frame = new AcademicPerformanceFrame(role);
                new controller.MarksController(frame, role);
                frame.setVisible(true);
            });
        });

        // Grade Computation
        this.view.getGradeComputationButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                this.view.dispose();
                EventQueue.invokeLater(() -> {
                    view.GradeComputationFrame frame = new view.GradeComputationFrame();
                    new MarksController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this.view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Result Generation
        this.view.getResultGenerationButton().addActionListener(e -> {
            if (UserSession.isAdmin() || UserSession.isTeacher()) {
                this.view.dispose();
                EventQueue.invokeLater(() -> {
                    view.GenerateResultFrame frame = new view.GenerateResultFrame();
                    new ResultController(frame);
                    frame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this.view, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Reports/Export
        this.view.getReportsExportButton().addActionListener(e -> {
            this.view.dispose();
            EventQueue.invokeLater(() -> {
                view.DownloadResultFrame frame = new view.DownloadResultFrame();
                new ResultController(frame);
                frame.setVisible(true);
            });
        });

        // Profile
        this.view.getProfileButton().addActionListener(e -> {
            this.view.dispose();
            EventQueue.invokeLater(() -> {
                view.ViewStudentProfile profileView = new view.ViewStudentProfile();
                new ViewStudentProfileController(profileView);
                profileView.setVisible(true);
            });
        });
    }

    private void setupSidebarEffects() {
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
            btn.addActionListener(e -> this.setActiveMenuItem(btn));
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

    public boolean saveAttendance(AttendanceData data) {
        if (data.getStudentId() == null || data.getStudentId().trim().isEmpty() || data.getStudentName() == null || data.getStudentName().trim().isEmpty() || data.getCourseName() == null || "Select Course".equals(data.getCourseName())) {
            JOptionPane.showMessageDialog(this.view, "Cannot save: Invalid student or course information!", "Validation Error", 2);
            return false;
        }
        if (data.getTotalClasses() <= 0) {
            JOptionPane.showMessageDialog(this.view, "Cannot save: Total classes must be greater than zero!", "Validation Error", 2);
            return false;
        }
        if (data.getAttendedClasses() < 0 || data.getAttendedClasses() > data.getTotalClasses()) {
            JOptionPane.showMessageDialog(this.view, "Cannot save: Attended classes must be between 0 and total classes!", "Validation Error", 2);
            return false;
        }
        double percent = (double)data.getAttendedClasses() / (double)data.getTotalClasses() * 100.0;
        data.setAttendancePercentage(percent);
        boolean success = this.attendanceDAO.insertAttendance(data);
        if (success) {
            this.loadAttendanceToTable();
        }
        return success;
    }

    public boolean updateAttendance(AttendanceData data) {
        if (data.getAttendanceId() <= 0) {
            JOptionPane.showMessageDialog(this.view, "Cannot update: Invalid Attendance ID specified!", "Validation Error", 2);
            return false;
        }
        if (data.getTotalClasses() <= 0) {
            JOptionPane.showMessageDialog(this.view, "Cannot update: Total classes must be greater than zero!", "Validation Error", 2);
            return false;
        }
        if (data.getAttendedClasses() < 0 || data.getAttendedClasses() > data.getTotalClasses()) {
            JOptionPane.showMessageDialog(this.view, "Cannot update: Attended classes must be between 0 and total classes!", "Validation Error", 2);
            return false;
        }
        double percent = (double)data.getAttendedClasses() / (double)data.getTotalClasses() * 100.0;
        data.setAttendancePercentage(percent);
        boolean success = this.attendanceDAO.updateAttendance(data);
        if (success) {
            this.loadAttendanceToTable();
        }
        return success;
    }

    public boolean deleteAttendance(int id) {
        if (id <= 0) {
            JOptionPane.showMessageDialog(this.view, "Cannot delete: Invalid ID specified!", "Validation Error", 2);
            return false;
        }
        int choice = JOptionPane.showConfirmDialog(this.view, "Are you sure you want to permanently delete attendance record #" + id + "?", "Confirm Deletion", 0, 2);
        if (choice == 0) {
            boolean success = this.attendanceDAO.deleteAttendance(id);
            if (success) {
                JOptionPane.showMessageDialog(this.view, "Attendance record successfully deleted!", "Deletion Success", 1);
                this.loadAttendanceToTable();
            }
            return success;
        }
        return false;
    }

    public void loadAttendanceToTable() {
        String selectedCourse = (String)this.view.getCourseComboBox().getSelectedItem();
        List<AttendanceData> list = selectedCourse == null || "Select Course".equals(selectedCourse) ? this.attendanceDAO.getAllAttendance() : this.attendanceDAO.getAttendanceByCourse(selectedCourse);
        this.renderTable(list);
        this.updateCardMetrics(list);
    }

    public void searchAttendance(String query) {
        if (query == null || query.trim().isEmpty()) {
            this.loadAttendanceToTable();
            return;
        }
        List<AttendanceData> list = this.attendanceDAO.searchAttendanceByStudentId(query);
        this.renderTable(list);
        this.updateCardMetrics(list);
    }

    private void renderTable(List<AttendanceData> list) {
        DefaultTableModel model = (DefaultTableModel) this.view.getAttendanceTable().getModel();
        model.setRowCount(0);
        for (AttendanceData d : list) {
            model.addRow(new Object[]{
                d.getAttendanceId(),
                d.getStudentId(),
                d.getStudentName(),
                d.getCourseName(),
                d.getTotalClasses(),
                d.getAttendedClasses(),
                String.format("%.1f%%", d.getAttendancePercentage()),
                d.getAttendanceDate()
            });
        }
    }

    private void updateCardMetrics(List<AttendanceData> list) {
        int enrolledCount = list.size();
        double sumPercentage = 0.0;
        for (AttendanceData d : list) {
            sumPercentage += d.getAttendancePercentage();
        }
        double avgPercentage = enrolledCount > 0 ? sumPercentage / (double)enrolledCount : 0.0;
        this.view.getTotalStudentsLabel().setText(String.format("%,d", enrolledCount));
        this.view.getAvgAttendanceLabel().setText(String.format("%.1f%%", avgPercentage));
    }

    public void exportAttendanceSummary() {
        List<AttendanceData> list;
        String selectedCourse = (String)this.view.getCourseComboBox().getSelectedItem();
        List<AttendanceData> list2 = list = selectedCourse == null || "Select Course".equals(selectedCourse) ? this.attendanceDAO.getAllAttendance() : this.attendanceDAO.getAttendanceByCourse(selectedCourse);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this.view, "No attendance records available to export!", "Export Failed", 2);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Attendance Summary Report");
        fileChooser.setSelectedFile(new File("Attendance_Summary_Report.csv"));
        int userSelection = fileChooser.showSaveDialog(this.view);
        if (userSelection == 0) {
            File fileToSave = fileChooser.getSelectedFile();
            String path = fileToSave.getAbsolutePath();
            if (!path.toLowerCase().endsWith(".csv")) {
                fileToSave = new File(path + ".csv");
            }
            try (FileWriter csvWriter = new FileWriter(fileToSave);){
                csvWriter.append("Attendance ID,Student ID,Student Name,Course Name,Total Classes,Attended Classes,Attendance %,Attendance Date\n");
                for (AttendanceData d : list) {
                    csvWriter.append(String.valueOf(d.getAttendanceId())).append(",").append(d.getStudentId()).append(",").append("\"").append(d.getStudentName().replace("\"", "\"\"")).append("\",").append("\"").append(d.getCourseName().replace("\"", "\"\"")).append("\",").append(String.valueOf(d.getTotalClasses())).append(",").append(String.valueOf(d.getAttendedClasses())).append(",").append(String.format("%.1f%%", d.getAttendancePercentage())).append(",").append(d.getAttendanceDate()).append("\n");
                }
                csvWriter.flush();
                JOptionPane.showMessageDialog(this.view, "Attendance summary report successfully exported to CSV!\nLocation: " + fileToSave.getAbsolutePath(), "Export Successful", 1);
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(this.view, "Error writing file: " + e.getMessage(), "Export Failure", 0);
            }
        }
    }

    private class CourseFilterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AttendanceController.this.loadAttendanceToTable();
        }
    }

    // ---- Bug 1 helpers ----

    /** Looks up the student name from StudentDAO when user tabs out of ID field */
    private void markStudentNameLookup() {
        String id = this.view.getMarkStudentIdField().getText().trim();
        if (id.isEmpty() || "Enter Student ID".equals(id)) {
            this.view.getMarkStudentNameField().setText("Auto-filled");
            this.view.getMarkStudentNameField().setForeground(new Color(128, 128, 128));
            return;
        }
        dao.StudentDAO sDao = new dao.StudentDAO();
        model.StudentData s = sDao.searchStudentById(id);
        if (s != null) {
            this.view.getMarkStudentNameField().setText(s.getFullName());
            this.view.getMarkStudentNameField().setForeground(Color.BLACK);
        } else {
            this.view.getMarkStudentNameField().setText("No record found");
            this.view.getMarkStudentNameField().setForeground(Color.RED);
        }
    }

    /** Reads the Mark Attendance form, validates, and calls saveAttendance() */
    private void handleSaveAttendance() {
        String studentId   = this.view.getMarkStudentIdField().getText().trim();
        String studentName = this.view.getMarkStudentNameField().getText().trim();
        String course      = (String) this.view.getMarkCourseCombo().getSelectedItem();
        String totalStr    = this.view.getMarkTotalClassesField().getText().trim();
        String attendedStr = this.view.getMarkAttendedField().getText().trim();
        String date        = this.view.getMarkDateField().getText().trim();

        if (studentId.isEmpty() || "Enter Student ID".equals(studentId)
                || "No record found".equals(studentName)
                || "Auto-filled".equals(studentName)
                || course == null || "Select Course".equals(course)
                || totalStr.isEmpty() || "e.g. 30".equals(totalStr)
                || attendedStr.isEmpty() || "e.g. 25".equals(attendedStr)
                || date.isEmpty()) {
            JOptionPane.showMessageDialog(this.view,
                "Please fill in all fields before saving attendance!",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int total, attended;
        try {
            total    = Integer.parseInt(totalStr);
            attended = Integer.parseInt(attendedStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.view,
                "Total Classes and Attended must be whole numbers!",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        AttendanceData data = new AttendanceData();
        data.setStudentId(studentId);
        data.setStudentName(studentName);
        data.setCourseName(course);
        data.setTotalClasses(total);
        data.setAttendedClasses(attended);
        data.setAttendanceDate(date);

        boolean ok = this.saveAttendance(data);
        if (ok) {
            JOptionPane.showMessageDialog(this.view,
                "Attendance record saved successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            this.clearMarkForm();
        }
    }

    /** Resets the Mark Attendance form to its placeholder state */
    private void clearMarkForm() {
        this.view.getMarkStudentIdField().setText("Enter Student ID");
        this.view.getMarkStudentIdField().setForeground(new Color(128, 128, 128));
        this.view.getMarkStudentNameField().setText("Auto-filled");
        this.view.getMarkStudentNameField().setForeground(new Color(128, 128, 128));
        this.view.getMarkCourseCombo().setSelectedIndex(0);
        this.view.getMarkTotalClassesField().setText("e.g. 30");
        this.view.getMarkTotalClassesField().setForeground(new Color(128, 128, 128));
        this.view.getMarkAttendedField().setText("e.g. 25");
        this.view.getMarkAttendedField().setForeground(new Color(128, 128, 128));
        this.view.getMarkDateField().setText(java.time.LocalDate.now().toString());
    }

    // ---- Bug 3 helper ----

    /**
     * Loads distinct course names from the courses table and pushes them
     * into both the filter combo and the mark-attendance course combo.
     */
    private void loadCourseNamesForCombo() {
        List<CourseData> courses = courseDAO.getAllCourses();
        this.view.getCourseComboBox().removeAllItems();
        this.view.getCourseComboBox().addItem("Select Course");
        this.view.getMarkCourseCombo().removeAllItems();
        this.view.getMarkCourseCombo().addItem("Select Course");
        for (CourseData c : courses) {
            String name = c.getCourseName();
            this.view.getCourseComboBox().addItem(name);
            this.view.getMarkCourseCombo().addItem(name);
        }
    }

    private void setupPlaceholder(final JTextField field, final String placeholder) {
        field.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                if (placeholder.equals(field.getText())) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(128, 128, 128));
                }
            }
        });
    }

    private void setupMenuIcons() {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        this.view.getTitleLabel().setText("SMS");
        this.view.getTitleLabel().setIcon(new AttendanceSummaryFrame.VectorIcon("hamburger", 20, whiteColor));
        this.view.getTitleLabel().setIconTextGap(12);
        this.view.getDashboardButton().setText("Dashboard");
        this.view.getDashboardButton().setIconTextGap(12);
        this.view.getStudentsButton().setText("Students Management");
        this.view.getStudentsButton().setIconTextGap(12);
        this.view.getCoursesButton().setText("Courses Management");
        this.view.getCoursesButton().setIconTextGap(12);
        this.view.getAttendanceButton().setText("Attendance Management");
        this.view.getAttendanceButton().setIconTextGap(12);
        this.view.getAcademicPerformanceButton().setText("Academic Performance");
        this.view.getAcademicPerformanceButton().setIconTextGap(12);
        this.view.getGradeComputationButton().setText("Grade Computation");
        this.view.getGradeComputationButton().setIconTextGap(12);
        this.view.getResultGenerationButton().setText("Result Generation");
        this.view.getResultGenerationButton().setIconTextGap(12);
        this.view.getReportsExportButton().setText("Reports Export");
        this.view.getReportsExportButton().setIconTextGap(12);
        this.view.getProfileButton().setText("Profile");
        this.view.getProfileButton().setIconTextGap(12);
        this.view.getLogoutButton().setText("Logout");
        this.view.getLogoutButton().setIconTextGap(12);
        this.setActiveMenuItem(this.view.getAttendanceButton());
    }

    private void setActiveMenuItem(JButton activeBtn) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        Color activeBg = new Color(243, 227, 225);
        Color normalColor = new Color(11, 27, 226);
        Color normalBg = new Color(224, 242, 248);
        JButton[] buttons = new JButton[]{
            this.view.getDashboardButton(),
            this.view.getStudentsButton(),
            this.view.getCoursesButton(),
            this.view.getAttendanceButton(),
            this.view.getAcademicPerformanceButton(),
            this.view.getGradeComputationButton(),
            this.view.getResultGenerationButton(),
            this.view.getReportsExportButton(),
            this.view.getProfileButton(),
            this.view.getLogoutButton()
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
                btn.setIcon(new AttendanceSummaryFrame.VectorIcon(type, 28, whiteColor));
                continue;
            }
            btn.setBackground(normalBg);
            btn.setForeground(normalColor);
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setIcon(new AttendanceSummaryFrame.VectorIcon(type, 28, activeColor));
        }
    }
}
