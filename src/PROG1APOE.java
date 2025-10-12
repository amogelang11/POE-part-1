/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import java.util.Scanner;

/**
 *
 * @author RC_Student_Lab
 */

public class PROG1APOE {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();
        
        
        System.out.println("Heyyy, Welcome To ChatApp");
       
        
        boolean exit = false;
        
        while (!exit) {
            System.out.println("Select an option");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    registerUser(scanner, loginSystem);
                    break;
                case 2:
                    boolean loginSuccess = loginUser(scanner, loginSystem);
                    if (loginSuccess) {
                        exit = true; 
                    }
                    break;
                case 3:
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void registerUser(Scanner scanner, Login loginSystem) {
        System.out.println("*****Registration****");
        
        // Get first name and last name 
        System.out.println("Please enter your first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Please enter your last name:");
        String lastName = scanner.nextLine();
        
        // Set username
        loginSystem.setFirstName(firstName);
        loginSystem.setLastName(lastName);
        
        String username, password, cellPhoneNumber;
        boolean validUsername = false;
        boolean validPassword = false;
        boolean validCellPhone = false;
        
        
        do {
            System.out.println("Please enter a username (must contain '_' and be no more than 5 characters):");
            username = scanner.nextLine();
            
            if (loginSystem.checkUserName(username)) {
                validUsername = true;
                System.out.println("Username successfully captured.");
            } else {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        } while (!validUsername);
        
        // Password validation with retry
        do {
            System.out.println("Please enter a password (must be at least than 8 characters, contain capital letter, number, and special character):");
            password = scanner.nextLine();
            
            if (loginSystem.checkPasswordComplexity(password)) {
                validPassword = true;
                System.out.println("Password successfully captured.");
            } else {
                System.out.println("Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        } while (!validPassword);
        
        // Cell phone validation with retry
        do {
            System.out.println("Please enter your cellphone number (format: +27123456789):");
            cellPhoneNumber = scanner.nextLine();
            
            if (loginSystem.checkCellPhoneNumber(cellPhoneNumber)) {
                validCellPhone = true;
                System.out.println("Cell phone number successfully added.");
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }
        } while (!validCellPhone);
        
        
        String registrationResult = loginSystem.registerUser(username, password, cellPhoneNumber);
        System.out.println("\n" + registrationResult);
    }
    
    private static boolean loginUser(Scanner scanner, Login loginSystem) {
        System.out.println("*****Login*****");
        
        boolean loginSuccess = false;
        
        while (!loginSuccess) {
            // Get login credentials
            System.out.println("Please enter your username:");
            String username = scanner.nextLine();
            
            System.out.println("Please enter your password:");
            String password = scanner.nextLine();
            
            // Attempt login
            loginSuccess = loginSystem.loginUser(username, password);
            String loginStatus = loginSystem.returnLoginStatus(loginSuccess);
            System.out.println(loginStatus);
            
            if (!loginSuccess) {
                System.out.println("Please try again.");
            } else {
                // Successful login 
                return true;
            }
        }
        return false;
    }
}