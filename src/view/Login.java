/*
 * Decompiled with CFR 0.152.
 */
package view;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Login
extends JFrame {

    public Login() {
        this.initComponents();
        this.setResizable(true);
        this.jPanel1.setPreferredSize(new java.awt.Dimension(680, 480));
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(this.jPanel1);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.setContentPane(scrollPane);
        this.jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Role", "Student", "Admin", "Teacher" }));

        // Set BasicButtonUI for Login button to render solid blue background under Nimbus
        this.jButton1.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.jButton1.setBorder(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Login");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel10.setText("System");
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel10);
        jLabel10.setBounds(0, 270, 230, 35);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Ellipse 21.png"))); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(50, 70, 130, 110);

        jLabel7.setText("Student");
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 190, 230, 35);

        jLabel9.setText("Management");
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel9);
        jLabel9.setBounds(0, 230, 230, 35);

        jPanel2.setBackground(new java.awt.Color(243, 227, 225));
        jPanel2.setLayout(null);

        jButton1.setBackground(new java.awt.Color(11, 27, 226));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel2.add(jButton1);
        jButton1.setBounds(40, 285, 370, 32);

        jButton2.setText("Forget Password?");
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jPanel2.add(jButton2);
        jButton2.setBounds(0, 330, 450, 25);

        jButton3.setText("Register");
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 255));
        jButton3.setBorder(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jPanel2.add(jButton3);
        jButton3.setBounds(275, 375, 60, 30);

        jLabel1.setText("Welcome Back");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1);
        jLabel1.setBounds(0, 30, 450, 30);

        jLabel2.setText("Sign in to your account");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 65, 450, 30);

        jLabel3.setText("Email or ID");
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel2.add(jLabel3);
        jLabel3.setBounds(40, 120, 110, 30);

        jLabel4.setText("Password");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel2.add(jLabel4);
        jLabel4.setBounds(40, 175, 110, 30);

        jLabel5.setText("Role");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel2.add(jLabel5);
        jLabel5.setBounds(40, 230, 110, 30);

        jLabel8.setText("Don't have an account ?");
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel2.add(jLabel8);
        jLabel8.setBounds(110, 375, 160, 30);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setLayout(null);

        jPasswordField1.setText("Enter password");
        jPasswordField1.setForeground(new java.awt.Color(128, 128, 128));
        jPasswordField1.setBorder(null);
        jPanel3.add(jPasswordField1);
        jPasswordField1.setBounds(5, 0, 180, 28);

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye-open.png"))); // NOI18N
        jToggleButton1.setBorder(null);
        jToggleButton1.setContentAreaFilled(false);
        jToggleButton1.setFocusPainted(false);
        jPanel3.add(jToggleButton1);
        jToggleButton1.setBounds(190, 0, 35, 28);

        jPanel2.add(jPanel3);
        jPanel3.setBounds(180, 175, 230, 30);

        jTextField1.setText("Enter email or ID");
        jTextField1.setForeground(new java.awt.Color(128, 128, 128));
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.add(jTextField1);
        jTextField1.setBounds(180, 120, 230, 30);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "", "", "" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.add(jComboBox1);
        jComboBox1.setBounds(180, 230, 230, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(230, 0, 450, 480);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 680, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 480, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    // Focus and styling behaviors refactored to LoginController                                                                                  

    public JToggleButton getEyeToggleButton() {
        return this.jToggleButton1;
    }

    public javax.swing.JPanel getPasswordPanel() {
        return this.jPanel3;
    }

    public JButton getLoginButton() {
        return this.jButton1;
    }

    public JButton getRegisterButton() {
        return this.jButton3;
    }

    public JButton getForgetPasswordButton() {
        return this.jButton2;
    }

    public JTextField getEmailField() {
        return this.jTextField1;
    }

    public JPasswordField getPasswordField() {
        return this.jPasswordField1;
    }

    public String getSelectedRole() {
        return (String)this.jComboBox1.getSelectedItem();
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(680, 480);
    }



                       

                       

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}