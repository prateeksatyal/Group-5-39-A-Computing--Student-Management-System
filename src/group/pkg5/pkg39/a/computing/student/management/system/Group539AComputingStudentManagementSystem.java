/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package group.pkg5.pkg39.a.computing.student.management.system;

import controller.LoginController;
import view.Login;

/**
 *
 * @author prate
 */
public class Group539AComputingStudentManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Use System Look and Feel for premium aesthetics
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // ignore
        }

        // Launch the application using the MVC Login controller
        java.awt.EventQueue.invokeLater(() -> {
            Login loginView = new Login();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
        });
    }
    
}
