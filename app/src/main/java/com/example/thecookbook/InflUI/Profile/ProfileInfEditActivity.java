package com.example.thecookbook.InflUI.Profile;

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

import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Profile.UserEditProfileActivity;
import com.google.android.material.imageview.ShapeableImageView;

import jp.wasabeef.blurry.Blurry;

public class ProfileInfEditActivity extends AppCompatActivity {
    private int InfID;
    private TextView name,password,confirmpassword,bio;
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
        setContentView(R.layout.activity_profile_inf_edit);
        Intent intent=getIntent();
        InfID=intent.getIntExtra("InfID",-1);
        dbHelper=new DBHelper(this);
        dbHelper.OpenDB();
        name=findViewById(R.id.edit_name_inf);
        image=findViewById(R.id.edit_image_inf);
        backbtn=findViewById(R.id.backbtneditinf);
        bgimg=findViewById(R.id.bginfeditpic);
        password=findViewById(R.id.editpasswordinf);
        bio=findViewById(R.id.edit_bio_inf);
        confirmpassword=findViewById(R.id.editconfirmpasswordinf);
        update=findViewById(R.id.updateprofileinf);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateinf();
            }
        });
        bgimg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (bgimg.getDrawable() != null) {
                    Blurry.with(ProfileInfEditActivity.this)
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
    private void updateinf(){
        if(name.getText().toString().isEmpty()||password.getText().toString().isEmpty()||image.getDrawable()==null||imagetostore==null) {
            Toast.makeText(getApplicationContext(), "Please add all data", Toast.LENGTH_SHORT).show();
        }else if(!password.getText().toString().equals(confirmpassword.getText().toString())){
            Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
        }else {
            InfluencerClass influencer = new InfluencerClass(name.getText().toString(),password.getText().toString(),bio.getText().toString(),imagetostore);
            if(dbHelper.UpdateInfluencer(influencer,InfID)){
                Toast.makeText(getApplicationContext(), "Detail updated Successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Detail updating failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}