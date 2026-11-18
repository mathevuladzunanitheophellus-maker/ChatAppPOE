package com.mycompany.myfirstprojectpart1prog1a;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class MessageManagerTest {
    private MessageManager manager;
    private Message m1, m2, m3, m4, m5;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(Path.of("messages.json"), "[]");
        manager = new MessageManager(20);
        m1 = new Message(1, "0830000000", "+27834557896", "Did you get the cake?");
        m1.setStatus("Sent");
        m2 = new Message(2, "0830000000", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.setStatus("Stored");
        m3 = new Message(3, "0830000000", "+27834484567", "Yohoooo, I am at your gate.");
        m3.setStatus("Disregarded");
        m4 = new Message(4, "0838884567", "+27834557896", "It is dinner time!");
        m4.setStatus("Sent");
        m5 = new Message(5, "0830000000", "+27838884567", "Ok, I am leaving without you.");
        m5.setStatus("Stored");
        manager.addMessage(m1);
        manager.addMessage(m2);
        manager.addMessage(m3);
        manager.addMessage(m4);
        manager.addMessage(m5);
    }

    @Test
    void sentMessagesArrayIsPopulatedCorrectly() {
        assertEquals("Did you get the cake?" + System.lineSeparator() + "It is dinner time!", manager.getSentMessagesText());
    }

    @Test
    void displaysLongestStoredMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", manager.getLongestStoredMessage());
    }

    @Test
    void searchesByMessageIdOrDeveloperSender() {
        String result = manager.searchByMessageID("0838884567");
        assertTrue(result.contains("It is dinner time!"));
    }

    @Test
    void searchesAllStoredMessagesForRecipient() {
        assertEquals("Where are you? You are late! I have asked you to be on time." + System.lineSeparator() + "Ok, I am leaving without you.", manager.searchByRecipient("+27838884567"));
    }

    @Test
    void deletesStoredMessageUsingHash() {
        String result = manager.deleteByHash(m2.getMessageHash());
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
        assertEquals(1, manager.getStoredCount());
    }

    @Test
    void displaysFullStoredMessageReport() {
        String report = manager.displayStoredReport();
        assertTrue(report.contains(m2.getMessageHash()));
        assertTrue(report.contains("+27838884567"));
        assertTrue(report.contains(m2.getMessageText()));
        assertTrue(report.contains(m5.getMessageText()));
    }
    @Test
    void displaysStoredSendersAndRecipients() {
        String result = manager.displayStoredSendersAndRecipients();
        assertTrue(result.contains("0830000000"));
        assertTrue(result.contains("+27838884567"));
    }

    @Test
    void readsStoredMessagesFromJsonFile() {
        Message saved = new Message(8, "0838884567", "+27838884567", "Read this from JSON.");
        assertTrue(saved.storeMessage());
        MessageManager reloaded = new MessageManager(20);
        assertTrue(reloaded.getStoredCount() >= 1);
        assertTrue(reloaded.searchByRecipient("+27838884567").contains("Read this from JSON."));
    }

}
