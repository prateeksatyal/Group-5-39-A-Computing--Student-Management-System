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
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * NetBeans GUI Builder compatible view class for GenerateResultFrame.
 */
public class GenerateResultFrame extends JFrame {

    public GenerateResultFrame() {
        initComponents();
        this.setResizable(true);
        this.jPanel1.setPreferredSize(new java.awt.Dimension(780, 480));
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(this.jPanel1);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.setContentPane(scrollPane);
        try {
            this.setIconImage(new ImageIcon(this.getClass().getResource("/images/Ellipse 21.png")).getImage());
        } catch (Exception exception) {
            // ignore icon exceptions
        }
    }

    // ── Getters ──────────────────────────────────────────────────────────────

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

    public JCheckBox getGpaCheckBox() { return this.jCheckBoxGPA; }
    public JCheckBox getRankCheckBox() { return this.jCheckBoxRank; }
    public JCheckBox getSubjectMarksCheckBox() { return this.jCheckBoxSubject; }

    public JRadioButton getDetailedRadioButton() { return this.jRadioButtonDetailed; }
    public JRadioButton getSummaryRadioButton() { return this.jRadioButtonSummary; }

    public JTextArea getPreviewTextArea() { return this.jTextAreaPreview; }
    public JButton getGenerateButton() { return this.jButtonGenerate; }
    public JButton getBackButton() { return this.jButtonBack; }
    public javax.swing.JLabel getTitleLabel() { return this.jLabel1; }

    // ── Styling / Visual Effects ─────────────────────────────────────────────



    // ── VectorIcon Class ──────────────────────────────────────────────────────

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
                    int hSize = (int) (shapeSize * 0.4);
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
                    g2.drawRoundRect(cx + 1, cy + 1, shapeSize - 2, (int) (shapeSize * 0.7), 2, 2);
                    g2.drawLine(cx + shapeSize / 2, cy + (int) (shapeSize * 0.7), cx + shapeSize / 2, cy + shapeSize - 1);
                    break;
                case "result":
                    g2.drawRoundRect(cx + 2, cy + 1, shapeSize - 4, shapeSize - 2, 2, 2);
                    break;
                case "reports":
                    g2.drawRoundRect(cx + 2, cy + 3, shapeSize - 4, shapeSize - 4, 2, 2);
                    break;
                case "profile":
                    int pHead = (int) (shapeSize * 0.45);
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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupReport = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanelCard = new javax.swing.JPanel();
        jLabelTerm = new javax.swing.JLabel();
        jComboBoxTerm = new javax.swing.JComboBox<>();
        jLabelCourse = new javax.swing.JLabel();
        jComboBoxCourse = new javax.swing.JComboBox<>();
        jLabelSection = new javax.swing.JLabel();
        jComboBoxSection = new javax.swing.JComboBox<>();
        jLabelFormat = new javax.swing.JLabel();
        jCheckBoxSubject = new javax.swing.JCheckBox();
        jCheckBoxGPA = new javax.swing.JCheckBox();
        jCheckBoxRank = new javax.swing.JCheckBox();
        jLabelOptions = new javax.swing.JLabel();
        jRadioButtonDetailed = new javax.swing.JRadioButton();
        jRadioButtonSummary = new javax.swing.JRadioButton();
        jButtonGenerate = new javax.swing.JButton();
        jScrollPanePreview = new javax.swing.JScrollPane();
        jTextAreaPreview = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System - Result Generation");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(28, 39, 50));
        jPanel2.setLayout(null);

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

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("  Academic Performance");
        jButton5.setBorder(null);
        jButton5.setContentAreaFilled(false);
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

