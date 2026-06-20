/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author prate
 */
public class UserDaoTest {
    
    public UserDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createUser method, of class UserDao.
     */
    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        UserData user = null;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.createUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkUser method, of class UserDao.
     */
    @Test
    public void testCheckUser() {
        System.out.println("checkUser");
        UserData user = null;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.checkUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class UserDao.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String email = "";
        String password = "";
        String role = "";
        UserDao instance = new UserDao();
        UserData expResult = null;
        UserData result = instance.loginUser(email, password, role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePassword method, of class UserDao.
     */
    @Test
    public void testUpdatePassword() {
        System.out.println("updatePassword");
        String email = "";
        String role = "";
        String newPassword = "";
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.updatePassword(email, role, newPassword);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFullNameByEmail method, of class UserDao.
     */
    @Test
    public void testGetFullNameByEmail() {
        System.out.println("getFullNameByEmail");
        String email = "";
        UserDao instance = new UserDao();
        String expResult = "";
        String result = instance.getFullNameByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
