package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
