package com.example.thecookbook.Classes;

import android.graphics.Bitmap;

public class UserClass {
    private int UserID;
    private String UserName;
    private String Password;
    private Bitmap ProfileImage;
    private int SubscriptionID;

    public UserClass(int userID,String username, String password, Bitmap profileImage, int subscriptionID) {
        UserID = userID;
        UserName = username;
        Password = password;
        ProfileImage = profileImage;
        SubscriptionID = subscriptionID;
    }
    public UserClass(){
    }

    public UserClass(String userName, String password, Bitmap profileImage) {
        UserName = userName;
        Password = password;
        ProfileImage = profileImage;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Bitmap getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        ProfileImage = profileImage;
    }

    public int getSubscriptionID() {
        return SubscriptionID;
    }

    public void setSubscriptionID(int subscriptionID) {
        SubscriptionID = subscriptionID;
    }
}
