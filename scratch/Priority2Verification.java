import view.*;
import controller.*;

public class Priority2Verification {
    public static void main(String[] args) {
        System.out.println("Starting Priority 2 instantiation checks...");
        
        try {
            System.out.print("Instantiating GenerateResultFrame... ");
            GenerateResultFrame grFrame = new GenerateResultFrame();
            new ResultController(grFrame);
            System.out.println("SUCCESS. Size: " + grFrame.getSize());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        try {
            System.out.print("Instantiating EnrollCourseFrame... ");
            EnrollCourseFrame ecFrame = new EnrollCourseFrame();
            new CourseController(ecFrame);
            System.out.println("SUCCESS. Size: " + ecFrame.getSize());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        System.exit(0);
    }
}
