package com.example.thecookbook.Classes;

public class CommentClass {
    private int CommentID;
    private String Comment;
    private String Reply;
    private int UserID;
    private int RecipeID;

    public CommentClass(int commentID, String comment, String reply, int userID, int recipeID) {
        CommentID = commentID;
        Comment = comment;
        Reply = reply;
        UserID = userID;
        RecipeID = recipeID;
    }
    public CommentClass() {
    }

    public CommentClass(String comment, int userID, int recipeID) {
        Comment = comment;
        UserID = userID;
        RecipeID = recipeID;
    }

    public CommentClass(int commentID, String reply) {
        CommentID = commentID;
        Reply = reply;
    }

    public int getCommentID() {
        return CommentID;
    }

    public void setCommentID(int commentID) {
        CommentID = commentID;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getReply() {
        return Reply;
    }

    public void setReply(String reply) {
        Reply = reply;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getRecipeID() {
        return RecipeID;
    }

    public void setRecipeID(int recipeID) {
        RecipeID = recipeID;
    }
}
