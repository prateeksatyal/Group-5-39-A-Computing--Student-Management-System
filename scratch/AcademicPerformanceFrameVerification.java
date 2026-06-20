import view.AcademicPerformanceFrame;
import controller.MarksController;
import controller.UserSession;
import model.UserData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AcademicPerformanceFrameVerification {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  ACADEMIC PERFORMANCE FRAME WORKFLOW & MVC CHECK");
        System.out.println("==================================================");

        boolean passAll = true;

        // Initialize UserSession with a dummy admin user to bypass check
        UserSession.setCurrentUser(new UserData("admin", "admin", "admin@gmail.com", "Admin"));

        // 1. Verify MVC compliance of AcademicPerformanceFrame.java
        passAll &= verifyViewMvcCompliance();

        // 2. Verify UI appearance, borders, and controller binding
        passAll &= verifyUiAndControllerWorkflows();

        // 3. Verify Grade and GPA calculation algorithms inside Controller
        passAll &= verifyCalculationLogics();

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
        System.out.println("Test 1: MVC Compliance Check on AcademicPerformanceFrame.java");
        try {
            Class<?> clazz = Class.forName("view.AcademicPerformanceFrame");
            
            // Check fields
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("  Checking fields in AcademicPerformanceFrame.java:");
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
            System.out.println("  Checking methods in AcademicPerformanceFrame.java:");
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
                    if (name.contains("Action") || name.contains("Mouse") || name.contains("Focus") || name.contains("Window") || name.contains("Key") || name.equals("setupMenuIcons") || name.equals("setActiveMenuItem")) {
                        System.out.println("    [FAIL] Violating behavior/listener method found in view: " + name);
                        return false;
                    }
                }
            }

            // Check inner classes
            Class<?>[] declaredClasses = clazz.getDeclaredClasses();
            System.out.println("  Checking inner classes in AcademicPerformanceFrame.java:");
            for (Class<?> inner : declaredClasses) {
                System.out.println("    Inner Class: " + inner.getName());
                if (!inner.getSimpleName().equals("VectorIcon")) {
                    System.out.println("    [FAIL] Unexpected inner class in passive view: " + inner.getName());
                    return false;
                }
            }

            System.out.println("  [PASS] AcademicPerformanceFrame.java has 0 listeners/helpers and is passive.");
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
            AcademicPerformanceFrame view = new AcademicPerformanceFrame("Admin");
            MarksController controller = new MarksController(view, "Admin");

            // 1. Check flat button UI and null borders on Save Marks Button
            System.out.print("  Checking flat styling on Save Marks button... ");
            if (view.getSaveMarksButton().getBorder() != null) {
                System.out.println("FAIL (Save Marks button border is not null)");
                return false;
            }
            System.out.println("PASS");

            // 2. Check JTable columns loaded dynamically by controller
            System.out.print("  Checking JTable model column headers... ");
            DefaultTableModel model = (DefaultTableModel) view.getMarksTable().getModel();
            if (model.getColumnCount() != 5) {
                System.out.println("FAIL (JTable does not have 5 columns, got " + model.getColumnCount() + ")");
                return false;
            }
            System.out.println("PASS");

            // 3. Check JTable first row structure
            System.out.print("  Checking initial rows population... ");
            if (model.getRowCount() == 0) {
                System.out.println("FAIL (JTable did not load any initial rows)");
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

    private static boolean verifyCalculationLogics() {
        System.out.println("\nTest 3: Calculation Logic Verification inside MarksController");
        try {
            // Verify GPA calculations
            // 90% -> 4.0
            // 85% -> 3.7
            // 75% -> 3.3
            // 65% -> 3.0
            // 55% -> 2.0
            // 45% -> 1.0
            // 35% -> 0.0
            System.out.print("  Checking GPA calculation logic... ");
            if (MarksController.computeGpa(92.0) != 4.0 ||
                MarksController.computeGpa(85.0) != 3.7 ||
                MarksController.computeGpa(72.0) != 3.3 ||
                MarksController.computeGpa(60.0) != 3.0 ||
                MarksController.computeGpa(50.0) != 2.0 ||
                MarksController.computeGpa(40.0) != 1.0 ||
                MarksController.computeGpa(30.0) != 0.0) {
                System.out.println("FAIL (GPA calculation mismatch)");
                return false;
            }
            System.out.println("PASS");

            // Verify Letter Grade calculations
            // Out of 200 marks total.
            // 180+ (>=90%) -> A+
            // 160+ (>=80%) -> A
            // 140+ (>=70%) -> B+
            // 120+ (>=60%) -> B
            // 100+ (>=50%) -> C
            // 80+  (>=40%) -> D
            // <80  (<40%)  -> F
            System.out.print("  Checking Letter Grade calculation logic... ");
            if (!"A+".equals(MarksController.computeLetterGrade(185.0)) ||
                !"A".equals(MarksController.computeLetterGrade(165.0)) ||
                !"B+".equals(MarksController.computeLetterGrade(145.0)) ||
                !"B".equals(MarksController.computeLetterGrade(125.0)) ||
                !"C".equals(MarksController.computeLetterGrade(105.0)) ||
                !"D".equals(MarksController.computeLetterGrade(85.0)) ||
                !"F".equals(MarksController.computeLetterGrade(75.0))) {
                System.out.println("FAIL (Letter grade calculation mismatch)");
                return false;
            }
            System.out.println("PASS");

            System.out.println("  [PASS] Marks/Grade/GPA calculation formulas verified successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("  [FAIL] Calculation logic test threw exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
