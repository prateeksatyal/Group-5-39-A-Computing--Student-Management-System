import view.AddStudentFrame;
import view.StudentManagementFrame;
import controller.StudentController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AddStudentFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  ADD STUDENT FRAME WORKFLOW & MVC VERIFICATION");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy admin user so Controller won't throw NPE on user checks
        UserSession.setCurrentUser(new UserData("admin", "admin", "admin@gmail.com", "Admin"));

        // 1. Verify MVC compliance of AddStudentFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, placeholders, save button styling
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
        System.out.println("Test 1: MVC Compliance Check on AddStudentFrame.java");
        try {
            Class<?> clazz = Class.forName("view.AddStudentFrame");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in AddStudentFrame.java:");
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
            System.out.println("  Checking methods in AddStudentFrame.java:");
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
            System.out.println("  Checking inner classes in AddStudentFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                System.out.println("    [FAIL] Unexpected inner class: " + inner.getName());
                return false;
            }

            System.out.println("  [PASS] AddStudentFrame.java has 0 listeners/helpers and is passive.");
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

            // Simulate clicking the Add Student button to initialize the addView
            JButton addBtn = mgtView.getAddButton();
            for (ActionListener al : addBtn.getActionListeners()) {
                al.actionPerformed(new ActionEvent(addBtn, ActionEvent.ACTION_PERFORMED, ""));
            }

            // Get the reference to the addView created inside the controller using reflection
            Field addViewField = StudentController.class.getDeclaredField("addView");
            addViewField.setAccessible(true);
            AddStudentFrame addView = (AddStudentFrame) addViewField.get(controller);

            if (addView == null) {
                System.out.println("  [FAIL] AddStudentFrame was not initialized by controller!");
                return false;
            }

            // 1. Check Save button styling (UI is basic and border is null)
            System.out.print("  Checking save button styling applied by controller... ");
            if (addView.getSaveButton().getBorder() != null) {
                System.out.println("FAIL (Save button border not null)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check input placeholders focus behaviors
            System.out.print("  Checking student ID field placeholder behaviors... ");
            JTextField studentIdField = addView.getStudentIdField();
            if (!"Enter Student ID".equals(studentIdField.getText())) {
                System.out.println("FAIL (Placeholder text missing initially)");
                return false;
            }
            
            // Focus gained should clear placeholder
            FocusEvent focusGained = new FocusEvent(studentIdField, FocusEvent.FOCUS_GAINED);
            for (FocusListener fl : studentIdField.getFocusListeners()) {
                fl.focusGained(focusGained);
            }
            if (!"".equals(studentIdField.getText())) {
                System.out.println("FAIL (Placeholder text was not cleared on focus gain)");
                return false;
            }

            // Focus lost with empty field should restore placeholder
            FocusEvent focusLost = new FocusEvent(studentIdField, FocusEvent.FOCUS_LOST);
            for (FocusListener fl : studentIdField.getFocusListeners()) {
                fl.focusLost(focusLost);
            }
            if (!"Enter Student ID".equals(studentIdField.getText())) {
                System.out.println("FAIL (Placeholder text was not restored on focus lost)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] AddStudentFrame save button styling and field placeholders verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
