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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EnrollCourseFrame
extends JFrame {

    public EnrollCourseFrame() {
        this.initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.jPanel1.setPreferredSize(new java.awt.Dimension(530, 570));
        this.setContentPane(this.jPanel1);
        try {
            this.setIconImage(new ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }



    public JTextField getStudentIdField() {
        return this.jTextFieldStudentId;
    }

    public JTextField getStudentNameField() {
        return this.jTextFieldStudentName;
    }

    public JComboBox<String> getCourseComboBox() {
        return this.jComboBoxCourse;
    }

    public JComboBox<String> getSemesterComboBox() {
        return this.jComboBoxSemester;
    }

    public JButton getEnrollButton() {
        return this.jButtonEnroll;
    }

    public JButton getClearButton() {
        return this.jButtonClear;
    }

    public JButton getBackButton() {
        return this.jButtonBack;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonEnroll = new javax.swing.JButton();
        jLabelCourse = new javax.swing.JLabel();
        jLabelHeader = new javax.swing.JLabel();
        jLabelSemester = new javax.swing.JLabel();
        jLabelStudentId = new javax.swing.JLabel();
        jLabelStudentName = new javax.swing.JLabel();
        jTextFieldStudentId = new javax.swing.JTextField();
        jTextFieldStudentName = new javax.swing.JTextField();
        jComboBoxCourse = new javax.swing.JComboBox();
        jComboBoxSemester = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Enroll Student in Course");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(243, 227, 225));
        jPanel1.setLayout(null);

        jButtonBack.setText("Back");
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonBack.setFocusPainted(false);
        jPanel1.add(jButtonBack);
        jButtonBack.setBounds(350, 400, 140, 35);

        jButtonClear.setText("Clear Form");
        jButtonClear.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonClear.setFocusPainted(false);
        jPanel1.add(jButtonClear);
        jButtonClear.setBounds(200, 400, 130, 35);

        jButtonEnroll.setText("Enroll Student");
        jButtonEnroll.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonEnroll.setBackground(new java.awt.Color(11, 27, 226));
        jButtonEnroll.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEnroll.setFocusPainted(false);
        jPanel1.add(jButtonEnroll);
        jButtonEnroll.setBounds(40, 400, 140, 35);

        jLabelCourse.setText("Select Course");
        jLabelCourse.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jPanel1.add(jLabelCourse);
        jLabelCourse.setBounds(40, 240, 150, 20);

        jLabelHeader.setText("Enroll Student in Course");
        jLabelHeader.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jPanel1.add(jLabelHeader);
        jLabelHeader.setBounds(40, 30, 370, 40);

        jLabelSemester.setText("Semester");
        jLabelSemester.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jPanel1.add(jLabelSemester);
        jLabelSemester.setBounds(40, 310, 150, 20);

        jLabelStudentId.setText("Student ID");
        jLabelStudentId.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jPanel1.add(jLabelStudentId);
        jLabelStudentId.setBounds(40, 100, 150, 20);

        jLabelStudentName.setText("Student Name");
        jLabelStudentName.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jPanel1.add(jLabelStudentName);
        jLabelStudentName.setBounds(40, 170, 150, 20);

        jTextFieldStudentId.setText("Enter Student ID");
        jTextFieldStudentId.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextFieldStudentId.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldStudentId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));
        jPanel1.add(jTextFieldStudentId);
        jTextFieldStudentId.setBounds(40, 125, 450, 30);

        jTextFieldStudentName.setText("Auto-loaded Student Name");
        jTextFieldStudentName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextFieldStudentName.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldStudentName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));
        jPanel1.add(jTextFieldStudentName);
        jTextFieldStudentName.setBounds(40, 195, 450, 30);

        jComboBoxCourse.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(jComboBoxCourse);
        jComboBoxCourse.setBounds(40, 265, 450, 30);

        jComboBoxSemester.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(jComboBoxSemester);
        jComboBoxSemester.setBounds(40, 335, 450, 30);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonEnroll;
    private javax.swing.JComboBox jComboBoxCourse;
    private javax.swing.JComboBox jComboBoxSemester;
    private javax.swing.JLabel jLabelCourse;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JLabel jLabelSemester;
    private javax.swing.JLabel jLabelStudentId;
    private javax.swing.JLabel jLabelStudentName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldStudentId;
    private javax.swing.JTextField jTextFieldStudentName;
    // End of variables declaration//GEN-END:variables

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(530, 570);
    }
}