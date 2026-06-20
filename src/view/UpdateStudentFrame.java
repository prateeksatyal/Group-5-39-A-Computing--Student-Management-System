package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UpdateStudentFrame
extends JFrame {

    public UpdateStudentFrame() {
        this.initComponents();
        this.setResizable(true);

        this.jPanel1.setPreferredSize(new java.awt.Dimension(480, 520));
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
        jButtonDelete = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jButtonSearch = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jLabelAddress = new javax.swing.JLabel();
        jLabelCourse = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelFullName = new javax.swing.JLabel();
        jLabelGender = new javax.swing.JLabel();
        jLabelHeader = new javax.swing.JLabel();
        jLabelPhone = new javax.swing.JLabel();
        jLabelSearch = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAddress = new javax.swing.JTextArea();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldFullName = new javax.swing.JTextField();
        jTextFieldPhone = new javax.swing.JTextField();
        jTextFieldSearchId = new javax.swing.JTextField();
        jComboBoxCourse = new javax.swing.JComboBox();
        jComboBoxGender = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Management System - Update Student");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(243, 227, 225));
        jPanel1.setLayout(null);

        jButtonBack.setText("Back");
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonBack.setBackground(new java.awt.Color(40, 55, 70));
        jButtonBack.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBack.setFocusPainted(false);
        jPanel1.add(jButtonBack);
        jButtonBack.setBounds(345, 440, 120, 35);

        jButtonDelete.setText("Delete");
        jButtonDelete.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonDelete.setBackground(new java.awt.Color(220, 53, 69));
        jButtonDelete.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDelete.setFocusPainted(false);
        jPanel1.add(jButtonDelete);
        jButtonDelete.setBounds(125, 440, 95, 35);

        jButtonReset.setText("Reset");
        jButtonReset.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonReset.setFocusPainted(false);
        jPanel1.add(jButtonReset);
        jButtonReset.setBounds(235, 440, 95, 35);

        jButtonSearch.setText("Search");
        jButtonSearch.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonSearch.setBackground(new java.awt.Color(11, 27, 226));
        jButtonSearch.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSearch.setFocusPainted(false);
        jPanel1.add(jButtonSearch);
        jButtonSearch.setBounds(360, 110, 85, 30);

        jButtonUpdate.setText("Update");
        jButtonUpdate.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonUpdate.setBackground(new java.awt.Color(11, 27, 226));
        jButtonUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUpdate.setFocusPainted(false);
        jPanel1.add(jButtonUpdate);
        jButtonUpdate.setBounds(15, 440, 95, 35);

        jLabelAddress.setText("Address");
        jLabelAddress.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelAddress);
        jLabelAddress.setBounds(30, 355, 100, 30);

        jLabelCourse.setText("Course");
        jLabelCourse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelCourse);
        jLabelCourse.setBounds(30, 275, 100, 30);

        jLabelEmail.setText("Email");
        jLabelEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelEmail);
        jLabelEmail.setBounds(30, 195, 100, 30);

        jLabelFullName.setText("Full Name");
        jLabelFullName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelFullName);
        jLabelFullName.setBounds(30, 155, 100, 30);

        jLabelGender.setText("Gender");
        jLabelGender.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelGender);
        jLabelGender.setBounds(30, 315, 100, 30);

        jLabelHeader.setText("Update Student Information");
        jLabelHeader.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabelHeader);
        jLabelHeader.setBounds(0, 30, 480, 35);

        jLabelPhone.setText("Phone");
        jLabelPhone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelPhone);
        jLabelPhone.setBounds(30, 235, 100, 30);

        jLabelSearch.setText("Student ID");
        jLabelSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelSearch);
        jLabelSearch.setBounds(30, 110, 100, 30);

        jTextAreaAddress.setColumns(20);
        jTextAreaAddress.setRows(5);
        jTextAreaAddress.setText("Enter Address");
        jTextAreaAddress.setForeground(new java.awt.Color(128, 128, 128));
        jScrollPane1.setViewportView(jTextAreaAddress);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(130, 355, 315, 60);

        jTextFieldEmail.setText("Enter Email Address");
        jTextFieldEmail.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextFieldEmail);
        jTextFieldEmail.setBounds(130, 195, 315, 30);

        jTextFieldFullName.setText("Enter Full Name");
        jTextFieldFullName.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldFullName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextFieldFullName);
        jTextFieldFullName.setBounds(130, 155, 315, 30);

        jTextFieldPhone.setText("Enter Phone Number");
        jTextFieldPhone.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldPhone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextFieldPhone);
        jTextFieldPhone.setBounds(130, 235, 315, 30);

        jTextFieldSearchId.setText("Enter Student ID to Search...");
        jTextFieldSearchId.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldSearchId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextFieldSearchId);
        jTextFieldSearchId.setBounds(130, 110, 215, 30);

        jComboBoxCourse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Course", "Computing", "Business", "Multimedia", "Networking" }));
        jComboBoxCourse.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jComboBoxCourse);
        jComboBoxCourse.setBounds(130, 275, 315, 30);

        jComboBoxGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Gender", "Male", "Female", "Other" }));
        jComboBoxGender.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jComboBoxGender);
        jComboBoxGender.setBounds(130, 315, 315, 30);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public JTextField getSearchIdField() {
        return this.jTextFieldSearchId;
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
        return this.jScrollPane1;
    }

    public JButton getSearchButton() {
        return this.jButtonSearch;
    }

    public JButton getUpdateButton() {
        return this.jButtonUpdate;
    }

    public JButton getDeleteButton() {
        return this.jButtonDelete;
    }

    public JButton getResetButton() {
        return this.jButtonReset;
    }

    public JButton getBackButton() {
        return this.jButtonBack;
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(480, 520);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox jComboBoxCourse;
    private javax.swing.JComboBox jComboBoxGender;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelCourse;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelFullName;
    private javax.swing.JLabel jLabelGender;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaAddress;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFullName;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldSearchId;
    // End of variables declaration//GEN-END:variables
}