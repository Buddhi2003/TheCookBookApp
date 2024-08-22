package com.example.thecookbook.UserUI.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import jp.wasabeef.blurry.Blurry;

public class UserEditProfileActivity extends AppCompatActivity {
    private int UserID;
    private TextView name,password,confirmpassword;
    private ShapeableImageView image,backbtn,bgimg;
    private Button update;
    private DBHelper dbHelper;
    private Bitmap imagetostore;
    private Uri imagepath;
    private static final int PICK_IMAGE_REQUEST = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_edit_profile);
        Intent intent=getIntent();
        UserID=intent.getIntExtra("UserID",-1);
        dbHelper=new DBHelper(this);
        dbHelper.OpenDB();
        name=findViewById(R.id.edit_name_User);
        image=findViewById(R.id.User_edit_image);
        password=findViewById(R.id.editpassworduser);
        confirmpassword=findViewById(R.id.editconfirmpassworduser);
        update=findViewById(R.id.updateprofileUser);
        backbtn=findViewById(R.id.backbtnedituser);
        bgimg=findViewById(R.id.bgusereditpic);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });
        bgimg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (bgimg.getDrawable() != null) {
                    Blurry.with(UserEditProfileActivity.this)
                            .radius(25)
                            .sampling(2)
                            .capture(bgimg)
                            .into(bgimg);
                } else {
                    Log.e("Blur Error", "Background image drawable is null");
                }
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void ChooseImage() {
        try{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imagepath=data.getData();
                imagetostore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                image.setImageBitmap(imagetostore);
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void Update() {
        if(name.getText().toString().isEmpty()||password.getText().toString().isEmpty()||image.getDrawable()==null||imagetostore==null) {
            Toast.makeText(getApplicationContext(), "Please add all data", Toast.LENGTH_SHORT).show();
        }else if(!password.getText().toString().equals(confirmpassword.getText().toString())){
            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
        }
        else{
            UserClass user = new UserClass(name.getText().toString(),password.getText().toString(),imagetostore);
            if(dbHelper.UpdateUser(user,UserID)){
                name.setText("");
                password.setText("");
                confirmpassword.setText("");
                image.setImageDrawable(null);
                Toast.makeText(getApplicationContext(), "Detail updated Successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Detail updating failed", Toast.LENGTH_SHORT).show();
            }

        }
    }

}