import view.DownloadResultFrame;
import controller.ResultController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DownloadResultFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  DOWNLOAD RESULT FRAME WORKFLOW & MVC CHECK");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy admin user
        UserSession.setCurrentUser(new UserData("admin", "admin", "admin@gmail.com", "Admin"));

        // 1. Verify MVC compliance of DownloadResultFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, borders, and controller binding
        passAll &= verifyUiAndControllerWorkflows();

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
        System.out.println("Test 1: MVC Compliance Check on DownloadResultFrame.java");
        try {
            Class<?> clazz = Class.forName("view.DownloadResultFrame");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in DownloadResultFrame.java:");
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
            System.out.println("  Checking methods in DownloadResultFrame.java:");
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
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupMenuIcons") || name.equals("setActiveMenuItem") || name.equals("addInteractiveEffects")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in DownloadResultFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                if (!inner.getSimpleName().equals("VectorIcon")) {
                    System.out.println("    [FAIL] Unexpected inner class in passive view: " + inner.getName());
                    return false;
                }
            }

            System.out.println("  [PASS] DownloadResultFrame.java has 0 listeners/helpers and is passive.");
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
            DownloadResultFrame view = new DownloadResultFrame();
            ResultController controller = new ResultController(view);

            // 1. Verify Download button has a null border
            System.out.print("  Checking basic styling on Download button... ");
            if (view.getDownloadButton().getBorder() != null) {
                System.out.println("FAIL (Download button border is not null)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check combo boxes options
            System.out.print("  Checking term combobox options... ");
            if (view.getTermComboBox().getItemCount() != 3) {
                System.out.println("FAIL (Term combobox has " + view.getTermComboBox().getItemCount() + " items, expected 3)");
                return false;
            }
            System.out.println("PASS");

            // 3. Verify Scorecard Preview rendering trigger
            System.out.print("  Checking scorecard preview workflow... ");
            String initialText = view.getReportTextArea().getText();
            
            // Switch term to trigger reload
            view.getTermComboBox().setSelectedIndex(1);

            String updatedText = view.getReportTextArea().getText();
            if (updatedText == null || updatedText.trim().isEmpty() || updatedText.equals(initialText)) {
                System.out.println("FAIL (Scorecard text was not updated/rendered)");
                return false;
            }
            System.out.println("PASS");
            System.out.println("  Scorecard preview header:\n" + updatedText.split("\n")[0]);

            System.out.println("  [PASS] UI and controller bindings verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] UI behavior test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
