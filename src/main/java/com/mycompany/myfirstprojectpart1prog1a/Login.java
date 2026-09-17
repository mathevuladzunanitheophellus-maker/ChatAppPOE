/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myfirstprojectpart1prog1a;

/**
 *
 * @author Student
 */
public class Login {
    private String username;
    private String password;
    private String cellNumber;
    
    public Login(String username, String password, String cellNumber){
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }
    

    
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    
    public  boolean checkPasswordComplexity(String password) {
        boolean hasLength = password.length() >= 8;
        boolean hasCapital = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        
        return hasLength && hasCapital && hasNumber && hasSpecial;
    }

  
    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber.startsWith("+27") && cellNumber.length() ==12;
    }
    public boolean loginUser(String enteredUsername, String enteredPassword, String storedUseraname, String storedPassword){
        
        return enteredUsername.equals(storedUseraname) && enteredPassword.equals(storedPassword);
    }
    public String registerUser(){
    
    if (!checkUserName(username)) {
return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
}
if (!checkPasswordComplexity(password)) {
return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
}
if (!checkCellPhoneNumber(cellNumber)) {
return "Cell phone number incorrectly formatted or does not contain international code.";
}
return "Username successfully captured.\n" + "Password successfully captured.\n" + "Cell phone number successfully added.\n";

 }

    
    public String returnLoginStatus(){
        return "Login successful.";
    }
    }
