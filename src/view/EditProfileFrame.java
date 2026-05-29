package view;

import controller.LogoutController;
import java.util.logging.Level;
import java.util.logging.Logger;
import controller.UserSession;
import dao.StudentDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import model.StudentData;
import model.UserData;

public class EditProfileFrame
extends JFrame {
    private static final Logger logger = Logger.getLogger(EditProfileFrame.class.getName());
    private final StudentDAO studentDAO = new StudentDAO();
    private StudentData currentStudent;

    public EditProfileFrame() {
        this.initComponents();
        this.setupMenuIcons();
        this.addHoverEffects();
        this.loadProfileData();
        LogoutController.wireLogout(this, this.getLogoutButton());
        
        // this.jButtonDashboard.addActionListener(e -> {
        //     this.dispose();
        //     EventQueue.invokeLater(() -> new StudentDashboard().setVisible(true));
        // });
        this.jButtonProfile.addActionListener(e -> {
            this.dispose();
            EventQueue.invokeLater(() -> new ViewStudentProfile().setVisible(true));
        });
        // this.jButtonAttendance.addActionListener(e -> {
        //     this.dispose();
        //     EventQueue.invokeLater(() -> new AttendanceSummaryFrame().setVisible(true));
        // });
        // this.jButtonCourses.addActionListener(e -> {
        //     this.dispose();
        //     EventQueue.invokeLater(() -> new ViewAssignedCourseFrame().setVisible(true));
        // });
        // this.jButtonResults.addActionListener(e -> {
        //     this.dispose();
        //     EventQueue.invokeLater(() -> new ViewResultFrame().setVisible(true));
        // });
        
        try {
            this.setIconImage(new ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        } catch (Exception e) {
            // ignore
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelProfileTitle = new javax.swing.JLabel();
        jPanelProfileCard = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jLabelAddressLabel = new javax.swing.JLabel();
        jLabelCourseLabel = new javax.swing.JLabel();
        jLabelCourseVal = new javax.swing.JLabel();
        jLabelEmailLabel = new javax.swing.JLabel();
        jLabelGenderLabel = new javax.swing.JLabel();
        jLabelIdLabel = new javax.swing.JLabel();
        jLabelIdVal = new javax.swing.JLabel();
        jLabelNameLabel = new javax.swing.JLabel();
        jLabelPhoneLabel = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldPhone = new javax.swing.JTextField();
        jComboBoxGender = new javax.swing.JComboBox();
        jPanelSidebar = new javax.swing.JPanel();
        jButtonDashboard = new javax.swing.JButton();
        jButtonProfile = new javax.swing.JButton();
        jButtonAttendance = new javax.swing.JButton();
        jButtonCourses = new javax.swing.JButton();
        jButtonResults = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jLabelHeader = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Edit Profile");
        setResizable(false);

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMain.setLayout(null);

        jPanelHeader.setBackground(new java.awt.Color(243, 227, 225));
        jPanelHeader.setLayout(null);

        jLabelProfileTitle.setText("Edit Personal Profile");
        jLabelProfileTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabelProfileTitle.setForeground(new java.awt.Color(28, 39, 50));
        jPanelHeader.add(jLabelProfileTitle);
        jLabelProfileTitle.setBounds(30, 15, 300, 30);

        jPanelMain.add(jPanelHeader);
        jPanelHeader.setBounds(240, 0, 710, 60);

        jPanelProfileCard.setBackground(new java.awt.Color(255, 255, 255));
        jPanelProfileCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanelProfileCard.setLayout(null);

        jButtonCancel.setText("Cancel");
        jButtonCancel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonCancel.setForeground(new java.awt.Color(28, 39, 50));
        jButtonCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        jButtonCancel.setFocusPainted(false);
        jPanelProfileCard.add(jButtonCancel);
        jButtonCancel.setBounds(350, 315, 120, 35);

        jButtonSave.setText("Save Changes");
        jButtonSave.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonSave.setBackground(new java.awt.Color(11, 27, 226));
        jButtonSave.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSave.setBorder(null);
        jButtonSave.setFocusPainted(false);
        jPanelProfileCard.add(jButtonSave);
        jButtonSave.setBounds(180, 315, 150, 35);

        jLabelAddressLabel.setText("Address:");
        jLabelAddressLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelAddressLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelAddressLabel);
        jLabelAddressLabel.setBounds(40, 265, 120, 25);

        jLabelCourseLabel.setText("Enrolled Course:");
        jLabelCourseLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCourseLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelCourseLabel);
        jLabelCourseLabel.setBounds(40, 185, 120, 25);

        jLabelCourseVal.setText("N/A");
        jLabelCourseVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCourseVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelCourseVal);
        jLabelCourseVal.setBounds(180, 185, 400, 25);

        jLabelEmailLabel.setText("Email:");
        jLabelEmailLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelEmailLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelEmailLabel);
        jLabelEmailLabel.setBounds(40, 105, 120, 25);

        jLabelGenderLabel.setText("Gender:");
        jLabelGenderLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelGenderLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelGenderLabel);
        jLabelGenderLabel.setBounds(40, 225, 120, 25);

        jLabelIdLabel.setText("Student ID:");
        jLabelIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelIdLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelIdLabel);
        jLabelIdLabel.setBounds(40, 25, 120, 25);

        jLabelIdVal.setText("N/A");
        jLabelIdVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelIdVal);
        jLabelIdVal.setBounds(180, 25, 400, 25);

        jLabelNameLabel.setText("Full Name:");
        jLabelNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNameLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelNameLabel);
        jLabelNameLabel.setBounds(40, 65, 120, 25);

        jLabelPhoneLabel.setText("Phone Number:");
        jLabelPhoneLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelPhoneLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelPhoneLabel);
        jLabelPhoneLabel.setBounds(40, 145, 120, 25);

        jTextFieldAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanelProfileCard.add(jTextFieldAddress);
        jTextFieldAddress.setBounds(180, 265, 350, 28);

        jTextFieldEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanelProfileCard.add(jTextFieldEmail);
        jTextFieldEmail.setBounds(180, 105, 350, 28);

        jTextFieldName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanelProfileCard.add(jTextFieldName);
        jTextFieldName.setBounds(180, 65, 350, 28);

        jTextFieldPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanelProfileCard.add(jTextFieldPhone);
        jTextFieldPhone.setBounds(180, 145, 350, 28);

        jComboBoxGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanelProfileCard.add(jComboBoxGender);
        jComboBoxGender.setBounds(180, 225, 150, 28);

        jPanelMain.add(jPanelProfileCard);
        jPanelProfileCard.setBounds(280, 80, 630, 380);

        jPanelSidebar.setBackground(new java.awt.Color(28, 39, 50));
        jPanelSidebar.setLayout(null);

        jButtonDashboard.setText("  Dashboard");
        jButtonDashboard.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonDashboard.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDashboard.setBorder(null);
        jButtonDashboard.setContentAreaFilled(false);
        jButtonDashboard.setFocusPainted(false);
        jButtonDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonDashboard);
        jButtonDashboard.setBounds(10, 80, 220, 32);

        jButtonProfile.setText("  View Profile");
        jButtonProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonProfile.setBackground(new java.awt.Color(243, 227, 225));
        jButtonProfile.setForeground(new java.awt.Color(11, 27, 226));
        jButtonProfile.setBorder(null);
        jButtonProfile.setFocusPainted(false);
        jButtonProfile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonProfile);
        jButtonProfile.setBounds(10, 120, 220, 32);

        jButtonAttendance.setText("  Attendance Summary");
        jButtonAttendance.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonAttendance.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAttendance.setBorder(null);
        jButtonAttendance.setContentAreaFilled(false);
        jButtonAttendance.setFocusPainted(false);
        jButtonAttendance.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonAttendance);
        jButtonAttendance.setBounds(10, 160, 220, 32);

        jButtonCourses.setText("  Enrolled Courses");
        jButtonCourses.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonCourses.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCourses.setBorder(null);
        jButtonCourses.setContentAreaFilled(false);
        jButtonCourses.setFocusPainted(false);
        jButtonCourses.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonCourses);
        jButtonCourses.setBounds(10, 200, 220, 32);

        jButtonResults.setText("  View Results");
        jButtonResults.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonResults.setForeground(new java.awt.Color(255, 255, 255));
        jButtonResults.setBorder(null);
        jButtonResults.setContentAreaFilled(false);
        jButtonResults.setFocusPainted(false);
        jButtonResults.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonResults);
        jButtonResults.setBounds(10, 240, 220, 32);

        jButtonLogout.setText("  Logout");
        jButtonLogout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setBorder(null);
        jButtonLogout.setContentAreaFilled(false);
        jButtonLogout.setFocusPainted(false);
        jButtonLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonLogout);
        jButtonLogout.setBounds(10, 280, 220, 32);

        jLabelHeader.setText("SMS STUDENT");
        jLabelHeader.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelSidebar.add(jLabelHeader);
        jLabelHeader.setBounds(0, 20, 240, 30);

        jPanelMain.add(jPanelSidebar);
        jPanelSidebar.setBounds(0, 0, 240, 500);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        if (this.currentStudent == null) {
            return;
        }
        
        String name = this.jTextFieldName.getText().trim();
        String email = this.jTextFieldEmail.getText().trim();
        String phone = this.jTextFieldPhone.getText().trim();
        String gender = (String) this.jComboBoxGender.getSelectedItem();
        String address = this.jTextFieldAddress.getText().trim();
        
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all details!", "Validation Error", 2);
            return;
        }
        
        // Update StudentData object
        this.currentStudent.setFullName(name);
        this.currentStudent.setEmail(email);
        this.currentStudent.setPhoneNumber(phone);
        this.currentStudent.setGender(gender);
        this.currentStudent.setAddress(address);
        
        boolean success = this.studentDAO.updateStudent(this.currentStudent);
        if (success) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", 1);
            this.dispose();
            EventQueue.invokeLater(() -> new ViewStudentProfile().setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile. Please try again.", "Error", 0);
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.dispose();
        EventQueue.invokeLater(() -> new ViewStudentProfile().setVisible(true));
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void setupMenuIcons() {
        try {
            this.jButtonDashboard.setIcon(new ImageIcon(this.getClass().getResource("/images/home.png")));
            this.jButtonProfile.setIcon(new ImageIcon(this.getClass().getResource("/images/user.png")));
            this.jButtonAttendance.setIcon(new ImageIcon(this.getClass().getResource("/images/attendance.png")));
            this.jButtonCourses.setIcon(new ImageIcon(this.getClass().getResource("/images/course.png")));
            this.jButtonResults.setIcon(new ImageIcon(this.getClass().getResource("/images/result.png")));
            this.jButtonLogout.setIcon(new ImageIcon(this.getClass().getResource("/images/logout.png")));
        } catch (Exception e) {
            // ignore
        }
    }

    private void addHoverEffects() {
        JButton[] buttons = {this.jButtonDashboard, this.jButtonAttendance, this.jButtonCourses, this.jButtonResults, this.jButtonLogout};
        for (JButton btn : buttons) {
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
        
        this.jButtonSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButtonSave.setBackground(new Color(9, 21, 180));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jButtonSave.setBackground(new Color(11, 27, 226));
            }
        });
        
        this.jButtonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButtonCancel.setBackground(new Color(245, 245, 245));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jButtonCancel.setBackground(new Color(255, 255, 255));
            }
        });
    }

    private void loadProfileData() {
        UserData user = UserSession.getCurrentUser();
        if (user != null) {
            String studentId = user.getUserName();
            this.currentStudent = this.studentDAO.searchStudentById(studentId);
            if (this.currentStudent != null) {
                this.jLabelIdVal.setText(this.currentStudent.getStudentId());
                this.jTextFieldName.setText(this.currentStudent.getFullName());
                this.jTextFieldEmail.setText(this.currentStudent.getEmail());
                this.jTextFieldPhone.setText(this.currentStudent.getPhoneNumber());
                this.jLabelCourseVal.setText(this.currentStudent.getCourse());
                this.jComboBoxGender.setSelectedItem(this.currentStudent.getGender());
                this.jTextFieldAddress.setText(this.currentStudent.getAddress());
            }
        }
    }

    public JButton getLogoutButton() {
        return this.jButtonLogout;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new EditProfileFrame().setVisible(true));
    }

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAttendance;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonCourses;
    private javax.swing.JButton jButtonDashboard;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonProfile;
    private javax.swing.JButton jButtonResults;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JComboBox jComboBoxGender;
    private javax.swing.JLabel jLabelAddressLabel;
    private javax.swing.JLabel jLabelCourseLabel;
    private javax.swing.JLabel jLabelCourseVal;
    private javax.swing.JLabel jLabelEmailLabel;
    private javax.swing.JLabel jLabelGenderLabel;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JLabel jLabelIdLabel;
    private javax.swing.JLabel jLabelIdVal;
    private javax.swing.JLabel jLabelNameLabel;
    private javax.swing.JLabel jLabelPhoneLabel;
    private javax.swing.JLabel jLabelProfileTitle;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelProfileCard;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPhone;
    // End of variables declaration//GEN-END:variables
}