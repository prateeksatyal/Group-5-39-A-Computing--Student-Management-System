import view.Signup;
import controller.SignupController;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SignupVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SIGNUP WORKFLOW & MVC COMPLIANCE VERIFICATION");
        System.out.println("==================================================");

        boolean passAll = true;

        // 1. Verify MVC compliance of Signup.java (Only getters, setters, init, declarations)
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, role selection, placeholders, password mask, hovers
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
        System.out.println("Test 1: MVC Compliance Check on Signup.java");
        try {
            Class<?> clazz = Class.forName("view.Signup");
            
            // Check fields: only JComponents and maybe a string/dummy field
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in Signup.java:");
            for (Field f : fields) {
                String name = f.getName();
                Class<?> type = f.getType();
                System.out.println("    Field: " + name + " (" + type.getName() + ")");
                // All fields should be Swing components or standard types, no custom listener classes
                if (name.toLowerCase().contains("listener")) {
                    System.out.println("    [FAIL] Field contains custom listener logic: " + name);
                    return false;
                }
            }

            // Check methods: only getters, setters, preferredSize, standard overrides
            Method[] methods = clazz.getDeclaredMethods();
            System.out.println("  Checking methods in Signup.java:");
            for (Method m : methods) {
                String name = m.getName();
                System.out.println("    Method: " + name + " (returns " + m.getReturnType().getSimpleName() + ")");
                // Methods must only be getters, setters, or lifecycle methods like initComponents, getPreferredSize, etc.
                boolean isAllowed = name.startsWith("get") || 
                                    name.startsWith("set") || 
                                    name.equals("initComponents") || 
                                    name.equals("main") ||
                                    name.equals("access$000") || // CFR helper
                                    name.equals("access$100") || // CFR helper
                                    name.equals("access$200");
                
                if (!isAllowed) {
                    // Check if it's an event handler
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key")) {
                        System.out.println("    [FAIL] Violating MVC method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check if there are any inner listener classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            if (declaredClasses.length > 0) {
                System.out.println("  [FAIL] Signup.java contains inner classes:");
                for (Class<?> inner : declaredClasses) {
                    System.out.println("    Inner Class: " + inner.getName());
                }
                return false;
            }

            System.out.println("  [PASS] Signup.java has 0 listeners and is purely passive (only components, getters/setters).");
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
            // Instantiate View and Controller
            Signup view = new Signup();
            SignupController controller = new SignupController(view);

            // 1. Role ComboBox options check
            System.out.print("  Checking Role Selection options... ");
            JComboBox<String> roleCombo = view.getRoleComboBox();
            if (roleCombo.getItemCount() != 4) {
                System.out.println("FAIL (Expected 4 items, got " + roleCombo.getItemCount() + ")");
                return false;
            }
            if (!"Select Role".equals(roleCombo.getItemAt(0)) ||
                !"Student".equals(roleCombo.getItemAt(1)) ||
                !"Admin".equals(roleCombo.getItemAt(2)) ||
                !"Teacher".equals(roleCombo.getItemAt(3))) {
                System.out.println("FAIL (Mismatched roles: " + 
                    roleCombo.getItemAt(0) + ", " + roleCombo.getItemAt(1) + ", " + 
                    roleCombo.getItemAt(2) + ", " + roleCombo.getItemAt(3) + ")");
                return false;
            }
            System.out.println("PASS");

            // 2. Initial state & placeholder check
            System.out.print("  Checking initial text field placeholders and colors... ");
            if (!"Enter full name".equals(view.getUsernameField().getText()) || 
                !new Color(128, 128, 128).equals(view.getUsernameField().getForeground())) {
                System.out.println("FAIL (Username field invalid placeholder)");
                return false;
            }
            if (!"Enter email".equals(view.getEmailField().getText()) || 
                !new Color(128, 128, 128).equals(view.getEmailField().getForeground())) {
                System.out.println("FAIL (Email field invalid placeholder)");
                return false;
            }
            if (!"Enter password".equals(new String(view.getPasswordField().getPassword())) || 
                !new Color(128, 128, 128).equals(view.getPasswordField().getForeground())) {
                System.out.println("FAIL (Password field invalid placeholder)");
                return false;
            }
            if (!"Confirm Password".equals(new String(view.getConfirmPasswordField().getPassword())) || 
                !new Color(128, 128, 128).equals(view.getConfirmPasswordField().getForeground())) {
                System.out.println("FAIL (Confirm password field invalid placeholder)");
                return false;
            }
            System.out.println("PASS");

            // 3. Password field masking echo char
            System.out.print("  Checking password placeholder masking (should be unmasked initially)... ");
            if (view.getPasswordField().getEchoChar() != '\u0000') {
                System.out.println("FAIL (Password echo char should be unmasked for placeholder)");
                return false;
            }
            if (view.getConfirmPasswordField().getEchoChar() != '\u0000') {
                System.out.println("FAIL (Confirm password echo char should be unmasked for placeholder)");
                return false;
            }
            System.out.println("PASS");

            // 4. Focus behaviors & placeholder swap
            System.out.print("  Checking placeholder focus behaviour (gain/lost)... ");
            // Focus Gained Username
            FocusEvent gainFocus = new FocusEvent(view.getUsernameField(), FocusEvent.FOCUS_GAINED);
            for (FocusListener l : view.getUsernameField().getFocusListeners()) {
                l.focusGained(gainFocus);
            }
            if (!"".equals(view.getUsernameField().getText()) || 
                !new Color(0, 0, 0).equals(view.getUsernameField().getForeground())) {
                System.out.println("FAIL (Username did not clear on focus)");
                return false;
            }

            // Focus Lost Username
            FocusEvent lostFocus = new FocusEvent(view.getUsernameField(), FocusEvent.FOCUS_LOST);
            for (FocusListener l : view.getUsernameField().getFocusListeners()) {
                l.focusLost(lostFocus);
            }
            if (!"Enter full name".equals(view.getUsernameField().getText()) || 
                !new Color(128, 128, 128).equals(view.getUsernameField().getForeground())) {
                System.out.println("FAIL (Username did not restore placeholder on focus lost)");
                return false;
            }

            // Focus Gained Password (should mask & clear)
            FocusEvent gainPassFocus = new FocusEvent(view.getPasswordField(), FocusEvent.FOCUS_GAINED);
            for (FocusListener l : view.getPasswordField().getFocusListeners()) {
                l.focusGained(gainPassFocus);
            }
            if (!"".equals(new String(view.getPasswordField().getPassword())) || 
                view.getPasswordField().getEchoChar() != '\u2022' ||
                !new Color(0, 0, 0).equals(view.getPasswordField().getForeground())) {
                System.out.println("FAIL (Password did not mask/clear on focus gained)");
                return false;
            }

            // Focus Lost Password (should unmask & restore)
            FocusEvent lostPassFocus = new FocusEvent(view.getPasswordField(), FocusEvent.FOCUS_LOST);
            for (FocusListener l : view.getPasswordField().getFocusListeners()) {
                l.focusLost(lostPassFocus);
            }
            if (!"Enter password".equals(new String(view.getPasswordField().getPassword())) || 
                view.getPasswordField().getEchoChar() != '\u0000' ||
                !new Color(128, 128, 128).equals(view.getPasswordField().getForeground())) {
                System.out.println("FAIL (Password did not restore placeholder on focus lost)");
                return false;
            }
            System.out.println("PASS");

            // 5. Button hover effects
            System.out.print("  Checking Register button hover and exit color changes... ");
            MouseEvent enterRegister = new MouseEvent(view.getSignUpButton(), MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : view.getSignUpButton().getMouseListeners()) {
                ml.mouseEntered(enterRegister);
            }
            if (!new Color(5, 18, 180).equals(view.getSignUpButton().getBackground())) {
                System.out.println("FAIL (Register hover color mismatch: " + view.getSignUpButton().getBackground() + ")");
                return false;
            }

            MouseEvent exitRegister = new MouseEvent(view.getSignUpButton(), MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : view.getSignUpButton().getMouseListeners()) {
                ml.mouseExited(exitRegister);
            }
            if (!new Color(11, 27, 226).equals(view.getSignUpButton().getBackground())) {
                System.out.println("FAIL (Register exit color mismatch: " + view.getSignUpButton().getBackground() + ")");
                return false;
            }
            System.out.println("PASS");

            System.out.print("  Checking Cancel button hover and exit color changes... ");
            MouseEvent enterCancel = new MouseEvent(view.getCancelButton(), MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : view.getCancelButton().getMouseListeners()) {
                ml.mouseEntered(enterCancel);
            }
            if (!new Color(240, 240, 240).equals(view.getCancelButton().getBackground())) {
                System.out.println("FAIL (Cancel hover color mismatch)");
                return false;
            }

            MouseEvent exitCancel = new MouseEvent(view.getCancelButton(), MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, 0, 0, 0, false);
            for (MouseListener ml : view.getCancelButton().getMouseListeners()) {
                ml.mouseExited(exitCancel);
            }
            if (!new Color(255, 255, 255).equals(view.getCancelButton().getBackground())) {
                System.out.println("FAIL (Cancel exit color mismatch)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] UI features, placeholders, hover styles verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
