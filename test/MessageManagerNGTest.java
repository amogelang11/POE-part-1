/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import java.util.List;
/**
 *
 * @author RC_Student_Lab
 */
public class MessageManagerNGTest {
    
    public MessageManagerNGTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MessageManager.resetAllArrays();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        MessageManager.resetAllArrays();
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        MessageManager.initializeWithTestData();
        
        List<Message> sentMessages = MessageManager.getSentMessages();
        
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
    public void testDisplayLongestSentMessage() {
        MessageManager.initializeWithTestData();
        
        String result = MessageManager.displayLongestSentMessage();
        
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
    }

    @Test
    public void testSearchMessageByID() {
        MessageManager.initializeWithTestData();
        
        List<Message> sentMessages = MessageManager.getSentMessages();
        String targetMessageID = null;
        
        for (Message msg : sentMessages) {
            if ("It is dinner time!".equals(msg.getMessageText())) {
                targetMessageID = msg.getMessageID();
                break;
            }
        }
        
        assertNotNull(targetMessageID);
        
        String result = MessageManager.searchMessageByID(targetMessageID);
        
        assertTrue(result.contains("It is dinner time!"));
        assertTrue(result.contains("0838884567"));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        MessageManager.initializeWithTestData();
        
        String result = MessageManager.searchMessagesByRecipient("+27838884567");
        
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
        assertTrue(result.contains("+27838884567"));
    }

    @Test
    public void testDeleteMessageByHash() {
        MessageManager.initializeWithTestData();
        
        List<Message> storedMessages = MessageManager.getStoredMessages();
        String targetHash = null;
        
        for (Message msg : storedMessages) {
            if ("Where are you? You are late! I have asked you to be on time.".equals(msg.getMessageText())) {
                targetHash = msg.getMessageHash();
                break;
            }
        }
        
        assertNotNull(targetHash);
        
        int initialStoredCount = storedMessages.size();
        
        String result = MessageManager.deleteMessageByHash(targetHash);
        
        assertTrue(result.contains("successfully deleted"));
        assertTrue(result.contains(targetHash));
        
        List<Message> updatedStoredMessages = MessageManager.getStoredMessages();
        assertEquals(updatedStoredMessages.size(), initialStoredCount - 1);
        
        boolean messageStillExists = false;
        for (Message msg : updatedStoredMessages) {
            if ("Where are you? You are late! I have asked you to be on time.".equals(msg.getMessageText())) {
                messageStillExists = true;
                break;
            }
        }
        
        assertFalse(messageStillExists);
    }

    @Test
    public void testDisplayFullReport() {
        MessageManager.initializeWithTestData();
        
        String result = MessageManager.displayFullReport();
        
        assertTrue(result.contains("FULL MESSAGE REPORT"));
        assertTrue(result.contains("Total Messages Sent"));
        assertTrue(result.contains("Message Hash"));
        assertTrue(result.contains("Recipient"));
        assertTrue(result.contains("Message"));
        assertTrue(result.contains("Did you get the cake?"));
        assertTrue(result.contains("It is dinner time!"));
        assertTrue(result.contains("Yohoooo, I am at your gate."));
    }
}
