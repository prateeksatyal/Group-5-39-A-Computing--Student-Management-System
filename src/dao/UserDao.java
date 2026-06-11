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
        String sql = "INSERT INTO users (full_name, username, email, password, confirm_password, role) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            
            pstm.setString(1, user.getUserName());       // full_name column
            pstm.setString(2, user.getEmail());           // username column (email as login ID)
            pstm.setString(3, user.getEmail());           // email column
            pstm.setString(4, user.getPassword());        // password column
            pstm.setString(5, user.getPassword());        // confirm_password column
            pstm.setString(6, user.getRole());            // role column
            pstm.executeUpdate();
            return true;
           
        } catch (SQLException e) {
            System.out.println("Error in createUser: " + e.getMessage());
            return false;
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
                    // IMPORTANT: UserData(username, password, email, role)
                    // getUserName() must return the login email so controllers
                    // can look up student records by email.
                    UserData user = new UserData(
                        rs.getString("email"),      // username = login email
                        rs.getString("password"),
                        rs.getString("email"),       // email
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

    // === GET FULL NAME BY EMAIL ===
    public String getFullNameByEmail(String email) {
        String sql = "SELECT full_name FROM users WHERE email = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("full_name");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getFullNameByEmail: " + e.getMessage());
        }
        return null;
    }
}