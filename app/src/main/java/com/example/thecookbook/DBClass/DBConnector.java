package com.example.thecookbook.DBClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnector extends SQLiteOpenHelper {

    public DBConnector (Context context){
        super(context,"CookBookDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table UserDetails (UserID INTEGER PRIMARY KEY AUTOINCREMENT,UserName Varchar,Password VARCHAR,Img BLOB,SubStatus Varchar DEFAULT 'Basic')");
        db.execSQL("create table InfluencerDetails (InfID INTEGER PRIMARY KEY AUTOINCREMENT,InfName Varchar,Password VARCHAR,Bio Varchar,Img BLOB,SubCount INTEGER DEFAULT 0,TotalLikes INTEGER DEFAULT 0,TotalComments INTEGER DEFAULT 0,Level INTEGER DEFAULT 1)");
        db.execSQL("create table Recipe (RecipeID INTEGER PRIMARY KEY AUTOINCREMENT,RecipeName Varchar,Description TEXT,SubType Varchar,VidLink Varchar,Origin Varchar,Category Varchar,InfID INTEGER,LikeCount INTEGER DEFAULT 0,CommentCount Integer DEFAULT 0,Status Varchar DEFAULT 'Pending',RecipeImg BLOB,Date Varchar,FOREIGN KEY(InfID) REFERENCES InfluencerDetails(InfID))");
        db.execSQL("create table Ingredients (RecipeID INTEGER,IngNo INTEGER,IngName Varchar,IngQuantity Varchar,FOREIGN KEY(RecipeID) REFERENCES Recipe(RecipeID))");
        db.execSQL("create table Instructions (RecipeID INTEGER,InstructionNo INTEGER,InstructionDetail Varchar,Time INTEGER,FOREIGN KEY(RecipeID) REFERENCES Recipe(RecipeID))");
        db.execSQL("create table Comments (CommentID INTEGER PRIMARY KEY AUTOINCREMENT,RecipeID INTEGER,UserID INTEGER,Comment Varchar,Reply Varchar,FOREIGN KEY(RecipeID) REFERENCES Recipe(RecipeID),FOREIGN KEY(UserID) REFERENCES UserDetails(UserID))");
        db.execSQL("create table SubscribeList (InfID INTEGER,UserID INTEGER,FOREIGN KEY(InfID) REFERENCES InfluencerDetails(InfID),FOREIGN KEY(UserID) REFERENCES UserDetails(UserID))");
        db.execSQL("create table LikeList (RecipeID INTEGER,UserID INTEGER,FOREIGN KEY(RecipeID) REFERENCES Recipe(RecipeID),FOREIGN KEY(UserID) REFERENCES UserDetails(UserID))");
        db.execSQL("create table FavouriteList (RecipeID INTEGER,UserID INTEGER,FOREIGN KEY(RecipeID) REFERENCES Recipe(RecipeID),FOREIGN KEY(UserID) REFERENCES UserDetails(UserID))");
        db.execSQL("create table Updates (UpdateID INTEGER PRIMARY KEY AUTOINCREMENT,InfID INTEGER,UpdateDetail Varchar,Date Varchar,UpdateImg BLOB,FOREIGN KEY(InfID) REFERENCES InfluencerDetails(InfID))");
        db.execSQL("create table Salary (InfID INTEGER PRIMARY KEY,LastPaidLevel INTEGER DEFAULT 1,LastPaidDate Varchar DEFAULT 'No Date yet',LastPaidSalary DOUBLE DEFAULT 0.00,TotalSalary DOUBLE DEFAULT 0.00,FOREIGN KEY(InfID) REFERENCES InfluencerDetails(InfID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
