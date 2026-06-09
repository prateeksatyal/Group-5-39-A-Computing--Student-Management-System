/*
 * Decompiled with CFR 0.152.
 */
package view;

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
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class ViewResultFrame
extends JFrame {

    // Stat card value labels — managed by ResultController (not NetBeans generated)
    private javax.swing.JLabel jLabelGpaVal     = new javax.swing.JLabel("0.00");
    private javax.swing.JLabel jLabelPercentVal = new javax.swing.JLabel("0%");
    private javax.swing.JLabel jLabelGradeVal   = new javax.swing.JLabel("—");

    public ViewResultFrame() {
        this.initComponents();
        this.setResizable(true);
        this.jPanelMain.setPreferredSize(new java.awt.Dimension(950, 500));
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(this.jPanelMain);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.setContentPane(scrollPane);
        this.setupMenuIcons();
        // Wire stat card labels into their panels
        setupStatCards();
        try {
            this.setIconImage(new ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void setupStatCards() {
        // Initialise card panels with summary labels so ResultController can update them
        java.awt.Font numFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20);
        java.awt.Font lblFont = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11);
        java.awt.Color numColor = new java.awt.Color(28, 39, 50);

        jLabelGpaVal.setFont(numFont); jLabelGpaVal.setForeground(numColor);
        jLabelGpaVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabelPercentVal.setFont(numFont); jLabelPercentVal.setForeground(numColor);
        jLabelPercentVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabelGradeVal.setFont(numFont); jLabelGradeVal.setForeground(numColor);
        jLabelGradeVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Add to card panels
        jPanelCard1.setBackground(new java.awt.Color(248,249,250));
        jPanelCard1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220,220,220)));
        jPanelCard1.setLayout(new java.awt.BorderLayout());
        javax.swing.JLabel l1 = new javax.swing.JLabel("Overall GPA", javax.swing.SwingConstants.CENTER);
        l1.setFont(lblFont); jPanelCard1.add(jLabelGpaVal, java.awt.BorderLayout.CENTER); jPanelCard1.add(l1, java.awt.BorderLayout.SOUTH);

        jPanelCard2.setBackground(new java.awt.Color(248,249,250));
        jPanelCard2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220,220,220)));
        jPanelCard2.setLayout(new java.awt.BorderLayout());
        javax.swing.JLabel l2 = new javax.swing.JLabel("Percentage", javax.swing.SwingConstants.CENTER);
        l2.setFont(lblFont); jPanelCard2.add(jLabelPercentVal, java.awt.BorderLayout.CENTER); jPanelCard2.add(l2, java.awt.BorderLayout.SOUTH);

        jPanelCard3.setBackground(new java.awt.Color(248,249,250));
        jPanelCard3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220,220,220)));
        jPanelCard3.setLayout(new java.awt.BorderLayout());
        javax.swing.JLabel l3 = new javax.swing.JLabel("Overall Grade", javax.swing.SwingConstants.CENTER);
        l3.setFont(lblFont); jPanelCard3.add(jLabelGradeVal, java.awt.BorderLayout.CENTER); jPanelCard3.add(l3, java.awt.BorderLayout.SOUTH);

        // Position card panels on the main panel
        jPanelCard1.setBounds(270,  70, 190, 110);
        jPanelCard2.setBounds(470,  70, 190, 110);
        jPanelCard3.setBounds(670,  70, 190, 110);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanelCard1 = new javax.swing.JPanel();
        jPanelCard2 = new javax.swing.JPanel();
        jPanelCard3 = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jLabelStudentId = new javax.swing.JLabel();
        jLabelStudentName = new javax.swing.JLabel();
        jLabelTerm = new javax.swing.JLabel();
        jComboBoxTerm = new javax.swing.JComboBox();
        jPanelSidebar = new javax.swing.JPanel();
        jButtonAttendance = new javax.swing.JButton();
        jButtonCourses = new javax.swing.JButton();
        jButtonDashboard = new javax.swing.JButton();
        jButtonDownloadResult = new javax.swing.JButton();
        jButtonProfile = new javax.swing.JButton();
        jLabelHeader = new javax.swing.JLabel();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jTableGrades = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Academic Transcript");
        setResizable(false);

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMain.setLayout(null);

        jPanelCard1.setLayout(null);
        jPanelMain.add(jPanelCard1);
        jPanelCard1.setBounds(0, 0, 100, 30);

        jPanelCard2.setLayout(null);
        jPanelMain.add(jPanelCard2);
        jPanelCard2.setBounds(0, 0, 100, 30);

        jPanelCard3.setLayout(null);
        jPanelMain.add(jPanelCard3);
        jPanelCard3.setBounds(0, 0, 100, 30);

        jPanelHeader.setBackground(new java.awt.Color(243, 227, 225));
        jPanelHeader.setLayout(null);

        jButtonBack.setText("Back");
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonBack.setBackground(new java.awt.Color(40, 55, 70));
        jButtonBack.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBack.setFocusPainted(false);
        jPanelHeader.add(jButtonBack);
        jButtonBack.setBounds(620, 18, 70, 28);

        jLabelStudentId.setText("Student ID: N/A");
        jLabelStudentId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelStudentId.setForeground(new java.awt.Color(100, 100, 100));
        jPanelHeader.add(jLabelStudentId);
        jLabelStudentId.setBounds(30, 30, 350, 20);

        jLabelStudentName.setText("Student Name: Guest User");
        jLabelStudentName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabelStudentName.setForeground(new java.awt.Color(28, 39, 50));
        jPanelHeader.add(jLabelStudentName);
        jLabelStudentName.setBounds(30, 10, 350, 25);

        jLabelTerm.setText("Select Term:");
        jLabelTerm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelHeader.add(jLabelTerm);
        jLabelTerm.setBounds(400, 20, 90, 25);

        jComboBoxTerm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanelHeader.add(jComboBoxTerm);
        jComboBoxTerm.setBounds(490, 18, 110, 28);

        jPanelMain.add(jPanelHeader);
        jPanelHeader.setBounds(240, 0, 710, 60);

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

        jButtonDownloadResult.setText("  View Results");
        jButtonDownloadResult.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonDownloadResult.setBackground(new java.awt.Color(243, 227, 225));
        jButtonDownloadResult.setForeground(new java.awt.Color(11, 27, 226));
        jButtonDownloadResult.setBorder(null);
        jButtonDownloadResult.setFocusPainted(false);
        jButtonDownloadResult.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonDownloadResult);
        jButtonDownloadResult.setBounds(10, 240, 220, 32);

        jButtonProfile.setText("  View Profile");
        jButtonProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonProfile.setForeground(new java.awt.Color(255, 255, 255));
        jButtonProfile.setBorder(null);
        jButtonProfile.setContentAreaFilled(false);
        jButtonProfile.setFocusPainted(false);
        jButtonProfile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonProfile);
        jButtonProfile.setBounds(10, 120, 220, 32);

        jLabelHeader.setText("SMS ACADEMICS");
        jLabelHeader.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelSidebar.add(jLabelHeader);
        jLabelHeader.setBounds(0, 20, 240, 30);

        jPanelMain.add(jPanelSidebar);
        jPanelSidebar.setBounds(0, 0, 240, 500);

        jScrollPaneTable.setViewportView(jTableGrades);

        jPanelMain.add(jScrollPaneTable);
        jScrollPaneTable.setBounds(270, 210, 650, 280);

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

    private void setupScoreCard(JPanel card, JLabel valLbl, String val, JLabel lbl, String text, int x, int y) {
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        card.setLayout(null);
        card.setBounds(x, y, 190, 110);
        valLbl.setFont(new Font("Segoe UI", 1, 28));
        valLbl.setForeground(new Color(28, 39, 50));
        valLbl.setHorizontalAlignment(0);
        valLbl.setText(val);
        card.add(valLbl);
        valLbl.setBounds(10, 15, 170, 40);
        lbl.setFont(new Font("Segoe UI", 0, 13));
        lbl.setForeground(new Color(120, 120, 120));
        lbl.setHorizontalAlignment(0);
        lbl.setText(text);
        card.add(lbl);
        lbl.setBounds(10, 65, 170, 20);
    }

    private void setupMenuIcons() {
        try {
            this.jButtonDashboard.setIcon(new ImageIcon(this.getClass().getResource("/images/home.png")));
            this.jButtonProfile.setIcon(new ImageIcon(this.getClass().getResource("/images/user.png")));
            this.jButtonAttendance.setIcon(new ImageIcon(this.getClass().getResource("/images/attendance.png")));
            this.jButtonCourses.setIcon(new ImageIcon(this.getClass().getResource("/images/course.png")));
            this.jButtonDownloadResult.setIcon(new ImageIcon(this.getClass().getResource("/images/result.png")));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public JLabel getStudentIdLabel() {
        return this.jLabelStudentId;
    }

    public JLabel getStudentNameLabel() {
        return this.jLabelStudentName;
    }

    public JComboBox<String> getTermComboBox() {
        return this.jComboBoxTerm;
    }

    public JButton getBackButton() {
        return this.jButtonBack;
    }

    public JButton getDashboardButton() {
        return this.jButtonDashboard;
    }

    public JButton getProfileButton() {
        return this.jButtonProfile;
    }

    public JButton getAttendanceButton() {
        return this.jButtonAttendance;
    }

    public JButton getCoursesButton() {
        return this.jButtonCourses;
    }

    public JButton getDownloadResultButton() {
        return this.jButtonDownloadResult;
    }

    public JTable getGradesTable() {
        return this.jTableGrades;
    }

    public JLabel getGpaValLabel() {
        return this.jLabelGpaVal;
    }

    public JLabel getPercentageValLabel() {
        return this.jLabelPercentVal;
    }

    public JLabel getOverallGradeValLabel() {
        return this.jLabelGradeVal;
    }



                       

                       

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAttendance;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonCourses;
    private javax.swing.JButton jButtonDashboard;
    private javax.swing.JButton jButtonDownloadResult;
    private javax.swing.JButton jButtonProfile;
    private javax.swing.JComboBox jComboBoxTerm;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JLabel jLabelStudentId;
    private javax.swing.JLabel jLabelStudentName;
    private javax.swing.JLabel jLabelTerm;
    private javax.swing.JPanel jPanelCard1;
    private javax.swing.JPanel jPanelCard2;
    private javax.swing.JPanel jPanelCard3;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JTable jTableGrades;
    // End of variables declaration//GEN-END:variables

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(950, 500);
    }
}