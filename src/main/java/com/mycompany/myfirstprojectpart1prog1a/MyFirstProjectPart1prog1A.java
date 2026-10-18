/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.myfirstprojectpart1prog1a;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class MyFirstProjectPart1prog1A {
    
    public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            
        System.out.println("Enter first name"); //Asks for user's name
        String firstName = input.nextLine();
        
         System.out.println("Enter last name"); //Asks for users's lastname/surname
        String lastName = input.nextLine();
        
        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter SA phone number (+27...): ");
        String phone = input.nextLine();
        
        Login login = new Login(username, password, phone);

        // ckeckUsername
        if (login.checkUserName(username)) {
            System.out.println("Username successfuUlly captured.");
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length");
            return;
        }

        // checkPassword
        if (login.checkPasswordComplexity(password)) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character"
);
            return;
        }

        // checkPhonenumber
        if (login.checkCellPhoneNumber(phone)) {
            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code");
            return;
        }

        // Login
        System.out.println("\n=====Login======:");
        System.out.print("Username: ");
        String userLogin = input.nextLine();

        System.out.print("Password: ");
        String passLogin = input.nextLine();

        if (login.loginUser(userLogin, passLogin, username, password)) {
            System.out.println("Welcome " + username + ", it is great to see you again.");
        }
        
        if (login.loginUser(userLogin, passLogin, username, password)) {
System.out.println("Welcome " + firstName + ", "
+ lastName + " it is great to see you again.");
System.out.println("\nWelcome to QuickChat.");
System.out.print("How many messages would you like to enter? ");
int maxMessages = Integer.parseInt(input.nextLine());
int messagesCreated = 0;
int menuChoice = 0;
while (menuChoice != 3) {
System.out.println("===== QuickChat Menu =====");
System.out.println("1. Send Messages");
System.out.println("2. Show recently sent messages");
System.out.println("3. Quit");
System.out.print("Choose an option: ");
menuChoice = Integer.parseInt(input.nextLine());
switch (menuChoice) {
case 1:
if (messagesCreated >= maxMessages) {
System.out.println(
"You have reached your message limit.");
break;
}
// The for loop limits message entry to the chosen number.
for (int i = messagesCreated;
i < maxMessages; i++) {
System.out.println("Message "
+ (i + 1) + " of " + maxMessages);
System.out.print("Enter recipient number (+27...): ");
String recipient = input.nextLine();
Message message;
// Validate the recipient before creating the message.
// The expected format is +27 followed by 9 digits.
if (!recipient.matches("\\+27[0-9]{9}")) {
System.out.println("Cell phone number is incorrectly formatted "
+ "or does not contain an international code. "
+ "Please correct the number and try again.");
i--;
continue;
}
System.out.print("Enter your message: ");
String messageText = input.nextLine();
if (messageText.trim().isEmpty()
|| messageText.length() > 250) {
System.out.println("Please enter a non-empty message "
+ "of no more than 250 characters.");
i--;
continue;
}
Message newMessage = new Message(
i, recipient, messageText);
System.out.println("Message ID generated: "
+ newMessage.getMessageID());
System.out.println("Message Hash: "
+ newMessage.getMessageHash());
System.out.println(newMessage.checkMessageLength());
System.out.println(
newMessage.SentMessage(input));
System.out.println("\nMessage ID: "
+ newMessage.getMessageID());
System.out.println("Message Hash: "
+ newMessage.getMessageHash());
System.out.println("Recipient: "
+ newMessage.getRecipient());
System.out.println("Message: "
+newMessage.getMessageText());
messagesCreated++;
}
System.out.println("\nTotal messages sent: "
+ new Message(0, "+27718693002",
"Counter").returnTotalMessagess());
break;
case 2:
System.out.println("Coming Soon.");
break;
case 3:
System.out.println("Thank you for using QuickChat.");
break;
default:
System.out.println(
"Invalid option. Please choose 1, 2 or 3.");
}
}
        
        
        }else {
            System.out.println("Username or password incorrect, please try again.");
        }
    
      
    }
}
