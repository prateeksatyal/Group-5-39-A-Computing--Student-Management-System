package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.UserData;
import view.loginpage;

public class LoginController {

    private final UserDao userDao = new UserDao();
    private final loginpage userView;

    public LoginController(loginpage userView) {
        this.userView = userView;

        // Login Button Listener
        this.userView.getLoginButton().addActionListener(new LoginListener());
    }

    public void open() {
        this.userView.setVisible(true);
    }

    public void close() {
        this.userView.dispose();
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Get values from form
            String email = userView.getEmailField().getText();

            String password = userView.getPasswordField().getText();

            String role = userView.getSelectedRole();

            // Validation
            if (email.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        userView,
                        "Please fill all fields!"
                );

                return;
            }

            // Check login
            UserData user = userDao.loginUser(email, password, role);

            if (user != null) {

                JOptionPane.showMessageDialog(
                        userView,
                        "Login Successful!"
                );

                close();

            } else {

                JOptionPane.showMessageDialog(
                        userView,
                        "Invalid Email or Password!"
                );
            }
        }
    }
}