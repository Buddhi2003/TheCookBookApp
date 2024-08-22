package com.example.thecookbook.DBClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.thecookbook.Classes.CommentClass;
import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.Classes.IngredientClass;
import com.example.thecookbook.Classes.InstructionClass;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.Classes.SalaryClass;
import com.example.thecookbook.Classes.UpdateClass;
import com.example.thecookbook.Classes.UserClass;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
    private Context context;
    private SQLiteDatabase database;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imginbyte;
    private static final double PREMIUM_MEMBERSHIP_PRICE = 10000.00;
    private static final double BASE_SALARY_PERCENTAGE = 0.20;

    public DBHelper(Context context) {
        this.context = context;
    }

    public DBHelper OpenDB() {
        DBConnector dbcon = new DBConnector(context);
        database = dbcon.getWritableDatabase();
        return this;
    }

    public boolean CreateNewUser(UserClass userClass) {
        Bitmap imagetostorebitmap = userClass.getProfileImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imginbyte = byteArrayOutputStream.toByteArray();

        try {
            ContentValues values = new ContentValues();
            values.put("UserName", userClass.getUserName());
            values.put("Password", userClass.getPassword());
            values.put("Img", imginbyte);

            long rowId = database.insert("UserDetails", null, values);
            if (rowId != -1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception c) {
            Toast.makeText(context.getApplicationContext(), c.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean CreateNewInfluencer(InfluencerClass influencer) {
        Bitmap imagetostorebitmap = influencer.getInfprofImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imginbyte = byteArrayOutputStream.toByteArray();
        try {
            ContentValues values = new ContentValues();
            values.put("InfName", influencer.getInfName());
            values.put("Bio", influencer.getBio());
            values.put("Password", influencer.getInfPassword());
            values.put("Img", imginbyte);
            long rowID = database.insert("InfluencerDetails", null, values);
            if (rowID != -1) {
                ContentValues salaryvalue = new ContentValues();
                salaryvalue.put("InfID", (int) rowID);
                long row2ID=database.insert("Salary", null, salaryvalue);
                if(row2ID!=-1){
                    return true;
                }else{
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getDetails() {
        Cursor cursor = database.rawQuery("Select * from UserDetails", null);
        return cursor;
    }


    public int CheckLoginUser(String UserName, String Password) {
        int UserID = 0;
        try {
            Cursor cursor = database.rawQuery("Select * from UserDetails where UserName='" + UserName + "' and Password='" + Password + "'", null);
            if (cursor.moveToFirst()) {
                UserID = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserID;
    }
    public boolean checkuserexist(String UserName,String Password){
        boolean isexist = false;
        try{
            Cursor cursor = database.rawQuery("Select * from UserDetails where UserName='" + UserName + "' and Password='" + Password + "'", null);
            if (cursor.moveToFirst()) {
                isexist = true;
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isexist;
    }

    public int CheckLoginInfluencer(String Name, String Password) {
        int InfID = 0;
        try {
            Cursor cursor = database.rawQuery("Select * from InfluencerDetails where InfName='" + Name + "' and Password='" + Password + "'", null);
            if (cursor.moveToFirst()) {
                InfID = cursor.getInt( 0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return InfID;
    }
    public boolean checkinfexist(String Name,String Password){
        boolean isexist = false;
        try{
            Cursor cursor = database.rawQuery("Select * from InfluencerDetails where InfName='" + Name + "' and Password='" + Password + "'", null);
            if (cursor.moveToFirst()) {
                isexist = true;
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isexist;
    }


    public int AddnewRecipe(RecipeClass recipe) {
        int RecipeID = -1;
        int maxsize = 1024 * 1024;
        Bitmap imagetostorebitmap = recipe.getRecipeImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imginbyte = byteArrayOutputStream.toByteArray();
        if (imginbyte.length > maxsize) {
            byteArrayOutputStream.reset();
            imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            imginbyte = byteArrayOutputStream.toByteArray();
        }
        try {
            ContentValues values = new ContentValues();
            values.put("RecipeName", recipe.getRecipeName());
            values.put("Description", recipe.getDescription());
            values.put("Origin", recipe.getOrigin());
            values.put("Category", recipe.getCategory());
            values.put("RecipeImg", imginbyte);
            values.put("InfID", recipe.getInfID());
            values.put("SubType", recipe.getSubType());
            values.put("VidLink", recipe.getVideo());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());
            values.put("Date", currentDate);
            long rowID = database.insert("Recipe", null, values);
            if (rowID != -1) {
                RecipeID = (int) rowID;
            }
        } catch (Exception c) {
            c.printStackTrace();
            Toast.makeText(context, c.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return RecipeID;
    }

    public boolean addIngredients(List<IngredientClass> ingredients, int RecipeID) {
        int IngredientNo = 1;
        boolean insertingredientstatus = true;
        try {
            for (int i = 0; i < ingredients.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("RecipeID", RecipeID);
                values.put("IngNo", IngredientNo);
                values.put("IngName", ingredients.get(i).getIngredientName());
                values.put("IngQuantity", ingredients.get(i).getIngredientQuantity());
                long result = database.insert("Ingredients", null, values);
                if (result == -1) {
                    insertingredientstatus = false;
                    break;
                }
                IngredientNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            insertingredientstatus = false;
        }
        return insertingredientstatus;
    }

    public boolean addInstructions(List<InstructionClass> instructions, int RecipeID) {
        int InstructionNo = 1;
        boolean insertinstructionstatus = true;
        try {
            for (int i = 0; i < instructions.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("RecipeID", RecipeID);
                values.put("InstructionNo", InstructionNo);
                values.put("InstructionDetail", instructions.get(i).getInstructionDetail());
                values.put("Time", instructions.get(i).getTime());
                long result = database.insert("Instructions", null, values);
                if (result == -1) {
                    insertinstructionstatus = false;
                    break;
                }
                InstructionNo++;
            }
        } catch (Exception c) {
            c.printStackTrace();
            insertinstructionstatus = false;
        }
        return insertinstructionstatus;
    }


    public List<RecipeClass> getRecipes() {
        List<RecipeClass> recipes = new ArrayList<>();
        Cursor cursor;
        try {
            cursor = database.rawQuery("Select * from Recipe where Status='Confirmed' order by LikeCount desc", null);
            if (cursor.moveToFirst()) {
                do {
                    RecipeClass recipe = new RecipeClass();
                    recipe.setRecipeID(cursor.getInt(0));
                    recipe.setRecipeName(cursor.getString(1));
                    recipe.setDescription(cursor.getString(2));
                    recipe.setOrigin(cursor.getString(5));
                    recipe.setCategory(cursor.getString(6));
                    recipe.setInfID(cursor.getInt(7));
                    recipe.setDate(cursor.getString(12));
                    recipe.setLikeCount(cursor.getInt(8));
                    recipe.setSubType(cursor.getString(3));
                    byte[] imageByte = cursor.getBlob(11);
                    if (imageByte != (null) && imageByte.length > 0) {
                        Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                        recipe.setRecipeImage(image);
                    }
                    recipes.add(recipe);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public List<RecipeClass> getRecipesfromInfluencer(int InfID) {
        List<RecipeClass> recipes = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from Recipe where InfID="+InfID+" and Status='Confirmed'", null);
        if (cursor.moveToFirst()) {
            do {
                RecipeClass recipe = new RecipeClass();
                recipe.setRecipeID(cursor.getInt(0));
                recipe.setRecipeName(cursor.getString(1));
                recipe.setDescription(cursor.getString(2));
                recipe.setOrigin(cursor.getString(5));
                recipe.setSubType(cursor.getString(3));
                recipe.setCategory(cursor.getString(6));
                recipe.setInfID(cursor.getInt(7));
                byte[] imageByte = cursor.getBlob(11);
                if (imageByte != (null) && imageByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    recipe.setRecipeImage(image);
                }
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipes;
    }

    public List<RecipeClass> getsubscribedRecipes(int UserID) {
        List<InfluencerClass> influenceridlist = new ArrayList<>();
        List<RecipeClass> recipelist = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("Select * from SubscribeList where UserID="+ UserID, null);
            if (cursor.moveToFirst()) {
                do {
                    InfluencerClass influencer = new InfluencerClass();
                    influencer.setInfID(cursor.getInt(0));
                    influenceridlist.add(influencer);
                } while (cursor.moveToNext());
            }
            cursor.close();
            for (InfluencerClass influencer : influenceridlist) {
                Cursor cursor2 = database.rawQuery("Select * from Recipe where InfID=" + influencer.getInfID() + " and Status='Confirmed'", null);
                if (cursor2.moveToFirst()) {
                    do {
                        RecipeClass recipe = new RecipeClass();
                        recipe.setRecipeID(cursor2.getInt(0));
                        recipe.setRecipeName(cursor2.getString(1));
                        recipe.setDescription(cursor2.getString(2));
                        recipe.setOrigin(cursor2.getString(5));
                        recipe.setCategory(cursor2.getString(6));
                        recipe.setInfID(cursor2.getInt(7));
                        recipe.setLikeCount(cursor2.getInt(8));
                        recipe.setSubType(cursor2.getString(3));
                        byte[] imageByte = cursor2.getBlob(11);
                        if (imageByte != (null) && imageByte.length > 0) {
                            Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                            recipe.setRecipeImage(image);
                        }
                        recipelist.add(recipe);
                    } while (cursor2.moveToNext());
                }
                cursor2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return recipelist;
    }

    public List<InfluencerClass> getInfluencers() {
        List<InfluencerClass> influencers = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from InfluencerDetails order by SubCount desc", null);
        if (cursor.moveToFirst()) {
            do {
                InfluencerClass influencer = new InfluencerClass();
                influencer.setInfID(cursor.getInt(0));
                influencer.setInfName(cursor.getString(1));
                influencer.setBio(cursor.getString(3));
                byte[] imageByte = cursor.getBlob(4);
                if (imageByte != (null) && imageByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    influencer.setInfprofImage(image);
                }
                influencers.add(influencer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return influencers;
    }

    public RecipeClass getRecipeDetails(int RecipeID) {
        Cursor cursor = database.rawQuery("Select * from Recipe where RecipeID=" + RecipeID, null);
        RecipeClass recipe = new RecipeClass();
        if (cursor.moveToFirst()) {
            recipe.setRecipeName(cursor.getString(1));
            recipe.setDescription(cursor.getString(2));
            recipe.setOrigin(cursor.getString(5));
            recipe.setCategory(cursor.getString(6));
            recipe.setSubType(cursor.getString(3));
            recipe.setVideo(cursor.getString(4));
            recipe.setLikeCount(cursor.getInt(8));
            recipe.setInfID(cursor.getInt(7));
            recipe.setDate(cursor.getString(12));

            byte[] imagerecipe = cursor.getBlob(11);
            if (imagerecipe != (null) && imagerecipe.length > 0) {
                Bitmap recipeimg = BitmapFactory.decodeByteArray(imagerecipe, 0, imagerecipe.length);
                recipe.setRecipeImage(recipeimg);
            }
        }
        cursor.close();
        return recipe;
    }

    public InfluencerClass getInfluencerDetails(int InfID) {
        Cursor cursor = database.rawQuery("Select * from InfluencerDetails where InfID=" + InfID, null);
        InfluencerClass influencer = new InfluencerClass();
        if (cursor.moveToFirst()) {
            influencer.setInfName(cursor.getString(1));
            influencer.setSubCount(cursor.getInt(5));
            influencer.setTotalLikes(cursor.getInt(6));
            influencer.setBio(cursor.getString(3));
            byte[] imageByte = cursor.getBlob(4);
            if (imageByte != (null) && imageByte.length > 0) {
                Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                influencer.setInfprofImage(image);
            }
        }
        cursor.close();
        return influencer;
    }

    public int getRecipeCount(int InfID) {
        Cursor cursor = database.rawQuery("Select count(*) from Recipe where InfID=" + InfID, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public String getInfName(int InfID) {
        String InfName = "";
        Cursor cursor = database.rawQuery("Select * from InfluencerDetails where InfID=" + InfID, null);
        if (cursor.moveToFirst()) {
            InfName = cursor.getString(1);
        }
        cursor.close();
        return InfName;
    }

    public Bitmap getInfPfp(int InfID) {
        Bitmap InfImage = null;
        Cursor cursor = database.rawQuery("Select * from InfluencerDetails where InfID=" + InfID, null);
        if (cursor.moveToFirst()) {
            byte[] img = cursor.getBlob(4);
            if (img != null && img.length > 0) {
                InfImage = BitmapFactory.decodeByteArray(img, 0, img.length);
            }
        }
        cursor.close();
        return InfImage;
    }

    public List<IngredientClass> getIngredients(int RecipeID) {
        List<IngredientClass> ingredients = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from Ingredients where RecipeID=" + RecipeID, null);
        if (cursor.moveToFirst()) {
            do {
                IngredientClass ingredient = new IngredientClass();
                ingredient.setIngredientNo(cursor.getInt(1));
                ingredient.setIngredientName(cursor.getString(2));
                ingredient.setIngredientQuantity(cursor.getString(3));
                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }

    public List<InstructionClass> getInstructions(int RecipeID) {
        List<InstructionClass> instructions = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from Instructions where RecipeID=" + RecipeID, null);
        if (cursor.moveToFirst()) {
            do {
                InstructionClass instruction = new InstructionClass();
                instruction.setInstructionNumber(cursor.getInt(1));
                instruction.setInstructionDetail(cursor.getString(2));
                instruction.setTime(cursor.getInt(3));
                instructions.add(instruction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return instructions;
    }

    public List<CommentClass> getComments(int RecipeID) {
        List<CommentClass> comments = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from Comments where RecipeID="+RecipeID, null);
        if (cursor.moveToFirst()) {
            do {
                CommentClass comment = new CommentClass();
                comment.setCommentID(cursor.getInt(0));
                comment.setComment(cursor.getString(3));
                comment.setReply(cursor.getString(4));
                comment.setUserID(cursor.getInt(2));
                comments.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return comments;
    }

    public UserClass getUserdetailsforComment(int UserID) {
        UserClass user = new UserClass();
        Cursor cursor = database.rawQuery("Select * from UserDetails where UserID=" + UserID, null);
        if (cursor.moveToFirst()) {
            String UserName = cursor.getString(1);
            byte[] Img = cursor.getBlob(3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(Img, 0, Img.length);
            user.setUserName(UserName);
            user.setProfileImage(bitmap);
        }
        cursor.close();
        return user;
    }
    public int getInfluencerID(int RecipeID){
        int InfID=0;
        Cursor cursor=database.rawQuery("Select InfID from Recipe where RecipeID="+RecipeID,null);
        if(cursor.moveToFirst()){
            InfID=cursor.getInt(0);
        }
        return InfID;
    }

    public boolean addComment(CommentClass comment) {
        try {
            ContentValues values = new ContentValues();
            values.put("RecipeID", comment.getRecipeID());
            values.put("Comment", comment.getComment());
            values.put("Reply", comment.getReply());
            values.put("UserID", comment.getUserID());
            long result = database.insert("Comments", null, values);
            if (result != -1) {
                database.execSQL("Update Recipe set CommentCount=CommentCount+1 where RecipeID=" + comment.getRecipeID());
                database.execSQL("Update InfluencerDetails set TotalComments=TotalComments+1 where InfID="+getInfluencerID(comment.getRecipeID()));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean SubscribetoInfluencer(int UserID, int InfID) {
        try {
            ContentValues values = new ContentValues();
            values.put("InfID", InfID);
            values.put("UserID", UserID);
            long result = database.insert("SubscribeList", null, values);
            if (result == -1) {
                return false;
            }
            database.execSQL("Update InfluencerDetails set SubCount=SubCount+1 where InfID=" + InfID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UnsubscribefromInfluencer(int InfID, int UserID) {
        try {
            database.execSQL("Update InfluencerDetails set SubCount=SubCount-1 where InfID=" + InfID);
            database.execSQL("Delete from SubscribeList where InfID=" + InfID + " and UserID=" + UserID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSubscribed(int InfID, int UserID) {
        Cursor cursor = database.rawQuery("Select * from SubscribeList where InfID=" + InfID + " and UserID=" + UserID, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean AddLike(int RecipeID, int UserID, int InfID) {
        try {
            ContentValues values = new ContentValues();
            values.put("RecipeID", RecipeID);
            values.put("UserID", UserID);
            long result = database.insert("LikeList", null, values);
            if (result == -1) {
                return false;
            }
            database.execSQL("Update InfluencerDetails set TotalLikes=TotalLikes+1 where InfID=" + InfID);
            database.execSQL("Update Recipe set LikeCount=LikeCount+1 where RecipeID=" + RecipeID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean RemoveLike(int RecipeID, int UserID, int InfID) {
        try {
            database.execSQL("Update InfluencerDetails set TotalLikes=TotalLikes-1 where InfID=" + InfID);
            database.execSQL("Update Recipe set LikeCount=LikeCount-1 where RecipeID=" + RecipeID);
            database.execSQL("Delete from LikeList where RecipeID=" + RecipeID + " and UserID=" + UserID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLiked(int UserID, int RecipeID) {
        Cursor cursor = database.rawQuery("Select * from LikeList where RecipeID=" + RecipeID + " and UserID=" + UserID, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public int getLikeCount(int RecipeID) {
        Cursor cursor = database.rawQuery("Select LikeCount from Recipe where RecipeID=" + RecipeID, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public List<RecipeClass> getRecipesforinfluencer(int InfID) {
        List<RecipeClass> recipes = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from Recipe where InfID=" + InfID+" and Status='Confirmed'", null);
        if (cursor.moveToFirst()) {
            do {
                RecipeClass recipe = new RecipeClass();
                recipe.setRecipeID(cursor.getInt(0));
                recipe.setRecipeName(cursor.getString(1));
                recipe.setCommentCount(cursor.getInt(9));
                recipe.setLikeCount(cursor.getInt(8));
                recipe.setDescription(cursor.getString(2));
                recipe.setOrigin(cursor.getString(5));
                recipe.setCategory(cursor.getString(6));
                recipe.setInfID(cursor.getInt(7));
                byte[] imageByte = cursor.getBlob(11);
                if (imageByte != (null) && imageByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    recipe.setRecipeImage(image);
                }
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipes;
    }

    public boolean deleteRecipe(int RecipeID, int InfID) {
        try {
            database.execSQL("Delete from Comments where RecipeID=" + RecipeID);
            database.execSQL("Delete from LikeList where RecipeID=" + RecipeID);
            database.execSQL("Delete from Instructions where RecipeID=" + RecipeID);
            database.execSQL("Delete from Ingredients where RecipeID=" + RecipeID);
            database.execSQL("Update InfluencerDetails set TotalLikes=TotalLikes-" + getLikeCount(RecipeID) + " where InfID=" + InfID);
            database.execSQL("Delete from Recipe where RecipeID=" + RecipeID);
            return true;
        } catch (Exception c) {
            c.printStackTrace();
            return false;
        }
    }

    public boolean updateRecipe(RecipeClass recipe, int RecipeID) {
        boolean updatestatus = false;
        int maxsize = 1024 * 1024;
        Bitmap imagetostorebitmap = recipe.getRecipeImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imginbyte = byteArrayOutputStream.toByteArray();
        if (imginbyte.length > maxsize) {
            byteArrayOutputStream.reset();
            imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            imginbyte = byteArrayOutputStream.toByteArray();
        }
        try {
            ContentValues values = new ContentValues();
            values.put("RecipeName", recipe.getRecipeName());
            values.put("Description", recipe.getDescription());
            values.put("Origin", recipe.getOrigin());
            values.put("Category", recipe.getCategory());
            values.put("RecipeImg", imginbyte);
            values.put("SubType", recipe.getSubType());
            values.put("VidLink", recipe.getVideo());

            long rowID = database.update("Recipe", values, "RecipeID=" + RecipeID, null);
            if (rowID != -1) {
                updatestatus = true;
            }
        } catch (Exception c) {
            c.printStackTrace();
            Toast.makeText(context, c.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return updatestatus;
    }

    public boolean updateIngredients(List<IngredientClass> ingredients, int RecipeID) {
        boolean updateingredientstatus = true;
        database.delete("Ingredients", "RecipeID=" + RecipeID, null);
        int IngredientNo = 1;
        try {
            for (IngredientClass ingredient : ingredients) {
                ContentValues values = new ContentValues();
                values.put("RecipeID", RecipeID);
                values.put("IngNo", IngredientNo);
                values.put("IngName", ingredient.getIngredientName());
                values.put("IngQuantity", ingredient.getIngredientQuantity());
                long result = database.insert("Ingredients", null, values);
                if (result == -1) {
                    updateingredientstatus = false;
                    break;
                }
                IngredientNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateingredientstatus = false;
        }
        return updateingredientstatus;
    }

    public boolean updateInstructions(List<InstructionClass> instructions, int RecipeID) {
        boolean updateinstructionstatus = true;
        database.delete("Instructions", "RecipeID=" + RecipeID, null);
        int InstructionNo = 1;
        try {
            for (InstructionClass instruction : instructions) {
                ContentValues values = new ContentValues();
                values.put("RecipeID", RecipeID);
                values.put("InstructionNo", InstructionNo);
                values.put("InstructionDetail", instruction.getInstructionDetail());
                values.put("Time", instruction.getTime());
                long result = database.insert("Instructions", null, values);
                if (result == -1) {
                    updateinstructionstatus = false;
                    break;
                }
                InstructionNo++;
            }
        } catch (Exception c) {
            c.printStackTrace();
            updateinstructionstatus = false;
        }
        return updateinstructionstatus;
    }

    public List<RecipeClass> getLikesforPiechart(int InfID) {
        List<RecipeClass> recipelist = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("Select * from Recipe where InfID="+InfID+" and Status='Confirmed'", null);
            if (cursor.moveToFirst()) {
                do {
                    RecipeClass recipe = new RecipeClass();
                    recipe.setRecipeName(cursor.getString(1));
                    recipe.setLikeCount(cursor.getInt(8));
                    recipelist.add(recipe);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipelist;
    }

    public List<RecipeClass> getCommentsforPiechart(int InfID) {
        List<RecipeClass> recipelist = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("Select * from Recipe where InfID=" + InfID+" and Status='Confirmed'", null);
            if (cursor.moveToFirst()) {
                do {
                    RecipeClass recipe = new RecipeClass();
                    recipe.setRecipeName(cursor.getString(1));
                    recipe.setCommentCount(cursor.getInt(9));
                    recipelist.add(recipe);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipelist;
    }

    public boolean addUpdate(UpdateClass update) {
        boolean addstatus = false;
        int maxsize = 1024 * 1024;
        Bitmap imagetostorebitmap = update.getUpdateImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imginbyte = byteArrayOutputStream.toByteArray();
        if (imginbyte.length > maxsize) {
            byteArrayOutputStream.reset();
            imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            imginbyte = byteArrayOutputStream.toByteArray();
        }
        try {
            ContentValues values = new ContentValues();
            values.put("UpdateDetail", update.getUpdateStatus());
            values.put("UpdateImg", imginbyte);
            values.put("InfID", update.getInfID());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());
            values.put("Date", currentDate);
            long rowID = database.insert("Updates", null, values);
            if (rowID != -1) {
                addstatus = true;
            }
        } catch (Exception c) {
            c.printStackTrace();
            Toast.makeText(context, c.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return addstatus;
    }

    public List<UpdateClass> getUpdates(int InfID) {
        List<UpdateClass> updatelist = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from Updates where InfID=" + InfID, null);
        if (cursor.moveToFirst()) {
            do {
                UpdateClass update = new UpdateClass();
                update.setUpdateStatus(cursor.getString(2));
                update.setDate(cursor.getString(3));
                byte[] imageByte = cursor.getBlob(4);
                if (imageByte != (null) && imageByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    update.setUpdateImage(image);
                }
                updatelist.add(update);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return updatelist;
    }

    public List<RecipeClass> getPendingRecipes() {
        List<RecipeClass> pendingrecipes = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from Recipe where Status='Pending'", null);
        if (cursor.moveToFirst()) {
            do {
                RecipeClass recipe = new RecipeClass();
                recipe.setRecipeID(cursor.getInt(0));
                recipe.setRecipeName(cursor.getString(1));
                recipe.setDescription(cursor.getString(2));
                recipe.setOrigin(cursor.getString(5));
                recipe.setCategory(cursor.getString(6));
                recipe.setInfID(cursor.getInt(7));
                recipe.setDate(cursor.getString(12));
                byte[] imageByte = cursor.getBlob(11);
                if (imageByte != (null) && imageByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    recipe.setRecipeImage(image);
                }

                pendingrecipes.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pendingrecipes;
    }

    public String checkpremiumsubUser(int UserID) {
        String Substatus = "";
        Cursor cursor = database.rawQuery("Select * from UserDetails where UserID=" + UserID, null);
        if (cursor.moveToFirst()) {
            Log.d("Substatus", cursor.getString(4));
            Substatus = cursor.getString(4);
        }
        cursor.close();
        return Substatus;
    }

    public boolean setPendingstatus(int UserID) {
        try {
            database.execSQL("Update UserDetails set SubStatus='Pending' where UserID=" + UserID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UserClass> getPendingUsers() {
        List<UserClass> pendingusers = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from UserDetails where SubStatus='Pending'", null);
        if (cursor.moveToFirst()) {
            do {
                UserClass user = new UserClass();
                user.setUserID(cursor.getInt(0));
                user.setUserName(cursor.getString(1));
                byte[] imageByte = cursor.getBlob(3);
                if (imageByte != (null) && imageByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    user.setProfileImage(image);
                }
                pendingusers.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pendingusers;
    }

    public boolean confirmPremiumUser(int UserID) {
        try {
            database.execSQL("Update UserDetails set SubStatus='Premium' where UserID=" + UserID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean declinePremiumUser(int UserID) {
        try {
            database.execSQL("Update UserDetails set SubStatus='Basic' where UserID=" + UserID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean confirmRecipe(int RecipeID) {
        try {
            database.execSQL("Update Recipe set Status='Confirmed' where RecipeID=" + RecipeID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean declineRecipe(int RecipeID) {
        try {
            database.execSQL("Update Recipe set Status='Declined' where RecipeID=" + RecipeID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DeleteInfluencer(int InfID) {
        try {
            int[] recipeList = RetreiveAllRecipes(InfID);
            if(recipeList!=null && recipeList.length!=0){
                for (int RecipeID : recipeList) {
                    DeleteAllLikesInRecipe(RecipeID);
                    DeleteAllCommentsInRecipe(RecipeID);
                }
            }
            database.execSQL("Delete from SubscribeList where InfID=" + InfID);
            database.execSQL("Delete from Updates where InfID=" + InfID);
            database.execSQL("Delete from Recipe where InfID=" + InfID);
            database.execSQL("Delete from InfluencerDetails where InfID=" + InfID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void DeleteAllCommentsInRecipe(int RecipeID) {
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM Comments WHERE RecipeID="+RecipeID,null);
            int commentCount = 0;
            if (cursor.moveToFirst()) {
                commentCount = cursor.getInt(0);
            }
            cursor.close();
            database.execSQL("Delete from Comments where RecipeID=" + RecipeID);
            int InfID = getInfluencerID(RecipeID);
            database.execSQL("UPDATE InfluencerDetails SET TotalComments = TotalComments-"+commentCount+" WHERE InfID="+InfID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteAllLikesInRecipe(int RecipeID) {
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM LikeList WHERE RecipeID="+RecipeID,null);
            int likeCount = 0;
            if (cursor.moveToFirst()) {
                likeCount = cursor.getInt(0);
            }
            cursor.close();
            database.execSQL("Delete from LikeList where RecipeID=" + RecipeID);
            int InfID = getInfluencerID(RecipeID);
            database.execSQL("UPDATE InfluencerDetails SET TotalLikes = TotalLikes-"+likeCount+" WHERE InfID="+InfID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean DeleteComment(int CommentID,int RecipeID) {
        try {
            database.execSQL("Delete from Comments where CommentID=" + CommentID);
            database.execSQL("Update Recipe set CommentCount=CommentCount-1 where RecipeID=" +RecipeID);
            int InfID=getInfluencerID(RecipeID);
            database.execSQL("Update InfluencerDetails set TotalComments=TotalComments-1 where InfID="+InfID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int[] RetreiveAllRecipes(int InfID) {
        int[] recipes = null;
        try {
            Cursor cursor = database.rawQuery("Select * from Recipe where InfID=" + InfID, null);
            if (cursor.moveToFirst()) {
                int count = cursor.getCount();
                recipes = new int[count];
                for (int i = 0; i < count; i++) {
                    recipes[i] = cursor.getInt(cursor.getInt(0));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public boolean isFavourite(int UserID, int RecipeID) {
        Cursor cursor = database.rawQuery("Select * from FavouriteList where RecipeID=" + RecipeID + " and UserID=" + UserID, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean AddFavourite(int RecipeID, int UserID) {
        try {
            ContentValues values = new ContentValues();
            values.put("RecipeID", RecipeID);
            values.put("UserID", UserID);
            long rowID = database.insert("FavouriteList", null, values);
            if (rowID != -1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean RemoveFavourite(int RecipeID, int UserID) {
        try {
            database.execSQL("Delete from FavouriteList where RecipeID=" + RecipeID + " and UserID=" + UserID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<RecipeClass> getFavRecipes(int UserID) {
        List<RecipeClass> recipes = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from FavouriteList where UserID=" + UserID, null);
        if (cursor.moveToFirst()) {
            do {
                int RecipeID = cursor.getInt(0);
                Cursor cursor2 = database.rawQuery("Select * from Recipe where RecipeID=" + RecipeID, null);
                if (cursor2.moveToFirst()) {
                    RecipeClass recipe = new RecipeClass();
                    recipe.setRecipeID(cursor2.getInt(0));
                    recipe.setRecipeName(cursor2.getString(1));
                    recipe.setCommentCount(cursor2.getInt(9));
                    recipe.setLikeCount(cursor2.getInt(8));
                    recipe.setDescription(cursor2.getString(2));
                    recipe.setOrigin(cursor2.getString(5));
                    recipe.setCategory(cursor2.getString(6));
                    recipe.setInfID(cursor2.getInt(7));
                    byte[] imageByte = cursor2.getBlob(11);
                    if (imageByte != null && imageByte.length > 0) {
                        Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                        recipe.setRecipeImage(image);
                    }
                    recipes.add(recipe);
                }
                cursor2.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipes;
    }
    public int CalculateInfLevel(int InfID){
        int totalLikes;
        int totalComments;
        int level=1;
        Cursor cursor = database.rawQuery("Select * from InfluencerDetails where InfID=" + InfID, null);
        if (cursor.moveToFirst()) {
            totalLikes = cursor.getInt(6);
            totalComments = cursor.getInt(7);
            level = ((totalLikes + totalComments) / 100) + 1;
            Log.d("level",String.valueOf(level));
            database.execSQL("Update InfluencerDetails set Level="+level+" where InfID="+ InfID);
        }
        cursor.close();
        return level;
    }
    public SalaryClass CalculateandGetSalary(int InfID) {
        SalaryClass salary = new SalaryClass();
        try {
            int level = CalculateInfLevel(InfID);
            Cursor cursor = database.rawQuery("Select * from Salary where InfID = " + InfID, null);
            if (cursor.moveToFirst()) {
                salary.setLastPaidLevel(cursor.getInt(1));
                salary.setLastPaidDate(cursor.getString(2));
                salary.setLastPaidSalary(cursor.getDouble(3));
                salary.setTotalSalary(cursor.getDouble(4));
            }
            cursor.close();
            if (level - salary.getLastPaidLevel() >= 10) {
                salary.setLastPaidLevel(level);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = sdf.format(new Date());
                salary.setLastPaidDate(currentDate);

                double newSalary = PREMIUM_MEMBERSHIP_PRICE * BASE_SALARY_PERCENTAGE * level;
                salary.setLastPaidSalary(newSalary);

                salary.setTotalSalary(salary.getTotalSalary() + newSalary);
                ContentValues values = new ContentValues();
                values.put("LastPaidLevel", level);
                values.put("LastPaidSalary", salary.getLastPaidSalary());
                values.put("LastPaidDate", salary.getLastPaidDate());
                values.put("TotalSalary", salary.getTotalSalary());
                long rowID = database.update("Salary", values, "InfID=" + InfID,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salary;
    }
    public int getTotalNumberOfRecipes() {
        int totalRecipes = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM Recipe WHERE Status='Confirmed'", null);
            if (cursor.moveToFirst()) {
                totalRecipes = cursor.getInt(0);
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return totalRecipes;
    }
    public int getTotalNumberOfUsers() {
        int totalUsers = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM UserDetails", null);
            if (cursor.moveToFirst()) {
                totalUsers = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
         e.printStackTrace();
        }
        return totalUsers;
    }
    public int getTotalNumberOfInfluencers() {
        int totalInfluencers = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM InfluencerDetails", null);
            if (cursor.moveToFirst()) {
                totalInfluencers = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return totalInfluencers;
    }
    public int getTotalNumberOfComments() {
        int totalComments = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM Comments", null);
            if (cursor.moveToFirst()) {
                totalComments = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return totalComments;
    }
    public int getTotalNumberOfLikes() {
        int totalLikes = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT SUM(LikeCount) FROM Recipe WHERE Status='Confirmed'", null);
            if (cursor.moveToFirst()) {
                totalLikes = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return totalLikes;
    }
    public double getAverageLikesPerRecipe() {
        double averageLikes = 0.0;
        try {
            Cursor cursor = database.rawQuery("SELECT AVG(LikeCount) FROM Recipe WHERE Status='Confirmed'", null);
            if (cursor.moveToFirst()) {
                averageLikes = cursor.getDouble(0);
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return averageLikes;
    }

    public List<RecipeClass> getTop5RecipesByLikes() {
        List<RecipeClass> recipeList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM Recipe WHERE Status='Confirmed' ORDER BY LikeCount DESC LIMIT 5", null);
            if (cursor.moveToFirst()) {
                do {
                    RecipeClass recipe = new RecipeClass();
                    recipe.setRecipeName(cursor.getString(1));
                    recipe.setLikeCount(cursor.getInt(8));
                    recipeList.add(recipe);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("top5", String.valueOf(recipeList.size()));
        return recipeList;
    }
    public List<RecipeClass> getTop5RecipesByComments() {
        List<RecipeClass> recipeList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM Recipe WHERE Status='Confirmed' ORDER BY CommentCount DESC LIMIT 5", null);
            if (cursor.moveToFirst()) {
                do {
                    RecipeClass recipe = new RecipeClass();
                    recipe.setRecipeName(cursor.getString(1));
                    recipe.setCommentCount(cursor.getInt(9));
                    recipeList.add(recipe);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return recipeList;
    }
    public int getAllRecipeCount(){
        int recipeCount = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM Recipe WHERE Status='Confirmed'", null);
            if (cursor.moveToFirst()) {
                recipeCount = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipeCount;
    }
    public int getRecipeCountByCategory(String Category){
        int recipeCount = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM Recipe WHERE Status='Confirmed' and Category='" + Category + "'", null);
            if (cursor.moveToFirst()) {
                recipeCount = cursor.getInt(0);
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return recipeCount;
    }
    public int getRecipeCountBySubscription(String Subscription){
        int recipeCount = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM Recipe WHERE Status='Confirmed' and SubType='"+Subscription+"'", null);
            if (cursor.moveToFirst()) {
                recipeCount = cursor.getInt(0);
            }
            cursor.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return recipeCount;
    }
    public double getTotalSalary(){
        double totalSalary = 0.0;
        try {
            Cursor cursor = database.rawQuery("SELECT SUM(TotalSalary) FROM Salary", null);
            if (cursor.moveToFirst()) {
                totalSalary = cursor.getDouble(0);
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return totalSalary;
    }


    public List<SalaryClass> getTop5InfluencersBySalary() {
        List<SalaryClass> SalaryList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM Salary ORDER BY TotalSalary DESC LIMIT 5", null);
            if (cursor.moveToFirst()) {
                do {
                    SalaryClass salary = new SalaryClass();
                    salary.setInfID(cursor.getInt(0));
                    salary.setTotalSalary(cursor.getDouble(4));
                    SalaryList.add(salary);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SalaryList;
    }

    public List<InfluencerClass> getTop5InfluencersBySubs() {
        List<InfluencerClass> influencers = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("Select * from InfluencerDetails order by SubCount desc limit 5", null);
            if (cursor.moveToFirst()) {
                do {
                    InfluencerClass influencer = new InfluencerClass();
                    influencer.setInfID(cursor.getInt(0));
                    influencer.setInfName(cursor.getString(1));
                    influencer.setSubCount(cursor.getInt(5));
                    influencers.add(influencer);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return influencers;
    }


    public List<InfluencerClass> getTop5InfluencersByLikes() {
        List<InfluencerClass> influencers = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("Select * from InfluencerDetails order by TotalLikes desc limit 5", null);
            if (cursor.moveToFirst()) {
                do {
                    InfluencerClass influencer = new InfluencerClass();
                    influencer.setInfID(cursor.getInt(0));
                    influencer.setInfName(cursor.getString(1));
                    influencer.setTotalLikes(cursor.getInt(6));
                    influencers.add(influencer);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return influencers;
    }

    public List<String[]> getTop5InfluencersByRecipes() {
        List<String[]> influencers = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(
                    "SELECT InfluencerDetails.InfID, InfluencerDetails.InfName, COUNT(Recipe.RecipeID) AS RecipeCount " +
                            "FROM InfluencerDetails " +
                            "JOIN Recipe ON InfluencerDetails.InfID = Recipe.InfID " +
                            "WHERE Recipe.Status='Confirmed' " +
                            "GROUP BY InfluencerDetails.InfID, InfluencerDetails.InfName " +
                            "ORDER BY RecipeCount DESC " +
                            "LIMIT 5", null);

            if (cursor.moveToFirst()) {
                do {
                    String InfID = cursor.getString(0);
                    String name = cursor.getString(1);
                    String recipeCount = cursor.getString(2);
                    influencers.add(new String[] { InfID, name, recipeCount });
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return influencers;
    }

    public List<String[]> getTop5UsersByLikes() {
        List<String[]> users = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(
                    "SELECT UserDetails.UserID, UserDetails.UserName, COUNT(LikeList.RecipeID) AS LikeCount " +
                            "FROM UserDetails " +
                            "JOIN LikeList ON UserDetails.UserID = LikeList.UserID " +
                            "GROUP BY UserDetails.UserID, UserDetails.UserName " +
                            "ORDER BY LikeCount DESC " +
                            "LIMIT 5", null);

            if (cursor.moveToFirst()) {
                do {
                    String userID = cursor.getString(0);
                    String userName = cursor.getString(1);
                    String likeCount = cursor.getString(2);
                    users.add(new String[] { userID, userName, likeCount });
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public int getUserCountBySubscription(String Sub) {
        int totalUsers = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM UserDetails WHERE SubStatus='" +Sub +"'", null);
            if (cursor.moveToFirst()) {
                totalUsers = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalUsers;
    }

    public List<UserClass> getUsers() {
        List<UserClass> users = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from UserDetails", null);
        if (cursor.moveToFirst()) {
            do {
                UserClass user = new UserClass();
                user.setUserID(cursor.getInt(0));
                user.setUserName(cursor.getString(1));
                byte[] imageByte = cursor.getBlob(3);
                if (imageByte != (null) && imageByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    user.setProfileImage(image);
                }
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public int getTotalLikesUser(int userID) {
        int totalLikes = 0;
        try {
            Cursor cursor = database.rawQuery("Select * from LikeList where UserID=" + userID, null);
            if (cursor.moveToFirst()) {
                totalLikes = cursor.getCount();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalLikes;
    }

    public int getFavouriteCountUser(int userID) {
        int favouriteCount = 0;
        try {
            Cursor cursor = database.rawQuery("Select * from FavouriteList where UserID=" + userID, null);
            if (cursor.moveToFirst()) {
                favouriteCount = cursor.getCount();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favouriteCount;
    }

    public int getCommentCountUser(int userID) {
        int commentCount = 0;
        try {
            Cursor cursor = database.rawQuery("Select * from Comments where UserID=" + userID, null);
            if (cursor.moveToFirst()) {
                commentCount = cursor.getCount();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentCount;
    }

    public boolean UpdateUser(UserClass user, int UserID) {
        boolean updatestatus = false;
        int maxsize = 1024 * 1024;
        Bitmap imagetostorebitmap = user.getProfileImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imginbyte = byteArrayOutputStream.toByteArray();
        if (imginbyte.length > maxsize) {
            byteArrayOutputStream.reset();
            imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            imginbyte = byteArrayOutputStream.toByteArray();
        }
        try {
            ContentValues values = new ContentValues();
            values.put("UserName", user.getUserName());
            values.put("Password", user.getPassword());
            values.put("Img", imginbyte);
            long rowID = database.update("UserDetails", values, "UserID=" + UserID, null);
            if (rowID != -1) {
                updatestatus = true;
            }
        }catch (Exception c) {
            c.printStackTrace();
            Toast.makeText(context, c.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return updatestatus;
    }

    public boolean DeleteUser(int userID) {
        try {
            Cursor cursor = database.rawQuery("SELECT InfID FROM SubscribeList WHERE UserID="+userID, null);
            if (cursor.moveToFirst()) {
                do {
                    int infID = cursor.getInt(0);
                    database.execSQL("UPDATE InfluencerDetails SET SubCount = SubCount - 1 WHERE InfID="+infID);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.execSQL("Delete from SubscribeList where UserID=" + userID);
            database.execSQL("Delete from LikeList where UserID=" + userID);
            database.execSQL("Delete from FavouriteList where UserID=" + userID);
            database.execSQL("Delete from Comments where UserID=" + userID);
            database.execSQL("Delete from UserDetails where UserID=" + userID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateInfluencer(InfluencerClass influencer, int InfID) {
        boolean updatestatus = false;
        int maxsize = 1024 * 1024;
        Bitmap imagetostorebitmap = influencer.getInfprofImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imginbyte = byteArrayOutputStream.toByteArray();
        if (imginbyte.length > maxsize) {
            byteArrayOutputStream.reset();
            imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            imginbyte = byteArrayOutputStream.toByteArray();
        }
        try {
            ContentValues values = new ContentValues();
            values.put("InfName", influencer.getInfName());
            values.put("Password", influencer.getInfPassword());
            values.put("Img", imginbyte);
            values.put("Bio", influencer.getBio());
            long rowID = database.update("InfluencerDetails", values, "InfID=" + InfID, null);
            if (rowID != -1) {
                updatestatus = true;
            }
        }catch (Exception c) {
            c.printStackTrace();
            Toast.makeText(context, c.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return updatestatus;
    }

    public List<RecipeClass> getRecipesforInfHistory(int InfID) {
        List<RecipeClass> recipelist = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("Select * from Recipe where InfID="+InfID+" Order by RecipeID Desc", null);
            if (cursor.moveToFirst()) {
                do {
                    RecipeClass recipe = new RecipeClass();
                    recipe.setRecipeID(cursor.getInt(0));
                    recipe.setRecipeName(cursor.getString(1));
                    recipe.setDate(cursor.getString(12));
                    recipe.setCategory(cursor.getString(6));
                    recipe.setStatus(cursor.getString(10));
                    byte[] imageByte = cursor.getBlob(11);
                    if (imageByte != (null) && imageByte.length > 0) {
                        Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                        recipe.setRecipeImage(image);
                    }
                    recipelist.add(recipe);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("recipelistsize", String.valueOf(recipelist.size()));
        return recipelist;
    }

    public UserClass getUserdetails(int userID) {
        UserClass user = new UserClass();
        try {
            Cursor cursor = database.rawQuery("Select * from UserDetails where UserID=" + userID, null);
            if (cursor.moveToFirst()) {
                user.setUserID(cursor.getInt(0));
                user.setUserName(cursor.getString(1));
                byte[] imageByte = cursor.getBlob(3);
                if (imageByte != (null) && imageByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    user.setProfileImage(image);
                }
            }
            cursor.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<InfluencerClass> getSubbedInfluencers(int UserID) {
        List<InfluencerClass> influencers = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("Select * from SubscribeList where UserID="+UserID, null);
            if (cursor.moveToFirst()) {
                Log.d("cursorcount", String.valueOf(cursor.getCount()));
                do {
                    int influencerID = cursor.getInt(0);
                    Cursor cursor2 = database.rawQuery("Select * from InfluencerDetails where InfID=" + influencerID, null);
                    if (cursor2.moveToFirst()) {
                        Log.d("cursor2count", String.valueOf(cursor2.getCount()));
                        InfluencerClass influencer = new InfluencerClass();
                        influencer.setInfID(cursor2.getInt(0));
                        influencer.setInfName(cursor2.getString(1));
                        influencer.setBio(cursor2.getString(3));
                        byte[] imageByte = cursor2.getBlob(4);
                        if (imageByte != (null) && imageByte.length > 0) {
                            Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                            influencer.setInfprofImage(image);
                        }
                        influencers.add(influencer);
                        cursor2.close();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return influencers;
    }

    public Map<String, Integer> getLikedRecipesByCategory(int UserID) {
        Map<String, Integer> categoryCountMap = new HashMap<>();
        try {
            Cursor cursor = database.rawQuery("SELECT Recipe.Category, COUNT(LikeList.RecipeID) as LikeCount FROM LikeList"+
                    " INNER JOIN Recipe ON LikeList.RecipeID = Recipe.RecipeID"+
                    " WHERE LikeList.UserID ="+UserID+" GROUP BY Recipe.Category",null);

            if (cursor.moveToFirst()) {
                do {
                    String category = cursor.getString(0);
                    int likeCount = cursor.getInt(1);
                    categoryCountMap.put(category, likeCount);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryCountMap;
    }

    public boolean AddReply(CommentClass newreply) {
        boolean addreplyStatus = false;
        try{
            database.execSQL("Update Comments set Reply='"+newreply.getReply()+"' where CommentID='"+newreply.getCommentID()+"'");
            addreplyStatus= true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return addreplyStatus;
    }
}
