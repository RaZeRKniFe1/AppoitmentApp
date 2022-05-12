package com.example.appoitmentapp;

public class NewUser {
    String displayName,phoneNumber,email,password;


    public NewUser(String displayName, String password, String email, String phoneNumber) {
        this.displayName = displayName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public NewUser(){

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
