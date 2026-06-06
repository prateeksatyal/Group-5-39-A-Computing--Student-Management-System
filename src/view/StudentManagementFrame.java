package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class StudentManagementFrame
extends JFrame {

    public StudentManagementFrame() {
        this.initComponents();
        this.setupMenuIcons();
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
        jPanel2 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelCRUD = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jPanelCard1 = new javax.swing.JPanel();
        jLabelCardIcon = new javax.swing.JLabel();
        jLabelCardLabel = new javax.swing.JLabel();
        jLabelCardNum = new javax.swing.JLabel();
        jPanelSearch = new javax.swing.JPanel();
        jLabelSearchLabel = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonRefresh = new javax.swing.JButton();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jTableStudents = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Students");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(28, 39, 50));
        jPanel2.setLayout(null);

        jButton10.setText("  Logout");
        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setBorder(null);
        jButton10.setContentAreaFilled(false);
        jButton10.setFocusPainted(false);
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton10);
        jButton10.setBounds(10, 440, 220, 32);

        jButton1.setText("  Dashboard");
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton1);
        jButton1.setBounds(10, 80, 220, 32);

        jButton2.setText("  Students Management");
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setBackground(new java.awt.Color(243, 227, 225));
        jButton2.setForeground(new java.awt.Color(11, 27, 226));
        jButton2.setBorder(null);
        jButton2.setFocusPainted(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton2);
        jButton2.setBounds(10, 120, 220, 32);

        jButton3.setText("  Courses Management");
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setBorder(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton3);
        jButton3.setBounds(10, 160, 220, 32);

        jButton4.setText("  Attendance Management");
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setBorder(null);
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton4);
        jButton4.setBounds(10, 200, 220, 32);

        jButton5.setText("  Academic Performance");
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setBorder(null);
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusPainted(false);
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton5);
        jButton5.setBounds(10, 240, 220, 32);

        jButton6.setText("  Grade Computation");
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setBorder(null);
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton6);
        jButton6.setBounds(10, 280, 220, 32);

        jButton7.setText("  Result Generation");
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setBorder(null);
        jButton7.setContentAreaFilled(false);
        jButton7.setFocusPainted(false);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton7);
        jButton7.setBounds(10, 320, 220, 32);

        jButton8.setText("  Reports Export");
        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setBorder(null);
        jButton8.setContentAreaFilled(false);
        jButton8.setFocusPainted(false);
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton8);
        jButton8.setBounds(10, 360, 220, 32);

        jButton9.setText("  Profile");
        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setBorder(null);
        jButton9.setContentAreaFilled(false);
        jButton9.setFocusPainted(false);
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton9);
        jButton9.setBounds(10, 400, 220, 32);

        jLabel1.setText("  SMS");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 20, 200, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 240, 480);

        jPanel3.setBackground(new java.awt.Color(243, 227, 225));
        jPanel3.setLayout(null);

        jLabel2.setText("Students Management");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jPanel3.add(jLabel2);
        jLabel2.setBounds(30, 20, 350, 35);

        jPanelCRUD.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCRUD.setLayout(null);

        jButtonAdd.setText("Add Student");
        jButtonAdd.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonAdd.setBackground(new java.awt.Color(11, 27, 226));
        jButtonAdd.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd.setFocusPainted(false);
        jPanelCRUD.add(jButtonAdd);
        jButtonAdd.setBounds(30, 15, 115, 35);

        jButtonUpdate.setText("Update/Search");
        jButtonUpdate.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonUpdate.setBackground(new java.awt.Color(11, 27, 226));
        jButtonUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUpdate.setFocusPainted(false);
        jPanelCRUD.add(jButtonUpdate);
        jButtonUpdate.setBounds(160, 15, 125, 35);

        jButtonDelete.setText("Delete");
        jButtonDelete.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonDelete.setFocusPainted(false);
        jPanelCRUD.add(jButtonDelete);
        jButtonDelete.setBounds(300, 15, 80, 35);

        jButtonBack.setText("Back");
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonBack.setFocusPainted(false);
        jPanelCRUD.add(jButtonBack);
        jButtonBack.setBounds(395, 15, 70, 35);

        jPanel3.add(jPanelCRUD);
        jPanelCRUD.setBounds(30, 395, 480, 65);

        jPanelCard1.setBackground(new java.awt.Color(224, 242, 248));
        jPanelCard1.setLayout(null);
        jPanelCard1.add(jLabelCardIcon);
        jLabelCardIcon.setBounds(160, 15, 50, 50);

        jLabelCardLabel.setText("Total Enrolled Students");
        jLabelCardLabel.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jPanelCard1.add(jLabelCardLabel);
        jLabelCardLabel.setBounds(15, 45, 130, 20);

        jLabelCardNum.setText("0");
        jLabelCardNum.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jPanelCard1.add(jLabelCardNum);
        jLabelCardNum.setBounds(15, 10, 100, 30);

        jPanel3.add(jPanelCard1);
        jPanelCard1.setBounds(30, 80, 225, 80);

        jPanelSearch.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSearch.setLayout(null);

        jLabelSearchLabel.setText("Quick Search");
        jLabelSearchLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanelSearch.add(jLabelSearchLabel);
        jLabelSearchLabel.setBounds(15, 10, 100, 20);

        jTextFieldSearch.setText("Search Name or ID...");
        jTextFieldSearch.setForeground(new java.awt.Color(128, 128, 128));
        jTextFieldSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224)));
        jPanelSearch.add(jTextFieldSearch);
        jTextFieldSearch.setBounds(15, 35, 160, 30);

        jButtonRefresh.setText("Refresh");
        jButtonRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonRefresh.setBackground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setFocusPainted(false);
        jPanelSearch.add(jButtonRefresh);
        jButtonRefresh.setBounds(185, 35, 45, 30);

        jPanel3.add(jPanelSearch);
        jPanelSearch.setBounds(270, 80, 240, 80);

        jTableStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Student ID", "Full Name", "Course", "Email", "Phone"
            }
        ));
        jScrollPaneTable.setViewportView(jTableStudents);

        jPanel3.add(jScrollPaneTable);
        jScrollPaneTable.setBounds(30, 180, 480, 200);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(240, 0, 540, 480);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public JButton getDashboardButton() {
        return this.jButton1;
    }

    public JButton getStudentsButton() {
        return this.jButton2;
    }

    public JButton getCoursesButton() {
        return this.jButton3;
    }

    public JButton getAttendanceButton() {
        return this.jButton4;
    }

    public JButton getAcademicPerformanceButton() {
        return this.jButton5;
    }

    public JButton getGradeComputationButton() {
        return this.jButton6;
    }

    public JButton getResultGenerationButton() {
        return this.jButton7;
    }

    public JButton getReportsExportButton() {
        return this.jButton8;
    }

    public JButton getProfileButton() {
        return this.jButton9;
    }

    public JButton getLogoutButton() {
        return this.jButton10;
    }

    public JTextField getSearchField() {
        return this.jTextFieldSearch;
    }

    public JButton getRefreshButton() {
        return this.jButtonRefresh;
    }

    public JTable getStudentsTable() {
        return this.jTableStudents;
    }

    public JLabel getTotalStudentsLabel() {
        return this.jLabelCardNum;
    }

    public JButton getAddButton() {
        return this.jButtonAdd;
    }

    public JButton getUpdateButton() {
        return this.jButtonUpdate;
    }

    public JButton getDeleteButton() {
        return this.jButtonDelete;
    }

    public JButton getBackButton() {
        return this.jButtonBack;
    }

    private void setupMenuIcons() {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        this.jLabel1.setText("SMS");
        this.jLabel1.setIcon(new VectorIcon("hamburger", 20, whiteColor));
        this.jLabel1.setIconTextGap(12);
        this.jButton1.setText("Dashboard");
        this.jButton1.setIconTextGap(12);
        this.jButton2.setText("Students Management");
        this.jButton2.setIconTextGap(12);
        this.jButton3.setText("Courses Management");
        this.jButton3.setIconTextGap(12);
        this.jButton4.setText("Attendance Management");
        this.jButton4.setIconTextGap(12);
        this.jButton5.setText("Academic Performance");
        this.jButton5.setIconTextGap(12);
        this.jButton6.setText("Grade Computation");
        this.jButton6.setIconTextGap(12);
        this.jButton7.setText("Result Generation");
        this.jButton7.setIconTextGap(12);
        this.jButton8.setText("Reports Export");
        this.jButton8.setIconTextGap(12);
        this.jButton9.setText("Profile");
        this.jButton9.setIconTextGap(12);
        this.jButton10.setText("Logout");
        this.jButton10.setIconTextGap(12);
        this.setActiveMenuItem(this.jButton2);

        this.jLabelCardIcon.setText("");
        this.jLabelCardIcon.setIcon(new VectorIcon("students", 40, activeColor));
        this.jButtonRefresh.setIcon(new VectorIcon("reports", 20, activeColor));
    }

    public void setActiveMenuItem(JButton activeBtn) {
        Color whiteColor = Color.WHITE;
        Color activeColor = new Color(11, 27, 226);
        Color activeBg = new Color(243, 227, 225);
        Color normalColor = new Color(11, 27, 226);
        Color normalBg = new Color(224, 242, 248);
        JButton[] buttons = new JButton[]{this.jButton1, this.jButton2, this.jButton3, this.jButton4, this.jButton5, this.jButton6, this.jButton7, this.jButton8, this.jButton9, this.jButton10};
        String[] types = new String[]{"dashboard", "students", "courses", "attendance", "performance", "grade", "result", "reports", "profile", "logout"};
        for (int i = 0; i < buttons.length; ++i) {
            JButton btn = buttons[i];
            String type = types[i];
            if (btn == activeBtn) {
                btn.setBackground(activeBg);
                btn.setForeground(activeColor);
                btn.setContentAreaFilled(true);
                btn.setOpaque(true);
                btn.setIcon(new VectorIcon(type, 28, whiteColor));
                continue;
            }
            btn.setBackground(normalBg);
            btn.setForeground(normalColor);
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setIcon(new VectorIcon(type, 28, activeColor));
        }
    }

    private static class VectorIcon
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
                case "performance": {
                    g2.drawOval(cx + 1, cy + 1, shapeSize - 2, shapeSize - 2);
                    g2.drawLine(cx + 3, cy + shapeSize - 3, cx + shapeSize / 2, cy + shapeSize / 2);
                    g2.drawLine(cx + shapeSize / 2, cy + shapeSize / 2, cx + shapeSize - 3, cy + 3);
                    break;
                }
                case "grade": {
                    g2.drawRoundRect(cx + 1, cy + 1, shapeSize - 2, (int)((double)shapeSize * 0.7), 2, 2);
                    g2.drawLine(cx + shapeSize / 2, cy + (int)((double)shapeSize * 0.7), cx + shapeSize / 2, cy + shapeSize - 1);
                    g2.drawLine(cx + shapeSize / 3, cy + shapeSize - 1, cx + 2 * shapeSize / 3, cy + shapeSize - 1);
                    g2.drawLine(cx + shapeSize / 4, cy + shapeSize - 1, cx + 3 * shapeSize / 4, cy + shapeSize - 1);
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
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCardIcon;
    private javax.swing.JLabel jLabelCardLabel;
    private javax.swing.JLabel jLabelCardNum;
    private javax.swing.JLabel jLabelSearchLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCRUD;
    private javax.swing.JPanel jPanelCard1;
    private javax.swing.JPanel jPanelSearch;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JTable jTableStudents;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}