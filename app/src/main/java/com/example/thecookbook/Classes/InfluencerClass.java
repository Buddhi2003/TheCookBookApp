package com.example.thecookbook.Classes;

import android.graphics.Bitmap;

public class InfluencerClass {
     private int InfID;
     private String InfName;
     private String InfPassword;
     private String Bio;
     private Integer SubCount;
     private Integer TotalLikes;
     private Integer InfLevel;
     private double Income;
     private Bitmap InfprofImage;

     public InfluencerClass() {
     }

     public InfluencerClass(int infID, String infName, String infPassword, String bio, Integer subCount, Integer totalLikes, Integer infLevel, double income, Bitmap infprofImage) {
          InfID = infID;
          InfName = infName;
          InfPassword = infPassword;
          Bio = bio;
          SubCount = subCount;
          TotalLikes = totalLikes;
          InfLevel = infLevel;
          Income = income;
          InfprofImage = infprofImage;
     }


     public InfluencerClass(String infName, String infPassword, String bio, Bitmap infprofImage) {
          InfName = infName;
          InfPassword = infPassword;
          Bio = bio;
          InfprofImage = infprofImage;
     }

     public int getInfID() {
          return InfID;
     }

     public void setInfID(int infID) {
          InfID = infID;
     }

     public String getInfName() {
          return InfName;
     }

     public void setInfName(String infName) {
          InfName = infName;
     }

     public String getInfPassword() {
          return InfPassword;
     }

     public void setInfPassword(String infPassword) {
          InfPassword = infPassword;
     }

     public String getBio() {
          return Bio;
     }

     public void setBio(String bio) {
          Bio = bio;
     }

     public Integer getSubCount() {
          return SubCount;
     }

     public void setSubCount(Integer subCount) {
          SubCount = subCount;
     }

     public Integer getTotalLikes() {
          return TotalLikes;
     }

     public void setTotalLikes(Integer totalLikes) {
          TotalLikes = totalLikes;
     }

     public Integer getInfLevel() {
          return InfLevel;
     }

     public void setInfLevel(Integer infLevel) {
          InfLevel = infLevel;
     }

     public double getIncome() {
          return Income;
     }

     public void setIncome(double income) {
          Income = income;
     }

     public Bitmap getInfprofImage() {
          return InfprofImage;
     }

     public void setInfprofImage(Bitmap infprofImage) {
          InfprofImage = infprofImage;
     }
}
