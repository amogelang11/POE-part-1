/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author ICT 2022
 */
public class LoginNGTest {
    
    private Login login;
    
    public LoginNGTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        login = new Login("John", "Doe");
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        login = null;
    }

    /**
     * Test of checkUserName method, of class Login.
     */
    @Test
    public void testCheckUserName_CorrectlyFormatted() {
        System.out.println("Username correctly formatted");
        String username = "kyl_1";
        boolean result = login.checkUserName(username);
        assertTrue(result);
    }
    
    @Test
    public void testCheckUserName_IncorrectlyFormatted() {
        System.out.println("Username incorrectly formatted");
        String username = "kyle!!!!!!!";
        boolean result = login.checkUserName(username);
        assertFalse(result);
    }

    /**
     * Test of checkPasswordComplexity method, of class Login.
     */
    @Test
    public void testCheckPasswordComplexity_MeetsRequirements() {
        System.out.println("Password meets complexity requirements");
        String password = "Ch&&sec@ke99!";
        boolean result = login.checkPasswordComplexity(password);
        assertTrue(result);
    }
    
    @Test
    public void testCheckPasswordComplexity_DoesNotMeetRequirements() {
        System.out.println("Password does not meet complexity requirements");
        String password = "password";
        boolean result = login.checkPasswordComplexity(password);
        assertFalse(result);
    }

    /**
     * Test of checkCellPhoneNumber method, of class Login.
     */
    @Test
    public void testCheckCellPhoneNumber_CorrectlyFormatted() {
        System.out.println("Cell phone number correctly formatted");
        String cellPhoneNumber = "+27123456789";
        boolean result = login.checkCellPhoneNumber(cellPhoneNumber);
        assertTrue(result);
    }
    
    @Test
    public void testCheckCellPhoneNumber_IncorrectlyFormatted() {
        System.out.println("Cell phone number incorrectly formatted");
        String cellPhoneNumber = "08966553";
        boolean result = login.checkCellPhoneNumber(cellPhoneNumber);
        assertFalse(result);
    }

    /**
     * Test of loginUser method, of class Login.
     */
    @Test
    public void testLoginUser_Successful() {
        System.out.println("Login Successful");
        // First register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27123456789");
        
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }
    
    @Test
    public void testLoginUser_Failed() {
        System.out.println("Login Failed");
        // First register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27123456789");
        
        boolean result = login.loginUser("kyl_1", "wrongpassword");
        assertFalse(result);
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @Test
    public void testReturnLoginStatus_Successful() {
        System.out.println("returnLoginStatus - successful login message");
        boolean isLoggedIn = true;
        String result = login.returnLoginStatus(isLoggedIn);
        assertEquals(result, "Welcome John, Doe it is great to see you again.");
    }
    
    @Test
    public void testReturnLoginStatus_Failed() {
        System.out.println("returnLoginStatus - failed login message");
        boolean isLoggedIn = false;
        String result = login.returnLoginStatus(isLoggedIn);
        assertEquals(result, "Username or password incorrect, please try again.");
    }
}