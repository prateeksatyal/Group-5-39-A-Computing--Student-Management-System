package controller;

import controller.AdminDashboardController;
import controller.TeacherDashboardController;
import controller.StudentDashboardController;
import dao.UserDao;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
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

        // Set initial echo char for password field placeholder to plain text
        this.userView.getPasswordField().setEchoChar('\u0000');

        // Window Opened Focus Listener
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
                if ("Enter email or ID".equals(userView.getEmailField().getText())) {
                    userView.getEmailField().setText("");
                    userView.getEmailField().setForeground(new Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getEmailField().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
                if (userView.getEmailField().getText().isEmpty()) {
                    userView.getEmailField().setText("Enter email or ID");
                    userView.getEmailField().setForeground(new Color(128, 128, 128));
                }
            }
        });

        // Password Focus Listener (Placeholder and Border Effects)
        this.userView.getPasswordField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getPasswordPanel().setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                String pass = new String(userView.getPasswordField().getPassword());
                if ("Enter password".equals(pass)) {
                    userView.getPasswordField().setText("");
                    if (!userView.getEyeToggleButton().isSelected()) {
                        userView.getPasswordField().setEchoChar('\u2022');
                    } else {
                        userView.getPasswordField().setEchoChar('\u0000');
                    }
                    userView.getPasswordField().setForeground(new Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getPasswordPanel().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
                String pass = new String(userView.getPasswordField().getPassword());
                if (pass.isEmpty()) {
                    userView.getPasswordField().setText("Enter password");
                    userView.getPasswordField().setEchoChar('\u0000');
                    userView.getPasswordField().setForeground(new Color(128, 128, 128));
                }
            }
        });

        // Login Button Hover/Focus/Border Effects
        this.userView.getLoginButton().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.userView.getLoginButton().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getLoginButton().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getLoginButton().setBorder(null);
            }
        });
        this.userView.getLoginButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getLoginButton().setBackground(new Color(5, 18, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                userView.getLoginButton().setBackground(new Color(11, 27, 226));
            }
        });

        // Forget Password Button Hover/Focus Effects
        this.userView.getForgetPasswordButton().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.userView.getForgetPasswordButton().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getForgetPasswordButton().setForeground(new Color(11, 27, 226));
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getForgetPasswordButton().setForeground(new Color(0, 0, 0));
            }
        });
        this.userView.getForgetPasswordButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getForgetPasswordButton().setForeground(new Color(11, 27, 226));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!userView.getForgetPasswordButton().isFocusOwner()) {
                    userView.getForgetPasswordButton().setForeground(new Color(0, 0, 0));
                }
            }
        });

        // Register Button Hover/Focus Effects
        this.userView.getRegisterButton().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.userView.getRegisterButton().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getRegisterButton().setForeground(new Color(11, 27, 226));
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getRegisterButton().setForeground(new Color(0, 51, 255));
            }
        });
        this.userView.getRegisterButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getRegisterButton().setForeground(new Color(11, 27, 226));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!userView.getRegisterButton().isFocusOwner()) {
                    userView.getRegisterButton().setForeground(new Color(0, 51, 255));
                }
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

                // Self-healing for legacy student accounts created before the fix
                if ("Student".equalsIgnoreCase(role)) {
                    dao.StudentDAO studentDAO = new dao.StudentDAO();
                    if (studentDAO.searchStudentByEmail(user.getEmail()) == null) {
                        String fullName = userDao.getFullNameByEmail(user.getEmail());
                        if (fullName == null || fullName.isEmpty()) {
                            fullName = user.getEmail().split("@")[0]; // fallback
                        }
                        String studentId;
                        do {
                            studentId = "STU" + (1000 + new java.util.Random().nextInt(9000));
                        } while (studentDAO.searchStudentById(studentId) != null);
                        model.StudentData student = new model.StudentData(studentId, fullName, user.getEmail(), "", "", "", "");
                        studentDAO.insertStudent(student);
                    }
                }

                JOptionPane.showMessageDialog(
                        userView,
                        "Login Successful!"
                );

                close();

                // Transition to target frame based on role
                java.awt.EventQueue.invokeLater(() -> {
                    try {
                        if ("Administrator".equalsIgnoreCase(role) || "Admin".equalsIgnoreCase(role)) {
                            view.AdminDashboard adminView = new view.AdminDashboard();
                            new AdminDashboardController(adminView);
                            adminView.pack();
                            adminView.setLocationRelativeTo(null);
                            adminView.setVisible(true);
                        } else if ("Teacher".equalsIgnoreCase(role)) {
                            view.TeacherDashboard teacherView = new view.TeacherDashboard();
                            new TeacherDashboardController(teacherView);
                            teacherView.pack();
                            teacherView.setLocationRelativeTo(null);
                            teacherView.setVisible(true);
                        } else {
                            // Student (and any other role)
                            view.StudentDashboard studentView = new view.StudentDashboard();
                            new StudentDashboardController(studentView);
                            studentView.pack();
                            studentView.setLocationRelativeTo(null);
                            studentView.setVisible(true);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                            "Error opening dashboard:\n" + ex.getMessage(),
                            "Navigation Error", JOptionPane.ERROR_MESSAGE);
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