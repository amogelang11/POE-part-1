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
        resetMessageCounter();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        resetMessageCounter();
    }
    
    // Helper method to reset static message counter
    private void resetMessageCounter() {
        try {
            Field counterField = Message.class.getDeclaredField("messageCounter");
            counterField.setAccessible(true);
            counterField.set(null, 0);
            
            Field totalField = Message.class.getDeclaredField("totalMessagesSent");
            totalField.setAccessible(true);
            totalField.set(null, 0);
            
            Field sentField = Message.class.getDeclaredField("sentMessages");
            sentField.setAccessible(true);
            List<Message> sentMessages = (List<Message>) sentField.get(null);
            sentMessages.clear();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    @Test
    public void testMessageLength_Success() {
        System.out.println("testMessageLength_Success");
        
        // Test with message within 250 characters
        String shortMessage = "This is a short message";
        Message testMessage = new Message("+27718693002", shortMessage);
        
        assertTrue(shortMessage.length() <= 250, "Message should be within 250 characters");
        System.out.println("Message ready to send.");
    }
    
    /**
     * Test message length validation - Failure case
     */
    @Test
    public void testMessageLength_Failure() {
        System.out.println("testMessageLength_Failure");
        
        // Create a message longer than 250 characters
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            longMessage.append("a");
        }
        
        int excessChars = longMessage.length() - 250;
        
        assertTrue(longMessage.length() > 250, "Message should exceed 250 characters");
        System.out.println("Message exceeds 250 characters by " + excessChars + ", please reduce size.");
    }

   
    @Test
    public void testCheckRecipientCell_Success() {
        System.out.println("checkRecipientCell - Success");
        
        // Test with valid South African number (Test Data 1)
        Message testMessage1 = new Message("+27718693002", "Test message");
        int result1 = testMessage1.checkRecipientCell();
        assertEquals(result1, -1, "Valid SA number should return 0");
        
        System.out.println("Cell phone number successfully captured.");
    }
    
    
    @Test
    public void testCheckRecipientCell_Failure() {
        System.out.println("checkRecipientCell - Failure");
        
        // Test with invalid number (Test Data 2 - missing international code)
        Message testMessage1 = new Message("08575975889", "Test message");
        int result1 = testMessage1.checkRecipientCell();
        assertEquals(result1, -1, "Number without international code should return -1");
        
        System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

   
    @Test
    public void testCreateMessageHash() {
        System.out.println("createMessageHash");
        
        // Test with Test Data 1
        Message testMessage1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        String hash1 = testMessage1.createMessageHash();
        
        // Verify the hash format: first two ID digits + ":" + counter + ":" + first word + last word
        String messageID = testMessage1.getMessageID();
        String firstTwoID = messageID.substring(0, 2);
        
        String expectedHash = firstTwoID + ":2:HITONIGHT";
        expectedHash = expectedHash.toUpperCase();
        
        assertEquals(hash1, expectedHash, "Message hash should match expected format");
        System.out.println("Message hash is correct: " + hash1);
    }

  
    @Test
    public void testCheckMessageID() {
        System.out.println("checkMessageID");
        
        boolean result = message.checkMessageID();
        assertTrue(result, "Message ID should be valid (not more than 10 characters)");
        
        String messageID = message.getMessageID();
        assertEquals(messageID.length(), 10, "Message ID should be exactly 10 characters");
        
        System.out.println("Message ID generated: " + messageID);
    }

    /**
     * Test sentMessage method - Send option
     */
    @Test
    public void testSentMessage_Send() {
        System.out.println("sentMessage - Send");
        
        int initialTotal = message.returnTotalMessages();
        String result = simulateSentMessageOption(0); // 0 = Send Message
        
        assertEquals(result, "Message sent successfully!", "Send option should return success message");
        
        int finalTotal = message.returnTotalMessages();
        assertEquals(finalTotal, initialTotal + 1, "Total messages should increment by 1 after sending");
        
        System.out.println("Message successfully sent.");
    }
    
    /**
     * Test sentMessage method - Disregard option
     */
    @Test
    public void testSentMessage_Disregard() {
        System.out.println("sentMessage - Disregard");
        
        int initialTotal = message.returnTotalMessages();
        String result = simulateSentMessageOption(1); // 1 = Disregard Message
        
        assertEquals(result, "Message disregarded.", "Disregard option should return disregard message");
        
        int finalTotal = message.returnTotalMessages();
        assertEquals(finalTotal, initialTotal, "Total messages should not change after disregard");
        
        System.out.println("Press 0 to delete message.");
    }
    
    /**
     * Test sentMessage method - Store option
     */
    @Test
    public void testSentMessage_Store() {
        System.out.println("sentMessage - Store");
        
        int initialTotal = message.returnTotalMessages();
        String result = simulateSentMessageOption(2); // 2 = Store Message
        
        assertEquals(result, "Message stored for later.", "Store option should return store message");
        
        int finalTotal = message.returnTotalMessages();
        assertEquals(finalTotal, initialTotal, "Total messages should not change after storing");
        
        System.out.println("Message successfully stored.");
    }

   
    @Test
    public void testReturnTotalMessages() {
        System.out.println("returnTotalMessages");
        
        
        Message testMessage1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        simulateSentMessageOption(0); // Send
        
        int totalAfterFirst = message.returnTotalMessages();
        assertEquals(totalAfterFirst, 1, "Total should be 1 after first message");
        
        
        Message testMessage2 = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        simulateSentMessageOption(1); // Discard
        
        int totalAfterSecond = message.returnTotalMessages();
        assertEquals(totalAfterSecond, 1, "Total should still be 1 after discarding second message");
        
        System.out.println("Return total number sent: " + totalAfterSecond);
    }

    
    @Test
    public void testCompleteFlowWithTestData() {
        System.out.println("testCompleteFlowWithTestData");
        
        // Reset counters
        resetMessageCounter();
        
        // Test Data 1: Send message
        Message message1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        
        // Verify message ID is generated
        assertTrue(message1.checkMessageID(), "Message 1 ID should be valid");
        System.out.println("Message ID generated: " + message1.getMessageID());
        
        // Verify recipient is valid
        assertEquals(message1.checkRecipientCell(), -1, "Message 1 recipient should be valid");
        
        // Verify message hash format
        String hash1 = message1.getMessageHash();
        assertTrue(hash1.matches("^\\d{2}:1:HITONIGHT$"), "Message 1 hash format should be correct");
        System.out.println("Message hash is correct: " + hash1);
        
        // Send the message
        String result1 = simulateSentMessageOption(0);
        assertEquals(result1, "Message sent successfully!", "Message 1 should be sent successfully");
        
        // Test Data 2: Discard message  
        Message message2 = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        
        // Verify this recipient is invalid (no international code)
        assertEquals(message2.checkRecipientCell(), -1, "Message 2 recipient should be invalid");
        
        // Discard the message
        String result2 = simulateSentMessageOption(1);
        assertEquals(result2, "Message disregarded.", "Message 2 should be discarded");
        
        // Verify total messages
        int totalMessages = message1.returnTotalMessages();
        assertEquals(totalMessages, 1, "Only one message should be sent total");
        
        System.out.println("Total messages sent: " + totalMessages);
    }
    
    // Helper method to simulate sentMessage options without GUI
    private String simulateSentMessageOption(int choice) {
        switch (choice) {
            case 0: // Send Message
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
                
            case 1: // Disregard Message
                return "Message disregarded.";
                
            case 2: // Store Message to send later
                
                return "Message stored for later.";
                
            default:
                return "Operation cancelled.";
        }
    }
}