package controller;

import dao.UserDao;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import model.UserData;
import view.Login;
import view.Signup;

public class SignupController {
    private final UserDao userDao = new UserDao();
    private final Signup userView;

    public SignupController(Signup userView) {
        this.userView = userView;

        userView.getSignUpButton().addActionListener(new AddUserListener());
        userView.getCancelButton().addActionListener(new CancelListener());

        // Redirect back to Login view
        userView.getLoginButton().addActionListener(e -> {
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

    class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = userView.getUsernameField().getText();
                String email = userView.getEmailField().getText();
                String password = new String(userView.getPasswordField().getPassword());
                String confirmPassword = new String(userView.getConfirmPasswordField().getPassword());
                String role = userView.getRoleComboBox().getSelectedItem().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || 
                    "Enter full name".equalsIgnoreCase(name) || "Enter email".equalsIgnoreCase(email) || 
                    "Enter password".equalsIgnoreCase(password) || "Confirm Password".equalsIgnoreCase(confirmPassword)) {
                    JOptionPane.showMessageDialog(userView, "Please fill in all fields.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(userView, "Passwords do not match.");
                    return;
                }

                if (role == null || "Select Role".equals(role) || role.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(userView, "Please select a valid role!");
                    return;
                }

                UserData user = new UserData(name, password, email, role);
               
                boolean check = userDao.checkUser(user);

                if (check) {
                    JOptionPane.showMessageDialog(userView, "Duplicate user: A user with this username/email already exists.");
                } else {
                    userDao.createUser(user);
                    JOptionPane.showMessageDialog(userView, "Registration Successful!");
                    close();
                    
                    java.awt.EventQueue.invokeLater(() -> {
                        Login loginView = new Login();
                        LoginController loginController = new LoginController(loginView);
                        loginController.open();
                    });
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(userView, "Error adding user: " + ex.getMessage());
            }
        }
    }

    class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            java.awt.EventQueue.invokeLater(() -> {
                Login loginView = new Login();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
            });
        }
    }
}