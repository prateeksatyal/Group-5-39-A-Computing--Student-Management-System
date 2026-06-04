/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class UserData {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String role;
    private String image; // Added in Week 7 for profile picture upload
    
    // Existing constructor (backward compatibility)
    public UserData(String username, String password, String email, String role){
        this(username, password, email, role, "");
    }
    
    // New constructor with image support
    public UserData(String username, String password, String email, String role, String image){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.image = image;
    }
    
    public int getUserId(){ return userId; }
    public String getUserName(){ return username; }
    public String getPassword(){ return password; }
    public String getEmail(){ return email; }
    public String getRole(){ return role; }
    public String getImage(){ return image; }
    
    public void setUserId(int userId){ this.userId = userId; }
    public void setUserName(String username){ this.username = username; }
    public void setPassword(String password){ this.password = password; }
    public void setEmail(String email){ this.email = email; }
    public void setRole(String role){ this.role = role; }
    public void setImage(String image){ this.image = image; }
}