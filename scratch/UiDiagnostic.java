import view.*;
import controller.*;
import dao.*;
import model.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class UiDiagnostic {
    public static void main(String[] args) {
        System.out.println("=== Starting UI Diagnostic Verification ===");

        // 1. Confirm JTable columns at runtime
        System.out.println("\nChecking JTable runtime columns:");
        CourseManagementFrame cmFrame = new CourseManagementFrame();
        ViewAssignedCourseFrame vacFrame = new ViewAssignedCourseFrame();

        int cmColumns = cmFrame.getCoursesTable().getColumnCount();
        int vacColumns = vacFrame.getAssignedTable().getColumnCount();
        System.out.println("CourseManagementFrame -> jTableCourses columns count: " + cmColumns);
        System.out.println("ViewAssignedCourseFrame -> jTableAssigned columns count: " + vacColumns);

        // 2. Confirm database queries returning records
        System.out.println("\nChecking Database queries:");
        CourseDAO courseDAO = new CourseDAO();
        try {
            List<CourseData> courses = courseDAO.getAllCourses();
            List<CourseData> assigned = courseDAO.getAssignedCourses();
            System.out.println("courseDAO.getAllCourses() returned " + courses.size() + " records.");
            System.out.println("courseDAO.getAssignedCourses() returned " + assigned.size() + " records.");
        } catch (Exception e) {
            System.out.println("Database query failed: " + e.getMessage());
            e.printStackTrace();
        }

        // 3. Confirm controller flow and render execution
        System.out.println("\nChecking Controller rendering logic:");
        try {
            CourseController controller = new CourseController(cmFrame);
            // After constructing controller, it calls loadCoursesToTable() which calls renderManagementTable()
            System.out.println("CourseController successfully instantiated. TotalCoursesLabel text: " + cmFrame.getTotalCoursesLabel().getText());
            System.out.println("jTableCourses column count after controller: " + cmFrame.getCoursesTable().getColumnCount());
            System.out.println("jTableCourses row count after rendering: " + cmFrame.getCoursesTable().getRowCount());
            
            CourseController vacController = new CourseController(vacFrame);
            System.out.println("jTableAssigned column count after controller: " + vacFrame.getAssignedTable().getColumnCount());
            System.out.println("jTableAssigned row count after rendering: " + vacFrame.getAssignedTable().getRowCount());
        } catch (Exception e) {
            System.out.println("Controller rendering failed: " + e.getMessage());
            e.printStackTrace();
        }

        // 4. Confirm it is NOT a layout or sizing issue
        System.out.println("\nChecking layout/sizing structure:");
        System.out.println("jTableCourses bounds: " + cmFrame.getCoursesTable().getBounds());
        System.out.println("jTableCourses is visible: " + cmFrame.getCoursesTable().isVisible());
        System.out.println("jTableCourses parent: " + cmFrame.getCoursesTable().getParent());

        System.exit(0);
    }
}
