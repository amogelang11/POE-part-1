/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import java.util.Scanner;
import javax.swing.JOptionPane;

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
        boolean isRegistered = false;
        
        while (!exit) {
            System.out.println("Select an option");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); //  newline
            
            switch (choice) {
                case 1:
                    registerUser(scanner, loginSystem);
                    isRegistered = true; // User is now registered
                    System.out.println("Registration completed!");
                    break;
                case 2:
                    if (!isRegistered) {
                        System.out.println("Please register first before logging in.");
                        break;
                    }
                    boolean loginSuccess = loginUser(scanner, loginSystem);
                    if (loginSuccess) {
                        
                        showMessagingMenu(scanner);
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
    private static void showMessagingMenu(Scanner scanner) {
    JOptionPane.showMessageDialog(null, "Welcome to QuickChat.", "QuickChat", JOptionPane.INFORMATION_MESSAGE);
    
    boolean quit = false;
    int maxMessages = 0;
    boolean messagesLimitSet = false;
    
    while (!quit) {
        if (!messagesLimitSet) {
            String maxMessagesInput = JOptionPane.showInputDialog("How many messages do you wish to enter?");
            if (maxMessagesInput == null) {
                quit = true;
                continue;
            }
            
            try {
                maxMessages = Integer.parseInt(maxMessagesInput);
                if (maxMessages > 0) {
                    messagesLimitSet = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
            continue;
        }
        
    
        String menu = "QuickChat Menu:\n\n" +
                     "1) Send Messages\n" +
                     "2) Show Recently Sent Messages & Part 3 Operations\n" +
                     "3) Quit\n\n" +
                     "Please select an option:";
        
        String choiceStr = JOptionPane.showInputDialog(menu);
        
        if (choiceStr == null) {
            quit = true;
            continue;
        }
        
        try {
            int choice = Integer.parseInt(choiceStr);
            
            switch (choice) {
                case 1:
                    sendMessages(scanner, maxMessages);
                    break;
                case 2:
                    showRecentlySentMessagesMenu(scanner);
                    break;
                case 3:
                    quit = true;
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please select 1-3.", "Invalid Choice", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number (1-3).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    private static void showRecentlySentMessagesMenu(Scanner scanner) {
    // Load stored messages from JSON file
    Message.loadStoredMessages();
    
    boolean backToMain = false;
    
    while (!backToMain) {
        String menu = "RECENTLY SENT MESSAGES & Part 3 Operations\n\n" +
                     "1. Show Recently Sent Messages\n" +
                     "2. Display sender and recipient of all sent messages\n" +
                     "3. Display the longest sent message\n" +
                     "4. Search for message by ID\n" +
                     "5. Search messages by recipient\n" +
                     "6. Delete message by hash\n" +
                     "7. Display full report of all sent messages\n" +
                     "8. Back to main menu\n\n" +
                     "Please select an option:";
        
        String choiceStr = JOptionPane.showInputDialog(menu);
        
        if (choiceStr == null) {
            backToMain = true;
            continue;
        }
        
        try {
            int choice = Integer.parseInt(choiceStr);
            
            switch (choice) {
                case 1:
    // Show recently sent messages 
                    String recentMessages = Message.printMessages();
                    JOptionPane.showMessageDialog(null, recentMessages, "Recently Sent Messages", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 2:
                    String result1 = Message.displaySentMessagesSendersRecipients();
                    JOptionPane.showMessageDialog(null, result1, "Sent Messages", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 3:
                    String result2 = Message.displayLongestSentMessage();
                    JOptionPane.showMessageDialog(null, result2, "Longest Message", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 4:
                    String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
                    if (searchID != null && !searchID.trim().isEmpty()) {
                        String result3 = Message.searchMessageByID(searchID.trim());
                        JOptionPane.showMessageDialog(null, result3, "Message Search", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                    
                case 5:
                    String recipient = JOptionPane.showInputDialog("Enter recipient number to search:");
                    if (recipient != null && !recipient.trim().isEmpty()) {
                        String result4 = Message.searchMessagesByRecipient(recipient.trim());
                        JOptionPane.showMessageDialog(null, result4, "Recipient Search", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                    
                case 6:
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                    if (hash != null && !hash.trim().isEmpty()) {
                        String result5 = Message.deleteMessageByHash(hash.trim());
                        JOptionPane.showMessageDialog(null, result5, "Delete Message", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                    
                case 7:
                    String result6 = Message.displayFullReport();
                    JOptionPane.showMessageDialog(null, result6, "Full Message Report", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 8:
                    backToMain = true;
                    break;
                    
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please select 1-8.", "Invalid Choice", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number (1-8).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    private static void sendMessages(Scanner scanner, int maxMessages) {
        int messagesSent = 0;
        
        for (int i = 0; i < maxMessages; i++) {
            // Get recipient
            String recipient = JOptionPane.showInputDialog("Enter recipient cell number (with international code, e.g., +27123456789):\n\nMessages remaining: " + (maxMessages - i));
            if (recipient == null) {
                
                return;
            }
            
            // Validate recipient
            Message tempMessage = new Message(recipient, "");
            if (tempMessage.checkRecipientCell() != 0) {
                JOptionPane.showMessageDialog(null, "Invalid recipient format. Please ensure the number starts with international code and has no more than 10 digits.", "Invalid Recipient", JOptionPane.ERROR_MESSAGE);
                i--; 
                continue;
            }
            
            // Get message text
            String messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters):\n\nMessages remaining: " + (maxMessages - i));
            if (messageText == null) {
                
                return;
            }
            
            // Validate message length
            if (messageText.length() > 250) {
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.", "Message Too Long", JOptionPane.ERROR_MESSAGE);
                i--; 
                continue;
            }
            
            if (messageText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Message cannot be empty.", "Empty Message", JOptionPane.ERROR_MESSAGE);
                i--; 
                continue;
            }
            
            // Create and process message
            Message message = new Message(recipient, messageText);
            String result = message.sentMessage();
            
            if (result.contains("sent successfully")) {
                messagesSent++;
            }
            
            // Ask if user wants to send more messages
            if (i < maxMessages - 1) {
                int continueOption = JOptionPane.showConfirmDialog(null, 
                    "Do you want to send another message?\n\nMessages sent: " + (messagesSent) + "\nMessages remaining: " + (maxMessages - i - 1), 
                    "Continue?", JOptionPane.YES_NO_OPTION);
                
                if (continueOption != JOptionPane.YES_OPTION) {
                    break;
                }
            }
        }
        
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

 private static void showArrayOperationsMenu(Scanner scanner) {
    // Load stored messages from JSON file
    Message.loadStoredMessages();
    
    boolean backToMain = false;
    
    while (!backToMain) {
        String menu = "ARRAY OPERATIONS MENU\n\n" +
                     "1. Display sender and recipient of all sent messages\n" +
                     "2. Display the longest sent message\n" +
                     "3. Search for message by ID\n" +
                     "4. Search messages by recipient\n" +
                     "5. Delete message by hash\n" +
                     "6. Display full report of all sent messages\n" +
                     "7. Back to main menu\n\n" +
                     "Please select an option:";
        
        String choiceStr = JOptionPane.showInputDialog(menu);
        
        if (choiceStr == null) {
            backToMain = true;
            continue;
        }
        
        try {
            int choice = Integer.parseInt(choiceStr);
            
            switch (choice) {
                case 1:
                    String result1 = Message.displaySentMessagesSendersRecipients();
                    JOptionPane.showMessageDialog(null, result1, "Sent Messages", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 2:
                    String result2 = Message.displayLongestSentMessage();
                    JOptionPane.showMessageDialog(null, result2, "Longest Message", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 3:
                    String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
                    if (searchID != null && !searchID.trim().isEmpty()) {
                        String result3 = Message.searchMessageByID(searchID.trim());
                        JOptionPane.showMessageDialog(null, result3, "Message Search", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                    
                case 4:
                    String recipient = JOptionPane.showInputDialog("Enter recipient number to search:");
                    if (recipient != null && !recipient.trim().isEmpty()) {
                        String result4 = Message.searchMessagesByRecipient(recipient.trim());
                        JOptionPane.showMessageDialog(null, result4, "Recipient Search", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                    
                case 5:
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                    if (hash != null && !hash.trim().isEmpty()) {
                        String result5 = Message.deleteMessageByHash(hash.trim());
                        JOptionPane.showMessageDialog(null, result5, "Delete Message", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                    
                case 6:
                    String result6 = Message.displayFullReport();
                    JOptionPane.showMessageDialog(null, result6, "Full Message Report", JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 7:
                    backToMain = true;
                    break;
                    
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please select 1-7.", "Invalid Choice", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number (1-7).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
}