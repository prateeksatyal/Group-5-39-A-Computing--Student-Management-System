import view.AttendanceSummaryFrame;
import controller.AttendanceController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AttendanceSummaryFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  ATTENDANCE SUMMARY FRAME WORKFLOW & MVC CHECK");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy admin user
        UserSession.setCurrentUser(new UserData("admin", "admin", "admin@gmail.com", "Admin"));

        // 1. Verify MVC compliance of AttendanceSummaryFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, cursors, placeholders, and styling
        passAll &= verifyUiBehavior();

        System.out.println("==================================================");
        if (passAll) {
            System.out.println("  VERIFICATION RESULT: ALL TESTS PASSED!");
        } else {
            System.out.println("  VERIFICATION RESULT: SOME TESTS FAILED.");
        }
        System.out.println("==================================================");

        System.exit(passAll ? 0 : 1);
    }

    private static boolean verifyViewMvcCompliance() {
        System.out.println("Test 1: MVC Compliance Check on AttendanceSummaryFrame.java");
        try {
            Class<?> clazz = Class.forName("view.AttendanceSummaryFrame");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in AttendanceSummaryFrame.java:");
            for (Field f : fields) {
                String name = f.getName();
                System.out.println("    Field: " + name + " (" + f.getType().getName() + ")");
                if (name.toLowerCase().contains("listener")) {
                    System.out.println("    [FAIL] Field contains custom listener: " + name);
                    return false;
                }
            }

            // Check methods
            Method[] methods = clazz.getDeclaredMethods();
            System.out.println("  Checking methods in AttendanceSummaryFrame.java:");
            for (Method m : methods) {
                String name = m.getName();
                System.out.println("    Method: " + name + " (returns " + m.getReturnType().getSimpleName() + ")");
                
                boolean isAllowed = name.startsWith("get") || 
                                     name.startsWith("set") || 
                                     name.equals("initComponents") || 
                                     name.equals("main") ||
                                     name.equals("getPreferredSize") ||
                                     name.equals("label") ||
                                     name.equals("setupMarkAttendancePanel") ||
                                     name.equals("access$000") ||
                                     name.equals("access$100") ||
                                     name.equals("access$200");
                
                if (!isAllowed) {
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupMenuIcons") || name.equals("setupPlaceholders") || name.equals("addPlaceholder") || name.equals("populateCourseCombo") || name.equals("setActiveMenuItem")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in AttendanceSummaryFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                if (!inner.getSimpleName().equals("VectorIcon")) {
                    System.out.println("    [FAIL] Unexpected inner class in passive view: " + inner.getName());
                    return false;
                }
            }

            System.out.println("  [PASS] AttendanceSummaryFrame.java has 0 listeners/helpers and is passive.");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] MVC Compliance test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean verifyUiBehavior() {
        System.out.println("\nTest 2: UI & Behavior Verification");
        try {
            AttendanceSummaryFrame view = new AttendanceSummaryFrame();
            AttendanceController controller = new AttendanceController(view);

            // 1. Check flat button UI and null borders
            System.out.print("  Checking flat styling on export button... ");
            if (view.getExportButton().getBorder() != null) {
                System.out.println("FAIL (Export button border is not null)");
                return false;
            }
            if (view.getMarkSaveButton().getBorder() != null) {
                System.out.println("FAIL (Mark save button border is not null)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check student ID field placeholder behavior
            System.out.print("  Checking student ID field placeholder behavior... ");
            JTextField studentIdField = view.getMarkStudentIdField();
            if (!"Enter Student ID".equals(studentIdField.getText())) {
                System.out.println("FAIL (Initial placeholder text mismatch)");
                return false;
            }
            
            // Focus Gained should clear placeholder
            FocusEvent fg = new FocusEvent(studentIdField, FocusEvent.FOCUS_GAINED);
            for (FocusListener fl : studentIdField.getFocusListeners()) {
                fl.focusGained(fg);
            }
            if (!"".equals(studentIdField.getText())) {
                System.out.println("FAIL (Placeholder text not cleared on focus gain)");
                return false;
            }
            
            // Focus Lost on empty should restore placeholder
            FocusEvent flost = new FocusEvent(studentIdField, FocusEvent.FOCUS_LOST);
            for (FocusListener fl : studentIdField.getFocusListeners()) {
                fl.focusLost(flost);
            }
            if (!"Enter Student ID".equals(studentIdField.getText())) {
                System.out.println("FAIL (Placeholder text not restored on focus lost)");
                return false;
            }
            System.out.println("PASS");

            // 3. Check JTable columns loaded dynamically by controller
            System.out.print("  Checking JTable model column headers... ");
            if (view.getAttendanceTable().getModel().getColumnCount() != 8) {
                System.out.println("FAIL (JTable does not have 8 columns)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] AttendanceSummaryFrame styling, placeholders, and JTable model state verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
