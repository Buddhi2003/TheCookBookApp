package com.example.thecookbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresExtension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 99;
    ImageView imageView;
    Button pickimg,register,move;
    private Bitmap imagetostore;
    private Uri imagepath;
    EditText uname,password;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper= new DBHelper(this);
        dbHelper.OpenDB();

        imageView =(ImageView) findViewById(R.id.imageView);
        pickimg =(Button) findViewById(R.id.imgpick);
        register = (Button) findViewById(R.id.register);
        uname = (EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        move= (Button) findViewById(R.id.movenext);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetails();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentnew = new Intent(RegisterActivity.this,testpfpview.class);
                startActivity(intentnew);
            }
        });

    }
    private void ChooseImage(){
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
                imagetostore=MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                imageView.setImageBitmap(imagetostore);
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void StoreDetails(){
        if(uname.getText().toString().isEmpty() || password.getText().toString().isEmpty()||imageView.getDrawable()==null||imagetostore==null){
            Toast.makeText(getApplicationContext(), "Please add all data", Toast.LENGTH_SHORT).show();
        }else{
            UserClass userClass = new UserClass(uname.getText().toString(),password.getText().toString(),imagetostore);
            if(dbHelper.CreateNewUser(userClass)){
                Toast.makeText(getApplicationContext(), "Detail added Successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Detail adding failed", Toast.LENGTH_SHORT).show();
            }

        }
    }

}