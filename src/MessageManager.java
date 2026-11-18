import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private List<String> sentMessages = new ArrayList<>();
    private List<String> disregardedMessages = new ArrayList<>();
    private List<String> storedMessages = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIDs = new ArrayList<>();
    private List<String> recipients = new ArrayList<>();

    // Load stored messages from JSON file into parallel arrays
    public void loadStoredMessagesFromJson() {
        Path filePath = Path.of("messages.json");
        if (!Files.exists(filePath)) {
            return;
        }

        try {
            String content = Files.readString(filePath, StandardCharsets.UTF_8).trim();
            if (content.isEmpty() || content.equals("[]")) {
                return;
            }

            content = content.substring(1, content.length() - 1);
            String[] entries = content.split("\\},\\s*\\{");

            for (String entry : entries) {
                entry = entry.replace("{", "").replace("}", "").replace("\"", "");
                String[] fields = entry.split(",");

                String id = "", hash = "", rec = "", msg = "";
                for (String field : fields) {
                    String[] keyValue = field.split(":", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String val = keyValue[1].trim();
                        if (key.equals("messageId")) id = val;
                        else if (key.equals("messageHash")) hash = val;
                        else if (key.equals("recipient")) rec = val;
                        else if (key.equals("message")) msg = val;
                    }
                }

                if (!storedMessages.contains(msg)) {
                    storedMessages.add(msg);
                    messageIDs.add(id);
                    messageHashes.add(hash);
                    recipients.add(rec);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading messages.json");
        }
    }

    // Add entry manually for testing or runtime tracking
    public void addMessageEntry(String id, String hash, String recipient, String message, String flag) {
        messageIDs.add(id);
        messageHashes.add(hash);
        recipients.add(recipient);

        if (flag.equalsIgnoreCase("Sent")) {
            sentMessages.add(message);
        } else if (flag.equalsIgnoreCase("Stored")) {
            storedMessages.add(message);
        } else if (flag.equalsIgnoreCase("Disregard")) {
            disregardedMessages.add(message);
        }
    }

    // Display the longest stored/sent message
    public String getLongestStoredMessage() {
        List<String> allMessages = new ArrayList<>();
        allMessages.addAll(storedMessages);
        allMessages.addAll(sentMessages);
        allMessages.addAll(disregardedMessages);

        if (allMessages.isEmpty()) {
            return "No messages available.";
        }

        String longest = allMessages.get(0);
        for (String m : allMessages) {
            if (m.length() > longest.length()) {
                longest = m;
            }
        }
        return longest;
    }

    // Search for a message ID and return recipient and message
    public String searchByMessageID(String searchId) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(searchId)) {
                String msgText = "";
                if (i < storedMessages.size()) msgText = storedMessages.get(i);
                else if (i < sentMessages.size()) msgText = sentMessages.get(i);
                return recipients.get(i) + ", \"" + msgText + "\"";
            }
        }
        return "Message ID not found.";
    }

    // Search for all messages stored for a particular recipient
    public String searchByRecipient(String targetRecipient) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(targetRecipient)) {
                String msg = "";
                if (i < storedMessages.size()) msg = storedMessages.get(i);
                else if (i < sentMessages.size()) msg = sentMessages.get(i);

                if (!msg.isEmpty()) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append("\"").append(msg).append("\"");
                }
            }
        }
        return sb.toString();
    }

    // Delete a message using the message hash
    public boolean deleteByMessageHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(hash)) {
                messageHashes.remove(i);
                messageIDs.remove(i);
                recipients.remove(i);
                if (i < storedMessages.size()) storedMessages.remove(i);
                else if (i < sentMessages.size()) sentMessages.remove(i);
                return true;
            }
        }
        return false;
    }

    // Display a report listing full details of all messages
    public String displayReport() {
        StringBuilder sb = new StringBuilder("--- MESSAGE REPORT ---\n");
        for (int i = 0; i < messageHashes.size(); i++) {
            String msg = i < storedMessages.size() ? storedMessages.get(i) : (i < sentMessages.size() ? sentMessages.get(i) : "");
            sb.append("Message Hash: ").append(messageHashes.get(i))
                    .append("\nRecipient: ").append(recipients.get(i))
                    .append("\nMessage: \"").append(msg).append("\"\n-------------------------\n");
        }
        return sb.toString();
    }

    // Getters for arrays and verification
    public List<String> getSentMessages() {
        return sentMessages;
    }
    public List<String> getStoredMessages() {
        return storedMessages;
    }
    public List<String> getDisregardedMessages() {
        return disregardedMessages;
    }
    public List<String> getMessageHashes() {
        return messageHashes;
    }
    public List<String> getMessageIDs() {
        return messageIDs;
    }
    public List<String> getRecipients() {
        return recipients;
    }
}