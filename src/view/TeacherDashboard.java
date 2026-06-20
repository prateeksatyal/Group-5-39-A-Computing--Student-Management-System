/*
 * Decompiled with CFR 0.152. Logic moved to TeacherDashboardController per MVC.
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class TeacherDashboard extends JFrame {

    public TeacherDashboard() {
        this.initComponents();
        this.setResizable(true);
        this.jPanel1.setPreferredSize(new java.awt.Dimension(950, 500));
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(this.jPanel1);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.setContentPane(scrollPane);
        try {
            this.setIconImage(new ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        } catch (Exception exception) {
            // ignore — icon is optional
        }
    }

    // ── Public Getters (used by TeacherDashboardController) ──────────────────

    public JButton getDashboardButton()       { return jButton1; }
    public JButton getViewProfileButton()     { return jButton2; }
    public JButton getStudentManagerButton()  { return jButton3; }
    public JButton getMarksButton()           { return jButton4; }
    public JButton getGradeButton()           { return jButton5; }
    public JButton getGenerateResultButton()  { return jButton6; }
    public JButton getAssignedCoursesButton() { return jButton7; }
    public JButton getLogoutButton()          { return jButton8; }

    /** Live stat card labels — updated by TeacherDashboardController */
    public JLabel getCard1NumLabel()  { return jLabelCard1Num; }
    public JLabel getCard2NumLabel()  { return jLabelCard2Num; }
    public JLabel getCard3NumLabel()  { return jLabelCard3Num; }
    public JLabel getCard4NumLabel()  { return jLabelCard4Num; }

    public JLabel getCard1IconLabel() { return jLabelCard1Icon; }
    public JLabel getCard2IconLabel() { return jLabelCard2Icon; }
    public JLabel getCard3IconLabel() { return jLabelCard3Icon; }
    public JLabel getCard4IconLabel() { return jLabelCard4Icon; }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelCard1 = new javax.swing.JPanel();
        jLabelCard1Icon = new javax.swing.JLabel();
        jLabelCard1Label = new javax.swing.JLabel();
        jLabelCard1Num = new javax.swing.JLabel();
        jPanelCard2 = new javax.swing.JPanel();
        jLabelCard2Icon = new javax.swing.JLabel();
        jLabelCard2Label = new javax.swing.JLabel();
        jLabelCard2Num = new javax.swing.JLabel();
        jPanelCard3 = new javax.swing.JPanel();
        jLabelCard3Icon = new javax.swing.JLabel();
        jLabelCard3Label = new javax.swing.JLabel();
        jLabelCard3Num = new javax.swing.JLabel();
        jPanelCard4 = new javax.swing.JPanel();
        jLabelCard4Icon = new javax.swing.JLabel();
        jLabelCard4Label = new javax.swing.JLabel();
        jLabelCard4Num = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Teacher Dashboard");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(28, 39, 50));
        jPanel2.setLayout(null);

        jButton1.setText("  Dashboard");
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setBackground(new java.awt.Color(243, 227, 225));
        jButton1.setForeground(new java.awt.Color(11, 27, 226));
        jButton1.setBorder(null);
        jButton1.setFocusPainted(false);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton1);
        jButton1.setBounds(10, 80, 220, 32);

        jButton2.setText("  View Profile");
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton2);
        jButton2.setBounds(10, 120, 220, 32);

        jButton3.setText("  Student Manager");
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setBorder(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton3);
        jButton3.setBounds(10, 160, 220, 32);

        jButton4.setText("  Marks Module");
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setBorder(null);
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton4);
        jButton4.setBounds(10, 200, 220, 32);

        jButton5.setText("  Grade Computation");
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setBorder(null);
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusPainted(false);
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton5);
        jButton5.setBounds(10, 240, 220, 32);

        jButton6.setText("  Generate Results");
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setBorder(null);
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton6);
        jButton6.setBounds(10, 280, 220, 32);

        jButton7.setText("  Assigned Courses");
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setBorder(null);
        jButton7.setContentAreaFilled(false);
        jButton7.setFocusPainted(false);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton7);
        jButton7.setBounds(10, 320, 220, 32);

        jButton8.setText("  Logout");
        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setBorder(null);
        jButton8.setContentAreaFilled(false);
        jButton8.setFocusPainted(false);
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton8);
        jButton8.setBounds(10, 360, 220, 32);

        jLabel1.setText("SMS TEACHER");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1);
        jLabel1.setBounds(0, 20, 240, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 240, 500);

        jPanel3.setBackground(new java.awt.Color(243, 227, 225));
        jPanel3.setLayout(null);

        jLabel2.setText("Teacher Command Center");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(28, 39, 50));
        jPanel3.add(jLabel2);
        jLabel2.setBounds(30, 20, 400, 40);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(240, 0, 710, 80);

        jPanelCard1.setBackground(new java.awt.Color(248, 249, 250));
        jPanelCard1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanelCard1.setLayout(null);
        jPanelCard1.add(jLabelCard1Icon);
        jLabelCard1Icon.setBounds(220, 40, 60, 60);

        jLabelCard1Label.setText("Total Students");
        jLabelCard1Label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCard1Label.setForeground(new java.awt.Color(120, 120, 120));
        jPanelCard1.add(jLabelCard1Label);
        jLabelCard1Label.setBounds(20, 80, 180, 25);

        jLabelCard1Num.setText("52");
        jLabelCard1Num.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelCard1Num.setForeground(new java.awt.Color(28, 39, 50));
        jPanelCard1.add(jLabelCard1Num);
        jLabelCard1Num.setBounds(20, 20, 180, 50);

        jPanel1.add(jPanelCard1);
        jPanelCard1.setBounds(270, 110, 225, 100);

        jPanelCard2.setBackground(new java.awt.Color(248, 249, 250));
        jPanelCard2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanelCard2.setLayout(null);
        jPanelCard2.add(jLabelCard2Icon);
        jLabelCard2Icon.setBounds(220, 40, 60, 60);

        jLabelCard2Label.setText("My Courses");
        jLabelCard2Label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCard2Label.setForeground(new java.awt.Color(120, 120, 120));
        jPanelCard2.add(jLabelCard2Label);
        jLabelCard2Label.setBounds(20, 80, 180, 25);

        jLabelCard2Num.setText("8");
        jLabelCard2Num.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelCard2Num.setForeground(new java.awt.Color(28, 39, 50));
        jPanelCard2.add(jLabelCard2Num);
        jLabelCard2Num.setBounds(20, 20, 180, 50);

        jPanel1.add(jPanelCard2);
        jPanelCard2.setBounds(695, 110, 225, 100);

        jPanelCard3.setBackground(new java.awt.Color(248, 249, 250));
        jPanelCard3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanelCard3.setLayout(null);
        jPanelCard3.add(jLabelCard3Icon);
        jLabelCard3Icon.setBounds(220, 40, 60, 60);

        jLabelCard3Label.setText("Attendance Rate");
        jLabelCard3Label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCard3Label.setForeground(new java.awt.Color(120, 120, 120));
        jPanelCard3.add(jLabelCard3Label);
        jLabelCard3Label.setBounds(20, 80, 180, 25);

        jLabelCard3Num.setText("91.2%");
        jLabelCard3Num.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelCard3Num.setForeground(new java.awt.Color(28, 39, 50));
        jPanelCard3.add(jLabelCard3Num);
        jLabelCard3Num.setBounds(20, 20, 180, 50);

        jPanel1.add(jPanelCard3);
        jPanelCard3.setBounds(270, 290, 225, 100);

        jPanelCard4.setBackground(new java.awt.Color(248, 249, 250));
        jPanelCard4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanelCard4.setLayout(null);
        jPanelCard4.add(jLabelCard4Icon);
        jLabelCard4Icon.setBounds(220, 40, 60, 60);

        jLabelCard4Label.setText("Class Avg GPA");
        jLabelCard4Label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCard4Label.setForeground(new java.awt.Color(120, 120, 120));
        jPanelCard4.add(jLabelCard4Label);
        jLabelCard4Label.setBounds(20, 80, 180, 25);

        jLabelCard4Num.setText("3.42");
        jLabelCard4Num.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelCard4Num.setForeground(new java.awt.Color(28, 39, 50));
        jPanelCard4.add(jLabelCard4Num);
        jLabelCard4Num.setBounds(20, 20, 180, 50);

        jPanel1.add(jPanelCard4);
        jPanelCard4.setBounds(695, 290, 225, 100);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents





                       

                       

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCard1Icon;
    private javax.swing.JLabel jLabelCard1Label;
    private javax.swing.JLabel jLabelCard1Num;
    private javax.swing.JLabel jLabelCard2Icon;
    private javax.swing.JLabel jLabelCard2Label;
    private javax.swing.JLabel jLabelCard2Num;
    private javax.swing.JLabel jLabelCard3Icon;
    private javax.swing.JLabel jLabelCard3Label;
    private javax.swing.JLabel jLabelCard3Num;
    private javax.swing.JLabel jLabelCard4Icon;
    private javax.swing.JLabel jLabelCard4Label;
    private javax.swing.JLabel jLabelCard4Num;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCard1;
    private javax.swing.JPanel jPanelCard2;
    private javax.swing.JPanel jPanelCard3;
    private javax.swing.JPanel jPanelCard4;
    // End of variables declaration//GEN-END:variables

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(950, 500);
    }
}