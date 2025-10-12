/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Random;//random ID generation
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JOptionPane;
// json imports
import org.json.JSONArray;
import org.json.JSONObject;
//file management
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
//list
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author RC_Student_Lab
 */

public class Message {
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int messageCounter = 0;
    private static int totalMessagesSent = 0;
    private static List<Message> sentMessages = new ArrayList<>();
    
    // Constructor
    public Message(String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    
    // Generate random 10-digit message ID
    private String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + random.nextInt(900000000);
        return String.valueOf(id);
    }
    
    // Method to check if message ID is valid (not more than 10 characters)
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
    
    // Method to check recipient cell number format
    public int checkRecipientCell() {
        if (recipient == null || recipient.isEmpty()) {
            return -1; // Invalid - empty
        }
        
        // Check if it starts with international code and has no more than 10 digits after code
        String regex = "^\\+[1-9]\\d{0,2}\\d{1,9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(recipient);
        
        if (!matcher.matches()) {
            return -1; // Invalid format
        }
        
        // Count digits after +
        String digitsOnly = recipient.replaceAll("\\D", "");
        if (digitsOnly.length() > 10) {
            return -1; // Too many digits
        }
        
        return 0; // Valid
    }
    
    // Method to create message hash
    public String createMessageHash() {
        messageCounter++;
        
        // Get first two numbers of message ID
        String firstTwoID = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        
        // Get first and last words of message
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        // Create hash in required format
        String hash = firstTwoID + ":" + messageCounter + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }
    
   
    public String sentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose an option for the message:",
            "Message Options",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0: // Send Message
                totalMessagesSent++;
                sentMessages.add(this);
                displayMessageDetails();
                return "Message sent successfully!";
                
            case 1: // Disregard Message
                return "Message disregarded.";
                
            case 2: // Store Message
                storeMessage();
                return "Message stored.";
                
            default:
                return "Operation cancelled.";
        }
    }
    
    // Method to display message details 
    private void displayMessageDetails() {
        String details = "Message Details:\n\n" +
                        "Message ID: " + messageID + "\n" +
                        "Message Hash: " + messageHash + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + messageText + "\n\n" +
                        "Total messages sent: " + totalMessagesSent;
        
        JOptionPane.showMessageDialog(null, details, "Message Sent", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Sent Messages:\n\n");
        
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            sb.append("Message ").append(i + 1).append(":\n")
              .append("ID: ").append(msg.getMessageID()).append("\n")
              .append("Hash: ").append(msg.getMessageHash()).append("\n")
              .append("Recipient: ").append(msg.getRecipient()).append("\n")
              .append("Message: ").append(msg.getMessageText()).append("\n\n");
        }
        
        return sb.toString();
    }
    
    // Method to return total number of messages sent
    public int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    // Method to store message in JSON file
    public void storeMessage() {
        try {
            // Creating JSON object
            JSONObject messageJson = new JSONObject();
            messageJson.put("messageID", this.messageID);
            messageJson.put("recipient", this.recipient);
            messageJson.put("messageText", this.messageText);
            messageJson.put("messageHash", this.messageHash);
            messageJson.put("timestamp", System.currentTimeMillis());
            
            // Read existing messages from file
            JSONArray messagesArray;
            try {
                String content = new String(Files.readAllBytes(Paths.get("stored_messages.json")));
                messagesArray = new JSONArray(content);
            } catch (IOException e) {
                messagesArray = new JSONArray();
            }
            
            // Add new message to array
            messagesArray.put(messageJson);
            
            // Write back to file
            try (FileWriter file = new FileWriter("stored_messages.json")) {
                file.write(messagesArray.toString(4)); // 4 spaces for indentation
                file.flush();
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage(), 
                                        "Storage Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Getters
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    
    // Setters
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
}
