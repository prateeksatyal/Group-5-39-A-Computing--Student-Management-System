package controller;

import dao.ResultDAO;
import model.ResultData;
import view.DownloadResultFrame;
import javax.swing.*;
import java.awt.EventQueue;
import java.util.List;

/**
 * MVC Controller for Sprint 3 result screens:
 *   - DownloadResultFrame   (export/print scorecard)
 *
 * No SQL here — all DB operations delegate to ResultDAO.
 */
public class ResultController {

    private final ResultDAO resultDAO = new ResultDAO();
    private DownloadResultFrame downloadView;

    // ── Constructor: DownloadResultFrame ──────────────────────────────────────

    public ResultController(DownloadResultFrame view) {
        this.downloadView = view;
        this.downloadView.pack();
        this.downloadView.setLocationRelativeTo(null);
        wireDownloadFrame();
    }

    // ── DownloadResultFrame wiring ────────────────────────────────────────────

    private void wireDownloadFrame() {
        if (downloadView.getTermComboBox().getItemCount() == 0) {
            downloadView.getTermComboBox().setModel(
                new DefaultComboBoxModel<>(new String[]{"Term 1", "Term 2", "Term 3"}));
        }

        downloadView.getTermComboBox().addActionListener(e -> generateScorecardPreview());

        generateScorecardPreview();

        downloadView.getDownloadButton().addActionListener(e -> downloadScorecard());

        downloadView.getPrintButton().addActionListener(e -> printScorecard());

        downloadView.getBackButton().addActionListener(e -> {
            downloadView.dispose();
            navigateToDashboard();
        });

        downloadView.getDashboardButton().addActionListener(e -> {
            downloadView.dispose();
            navigateToDashboard();
        });
    }

    private void generateScorecardPreview() {
        String term = (String) downloadView.getTermComboBox().getSelectedItem();
        String studentId = resolveStudentId();

        List<ResultData> results;
        if (UserSession.getCurrentUser() != null && ("Admin".equals(UserSession.getCurrentUser().getRole()) || "Teacher".equals(UserSession.getCurrentUser().getRole()))) {
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

    private void navigateToDashboard() {
        if (downloadView != null) downloadView.dispose();
    }

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
