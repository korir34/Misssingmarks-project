package com.example.missingmarksapp.Models;

public class users {
    String UserName, mail,password,userId;
    public users(String s, String toString, String string){

    }

    public users(String userName, String mail, String password, String userId) {
        UserName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


