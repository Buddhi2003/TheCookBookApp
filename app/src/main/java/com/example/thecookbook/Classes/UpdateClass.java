package com.example.thecookbook.Classes;

import android.graphics.Bitmap;

public class UpdateClass {
    private String UpdateStatus;
    private Bitmap UpdateImage;
    private int InfID;
    private String Date;

    public UpdateClass() {

    }

    public UpdateClass(String updateStatus, Bitmap updateImage, int infID, String date) {
        UpdateStatus = updateStatus;
        UpdateImage = updateImage;
        InfID = infID;
        Date = date;
    }

    public UpdateClass(String updateStatus, Bitmap updateImage, int infID) {
        UpdateStatus = updateStatus;
        UpdateImage = updateImage;
        InfID = infID;
    }


    public String getUpdateStatus() {
        return UpdateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        UpdateStatus = updateStatus;
    }

    public Bitmap getUpdateImage() {
        return UpdateImage;
    }

    public void setUpdateImage(Bitmap updateImage) {
        UpdateImage = updateImage;
    }

    public int getInfID() {
        return InfID;
    }

    public void setInfID(int infID) {
        InfID = infID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
