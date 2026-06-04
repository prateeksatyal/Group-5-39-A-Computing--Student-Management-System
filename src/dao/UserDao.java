/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.MySqlConnection;
import model.UserData;
import java.sql.*;

public class UserDao {
    
    private final MySqlConnection mysql = new MySqlConnection();
   
    // === REGISTER NEW USER ===
    public boolean createUser(UserData user) {
        String sqlWithImage = "INSERT INTO users (full_name, username, email, password, confirm_password, role, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlWithoutImage = "INSERT INTO users (full_name, username, email, password, confirm_password, role) VALUES (?, ?, ?, ?, ?, ?)";
        
        // 1. Try to insert with the image column first (Week 7 requirement)
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sqlWithImage)) {
            
            pstm.setString(1, user.getUserName());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getPassword());
            pstm.setString(5, user.getPassword());
            pstm.setString(6, user.getRole());
            pstm.setString(7, user.getImage() != null ? user.getImage() : "");
            pstm.executeUpdate();
            return true;
           
        } catch (SQLException e) {
            System.out.println("Inserting with image failed, attempting fallback: " + e.getMessage());
            
            // 2. Fallback to schema without the image column if database column is missing
            try (Connection conn = mysql.openConnection();
                 PreparedStatement pstm = conn.prepareStatement(sqlWithoutImage)) {
                
                pstm.setString(1, user.getUserName());
                pstm.setString(2, user.getEmail());
                pstm.setString(3, user.getEmail());
                pstm.setString(4, user.getPassword());
                pstm.setString(5, user.getPassword());
                pstm.setString(6, user.getRole());
                pstm.executeUpdate();
                return true;
            } catch (SQLException ex) {
                System.out.println("Error in createUser fallback: " + ex.getMessage());
                return false;
            }
        }
    }
   
    // === CHECK FOR DUPLICATE USERNAME OR EMAIL ===
    public boolean checkUser(UserData user) {
        String sql = "SELECT * FROM users WHERE full_name = ? OR email = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getUserName());
            pstm.setString(2, user.getEmail());
            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error in checkUser: " + e.getMessage());
            return false;
        }
    }
   
    // === LOGIN USER ===
    public UserData loginUser(String email, String password, String role) {
        String sql = "SELECT * FROM users WHERE email = ? AND (password = ? OR password = SHA2(?, 256)) AND role = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            
            pstm.setString(1, email);
            pstm.setString(2, password);
            pstm.setString(3, password);
            pstm.setString(4, role);
            
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String imagePath = "";
                    try {
                        imagePath = rs.getString("image");
                    } catch (SQLException sqle) {
                        // Suppress exception if image column doesn't exist in active schema
                    }
                    
                    UserData user = new UserData(
                        rs.getString("email"),     // getUserName() returns email (login credential)
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role"),
                        imagePath
                    );
                    user.setUserId(rs.getInt("user_id"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in loginUser: " + e.getMessage());
        }
        return null;
    }

    // === UPDATE PASSWORD ===
    public boolean updatePassword(String email, String role, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ? AND role = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, newPassword);
            pstm.setString(2, email);
            pstm.setString(3, role);
            int rowsUpdated = pstm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error in updatePassword: " + e.getMessage());
            return false;
        }
    }
}