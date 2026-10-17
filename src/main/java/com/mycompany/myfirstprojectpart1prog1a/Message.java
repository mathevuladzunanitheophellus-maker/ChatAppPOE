/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myfirstprojectpart1prog1a;

/**
 *
 * @author Student
 */
public class Message {
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    // Constructor (used to create a Message object)
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Method to check if the message ID is valid (10 digits)
    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    // Method to check if the recipient number is valid
    public boolean checkRecipientCell() {
        return recipient.startsWith("+") && recipient.length() <= 10;
    }

    // Method to create a message hash (e.g., 00:0:HITHERE)
    public String createMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);
        String[] words = messageText.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String hash = firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();  // Make sure it's in all caps
    }

    // Method to simulate the message action: send, store, disregard
    public String sendMessageOption(int choice) {
        return switch (choice) {
            case 0 -> "Message successfully sent.";
            case 1 -> "Press 0 to delete message.";
            case 2 -> "Message successfully stored.";
            default -> "No action taken.";
        };
    }

    // Getter methods so we can access the private data
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    // Static counter to keep track of total messages sent
    private static int totalMessages = 0;

    public static void increaseTotalMessages() {
        totalMessages++;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }
    
}
   
