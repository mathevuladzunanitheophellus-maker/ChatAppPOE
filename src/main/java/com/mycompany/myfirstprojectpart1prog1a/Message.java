package com.mycompany.myfirstprojectpart1prog1a;
//Removed array lists
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
    private final String messageID;
    private final int messageNumber;
    private final String sender;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private String status;
    private static int totalMessagesSent = 0;
    private static Message[] sentMessages = new Message[0];
    private static int sentCount = 0;

    public Message(int messageNumber, String recipient, String messageText) {
        this(messageNumber, "", recipient, messageText);
    }

    public Message(int messageNumber, String sender, String recipient, String messageText) {
        this(messageNumber, sender, recipient, messageText,
                generateID(), null, "Pending");
    }

    Message(int messageNumber, String sender, String recipient, String messageText,
            String messageID, String messageHash, String status) {
        this.messageNumber = messageNumber;
        this.sender = sender == null ? "" : sender;
        this.recipient = recipient == null ? "" : recipient;
        this.messageText = messageText == null ? "" : messageText;
        this.messageID = messageID == null ? generateID() : messageID;
        this.messageHash = messageHash == null || messageHash.isBlank()
                ? createMessageHash() : messageHash;
        this.status = status == null ? "Pending" : status;
    }

    private static String generateID() {
        long number = 1_000_000_000L + new Random().nextInt(100_000_000);
        return String.valueOf(number);
    }

    public boolean checkMessageID() { return messageID.length() <= 10; }

    public String checkRecipientCell() {
        return recipient.matches("\\+27[0-9]{9}")
                ? "Cell phone number successfully captured."
                : "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText.isEmpty() || messageText.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }
        return "Message ready to send.";
    }

    public String createMessageHash() {
        String trimmed = messageText.trim();
        String[] words = trimmed.isEmpty() ? new String[]{"EMPTY"} : trimmed.split("\\s+");
        String first = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }

    public String SentMessage(Scanner input) {
        System.out.println("\nChoose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message");
        System.out.print("Enter your choice: ");
        String choice = input.nextLine();
        switch (choice) {
            case "1":
                status = "Sent";
                sentMessages = Arrays.copyOf(sentMessages, sentCount + 1);
                sentMessages[sentCount++] = this;
                totalMessagesSent++;
                return "Message successfully sent.";
            case "2":
                status = "Disregarded";
                return "Message disregarded.";
            case "3":
                status = "Stored";
                return storeMessage() ? "Message successfully stored." : "Message could not be stored.";
            default:
                return "Invalid option. Message not sent.";
        }
    }

    public String printMessages() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < sentCount; i++) {
            Message msg = sentMessages[i];
            output.append("Message ID: ").append(msg.messageID).append("\n")
                  .append("Message Hash: ").append(msg.messageHash).append("\n")
                  .append("Recipient: ").append(msg.recipient).append("\n")
                  .append("Message: ").append(msg.messageText).append("\n\n");
        }
        return output.toString();
    }
    public int returnTotalMessagess() { return totalMessagesSent; }

    // Writes a valid JSON array. Existing legacy comma-separated objects are retained.
    public boolean storeMessage() {
        try {
            Path file = Path.of("messages.json");
            String old = Files.exists(file) ? Files.readString(file, StandardCharsets.UTF_8).trim() : "";
            StringBuilder json = new StringBuilder("[\n");
            boolean first = true;
            Matcher matcher = Pattern.compile("\\{[^{}]*\\}", Pattern.DOTALL).matcher(old);
            while (matcher.find()) {
                if (!first) json.append(",\n");
                json.append("  ").append(matcher.group().trim());
                first = false;
            }
            if (!first) json.append(",\n");
            json.append("  {\n")
                .append("    \"messageID\": \"").append(escape(messageID)).append("\",\n")
                .append("    \"messageHash\": \"").append(escape(messageHash)).append("\",\n")
                .append("    \"messageNumber\": ").append(messageNumber).append(",\n")
                .append("    \"sender\": \"").append(escape(sender)).append("\",\n")
                .append("    \"recipient\": \"").append(escape(recipient)).append("\",\n")
                .append("    \"message\": \"").append(escape(messageText)).append("\",\n")
                .append("    \"status\": \"Stored\"\n")
                .append("  }\n]");
            Files.writeString(file, json.toString(), StandardCharsets.UTF_8);
            return true;
        } catch (IOException ex) {
            System.out.println("Storage error: " + ex.getMessage());
            return false;
        }
    }

    static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
    static String readJsonString(String object, String key) {
        Matcher m = Pattern.compile("\\\"" + Pattern.quote(key) + "\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"\\\\])*)\\\"", Pattern.DOTALL).matcher(object);
        if (!m.find()) return "";
        return m.group(1).replace("\\n", "\n").replace("\\r", "\r")
                .replace("\\t", "\t").replace("\\\"", "\"").replace("\\\\", "\\");
    }
    static int readJsonInt(String object, String key, int fallback) {
        Matcher m = Pattern.compile("\\\"" + Pattern.quote(key) + "\\\"\\s*:\\s*(\\d+)").matcher(object);
        return m.find() ? Integer.parseInt(m.group(1)) : fallback;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public int getMessageNumber() { return messageNumber; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
