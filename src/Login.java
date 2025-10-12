/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author RC_Student_Lab
 */
public class Login {
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;
    
    // Constructor
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Default constructor
    public Login() {
    }
    
    // Check username format
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
    
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }
        
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = !password.matches("[A-Za-z0-9]*");
        
        
        if (!hasUpperCase) {
            System.out.println("Password must contain at least one capital letter.");
        }
        if (!hasNumber) {
            System.out.println("Password must contain at least one number.");
        }
        if (!hasSpecialChar) {
            System.out.println("Password must contain at least one special character.");
        }
        
        return hasUpperCase && hasNumber && hasSpecialChar;
    }
    
    // Check cell phone number format using regex
    // AI Tool Reference: OpenAI. (2025). ChatGPT (September version) [Large language model]. https://chat.openai.com
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        // South African numbers: +27 followed by 9 digits (total 12 characters)
        String regex = "^\\+27\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellPhoneNumber);
        
        if (!matcher.matches()) {
            System.out.println(" Cell phone must start with +27 followed by 9 digits (e.g., +27123456789)");
            return false;
        }
        return true;
    }
    
    // Register user and return appropriate message
    public String registerUser(String username, String password, String cellPhoneNumber) {
        
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
        // Store the registration details to allow login
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        
        return "Registration successful!\n";
               
    }
    
    // Login user by verifying credentials
    public boolean loginUser(String username, String password) {
        // Check if user is registered first
        if (this.username == null || this.password == null) {
            System.out.println("No user registered. Please register first.");
            return false;
        }
        return username.equals(this.username) && password.equals(this.password);
    }
    
    // Return login status message
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    // Getters
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    // Check if user is registered
    public boolean isRegistered() {
        return username != null && password != null && cellPhoneNumber != null;
    }
}