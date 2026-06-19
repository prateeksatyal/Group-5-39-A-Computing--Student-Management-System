import view.*;
import controller.*;
import dao.*;
import model.*;
import database.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class RuntimeSecurityVerification {

    private static final UserDao userDao = new UserDao();
    private static StringBuilder reportBuilder = new StringBuilder();

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   RUNTIME REAL SECURITY VERIFICATION SUITE");
        System.out.println("==================================================");

        // Ensure headless is FALSE
        System.setProperty("java.awt.headless", "false");

        // 1. Login as Student
        UserData student = userDao.loginUser("ram@sms.com", "123", "Student");
        if (student == null) {
            System.err.println("Failed to log in student ram@sms.com");
            System.exit(1);
        }
        UserSession.setCurrentUser(student);
        System.out.println("Logged in as student: " + student.getEmail());

        reportBuilder.append("# Runtime Security Verification Report\n\n");
        reportBuilder.append("| Screen | Login Role | Controller Executed | Access Denied Triggered | Frame Disposed | Data Visible | CRUD Possible | Result |\n");
        reportBuilder.append("| --- | --- | --- | --- | --- | --- | --- | --- |\n");

        verifyStudentManagement();
        verifyCourseManagement();
        verifyGradeComputation();
        verifyGenerateResult();

        saveVerificationReport();
        System.out.println("==================================================");
        System.out.println("   VERIFICATION SUITE COMPLETE");
        System.out.println("==================================================");
        System.exit(0);
    }

    private static void verifyStudentManagement() {
        System.out.println("\n[Testing StudentManagementFrame]");
        final AtomicBoolean dialogTriggered = new AtomicBoolean(false);
        final AtomicBoolean isDisposed = new AtomicBoolean(false);
        final AtomicBoolean isDataVisible = new AtomicBoolean(false);
        final AtomicBoolean isCrudPossible = new AtomicBoolean(false);

        // Start Bypasser Thread
        Thread bypasser = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 3000) {
                try {
                    Thread.sleep(50);
                    SwingUtilities.invokeLater(() -> {
                        for (Window w : Window.getWindows()) {
                            if (w instanceof Dialog && w.isVisible()) {
                                dialogTriggered.set(true);
                                w.dispose();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        bypasser.setDaemon(true);
        bypasser.start();

        try {
            // Instantiate Frame and Controller on EDT
            SwingUtilities.invokeAndWait(() -> {
                try {
                    StudentManagementFrame frame = new StudentManagementFrame();
                    new StudentController(frame);

                    // Check visibility and disposition
                    isDisposed.set(!frame.isVisible() || !frame.isDisplayable());
                    
                    // Check if table loaded data
                    if (!isDisposed.get() && frame.getStudentsTable() != null && frame.getStudentsTable().getRowCount() > 0) {
                        isDataVisible.set(true);
                    }
                    
                    // Check if add button is enabled/functional
                    if (!isDisposed.get() && frame.getAddButton() != null && frame.getAddButton().isEnabled()) {
                        isCrudPossible.set(true);
                    }
                } catch (Exception e) {
                    System.out.println("Exception during StudentManagementFrame initialization: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Wait for bypasser to finish
        try { bypasser.join(4000); } catch (Exception e) {}

        boolean pass = dialogTriggered.get() && isDisposed.get() && !isDataVisible.get() && !isCrudPossible.get();
        System.out.println("  Access Denied Triggered: " + (dialogTriggered.get() ? "YES" : "NO"));
        System.out.println("  Frame Disposed: " + (isDisposed.get() ? "YES" : "NO"));
        System.out.println("  Data Visible: " + (isDataVisible.get() ? "YES" : "NO"));
        System.out.println("  CRUD Possible: " + (isCrudPossible.get() ? "YES" : "NO"));
        System.out.println("  Result: " + (pass ? "PASS" : "FAIL"));

        reportBuilder.append(String.format("| StudentManagementFrame | Student | StudentController | %s | %s | %s | %s | %s |\n",
            dialogTriggered.get() ? "YES" : "NO",
            isDisposed.get() ? "YES" : "NO",
            isDataVisible.get() ? "YES" : "NO",
            isCrudPossible.get() ? "YES" : "NO",
            pass ? "PASS" : "FAIL"
        ));
    }

    private static void verifyCourseManagement() {
        System.out.println("\n[Testing CourseManagementFrame]");
        final AtomicBoolean dialogTriggered = new AtomicBoolean(false);
        final AtomicBoolean isDisposed = new AtomicBoolean(false);
        final AtomicBoolean isDataVisible = new AtomicBoolean(false);
        final AtomicBoolean isCrudPossible = new AtomicBoolean(false);

        Thread bypasser = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 3000) {
                try {
                    Thread.sleep(50);
                    SwingUtilities.invokeLater(() -> {
                        for (Window w : Window.getWindows()) {
                            if (w instanceof Dialog && w.isVisible()) {
                                dialogTriggered.set(true);
                                w.dispose();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        bypasser.setDaemon(true);
        bypasser.start();

        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    CourseManagementFrame frame = new CourseManagementFrame();
                    new CourseController(frame);

                    isDisposed.set(!frame.isVisible() || !frame.isDisplayable());
                    
                    if (!isDisposed.get() && frame.getCoursesTable() != null && frame.getCoursesTable().getRowCount() > 0) {
                        isDataVisible.set(true);
                    }
                    
                    if (!isDisposed.get() && frame.getAddButton() != null && frame.getAddButton().isEnabled()) {
                        isCrudPossible.set(true);
                    }
                } catch (Exception e) {
                    System.out.println("Exception during CourseManagementFrame initialization: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try { bypasser.join(4000); } catch (Exception e) {}

        boolean pass = dialogTriggered.get() && isDisposed.get() && !isDataVisible.get() && !isCrudPossible.get();
        System.out.println("  Access Denied Triggered: " + (dialogTriggered.get() ? "YES" : "NO"));
        System.out.println("  Frame Disposed: " + (isDisposed.get() ? "YES" : "NO"));
        System.out.println("  Data Visible: " + (isDataVisible.get() ? "YES" : "NO"));
        System.out.println("  CRUD Possible: " + (isCrudPossible.get() ? "YES" : "NO"));
        System.out.println("  Result: " + (pass ? "PASS" : "FAIL"));

        reportBuilder.append(String.format("| CourseManagementFrame | Student | CourseController | %s | %s | %s | %s | %s |\n",
            dialogTriggered.get() ? "YES" : "NO",
            isDisposed.get() ? "YES" : "NO",
            isDataVisible.get() ? "YES" : "NO",
            isCrudPossible.get() ? "YES" : "NO",
            pass ? "PASS" : "FAIL"
        ));
    }

    private static void verifyGradeComputation() {
        System.out.println("\n[Testing GradeComputationFrame]");
        final AtomicBoolean dialogTriggered = new AtomicBoolean(false);
        final AtomicBoolean isDisposed = new AtomicBoolean(false);
        final AtomicBoolean isDataVisible = new AtomicBoolean(false);
        final AtomicBoolean isCrudPossible = new AtomicBoolean(false);

        Thread bypasser = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 3000) {
                try {
                    Thread.sleep(50);
                    SwingUtilities.invokeLater(() -> {
                        for (Window w : Window.getWindows()) {
                            if (w instanceof Dialog && w.isVisible()) {
                                dialogTriggered.set(true);
                                w.dispose();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        bypasser.setDaemon(true);
        bypasser.start();

        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    GradeComputationFrame frame = new GradeComputationFrame();
                    new MarksController(frame);

                    isDisposed.set(!frame.isVisible() || !frame.isDisplayable());
                    
                    if (!isDisposed.get() && frame.getGradesTable() != null && frame.getGradesTable().getRowCount() > 0) {
                        isDataVisible.set(true);
                    }
                    
                    if (!isDisposed.get() && frame.getComputeButton() != null && frame.getComputeButton().isEnabled()) {
                        isCrudPossible.set(true);
                    }
                } catch (Exception e) {
                    System.out.println("Exception during GradeComputationFrame initialization: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try { bypasser.join(4000); } catch (Exception e) {}

        boolean pass = dialogTriggered.get() && isDisposed.get() && !isDataVisible.get() && !isCrudPossible.get();
        System.out.println("  Access Denied Triggered: " + (dialogTriggered.get() ? "YES" : "NO"));
        System.out.println("  Frame Disposed: " + (isDisposed.get() ? "YES" : "NO"));
        System.out.println("  Data Visible: " + (isDataVisible.get() ? "YES" : "NO"));
        System.out.println("  CRUD Possible: " + (isCrudPossible.get() ? "YES" : "NO"));
        System.out.println("  Result: " + (pass ? "PASS" : "FAIL"));

        reportBuilder.append(String.format("| GradeComputationFrame | Student | MarksController | %s | %s | %s | %s | %s |\n",
            dialogTriggered.get() ? "YES" : "NO",
            isDisposed.get() ? "YES" : "NO",
            isDataVisible.get() ? "YES" : "NO",
            isCrudPossible.get() ? "YES" : "NO",
            pass ? "PASS" : "FAIL"
        ));
    }

    private static void verifyGenerateResult() {
        System.out.println("\n[Testing GenerateResultFrame]");
        final AtomicBoolean dialogTriggered = new AtomicBoolean(false);
        final AtomicBoolean isDisposed = new AtomicBoolean(false);
        final AtomicBoolean isDataVisible = new AtomicBoolean(false);
        final AtomicBoolean isCrudPossible = new AtomicBoolean(false);

        Thread bypasser = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 3000) {
                try {
                    Thread.sleep(50);
                    SwingUtilities.invokeLater(() -> {
                        for (Window w : Window.getWindows()) {
                            if (w instanceof Dialog && w.isVisible()) {
                                dialogTriggered.set(true);
                                w.dispose();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        bypasser.setDaemon(true);
        bypasser.start();

        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    GenerateResultFrame frame = new GenerateResultFrame();
                    new ResultController(frame);

                    isDisposed.set(!frame.isVisible() || !frame.isDisplayable());
                    
                    if (!isDisposed.get() && frame.getPreviewTextArea() != null && !frame.getPreviewTextArea().getText().trim().isEmpty()) {
                        isDataVisible.set(true);
                    }
                    
                    if (!isDisposed.get() && frame.getGenerateButton() != null && frame.getGenerateButton().isEnabled()) {
                        isCrudPossible.set(true);
                    }
                } catch (Exception e) {
                    System.out.println("Exception during GenerateResultFrame initialization: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try { bypasser.join(4000); } catch (Exception e) {}

        boolean pass = dialogTriggered.get() && isDisposed.get() && !isDataVisible.get() && !isCrudPossible.get();
        System.out.println("  Access Denied Triggered: " + (dialogTriggered.get() ? "YES" : "NO"));
        System.out.println("  Frame Disposed: " + (isDisposed.get() ? "YES" : "NO"));
        System.out.println("  Data Visible: " + (isDataVisible.get() ? "YES" : "NO"));
        System.out.println("  CRUD Possible: " + (isCrudPossible.get() ? "YES" : "NO"));
        System.out.println("  Result: " + (pass ? "PASS" : "FAIL"));

        reportBuilder.append(String.format("| GenerateResultFrame | Student | ResultController | %s | %s | %s | %s | %s |\n",
            dialogTriggered.get() ? "YES" : "NO",
            isDisposed.get() ? "YES" : "NO",
            isDataVisible.get() ? "YES" : "NO",
            isCrudPossible.get() ? "YES" : "NO",
            pass ? "PASS" : "FAIL"
        ));
    }

    private static void saveVerificationReport() {
        try {
            java.io.File file = new java.io.File("C:\\Users\\prate\\.gemini\\antigravity\\brain\\04b4f061-388c-4c0e-8e7a-e8f194250bce\\runtime_security_verification.md");
            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.write(reportBuilder.toString());
            writer.close();
            System.out.println("Runtime verification report saved to: runtime_security_verification.md");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
