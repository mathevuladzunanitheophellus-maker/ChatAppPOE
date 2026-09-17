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
            System.out.println("Username successfully captured.");
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
        } else {
            System.out.println("Username or password incorrect, please try again.");
        }
    
      
    }
}
