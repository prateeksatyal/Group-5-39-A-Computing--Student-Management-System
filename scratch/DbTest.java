import dao.ResultDAO;
import model.ResultData;
import database.MySqlConnector;
import java.sql.*;
import java.util.List;

public class DbTest {
    public static void main(String[] args) {
        MySqlConnector mysql = new MySqlConnector();
        Connection conn = null;
        try {
            conn = mysql.openConnection();
            System.out.println("Connection OK!");
            
            // 1. Ensure at least one test student exists in students table
            String checkStudentSql = "SELECT student_id FROM students WHERE student_id = 'ST1001'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkStudentSql);
            if (!rs.next()) {
                System.out.println("Creating test student ST1001...");
                String insertStudentSql = "INSERT INTO students (student_id, full_name, email, phone_number, course, gender, address) "
                                        + "VALUES ('ST1001', 'Prateek Satyal', 'prateek@test.com', '1234567890', 'BSc Computer Science', 'Male', 'Kathmandu')";
                stmt.executeUpdate(insertStudentSql);
            }
            rs.close();
            
            // 2. Save marks
            System.out.println("Saving marks for ST1001...");
            // Simulate saving marks via MarksController sql
            String term = "Term 1";
            String course = "BSc Computer Science";
            String section = "Section A";
            double totalMarks = 163.0;
            double pct = (totalMarks / 200.0) * 100.0;
            String grade = "B+";
            double gpa = 3.3;
            
            // Check if exists
            String checkMarksSql = "SELECT id FROM marks WHERE student_id = ? AND term = ? AND course_name = ? AND section_name = ?";
            PreparedStatement checkPstm = conn.prepareStatement(checkMarksSql);
            checkPstm.setString(1, "ST1001");
            checkPstm.setString(2, term);
            checkPstm.setString(3, course);
            checkPstm.setString(4, section);
            ResultSet marksRs = checkPstm.executeQuery();
            if (marksRs.next()) {
                int existingId = marksRs.getInt("id");
                String updateSql = "UPDATE marks SET student_name = ?, marks = ?, grade = ?, grade_point = ?, percentage = ? WHERE id = ?";
                PreparedStatement updatePstm = conn.prepareStatement(updateSql);
                updatePstm.setString(1, "Prateek Satyal");
                updatePstm.setDouble(2, totalMarks);
                updatePstm.setString(3, grade);
                updatePstm.setDouble(4, gpa);
                updatePstm.setDouble(5, pct);
                updatePstm.setInt(6, existingId);
                updatePstm.executeUpdate();
                updatePstm.close();
            } else {
                String insertSql = "INSERT INTO marks (student_id, student_name, course_name, term, section_name, marks, grade, grade_point, percentage) "
                                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertPstm = conn.prepareStatement(insertSql);
                insertPstm.setString(1, "ST1001");
                insertPstm.setString(2, "Prateek Satyal");
                insertPstm.setString(3, course);
                insertPstm.setString(4, term);
                insertPstm.setString(5, section);
                insertPstm.setDouble(6, totalMarks);
                insertPstm.setString(7, grade);
                insertPstm.setDouble(8, gpa);
                insertPstm.setDouble(9, pct);
                insertPstm.executeUpdate();
                insertPstm.close();
            }
            marksRs.close();
            checkPstm.close();
            System.out.println("Marks saved successfully!");
            
            // 3. Load/Generate results
            System.out.println("Generating result from marks...");
            ResultDAO resultDAO = new ResultDAO();
            List<ResultData> list = resultDAO.generateResultsFromMarks(term, course, section);
            System.out.println("Generated list size: " + list.size());
            if (!list.isEmpty()) {
                ResultData res = list.get(0);
                System.out.println("Generated result - Student: " + res.getStudentName() + ", GPA: " + res.getGpa() + ", Grade: " + res.getGrade());
                
                // Save it
                System.out.println("Saving generated result...");
                boolean saved = resultDAO.saveResult(res);
                System.out.println("Result saved: " + saved);
            }
            
            // 4. Retrieve saved results
            System.out.println("Querying all results...");
            List<ResultData> savedResults = resultDAO.getAllResults();
            System.out.println("Total saved results in DB: " + savedResults.size());
            for (ResultData r : savedResults) {
                System.out.println("  - Student: " + r.getStudentName() + " | Term: " + r.getTerm() + " | Course: " + r.getCourse() + " | GPA: " + r.getGpa() + " | Grade: " + r.getGrade());
            }
            
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { mysql.closeConnection(conn); } catch(Exception e){}
            }
        }
    }
}
