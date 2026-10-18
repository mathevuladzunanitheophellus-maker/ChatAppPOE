/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myfirstprojectpart1prog1a;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Message {
private final String messageID;
private final int messageNumber;
private final String recipient;
private final String messageText;
private final String messageHash;

private String status;
private static int totalMessagesSent = 0;
private static final List<Message> sentMessages =
new ArrayList<>();
public Message(int messageNumber, String recipient,
String messageText) {
this.messageNumber = messageNumber;
this.recipient = recipient;
this.messageText = messageText;
this.messageID = generateMessageID();
this.messageHash = createMessageHash();
this.status = "Pending";
}
private String generateMessageID() {
long number = 1_000_000_000L;
new Random().nextInt(900_000_000);
return String.valueOf(number);
}
public boolean checkMessageID() {
return messageID.length() <= 10;
}
public String checkRecipientCell() {
// Uses the international format +27 followed by 9 digits.
if (recipient != null
&& recipient.matches("\\+27[0-9]{9}")) {
return "Cell phone number successfully captured.";
}
return "Cell phone number is incorrectly formatted or "
+"does not contain an international code. "
+"Please correct the number and try again.";
}
public String checkMessageLength() {
if (messageText == null) {
return "Please enter a message of less than " + "250 characters.";
}
if (messageText.length() <= 250) {
return "Message ready to send.";
}
int excess = messageText.length() - 250;
return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
}
public String createMessageHash() {
String[] words = messageText.trim().split("\s+");
String firstWord = words[0].replaceAll(
"[^a-zA-Z0-9]", "");
String lastWord = words[words.length - 1].replaceAll(
"[^a-zA-Z0-9]", "");
String firstTwoDigits = messageID.substring(0, 2);
return (firstTwoDigits + ":"
+messageNumber + ":"
+ firstWord + lastWord).toUpperCase();
}
public String SentMessage(Scanner input) {
System.out.println("Choose an option:");
System.out.println("1. Send Message");
System.out.println("2. Disregard Message");
System.out.println("3. Store Message");
System.out.print("Enter your choice: ");
String choice = input.nextLine();
switch (choice) {
case "1":
status = "Sent";
sentMessages.add(this);
totalMessagesSent++;
return "Message successfully sent.";
case "2":
status = "Disregarded";
return "Press 0 to delete the message.";
case "3":
status = "Stored";
if (storeMessage()) {
return "Message successfully stored.";
}
return "Message could not be stored.";
default:
return "Invalid option. Message not sent.";
}
}
public String printMessages() {
StringBuilder output = new StringBuilder();
for (Message msg : sentMessages) {
output.append("Message ID: ")
.append(msg.messageID).append("\n");
output.append("Message Hash: ")
.append(msg.messageHash).append("\n");
output.append("Recipient: ")
.append(msg.recipient).append("\n");
output.append("Message: ")
.append(msg.messageText).append("\n");
}
return output.toString();
}
public int returnTotalMessagess() {
return totalMessagesSent;
}
public boolean storeMessage() {
try (FileWriter writer = new FileWriter(
"messages.json", true)) {
writer.write("{\n");
writer.write(" \"messageID\": \""
+ escapeJson(messageID) + "\",\n");
writer.write(" \"messageHash\": \""
+ escapeJson(messageHash) + "\",\n");
writer.write(" \"messageNumber\": "
+ messageNumber + ",\n");
writer.write(" \"recipient\": \""
+ escapeJson(recipient) + "\",\n");
writer.write(" \"message\": \""
+ escapeJson(messageText) + "\",\n");
writer.write(" \"status\": \"Stored\"\n");
writer.write("},\n");

return true;
} catch (IOException e) {
System.out.println("Storage error: " + e.getMessage());
return false;
}
}
private String escapeJson(String text) {
return text.replace("\\", "\\\\")
         .replace("\"", "\\\"")
         .replace("\n", "\\n")
         .replace("\r", "\r")
         .replace("\t", "\t");
}
public String getMessageID() {
return messageID;
}
public String getMessageHash() {
return messageHash;
}
public String getRecipient() {
return recipient;
}
public String getMessageText() {
return messageText;
}
public String getStatus() {
return status;
}
}
