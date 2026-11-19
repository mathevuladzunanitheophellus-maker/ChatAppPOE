package com.mycompany.myfirstprojectpart1prog1a;

import java.util.Scanner;

public class MyFirstProjectPart1prog1A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter first name");//The code allows the user to enter their names first
        String firstName = input.nextLine();
        System.out.println("Enter last name"); //The user must also enter their surname before they enter theur usernames and password
        String lastName = input.nextLine();
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        System.out.print("Enter SA phone number (+27...): ");
        String phone = input.nextLine();

        Login login = new Login(username, password, phone);
        if (!login.checkUserName(username)) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length");
            return;
        }
        System.out.println("Username successfully captured.");
        if (!login.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character");
            return;
        }
        System.out.println("Password successfully captured.");
        if (!login.checkCellPhoneNumber(phone)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code");
            return;
        }
        System.out.println("Cell phone number successfully added.");

        System.out.println("\n===== Login =====");
        System.out.print("Username: ");
        String userLogin = input.nextLine();
        System.out.print("Password: ");
        String passLogin = input.nextLine();
        if (!login.loginUser(userLogin, passLogin, username, password)) {
            System.out.println("Username or password incorrect, please try again.");
            return;
        }

        System.out.println("Welcome " + firstName + ", " + lastName + " it is great to see you again.");
        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages would you like to enter? ");
        int maxMessages;
        try { maxMessages = Integer.parseInt(input.nextLine()); }
        catch (NumberFormatException ex) { System.out.println("Please enter a valid number."); return; }
        if (maxMessages < 1) { System.out.println("Enter at least one message."); return; }

        MessageManager manager = new MessageManager(maxMessages + 100);
        int messagesCreated = 0;
        int menuChoice = 0;
        while (menuChoice != 3) {
            System.out.println("\n===== QuickChat Menu =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");
            try { menuChoice = Integer.parseInt(input.nextLine()); }
            catch (NumberFormatException ex) { System.out.println("Please enter a number."); continue; }

            switch (menuChoice) {
                case 1:
                    if (messagesCreated >= maxMessages) {
                        System.out.println("You have reached your message limit.");
                        break;
                    }
                    while (messagesCreated < maxMessages) {
                        int number = messagesCreated + 1;
                        System.out.println("\nMessage " + number + " of " + maxMessages);
                        System.out.print("Enter recipient number (+27...): ");
                        String recipient = input.nextLine();
                        if (!recipient.matches("\\+27[0-9]{9}")) {
                            System.out.println("Cell phone number is incorrectly formatted. Please try again.");
                            continue;
                        }
                        System.out.print("Enter your message: ");
                        String messageText = input.nextLine();
                        if (messageText.trim().isEmpty() || messageText.length() > 250) {
                            System.out.println("Please enter a non-empty message of no more than 250 characters.");
                            continue;
                        }
                        Message newMessage = new Message(number, phone, recipient, messageText);
                        System.out.println("Message ID generated: " + newMessage.getMessageID());
                        System.out.println("Message Hash: " + newMessage.getMessageHash());
                        System.out.println(newMessage.checkMessageLength());
                        System.out.println(newMessage.SentMessage(input));
                        manager.addMessage(newMessage);
                        System.out.println("Message ID: " + newMessage.getMessageID());
                        System.out.println("Message Hash: " + newMessage.getMessageHash());
                        System.out.println("Sender: " + newMessage.getSender());
                        System.out.println("Recipient: " + newMessage.getRecipient());
                        System.out.println("Message: " + newMessage.getMessageText());
                        messagesCreated++;
                        System.out.print("Enter another message? (yes/no): ");
                        if (!input.nextLine().equalsIgnoreCase("yes")) break;
                    }
                    System.out.println("Total messages sent: " + manager.getSentCount());
                    break;
                case 2:
                    String sent = manager.getSentMessagesText();
                    System.out.println(sent.isEmpty() ? "No sent messages yet." : sent);
                    break;
                case 3:
                    System.out.println("Thank you for using QuickChat.");
                    break;
                case 4:
                    storedMessagesMenu(input, manager);
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3 or 4.");
            }
        }
    }

    private static void storedMessagesMenu(Scanner input, MessageManager manager) {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n===== Stored Messages =====");
            System.out.println("1. Display sender and recipient of stored messages");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search by message ID");
            System.out.println("4. Search messages by recipient");
            System.out.println("5. Delete a stored message using its hash");
            System.out.println("6. Display stored-message report");
            System.out.println("0. Return to main menu");
            System.out.print("Choose an option: ");
            try { choice = Integer.parseInt(input.nextLine()); }
            catch (NumberFormatException ex) { System.out.println("Please enter a number."); continue; }
            switch (choice) {
                case 1: System.out.println(manager.displayStoredSendersAndRecipients()); break;
                case 2: System.out.println(manager.getLongestStoredMessage()); break;
                case 3:
                    System.out.print("Enter message ID: ");
                    System.out.println(manager.searchByMessageID(input.nextLine()));
                    break;
                case 4:
                    System.out.print("Enter recipient number: ");
                    System.out.println(manager.searchByRecipient(input.nextLine()));
                    break;
                case 5:
                    System.out.print("Enter message hash: ");
                    System.out.println(manager.deleteByHash(input.nextLine()));
                    break;
                case 6: System.out.println(manager.displayStoredReport()); break;
                case 0: break;
                default: System.out.println("Invalid option.");
            }
        }
    }
}
