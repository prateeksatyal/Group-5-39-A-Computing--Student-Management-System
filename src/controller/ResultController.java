package controller;

import dao.ResultDAO;
import model.ResultData;
import view.DownloadResultFrame;
import view.GenerateResultFrame;
import view.ViewResultFrame;
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
        this.generateView.pack();
        this.generateView.setLocationRelativeTo(null);
        wireGenerateFrame();
    }

    // ── Constructor: ViewResultFrame ──────────────────────────────────────────

    public ResultController(ViewResultFrame view) {
        this.viewResultView = view;
        this.viewResultView.pack();
        this.viewResultView.setLocationRelativeTo(null);
        wireViewResultFrame();
    }

    // ── Constructor: DownloadResultFrame ──────────────────────────────────────

    public ResultController(DownloadResultFrame view) {
        this.downloadView = view;
        this.downloadView.pack();
        this.downloadView.setLocationRelativeTo(null);
        wireDownloadFrame();
    }

    // ── GenerateResultFrame wiring ────────────────────────────────────────────

    private void wireGenerateFrame() {
        // Populate combo-boxes with defaults if needed
        if (generateView.getTermComboBox().getItemCount() == 0) {
            generateView.getTermComboBox().setModel(
                new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));
        }
        if (generateView.getCourseComboBox().getItemCount() == 0) {
            generateView.getCourseComboBox().setModel(
                new DefaultComboBoxModel<>(new String[]{"BSc Computer Science", "BIT", "BBA"}));
        }
        if (generateView.getSectionComboBox().getItemCount() == 0) {
            generateView.getSectionComboBox().setModel(
                new DefaultComboBoxModel<>(new String[]{"Section A", "Section B", "Section C"}));
        }

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
        if (viewResultView.getTermComboBox().getItemCount() == 0) {
            viewResultView.getTermComboBox().setModel(
                new DefaultComboBoxModel<>(new String[]{"All Terms", "Term 1", "Term 2", "Term 3"}));
        }

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
        if (downloadView.getTermComboBox().getItemCount() == 0) {
            downloadView.getTermComboBox().setModel(
                new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));
        }

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