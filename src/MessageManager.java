/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


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
public class MessageManager {
    // Arrays as specified in requirements
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static List<String> messageHashArray = new ArrayList<>();
    private static List<String> messageIDArray = new ArrayList<>();
    
    // Private constructor 
    private MessageManager() {}
    
   
   
    public static void addToSentMessages(Message message) {
        if (message != null) {
            sentMessages.add(message);
            addToMessageArrays(message);
        }
    }
    
    
    public static void addToDisregardedMessages(Message message) {
        if (message != null) {
            disregardedMessages.add(message);
            addToMessageArrays(message);
        }
    }
    
    
    public static void addToStoredMessages(Message message) {
        if (message != null) {
            storedMessages.add(message);
            addToMessageArrays(message);
        }
    }
    
 
    private static void addToMessageArrays(Message message) {
        if (message.getMessageID() != null && !messageIDArray.contains(message.getMessageID())) {
            messageIDArray.add(message.getMessageID());
        }
        if (message.getMessageHash() != null && !messageHashArray.contains(message.getMessageHash())) {
            messageHashArray.add(message.getMessageHash());
        }
    }
    
    
    public static void loadStoredMessagesFromJSON() {
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
                message.setMessageID(messageJson.getString("messageID"));
                message.setMessageHash(messageJson.getString("messageHash"));
                
                storedMessages.add(message);
                addToMessageArrays(message);
            }
            
        } catch (IOException e) {
            System.out.println("No stored messages file found or error reading file: " + e.getMessage());
        }
    }
    
    // ===== PART 3 OPERATIONS METHODS =====
    
    /**
     * a. Display the sender and recipient of all sent messages
     */
    public static String displaySentMessagesSendersRecipients() {
        if (sentMessages.isEmpty()) {
            return "No sent messages available.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== SENT MESSAGES - SENDERS AND RECIPIENTS ===\n\n");
        
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            sb.append("Message ").append(i + 1).append(":\n")
              .append("Recipient: ").append(msg.getRecipient()).append("\n\n");
        }
        
        return sb.toString();
    }
    
    /**
     * b. Display the longest sent message
     */
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
        
        return "=== LONGEST SENT MESSAGE ===\n\n" +
               "Message ID: " + longestMessage.getMessageID() + "\n" +
               "Recipient: " + longestMessage.getRecipient() + "\n" +
               "Message: " + longestMessage.getMessageText() + "\n" +
               "Length: " + longestMessage.getMessageText().length() + " characters";
    }
    
    /**
     * c. Search for a message ID and display the corresponding recipient and message
     */
    public static String searchMessageByID(String searchID) {
        // Search in sent messages
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(searchID)) {
                return "=== MESSAGE FOUND (SENT) ===\n\n" +
                       "Message ID: " + msg.getMessageID() + "\n" +
                       "Recipient: " + msg.getRecipient() + "\n" +
                       "Message: " + msg.getMessageText();
            }
        }
        
        // Search in stored messages
        for (Message msg : storedMessages) {
            if (msg.getMessageID().equals(searchID)) {
                return "=== MESSAGE FOUND (STORED) ===\n\n" +
                       "Message ID: " + msg.getMessageID() + "\n" +
                       "Recipient: " + msg.getRecipient() + "\n" +
                       "Message: " + msg.getMessageText();
            }
        }
        
        // Search in disregarded messages
        for (Message msg : disregardedMessages) {
            if (msg.getMessageID().equals(searchID)) {
                return "=== MESSAGE FOUND (DISREGARDED) ===\n\n" +
                       "Message ID: " + msg.getMessageID() + "\n" +
                       "Recipient: " + msg.getRecipient() + "\n" +
                       "Message: " + msg.getMessageText();
            }
        }
        
        return "No message found with ID: " + searchID;
    }
    
    /**
     * d. Search for all the messages sent to a particular recipient
     */
    public static String searchMessagesByRecipient(String recipient) {
        List<Message> matchingMessages = new ArrayList<>();
        
        // Search in sent messages
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                matchingMessages.add(msg);
            }
        }
        
        // Search in stored messages
        for (Message msg : storedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                matchingMessages.add(msg);
            }
        }
        
        // Search in disregarded messages
        for (Message msg : disregardedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                matchingMessages.add(msg);
            }
        }
        
        if (matchingMessages.isEmpty()) {
            return "No messages found for recipient: " + recipient;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== MESSAGES FOR RECIPIENT: ").append(recipient).append(" ===\n\n");
        
        for (int i = 0; i < matchingMessages.size(); i++) {
            Message msg = matchingMessages.get(i);
            String messageType = getMessageType(msg);
            sb.append("Message ").append(i + 1).append(" (").append(messageType).append("):\n")
              .append("Message ID: ").append(msg.getMessageID()).append("\n")
              .append("Message: ").append(msg.getMessageText()).append("\n\n");
        }
        
        return sb.toString();
    }
    
    /**
     * e. Delete a message using the message hash
     */
    public static String deleteMessageByHash(String hash) {
        // Search in sent messages
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageHash().equals(hash)) {
                Message removed = sentMessages.remove(i);
                messageHashArray.remove(hash);
                messageIDArray.remove(removed.getMessageID());
                return "Message successfully deleted from sent messages.\nHash: " + hash;
            }
        }
        
        // Search in stored messages
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(hash)) {
                Message removed = storedMessages.remove(i);
                messageHashArray.remove(hash);
                messageIDArray.remove(removed.getMessageID());
                return "Message successfully deleted from stored messages.\nHash: " + hash;
            }
        }
        
        // Search in disregarded messages
        for (int i = 0; i < disregardedMessages.size(); i++) {
            if (disregardedMessages.get(i).getMessageHash().equals(hash)) {
                Message removed = disregardedMessages.remove(i);
                messageHashArray.remove(hash);
                messageIDArray.remove(removed.getMessageID());
                return "Message successfully deleted from disregarded messages.\nHash: " + hash;
            }
        }
        
        return "No message found with hash: " + hash;
    }
    
    /**
     * f. Display a report that lists the full details of all the sent messages
     */
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
    
    // HELPER METHODS 
    
   
    private static String getMessageType(Message message) {
        if (sentMessages.contains(message)) {
            return "Sent";
        } else if (storedMessages.contains(message)) {
            return "Stored";
        } else if (disregardedMessages.contains(message)) {
            return "Disregarded";
        }
        return "Unknown";
    }
    
   
    public static void initializeWithTestData() {
        resetAllArrays();
        
        // Test Data Message 1: Sent
        Message message1 = new Message("+27834557896", "Did you get the cake?");
        addToSentMessages(message1);
        
        // Test Data Message 2: Stored
        Message message2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        addToStoredMessages(message2);
        
        // Test Data Message 3: Sent 
        Message message3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        addToSentMessages(message3);
        
        // Test Data Message 4: Sent
        Message message4 = new Message("0838884567", "It is dinner time!");
        addToSentMessages(message4);
        
        // Test Data Message 5: Stored
        Message message5 = new Message("+27838884567", "Ok, I am leaving without you.");
        addToStoredMessages(message5);
    }
    
    /**
     * Reset all arrays 
     */
    public static void resetAllArrays() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashArray.clear();
        messageIDArray.clear();
    }
    
     //GETTER METHODS 
    
    public static List<Message> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }
    
    public static List<Message> getDisregardedMessages() {
        return new ArrayList<>(disregardedMessages);
    }
    
    public static List<Message> getStoredMessages() {
        return new ArrayList<>(storedMessages);
    }
    
    public static List<String> getMessageHashArray() {
        return new ArrayList<>(messageHashArray);
    }
    
    public static List<String> getMessageIDArray() {
        return new ArrayList<>(messageIDArray);
    }
}
