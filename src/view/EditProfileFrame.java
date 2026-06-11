package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;

public class EditProfileFrame
extends JFrame {

    public EditProfileFrame() {
        this.initComponents();
        this.setResizable(true);


        this.jPanelMain.setPreferredSize(new java.awt.Dimension(950, 500));
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(this.jPanelMain);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.setContentPane(scrollPane);

        // All navigation, profile loading, hover effects, icons, and logout
        // are wired by EditProfileController (MVC pattern).
        
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
            .add(jPanelMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // All business logic (save, cancel, profile loading, hover effects, icons)
    // is handled by EditProfileController — MVC pattern.

    public JButton getLogoutButton()     { return this.jButtonLogout; }
    public JButton getSaveButton()       { return this.jButtonSave; }
    public JButton getCancelButton()     { return this.jButtonCancel; }
    public JButton getDashboardButton()  { return this.jButtonDashboard; }
    public JButton getProfileButton()    { return this.jButtonProfile; }
    public JButton getAttendanceButton() { return this.jButtonAttendance; }
    public JButton getCoursesButton()    { return this.jButtonCourses; }
    public JButton getResultsButton()    { return this.jButtonResults; }

    public JTextField getNameField()    { return this.jTextFieldName; }
    public JTextField getEmailField()   { return this.jTextFieldEmail; }
    public JTextField getPhoneField()   { return this.jTextFieldPhone; }
    public JTextField getAddressField() { return this.jTextFieldAddress; }
    public JComboBox  getGenderCombo()  { return this.jComboBoxGender; }

    public JLabel getIdValueLabel()     { return this.jLabelIdVal; }
    public JLabel getCourseValueLabel() { return this.jLabelCourseVal; }



                       

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

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(950, 500);
    }
}