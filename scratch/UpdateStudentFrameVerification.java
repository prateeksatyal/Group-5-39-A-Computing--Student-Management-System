import view.UpdateStudentFrame;
import view.StudentManagementFrame;
import controller.StudentController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UpdateStudentFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  UPDATE STUDENT FRAME WORKFLOW & MVC VERIFICATION");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy admin user so Controller won't throw NPE on user checks
        UserSession.setCurrentUser(new UserData("admin", "admin", "admin@gmail.com", "Admin"));

        // 1. Verify MVC compliance of UpdateStudentFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, placeholders, button styling
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
        System.out.println("Test 1: MVC Compliance Check on UpdateStudentFrame.java");
        try {
            Class<?> clazz = Class.forName("view.UpdateStudentFrame");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in UpdateStudentFrame.java:");
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
            System.out.println("  Checking methods in UpdateStudentFrame.java:");
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
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupPlaceholders") || name.equals("addPlaceholder")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in UpdateStudentFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                System.out.println("    [FAIL] Unexpected inner class: " + inner.getName());
                return false;
            }

            System.out.println("  [PASS] UpdateStudentFrame.java has 0 listeners/helpers and is passive.");
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
            StudentManagementFrame mgtView = new StudentManagementFrame();
            StudentController controller = new StudentController(mgtView);

            // Simulate clicking the Update Student button to initialize the updateView
            JButton updateBtn = mgtView.getUpdateButton();
            for (ActionListener al : updateBtn.getActionListeners()) {
                al.actionPerformed(new ActionEvent(updateBtn, ActionEvent.ACTION_PERFORMED, ""));
            }

            // Get the reference to the updateView created inside the controller using reflection
            Field updateViewField = StudentController.class.getDeclaredField("updateView");
            updateViewField.setAccessible(true);
            UpdateStudentFrame updateView = (UpdateStudentFrame) updateViewField.get(controller);

            if (updateView == null) {
                System.out.println("  [FAIL] UpdateStudentFrame was not initialized by controller!");
                return false;
            }

            // 1. Check Button styling (UI is basic and border is null)
            System.out.print("  Checking buttons styling applied by controller... ");
            JButton[] buttons = {
                updateView.getUpdateButton(),
                updateView.getSearchButton(),
                updateView.getDeleteButton(),
                updateView.getBackButton(),
                updateView.getResetButton()
            };
            
            for (JButton btn : buttons) {
                if (btn.getBorder() != null) {
                    System.out.println("FAIL (Button border not null for " + btn.getText() + ")");
                    return false;
                }
            }
            
            // Check Reset button colors explicitly
            JButton resetBtn = updateView.getResetButton();
            if (!new Color(108, 117, 125).equals(resetBtn.getBackground())) {
                System.out.println("FAIL (Reset button background color not Color(108,117,125))");
                return false;
            }
            if (!Color.WHITE.equals(resetBtn.getForeground())) {
                System.out.println("FAIL (Reset button foreground color not Color.WHITE)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check input placeholders focus behaviors
            System.out.print("  Checking student ID search field placeholder behaviors... ");
            JTextField searchIdField = updateView.getSearchIdField();
            if (!"Enter Student ID to Search...".equals(searchIdField.getText())) {
                System.out.println("FAIL (Placeholder text missing initially on search ID field)");
                return false;
            }
            
            // Focus gained should clear placeholder
            FocusEvent focusGained = new FocusEvent(searchIdField, FocusEvent.FOCUS_GAINED);
            for (FocusListener fl : searchIdField.getFocusListeners()) {
                fl.focusGained(focusGained);
            }
            if (!"".equals(searchIdField.getText())) {
                System.out.println("FAIL (Placeholder text was not cleared on focus gain)");
                return false;
            }

            // Focus lost with empty field should restore placeholder
            FocusEvent focusLost = new FocusEvent(searchIdField, FocusEvent.FOCUS_LOST);
            for (FocusListener fl : searchIdField.getFocusListeners()) {
                fl.focusLost(focusLost);
            }
            if (!"Enter Student ID to Search...".equals(searchIdField.getText())) {
                System.out.println("FAIL (Placeholder text was not restored on focus lost)");
                return false;
            }
            System.out.println("PASS");

            // 3. Reset Button behavior simulation
            System.out.print("  Checking reset button form clearing logic... ");
            updateView.getFullNameField().setText("John Doe");
            updateView.getFullNameField().setForeground(Color.BLACK);
            
            JButton resetB = updateView.getResetButton();
            for (ActionListener al : resetB.getActionListeners()) {
                al.actionPerformed(new ActionEvent(resetB, ActionEvent.ACTION_PERFORMED, ""));
            }
            if (!"Enter Full Name".equals(updateView.getFullNameField().getText())) {
                System.out.println("FAIL (Reset button did not clear/restore full name field placeholder)");
                return false;
            }
            System.out.println("PASS");

            // 4. Back Button navigation simulation
            System.out.print("  Checking back button navigation closes the window... ");
            JButton backB = updateView.getBackButton();
            for (ActionListener al : backB.getActionListeners()) {
                al.actionPerformed(new ActionEvent(backB, ActionEvent.ACTION_PERFORMED, ""));
            }
            // Check if updateView reference is cleared inside controller
            UpdateStudentFrame postBackView = (UpdateStudentFrame) updateViewField.get(controller);
            if (postBackView != null) {
                System.out.println("FAIL (updateView reference in controller was not set to null upon back navigation)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] UpdateStudentFrame buttons styling, placeholders, reset, and back navigation verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

// Verified Swing component states and focus listeners

// Verified Swing component states and focus listeners
