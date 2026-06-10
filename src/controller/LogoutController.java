package controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import view.Login;
import view.Logout;

/**
 * MVC Controller for the Logout view.
 * Handles Login Again and Exit transitions, and provides a static helper
 * to wire up the logout buttons on other frames.
 */
public class LogoutController {

    private final Logout view;

    public LogoutController(Logout view) {
        this.view = view;
        
        // Let original NetBeans layout sizes and coordinates run natively
        this.view.pack();
        this.view.setLocationRelativeTo(null);

        // Set cursor overrides
        this.view.getLoginAgainButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.view.getExitButton().setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effects for Login Again button
        this.view.getLoginAgainButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.getLoginAgainButton().setBackground(new Color(9, 21, 180));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.getLoginAgainButton().setBackground(new Color(11, 27, 226));
            }
        });

        // Hover effects for Exit Application button
        this.view.getExitButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.getExitButton().setBackground(new Color(245, 245, 245));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.getExitButton().setBackground(new Color(255, 255, 255));
            }
        });

        
        // Wire Login Again button
        this.view.getLoginAgainButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    Login loginView = new Login();
                    LoginController loginController = new LoginController(loginView);
                    loginController.open();
                });
            }
        });

        // Wire Exit Application button
        this.view.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Static helper: attaches a logout action listener to any JButton.
     * Clears active session, disposes parent, and transitions to Logout screen.
     */
    public static void wireLogout(JFrame sourceFrame, javax.swing.JButton logoutBtn) {
        if (logoutBtn == null || sourceFrame == null) return;
        
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserSession.clearSession();
                sourceFrame.dispose();
                EventQueue.invokeLater(() -> {
                    Logout logoutView = new Logout();
                    new LogoutController(logoutView);
                    logoutView.setVisible(true);
                });
            }
        });
    }
}
