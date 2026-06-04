package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserData;
import view.Login;

public class LoginController {

    private final UserDao userDao = new UserDao();
    private final Login userView;

    public LoginController(Login userView) {
        this.userView = userView;

        // Login Button Listener
        this.userView.getLoginButton().addActionListener(new LoginListener());

        // Register Button Listener
        this.userView.getRegisterButton().addActionListener(e -> {
            close();
            java.awt.EventQueue.invokeLater(() -> {
                view.Signup signupView = new view.Signup();
                SignupController signupController = new SignupController(signupView);
                signupController.open();
            });
        });

        // Forget Password Button Listener
        this.userView.getForgetPasswordButton().addActionListener(e -> {
            close();
            java.awt.EventQueue.invokeLater(() -> {
                view.ForgotPassword forgotView = new view.ForgotPassword();
                ForgotPasswordController forgotController = new ForgotPasswordController(forgotView);
                forgotController.open();
            });
        });

        // Password Toggle Button Listener
        this.userView.getEyeToggleButton().addActionListener(e -> {
            if (this.userView.getEyeToggleButton().isSelected()) {
                this.userView.getPasswordField().setEchoChar('\u0000');
                this.userView.getEyeToggleButton().setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye-close.png")));
            } else {
                String passText = new String(this.userView.getPasswordField().getPassword());
                if (!"Enter password".equals(passText)) {
                    this.userView.getPasswordField().setEchoChar('\u2022');
                } else {
                    this.userView.getPasswordField().setEchoChar('\u0000');
                }
                this.userView.getEyeToggleButton().setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye-open.png")));
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

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = userView.getEmailField().getText();
            String password = new String(userView.getPasswordField().getPassword());
            String role = userView.getSelectedRole();

            // Validation
            if (email.isEmpty() || password.isEmpty() || "Enter email or ID".equals(email) || "Enter password".equals(password)) {
                JOptionPane.showMessageDialog(
                        userView,
                        "Please fill all fields!"
                );
                return;
            }

            if (role == null || "Select Role".equals(role) || role.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        userView,
                        "Please select a valid role!"
                );
                return;
            }

            // Check login
            UserData user = userDao.loginUser(email, password, role);

            if (user != null) {
                // Set the active user session!
                UserSession.setCurrentUser(user);

                JOptionPane.showMessageDialog(
                        userView,
                        "Login Successful!"
                );

                close();

                // Transition to target frame based on role
                java.awt.EventQueue.invokeLater(() -> {
                    if (UserSession.isAdmin()) {
                        view.AdminDashboard adminView = new view.AdminDashboard();
                        new AdminDashboardController(adminView);
                        adminView.setResizable(true);
                        adminView.pack();
                        adminView.setLocationRelativeTo(null);
                        adminView.setVisible(true);
                    } else if (UserSession.isTeacher()) {
                        view.TeacherDashboard teacherView = new view.TeacherDashboard();
                        new TeacherDashboardController(teacherView);
                        teacherView.setResizable(true);
                        teacherView.pack();
                        teacherView.setLocationRelativeTo(null);
                        teacherView.setVisible(true);
                    } else {
                        // Student
                        view.StudentDashboard studentView = new view.StudentDashboard();
                        new StudentDashboardController(studentView);
                        studentView.setResizable(true);
                        studentView.pack();
                        studentView.setLocationRelativeTo(null);
                        studentView.setVisible(true);
                    }
                });

            } else {
                JOptionPane.showMessageDialog(
                        userView,
                        "Invalid Email or Password!"
                );
            }
        }
    }
}