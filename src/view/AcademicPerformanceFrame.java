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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AcademicPerformanceFrame extends javax.swing.JFrame {
    private String userRole = "Admin";

    public AcademicPerformanceFrame() {
        this("Admin");
    }

    public AcademicPerformanceFrame(String role) {
        this.userRole = role;
        initComponents();
        this.setResizable(false);
        try {
            this.setIconImage(new ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public String getUserRole() {
        return this.userRole;
    }

    public JButton getDashboardButton() { return this.jButton1; }
    public JButton getStudentsButton() { return this.jButton2; }
    public JButton getCoursesButton() { return this.jButton3; }
    public JButton getAttendanceButton() { return this.jButton4; }
    public JButton getAcademicPerformanceButton() { return this.jButton5; }
    public JButton getGradeComputationButton() { return this.jButton6; }
    public JButton getResultGenerationButton() { return this.jButton7; }
    public JButton getReportsExportButton() { return this.jButton8; }
    public JButton getProfileButton() { return this.jButton9; }
    public JButton getLogoutButton() { return this.jButton10; }
    public JComboBox<String> getTermComboBox() { return this.jComboBoxTerm; }
    public JComboBox<String> getCourseComboBox() { return this.jComboBoxCourse; }
    public JComboBox<String> getSectionComboBox() { return this.jComboBoxSection; }
    public JTable getMarksTable() { return this.jTableMarks; }
    public JButton getAddStudentButton() { return this.jButtonAddStudent; }
    public JButton getSaveMarksButton() { return this.jButtonSaveMarks; }
    public JButton getResetButton() { return this.jButtonReset; }
    public JButton getBackButton() { return this.jButtonBack; }
    public javax.swing.JLabel getTitleLabel() { return this.jLabel1; }



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
        jButtonAddStudent = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jButtonSaveMarks = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabelCourse = new javax.swing.JLabel();
        jLabelSection = new javax.swing.JLabel();
        jLabelTerm = new javax.swing.JLabel();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jTableMarks = new javax.swing.JTable();
        jComboBoxCourse = new javax.swing.JComboBox<>();
        jComboBoxSection = new javax.swing.JComboBox<>();
        jComboBoxTerm = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Academic Performance");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(28, 39, 50));
        jPanel2.setLayout(null);

        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("  Logout");
        jButton10.setBorder(null);
        jButton10.setContentAreaFilled(false);
        jButton10.setFocusPainted(false);
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton10);
        jButton10.setBounds(10, 440, 220, 32);

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("  Dashboard");
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton1);
        jButton1.setBounds(10, 80, 220, 32);

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("  Students Management");
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton2);
        jButton2.setBounds(10, 120, 220, 32);

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("  Courses Management");
        jButton3.setBorder(null);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton3);
        jButton3.setBounds(10, 160, 220, 32);

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("  Attendance Management");
        jButton4.setBorder(null);
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton4);
        jButton4.setBounds(10, 200, 220, 32);

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(11, 27, 226));
        jButton5.setText("  Academic Performance");
        jButton5.setBackground(new java.awt.Color(243, 227, 225));
        jButton5.setBorder(null);
        jButton5.setFocusPainted(false);
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton5);
        jButton5.setBounds(10, 240, 220, 32);

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("  Grade Computation");
        jButton6.setBorder(null);
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton6);
        jButton6.setBounds(10, 280, 220, 32);

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("  Result Generation");
        jButton7.setBorder(null);
        jButton7.setContentAreaFilled(false);
        jButton7.setFocusPainted(false);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton7);
        jButton7.setBounds(10, 320, 220, 32);

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("  Reports Export");
        jButton8.setBorder(null);
        jButton8.setContentAreaFilled(false);
        jButton8.setFocusPainted(false);
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton8);
        jButton8.setBounds(10, 360, 220, 32);

        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("  Profile");
        jButton9.setBorder(null);
        jButton9.setContentAreaFilled(false);
        jButton9.setFocusPainted(false);
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton9);
        jButton9.setBounds(10, 400, 220, 32);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  SMS");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 20, 200, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 240, 480);

        jPanel3.setBackground(new java.awt.Color(243, 227, 225));
        jPanel3.setLayout(null);

        jButtonAddStudent.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonAddStudent.setText("Add Student");
        jButtonAddStudent.setFocusPainted(false);
        jPanel3.add(jButtonAddStudent);
        jButtonAddStudent.setBounds(30, 410, 115, 35);

        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonBack.setText("Back");
        jButtonBack.setFocusPainted(false);
        jPanel3.add(jButtonBack);
        jButtonBack.setBounds(410, 410, 100, 35);

        jButtonReset.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonReset.setText("Reset");
        jButtonReset.setFocusPainted(false);
        jPanel3.add(jButtonReset);
        jButtonReset.setBounds(290, 410, 100, 35);

        jButtonSaveMarks.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonSaveMarks.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSaveMarks.setText("Save Marks");
        jButtonSaveMarks.setBackground(new java.awt.Color(11, 27, 226));
        jButtonSaveMarks.setFocusPainted(false);
        jPanel3.add(jButtonSaveMarks);
        jButtonSaveMarks.setBounds(160, 410, 115, 35);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Academic Performance");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(30, 15, 350, 35);

        jLabelCourse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelCourse.setText("Course");
        jPanel3.add(jLabelCourse);
        jLabelCourse.setBounds(190, 65, 100, 16);

        jLabelSection.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelSection.setText("Section");
        jPanel3.add(jLabelSection);
        jLabelSection.setBounds(370, 65, 100, 16);

        jLabelTerm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelTerm.setText("Term");
        jPanel3.add(jLabelTerm);
        jLabelTerm.setBounds(30, 65, 100, 16);

        jScrollPaneTable.setViewportView(jTableMarks);

        jPanel3.add(jScrollPaneTable);
        jScrollPaneTable.setBounds(30, 130, 480, 250);

        jPanel3.add(jComboBoxCourse);
        jComboBoxCourse.setBounds(190, 85, 160, 25);

        jPanel3.add(jComboBoxSection);
        jComboBoxSection.setBounds(370, 85, 140, 25);

        jPanel3.add(jComboBoxTerm);
        jComboBoxTerm.setBounds(30, 85, 140, 25);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(240, 0, 540, 480);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static class VectorIcon implements Icon {
        private final String type;
        private final int size;
        private final Color color;

        public VectorIcon(String type, int size, Color color) {
            this.type = type;
            this.size = size;
            this.color = color;
        }

        @Override
        public int getIconWidth() { return this.size; }
        @Override
        public int getIconHeight() { return this.size; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color shapeColor;
            Color circleBg;
            Graphics2D g2 = (Graphics2D) g.create();
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
                case "dashboard":
                    g2.drawLine(cx + shapeSize / 2, cy + 1, cx + 1, cy + shapeSize / 2);
                    g2.drawLine(cx + shapeSize / 2, cy + 1, cx + shapeSize - 1, cy + shapeSize / 2);
                    g2.drawLine(cx + 2, cy + shapeSize / 2, cx + 2, cy + shapeSize - 1);
                    g2.drawLine(cx + shapeSize - 2, cy + shapeSize / 2, cx + shapeSize - 2, cy + shapeSize - 1);
                    g2.drawLine(cx + 2, cy + shapeSize - 1, cx + shapeSize - 2, cy + shapeSize - 1);
                    break;
                case "students":
                    int hSize = (int) ((double) shapeSize * 0.4);
                    g2.drawOval(cx + shapeSize / 2 - hSize / 2, cy + 2, hSize, hSize);
                    g2.drawArc(cx + 1, cy + shapeSize / 2 + 1, shapeSize - 2, shapeSize / 2, 0, 180);
                    break;
                case "courses":
                    int bookH = shapeSize / 3;
                    g2.drawRoundRect(cx + 1, cy + 1, shapeSize - 2, bookH, 2, 2);
                    g2.drawRoundRect(cx + 2, cy + 1 + bookH, shapeSize - 4, bookH, 2, 2);
                    g2.drawRoundRect(cx + 1, cy + 1 + 2 * bookH, shapeSize - 2, bookH, 2, 2);
                    break;
                case "attendance":
                    g2.drawRoundRect(cx + 1, cy + 2, shapeSize - 2, shapeSize - 3, 2, 2);
                    g2.drawLine(cx + 1, cy + 2 + shapeSize / 3, cx + shapeSize - 1, cy + 2 + shapeSize / 3);
                    break;
                case "performance":
                    g2.drawOval(cx + 1, cy + 1, shapeSize - 2, shapeSize - 2);
                    g2.drawLine(cx + 3, cy + shapeSize - 3, cx + shapeSize / 2, cy + shapeSize / 2);
                    g2.drawLine(cx + shapeSize / 2, cy + shapeSize / 2, cx + shapeSize - 3, cy + 3);
                    break;
                case "grade":
                    g2.drawRoundRect(cx + 1, cy + 1, shapeSize - 2, (int) ((double) shapeSize * 0.7), 2, 2);
                    g2.drawLine(cx + shapeSize / 2, cy + (int) ((double) shapeSize * 0.7), cx + shapeSize / 2, cy + shapeSize - 1);
                    break;
                case "result":
                    g2.drawRoundRect(cx + 2, cy + 1, shapeSize - 4, shapeSize - 2, 2, 2);
                    break;
                case "reports":
                    g2.drawRoundRect(cx + 2, cy + 3, shapeSize - 4, shapeSize - 4, 2, 2);
                    break;
                case "profile":
                    int pHead = (int) ((double) shapeSize * 0.45);
                    g2.drawOval(cx + shapeSize / 2 - pHead / 2, cy + 1, pHead, pHead);
                    g2.drawArc(cx + 1, cy + shapeSize / 2 + 2, shapeSize - 2, shapeSize / 2, 0, 180);
                    break;
                case "logout":
                    g2.drawPolyline(new int[]{cx + shapeSize / 2, cx + 1, cx + 1, cx + shapeSize / 2}, new int[]{cy + 1, cy + 1, cy + shapeSize - 1, cy + shapeSize - 1}, 4);
                    int arrowY = cy + shapeSize / 2;
                    g2.drawLine(cx + 3, arrowY, cx + shapeSize - 1, arrowY);
                    break;
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
    private javax.swing.JButton jButtonAddStudent;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSaveMarks;
    private javax.swing.JComboBox<String> jComboBoxCourse;
    private javax.swing.JComboBox<String> jComboBoxSection;
    private javax.swing.JComboBox<String> jComboBoxTerm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCourse;
    private javax.swing.JLabel jLabelSection;
    private javax.swing.JLabel jLabelTerm;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JTable jTableMarks;
    // End of variables declaration//GEN-END:variables

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(780, 480);
    }
}
