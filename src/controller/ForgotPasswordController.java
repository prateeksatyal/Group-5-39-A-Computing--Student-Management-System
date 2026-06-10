package controller;

import dao.UserDao;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import view.ForgotPassword;
import view.Login;

public class ForgotPasswordController {

    private final UserDao userDao = new UserDao();
    private final ForgotPassword userView;

    public ForgotPasswordController(ForgotPassword userView) {
        this.userView = userView;

        // Wire Action Listeners
        this.userView.getUpdateButton().addActionListener(new UpdatePasswordListener());
        this.userView.getCancelButton().addActionListener(e -> {
            close();
            java.awt.EventQueue.invokeLater(() -> {
                Login loginView = new Login();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
            });
        });

        // Set initial echo char for password fields placeholders to plain text
        this.userView.getPasswordField().setEchoChar('\u0000');
        this.userView.getConfirmPasswordField().setEchoChar('\u0000');

        // Window Opened focus requester
        this.userView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                userView.getContentPane().setFocusable(true);
                userView.getContentPane().requestFocusInWindow();
            }
        });

        // Email Focus Listener (Placeholder and Border Effects)
        this.userView.getEmailField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getEmailField().setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                if ("Enter email".equals(userView.getEmailField().getText())) {
                    userView.getEmailField().setText("");
                    userView.getEmailField().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getEmailField().setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                if (userView.getEmailField().getText().trim().isEmpty()) {
                    userView.getEmailField().setText("Enter email");
                    userView.getEmailField().setForeground(new Color(128, 128, 128));
                }
            }
        });

        // New Password Focus Listener (Placeholder and Border Effects)
        this.userView.getPasswordField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getPasswordField().setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                String pass = new String(userView.getPasswordField().getPassword());
                if ("New password".equals(pass)) {
                    userView.getPasswordField().setText("");
                    userView.getPasswordField().setEchoChar('\u2022');
                    userView.getPasswordField().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getPasswordField().setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                String pass = new String(userView.getPasswordField().getPassword());
                if (pass.trim().isEmpty()) {
                    userView.getPasswordField().setText("New password");
                    userView.getPasswordField().setEchoChar('\u0000');
                    userView.getPasswordField().setForeground(new Color(128, 128, 128));
                }
            }
        });

        // Confirm Password Focus Listener (Placeholder and Border Effects)
        this.userView.getConfirmPasswordField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getConfirmPasswordField().setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                String pass = new String(userView.getConfirmPasswordField().getPassword());
                if ("Confirm password".equals(pass)) {
                    userView.getConfirmPasswordField().setText("");
                    userView.getConfirmPasswordField().setEchoChar('\u2022');
                    userView.getConfirmPasswordField().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getConfirmPasswordField().setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                String pass = new String(userView.getConfirmPasswordField().getPassword());
                if (pass.trim().isEmpty()) {
                    userView.getConfirmPasswordField().setText("Confirm password");
                    userView.getConfirmPasswordField().setEchoChar('\u0000');
                    userView.getConfirmPasswordField().setForeground(new Color(128, 128, 128));
                }
            }
        });

        // Update Button Hover/Focus/Border Effects
        this.userView.getUpdateButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.userView.getUpdateButton().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getUpdateButton().setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getUpdateButton().setBorder(null);
            }
        });
        this.userView.getUpdateButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getUpdateButton().setBackground(new Color(5, 18, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                userView.getUpdateButton().setBackground(new Color(11, 27, 226));
            }
        });

        // Cancel Button Hover/Focus/Border Effects
        this.userView.getCancelButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.userView.getCancelButton().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getCancelButton().setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getCancelButton().setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
        });
        this.userView.getCancelButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getCancelButton().setBackground(new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                userView.getCancelButton().setBackground(Color.WHITE);
            }
        });
    }


    public void open() {
        this.userView.pack();
        this.userView.setLocationRelativeTo(null);
        this.userView.setVisible(true);
    }

    public void close() {
        this.userView.dispose();
    }

    class UpdatePasswordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String email = userView.getEmailField().getText();
                String role = userView.getRoleComboBox().getSelectedItem().toString();
                String password = new String(userView.getPasswordField().getPassword());
                String confirmPassword = new String(userView.getConfirmPasswordField().getPassword());

                // Validation
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                    "Enter email".equalsIgnoreCase(email) || "New password".equalsIgnoreCase(password) ||
                    "Confirm password".equalsIgnoreCase(confirmPassword)) {
                    JOptionPane.showMessageDialog(userView, "Please fill in all fields.");
                    return;
                }

                if (role == null || "Select Role".equals(role) || role.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(userView, "Please select a valid role!");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(userView, "Passwords do not match.");
                    return;
                }

                boolean success = userDao.updatePassword(email, role, password);

                if (success) {
                    JOptionPane.showMessageDialog(userView, "Password Reset Successful!");
                    close();
                    java.awt.EventQueue.invokeLater(() -> {
                        Login loginView = new Login();
                        LoginController loginController = new LoginController(loginView);
                        loginController.open();
                    });
                } else {
                    JOptionPane.showMessageDialog(userView, "No user found with the specified Email and Role.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(userView, "Error updating password: " + ex.getMessage());
            }
        }
    }
}
