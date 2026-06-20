package controller;

import dao.ResultDAO;
import model.ResultData;
import view.DownloadResultFrame;
import view.GenerateResultFrame;
import view.ViewResultFrame;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.EventQueue;
import java.util.List;

/**
 * MVC Controller for Sprint 3 result screens:
 *   - GenerateResultFrame   (generate/save results from marks)
 *   - ViewResultFrame       (view student transcript with GPA)
 *   - DownloadResultFrame   (export/print scorecard)
 *
 * No SQL here — all DB operations delegate to ResultDAO.
 * Follows the same pattern as MarksController, AttendanceController.
 */
public class ResultController {

    private final ResultDAO resultDAO = new ResultDAO();

    // Each constructor wires one view — only the matching field will be non-null.
    private GenerateResultFrame generateView;
    private ViewResultFrame     viewResultView;
    private DownloadResultFrame downloadView;

    // ── Constructor: GenerateResultFrame ─────────────────────────────────────

    public ResultController(GenerateResultFrame view) {
        this.generateView = view;
        
        if (UserSession.isStudent()) {
            JOptionPane.showMessageDialog(view, "Access denied. Students cannot access Result Generation.", "Security Alert", JOptionPane.WARNING_MESSAGE);
            view.dispose();
            return;
        }
        
        // WindowOpened focus handling
        this.generateView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                view.getContentPane().setFocusable(true);
                view.getContentPane().requestFocusInWindow();
            }
        });

        // Styling and sidebar setup
        view.getGenerateButton().setUI(new javax.swing.plaf.basic.BasicButtonUI());
        view.getGenerateButton().setBorder(null);
        setupMenuIcons(view);

        // Expand the frame programmatically to show the preview on the right
        this.generateView.setSize(1040, 520);
        this.generateView.setLocationRelativeTo(null);
        
        // Configure preview scroll pane and text area visibility
        JTextArea previewArea = this.generateView.getPreviewTextArea();
        if (previewArea != null) {
            previewArea.setVisible(true);
            previewArea.setEditable(false);
            previewArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 11));
            java.awt.Container scrollPane = previewArea.getParent().getParent();
            if (scrollPane instanceof JScrollPane) {
                scrollPane.setVisible(true);
                scrollPane.setBounds(525, 70, 460, 330); // Position on the right side
            }
            java.awt.Container jPanel3 = previewArea.getParent().getParent().getParent();
            if (jPanel3 != null) {
                jPanel3.setBounds(240, 0, 800, 480); // Expand right-side panel
            }
        }
        
        LogoutController.wireLogout(this.generateView, this.generateView.getLogoutButton());
        wireGenerateFrame();
    }

    // ── Constructor: ViewResultFrame ──────────────────────────────────────────

    public ResultController(ViewResultFrame view) {
        this.viewResultView = view;
        
        setupMenuIcons(view);
        setupStatCards(view);
        
        // Setup interactive effects for sidebar buttons
        JButton[] sidebarButtons = new JButton[]{
            view.getDashboardButton(),
            view.getProfileButton(),
            view.getAttendanceButton(),
            view.getCoursesButton(),
            view.getDownloadResultButton()
        };
        for (JButton btn : sidebarButtons) {
            addSidebarHoverEffects(btn, view.getDownloadResultButton());
        }

        this.viewResultView.pack();
        this.viewResultView.setLocationRelativeTo(null);
        wireViewResultFrame();
    }

    // ── Constructor: DownloadResultFrame ──────────────────────────────────────

    public ResultController(DownloadResultFrame view) {
        this.downloadView = view;
        
        // WindowOpened focus handling
        this.downloadView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                view.getContentPane().setFocusable(true);
                view.getContentPane().requestFocusInWindow();
            }
        });

        // Styling and sidebar setup
        view.getDownloadButton().setUI(new javax.swing.plaf.basic.BasicButtonUI());
        view.getDownloadButton().setBorder(null);
        setupMenuIcons(view);

        this.downloadView.pack();
        this.downloadView.setLocationRelativeTo(null);
        LogoutController.wireLogout(this.downloadView, this.downloadView.getLogoutButton());
        wireDownloadFrame();
    }

    // ── GenerateResultFrame wiring ────────────────────────────────────────────

    private void wireGenerateFrame() {
        // Populate combo-boxes with defaults
        generateView.getTermComboBox().setModel(
            new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));
        generateView.getCourseComboBox().setModel(
            new DefaultComboBoxModel<>(new String[]{"BSc Computer Science", "BIT", "BBA"}));
        generateView.getSectionComboBox().setModel(
            new DefaultComboBoxModel<>(new String[]{"Section A", "Section B", "Section C"}));

        // Generate button — compute results from marks and save
        generateView.getGenerateButton().addActionListener(e -> generateResults());

        // Back button
        generateView.getBackButton().addActionListener(e -> {
            generateView.dispose();
            navigateToDashboard();
        });

        // Sidebar navigation
        wireSidebarAdmin(generateView);
    }

    private void generateResults() {
        String term    = (String) generateView.getTermComboBox().getSelectedItem();
        String course  = (String) generateView.getCourseComboBox().getSelectedItem();
        String section = (String) generateView.getSectionComboBox().getSelectedItem();

        List<ResultData> results = resultDAO.generateResultsFromMarks(term, course, section);

        StringBuilder preview = new StringBuilder();
        preview.append("=== RESULT SHEET ===\n");
        preview.append("Term: ").append(term)
               .append(" | Course: ").append(course)
               .append(" | Section: ").append(section).append("\n");
        preview.append("─".repeat(60)).append("\n");
        preview.append(String.format("%-12s %-22s %-6s %-6s %-7s %-5s %s\n",
            "ID", "Name", "Assgn", "Exam", "Total", "Grade", "Status"));
        preview.append("─".repeat(60)).append("\n");

        if (results.isEmpty()) {
            preview.append("No marks data found for the selected criteria.\n");
            preview.append("Please ensure marks have been entered in Academic Performance.\n");
        } else {
            for (ResultData r : results) {
                // Save each row to DB
                resultDAO.saveResult(r);
                preview.append(String.format("%-12s %-22s %-6.1f %-6.1f %-7.1f %-5s %s\n",
                    r.getStudentId(),
                    r.getStudentName().length() > 20 ? r.getStudentName().substring(0, 20) : r.getStudentName(),
                    r.getAssignmentMarks(),
                    r.getExamMarks(),
                    r.getTotalMarks(),
                    r.getGrade(),
                    r.getStatus()
                ));
            }
            preview.append("─".repeat(60)).append("\n");
            preview.append("Total students: ").append(results.size()).append("\n");
        }

        generateView.getPreviewTextArea().setText(preview.toString());

        if (!results.isEmpty()) {
            JOptionPane.showMessageDialog(generateView,
                results.size() + " result(s) generated and saved successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ── ViewResultFrame wiring ────────────────────────────────────────────────

    private void wireViewResultFrame() {
        // Populate term combo-box
        viewResultView.getTermComboBox().setModel(
            new DefaultComboBoxModel<>(new String[]{"All Terms", "Term 1", "Term 2", "Term 3"}));

        // Load results for logged-in student (or all, if admin/teacher)
        loadResultsIntoTable();

        // Reload on term change
        viewResultView.getTermComboBox().addActionListener(e -> loadResultsIntoTable());

        // Back button
        viewResultView.getBackButton().addActionListener(e -> {
            viewResultView.dispose();
            navigateToDashboard();
        });

        // Download Result button — navigate to DownloadResultFrame
        viewResultView.getDownloadResultButton().addActionListener(e -> {
            viewResultView.dispose();
            EventQueue.invokeLater(() -> {
                DownloadResultFrame dlFrame = new DownloadResultFrame();
                new ResultController(dlFrame);
                dlFrame.setVisible(true);
            });
        });

        // Dashboard button
        viewResultView.getDashboardButton().addActionListener(e -> {
            viewResultView.dispose();
            navigateToDashboard();
        });

        // Sidebar: Profile
        viewResultView.getProfileButton().addActionListener(e -> {
            viewResultView.dispose();
            EventQueue.invokeLater(() -> {
                view.ViewStudentProfile frame = new view.ViewStudentProfile();
                new ViewStudentProfileController(frame);
                frame.setVisible(true);
            });
        });

        // Sidebar: Attendance
        viewResultView.getAttendanceButton().addActionListener(e -> {
            viewResultView.dispose();
            EventQueue.invokeLater(() -> {
                view.AttendanceSummaryFrame frame = new view.AttendanceSummaryFrame();
                AttendanceController ac = new AttendanceController(frame);
                if (UserSession.isStudent()) {
                    String sid = resolveStudentId();
                    if (sid != null && !sid.isEmpty()) {
                        ac.searchAttendance(sid);
                    }
                    frame.getMarkSaveButton().setEnabled(false);
                }
                frame.setVisible(true);
            });
        });

        // Sidebar: Courses
        viewResultView.getCoursesButton().addActionListener(e -> {
            viewResultView.dispose();
            EventQueue.invokeLater(() -> {
                view.ViewAssignedCourseFrame frame = new view.ViewAssignedCourseFrame();
                new CourseController(frame);
                frame.setVisible(true);
            });
        });
    }

    private void loadResultsIntoTable() {
        DefaultTableModel model = (DefaultTableModel) viewResultView.getGradesTable().getModel();
        model.setRowCount(0);

        // Set columns if not set
        if (model.getColumnCount() == 0) {
            model.addColumn("Student ID");
            model.addColumn("Student Name");
            model.addColumn("Term");
            model.addColumn("Course");
            model.addColumn("Assignment");
            model.addColumn("Exam");
            model.addColumn("Total");
            model.addColumn("Grade");
            model.addColumn("GPA");
            model.addColumn("Status");
        }

        String selectedTerm = (String) viewResultView.getTermComboBox().getSelectedItem();
        List<ResultData> results;

        // Role-aware data loading
        if (UserSession.isAdmin() || UserSession.isTeacher()) {
            if ("All Terms".equals(selectedTerm)) {
                results = resultDAO.getAllResults();
            } else {
                results = resultDAO.getResultsByTerm(selectedTerm);
            }
        } else {
            // Student: show only their own results
            String studentId = resolveStudentId();
            if ("All Terms".equals(selectedTerm)) {
                results = resultDAO.getResultsByStudentId(studentId);
            } else {
                results = resultDAO.getResultsByStudentAndTerm(studentId, selectedTerm);
            }
            // Update header labels
            if (!results.isEmpty()) {
                viewResultView.getStudentIdLabel().setText("Student ID: " + results.get(0).getStudentId());
                viewResultView.getStudentNameLabel().setText("Student: " + results.get(0).getStudentName());
            }
        }

        if (results.isEmpty()) {
            // Show a demo row so the view is not blank
            model.addRow(new Object[]{"N/A", "No results found", "-", "-", 0.0, 0.0, 0.0, "-", 0.0, "-"});
        } else {
            for (ResultData r : results) {
                model.addRow(new Object[]{
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getTerm(),
                    r.getCourse(),
                    r.getAssignmentMarks(),
                    r.getExamMarks(),
                    r.getTotalMarks(),
                    r.getGrade(),
                    r.getGpa(),
                    r.getStatus()
                });
            }
        }
    }

    // ── DownloadResultFrame wiring ────────────────────────────────────────────

    private void wireDownloadFrame() {
        // Populate term combo-box
        downloadView.getTermComboBox().setModel(
            new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));

        // Generate preview on term change
        downloadView.getTermComboBox().addActionListener(e -> generateScorecardPreview());

        // Initial preview
        generateScorecardPreview();

        // Download PDF button
        downloadView.getDownloadButton().addActionListener(e -> downloadScorecard());

        // Print button
        downloadView.getPrintButton().addActionListener(e -> printScorecard());

        // Back button
        downloadView.getBackButton().addActionListener(e -> {
            downloadView.dispose();
            navigateToDashboard();
        });

        // Sidebar: View Results
        downloadView.getViewResultsButton().addActionListener(e -> {
            downloadView.dispose();
            EventQueue.invokeLater(() -> {
                ViewResultFrame vrFrame = new ViewResultFrame();
                new ResultController(vrFrame);
                vrFrame.setVisible(true);
            });
        });

        // Sidebar: Dashboard
        downloadView.getDashboardButton().addActionListener(e -> {
            downloadView.dispose();
            navigateToDashboard();
        });

        // Sidebar: Profile
        downloadView.getProfileButton().addActionListener(e -> {
            downloadView.dispose();
            EventQueue.invokeLater(() -> {
                view.ViewStudentProfile frame = new view.ViewStudentProfile();
                new ViewStudentProfileController(frame);
                frame.setVisible(true);
            });
        });

        // Sidebar: Attendance
        downloadView.getAttendanceButton().addActionListener(e -> {
            downloadView.dispose();
            EventQueue.invokeLater(() -> {
                view.AttendanceSummaryFrame frame = new view.AttendanceSummaryFrame();
                AttendanceController ac = new AttendanceController(frame);
                if (UserSession.isStudent()) {
                    String sid = resolveStudentId();
                    if (sid != null && !sid.isEmpty()) {
                        ac.searchAttendance(sid);
                    }
                    frame.getMarkSaveButton().setEnabled(false);
                }
                frame.setVisible(true);
            });
        });

        // Sidebar: Courses
        downloadView.getCoursesButton().addActionListener(e -> {
            downloadView.dispose();
            EventQueue.invokeLater(() -> {
                view.ViewAssignedCourseFrame frame = new view.ViewAssignedCourseFrame();
                new CourseController(frame);
                frame.setVisible(true);
            });
        });
    }

    private void generateScorecardPreview() {
        String term = (String) downloadView.getTermComboBox().getSelectedItem();
        String studentId = resolveStudentId();

        List<ResultData> results;
        if (UserSession.isAdmin() || UserSession.isTeacher()) {
            results = resultDAO.getResultsByTerm(term);
        } else {
            results = resultDAO.getResultsByStudentAndTerm(studentId, term);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════╗\n");
        sb.append("║          STUDENT MANAGEMENT SYSTEM — SCORECARD         ║\n");
        sb.append("╚════════════════════════════════════════════════════════╝\n\n");
        sb.append("Term: ").append(term).append("\n");
        sb.append("Generated: ").append(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
                                            .format(new java.util.Date())).append("\n\n");
        sb.append(String.format("%-10s %-20s %-16s %-6s %-7s %-5s %-4s %s\n",
            "ID", "Name", "Course", "Assign", "Exam", "Total", "Grd", "Status"));
        sb.append("─".repeat(78)).append("\n");

        if (results.isEmpty()) {
            sb.append("No results found for ").append(term).append(".\n");
            sb.append("Generate results first from the Result Generation screen.\n");
        } else {
            double totalGpa = 0;
            for (ResultData r : results) {
                totalGpa += r.getGpa();
                String name = r.getStudentName();
                if (name.length() > 18) name = name.substring(0, 18);
                String course = r.getCourse();
                if (course.length() > 14) course = course.substring(0, 14);
                sb.append(String.format("%-10s %-20s %-16s %-6.1f %-7.1f %-5.1f %-4s %s\n",
                    r.getStudentId(), name, course,
                    r.getAssignmentMarks(), r.getExamMarks(), r.getTotalMarks(),
                    r.getGrade(), r.getStatus()));
            }
            sb.append("─".repeat(78)).append("\n");
            sb.append(String.format("Overall Average GPA: %.2f\n", totalGpa / results.size()));
        }

        downloadView.getReportTextArea().setText(sb.toString());
    }

    private void downloadScorecard() {
        String content = downloadView.getReportTextArea().getText();
        if (content.trim().isEmpty()) {
            JOptionPane.showMessageDialog(downloadView, "No scorecard to download.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Use JFileChooser to save as .txt (PDF requires iText library not in project)
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Save Scorecard");
        chooser.setSelectedFile(new java.io.File("scorecard_" + (String) downloadView.getTermComboBox().getSelectedItem() + ".txt"));
        int result = chooser.showSaveDialog(downloadView);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(file))) {
                pw.print(content);
                JOptionPane.showMessageDialog(downloadView, "Scorecard saved to:\n" + file.getAbsolutePath(), "Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(downloadView, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void printScorecard() {
        try {
            downloadView.getReportTextArea().print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(downloadView, "Print failed: " + e.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Shared sidebar navigation helper (Admin screens) ─────────────────────

    private void wireSidebarAdmin(JFrame source) {
        // This is called for GenerateResultFrame which has Admin sidebar
        if (generateView != null) {
            generateView.getDashboardButton().addActionListener(e -> {
                generateView.dispose();
                navigateToDashboard();
            });
            generateView.getStudentsButton().addActionListener(e -> {
                if (UserSession.isAdmin() || UserSession.isTeacher()) {
                    generateView.dispose();
                    EventQueue.invokeLater(() -> {
                        view.StudentManagementFrame frame = new view.StudentManagementFrame();
                        new StudentController(frame);
                        frame.setVisible(true);
                    });
                } else {
                    JOptionPane.showMessageDialog(generateView, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
                }
            });
            generateView.getCoursesButton().addActionListener(e -> {
                if (UserSession.isAdmin()) {
                    generateView.dispose();
                    EventQueue.invokeLater(() -> {
                        view.CourseManagementFrame frame = new view.CourseManagementFrame();
                        new CourseController(frame);
                        frame.setVisible(true);
                    });
                } else {
                    JOptionPane.showMessageDialog(generateView, "Access denied.", "Security Alert", JOptionPane.WARNING_MESSAGE);
                }
            });
            generateView.getAttendanceButton().addActionListener(e -> {
                generateView.dispose();
                EventQueue.invokeLater(() -> {
                    view.AttendanceSummaryFrame frame = new view.AttendanceSummaryFrame();
                    new AttendanceController(frame);
                    frame.setVisible(true);
                });
            });
            generateView.getAcademicPerformanceButton().addActionListener(e -> {
                generateView.dispose();
                EventQueue.invokeLater(() -> {
                    view.AcademicPerformanceFrame af = new view.AcademicPerformanceFrame("Admin");
                    new MarksController(af, "Admin");
                    af.setVisible(true);
                });
            });
            generateView.getGradeComputationButton().addActionListener(e -> {
                generateView.dispose();
                EventQueue.invokeLater(() -> {
                    view.GradeComputationFrame gf = new view.GradeComputationFrame();
                    new MarksController(gf);
                    gf.setVisible(true);
                });
            });
            generateView.getReportsExportButton().addActionListener(e -> {
                generateView.dispose();
                EventQueue.invokeLater(() -> {
                    view.DownloadResultFrame frame = new view.DownloadResultFrame();
                    new ResultController(frame);
                    frame.setVisible(true);
                });
            });
            generateView.getProfileButton().addActionListener(e -> {
                generateView.dispose();
                EventQueue.invokeLater(() -> {
                    view.ViewStudentProfile frame = new view.ViewStudentProfile();
                    new ViewStudentProfileController(frame);
                    frame.setVisible(true);
                });
            });
        }
    }

    // ── Role-aware dashboard navigation ──────────────────────────────────────

    private void navigateToDashboard() {
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

    private void setupMenuIcons(GenerateResultFrame view) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);

        view.getTitleLabel().setText("SMS");
        view.getTitleLabel().setIcon(new GenerateResultFrame.VectorIcon("hamburger", 20, whiteColor));
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

        this.setActiveMenuItem(view, view.getResultGenerationButton());

        JButton[] pageTabs = new JButton[]{
            view.getDashboardButton(),
            view.getStudentsButton(),
            view.getCoursesButton(),
            view.getAttendanceButton(),
            view.getAcademicPerformanceButton(),
            view.getGradeComputationButton(),
            view.getResultGenerationButton(),
            view.getReportsExportButton(),
            view.getProfileButton()
        };
        for (JButton btn : pageTabs) {
            btn.addActionListener(e -> this.setActiveMenuItem(view, btn));
        }

        JButton[] sidebarButtons = new JButton[]{
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
        for (JButton btn : sidebarButtons) {
            this.addInteractiveEffects(btn);
        }

        JButton[] actionButtons = new JButton[]{view.getGenerateButton(), view.getBackButton()};
        for (final JButton btn : actionButtons) {
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn.addFocusListener(new java.awt.event.FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    btn.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (btn == view.getGenerateButton()) {
                        btn.setBorder(null);
                    } else {
                        btn.setBorder(BorderFactory.createEtchedBorder());
                    }
                }
            });
        }

        view.getDetailedRadioButton().setSelected(true);
        view.getSubjectMarksCheckBox().setSelected(true);
        view.getGpaCheckBox().setSelected(true);
        view.getRankCheckBox().setSelected(true);
    }

    private void addInteractiveEffects(final JButton btn) {
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                btn.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                btn.setBorder(null);
            }
        });
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn.getBackground().equals(new Color(224, 242, 248))) {
                    btn.setBackground(new Color(200, 235, 245));
                } else if (btn.getBackground().equals(new Color(243, 227, 225))) {
                    btn.setBackground(new Color(233, 212, 209));
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn.getBackground().equals(new Color(200, 235, 245))) {
                    btn.setBackground(new Color(224, 242, 248));
                } else if (btn.getBackground().equals(new Color(233, 212, 209))) {
                    btn.setBackground(new Color(243, 227, 225));
                }
            }
        });
    }

    private void setActiveMenuItem(GenerateResultFrame view, JButton activeBtn) {
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
                btn.setIcon(new GenerateResultFrame.VectorIcon(type, 28, whiteColor));
            } else {
                btn.setBackground(normalBg);
                btn.setForeground(normalColor);
                btn.setContentAreaFilled(true);
                btn.setOpaque(true);
                btn.setIcon(new GenerateResultFrame.VectorIcon(type, 28, activeColor));
            }
        }
    }

    private void setupMenuIcons(DownloadResultFrame view) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);

        view.getTitleLabel().setText("SMS");
        view.getTitleLabel().setIcon(new DownloadResultFrame.VectorIcon("hamburger", 20, whiteColor));
        view.getTitleLabel().setIconTextGap(12);

        view.getDashboardButton().setText("Dashboard");
        view.getDashboardButton().setIconTextGap(12);
        view.getProfileButton().setText("View Profile");
        view.getProfileButton().setIconTextGap(12);
        view.getAttendanceButton().setText("Attendance Summary");
        view.getAttendanceButton().setIconTextGap(12);
        view.getCoursesButton().setText("Enrolled Courses");
        view.getCoursesButton().setIconTextGap(12);
        view.getViewResultsButton().setText("View Results");
        view.getViewResultsButton().setIconTextGap(12);
        view.getDownloadResultButton().setText("Download Result");
        view.getDownloadResultButton().setIconTextGap(12);
        view.getLogoutButton().setText("Logout");
        view.getLogoutButton().setIconTextGap(12);

        this.setActiveMenuItem(view, view.getDownloadResultButton());

        JButton[] pageTabs = new JButton[]{
            view.getDashboardButton(),
            view.getProfileButton(),
            view.getAttendanceButton(),
            view.getCoursesButton(),
            view.getViewResultsButton(),
            view.getDownloadResultButton()
        };
        for (JButton btn : pageTabs) {
            btn.addActionListener(e -> this.setActiveMenuItem(view, btn));
        }

        JButton[] sidebarButtons = new JButton[]{
            view.getDashboardButton(),
            view.getProfileButton(),
            view.getAttendanceButton(),
            view.getCoursesButton(),
            view.getViewResultsButton(),
            view.getDownloadResultButton(),
            view.getLogoutButton()
        };
        for (JButton btn : sidebarButtons) {
            this.addInteractiveEffects(btn);
        }

        JButton[] actionButtons = new JButton[]{view.getDownloadButton(), view.getPrintButton(), view.getBackButton()};
        for (final JButton btn : actionButtons) {
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btn.addFocusListener(new java.awt.event.FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    btn.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (btn == view.getDownloadButton()) {
                        btn.setBorder(null);
                    } else {
                        btn.setBorder(BorderFactory.createEtchedBorder());
                    }
                }
            });
        }
    }

    private void setActiveMenuItem(DownloadResultFrame view, JButton activeBtn) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        Color activeBg = new Color(243, 227, 225);
        Color normalColor = new Color(11, 27, 226);
        Color normalBg = new Color(224, 242, 248);

        JButton[] buttons = new JButton[]{
            view.getDashboardButton(),
            view.getProfileButton(),
            view.getAttendanceButton(),
            view.getCoursesButton(),
            view.getViewResultsButton(),
            view.getDownloadResultButton(),
            view.getLogoutButton()
        };
        String[] types = new String[]{"dashboard", "profile", "attendance", "courses", "result", "reports", "logout"};

        for (int i = 0; i < buttons.length; ++i) {
            JButton btn = buttons[i];
            String type = types[i];
            if (btn == activeBtn) {
                btn.setBackground(activeBg);
                btn.setForeground(activeColor);
                btn.setContentAreaFilled(true);
                btn.setOpaque(true);
                btn.setIcon(new DownloadResultFrame.VectorIcon(type, 28, whiteColor));
            } else {
                btn.setBackground(normalBg);
                btn.setForeground(normalColor);
                btn.setContentAreaFilled(true);
                btn.setOpaque(true);
                btn.setIcon(new DownloadResultFrame.VectorIcon(type, 28, activeColor));
            }
        }
    }

    private void setupMenuIcons(ViewResultFrame view) {
        try {
            view.getDashboardButton().setIcon(new ImageIcon(this.getClass().getResource("/images/home.png")));
            view.getProfileButton().setIcon(new ImageIcon(this.getClass().getResource("/images/user.png")));
            view.getAttendanceButton().setIcon(new ImageIcon(this.getClass().getResource("/images/attendance.png")));
            view.getCoursesButton().setIcon(new ImageIcon(this.getClass().getResource("/images/course.png")));
            view.getDownloadResultButton().setIcon(new ImageIcon(this.getClass().getResource("/images/result.png")));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void setupStatCards(ViewResultFrame view) {
        java.awt.Font numFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20);
        java.awt.Font lblFont = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11);
        java.awt.Color numColor = new java.awt.Color(28, 39, 50);

        view.getGpaValLabel().setFont(numFont); 
        view.getGpaValLabel().setForeground(numColor);
        view.getGpaValLabel().setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        view.getPercentageValLabel().setFont(numFont); 
        view.getPercentageValLabel().setForeground(numColor);
        view.getPercentageValLabel().setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        view.getOverallGradeValLabel().setFont(numFont); 
        view.getOverallGradeValLabel().setForeground(numColor);
        view.getOverallGradeValLabel().setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Add to card panels
        view.getCard1Panel().setBackground(new java.awt.Color(248, 249, 250));
        view.getCard1Panel().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        view.getCard1Panel().setLayout(new java.awt.BorderLayout());
        javax.swing.JLabel l1 = new javax.swing.JLabel("Overall GPA", javax.swing.SwingConstants.CENTER);
        l1.setFont(lblFont); 
        view.getCard1Panel().add(view.getGpaValLabel(), java.awt.BorderLayout.CENTER); 
        view.getCard1Panel().add(l1, java.awt.BorderLayout.SOUTH);

        view.getCard2Panel().setBackground(new java.awt.Color(248, 249, 250));
        view.getCard2Panel().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        view.getCard2Panel().setLayout(new java.awt.BorderLayout());
        javax.swing.JLabel l2 = new javax.swing.JLabel("Percentage", javax.swing.SwingConstants.CENTER);
        l2.setFont(lblFont); 
        view.getCard2Panel().add(view.getPercentageValLabel(), java.awt.BorderLayout.CENTER); 
        view.getCard2Panel().add(l2, java.awt.BorderLayout.SOUTH);

        view.getCard3Panel().setBackground(new java.awt.Color(248, 249, 250));
        view.getCard3Panel().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        view.getCard3Panel().setLayout(new java.awt.BorderLayout());
        javax.swing.JLabel l3 = new javax.swing.JLabel("Overall Grade", javax.swing.SwingConstants.CENTER);
        l3.setFont(lblFont); 
        view.getCard3Panel().add(view.getOverallGradeValLabel(), java.awt.BorderLayout.CENTER); 
        view.getCard3Panel().add(l3, java.awt.BorderLayout.SOUTH);

        // Position card panels on the main panel
        view.getCard1Panel().setBounds(270, 70, 190, 110);
        view.getCard2Panel().setBounds(470, 70, 190, 110);
        view.getCard3Panel().setBounds(670, 70, 190, 110);
    }

    private void addSidebarHoverEffects(final JButton btn, final JButton activeBtn) {
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn != activeBtn) {
                    btn.setContentAreaFilled(true);
                    btn.setBackground(new Color(40, 55, 70));
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != activeBtn && !btn.isFocusOwner()) {
                    btn.setContentAreaFilled(false);
                }
            }
        });
        btn.addFocusListener(new java.awt.event.FocusListener(){
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (btn != activeBtn) {
                    btn.setContentAreaFilled(true);
                    btn.setBackground(new Color(50, 70, 90));
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (btn != activeBtn) {
                    btn.setContentAreaFilled(false);
                }
            }
        });
    }

    /** Resolves the student_id for the currently logged-in student. */
    private String resolveStudentId() {
        try {
            model.UserData user = UserSession.getCurrentUser();
            if (user == null) return "";
            dao.StudentDAO studentDAO = new dao.StudentDAO();
            model.StudentData s = studentDAO.searchStudentByEmail(user.getUserName());
            if (s != null) return s.getStudentId();
            return user.getUserName();
        } catch (Exception e) {
            return "";
        }
    }
}
