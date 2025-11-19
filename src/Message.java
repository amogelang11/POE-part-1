/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Random;//random ID generation
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    
    // Required arrays
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static List<String> messageHashArray = new ArrayList<>();
    private static List<String> messageIDArray = new ArrayList<>();
    
    // Constructor
    public Message(String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        
      
        messageIDArray.add(this.messageID);
        messageHashArray.add(this.messageHash);
    }

    // Generate random 10-digit message ID
    private String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + random.nextInt(900000000);
        return String.valueOf(id);
    }
    
    // Method to check if message ID is valid
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
    
    // Method to check recipient cell number format
    public int checkRecipientCell() {
        if (recipient == null || recipient.isEmpty()) {
            return -1;
        }
        
        String regex = "^\\+[1-9]\\d{0,2}\\d{1,9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(recipient);
        
        if (!matcher.matches()) {
            return -1;
        }
        
        String digitsOnly = recipient.replaceAll("\\D", "");
        if (digitsOnly.length() > 10) {
            return -1;
        }
        
        return 0;
    }
    
    // Method to create message hash
    public String createMessageHash() {
        messageCounter++;
        
        String firstTwoID = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
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
                disregardedMessages.add(this);
                return "Message disregarded.";
                
            case 2: // Store Message
                storedMessages.add(this);
                storeMessage();
                return "Message stored.";
                
            default:
                return "Operation cancelled.";
        }
    }
    
    // Display message details
    private void displayMessageDetails() {
        String details = "Message Details:\n\n" +
                        "Message ID: " + messageID + "\n" +
                        "Message Hash: " + messageHash + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + messageText + "\n\n" +
                        "Total messages sent: " + totalMessagesSent;
        
        JOptionPane.showMessageDialog(null, details, "Message Sent", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Method to store message in JSON file
    public void storeMessage() {
        try {
            JSONObject messageJson = new JSONObject();
            messageJson.put("messageID", this.messageID);
            messageJson.put("recipient", this.recipient);
            messageJson.put("messageText", this.messageText);
            messageJson.put("messageHash", this.messageHash);
            messageJson.put("timestamp", System.currentTimeMillis());
            
            JSONArray messagesArray;
            try {
                String content = new String(Files.readAllBytes(Paths.get("stored_messages.json")));
                messagesArray = new JSONArray(content);
            } catch (IOException e) {
                messagesArray = new JSONArray();
            }
            
            messagesArray.put(messageJson);
            
            try (FileWriter file = new FileWriter("stored_messages.json")) {
                file.write(messagesArray.toString(4));
                file.flush();
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage(), 
                                        "Storage Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Load stored messages from JSON file into storedMessages array
    public static void loadStoredMessages() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("stored_messages.json")));
            JSONArray messagesArray = new JSONArray(content);
            
            storedMessages.clear();
            
            for (int i = 0; i < messagesArray.length(); i++) {
                JSONObject messageJson = messagesArray.getJSONObject(i);
                Message message = new Message(
                    messageJson.getString("recipient"),
                    messageJson.getString("messageText")
                );
                // Set the original ID and hash
                message.messageID = messageJson.getString("messageID");
                message.messageHash = messageJson.getString("messageHash");
                
                storedMessages.add(message);
            }
            
        } catch (IOException e) {
            System.out.println("No stored messages file found or error reading file: " + e.getMessage());
        }
    }
    
    // PART 3 OPERATIONS
    
    // a. Display sender and recipient of all sent messages
    public static String displaySentMessagesSendersRecipients() {
        if (sentMessages.isEmpty()) {
            return "No sent messages available.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Sent Messages - Senders and Recipients:\n\n");
        
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            sb.append("Message ").append(i + 1).append(":\n")
              .append("Recipient: ").append(msg.getRecipient()).append("\n\n");
        }
        
        return sb.toString();
    }
    
    // b. Display the longest sent message
    public static String displayLongestSentMessage() {
        if (sentMessages.isEmpty()) {
            return "No sent messages available.";
        }
        
        Message longestMessage = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getMessageText().length() > longestMessage.getMessageText().length()) {
                longestMessage = msg;
            }
        }
        
        return "Longest Sent Message:\n\n" +
               "Message ID: " + longestMessage.getMessageID() + "\n" +
               "Recipient: " + longestMessage.getRecipient() + "\n" +
               "Message: " + longestMessage.getMessageText() + "\n" +
               "Length: " + longestMessage.getMessageText().length() + " characters";
    }
    
    // c. Search for a message ID and display corresponding recipient and message
    public static String searchMessageByID(String searchID) {
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(searchID)) {
                return "Message Found:\n\n" +
                       "Message ID: " + msg.getMessageID() + "\n" +
                       "Recipient: " + msg.getRecipient() + "\n" +
                       "Message: " + msg.getMessageText();
            }
        }
        
        // Also check stored messages
        for (Message msg : storedMessages) {
            if (msg.getMessageID().equals(searchID)) {
                return "Message Found (Stored):\n\n" +
                       "Message ID: " + msg.getMessageID() + "\n" +
                       "Recipient: " + msg.getRecipient() + "\n" +
                       "Message: " + msg.getMessageText();
            }
        }
        
        return "No message found with ID: " + searchID;
    }
    
    // d. Search for all messages sent to a particular recipient
    public static String searchMessagesByRecipient(String recipient) {
        List<Message> matchingMessages = new ArrayList<>();
        
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                matchingMessages.add(msg);
            }
        }
        
        if (matchingMessages.isEmpty()) {
            return "No messages found for recipient: " + recipient;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Messages sent to ").append(recipient).append(":\n\n");
        
        for (int i = 0; i < matchingMessages.size(); i++) {
            Message msg = matchingMessages.get(i);
            sb.append("Message ").append(i + 1).append(":\n")
              .append("Message ID: ").append(msg.getMessageID()).append("\n")
              .append("Message: ").append(msg.getMessageText()).append("\n\n");
        }
        
        return sb.toString();
    }
    
    // e. Delete a message using the message hash
    public static String deleteMessageByHash(String hash) {
        // Search in sent messages
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageHash().equals(hash)) {
                Message removed = sentMessages.remove(i);
                messageHashArray.remove(hash);
                messageIDArray.remove(removed.getMessageID());
                return "Message deleted successfully from sent messages.\nHash: " + hash;
            }
        }
        
        // Search in stored messages
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(hash)) {
                Message removed = storedMessages.remove(i);
                messageHashArray.remove(hash);
                messageIDArray.remove(removed.getMessageID());
                return "Message deleted successfully from stored messages.\nHash: " + hash;
            }
        }
        
        return "No message found with hash: " + hash;
    }
    
    // f. Display report with full details of all sent messages
    public static String displayFullReport() {
        if (sentMessages.isEmpty()) {
            return "No sent messages available for report.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== FULL MESSAGE REPORT ===\n\n");
        sb.append("Total Messages Sent: ").append(sentMessages.size()).append("\n\n");
        
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            sb.append("MESSAGE ").append(i + 1).append(":\n")
              .append("Message ID: ").append(msg.getMessageID()).append("\n")
              .append("Message Hash: ").append(msg.getMessageHash()).append("\n")
              .append("Recipient: ").append(msg.getRecipient()).append("\n")
              .append("Message: ").append(msg.getMessageText()).append("\n")
              .append("Message Length: ").append(msg.getMessageText().length()).append(" characters\n\n");
        }
        
        return sb.toString();
    }
    
   
public static String printMessages() {
    if (sentMessages.isEmpty()) {
        return "No messages sent yet.";
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append("Recently Sent Messages:\n\n");
    
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
    
    // Getters for arrays (for testing purposes)
    public static List<Message> getSentMessages() { return sentMessages; }
    public static List<Message> getDisregardedMessages() { return disregardedMessages; }
    public static List<Message> getStoredMessages() { return storedMessages; }
    public static List<String> getMessageHashArray() { return messageHashArray; }
    public static List<String> getMessageIDArray() { return messageIDArray; }
    
    // Individual getters
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    
    // Setters
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public void setMessageID(String messageID) {
    this.messageID = messageID;
}

    public void setMessageHash(String messageHash) {
    this.messageHash = messageHash;
}
}
