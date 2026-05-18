package controller;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.Login;

public class LogoutController {
    public static void wireLogout(JFrame frame, JButton logoutButton) {
        if (logoutButton == null || frame == null) {
            return;
        }
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Logout successful!", "Logout Success", 1);
            frame.dispose();
            EventQueue.invokeLater(() -> {
                Login loginView = new Login();
                LoginController controller = new LoginController(loginView);
                controller.open();
            });
        });
    }
}
