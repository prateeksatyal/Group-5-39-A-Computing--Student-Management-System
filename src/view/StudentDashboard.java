/*
 * Decompiled with CFR 0.152. Logic moved to StudentDashboardController per MVC.
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StudentDashboard extends JFrame {

    public StudentDashboard() {
        this.initComponents();
        this.setResizable(true);
        this.jPanel1.setPreferredSize(new java.awt.Dimension(780, 480));
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

    // ── Public Getters (used by StudentDashboardController) ─────────────────

    public JButton getDashboardButton()       { return jButton1; }
    public JButton getViewProfileButton()     { return jButton2; }
    public JButton getAttendanceButton()      { return jButton3; }
    public JButton getEnrolledCoursesButton() { return jButton4; }
    public JButton getViewResultsButton()     { return jButton5; }
    public JButton getDownloadResultButton()  { return jButton6; }
    public JButton getLogoutButton()          { return jButton7; }

    /** Welcome label — updated by controller with real student name */
    public JLabel getWelcomeLabel()    { return jLabel2; }
    /** Attendance rate card num — updated by controller */
    public JLabel getCard2NumLabel()   { return jLabelCard2Num; }
    /** Enrolled courses count card — updated by controller */
    public JLabel getCard3NumLabel()   { return jLabelCard3Num; }


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
        jPanelRecent = new javax.swing.JPanel();
        jLabelLog1 = new javax.swing.JLabel();
        jLabelLog2 = new javax.swing.JLabel();
        jLabelLog3 = new javax.swing.JLabel();
        jLabelRecentTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Student Dashboard");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(28, 39, 50));
        jPanel2.setLayout(null);

        jButton1.setText("  Dashboard");
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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

        jButton3.setText("  Attendance Summary");
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setBorder(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton3);
        jButton3.setBounds(10, 160, 220, 32);

        jButton4.setText("  Enrolled Courses");
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setBorder(null);
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton4);
        jButton4.setBounds(10, 200, 220, 32);

        jButton5.setText("  View Results");
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setBorder(null);
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusPainted(false);
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton5);
        jButton5.setBounds(10, 240, 220, 32);

        jButton6.setText("  Download Result");
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setBorder(null);
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton6);
        jButton6.setBounds(10, 280, 220, 32);

        jButton7.setText("  Logout");
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setBorder(null);
        jButton7.setContentAreaFilled(false);
        jButton7.setFocusPainted(false);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton7);
        jButton7.setBounds(10, 320, 220, 32);

        jLabel1.setText("  SMS");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 20, 200, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 240, 480);

        jPanel3.setBackground(new java.awt.Color(243, 227, 225));
        jPanel3.setLayout(null);

        jLabel2.setText("Welcome, Student!");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jPanel3.add(jLabel2);
        jLabel2.setBounds(30, 20, 350, 35);

        jPanelCard1.setBackground(new java.awt.Color(224, 242, 248));
        jPanelCard1.setLayout(null);
        jPanelCard1.add(jLabelCard1Icon);
        jLabelCard1Icon.setBounds(160, 20, 50, 50);

        jLabelCard1Label.setText("Active Enrollment");
        jLabelCard1Label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanelCard1.add(jLabelCard1Label);
        jLabelCard1Label.setBounds(15, 55, 130, 20);

        jLabelCard1Num.setText("Student Info");
        jLabelCard1Num.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jPanelCard1.add(jLabelCard1Num);
        jLabelCard1Num.setBounds(15, 15, 130, 30);

        jPanel3.add(jPanelCard1);
        jPanelCard1.setBounds(30, 80, 225, 100);

        jPanelCard2.setBackground(new java.awt.Color(230, 248, 235));
        jPanelCard2.setLayout(null);
        jPanelCard2.add(jLabelCard2Icon);
        jLabelCard2Icon.setBounds(160, 20, 50, 50);

        jLabelCard2Label.setText("Attendance Rate");
        jLabelCard2Label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanelCard2.add(jLabelCard2Label);
        jLabelCard2Label.setBounds(15, 55, 130, 20);

        jLabelCard2Num.setText("95%");
        jLabelCard2Num.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jPanelCard2.add(jLabelCard2Num);
        jLabelCard2Num.setBounds(15, 15, 100, 30);

        jPanel3.add(jPanelCard2);
        jPanelCard2.setBounds(285, 80, 225, 100);

        jPanelCard3.setBackground(new java.awt.Color(254, 243, 225));
        jPanelCard3.setLayout(null);
        jPanelCard3.add(jLabelCard3Icon);
        jLabelCard3Icon.setBounds(160, 20, 50, 50);

        jLabelCard3Label.setText("Enrolled Courses");
        jLabelCard3Label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanelCard3.add(jLabelCard3Label);
        jLabelCard3Label.setBounds(15, 55, 130, 20);

        jLabelCard3Num.setText("5");
        jLabelCard3Num.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jPanelCard3.add(jLabelCard3Num);
        jLabelCard3Num.setBounds(15, 15, 100, 30);

        jPanel3.add(jPanelCard3);
        jPanelCard3.setBounds(30, 190, 225, 100);

        jPanelCard4.setBackground(new java.awt.Color(253, 235, 235));
        jPanelCard4.setLayout(null);
        jPanelCard4.add(jLabelCard4Icon);
        jLabelCard4Icon.setBounds(160, 20, 50, 50);

        jLabelCard4Label.setText("Latest Term GPA");
        jLabelCard4Label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanelCard4.add(jLabelCard4Label);
        jLabelCard4Label.setBounds(15, 55, 130, 20);

        jLabelCard4Num.setText("A / 4.0");
        jLabelCard4Num.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jPanelCard4.add(jLabelCard4Num);
        jLabelCard4Num.setBounds(15, 15, 100, 30);

        jPanel3.add(jPanelCard4);
        jPanelCard4.setBounds(285, 190, 225, 100);

        jPanelRecent.setBackground(new java.awt.Color(255, 255, 255));
        jPanelRecent.setLayout(null);

        jLabelLog1.setText("• Term End Examination schedule has been posted.");
        jPanelRecent.add(jLabelLog1);
        jLabelLog1.setBounds(15, 35, 450, 20);

        jLabelLog2.setText("• Attendance update: CS101 Lecture 5 attendance uploaded.");
        jPanelRecent.add(jLabelLog2);
        jLabelLog2.setBounds(15, 60, 450, 20);

        jLabelLog3.setText("• Grade Report: CS101 final marks released by Prof. Davis.");
        jPanelRecent.add(jLabelLog3);
        jLabelLog3.setBounds(15, 85, 450, 20);

        jLabelRecentTitle.setText("Announcements & Notifications");
        jLabelRecentTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelRecent.add(jLabelRecentTitle);
        jLabelRecentTitle.setBounds(15, 10, 400, 20);

        jPanel3.add(jPanelRecent);
        jPanelRecent.setBounds(30, 300, 480, 150);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(240, 0, 540, 480);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 780, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 480, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // ── Icon and title getters for controller styling ───────
    public JLabel getCard1IconLabel() { return this.jLabelCard1Icon; }
    public JLabel getCard2IconLabel() { return this.jLabelCard2Icon; }
    public JLabel getCard3IconLabel() { return this.jLabelCard3Icon; }
    public JLabel getCard4IconLabel() { return this.jLabelCard4Icon; }
    public JLabel getTitleLabel() { return this.jLabel1; }



    public static class VectorIcon
    implements Icon {
        private final String type;
        private final int size;
        private final Color color;

        public VectorIcon(String type, int size, Color color) {
            this.type = type;
            this.size = size;
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return this.size;
        }

        @Override
        public int getIconHeight() {
            return this.size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color shapeColor;
            Color circleBg;
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (this.type.equalsIgnoreCase("hamburger")) {
                g2.setColor(this.color);
                g2.setStroke(new BasicStroke(1.8f, 1, 1));
                int gap = this.size / 4;
                g2.drawLine(x + 2, y + this.size / 2 - gap, x + this.size - 2, y + this.size / 2 - gap);
                g2.drawLine(x + 2, y + this.size / 2, x + this.size - 2, y + this.size / 2);
                g2.drawLine(x + 2, y + this.size / 2 + gap, x + this.size - 2, y + this.size / 2 + gap);
                g2.dispose();
                return;
            }
            if (this.color.equals(Color.WHITE)) {
                circleBg = new Color(11, 27, 226);
                shapeColor = Color.WHITE;
            } else {
                circleBg = Color.WHITE;
                shapeColor = new Color(11, 27, 226);
            }
            g2.setColor(circleBg);
            g2.fillOval(x, y, this.size, this.size);
            int shapeSize = this.size / 2;
            int offset = this.size / 4;
            int cx = x + offset;
            int cy = y + offset;
            g2.setColor(shapeColor);
            g2.setStroke(new BasicStroke(1.5f, 1, 1));
            switch (this.type.toLowerCase()) {
                case "dashboard": {
                    g2.drawLine(cx + shapeSize / 2, cy + 1, cx + 1, cy + shapeSize / 2);
                    g2.drawLine(cx + shapeSize / 2, cy + 1, cx + shapeSize - 1, cy + shapeSize / 2);
                    g2.drawLine(cx + 2, cy + shapeSize / 2, cx + 2, cy + shapeSize - 1);
                    g2.drawLine(cx + shapeSize - 2, cy + shapeSize / 2, cx + shapeSize - 2, cy + shapeSize - 1);
                    g2.drawLine(cx + 2, cy + shapeSize - 1, cx + shapeSize - 2, cy + shapeSize - 1);
                    g2.drawRect(cx + shapeSize / 2 - 1, cy + shapeSize - 3, 2, 3);
                    break;
                }
                case "students": {
                    int hSize = (int)((double)shapeSize * 0.4);
                    g2.drawOval(cx + shapeSize / 2 - hSize / 2, cy + 2, hSize, hSize);
                    g2.drawArc(cx + 1, cy + shapeSize / 2 + 1, shapeSize - 2, shapeSize / 2, 0, 180);
                    int capY = cy + 1;
                    int capW = (int)((double)shapeSize * 0.5);
                    int capH = (int)((double)shapeSize * 0.2);
                    int[] capXPoints = new int[]{cx + shapeSize / 2, cx + shapeSize / 2 - capW / 2, cx + shapeSize / 2, cx + shapeSize / 2 + capW / 2};
                    int[] capYPoints = new int[]{capY, capY + capH / 2, capY + capH, capY + capH / 2};
                    g2.fillPolygon(capXPoints, capYPoints, 4);
                    break;
                }
                case "courses": {
                    int bookH = shapeSize / 3;
                    g2.drawRoundRect(cx + 1, cy + 1, shapeSize - 2, bookH, 2, 2);
                    g2.drawRoundRect(cx + 2, cy + 1 + bookH, shapeSize - 4, bookH, 2, 2);
                    g2.drawRoundRect(cx + 1, cy + 1 + 2 * bookH, shapeSize - 2, bookH, 2, 2);
                    break;
                }
                case "attendance": {
                    g2.drawRoundRect(cx + 1, cy + 2, shapeSize - 2, shapeSize - 3, 2, 2);
                    g2.drawLine(cx + 1, cy + 2 + shapeSize / 3, cx + shapeSize - 1, cy + 2 + shapeSize / 3);
                    g2.fillOval(cx + 3, cy + shapeSize - 3, 1, 1);
                    g2.fillOval(cx + shapeSize / 2, cy + shapeSize - 3, 1, 1);
                    g2.fillOval(cx + shapeSize - 4, cy + shapeSize - 3, 1, 1);
                    break;
                }
                case "grade": {
                    g2.drawRoundRect(cx + 1, cy + 1, shapeSize - 2, (int)((double)shapeSize * 0.7), 2, 2);
                    g2.drawLine(cx + shapeSize / 2, cy + (int)((double)shapeSize * 0.7), cx + shapeSize / 2, cy + shapeSize - 1);
                    g2.drawLine(cx + shapeSize / 3, cy + shapeSize - 1, cx + 2 * shapeSize / 3, cy + shapeSize - 1);
                    break;
                }
                case "result": {
                    g2.drawRoundRect(cx + 2, cy + 1, shapeSize - 4, shapeSize - 2, 2, 2);
                    g2.drawLine(cx + 4, cy + 4, cx + shapeSize - 4, cy + 4);
                    g2.drawLine(cx + 4, cy + 7, cx + shapeSize - 4, cy + 7);
                    break;
                }
                case "reports": {
                    g2.drawRoundRect(cx + 2, cy + 3, shapeSize - 4, shapeSize - 4, 2, 2);
                    g2.fillRect(cx + shapeSize / 2 - 2, cy + 1, 4, 2);
                    g2.drawLine(cx + 4, cy + 6, cx + shapeSize - 4, cy + 6);
                    break;
                }
                case "profile": {
                    int pHead = (int)((double)shapeSize * 0.45);
                    g2.drawOval(cx + shapeSize / 2 - pHead / 2, cy + 1, pHead, pHead);
                    g2.drawArc(cx + 1, cy + shapeSize / 2 + 2, shapeSize - 2, shapeSize / 2, 0, 180);
                    break;
                }
                case "logout": {
                    g2.drawPolyline(new int[]{cx + shapeSize / 2, cx + 1, cx + 1, cx + shapeSize / 2}, new int[]{cy + 1, cy + 1, cy + shapeSize - 1, cy + shapeSize - 1}, 4);
                    int arrowY = cy + shapeSize / 2;
                    g2.drawLine(cx + 3, arrowY, cx + shapeSize - 1, arrowY);
                    g2.drawLine(cx + shapeSize - 3, arrowY - 2, cx + shapeSize - 1, arrowY);
                    g2.drawLine(cx + shapeSize - 3, arrowY + 2, cx + shapeSize - 1, arrowY);
                }
            }
            g2.dispose();
        }
    }

                       

                       

                       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
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
    private javax.swing.JLabel jLabelLog1;
    private javax.swing.JLabel jLabelLog2;
    private javax.swing.JLabel jLabelLog3;
    private javax.swing.JLabel jLabelRecentTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCard1;
    private javax.swing.JPanel jPanelCard2;
    private javax.swing.JPanel jPanelCard3;
    private javax.swing.JPanel jPanelCard4;
    private javax.swing.JPanel jPanelRecent;
    // End of variables declaration//GEN-END:variables

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(780, 480);
    }
}