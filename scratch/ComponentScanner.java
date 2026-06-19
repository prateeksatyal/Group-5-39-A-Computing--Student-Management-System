import java.lang.reflect.Field;
import javax.swing.*;
import java.util.*;

public class ComponentScanner {
    public static void main(String[] args) {
        String[] viewClasses = {
            "view.AcademicPerformanceFrame",
            "view.AddStudentFrame",
            "view.AdminDashboard",
            "view.AttendanceSummaryFrame",
            "view.CourseManagementFrame",
            "view.DownloadResultFrame"
        };

        System.out.println("# JFrame Component Inventory\n");

        for (String className : viewClasses) {
            try {
                Class<?> clazz = Class.forName(className);
                System.out.println("## Class: " + className);
                
                Map<String, List<String>> components = new TreeMap<>();
                for (Field field : clazz.getDeclaredFields()) {
                    Class<?> type = field.getType();
                    if (JComponent.class.isAssignableFrom(type) || 
                        JFrame.class.isAssignableFrom(type) || 
                        java.awt.Component.class.isAssignableFrom(type)) {
                        
                        String typeName = type.getSimpleName();
                        components.computeIfAbsent(typeName, k -> new ArrayList<>()).add(field.getName());
                    }
                }

                for (Map.Entry<String, List<String>> entry : components.entrySet()) {
                    System.out.println("### " + entry.getKey());
                    for (String name : entry.getValue()) {
                        System.out.println("- `" + name + "`");
                    }
                }
                System.out.println();
            } catch (Exception e) {
                System.out.println("Could not load " + className + ": " + e.getMessage());
            }
        }
    }
}
