package controller;

import dao.ResultDAO;
import model.ResultData;
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
 *
 * No SQL here — all DB operations delegate to ResultDAO.
 */
public class ResultController {

    private final ResultDAO resultDAO = new ResultDAO();

    private GenerateResultFrame generateView;
    private ViewResultFrame     viewResultView;

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

    // ── GenerateResultFrame wiring ────────────────────────────────────────────

    private void wireGenerateFrame() {
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

        generateView.getGenerateButton().addActionListener(e -> generateResults());

        generateView.getBackButton().addActionListener(e -> {
            generateView.dispose();
            navigateToDashboard();
        });

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
        if (viewResultView.getTermComboBox().getItemCount() == 0) {
            viewResultView.getTermComboBox().setModel(
                new DefaultComboBoxModel<>(new String[]{"All Terms", "Term 1", "Term 2", "Term 3"}));
        }

        loadResultsIntoTable();

        viewResultView.getTermComboBox().addActionListener(e -> loadResultsIntoTable());

        viewResultView.getBackButton().addActionListener(e -> {
            viewResultView.dispose();
            navigateToDashboard();
        });

        viewResultView.getDashboardButton().addActionListener(e -> {
            viewResultView.dispose();
            navigateToDashboard();
        });
    }

    private void loadResultsIntoTable() {
        DefaultTableModel model = (DefaultTableModel) viewResultView.getGradesTable().getModel();
        model.setRowCount(0);

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

        if (UserSession.getCurrentUser() != null && ("Admin".equals(UserSession.getCurrentUser().getRole()) || "Teacher".equals(UserSession.getCurrentUser().getRole()))) {
            if ("All Terms".equals(selectedTerm)) {
                results = resultDAO.getAllResults();
            } else {
                results = resultDAO.getResultsByTerm(selectedTerm);
            }
        } else {
            String studentId = resolveStudentId();
            if ("All Terms".equals(selectedTerm)) {
                results = resultDAO.getResultsByStudentId(studentId);
            } else {
                results = resultDAO.getResultsByStudentAndTerm(studentId, selectedTerm);
            }
            if (!results.isEmpty()) {
                viewResultView.getStudentIdLabel().setText("Student ID: " + results.get(0).getStudentId());
                viewResultView.getStudentNameLabel().setText("Student: " + results.get(0).getStudentName());
            }
        }

        if (results.isEmpty()) {
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

    private void wireSidebarAdmin(JFrame source) {
        if (generateView != null) {
            generateView.getAcademicPerformanceButton().addActionListener(e -> {
                generateView.dispose();
                EventQueue.invokeLater(() -> {
                    view.AcademicPerformanceFrame af = new view.AcademicPerformanceFrame("Admin");
                    new MarksController(af, "Admin");
                    af.setVisible(true);
                });
            });
            /*
            generateView.getGradeComputationButton().addActionListener(e -> {
                generateView.dispose();
                EventQueue.invokeLater(() -> {
                    view.GradeComputationFrame gf = new view.GradeComputationFrame();
                    new MarksController(gf);
                    gf.setVisible(true);
                });
            });
            */
        }
    }

    private void navigateToDashboard() {
        EventQueue.invokeLater(() -> {
            sourceDispose();
        });
    }

    private void sourceDispose() {
        if (generateView != null) generateView.dispose();
        if (viewResultView != null) viewResultView.dispose();
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
