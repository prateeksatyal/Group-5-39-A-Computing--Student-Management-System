package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class AttendanceSummaryFrame extends javax.swing.JFrame {

    public AttendanceSummaryFrame() {
        initComponents();
        this.setResizable(true);
        setupMarkAttendancePanel();
        
        jPanel1.setPreferredSize(new java.awt.Dimension(780, 640));
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(jPanel1);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);

        try {
            this.setIconImage(new javax.swing.ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        } catch (Exception e) {
            // ignore
        }
    }

    public JButton getRefreshButton() { return refreshButton; }
    public JButton getExportButton() { return exportButton; }
    public JComboBox<String> getCourseComboBox() { return courseComboBox; }
    public JButton getBackButton() { return backButton; }
    public JButton getDashboardButton() { return jButton1; }
    public JButton getStudentsButton() { return jButton2; }
    public JButton getCoursesButton() { return jButton3; }
    public JButton getAttendanceButton() { return jButton4; }
    public JButton getAcademicPerformanceButton() { return jButton5; }
    public JButton getGradeComputationButton() { return jButton6; }
    public JButton getResultGenerationButton() { return jButton7; }
    public JButton getReportsExportButton() { return jButton8; }
    public JButton getProfileButton() { return jButton9; }
    public JButton getLogoutButton() { return jButton10; }
    public JTable getAttendanceTable() { return attendanceTable; }
    public JLabel getTotalStudentsLabel() { return totalStudentsLabel; }
    public JLabel getAvgAttendanceLabel() { return avgAttendanceLabel; }
    public javax.swing.JLabel getTitleLabel() { return jLabel1; }

    // Getters for Mark Attendance panel (Bug 1 fix)
    public JTextField getMarkStudentIdField()     { return markStudentIdField; }
    public JTextField getMarkStudentNameField()    { return markStudentNameField; }
    public JTextField getMarkTotalClassesField()   { return markTotalClassesField; }
    public JTextField getMarkAttendedField()       { return markAttendedField; }
    public JTextField getMarkDateField()           { return markDateField; }
    public JComboBox<String> getMarkCourseCombo()  { return markCourseCombo; }
    public JButton getMarkSaveButton()             { return markSaveButton; }
    public JButton getMarkClearButton()            { return markClearButton; }

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
        refreshButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabelCourse = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        attendanceTable = new javax.swing.JTable();
        courseComboBox = new javax.swing.JComboBox<>();
        jPanelCard1 = new javax.swing.JPanel();
        jLabelCard1Title = new javax.swing.JLabel();
        totalStudentsLabel = new javax.swing.JLabel();
        jPanelCard2 = new javax.swing.JPanel();
        jLabelCard2Title = new javax.swing.JLabel();
        avgAttendanceLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Management System - Attendance Summary");
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
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
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
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setBackground(new java.awt.Color(243, 227, 225));
        jButton4.setForeground(new java.awt.Color(11, 27, 226));
        jButton4.setBorder(null);
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

        refreshButton.setText("Refresh");
        refreshButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        refreshButton.setFocusPainted(false);
        jPanel3.add(refreshButton);
        refreshButton.setBounds(220, 85, 80, 25);

        exportButton.setText("Export CSV");
        exportButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        exportButton.setBackground(new java.awt.Color(11, 27, 226));
        exportButton.setForeground(new java.awt.Color(255, 255, 255));
        exportButton.setFocusPainted(false);
        jPanel3.add(exportButton);
        exportButton.setBounds(315, 85, 100, 25);

        backButton.setText("Back");
        backButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backButton.setFocusPainted(false);
        jPanel3.add(backButton);
        backButton.setBounds(430, 85, 80, 25);

        jLabel2.setText("Attendance Summary");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jPanel3.add(jLabel2);
        jLabel2.setBounds(30, 15, 350, 35);

        jLabelCourse.setText("Filter Course");
        jLabelCourse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanel3.add(jLabelCourse);
        jLabelCourse.setBounds(30, 65, 100, 16);

        // Bug 2 fix: expanded to all 8 AttendanceData columns
        attendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Student ID", "Student Name", "Course", "Total Classes", "Attended", "Attendance %", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(attendanceTable);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(30, 130, 480, 220);

        // Bug 3 fix: combo starts with placeholder only; populateCourseCombo() fills it from DB via controller
        courseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course" }));
        jPanel3.add(courseComboBox);
        courseComboBox.setBounds(30, 85, 175, 25);

        jPanelCard1.setBackground(new java.awt.Color(224, 242, 248));
        jPanelCard1.setLayout(null);

        jLabelCard1Title.setText("Total Students");
        jLabelCard1Title.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jPanelCard1.add(jLabelCard1Title);
        jLabelCard1Title.setBounds(15, 10, 150, 20);

        totalStudentsLabel.setText("0");
        totalStudentsLabel.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jPanelCard1.add(totalStudentsLabel);
        totalStudentsLabel.setBounds(15, 35, 150, 30);

        jPanel3.add(jPanelCard1);
        jPanelCard1.setBounds(30, 370, 225, 80);

        jPanelCard2.setBackground(new java.awt.Color(230, 248, 235));
        jPanelCard2.setLayout(null);

        jLabelCard2Title.setText("Average Attendance");
        jLabelCard2Title.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jPanelCard2.add(jLabelCard2Title);
        jLabelCard2Title.setBounds(15, 10, 150, 20);

        avgAttendanceLabel.setText("0.0%");
        avgAttendanceLabel.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jPanelCard2.add(avgAttendanceLabel);
        avgAttendanceLabel.setBounds(15, 35, 150, 30);

        jPanel3.add(jPanelCard2);
        jPanelCard2.setBounds(285, 370, 225, 80);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Bug 1 fix: Mark Attendance input panel injected into jPanel3 AFTER
     * initComponents(), so NetBeans guard blocks are never touched.
     * All new components are declared as plain private fields below.
     */
    private void setupMarkAttendancePanel() {
        // -- Mark Attendance sub-panel -- placed below the stat cards (y=460)
        javax.swing.JPanel markPanel = new javax.swing.JPanel(null);
        markPanel.setBackground(new java.awt.Color(255, 255, 255));
        markPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new java.awt.Color(11, 27, 226), 1),
            "Mark / Add Attendance Record",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12),
            new java.awt.Color(11, 27, 226)
        ));

        // Row 1 labels
        javax.swing.JLabel lbId   = label("Student ID:");
        javax.swing.JLabel lbName = label("Student Name:");
        javax.swing.JLabel lbCrs  = label("Course:");
        javax.swing.JLabel lbTot  = label("Total Classes:");
        javax.swing.JLabel lbAtt  = label("Attended:");
        javax.swing.JLabel lbDate = label("Date (YYYY-MM-DD):");

        markPanel.add(lbId);   lbId.setBounds(10, 25, 90, 20);
        markPanel.add(lbName); lbName.setBounds(180, 25, 100, 20);
        markPanel.add(lbCrs);  lbCrs.setBounds(10, 65, 90, 20);
        markPanel.add(lbTot);  lbTot.setBounds(180, 65, 100, 20);
        markPanel.add(lbAtt);  lbAtt.setBounds(280, 65, 80, 20);
        markPanel.add(lbDate); lbDate.setBounds(370, 65, 140, 20);

        // Row 1 fields
        markStudentIdField = new JTextField();
        markStudentIdField.setFont(new java.awt.Font("Segoe UI", 0, 13));
        markStudentIdField.setForeground(new java.awt.Color(128, 128, 128));
        markStudentIdField.setText("Enter Student ID");
        markPanel.add(markStudentIdField);
        markStudentIdField.setBounds(10, 45, 160, 25);

        markStudentNameField = new JTextField();
        markStudentNameField.setFont(new java.awt.Font("Segoe UI", 0, 13));
        markStudentNameField.setForeground(new java.awt.Color(128, 128, 128));
        markStudentNameField.setText("Auto-filled");
        markStudentNameField.setEditable(false);
        markPanel.add(markStudentNameField);
        markStudentNameField.setBounds(180, 45, 300, 25);

        // Row 2 fields
        markCourseCombo = new JComboBox<>();
        markCourseCombo.setFont(new java.awt.Font("Segoe UI", 0, 13));
        markPanel.add(markCourseCombo);
        markCourseCombo.setBounds(10, 85, 160, 25);

        markTotalClassesField = new JTextField();
        markTotalClassesField.setFont(new java.awt.Font("Segoe UI", 0, 13));
        markTotalClassesField.setForeground(new java.awt.Color(128, 128, 128));
        markTotalClassesField.setText("e.g. 30");
        markPanel.add(markTotalClassesField);
        markTotalClassesField.setBounds(180, 85, 90, 25);

        markAttendedField = new JTextField();
        markAttendedField.setFont(new java.awt.Font("Segoe UI", 0, 13));
        markAttendedField.setForeground(new java.awt.Color(128, 128, 128));
        markAttendedField.setText("e.g. 25");
        markPanel.add(markAttendedField);
        markAttendedField.setBounds(280, 85, 80, 25);

        markDateField = new JTextField();
        markDateField.setFont(new java.awt.Font("Segoe UI", 0, 13));
        markDateField.setForeground(new java.awt.Color(128, 128, 128));
        markDateField.setText(java.time.LocalDate.now().toString());
        markPanel.add(markDateField);
        markDateField.setBounds(370, 85, 100, 25);

        // Buttons
        markSaveButton = new JButton("Save Attendance");
        markSaveButton.setBackground(new java.awt.Color(11, 27, 226));
        markSaveButton.setForeground(java.awt.Color.WHITE);
        markSaveButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        markSaveButton.setFocusPainted(false);
        markPanel.add(markSaveButton);
        markSaveButton.setBounds(10, 120, 140, 28);

        markClearButton = new JButton("Clear");
        markClearButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        markClearButton.setFocusPainted(false);
        markPanel.add(markClearButton);
        markClearButton.setBounds(160, 120, 80, 28);

        // Add the panel to jPanel3 (content area)
        jPanel3.add(markPanel);
        markPanel.setBounds(30, 465, 480, 160);

        // Resize jPanel3 and jPanel1 to accommodate the new panel
        jPanel3.setPreferredSize(new java.awt.Dimension(540, 640));
        jPanel3.setBounds(240, 0, 540, 640);
        jPanel1.setPreferredSize(new java.awt.Dimension(780, 640));
        jPanel1.setBounds(0, 0, 780, 640);
        setSize(780, 660);
        setLocationRelativeTo(null);
    }

    /** Helper to build a small bold label for the Mark Attendance form */
    private javax.swing.JLabel label(String text) {
        javax.swing.JLabel l = new javax.swing.JLabel(text);
        l.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        return l;
    }

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
                    int hSize = (int) ((double) shapeSize * 0.4);
                    g2.drawOval(cx + shapeSize / 2 - hSize / 2, cy + 2, hSize, hSize);
                    g2.drawArc(cx + 1, cy + shapeSize / 2 + 1, shapeSize - 2, shapeSize / 2, 0, 180);
                    int capY = cy + 1;
                    int capW = (int) ((double) shapeSize * 0.5);
                    int capH = (int) ((double) shapeSize * 0.2);
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
                    g2.drawRoundRect(cx + 1, cy + 1, shapeSize - 2, (int) ((double) shapeSize * 0.7), 2, 2);
                    g2.drawLine(cx + shapeSize / 2, cy + (int) ((double) shapeSize * 0.7), cx + shapeSize / 2, cy + shapeSize - 1);
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
                    int pHead = (int) ((double) shapeSize * 0.45);
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
    private javax.swing.JTable attendanceTable;
    private javax.swing.JLabel avgAttendanceLabel;
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> courseComboBox;
    private javax.swing.JButton exportButton;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCard1Title;
    private javax.swing.JLabel jLabelCard2Title;
    private javax.swing.JLabel jLabelCourse;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCard1;
    private javax.swing.JPanel jPanelCard2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JLabel totalStudentsLabel;
    // End of variables declaration//GEN-END:variables

    private JTextField     markStudentIdField;
    private JTextField     markStudentNameField;
    private JTextField     markTotalClassesField;
    private JTextField     markAttendedField;
    private JTextField     markDateField;
    private JComboBox<String> markCourseCombo;
    private JButton        markSaveButton;
    private JButton        markClearButton;

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(780, 660);
    }
}
