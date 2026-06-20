import view.ViewAssignedCourseFrame;
import controller.CourseController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ViewAssignedCourseFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  VIEW ASSIGNED COURSE FRAME MVC VERIFICATION");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy admin user
        UserSession.setCurrentUser(new UserData("admin", "admin", "admin@gmail.com", "Admin"));

        // 1. Verify MVC compliance of ViewAssignedCourseFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, placeholders, and focus highlights
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
        System.out.println("Test 1: MVC Compliance Check on ViewAssignedCourseFrame.java");
        try {
            Class<?> clazz = Class.forName("view.ViewAssignedCourseFrame");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in ViewAssignedCourseFrame.java:");
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
            System.out.println("  Checking methods in ViewAssignedCourseFrame.java:");
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
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupPlaceholders")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in ViewAssignedCourseFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                System.out.println("    [FAIL] Unexpected inner class in passive view: " + inner.getName());
                return false;
            }

            System.out.println("  [PASS] ViewAssignedCourseFrame.java has 0 listeners/helpers and is passive.");
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
            ViewAssignedCourseFrame view = new ViewAssignedCourseFrame();
            CourseController controller = new CourseController(view);

            // 1. Placeholder logic test on search field
            System.out.print("  Checking search field placeholder behavior... ");
            JTextField searchField = view.getSearchField();
            if (!"Type Course Code or Name to Search...".equals(searchField.getText())) {
                System.out.println("FAIL (Initial placeholder text mismatch)");
                return false;
            }
            
            // Focus Gained should clear placeholder
            FocusEvent fg = new FocusEvent(searchField, FocusEvent.FOCUS_GAINED);
            for (FocusListener fl : searchField.getFocusListeners()) {
                fl.focusGained(fg);
            }
            if (!"".equals(searchField.getText())) {
                System.out.println("FAIL (Placeholder text not cleared on focus gain)");
                return false;
            }
            
            // Focus Lost should restore placeholder if empty
            FocusEvent flost = new FocusEvent(searchField, FocusEvent.FOCUS_LOST);
            for (FocusListener fl : searchField.getFocusListeners()) {
                fl.focusLost(flost);
            }
            if (!"Type Course Code or Name to Search...".equals(searchField.getText())) {
                System.out.println("FAIL (Placeholder text not restored on empty focus lost)");
                return false;
            }
            System.out.println("PASS");

            // 2. Focus border highlight on search field
            System.out.print("  Checking border highlight on search field... ");
            // Focus Gained should change border to thickness 2 blue
            for (FocusListener fl : searchField.getFocusListeners()) {
                fl.focusGained(fg);
            }
            // Check that border is set (not null)
            if (searchField.getBorder() == null) {
                System.out.println("FAIL (Search field border is null on focus gained)");
                return false;
            }
            
            // Focus Lost should restore border
            for (FocusListener fl : searchField.getFocusListeners()) {
                fl.focusLost(flost);
            }
            if (searchField.getBorder() == null) {
                System.out.println("FAIL (Search field border is null on focus lost)");
                return false;
            }
            System.out.println("PASS");

            // 3. Confirm JTable is loaded and exposed
            System.out.print("  Checking JTable model initialization... ");
            if (view.getAssignedTable().getModel() == null) {
                System.out.println("FAIL (JTable model not set)");
                return false;
            }
            if (view.getAssignedTable().getModel().getColumnCount() != 6) {
                System.out.println("FAIL (JTable model does not have correct column count)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] ViewAssignedCourseFrame placeholder behaviors, JTable initialization, and focus highlights verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
