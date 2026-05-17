package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Logout
extends JFrame {
    private static final Logger logger = Logger.getLogger(Logout.class.getName());

    public Logout() {
        this.initComponents();
        this.addHoverEffects();
        try {
            this.setIconImage(new javax.swing.ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        } catch (Exception e) {
            // ignore
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelCard = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabelIcon = new javax.swing.JLabel();
        jLabelSub = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Logged Out");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(243, 227, 225));
        jPanel1.setLayout(null);

        jPanelCard.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanelCard.setLayout(null);

        jButton1.setText("Login Again");
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setBackground(new java.awt.Color(11, 27, 226));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setBorder(null);
        jButton1.setFocusPainted(false);
        jPanelCard.add(jButton1);
        jButton1.setBounds(125, 180, 200, 40);

        jButtonExit.setText("Exit Application");
        jButtonExit.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonExit.setForeground(new java.awt.Color(28, 39, 50));
        jButtonExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        jButtonExit.setFocusPainted(false);
        jPanelCard.add(jButtonExit);
        jButtonExit.setBounds(125, 235, 200, 35);

        jLabel1.setText("Logged Out Successfully");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(28, 39, 50));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelCard.add(jLabel1);
        jLabel1.setBounds(30, 100, 390, 30);

        jLabelIcon.setText("✓");
        jLabelIcon.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabelIcon.setForeground(new java.awt.Color(11, 27, 226));
        jLabelIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelCard.add(jLabelIcon);
        jLabelIcon.setBounds(190, 25, 70, 60);

        jLabelSub.setText("Thank you for using the system. Have a wonderful day!");
        jLabelSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabelSub.setForeground(new java.awt.Color(120, 120, 120));
        jLabelSub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelCard.add(jLabelSub);
        jLabelSub.setBounds(20, 135, 410, 20);

        jPanel1.add(jPanelCard);
        jPanelCard.setBounds(175, 90, 450, 320);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                                                                  
        this.dispose();
        new Login().setVisible(true);
    }                                                                                

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void addHoverEffects() {
        this.jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton1.setBackground(new Color(9, 21, 180));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jButton1.setBackground(new Color(11, 27, 226));
            }
        });
        
        this.jButtonExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButtonExit.setBackground(new Color(245, 245, 245));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jButtonExit.setBackground(new Color(255, 255, 255));
            }
        });
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new Logout().setVisible(true));
    }

                       

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelSub;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCard;
    // End of variables declaration//GEN-END:variables
}