package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddStudentFrame
extends JFrame {

    public AddStudentFrame() {
        this.initComponents();
        this.setResizable(true);
        this.jPanel1.setPreferredSize(new java.awt.Dimension(530, 570));
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(this.jPanel1);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.setContentPane(scrollPane);

        try {
            this.setIconImage(new javax.swing.ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jLabelAddress = new javax.swing.JLabel();
        jLabelCourse = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelFullName = new javax.swing.JLabel();
        jLabelGender = new javax.swing.JLabel();
        jLabelHeader = new javax.swing.JLabel();
        jLabelPhone = new javax.swing.JLabel();
        jLabelStudentId = new javax.swing.JLabel();
        jScrollPaneAddress = new javax.swing.JScrollPane();
        jTextAreaAddress = new javax.swing.JTextArea();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldFullName = new javax.swing.JTextField();
        jTextFieldPhone = new javax.swing.JTextField();
        jTextFieldStudentId = new javax.swing.JTextField();
        jComboBoxCourse = new javax.swing.JComboBox();
        jComboBoxGender = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Management System - Add Student");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(243, 227, 225));
        jPanel1.setLayout(null);

        jButtonBack.setText("Back");
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonBack.setFocusPainted(false);
        jPanel1.add(jButtonBack);
        jButtonBack.setBounds(350, 440, 140, 35);

        jButtonClear.setText("Clear");
        jButtonClear.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonClear.setFocusPainted(false);
        jPanel1.add(jButtonClear);
        jButtonClear.setBounds(200, 440, 130, 35);

        jButtonSave.setText("Save");
        jButtonSave.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonSave.setBackground(new java.awt.Color(11, 27, 226));
        jButtonSave.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSave.setFocusPainted(false);
        jPanel1.add(jButtonSave);
        jButtonSave.setBounds(40, 440, 140, 35);

        jLabelAddress.setText("Address");
        jLabelAddress.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelAddress);
        jLabelAddress.setBounds(30, 370, 130, 30);

        jLabelCourse.setText("Course");
        jLabelCourse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelCourse);
        jLabelCourse.setBounds(30, 290, 130, 30);

        jLabelEmail.setText("Email");
        jLabelEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelEmail);
        jLabelEmail.setBounds(30, 200, 130, 30);

        jLabelFullName.setText("Full Name");
        jLabelFullName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelFullName);
        jLabelFullName.setBounds(30, 155, 130, 30);

        jLabelGender.setText("Gender");
        jLabelGender.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelGender);
        jLabelGender.setBounds(30, 330, 130, 30);

        jLabelHeader.setText("Add New Student");
        jLabelHeader.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabelHeader);
        jLabelHeader.setBounds(0, 30, 530, 35);

        jLabelPhone.setText("Phone Number");
        jLabelPhone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelPhone);
        jLabelPhone.setBounds(30, 245, 130, 30);

        jLabelStudentId.setText("Student ID");
        jLabelStudentId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelStudentId);
        jLabelStudentId.setBounds(30, 110, 130, 30);

        jTextAreaAddress.setColumns(20);
        jTextAreaAddress.setRows(5);
        jTextAreaAddress.setText("Enter Address");
        jTextAreaAddress.setForeground(new java.awt.Color(128, 128, 128));
        jScrollPaneAddress.setViewportView(jTextAreaAddress);

        jPanel1.add(jScrollPaneAddress);
        jScrollPaneAddress.setBounds(180, 370, 310, 50);

        jTextFieldEmail.setText("Enter Email Address");
        jTextFieldEmail.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextFieldEmail);
        jTextFieldEmail.setBounds(180, 200, 310, 30);

        jTextFieldFullName.setText("Enter Full Name");
        jTextFieldFullName.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldFullName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextFieldFullName);
        jTextFieldFullName.setBounds(180, 155, 310, 30);

        jTextFieldPhone.setText("Enter Phone Number");
        jTextFieldPhone.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldPhone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextFieldPhone);
        jTextFieldPhone.setBounds(180, 245, 310, 30);

        jTextFieldStudentId.setText("Enter Student ID");
        jTextFieldStudentId.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldStudentId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextFieldStudentId);
        jTextFieldStudentId.setBounds(180, 110, 310, 30);

        jComboBoxCourse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Course", "Computing", "Business", "Multimedia", "Networking" }));
        jComboBoxCourse.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jComboBoxCourse);
        jComboBoxCourse.setBounds(180, 290, 310, 30);

        jComboBoxGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Gender", "Male", "Female", "Other" }));
        jComboBoxGender.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jComboBoxGender);
        jComboBoxGender.setBounds(180, 330, 310, 30);

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

    public JTextField getStudentIdField() {
        return this.jTextFieldStudentId;
    }

    public JTextField getFullNameField() {
        return this.jTextFieldFullName;
    }

    public JTextField getEmailField() {
        return this.jTextFieldEmail;
    }

    public JTextField getPhoneField() {
        return this.jTextFieldPhone;
    }

    public JComboBox<String> getCourseComboBox() {
        return this.jComboBoxCourse;
    }

    public JComboBox<String> getGenderComboBox() {
        return this.jComboBoxGender;
    }

    public JTextArea getAddressArea() {
        return this.jTextAreaAddress;
    }

    public JScrollPane getAddressScrollPane() {
        return this.jScrollPaneAddress;
    }

    public JButton getSaveButton() {
        return this.jButtonSave;
    }

    public JButton getClearButton() {
        return this.jButtonClear;
    }

    public JButton getBackButton() {
        return this.jButtonBack;
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(530, 570);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JComboBox jComboBoxCourse;
    private javax.swing.JComboBox jComboBoxGender;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelCourse;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelFullName;
    private javax.swing.JLabel jLabelGender;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelStudentId;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPaneAddress;
    private javax.swing.JTextArea jTextAreaAddress;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFullName;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldStudentId;
    // End of variables declaration//GEN-END:variables
}