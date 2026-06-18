import view.EnrollCourseFrame;
import controller.CourseController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnrollCourseFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  ENROLL COURSE FRAME WORKFLOW & MVC VERIFICATION");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy student/admin user if needed
        UserSession.setCurrentUser(new UserData("admin", "admin", "admin@gmail.com", "Admin"));

        // 1. Verify MVC compliance of EnrollCourseFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, cursors, hovers, placeholders
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
        System.out.println("Test 1: MVC Compliance Check on EnrollCourseFrame.java");
        try {
            Class<?> clazz = Class.forName("view.EnrollCourseFrame");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in EnrollCourseFrame.java:");
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
            System.out.println("  Checking methods in EnrollCourseFrame.java:");
            for (Method m : methods) {
                String name = m.getName();
                System.out.println("    Method: " + name + " (returns " + m.getReturnType().getSimpleName() + ")");
                
                boolean isAllowed = name.startsWith("get") || 
                                     name.startsWith("set") || 
                                     name.equals("initComponents") || 
                                     name.equals("main") ||
                                     name.equals("getPreferredSize") ||
                                     name.equals("access$000") ||
                                     name.equals("access$100") ||
                                     name.equals("access$200");
                
                if (!isAllowed) {
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupVisuals") || name.equals("setupPlaceholder")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in EnrollCourseFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                System.out.println("    [FAIL] Unexpected inner class in passive view: " + inner.getName());
                return false;
            }

            System.out.println("  [PASS] EnrollCourseFrame.java has 0 listeners/helpers and is passive.");
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
            EnrollCourseFrame view = new EnrollCourseFrame();
            CourseController controller = new CourseController(view);

            // 1. Check cursors on combo boxes are set to HAND_CURSOR (12)
            System.out.print("  Checking hand cursor set on combo boxes... ");
            if (view.getCourseComboBox().getCursor().getType() != Cursor.HAND_CURSOR) {
                System.out.println("FAIL (Course combo box cursor is not hand cursor)");
                return false;
            }
            if (view.getSemesterComboBox().getCursor().getType() != Cursor.HAND_CURSOR) {
                System.out.println("FAIL (Semester combo box cursor is not hand cursor)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check flat button UI and null border on jButtonEnroll
            System.out.print("  Checking flat styling on enroll button... ");
            if (view.getEnrollButton().getBorder() != null) {
                System.out.println("FAIL (Enroll button border is not null)");
                return false;
            }
            System.out.println("PASS");

            // 3. Placeholder logic test on jTextFieldStudentId
            System.out.print("  Checking student ID placeholder behavior... ");
            JTextField studentIdField = view.getStudentIdField();
            if (!"Enter Student ID".equals(studentIdField.getText())) {
                System.out.println("FAIL (Initial placeholder text mismatch)");
                return false;
            }
            
            // Focus Gained should clear the placeholder
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
                System.out.println("FAIL (Placeholder text not restored on empty focus lost)");
                return false;
            }
            System.out.println("PASS");

            // 4. Focus border highlights on buttons and inputs
            System.out.print("  Checking border highlights on buttons and inputs on focus events... ");
            JButton enrollBtn = view.getEnrollButton();
            FocusEvent enrollFg = new FocusEvent(enrollBtn, FocusEvent.FOCUS_GAINED);
            for (FocusListener fl : enrollBtn.getFocusListeners()) {
                fl.focusGained(enrollFg);
            }
            // Enroll button focus gained should show border
            if (enrollBtn.getBorder() == null) {
                System.out.println("FAIL (Enroll button did not show border on focus gained)");
                return false;
            }
            
            FocusEvent enrollFlost = new FocusEvent(enrollBtn, FocusEvent.FOCUS_LOST);
            for (FocusListener fl : enrollBtn.getFocusListeners()) {
                fl.focusLost(enrollFlost);
            }
            // Enroll button focus lost should restore null border
            if (enrollBtn.getBorder() != null) {
                System.out.println("FAIL (Enroll button did not clear border on focus lost)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] EnrollCourseFrame hand cursors, flat button styling, placeholders, and focus transitions verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
