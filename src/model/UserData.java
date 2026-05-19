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
    
    public UserData(String username, String password, String email, String role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    
    public int getUserId(){ return userId; }
    public String getUserName(){ return username; }
    public String getPassword(){ return password; }
    public String getEmail(){ return email; }
    public String getRole(){ return role; }
    
    public void setUserId(int userId){ this.userId = userId; }
    public void setUserName(String username){ this.username = username; }
    public void setPassword(String password){ this.password = password; }
    public void setEmail(String email){ this.email = email; }
    public void setRole(String role){ this.role = role; }
}