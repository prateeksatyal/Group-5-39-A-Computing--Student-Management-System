import view.*;
import controller.*;
import java.lang.reflect.Field;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.*;

public class ButtonForensicsScanner {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("          BUTTON FORENSICS SCANNER");
        System.out.println("==================================================");

        // Force headless to false so JFrames can instantiate under AWT normally
        System.setProperty("java.awt.headless", "false");

        // We will mock a user session so controllers initialize without blocking
        UserSession.setCurrentUser(new model.UserData("admin", "123", "admin@sms.com", "Admin"));

        auditFrame("AdminDashboard", new AdminDashboard(), frame -> {
            new AdminDashboardController((AdminDashboard) frame);
        });

        auditFrame("StudentManagementFrame", new StudentManagementFrame(), frame -> {
            new StudentController((StudentManagementFrame) frame);
        });

        auditFrame("CourseManagementFrame", new CourseManagementFrame(), frame -> {
            new CourseController((CourseManagementFrame) frame);
        });

        auditFrame("AttendanceSummaryFrame", new AttendanceSummaryFrame(), frame -> {
            new AttendanceController((AttendanceSummaryFrame) frame);
        });

        auditFrame("AcademicPerformanceFrame", new AcademicPerformanceFrame(), frame -> {
            new MarksController((AcademicPerformanceFrame) frame, "Teacher");
        });

        auditFrame("GradeComputationFrame", new GradeComputationFrame(), frame -> {
            new MarksController((GradeComputationFrame) frame);
        });

        auditFrame("GenerateResultFrame", new GenerateResultFrame(), frame -> {
            new ResultController((GenerateResultFrame) frame);
        });

        auditFrame("DownloadResultFrame", new DownloadResultFrame(), frame -> {
            new ResultController((DownloadResultFrame) frame);
        });

        auditFrame("ViewResultFrame", new ViewResultFrame(), frame -> {
            new ResultController((ViewResultFrame) frame);
        });

        auditFrame("Login", new Login(), frame -> {
            new LoginController((Login) frame);
        });

        auditFrame("Signup", new Signup(), frame -> {
            new SignupController((Signup) frame);
        });

        auditFrame("ForgotPassword", new ForgotPassword(), frame -> {
            new ForgotPasswordController((ForgotPassword) frame);
        });

        auditFrame("Logout", new Logout(), frame -> {
            // no dedicated controller, wired statically
        });

        System.out.println("==================================================");
        System.out.println("          BUTTON FORENSICS COMPLETE");
        System.out.println("==================================================");
        System.exit(0);
    }

    private static void auditFrame(String name, Container frame, ControllerInitializer initializer) {
        System.out.println("\nAuditing: " + name);
        try {
            if (initializer != null) {
                initializer.initialize(frame);
            }
            
            List<JButton> buttons = findAllButtons(frame);
            System.out.println("  Found " + buttons.size() + " buttons.");
            for (JButton btn : buttons) {
                ActionListener[] listeners = btn.getActionListeners();
                System.out.print("    Button [" + btn.getName() + " / Text: \"" + btn.getText() + "\"] has " + listeners.length + " listeners.");
                if (listeners.length == 0) {
                    System.out.println(" -> [WARNING] DEAD BUTTON (No action listeners bound!)");
                } else if (listeners.length > 1) {
                    System.out.println(" -> [INFO] Multiple action listeners bound (" + listeners.length + ")");
                } else {
                    System.out.println(" -> OK");
                }
            }
        } catch (Exception e) {
            System.out.println("  Error auditing " + name + ": " + e.getMessage());
        }
    }

    private static List<JButton> findAllButtons(Container container) {
        List<JButton> list = new ArrayList<>();
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                list.add((JButton) comp);
            } else if (comp instanceof Container) {
                list.addAll(findAllButtons((Container) comp));
            }
        }
        return list;
    }

    interface ControllerInitializer {
        void initialize(Container frame) throws Exception;
    }
}
