import view.Logout;
import controller.LogoutController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LogoutVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  LOGOUT WORKFLOW & MVC COMPLIANCE VERIFICATION");
        System.out.println("==================================================");

        boolean passAll = true;

        // 1. Verify MVC compliance of Logout.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, hover effects, cursors
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
        System.out.println("Test 1: MVC Compliance Check on Logout.java");
        try {
            Class<?> clazz = Class.forName("view.Logout");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in Logout.java:");
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
            System.out.println("  Checking methods in Logout.java:");
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
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("addHoverEffects")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            if (declaredClasses.length > 0) {
                System.out.println("  [FAIL] Logout.java contains inner classes:");
                for (Class<?> inner : declaredClasses) {
                    System.out.println("    Inner Class: " + inner.getName());
                }
                return false;
            }

            System.out.println("  [PASS] Logout.java has 0 listeners and is purely passive.");
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
            Logout view = new Logout();
            LogoutController controller = new LogoutController(view);

            // 1. Cursor check
            System.out.print("  Checking hand cursors on buttons... ");
            if (view.getLoginAgainButton().getCursor().getType() != Cursor.HAND_CURSOR) {
                System.out.println("FAIL (Login Again button has default cursor)");
                return false;
            }
            if (view.getExitButton().getCursor().getType() != Cursor.HAND_CURSOR) {
                System.out.println("FAIL (Exit button has default cursor)");
                return false;
            }
            System.out.println("PASS");

            // 2. Login Again button hover effects
            System.out.print("  Checking Login Again button hover and exit color changes... ");
            MouseEvent enterLoginAgain = new MouseEvent(view.getLoginAgainButton(), MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : view.getLoginAgainButton().getMouseListeners()) {
                ml.mouseEntered(enterLoginAgain);
            }
            if (!new Color(9, 21, 180).equals(view.getLoginAgainButton().getBackground())) {
                System.out.println("FAIL (Login Again hover color mismatch: " + view.getLoginAgainButton().getBackground() + ")");
                return false;
            }

            MouseEvent exitLoginAgain = new MouseEvent(view.getLoginAgainButton(), MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : view.getLoginAgainButton().getMouseListeners()) {
                ml.mouseExited(exitLoginAgain);
            }
            if (!new Color(11, 27, 226).equals(view.getLoginAgainButton().getBackground())) {
                System.out.println("FAIL (Login Again exit color mismatch: " + view.getLoginAgainButton().getBackground() + ")");
                return false;
            }
            System.out.println("PASS");

            // 3. Exit button hover effects
            System.out.print("  Checking Exit button hover and exit color changes... ");
            MouseEvent enterExit = new MouseEvent(view.getExitButton(), MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : view.getExitButton().getMouseListeners()) {
                ml.mouseEntered(enterExit);
            }
            if (!new Color(245, 245, 245).equals(view.getExitButton().getBackground())) {
                System.out.println("FAIL (Exit hover color mismatch: " + view.getExitButton().getBackground() + ")");
                return false;
            }

            MouseEvent exitExit = new MouseEvent(view.getExitButton(), MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : view.getExitButton().getMouseListeners()) {
                ml.mouseExited(exitExit);
            }
            if (!new Color(255, 255, 255).equals(view.getExitButton().getBackground())) {
                System.out.println("FAIL (Exit exit color mismatch: " + view.getExitButton().getBackground() + ")");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] UI hover styles and cursors verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
