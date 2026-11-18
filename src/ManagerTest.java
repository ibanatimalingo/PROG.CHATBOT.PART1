import org.junit.Test;
import static org.junit.Assert.*;

public class ManagerTest {

    @Test
    public void testSentMessagesArrayPopulated() {
        MessageManager manager = new MessageManager();
        // Test Data Message 1-4 from Rubric Page 18
        manager.addMessageEntry("1234567890", "00:1:DIDFOU", "+27834557896", "Did you get the cake?", "Sent");
        manager.addMessageEntry("0987654321", "00:4:ITISDINNER", "0838884567", "It is dinner time!", "Sent");

        assertEquals(2, manager.getSentMessages().size());
        assertEquals("Did you get the cake?", manager.getSentMessages().get(0));
        assertEquals("It is dinner time!", manager.getSentMessages().get(1));
    }

    @Test
    public void testDisplayLongestMessage() {
        MessageManager manager = new MessageManager();
        manager.addMessageEntry("1111111111", "00:1:DIDFOU", "+27834557896", "Did you get the cake?", "Sent");
        manager.addMessageEntry("2222222222", "00:2:WHEREYOU", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        manager.addMessageEntry("3333333333", "00:3:YOHOOOAT", "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        manager.addMessageEntry("4444444444", "00:4:ITISDINNER", "0838884567", "It is dinner time!", "Sent");

        String longest = manager.getLongestStoredMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID() {
        MessageManager manager = new MessageManager();
        manager.addMessageEntry("0838884567", "00:4:ITISDINNER", "0838884567", "It is dinner time!", "Sent");

        String result = manager.searchByMessageID("0838884567");
        assertTrue(result.contains("0838884567"));
        assertTrue(result.contains("It is dinner time!"));
    }

    @Test
    public void testSearchByRecipient() {
        MessageManager manager = new MessageManager();
        // Test Data Message 2 & 5 for recipient +27838884567
        manager.addMessageEntry("2222222222", "00:2:WHEREYOU", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        manager.addMessageEntry("5555555555", "00:5:OKIAM", "+27838884567", "Ok, I am leaving without you.", "Stored");

        String result = manager.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByMessageHash() {
        MessageManager manager = new MessageManager();
        manager.addMessageEntry("2222222222", "00:2:WHEREYOU", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");

        boolean deleted = manager.deleteByMessageHash("00:2:WHEREYOU");
        assertTrue(deleted);
        assertTrue(manager.getMessageHashes().isEmpty());
    }
}