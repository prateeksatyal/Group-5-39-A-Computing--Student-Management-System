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

        // Username / Full Name Focus Listener (Placeholder and Border Effects)
        this.userView.getUsernameField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getUsernameField().setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                if ("Enter full name".equals(userView.getUsernameField().getText())) {
                    userView.getUsernameField().setText("");
                    userView.getUsernameField().setForeground(new Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getUsernameField().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
                if (userView.getUsernameField().getText().isEmpty()) {
                    userView.getUsernameField().setText("Enter full name");
                    userView.getUsernameField().setForeground(new Color(128, 128, 128));
                }
            }
        });

        // Email Focus Listener (Placeholder and Border Effects)
        this.userView.getEmailField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getEmailField().setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                if ("Enter email".equals(userView.getEmailField().getText())) {
                    userView.getEmailField().setText("");
                    userView.getEmailField().setForeground(new Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getEmailField().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
                if (userView.getEmailField().getText().isEmpty()) {
                    userView.getEmailField().setText("Enter email");
                    userView.getEmailField().setForeground(new Color(128, 128, 128));
                }
            }
        });

        // Password Focus Listener (Placeholder and Border Effects)
        this.userView.getPasswordField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getPasswordField().setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                String pass = new String(userView.getPasswordField().getPassword());
                if ("Enter password".equals(pass)) {
                    userView.getPasswordField().setText("");
                    userView.getPasswordField().setEchoChar('\u2022');
                    userView.getPasswordField().setForeground(new Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getPasswordField().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
                String pass = new String(userView.getPasswordField().getPassword());
                if (pass.isEmpty()) {
                    userView.getPasswordField().setText("Enter password");
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
                if ("Confirm Password".equals(pass)) {
                    userView.getConfirmPasswordField().setText("");
                    userView.getConfirmPasswordField().setEchoChar('\u2022');
                    userView.getConfirmPasswordField().setForeground(new Color(0, 0, 0));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getConfirmPasswordField().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
                String pass = new String(userView.getConfirmPasswordField().getPassword());
                if (pass.isEmpty()) {
                    userView.getConfirmPasswordField().setText("Confirm Password");
                    userView.getConfirmPasswordField().setEchoChar('\u0000');
                    userView.getConfirmPasswordField().setForeground(new Color(128, 128, 128));
                }
            }
        });

        // Register Button Hover/Focus/Border Effects
        this.userView.getSignUpButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.userView.getSignUpButton().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getSignUpButton().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getSignUpButton().setBorder(null);
            }
        });
        this.userView.getSignUpButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getSignUpButton().setBackground(new Color(5, 18, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                userView.getSignUpButton().setBackground(new Color(11, 27, 226));
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
                userView.getCancelButton().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
            }
        });
        this.userView.getCancelButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getCancelButton().setBackground(new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                userView.getCancelButton().setBackground(new Color(255, 255, 255));
            }
        });

        // Login Button Hover/Focus Effects
        this.userView.getLoginButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.userView.getLoginButton().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userView.getLoginButton().setForeground(new Color(11, 27, 226));
            }

            @Override
            public void focusLost(FocusEvent e) {
                userView.getLoginButton().setForeground(new Color(0, 0, 0));
            }
        });
        this.userView.getLoginButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                userView.getLoginButton().setForeground(new Color(11, 27, 226));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!userView.getLoginButton().isFocusOwner()) {
                    userView.getLoginButton().setForeground(new Color(0, 0, 0));
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
                    if ("Student".equalsIgnoreCase(role)) {
                        dao.StudentDAO studentDAO = new dao.StudentDAO();
                        String studentId;
                        do {
                            studentId = "STU" + (1000 + new java.util.Random().nextInt(9000));
                        } while (studentDAO.searchStudentById(studentId) != null);
                        model.StudentData student = new model.StudentData(studentId, name, email, "", "", "", "");
                        studentDAO.insertStudent(student);
                    }
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