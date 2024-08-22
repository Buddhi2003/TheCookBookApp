package com.example.thecookbook.Classes;

import android.graphics.Bitmap;

public class RecipeClass {
    private int RecipeID;
    private String RecipeName;
    private String Status;
    private String Description;
    private String SubType;
    private String Category;
    private String Origin;
    private String Video;
    private int InfID;
    private int LikeCount;
    private int CommentCount;
    private Bitmap RecipeImage;
    private String Date;

    public RecipeClass(int recipeID, String recipeName, String status, String description, String subtype, String category, String origin, String video, int infID, int likeCount, int commentCount, Bitmap recipeImage, String date) {
        RecipeID = recipeID;
        RecipeName = recipeName;
        Status = status;
        Description = description;
        SubType = subtype;
        Category = category;
        Origin = origin;
        Video = video;
        InfID = infID;
        LikeCount = likeCount;
        CommentCount = commentCount;
        RecipeImage = recipeImage;
        Date = date;
    }
    public RecipeClass(){
    }

    public RecipeClass(String recipeName, String description,  String category, String origin,int infID,String subtype, String video, Bitmap recipeImage) {
        RecipeName = recipeName;
        Description = description;
        SubType = subtype;
        Category = category;
        InfID = infID;
        Origin = origin;
        Video = video;
        RecipeImage = recipeImage;
    }

    public int getRecipeID() {
        return RecipeID;
    }

    public void setRecipeID(int recipeID) {
        RecipeID = recipeID;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSubType() {
        return SubType;
    }

    public void setSubType(String subType) {
        SubType = subType;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public int getInfID() {
        return InfID;
    }

    public void setInfID(int infID) {
        InfID = infID;
    }

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int likeCount) {
        LikeCount = likeCount;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public Bitmap getRecipeImage() {
        return RecipeImage;
    }

    public void setRecipeImage(Bitmap recipeImage) {
        RecipeImage = recipeImage;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
