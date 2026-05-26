/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.MySqlConnector;
import model.UserData;
import java.sql.*;

public class UserDao {
    
    private final MySqlConnector mysql = new MySqlConnector();
   
    // === REGISTER NEW USER ===
    public boolean createUser(UserData user) {
        String sql = "INSERT INTO users (fullName, email, password, role) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            
            pstm.setString(1, user.getUserName());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPassword());
            pstm.setString(4, user.getRole());
            pstm.executeUpdate();
            return true;
           
        } catch (SQLException e) {
            System.out.println("Error in createUser: " + e.getMessage());
            return false;
        }
    }
   
    // === LOGIN USER ===
    public UserData loginUser(String email, String password, String role) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND role = ?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            
            pstm.setString(1, email);
            pstm.setString(2, password);
            pstm.setString(3, role);
            
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    UserData user = new UserData(
                        rs.getString("fullName"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role")
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
}