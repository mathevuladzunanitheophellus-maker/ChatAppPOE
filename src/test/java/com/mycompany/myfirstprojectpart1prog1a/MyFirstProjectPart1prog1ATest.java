/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.myfirstprojectpart1prog1a;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Student
 */

public class MyFirstProjectPart1prog1ATest {

    @Test
    public void testUsernameCorrect() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        assertTrue(login.checkUserName("ky_1"));
    }

    @Test
    public void testUsernameIncorrect() {
        Login login = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");

        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordCorrect() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordIncorrect() {
        Login login = new Login("kyl_1", "password", "+27838968976");

        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneCorrect() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneIncorrect() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "08966553");

        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}