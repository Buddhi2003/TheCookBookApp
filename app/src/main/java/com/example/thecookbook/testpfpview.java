package com.example.thecookbook;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.thecookbook.DBClass.DBHelper;

public class testpfpview extends AppCompatActivity {
    private DBHelper dbHelper;
    ImageView img;
    TextView name,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpfpview);
        dbHelper= new DBHelper(this);
        dbHelper.OpenDB();
        img =(ImageView) findViewById(R.id.pfptest);
        name= (TextView) findViewById(R.id.Nametest);
        pass= (TextView) findViewById(R.id.Passwordtest);

        Cursor cursor = dbHelper.getDetails();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "NoData found", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                name.setText(cursor.getString(1));
                pass.setText(cursor.getString(2));
                byte[] imageByte = cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                img.setImageBitmap(bitmap);
            }
        }
    }



}