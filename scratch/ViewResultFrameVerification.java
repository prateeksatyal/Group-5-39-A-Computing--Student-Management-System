import view.ViewResultFrame;
import controller.ResultController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ViewResultFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("    VIEW RESULT FRAME WORKFLOW & MVC CHECK");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy student user to resolve ID
        UserSession.setCurrentUser(new UserData("student", "student123", "student@gmail.com", "Student"));

        // 1. Verify MVC compliance of ViewResultFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, borders, and controller binding
        passAll &= verifyUiAndControllerWorkflows();

        System.out.println("==================================================");
        if (passAll) {
            System.out.println("    VERIFICATION RESULT: ALL TESTS PASSED!");
        } else {
            System.out.println("    VERIFICATION RESULT: SOME TESTS FAILED.");
        }
        System.out.println("==================================================");

        System.exit(passAll ? 0 : 1);
    }

    private static boolean verifyViewMvcCompliance() {
        System.out.println("Test 1: MVC Compliance Check on ViewResultFrame.java");
        try {
            Class<?> clazz = Class.forName("view.ViewResultFrame");
            
            // Check fields for any listener fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in ViewResultFrame.java:");
            for (Field f : fields) {
                String name = f.getName();
                System.out.println("    Field: " + name + " (" + f.getType().getName() + ")");
                if (name.toLowerCase().contains("listener")) {
                    System.out.println("    [FAIL] Field contains custom listener: " + name);
                    return false;
                }
            }

            // Check methods to ensure only getters/setters/initComponents exist
            Method[] methods = clazz.getDeclaredMethods();
            System.out.println("  Checking methods in ViewResultFrame.java:");
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
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupMenuIcons") || name.equals("setupStatCards") || name.equals("setupSidebarButton") || name.equals("addSidebarHoverEffects") || name.equals("setupScoreCard")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in ViewResultFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                System.out.println("    [FAIL] Unexpected inner class in passive view: " + inner.getName());
                return false;
            }

            System.out.println("  [PASS] ViewResultFrame.java has 0 listeners/helpers and is passive.");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] MVC Compliance test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean verifyUiAndControllerWorkflows() {
        System.out.println("\nTest 2: UI & Controller Bindings Verification");
        try {
            ViewResultFrame view = new ViewResultFrame();
            ResultController controller = new ResultController(view);

            // 1. Verify Stat cards layout was initialized and structured by controller
            System.out.print("  Checking stat cards layout type... ");
            if (!(view.getCard1Panel().getLayout() instanceof java.awt.BorderLayout)) {
                System.out.println("FAIL (Card1 layout is not BorderLayout)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check value labels are correct
            System.out.print("  Checking placeholder labels... ");
            if (!view.getGpaValLabel().getText().equals("0.00") || !view.getPercentageValLabel().getText().equals("0%")) {
                System.out.println("FAIL (Statistic labels have unexpected starting text)");
                return false;
            }
            System.out.println("PASS");

            // 3. Verify JTable default model and term combobox options
            System.out.print("  Checking term combobox options... ");
            if (view.getTermComboBox().getItemCount() != 4) { // "All Terms", "Term 1", "Term 2", "Term 3"
                System.out.println("FAIL (Term combobox has " + view.getTermComboBox().getItemCount() + " items, expected 4)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] UI and controller bindings verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
