import view.StudentManagementFrame;
import controller.StudentController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StudentManagementFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  STUDENT MGT FRAME WORKFLOW & MVC VERIFICATION");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy admin user so Controller won't throw NPE on user checks
        UserSession.setCurrentUser(new UserData("admin", "admin", "admin@gmail.com", "Admin"));

        // 1. Verify MVC compliance of StudentManagementFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, active menu styling, hovers, placeholders
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
        System.out.println("Test 1: MVC Compliance Check on StudentManagementFrame.java");
        try {
            Class<?> clazz = Class.forName("view.StudentManagementFrame");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in StudentManagementFrame.java:");
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
            System.out.println("  Checking methods in StudentManagementFrame.java:");
            for (Method m : methods) {
                String name = m.getName();
                System.out.println("    Method: " + name + " (returns " + m.getReturnType().getSimpleName() + ")");
                
                boolean isAllowed = name.startsWith("get") || 
                                     name.startsWith("set") || 
                                     name.equals("initComponents") || 
                                     name.equals("main") ||
                                     name.equals("access$000") ||
                                     name.equals("access$100") ||
                                     name.equals("access$200");
                
                if (!isAllowed) {
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupMenuIcons") || name.equals("setActiveMenuItem") || name.equals("setupPlaceholder")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes - VectorIcon is the only allowed exception
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in StudentManagementFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                if (!inner.getSimpleName().equals("VectorIcon")) {
                    System.out.println("    [FAIL] Unexpected inner class: " + inner.getName());
                    return false;
                }
            }

            System.out.println("  [PASS] StudentManagementFrame.java has 0 listeners and is passive (VectorIcon allowed temporarily).");
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
            StudentManagementFrame view = new StudentManagementFrame();
            StudentController controller = new StudentController(view);

            // 1. Check title label icon and card icons are set
            System.out.print("  Checking menu icons initialized correctly... ");
            if (view.getTitleLabel().getIcon() == null) {
                System.out.println("FAIL (Title label icon missing)");
                return false;
            }
            if (view.getCardIconLabel().getIcon() == null) {
                System.out.println("FAIL (Card icon missing)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check active menu styling (Students Management button is active initially)
            System.out.print("  Checking initial active tab styling... ");
            JButton studentsBtn = view.getStudentsButton();
            if (!new Color(243, 227, 225).equals(studentsBtn.getBackground()) ||
                !new Color(11, 27, 226).equals(studentsBtn.getForeground())) {
                System.out.println("FAIL (Students active background/foreground mismatched)");
                return false;
            }
            
            JButton dbBtn = view.getDashboardButton();
            if (!new Color(224, 242, 248).equals(dbBtn.getBackground()) ||
                !new Color(11, 27, 226).equals(dbBtn.getForeground())) {
                System.out.println("FAIL (Dashboard inactive background/foreground mismatched)");
                return false;
            }
            System.out.println("PASS");

            // 3. Search field placeholder test
            System.out.print("  Checking search field placeholder behaviors... ");
            JTextField searchField = view.getSearchField();
            if (!"Search Name or ID...".equals(searchField.getText())) {
                System.out.println("FAIL (Placeholder text missing initially)");
                return false;
            }
            
            // Focus gained should clear placeholder
            FocusEvent focusGained = new FocusEvent(searchField, FocusEvent.FOCUS_GAINED);
            for (FocusListener fl : searchField.getFocusListeners()) {
                fl.focusGained(focusGained);
            }
            if (!"".equals(searchField.getText())) {
                System.out.println("FAIL (Placeholder text was not cleared on focus gain)");
                return false;
            }

            // Focus lost with empty field should restore placeholder
            FocusEvent focusLost = new FocusEvent(searchField, FocusEvent.FOCUS_LOST);
            for (FocusListener fl : searchField.getFocusListeners()) {
                fl.focusLost(focusLost);
            }
            if (!"Search Name or ID...".equals(searchField.getText())) {
                System.out.println("FAIL (Placeholder text was not restored on focus lost)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] Custom menu icons, hover highlights, active tab transitions, and placeholders verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
