package controller;

import dao.StudentDAO;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.StudentData;
import model.UserData;
import view.AdminDashboard;
import view.AttendanceSummaryFrame;
import view.EditProfileFrame;
import view.Logout;
import view.StudentDashboard;
import view.TeacherDashboard;
import view.ViewAssignedCourseFrame;
import view.ViewStudentProfile;

/**
 * MVC Controller for EditProfileFrame.
 * Handles: profile data loading, form save/cancel, sidebar navigation, hover effects, icons, logout.
 */
public class EditProfileController {

    private final EditProfileFrame view;
    private final StudentDAO studentDAO = new StudentDAO();
    private StudentData currentStudent;
    public EditProfileController(EditProfileFrame view) {
        this.view = view;
        
        // Apply flat button styling
        this.view.getSaveButton().setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.view.getSaveButton().setBorder(null);
        this.view.getCancelButton().setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.view.pack();
        this.view.setLocationRelativeTo(null);
        
        loadProfileData();
        setupMenuIcons();
        wireSidebarListeners();
        wireSidebarHoverEffects();
        wireFormButtons();
        wireLogout();
    }

    // ── Data Loading ─────────────────────────────────────────────────────────

    private void loadProfileData() {
        UserData user = UserSession.getCurrentUser();
        if (user == null) return;

        // getUserName() now returns login email (fixed in UserDao.loginUser)
        String loginEmail = user.getUserName();

        // Primary lookup: find student record by email
        currentStudent = studentDAO.searchStudentByEmail(loginEmail);

        // Fallback: in case email == student_id
        if (currentStudent == null) {
            currentStudent = studentDAO.searchStudentById(loginEmail);
        }

        if (currentStudent != null) {
            view.getIdValueLabel().setText(currentStudent.getStudentId());
            view.getNameField().setText(currentStudent.getFullName());
            view.getEmailField().setText(currentStudent.getEmail());
            view.getPhoneField().setText(currentStudent.getPhoneNumber());
            view.getCourseValueLabel().setText(currentStudent.getCourse());
            view.getGenderCombo().setSelectedItem(currentStudent.getGender());
            view.getAddressField().setText(currentStudent.getAddress());
        }
    }

    // ── Form Buttons ─────────────────────────────────────────────────────────

    private void wireFormButtons() {
        // Save Changes
        view.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        // Cancel
        view.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    ViewStudentProfile profileView = new ViewStudentProfile();
                    new ViewStudentProfileController(profileView);
                    profileView.setVisible(true);
                });
            }
        });
    }

    private void handleSave() {
        if (currentStudent == null) {
            JOptionPane.showMessageDialog(view, "No student data loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name    = view.getNameField().getText().trim();
        String email   = view.getEmailField().getText().trim();
        String phone   = view.getPhoneField().getText().trim();
        String gender  = (String) view.getGenderCombo().getSelectedItem();
        String address = view.getAddressField().getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all details!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentStudent.setFullName(name);
        currentStudent.setEmail(email);
        currentStudent.setPhoneNumber(phone);
        currentStudent.setGender(gender);
        currentStudent.setAddress(address);

        boolean success = studentDAO.updateStudent(currentStudent);
        if (success) {
            JOptionPane.showMessageDialog(view, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            EventQueue.invokeLater(() -> {
                ViewStudentProfile profileView = new ViewStudentProfile();
                new ViewStudentProfileController(profileView);
                profileView.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(view, "Failed to update profile. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Sidebar Navigation ────────────────────────────────────────────────────

    private void wireSidebarListeners() {
        view.getProfileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    ViewStudentProfile profileView = new ViewStudentProfile();
                    new ViewStudentProfileController(profileView);
                    profileView.setVisible(true);
                });
            }
        });

        view.getAttendanceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    AttendanceSummaryFrame f = new AttendanceSummaryFrame();
                    AttendanceController ac = new AttendanceController(f);
                    if (UserSession.isStudent()) {
                        UserData user = UserSession.getCurrentUser();
                        if (user != null) {
                            String studentId = currentStudent != null ? currentStudent.getStudentId() : user.getUserName();
                            ac.searchAttendance(studentId);
                        }
                        f.getMarkSaveButton().setEnabled(false);
                        f.getMarkSaveButton().setToolTipText("Only teachers and admins can mark attendance.");
                    }
                    f.setVisible(true);
                });
            }
        });

        view.getCoursesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    ViewAssignedCourseFrame f = new ViewAssignedCourseFrame();
                    new CourseController(f);
                    f.setVisible(true);
                });
            }
        });

        // Dashboard Button
        view.getDashboardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    if (UserSession.isAdmin()) {
                        AdminDashboard admin = new AdminDashboard();
                        new AdminDashboardController(admin);
                        admin.setResizable(true);
                        admin.pack();
                        admin.setLocationRelativeTo(null);
                        admin.setVisible(true);
                    } else if (UserSession.isTeacher()) {
                        TeacherDashboard teacher = new TeacherDashboard();
                        new TeacherDashboardController(teacher);
                        teacher.setResizable(true);
                        teacher.pack();
                        teacher.setLocationRelativeTo(null);
                        teacher.setVisible(true);
                    } else {
                        StudentDashboard student = new StudentDashboard();
                        new StudentDashboardController(student);
                        student.setResizable(true);
                        student.pack();
                        student.setLocationRelativeTo(null);
                        student.setVisible(true);
                    }
                });
            }
        });

        // Results Button
        view.getResultsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                EventQueue.invokeLater(() -> {
                    view.ViewResultFrame f = new view.ViewResultFrame();
                    new ResultController(f);
                    f.setVisible(true);
                });
            }
        });
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    private void wireLogout() {
        view.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserSession.clearSession();
                view.dispose();
                EventQueue.invokeLater(() -> {
                    Logout logoutView = new Logout();
                    new LogoutController(logoutView);
                    logoutView.setVisible(true);
                });
            }
        });
    }

    // ── Icons ─────────────────────────────────────────────────────────────────

    private void setupMenuIcons() {
        try {
            view.getDashboardButton().setIcon(new ImageIcon(getClass().getResource("/images/home.png")));
            view.getProfileButton().setIcon(new ImageIcon(getClass().getResource("/images/user.png")));
            view.getAttendanceButton().setIcon(new ImageIcon(getClass().getResource("/images/attendance.png")));
            view.getCoursesButton().setIcon(new ImageIcon(getClass().getResource("/images/course.png")));
            view.getResultsButton().setIcon(new ImageIcon(getClass().getResource("/images/result.png")));
            view.getLogoutButton().setIcon(new ImageIcon(getClass().getResource("/images/logout.png")));
        } catch (Exception e) {
            // icons optional
        }
    }

    // ── Hover Effects ─────────────────────────────────────────────────────────

    private void wireSidebarHoverEffects() {
        javax.swing.JButton[] btns = {
            view.getDashboardButton(),
            view.getAttendanceButton(),
            view.getCoursesButton(),
            view.getResultsButton()
        };
        for (javax.swing.JButton btn : btns) {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setContentAreaFilled(true);
                    btn.setBackground(new Color(40, 55, 70));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setContentAreaFilled(false);
                }
            });
        }

        view.getSaveButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.getSaveButton().setBackground(new Color(9, 21, 180));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.getSaveButton().setBackground(new Color(11, 27, 226));
            }
        });

        view.getCancelButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.getCancelButton().setBackground(new Color(245, 245, 245));
                view.getCancelButton().setOpaque(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                view.getCancelButton().setBackground(new Color(255, 255, 255));
            }
        });
    }
}
