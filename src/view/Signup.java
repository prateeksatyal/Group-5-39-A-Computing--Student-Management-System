/*
 * Decompiled with CFR 0.152.
 */
package view;

import controller.SignupController;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Signup
extends JFrame {
    private javax.swing.JTextField dummyImagePathField;

    public Signup() {
        this.initComponents();
        this.jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Role", "Student", "Admin", "Teacher" }));
        this.dummyImagePathField = new JTextField("");
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowOpened(WindowEvent e) {
                Signup.this.getContentPane().setFocusable(true);
                Signup.this.getContentPane().requestFocusInWindow();
            }
        });

        // Register focus listeners to invoke the view's focus design methods
        this.jTextField1.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                jTextField1FocusGained(e);
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                jTextField1FocusLost(e);
            }
        });

        this.jTextField2.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                jTextField2FocusGained(e);
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                jTextField2FocusLost(e);
            }
        });

        this.jPasswordField1.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                jPasswordField1FocusGained(e);
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                jPasswordField1FocusLost(e);
            }
        });

        this.jPasswordField2.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                jPasswordField2FocusGained(e);
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                jPasswordField2FocusLost(e);
            }
        });

        // Set initial echo char for password fields placeholders to plain text
        this.jPasswordField1.setEchoChar('\u0000');
        this.jPasswordField2.setEchoChar('\u0000');

        // Set BasicButtonUI for Register and Cancel buttons to render solid background under Nimbus
        this.jButton1.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.jButton2.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        this.addSignupButtonEffects();
    }

    private void addSignupButtonEffects() {
        this.jButton1.setCursor(new Cursor(12));
        this.jButton1.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                Signup.this.jButton1.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                Signup.this.jButton1.setBorder(null);
            }
        });
        this.jButton1.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent e) {
                Signup.this.jButton1.setBackground(new Color(5, 18, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Signup.this.jButton1.setBackground(new Color(11, 27, 226));
            }
        });
        this.jButton2.setCursor(new Cursor(12));
        this.jButton2.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                Signup.this.jButton2.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                Signup.this.jButton2.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
            }
        });
        this.jButton2.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent e) {
                Signup.this.jButton2.setBackground(new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Signup.this.jButton2.setBackground(new Color(255, 255, 255));
            }
        });
        this.jButton3.setCursor(new Cursor(12));
        this.jButton3.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                Signup.this.jButton3.setForeground(new Color(11, 27, 226));
            }

            @Override
            public void focusLost(FocusEvent e) {
                Signup.this.jButton3.setForeground(new Color(0, 0, 0));
            }
        });
        this.jButton3.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent e) {
                Signup.this.jButton3.setForeground(new Color(11, 27, 226));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!Signup.this.jButton3.isFocusOwner()) {
                    Signup.this.jButton3.setForeground(new Color(0, 0, 0));
                }
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create an Account");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(243, 227, 225));
        jPanel1.setLayout(null);

        jButton1.setText("Register");
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setBackground(new java.awt.Color(11, 27, 226));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setFocusPainted(false);
        jPanel1.add(jButton1);
        jButton1.setBounds(180, 335, 140, 30);

        jButton2.setText("Cancel");
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton2.setFocusPainted(false);
        jPanel1.add(jButton2);
        jButton2.setBounds(360, 335, 140, 30);

        jButton3.setText("Login");
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setBorder(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jPanel1.add(jButton3);
        jButton3.setBounds(380, 380, 45, 30);

        jLabel1.setText("Create an Account");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 20, 680, 30);

        jLabel2.setText("Fill in the details to register");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 55, 680, 20);

        jLabel3.setText("Full Name");
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(80, 100, 160, 30);

        jLabel4.setText("Email");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(80, 145, 160, 30);

        jLabel5.setText("Password");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(80, 190, 160, 30);

        jLabel6.setText("Confirm Password");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(80, 235, 160, 30);

        jLabel7.setText("Role");
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jPanel1.add(jLabel7);
        jLabel7.setBounds(80, 280, 160, 30);

        jLabel8.setText("Already have an account?");
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(210, 380, 165, 30);

        jPasswordField1.setText("Enter password");
        jPasswordField1.setForeground(new java.awt.Color(128, 128, 128));
        jPasswordField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jPasswordField1);
        jPasswordField1.setBounds(280, 190, 320, 30);

        jPasswordField2.setText("Confirm Password");
        jPasswordField2.setForeground(new java.awt.Color(128, 128, 128));
        jPasswordField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jPasswordField2);
        jPasswordField2.setBounds(280, 235, 320, 30);

        jTextField1.setText("Enter full name");
        jTextField1.setForeground(new java.awt.Color(128, 128, 128));
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextField1);
        jTextField1.setBounds(280, 100, 320, 30);

        jTextField2.setText("Enter email");
        jTextField2.setForeground(new java.awt.Color(128, 128, 128));
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jTextField2);
        jTextField2.setBounds(280, 145, 320, 30);

        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(280, 280, 320, 30);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {                                                                                
        this.jTextField1.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
        if (this.jTextField1.getText().equals("Enter full name")) {
            this.jTextField1.setText("");
            this.jTextField1.setForeground(new Color(0, 0, 0));
        }
    }                                                                              

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {                                                                            
        this.jTextField1.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
        if (this.jTextField1.getText().isEmpty()) {
            this.jTextField1.setText("Enter full name");
            this.jTextField1.setForeground(new Color(128, 128, 128));
        }
    }                                                                          

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {                                                                                
        this.jTextField2.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
        if (this.jTextField2.getText().equals("Enter email")) {
            this.jTextField2.setText("");
            this.jTextField2.setForeground(new Color(0, 0, 0));
        }
    }                                                                              

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {                                                                            
        this.jTextField2.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
        if (this.jTextField2.getText().isEmpty()) {
            this.jTextField2.setText("Enter email");
            this.jTextField2.setForeground(new Color(128, 128, 128));
        }
    }                                                                          

    private void jPasswordField1FocusGained(java.awt.event.FocusEvent evt) {                                                                                        
        this.jPasswordField1.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
        String pass = new String(this.jPasswordField1.getPassword());
        if (pass.equals("Enter password")) {
            this.jPasswordField1.setText("");
            this.jPasswordField1.setEchoChar('\u2022');
            this.jPasswordField1.setForeground(new Color(0, 0, 0));
        }
    }                                                                                      

    private void jPasswordField1FocusLost(java.awt.event.FocusEvent evt) {                                                                                    
        this.jPasswordField1.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
        String pass = new String(this.jPasswordField1.getPassword());
        if (pass.isEmpty()) {
            this.jPasswordField1.setText("Enter password");
            this.jPasswordField1.setEchoChar('\u0000');
            this.jPasswordField1.setForeground(new Color(128, 128, 128));
        }
    }                                                                                  

    private void jPasswordField2FocusGained(java.awt.event.FocusEvent evt) {                                                                                        
        this.jPasswordField2.setBorder(BorderFactory.createLineBorder(new Color(11, 27, 226), 2));
        String pass = new String(this.jPasswordField2.getPassword());
        if (pass.equals("Confirm Password")) {
            this.jPasswordField2.setText("");
            this.jPasswordField2.setEchoChar('\u2022');
            this.jPasswordField2.setForeground(new Color(0, 0, 0));
        }
    }                                                                                      

    private void jPasswordField2FocusLost(java.awt.event.FocusEvent evt) {                                                                                    
        this.jPasswordField2.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
        String pass = new String(this.jPasswordField2.getPassword());
        if (pass.isEmpty()) {
            this.jPasswordField2.setText("Confirm Password");
            this.jPasswordField2.setEchoChar('\u0000');
            this.jPasswordField2.setForeground(new Color(128, 128, 128));
        }
    }                                                                                  

    public JTextField getUsernameField() {
        return this.jTextField1;
    }

    public JTextField getEmailField() {
        return this.jTextField2;
    }

    public JPasswordField getPasswordField() {
        return this.jPasswordField1;
    }

    public JPasswordField getConfirmPasswordField() {
        return this.jPasswordField2;
    }

    public JComboBox<String> getRoleComboBox() {
        return this.jComboBox1;
    }

    public JTextField getImagePathField() {
        return this.dummyImagePathField;
    }

    public JButton getSignUpButton() {
        return this.jButton1;
    }

    public JButton getCancelButton() {
        return this.jButton2;
    }

    public JButton getLoginButton() {
        return this.jButton3;
    }

    public void addAddUserListener(ActionListener listener) {
        this.jButton1.addActionListener(listener);
    }

    public void addLoginListener(ActionListener listener) {
        this.jButton3.addActionListener(listener);
    }

    public void addBrowseListener(ActionListener listener) {
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(680, 480);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> {
            Signup signupView = new Signup();
            SignupController controller = new SignupController(signupView);
            controller.open();
        });
    }

                       

                       

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}