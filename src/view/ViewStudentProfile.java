/*
 * Decompiled with CFR 0.152.
 */
package view;

import controller.LogoutController;
import controller.UserSession;
import dao.StudentDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import model.StudentData;
import model.UserData;

public class ViewStudentProfile
extends JFrame {

    public ViewStudentProfile() {
        this.initComponents();
        this.setupMenuIcons();
        LogoutController.wireLogout(this, this.getLogoutButton());
        // this.jButtonDashboard.addActionListener(e -> {
        //     this.dispose();
        //     EventQueue.invokeLater(() -> new StudentDashboard().setVisible(true));
        // });
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
        this.loadProfileData();
        try {
            this.setIconImage(new ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelProfileTitle = new javax.swing.JLabel();
        jPanelProfileCard = new javax.swing.JPanel();
        jLabelAddressLabel = new javax.swing.JLabel();
        jLabelAddressVal = new javax.swing.JLabel();
        jLabelCourseLabel = new javax.swing.JLabel();
        jLabelCourseVal = new javax.swing.JLabel();
        jLabelEmailLabel = new javax.swing.JLabel();
        jLabelEmailVal = new javax.swing.JLabel();
        jLabelGenderLabel = new javax.swing.JLabel();
        jLabelGenderVal = new javax.swing.JLabel();
        jLabelIdLabel = new javax.swing.JLabel();
        jLabelIdVal = new javax.swing.JLabel();
        jLabelNameLabel = new javax.swing.JLabel();
        jLabelNameVal = new javax.swing.JLabel();
        jLabelPhoneLabel = new javax.swing.JLabel();
        jLabelPhoneVal = new javax.swing.JLabel();
        jPanelSidebar = new javax.swing.JPanel();
        jButtonAttendance = new javax.swing.JButton();
        jButtonCourses = new javax.swing.JButton();
        jButtonDashboard = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonProfile = new javax.swing.JButton();
        jButtonResults = new javax.swing.JButton();
        jLabelHeader = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Personal Profile");
        setResizable(false);

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMain.setLayout(null);

        jPanelHeader.setBackground(new java.awt.Color(243, 227, 225));
        jPanelHeader.setLayout(null);

        jLabelProfileTitle.setText("My Personal Profile");
        jLabelProfileTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabelProfileTitle.setForeground(new java.awt.Color(28, 39, 50));
        jPanelHeader.add(jLabelProfileTitle);
        jLabelProfileTitle.setBounds(30, 15, 300, 30);

        jPanelMain.add(jPanelHeader);
        jPanelHeader.setBounds(240, 0, 710, 60);

        jPanelProfileCard.setBackground(new java.awt.Color(248, 249, 250));
        jPanelProfileCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanelProfileCard.setLayout(null);

        jLabelAddressLabel.setText("Address:");
        jLabelAddressLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelAddressLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelAddressLabel);
        jLabelAddressLabel.setBounds(40, 270, 150, 25);

        jLabelAddressVal.setText("N/A");
        jLabelAddressVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelAddressVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelAddressVal);
        jLabelAddressVal.setBounds(200, 270, 400, 25);

        jLabelCourseLabel.setText("Enrolled Course:");
        jLabelCourseLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCourseLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelCourseLabel);
        jLabelCourseLabel.setBounds(40, 190, 150, 25);

        jLabelCourseVal.setText("N/A");
        jLabelCourseVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCourseVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelCourseVal);
        jLabelCourseVal.setBounds(200, 190, 400, 25);

        jLabelEmailLabel.setText("Email:");
        jLabelEmailLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelEmailLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelEmailLabel);
        jLabelEmailLabel.setBounds(40, 110, 150, 25);

        jLabelEmailVal.setText("N/A");
        jLabelEmailVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelEmailVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelEmailVal);
        jLabelEmailVal.setBounds(200, 110, 400, 25);

        jLabelGenderLabel.setText("Gender:");
        jLabelGenderLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelGenderLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelGenderLabel);
        jLabelGenderLabel.setBounds(40, 230, 150, 25);

        jLabelGenderVal.setText("N/A");
        jLabelGenderVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelGenderVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelGenderVal);
        jLabelGenderVal.setBounds(200, 230, 400, 25);

        jLabelIdLabel.setText("Student ID:");
        jLabelIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelIdLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelIdLabel);
        jLabelIdLabel.setBounds(40, 30, 150, 25);

        jLabelIdVal.setText("N/A");
        jLabelIdVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelIdVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelIdVal);
        jLabelIdVal.setBounds(200, 30, 400, 25);

        jLabelNameLabel.setText("Full Name:");
        jLabelNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNameLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelNameLabel);
        jLabelNameLabel.setBounds(40, 70, 150, 25);

        jLabelNameVal.setText("Guest User");
        jLabelNameVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNameVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelNameVal);
        jLabelNameVal.setBounds(200, 70, 400, 25);

        jLabelPhoneLabel.setText("Phone Number:");
        jLabelPhoneLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelPhoneLabel.setForeground(new java.awt.Color(28, 39, 50));
        jPanelProfileCard.add(jLabelPhoneLabel);
        jLabelPhoneLabel.setBounds(40, 150, 150, 25);

        jLabelPhoneVal.setText("N/A");
        jLabelPhoneVal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelPhoneVal.setForeground(new java.awt.Color(60, 60, 60));
        jPanelProfileCard.add(jLabelPhoneVal);
        jLabelPhoneVal.setBounds(200, 150, 400, 25);

        jPanelMain.add(jPanelProfileCard);
        jPanelProfileCard.setBounds(280, 100, 630, 350);

        jPanelSidebar.setBackground(new java.awt.Color(28, 39, 50));
        jPanelSidebar.setLayout(null);

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

        jButtonDashboard.setText("  Dashboard");
        jButtonDashboard.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonDashboard.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDashboard.setBorder(null);
        jButtonDashboard.setContentAreaFilled(false);
        jButtonDashboard.setFocusPainted(false);
        jButtonDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonDashboard);
        jButtonDashboard.setBounds(10, 80, 220, 32);

        jButtonLogout.setText("  Logout");
        jButtonLogout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setBorder(null);
        jButtonLogout.setContentAreaFilled(false);
        jButtonLogout.setFocusPainted(false);
        jButtonLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonLogout);
        jButtonLogout.setBounds(10, 280, 220, 32);

        jButtonProfile.setText("  View Profile");
        jButtonProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonProfile.setBackground(new java.awt.Color(243, 227, 225));
        jButtonProfile.setForeground(new java.awt.Color(11, 27, 226));
        jButtonProfile.setBorder(null);
        jButtonProfile.setFocusPainted(false);
        jButtonProfile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonProfile);
        jButtonProfile.setBounds(10, 120, 220, 32);

        jButtonResults.setText("  View Results");
        jButtonResults.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonResults.setForeground(new java.awt.Color(255, 255, 255));
        jButtonResults.setBorder(null);
        jButtonResults.setContentAreaFilled(false);
        jButtonResults.setFocusPainted(false);
        jButtonResults.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonResults);
        jButtonResults.setBounds(10, 240, 220, 32);

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
            .add(jPanelMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
private void setupSidebarButton(JButton btn, String text, int y, boolean isActive) {
        btn.setFont(new Font("Segoe UI", 0, 14));
        btn.setForeground(new Color(255, 255, 255));
        btn.setText(text);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(2);
        btn.setBounds(10, y, 220, 32);
        btn.setCursor(new Cursor(12));
        if (isActive) {
            btn.setBackground(new Color(243, 227, 225));
            btn.setForeground(new Color(11, 27, 226));
            btn.setContentAreaFilled(true);
        } else {
            this.addSidebarHoverEffects(btn);
        }
    }

    private void addSidebarHoverEffects(final JButton btn) {
        btn.addMouseListener(new MouseAdapter(){
            

            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new Color(40, 55, 70));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!btn.isFocusOwner()) {
                    btn.setContentAreaFilled(false);
                }
            }
        });
        btn.addFocusListener(new FocusListener(){
            

            @Override
            public void focusGained(FocusEvent e) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new Color(50, 70, 90));
            }

            @Override
            public void focusLost(FocusEvent e) {
                btn.setContentAreaFilled(false);
            }
        });
    }

    private void setupProfileRow(JLabel lbl, String text, JLabel valLbl, String valText, int y) {
        lbl.setFont(new Font("Segoe UI", 1, 14));
        lbl.setForeground(new Color(28, 39, 50));
        lbl.setText(text);
        lbl.setBounds(40, y, 150, 25);
        valLbl.setFont(new Font("Segoe UI", 0, 14));
        valLbl.setForeground(new Color(60, 60, 60));
        valLbl.setText(valText);
        valLbl.setBounds(200, y, 400, 25);
    }

    private void setupMenuIcons() {
        try {
            this.jButtonDashboard.setIcon(new ImageIcon(this.getClass().getResource("/images/home.png")));
            this.jButtonProfile.setIcon(new ImageIcon(this.getClass().getResource("/images/user.png")));
            this.jButtonAttendance.setIcon(new ImageIcon(this.getClass().getResource("/images/attendance.png")));
            this.jButtonCourses.setIcon(new ImageIcon(this.getClass().getResource("/images/course.png")));
            this.jButtonResults.setIcon(new ImageIcon(this.getClass().getResource("/images/result.png")));
            this.jButtonLogout.setIcon(new ImageIcon(this.getClass().getResource("/images/logout.png")));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void loadProfileData() {
        UserData user = UserSession.getCurrentUser();
        if (user != null) {
            StudentDAO dao = new StudentDAO();
            String studentId = user.getUserName();
            StudentData student = dao.searchStudentById(studentId);
            if (student != null) {
                this.jLabelIdVal.setText(student.getStudentId());
                this.jLabelNameVal.setText(student.getFullName());
                this.jLabelEmailVal.setText(student.getEmail());
                this.jLabelPhoneVal.setText(student.getPhoneNumber());
                this.jLabelCourseVal.setText(student.getCourse());
                this.jLabelGenderVal.setText(student.getGender());
                this.jLabelAddressVal.setText(student.getAddress());
            } else {
                this.jLabelIdVal.setText(studentId);
                this.jLabelNameVal.setText(user.getUserName());
                this.jLabelEmailVal.setText(user.getEmail());
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
        }
        catch (Exception exception) {
            // empty catch block
        }
        EventQueue.invokeLater(() -> new ViewStudentProfile().setVisible(true));
    }

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAttendance;
    private javax.swing.JButton jButtonCourses;
    private javax.swing.JButton jButtonDashboard;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonProfile;
    private javax.swing.JButton jButtonResults;
    private javax.swing.JLabel jLabelAddressLabel;
    private javax.swing.JLabel jLabelAddressVal;
    private javax.swing.JLabel jLabelCourseLabel;
    private javax.swing.JLabel jLabelCourseVal;
    private javax.swing.JLabel jLabelEmailLabel;
    private javax.swing.JLabel jLabelEmailVal;
    private javax.swing.JLabel jLabelGenderLabel;
    private javax.swing.JLabel jLabelGenderVal;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JLabel jLabelIdLabel;
    private javax.swing.JLabel jLabelIdVal;
    private javax.swing.JLabel jLabelNameLabel;
    private javax.swing.JLabel jLabelNameVal;
    private javax.swing.JLabel jLabelPhoneLabel;
    private javax.swing.JLabel jLabelPhoneVal;
    private javax.swing.JLabel jLabelProfileTitle;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelProfileCard;
    private javax.swing.JPanel jPanelSidebar;
    // End of variables declaration//GEN-END:variables
}