        jButton7.setBackground(new java.awt.Color(243, 227, 225));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(11, 27, 226));
        jButton7.setText("  Result Generation");
        jButton7.setBorder(null);
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

        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("  Logout");
        jButton10.setBorder(null);
        jButton10.setContentAreaFilled(false);
        jButton10.setFocusPainted(false);
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jButton10);
        jButton10.setBounds(10, 440, 220, 32);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  SMS");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 20, 200, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 240, 480);

        jPanel3.setBackground(new java.awt.Color(243, 227, 225));
        jPanel3.setLayout(null);

        jButtonBack.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonBack.setText("Back");
        jButtonBack.setFocusPainted(false);
        jPanel3.add(jButtonBack);
        jButtonBack.setBounds(430, 15, 80, 25);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Generate Result Report");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(30, 15, 350, 35);

        jPanelCard.setBackground(new java.awt.Color(217, 217, 217));
        jPanelCard.setLayout(null);

        jLabelTerm.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelTerm.setText("Term");
        jPanelCard.add(jLabelTerm);
        jLabelTerm.setBounds(20, 15, 440, 20);

        jComboBoxTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "" }));
        jPanelCard.add(jComboBoxTerm);
        jComboBoxTerm.setBounds(20, 35, 440, 30);

        jLabelCourse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelCourse.setText("Courses");
        jPanelCard.add(jLabelCourse);
        jLabelCourse.setBounds(20, 75, 440, 20);

        jComboBoxCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "" }));
        jPanelCard.add(jComboBoxCourse);
        jComboBoxCourse.setBounds(20, 95, 440, 30);

        jLabelSection.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelSection.setText("Section");
        jPanelCard.add(jLabelSection);
        jLabelSection.setBounds(20, 135, 440, 20);

        jComboBoxSection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "" }));
        jPanelCard.add(jComboBoxSection);
        jComboBoxSection.setBounds(20, 155, 440, 30);

        jLabelFormat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelFormat.setText("Include");
        jPanelCard.add(jLabelFormat);
        jLabelFormat.setBounds(20, 195, 440, 20);

        jCheckBoxSubject.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jCheckBoxSubject.setText("Subject Marks");
        jCheckBoxSubject.setOpaque(false);
        jPanelCard.add(jCheckBoxSubject);
        jCheckBoxSubject.setBounds(20, 215, 130, 25);

        jCheckBoxGPA.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jCheckBoxGPA.setText("GPA");
        jCheckBoxGPA.setOpaque(false);
        jPanelCard.add(jCheckBoxGPA);
        jCheckBoxGPA.setBounds(160, 215, 80, 25);

        jCheckBoxRank.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jCheckBoxRank.setText("Rank");
        jCheckBoxRank.setOpaque(false);
        jPanelCard.add(jCheckBoxRank);
        jCheckBoxRank.setBounds(250, 215, 80, 25);

        jLabelOptions.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelOptions.setText("Preview Type");
        jPanelCard.add(jLabelOptions);
        jLabelOptions.setBounds(20, 250, 440, 20);

        buttonGroupReport.add(jRadioButtonDetailed);
        jRadioButtonDetailed.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jRadioButtonDetailed.setText("Detailed");
        jRadioButtonDetailed.setOpaque(false);
        jPanelCard.add(jRadioButtonDetailed);
        jRadioButtonDetailed.setBounds(20, 270, 100, 25);

        buttonGroupReport.add(jRadioButtonSummary);
        jRadioButtonSummary.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jRadioButtonSummary.setText("Summary");
        jRadioButtonSummary.setOpaque(false);
        jPanelCard.add(jRadioButtonSummary);
        jRadioButtonSummary.setBounds(130, 270, 100, 25);

        jPanel3.add(jPanelCard);
        jPanelCard.setBounds(30, 70, 480, 320);

        jButtonGenerate.setBackground(new java.awt.Color(11, 27, 226));
        jButtonGenerate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonGenerate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGenerate.setText("Generate Report");
        jButtonGenerate.setFocusPainted(false);
        jPanel3.add(jButtonGenerate);
        jButtonGenerate.setBounds(30, 410, 480, 40);

        jScrollPanePreview.setVisible(false);

        jTextAreaPreview.setColumns(20);
        jTextAreaPreview.setRows(5);
        jTextAreaPreview.setVisible(false);
        jScrollPanePreview.setViewportView(jTextAreaPreview);

        jPanel3.add(jScrollPanePreview);
        jScrollPanePreview.setBounds(0, 0, 0, 0);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(240, 0, 540, 480);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupReport;
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
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonGenerate;
    private javax.swing.JCheckBox jCheckBoxGPA;
    private javax.swing.JCheckBox jCheckBoxRank;
    private javax.swing.JCheckBox jCheckBoxSubject;
    private javax.swing.JComboBox<String> jComboBoxCourse;
    private javax.swing.JComboBox<String> jComboBoxSection;
    private javax.swing.JComboBox<String> jComboBoxTerm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCourse;
    private javax.swing.JLabel jLabelFormat;
    private javax.swing.JLabel jLabelOptions;
    private javax.swing.JLabel jLabelSection;
    private javax.swing.JLabel jLabelTerm;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCard;
    private javax.swing.JRadioButton jRadioButtonDetailed;
    private javax.swing.JRadioButton jRadioButtonSummary;
    private javax.swing.JScrollPane jScrollPanePreview;
    private javax.swing.JTextArea jTextAreaPreview;
    // End of variables declaration//GEN-END:variables

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(780, 480);
    }
}