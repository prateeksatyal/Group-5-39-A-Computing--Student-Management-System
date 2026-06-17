import view.AdminDashboard;
import controller.AdminDashboardController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AdminDashboardVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  ADMIN DASHBOARD WORKFLOW & MVC VERIFICATION");
        System.out.println("==================================================");

        boolean passAll = true;

        // 1. Verify MVC compliance of AdminDashboard.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, active menu styling, hovers, cursors
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
        System.out.println("Test 1: MVC Compliance Check on AdminDashboard.java");
        try {
            Class<?> clazz = Class.forName("view.AdminDashboard");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in AdminDashboard.java:");
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
            System.out.println("  Checking methods in AdminDashboard.java:");
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
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupMenuIcons") || name.equals("setActiveMenuItem")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes - VectorIcon is the only allowed exception
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in AdminDashboard.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                if (!inner.getSimpleName().equals("VectorIcon")) {
                    System.out.println("    [FAIL] Unexpected inner class: " + inner.getName());
                    return false;
                }
            }

            System.out.println("  [PASS] AdminDashboard.java has 0 listeners and is passive (VectorIcon allowed temporarily).");
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
            AdminDashboard view = new AdminDashboard();
            AdminDashboardController controller = new AdminDashboardController(view);

            // 1. Check title label icon and card icons are set
            System.out.print("  Checking menu icons initialized correctly... ");
            if (view.getTitleLabel().getIcon() == null) {
                System.out.println("FAIL (Title label icon missing)");
                return false;
            }
            if (view.getCard1IconLabel().getIcon() == null || view.getCard2IconLabel().getIcon() == null) {
                System.out.println("FAIL (Card icons missing)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check active menu styling (Dashboard button is active initially)
            System.out.print("  Checking initial active tab styling... ");
            JButton dbBtn = view.getDashboardButton();
            if (!new Color(243, 227, 225).equals(dbBtn.getBackground()) ||
                !new Color(11, 27, 226).equals(dbBtn.getForeground())) {
                System.out.println("FAIL (Dashboard active background/foreground mismatched)");
                return false;
            }
            
            JButton studentsBtn = view.getStudentsButton();
            if (!new Color(224, 242, 248).equals(studentsBtn.getBackground()) ||
                !new Color(11, 27, 226).equals(studentsBtn.getForeground())) {
                System.out.println("FAIL (Students inactive background/foreground mismatched)");
                return false;
            }
            System.out.println("PASS");

            // 3. Simulated button click to swap active menu tab
            System.out.print("  Checking active menu tab switching... ");
            // Simulate click on Students Management
            for (ActionListener al : studentsBtn.getActionListeners()) {
                al.actionPerformed(new ActionEvent(studentsBtn, ActionEvent.ACTION_PERFORMED, ""));
            }
            // Students button should now be active
            if (!new Color(243, 227, 225).equals(studentsBtn.getBackground())) {
                System.out.println("FAIL (Students button did not become active)");
                return false;
            }
            // Dashboard button should now be normal/inactive
            if (!new Color(224, 242, 248).equals(dbBtn.getBackground())) {
                System.out.println("FAIL (Dashboard button did not revert to inactive)");
                return false;
            }
            System.out.println("PASS");

            // 4. Hover effect checks on active vs inactive buttons
            System.out.print("  Checking hover background updates for active vs inactive items... ");
            // Inactive button (Dashboard) hover
            MouseEvent enterDb = new MouseEvent(dbBtn, MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : dbBtn.getMouseListeners()) {
                ml.mouseEntered(enterDb);
            }
            if (!new Color(200, 235, 245).equals(dbBtn.getBackground())) {
                System.out.println("FAIL (Inactive button hover color mismatch: " + dbBtn.getBackground() + ")");
                return false;
            }

            MouseEvent exitDb = new MouseEvent(dbBtn, MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : dbBtn.getMouseListeners()) {
                ml.mouseExited(exitDb);
            }
            if (!new Color(224, 242, 248).equals(dbBtn.getBackground())) {
                System.out.println("FAIL (Inactive button hover exit mismatch)");
                return false;
            }

            // Active button (Students) hover
            MouseEvent enterStudents = new MouseEvent(studentsBtn, MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : studentsBtn.getMouseListeners()) {
                ml.mouseEntered(enterStudents);
            }
            if (!new Color(233, 212, 209).equals(studentsBtn.getBackground())) {
                System.out.println("FAIL (Active button hover color mismatch: " + studentsBtn.getBackground() + ")");
                return false;
            }

            MouseEvent exitStudents = new MouseEvent(studentsBtn, MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : studentsBtn.getMouseListeners()) {
                ml.mouseExited(exitStudents);
            }
            if (!new Color(243, 227, 225).equals(studentsBtn.getBackground())) {
                System.out.println("FAIL (Active button hover exit mismatch)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] Custom menu icons, hover highlights, and active tab transitions verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
