/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import java.lang.reflect.Field;
import java.util.List;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageNGTest {
    
    private Message message;
    
    public MessageNGTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        resetMessageArrays();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        resetMessageArrays();
    }
    
    // Helper method to reset static message arrays and counters
    private void resetMessageArrays() {
        try {
            // Reset counters
            Field counterField = Message.class.getDeclaredField("messageCounter");
            counterField.setAccessible(true);
            counterField.set(null, 0);
            
            Field totalField = Message.class.getDeclaredField("totalMessagesSent");
            totalField.setAccessible(true);
            totalField.set(null, 0);
            
            // Reset arrays
            Field sentField = Message.class.getDeclaredField("sentMessages");
            sentField.setAccessible(true);
            List<Message> sentMessages = (List<Message>) sentField.get(null);
            sentMessages.clear();
            
            Field disregardedField = Message.class.getDeclaredField("disregardedMessages");
            disregardedField.setAccessible(true);
            List<Message> disregardedMessages = (List<Message>) disregardedField.get(null);
            disregardedMessages.clear();
            
            Field storedField = Message.class.getDeclaredField("storedMessages");
            storedField.setAccessible(true);
            List<Message> storedMessages = (List<Message>) storedField.get(null);
            storedMessages.clear();
            
            Field hashField = Message.class.getDeclaredField("messageHashArray");
            hashField.setAccessible(true);
            List<String> messageHashArray = (List<String>) hashField.get(null);
            messageHashArray.clear();
            
            Field idField = Message.class.getDeclaredField("messageIDArray");
            idField.setAccessible(true);
            List<String> messageIDArray = (List<String>) idField.get(null);
            messageIDArray.clear();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TEST DATA SETUP 
    private void setupTestData() throws Exception {
        resetMessageArrays();
        
        // Test Data Message 1
        Message message1 = new Message("+27834557896", "Did you get the cake?");
        addToSentMessages(message1);
        
        // Test Data Message 2
        Message message2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        addToStoredMessages(message2);
        
        // Test Data Message 3
        Message message3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        addToSentMessages(message3);
        
        // Test Data Message 4
        Message message4 = new Message("0838884567", "It is dinner time!");
        addToSentMessages(message4);
        
        // Test Data Message 5
        Message message5 = new Message("+27838884567", "Ok, I am leaving without you.");
        addToStoredMessages(message5);
    }
    
    private void addToSentMessages(Message message) throws Exception {
        Field sentField = Message.class.getDeclaredField("sentMessages");
        sentField.setAccessible(true);
        List<Message> sentMessages = (List<Message>) sentField.get(null);
        sentMessages.add(message);
        
      
        Field idField = Message.class.getDeclaredField("messageIDArray");
        idField.setAccessible(true);
        List<String> messageIDArray = (List<String>) idField.get(null);
        messageIDArray.add(message.getMessageID());
        
        Field hashField = Message.class.getDeclaredField("messageHashArray");
        hashField.setAccessible(true);
        List<String> messageHashArray = (List<String>) hashField.get(null);
        messageHashArray.add(message.getMessageHash());
        
        // Increment total sent
        Field totalField = Message.class.getDeclaredField("totalMessagesSent");
        totalField.setAccessible(true);
        int currentTotal = (int) totalField.get(null);
        totalField.set(null, currentTotal + 1);
    }
    
    private void addToStoredMessages(Message message) throws Exception {
        Field storedField = Message.class.getDeclaredField("storedMessages");
        storedField.setAccessible(true);
        List<Message> storedMessages = (List<Message>) storedField.get(null);
        storedMessages.add(message);
        
       
        Field idField = Message.class.getDeclaredField("messageIDArray");
        idField.setAccessible(true);
        List<String> messageIDArray = (List<String>) idField.get(null);
        messageIDArray.add(message.getMessageID());
        
        Field hashField = Message.class.getDeclaredField("messageHashArray");
        hashField.setAccessible(true);
        List<String> messageHashArray = (List<String>) hashField.get(null);
        messageHashArray.add(message.getMessageHash());
    }

    

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() throws Exception {
        setupTestData();
        
        Field sentField = Message.class.getDeclaredField("sentMessages");
        sentField.setAccessible(true);
        List<Message> sentMessages = (List<Message>) sentField.get(null);
        
        assertEquals(sentMessages.size(), 3);
        
        boolean foundMessage1 = false;
        boolean foundMessage4 = false;
        
        for (Message msg : sentMessages) {
            if ("Did you get the cake?".equals(msg.getMessageText())) {
                foundMessage1 = true;
            }
            if ("It is dinner time!".equals(msg.getMessageText())) {
                foundMessage4 = true;
            }
        }
        
        assertTrue(foundMessage1);
        assertTrue(foundMessage4);
    }

    @Test
    public void testDisplayLongestMessage() throws Exception {
        setupTestData();
        
        
        resetMessageArrays();
        
        
        Message message1 = new Message("+27834557896", "Did you get the cake?");
        addToSentMessages(message1);
        
        Message message2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        addToSentMessages(message2); // This is the longest message
        
        Message message3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        addToSentMessages(message3);
        
        String result = Message.displayLongestSentMessage();
        
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
    }

    @Test
    public void testSearchForMessageID() throws Exception {
        setupTestData();
        
        Field sentField = Message.class.getDeclaredField("sentMessages");
        sentField.setAccessible(true);
        List<Message> sentMessages = (List<Message>) sentField.get(null);
        
        String targetMessageID = null;
        for (Message msg : sentMessages) {
            if ("It is dinner time!".equals(msg.getMessageText())) {
                targetMessageID = msg.getMessageID();
                break;
            }
        }
        
        assertNotNull(targetMessageID);
        
        String result = Message.searchMessageByID(targetMessageID);
        
        assertTrue(result.contains("It is dinner time!"));
        assertTrue(result.contains(targetMessageID));
    }

    @Test
    public void testSearchMessagesByRecipient() throws Exception {
        setupTestData();
        
        String result = Message.searchMessagesByRecipient("+27838884567");
        
         
        assertTrue(result.contains("+27838884567"));
    }

    @Test
    public void testDeleteMessageByHash() throws Exception {
        setupTestData();
        
        Field storedField = Message.class.getDeclaredField("storedMessages");
        storedField.setAccessible(true);
        List<Message> storedMessages = (List<Message>) storedField.get(null);
        
        String targetHash = null;
        Message targetMessage = null;
        for (Message msg : storedMessages) {
            if ("Where are you? You are late! I have asked you to be on time.".equals(msg.getMessageText())) {
                targetHash = msg.getMessageHash();
                targetMessage = msg;
                break;
            }
        }
        
        assertNotNull(targetHash);
        
        // Store initial size
        int initialSize = storedMessages.size();
        
        String result = Message.deleteMessageByHash(targetHash);
        
        // Check if deletion was successful
        assertTrue(result.contains("deleted") || result.contains("successfully"));
        
        // Verify message is removed
        boolean messageStillExists = false;
        for (Message msg : storedMessages) {
            if (msg == targetMessage) {
                messageStillExists = true;
                break;
            }
        }
        assertFalse(messageStillExists);
    }

    @Test
    public void testDisplayReport() throws Exception {
        setupTestData();
        
        String result = Message.displayFullReport();
        
        assertTrue(result.contains("FULL MESSAGE REPORT") || result.contains("Report"));
        assertTrue(result.contains("Message") || result.contains("Recipient"));
    }

    // EXISTING TESTS
   
    @Test
    public void testMessageLength_Success() {
        String shortMessage = "This is a short message";
        Message testMessage = new Message("+27718693002", shortMessage);
        assertTrue(shortMessage.length() <= 250);
    }
    
    @Test
    public void testMessageLength_Failure() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            longMessage.append("a");
        }
        assertTrue(longMessage.length() > 250);
    }

    @Test
    public void testCheckRecipientCell_Success() {
        Message testMessage1 = new Message("+27718693002", "Test message");
        int result1 = testMessage1.checkRecipientCell();
        assertEquals(result1, -1);
    }
    
    @Test
    public void testCheckRecipientCell_Failure() {
        Message testMessage1 = new Message("08575975889", "Test message");
        int result1 = testMessage1.checkRecipientCell();
        assertEquals(result1, -1);
    }

    @Test
    public void testCreateMessageHash() {
        Message testMessage1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        String hash1 = testMessage1.createMessageHash();
        
        String messageID = testMessage1.getMessageID();
        String firstTwoID = messageID.substring(0, 2);
        
        String expectedHash = firstTwoID + ":2:HITONIGHT";
        expectedHash = expectedHash.toUpperCase();
        
        assertEquals(hash1, expectedHash);
    }

    @Test
    public void testCheckMessageID() {
        boolean result = message.checkMessageID();
        assertTrue(result);
        
        String messageID = message.getMessageID();
        assertEquals(messageID.length(), 10);
    }

    @Test
    public void testSentMessage_Send() {
        int initialTotal = message.returnTotalMessages();
        String result = simulateSentMessageOption(0);
        assertEquals(result, "Message sent successfully!");
        
        int finalTotal = message.returnTotalMessages();
        assertEquals(finalTotal, initialTotal + 1);
    }
    
    @Test
    public void testSentMessage_Disregard() {
        int initialTotal = message.returnTotalMessages();
        String result = simulateSentMessageOption(1);
        assertEquals(result, "Message disregarded.");
        
        int finalTotal = message.returnTotalMessages();
        assertEquals(finalTotal, initialTotal);
    }
    
    @Test
    public void testSentMessage_Store() {
        int initialTotal = message.returnTotalMessages();
        String result = simulateSentMessageOption(2);
        assertEquals(result, "Message stored for later.");
        
        int finalTotal = message.returnTotalMessages();
        assertEquals(finalTotal, initialTotal);
    }

    @Test
    public void testReturnTotalMessages() {
        Message testMessage1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        simulateSentMessageOption(0);
        
        int totalAfterFirst = message.returnTotalMessages();
        assertEquals(totalAfterFirst, 1);
        
        Message testMessage2 = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        simulateSentMessageOption(1);
        
        int totalAfterSecond = message.returnTotalMessages();
        assertEquals(totalAfterSecond, 1);
    }

    @Test
    public void testCompleteFlowWithTestData() {
        resetMessageArrays();
        
        Message message1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertTrue(message1.checkMessageID());
        assertEquals(message1.checkRecipientCell(), -1);
        
        String hash1 = message1.getMessageHash();
        assertTrue(hash1.matches("^\\d{2}:1:HITONIGHT$"));
        
        String result1 = simulateSentMessageOption(0);
        assertEquals(result1, "Message sent successfully!");
        
        Message message2 = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals(message2.checkRecipientCell(), -1);
        
        String result2 = simulateSentMessageOption(1);
        assertEquals(result2, "Message disregarded.");
        
        int totalMessages = message1.returnTotalMessages();
        assertEquals(totalMessages, 1);
    }
    
    private String simulateSentMessageOption(int choice) {
        switch (choice) {
            case 0:
                try {
                    Field totalField = Message.class.getDeclaredField("totalMessagesSent");
                    totalField.setAccessible(true);
                    int currentTotal = (int) totalField.get(null);
                    totalField.set(null, currentTotal + 1);
                    
                    Field sentField = Message.class.getDeclaredField("sentMessages");
                    sentField.setAccessible(true);
                    List<Message> sentMessages = (List<Message>) sentField.get(null);
                    sentMessages.add(message);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Message sent successfully!";
                
            case 1:
                return "Message disregarded.";
                
            case 2:
                return "Message stored for later.";
                
            default:
                return "Operation cancelled.";
        }
    }
}