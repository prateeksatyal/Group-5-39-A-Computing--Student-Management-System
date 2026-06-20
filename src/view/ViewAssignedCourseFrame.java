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
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class ViewAssignedCourseFrame
extends JFrame {

    public ViewAssignedCourseFrame() {
        this.initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.jPanel1.setPreferredSize(new java.awt.Dimension(780, 480));
        this.setContentPane(this.jPanel1);
        try {
            this.setIconImage(new ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();
        jLabelSearch = new javax.swing.JLabel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelHeader = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAssigned = new javax.swing.JTable();
        jTextFieldSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - View Assigned Courses");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jButtonBack.setText("Back");
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonBack.setBackground(new java.awt.Color(40, 55, 70));
        jButtonBack.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBack.setFocusPainted(false);
        jPanel1.add(jButtonBack);
        jButtonBack.setBounds(650, 85, 100, 30);

        jButtonRefresh.setText("Refresh");
        jButtonRefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonRefresh.setBackground(new java.awt.Color(11, 27, 226));
        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setFocusPainted(false);
        jPanel1.add(jButtonRefresh);
        jButtonRefresh.setBounds(540, 85, 100, 30);

        jLabelSearch.setText("Search Course:");
        jLabelSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel1.add(jLabelSearch);
        jLabelSearch.setBounds(30, 85, 110, 30);

        jPanelHeader.setBackground(new java.awt.Color(243, 227, 225));
        jPanelHeader.setLayout(null);

        jLabelHeader.setText("Assigned Courses Directory");
        jLabelHeader.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(28, 39, 50));
        jPanelHeader.add(jLabelHeader);
        jLabelHeader.setBounds(30, 15, 400, 30);

        jPanel1.add(jPanelHeader);
        jPanelHeader.setBounds(0, 0, 780, 60);

        jScrollPane1.setViewportView(jTableAssigned);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 140, 720, 310);

        jTextFieldSearch.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextFieldSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        jPanel1.add(jTextFieldSearch);
        jTextFieldSearch.setBounds(150, 85, 250, 30);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    public JButton getRefreshButton() {
        return this.jButtonRefresh;
    }

    public JButton getBackButton() {
        return this.jButtonBack;
    }

    public JTable getAssignedTable() {
        return this.jTableAssigned;
    }

    public JTextField getSearchField() {
        return this.jTextFieldSearch;
    }



                       

                       

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAssigned;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(780, 480);
    }
}