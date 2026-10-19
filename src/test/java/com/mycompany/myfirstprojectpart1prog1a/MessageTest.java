/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.myfirstprojectpart1prog1a;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {
    
      // Test 1: Message ID must contain 10 digits
    @Test
    void testMessageID() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    }

    // Test 2: Message must not exceed 250 characters
    @Test
    void testMessageLengthSuccess() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    // Test 3: Recipient number must be correctly formatted
    @Test
    void testRecipientNumberSuccess() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    // Test 4: Message hash must match the assignment
    @Test
    void testMessageHash() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "10:0:HITONIGHT",
                message.createMessageHash()
        );
    }
    //Test 5
    @Test
void testSendMessage() {
    Message message = new Message(
            0,
            "+27718693002",
            "Hi Mike, can you join us for dinner tonight?"
    );

    java.util.Scanner input = new java.util.Scanner("1\n");

    String result = message.SentMessage(input);

    assertEquals("Message successfully sent.", result);
}
//Test 6
@Test
void testDisregardMessage() {
    Message message = new Message(
            1,
            "08575975889",
            "Hi Keegan, did you receive the payment?"
    );

    java.util.Scanner input = new java.util.Scanner("2\n");

    String result = message.SentMessage(input);

    assertEquals("Press 0 to delete the message.", result);
}


    
    // Test 7: Storing a message should save it successfully
    @Test
    void testStoreMessage() {
        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.storeMessage());
    }
}