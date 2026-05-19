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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ForgotPassword extends JFrame {

    public ForgotPassword() {
        this.initComponents();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                ForgotPassword.this.getContentPane().setFocusable(true);
                ForgotPassword.this.getContentPane().requestFocusInWindow();
            }
        });
        this.addButtonEffects();
    }

    private void addButtonEffects() {
        this.jButton1.setCursor(new Cursor(12));
        this.jButton1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jButton1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
            @Override
            public void focusLost(FocusEvent e) {
                jButton1.setBorder(null);
            }
        });
        this.jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton1.setBackground(new Color(5, 18, 180));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jButton1.setBackground(new Color(11, 27, 226));
            }
        });

        this.jButton2.setCursor(new Cursor(12));
        this.jButton2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jButton2.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
            }
            @Override
            public void focusLost(FocusEvent e) {
                jButton2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
        });
        this.jButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton2.setBackground(new Color(240, 240, 240));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jButton2.setBackground(Color.WHITE);
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reset Password");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(243, 227, 225));
        jPanel1.setLayout(null);

        jButton1.setText("Update");
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setBackground(new java.awt.Color(11, 27, 226));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setFocusPainted(false);
        jPanel1.add(jButton1);
        jButton1.setBounds(180, 335, 140, 30);

        jButton2.setText("Cancel");
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFocusPainted(false);
        jPanel1.add(jButton2);
        jButton2.setBounds(360, 335, 140, 30);

        jLabel1.setText("Reset Password");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 30, 680, 30);

        jLabel2.setText("Enter your details to securely reset your password");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 65, 680, 20);

        jLabel3.setText("Email");
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(80, 115, 160, 30);

        jLabel4.setText("Role");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(80, 165, 160, 30);

        jLabel5.setText("New Password");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(80, 215, 160, 30);

        jLabel6.setText("Confirm Password");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(80, 265, 160, 30);

        jTextField1.setText("Enter email");
        jTextField1.setForeground(new java.awt.Color(128, 128, 128));
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextField1);
        jTextField1.setBounds(280, 115, 320, 30);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Role", "Student", "Admin", "Teacher" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(280, 165, 320, 30);

        jPasswordField1.setText("New password");
        jPasswordField1.setForeground(new java.awt.Color(128, 128, 128));
        jPasswordField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jPasswordField1);
        jPasswordField1.setBounds(280, 215, 320, 30);

        jPasswordField2.setText("Confirm password");
        jPasswordField2.setForeground(new java.awt.Color(128, 128, 128));
        jPasswordField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jPasswordField2);
        jPasswordField2.setBounds(280, 265, 320, 30);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        // Wire Focus Listeners programmatically
        jTextField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jTextField1.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                if ("Enter email".equals(jTextField1.getText())) {
                    jTextField1.setText("");
                    jTextField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                jTextField1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                if (jTextField1.getText().trim().isEmpty()) {
                    jTextField1.setText("Enter email");
                    jTextField1.setForeground(new Color(128, 128, 128));
                }
            }
        });

        jPasswordField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jPasswordField1.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                String pass = new String(jPasswordField1.getPassword());
                if ("New password".equals(pass)) {
                    jPasswordField1.setText("");
                    jPasswordField1.setEchoChar('\u2022');
                    jPasswordField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                jPasswordField1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                String pass = new String(jPasswordField1.getPassword());
                if (pass.trim().isEmpty()) {
                    jPasswordField1.setText("New password");
                    jPasswordField1.setEchoChar('\u0000');
                    jPasswordField1.setForeground(new Color(128, 128, 128));
                }
            }
        });

        jPasswordField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jPasswordField2.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
                String pass = new String(jPasswordField2.getPassword());
                if ("Confirm password".equals(pass)) {
                    jPasswordField2.setText("");
                    jPasswordField2.setEchoChar('\u2022');
                    jPasswordField2.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                jPasswordField2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                String pass = new String(jPasswordField2.getPassword());
                if (pass.trim().isEmpty()) {
                    jPasswordField2.setText("Confirm password");
                    jPasswordField2.setEchoChar('\u0000');
                    jPasswordField2.setForeground(new Color(128, 128, 128));
                }
            }
        });

        // Setup clear-text placeholders initially
        jPasswordField1.setEchoChar('\u0000');
        jPasswordField2.setEchoChar('\u0000');

        // Apply BasicButtonUI for flat Nimbus rendering
        jButton1.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        jButton2.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public JTextField getEmailField() {
        return this.jTextField1;
    }

    public JComboBox<String> getRoleComboBox() {
        return this.jComboBox1;
    }

    public JPasswordField getPasswordField() {
        return this.jPasswordField1;
    }

    public JPasswordField getConfirmPasswordField() {
        return this.jPasswordField2;
    }

    public JButton getUpdateButton() {
        return this.jButton1;
    }

    public JButton getCancelButton() {
        return this.jButton2;
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(680, 480);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
