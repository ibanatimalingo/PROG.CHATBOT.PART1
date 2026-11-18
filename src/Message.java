import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message{
    // keeping track of how many messages we push through
    private static int messageCount = 0;
    private static final List<String> messageHistory = new ArrayList<>();

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String content;
    private final String messageHash;

    // constructor for normal message creation
    public Message(int messageNumber, String recipient, String content) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.content = content;
        this.messageHash = createMessageHash();
    }

    // overloaded one so main can initialize sessions properly
    public Message(String messageID, int messageNumber, String recipient, String content) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.content = content;
        this.messageHash = createMessageHash();
    }

    // just generating a random 10 digit id here, nothing too deep
    private String generateMessageID() {
        Random rand = new Random();
        long num = 1000000000L + (long) (rand.nextDouble() * 8999999999L);
        return String.valueOf(num);
    }

    // checking if the id doesn't exceed 10 chars as per rubric
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // making sure the cell number has +27 and the right length
    public boolean hasValidRecipientCell() {
        return recipient != null && recipient.trim().matches("\\+27\\d{9}");
    }

    // returns whatever status string the lecture needs for the cell check
    public String checkRecipientCell() {
        if (hasValidRecipientCell()) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // quick length check against 250 characters max
    public boolean isMessageLengthValid() {
        return content != null && content.length() <= 250;
    }

    // checking length and returning the exact error text required
    public String checkMessageLength() {
        if (content == null) {
            return "Please enter a message.";
        }
        if (isMessageLengthValid()) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (content.length() - 250) + "; please reduce the size.";
    }

    // building that hash using ID, message number, and the first/last words
    public String createMessageHash() {
        if (content == null || content.trim().isEmpty()) {
            return messageID.substring(0, 2) + ":" + messageNumber + ":";
        }
        String[] words = content.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();

        return messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // handles options 1, 2, or 3 for what to do with the message
    public String SentMessage(String choice) {
        if (choice == null) return "Invalid option.";

        switch (choice.trim()) {
            case "1":
                messageCount++;
                addToList();
                return "Message successfully sent.";
            case "2":
                return "Press 0 to delete the message.";
            case "3":
                addToList();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // loops through saved messages to print them on screen
    public static String printMessages() {
        if (messageHistory.isEmpty()) {
            return "No messages have been sent.";
        }
        StringBuilder sb = new StringBuilder();
        for (String msg : messageHistory) {
            sb.append(msg).append("\n-------------------------\n");
        }
        return sb.toString();
    }

    // formats everything nicely so it looks neat when displayed
    public String toDisplayString() {
        return "Message ID: " + messageID + "\n" +
                "Message Hash: " + messageHash + "\n" +
                "Recipient: " + recipient + "\n" +
                "Message: " + content;
    }

    // returns total sent messages (keeping that double s spelling from the rubric)
    public int returnTotalMessagess() {
        return messageCount;
    }

    // helper to chuck messages into our local array list
    private void addToList() {
        String msg = toDisplayString();
        if (!messageHistory.contains(msg)) {
            messageHistory.add(msg);
        }
    }

    // saves stuff into a json file so part 3 next month is already sorted
    public boolean storeMessage() {
        Path filePath = Path.of("messages.json");
        String existingContent = "[]";

        try {
            if (Files.exists(filePath)) {
                existingContent = Files.readString(filePath, StandardCharsets.UTF_8).trim();
            }

            String jsonEntry = String.format(
                    "{\"messageId\":\"%s\",\"messageHash\":\"%s\",\"recipient\":\"%s\",\"message\":\"%s\"}",
                    messageID, messageHash, recipient, content.replace("\"", "\\\"")
            );

            String updatedContent;
            if (existingContent.isEmpty() || existingContent.equals("[]")) {
                updatedContent = "[" + jsonEntry + "]";
            } else if (existingContent.endsWith("]")) {
                updatedContent = existingContent.substring(0, existingContent.length() - 1) + "," + jsonEntry + "]";
            } else {
                updatedContent = "[" + jsonEntry + "]";
            }
            Files.writeString(filePath, updatedContent, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    // standard getters because private fields need to be accessed
    public String getMessageId() { return messageID; }
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public String getMessageHash() { return messageHash; }
}