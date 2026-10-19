/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.myfirstprojectpart1prog1a;

import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {
    
    @Test
    void testMessageIDLength() {
        Message message = new Message(
                0, "+27718693002", "Hello there"
        );

        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    }

    @Test
    void testRecipientCellNumber() {
        Message message = new Message(
                0, "+27718693002", "Hello there"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    void testMessageLengthUnder250Characters() {
        Message message = new Message(
                0, "+27718693002", "Hello, how are you?"
        );

        
    }

    @Test
    void testMessageHash() {
        Message message = new Message(
                0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertNotNull(message.createMessageHash());
        assertFalse(message.createMessageHash().isEmpty());
    }

    @Test
    void testMessageCanBeCreated() {
        Message message = new Message(
                0, "+27718693002", "Hello there"
        );

        assertEquals("Hello there", message.getMessageText());
        assertEquals("+27718693002", message.getRecipient());
    }

    @Test
    void testStoreMessage() {
        Message message = new Message(
                0, "+27718693002", "This is a stored test message"
        );

        assertTrue(message.storeMessage());
    }


    }

