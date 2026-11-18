package com.mycompany.myfirstprojectpart1prog1a;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Manages the five arrays required for Part 3. */
public class MessageManager {
    private final Message[] sentMessages;
    private final Message[] disregardedMessages;
    private final Message[] storedMessages;
    private final String[] messageHashes;
    private final String[] messageIDs;
    private int sentCount;
    private int disregardedCount;
    private int storedCount;
    private int messageCount;

    public MessageManager(int maxMessages) {
        int capacity = Math.max(1, maxMessages);
        sentMessages = new Message[capacity];
        disregardedMessages = new Message[capacity];
        storedMessages = new Message[capacity];
        messageHashes = new String[capacity];
        messageIDs = new String[capacity];
        loadStoredMessages();
    }

    public boolean addMessage(Message message) {
        if (message == null || messageCount >= messageIDs.length) return false;
        messageIDs[messageCount] = message.getMessageID();
        messageHashes[messageCount] = message.getMessageHash();
        messageCount++;
        switch (message.getStatus()) {
            case "Sent":
                if (sentCount < sentMessages.length) sentMessages[sentCount++] = message;
                break;
            case "Disregarded":
                if (disregardedCount < disregardedMessages.length) disregardedMessages[disregardedCount++] = message;
                break;
            case "Stored":
                if (storedCount < storedMessages.length) storedMessages[storedCount++] = message;
                break;
            default:
                return false;
        }
        return true;
    }

