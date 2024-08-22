package com.example.thecookbook.InflUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UIAdapters.RecipeHistoryAdapter;

import java.util.List;

public class RecipeHistoryActivity extends AppCompatActivity {
    private RecipeHistoryAdapter adapter;
    private List<RecipeClass> recipes;
    private int InfID;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_history);
        Intent intent = getIntent();
        InfID = intent.getIntExtra("InfID", -1);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();
        recyclerView = findViewById(R.id.RecipeHistoryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setuprecycler();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }

    private void setuprecycler() {
        Log.d("InfID", InfID + "");
        recipes = dbHelper.getRecipesforInfHistory(InfID);
        if (!recipes.isEmpty()) {
            adapter = new RecipeHistoryAdapter(recipes);
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(this, "No Recipes Found", Toast.LENGTH_SHORT).show();
        }

    }

}