    /** Reads stored JSON objects into the stored-message array. */
    public int loadStoredMessages() {
        Path file = Path.of("messages.json");
        if (!Files.exists(file)) return 0;
        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            Matcher objects = Pattern.compile("\\{[^{}]*\\}", Pattern.DOTALL).matcher(json);
            while (objects.find() && storedCount < storedMessages.length) {
                String object = objects.group();
                String status = Message.readJsonString(object, "status");
                if (!"Stored".equalsIgnoreCase(status)) continue;
                String id = Message.readJsonString(object, "messageID");
                String hash = Message.readJsonString(object, "messageHash");
                int number = Message.readJsonInt(object, "messageNumber", storedCount);
                String sender = Message.readJsonString(object, "sender");
                String recipient = Message.readJsonString(object, "recipient");
                String body = Message.readJsonString(object, "message");
                Message message = new Message(number, sender, recipient, body, id, hash, "Stored");
                storedMessages[storedCount++] = message;
                if (messageCount < messageIDs.length) {
                    messageIDs[messageCount] = message.getMessageID();
                    messageHashes[messageCount] = message.getMessageHash();
                    messageCount++;
                }
            }
        } catch (IOException ex) {
            System.out.println("Could not read messages.json: " + ex.getMessage());
        }
        return storedCount;
    }

    public String displayStoredSendersAndRecipients() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < storedCount; i++) {
            Message m = storedMessages[i];
            out.append("Sender: ").append(m.getSender().isBlank() ? "Not recorded" : m.getSender())
               .append(" | Recipient: ").append(m.getRecipient()).append(System.lineSeparator());
        }
        return out.length() == 0 ? "No stored messages found." : out.toString();
    }

    public String getLongestStoredMessage() {
        Message longest = null;
        for (int i = 0; i < storedCount; i++) {
            if (longest == null || storedMessages[i].getMessageText().length() > longest.getMessageText().length()) longest = storedMessages[i];
        }
        return longest == null ? "No stored messages found." : longest.getMessageText();
    }

    public String searchByMessageID(String id) {
        for (int i = 0; i < sentCount; i++) {
            Message m = sentMessages[i];
            if (m.getMessageID().equals(id) || m.getSender().equals(id)) {
                return "Recipient: " + m.getRecipient() + System.lineSeparator() + "Message: " + m.getMessageText();
            }
        }
        for (int i = 0; i < storedCount; i++) {
            Message m = storedMessages[i];
            if (m.getMessageID().equals(id) || m.getSender().equals(id)) {
                return "Recipient: " + m.getRecipient() + System.lineSeparator() + "Message: " + m.getMessageText();
            }
        }
        return "Message not found.";
    }

    public String searchByRecipient(String recipient) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < storedCount; i++) {
            Message m = storedMessages[i];
            if (m.getRecipient().equals(recipient)) out.append(m.getMessageText()).append(System.lineSeparator());
        }
        return out.length() == 0 ? "No messages found for that recipient." : out.toString().trim();
    }

    public String deleteByHash(String hash) {
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].getMessageHash().equals(hash)) {
                String deleted = storedMessages[i].getMessageText();
                for (int j = i; j < storedCount - 1; j++) storedMessages[j] = storedMessages[j + 1];
                storedMessages[--storedCount] = null;
                for (int k = 0; k < messageCount; k++) {
                    if (hash.equals(messageHashes[k])) {
                        for (int j = k; j < messageCount - 1; j++) {
                            messageHashes[j] = messageHashes[j + 1];
                            messageIDs[j] = messageIDs[j + 1];
                        }
                        messageHashes[--messageCount] = null;
                        messageIDs[messageCount] = null;
                        break;
                    }
                }
                rewriteStoredJson();
                return "Message: \"" + deleted + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    private void rewriteStoredJson() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < storedCount; i++) {
            Message m = storedMessages[i];
            if (i > 0) json.append(",\n");
            json.append("  {\n")
                .append("    \"messageID\": \"").append(Message.escape(m.getMessageID())).append("\",\n")
                .append("    \"messageHash\": \"").append(Message.escape(m.getMessageHash())).append("\",\n")
                .append("    \"messageNumber\": ").append(m.getMessageNumber()).append(",\n")
                .append("    \"sender\": \"").append(Message.escape(m.getSender())).append("\",\n")
                .append("    \"recipient\": \"").append(Message.escape(m.getRecipient())).append("\",\n")
                .append("    \"message\": \"").append(Message.escape(m.getMessageText())).append("\",\n")
                .append("    \"status\": \"Stored\"\n  }");
        }
        json.append("\n]");
        try { Files.writeString(Path.of("messages.json"), json.toString(), StandardCharsets.UTF_8); }
        catch (IOException ex) { System.out.println("Could not update messages.json: " + ex.getMessage()); }
    }

    public String displayStoredReport() {
        StringBuilder out = new StringBuilder("STORED MESSAGE REPORT\n");
        for (int i = 0; i < storedCount; i++) {
            Message m = storedMessages[i];
            out.append("Message ID: ").append(m.getMessageID()).append('\n')
               .append("Message Hash: ").append(m.getMessageHash()).append('\n')
               .append("Sender: ").append(m.getSender()).append('\n')
               .append("Recipient: ").append(m.getRecipient()).append('\n')
               .append("Message: ").append(m.getMessageText()).append('\n')
               .append("Status: ").append(m.getStatus()).append("\n\n");
        }
        return storedCount == 0 ? "No stored messages found." : out.toString();
    }

    public String getSentMessagesText() { return messagesToText(sentMessages, sentCount); }
    public String getDisregardedMessagesText() { return messagesToText(disregardedMessages, disregardedCount); }
    private String messagesToText(Message[] array, int count) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < count; i++) out.append(array[i].getMessageText()).append(System.lineSeparator());
        return out.toString().trim();
    }
    public int getSentCount() { return sentCount; }
    public int getDisregardedCount() { return disregardedCount; }
    public int getStoredCount() { return storedCount; }
    public Message[] getSentMessages() { return sentMessages.clone(); }
    public Message[] getDisregardedMessages() { return disregardedMessages.clone(); }
    public Message[] getStoredMessages() { return storedMessages.clone(); }
    public String[] getMessageHashes() { return messageHashes.clone(); }
    public String[] getMessageIDs() { return messageIDs.clone(); }
}
