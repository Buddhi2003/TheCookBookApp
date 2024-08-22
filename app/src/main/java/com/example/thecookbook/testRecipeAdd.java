package com.example.thecookbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thecookbook.Classes.IngredientClass;
import com.example.thecookbook.Classes.IngredientFieldsClass;
import com.example.thecookbook.Classes.InstructionClass;
import com.example.thecookbook.Classes.InstructionFieldsClass;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class testRecipeAdd extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 98;
    private DBHelper dbHelper;
    private Uri imagepath;
    private Bitmap imagetostore;
    private int InfID;
    Spinner SubType,Category;
    TextInputEditText RecipeName,Description,Origin,VidLink;
    ShapeableImageView RecipeImage,BgImage,Backbutton;
    Button AddRecipe,AddIngredient,AddInstruction;
    LinearLayout ingredientcontainer,instructioncontainer;
    List<IngredientFieldsClass> ingredientFieldsList;
    List<InstructionFieldsClass> instructionFieldsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_recipe_add);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();
        Intent intent = getIntent();
        InfID = intent.getIntExtra("InfID",0);
        RecipeName=(TextInputEditText)findViewById(R.id.recipenameadd);
        Description=(TextInputEditText)findViewById(R.id.recipedescriptionadd);
        Category=(Spinner) findViewById(R.id.SpnrCategory);
        Origin=(TextInputEditText)findViewById(R.id.originadd);
        SubType=(Spinner)findViewById(R.id.Spnrsubtype);
        VidLink=(TextInputEditText)findViewById(R.id.videolinkadd);
        RecipeImage=(ShapeableImageView) findViewById(R.id.Recipeimgadd);
        BgImage=(ShapeableImageView) findViewById(R.id.bgimagerecipeadd);
        Backbutton=(ShapeableImageView) findViewById(R.id.backbuttonadd);
        AddRecipe=(Button) findViewById(R.id.addrecipebutton);
        AddIngredient=(Button) findViewById(R.id.addingredientbutton);
        AddInstruction=(Button) findViewById(R.id.addinstructionbutton);
        instructioncontainer= (LinearLayout) findViewById(R.id.instructioncontainer);
        ingredientcontainer=(LinearLayout) findViewById(R.id.ingredientcontainer);
        ingredientFieldsList=new ArrayList<>();
        instructionFieldsList=new ArrayList<>();
        String[] SubTypes = {"Basic","Premium"};
        String[] Categories ={"Veg","Non-Veg"};

        ArrayAdapter<String> adaptersubs = new ArrayAdapter<>(this,R.layout.spinner_item, SubTypes);
        SubType.setAdapter(adaptersubs);
        SubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        SubType.setSelection(0);

        ArrayAdapter<String> adaptercategories = new ArrayAdapter<>(this,R.layout.spinner_item,Categories);
        Category.setAdapter(adaptercategories);
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Category.setSelection(0);
        RecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        AddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeInsert();
            }
        });
        AddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddIngredients();
            }
        });
        AddInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInstructions();
            }
        });
        BgImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (BgImage.getDrawable() != null) {
                    Blurry.with(testRecipeAdd.this)
                            .radius(25)
                            .sampling(2)
                            .capture(BgImage)
                            .into(BgImage);
                } else {
                    Log.e("Blur Error", "Background image drawable is null");
                }
            }
        });
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                imagetostore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                RecipeImage.setImageBitmap(imagetostore);
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void RecipeInsert(){
        if(RecipeName.getText().toString().isEmpty() || Description.getText().toString().isEmpty() || Origin.getText().toString().isEmpty() ||  RecipeImage.getDrawable()==null || VidLink.getText().toString().isEmpty()||imagetostore==null){
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }else{
            List<IngredientClass> ingredients = new ArrayList<>();
            List<InstructionClass> instructions = new ArrayList<>();
            for(int i=0;i<ingredientFieldsList.size();i++){
                IngredientFieldsClass field = ingredientFieldsList.get(i);
                String ingredientName = field.getIngredientName().getText().toString().trim();
                String ingredientQuantity = field.getIngredientQuantity().getText().toString().trim();
                if(!ingredientName.isEmpty() && !ingredientQuantity.isEmpty()){
                    IngredientClass ingredient = new IngredientClass(ingredientName,ingredientQuantity);
                    ingredients.add(ingredient);
                }
            }
            for (int i=0;i<instructionFieldsList.size();i++){
              InstructionFieldsClass field = instructionFieldsList.get(i);
              String instructionDetail = field.getInstructionDetail().getText().toString().trim();
              int instructionTime = Integer.parseInt(field.getInstructionTime().getText().toString().trim());
              if(!instructionDetail.isEmpty() && instructionTime!=0){
                  InstructionClass instruction = new InstructionClass(instructionDetail,instructionTime);
                  instructions.add(instruction);
              }
            }
            if(ingredients.isEmpty()){
                Toast.makeText(this, "Please enter at least one Ingredient", Toast.LENGTH_SHORT).show();
            } else if(instructions.isEmpty()) {
                Toast.makeText(this, "Please enter at least one instruction", Toast.LENGTH_SHORT).show();
            } else{
                RecipeClass recipe = new RecipeClass(RecipeName.getText().toString(),Description.getText().toString(),Category.getSelectedItem().toString(),Origin.getText().toString(),InfID,SubType.getSelectedItem().toString(),VidLink.getText().toString(),imagetostore);
                int RecipeID = dbHelper.AddnewRecipe(recipe);
                if(RecipeID!=-1){
                    if (dbHelper.addIngredients(ingredients,RecipeID)&&dbHelper.addInstructions(instructions,RecipeID)) {
                        Toast.makeText(getApplicationContext(), "Recipe added successfully", Toast.LENGTH_SHORT).show();
                        RecipeName.setText("");
                        Description.setText("");
                        Origin.setText("");
                        VidLink.setText("");
                        RecipeImage.setImageResource(R.drawable.baseline_camera_alt_24);
                        RecipeImage.setScaleType(ImageView.ScaleType.CENTER);
                        ingredientcontainer.removeAllViews();
                        instructioncontainer.removeAllViews();
                        ingredientFieldsList.clear();
                        instructionFieldsList.clear();

                    }else{
                        Toast.makeText(getApplicationContext(), "Recipe adding with ingredients and instructions failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Recipe adding failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void AddIngredients(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View ingredientlayout = inflater.inflate(R.layout.addingredientitem,ingredientcontainer,false);

        EditText IngredientName = ingredientlayout.findViewById(R.id.IngredientName);
        EditText IngredientQuantity = ingredientlayout.findViewById(R.id.IngredientQuantity);
        ImageView removeingredient = ingredientlayout.findViewById(R.id.RemoveIngredient);


        ingredientcontainer.addView(ingredientlayout);
        IngredientFieldsClass ingredientfields = new IngredientFieldsClass(IngredientName,IngredientQuantity);
        ingredientFieldsList.add(ingredientfields);

        removeingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientcontainer.removeView(ingredientlayout);
                ingredientFieldsList.remove(ingredientfields);
            }
        });
    }
    private void AddInstructions(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View instructionlayout = inflater.inflate(R.layout.addinstructionitem,instructioncontainer,false);

        EditText InstructionDetail = instructionlayout.findViewById(R.id.InstructionDetail);
        EditText InstructionTime = instructionlayout.findViewById(R.id.InstructionTime);
        ImageView removeinstruction = instructionlayout.findViewById(R.id.RemoveInstruction);

        instructioncontainer.addView(instructionlayout);
        InstructionFieldsClass instructionfields = new InstructionFieldsClass(InstructionDetail,InstructionTime);
        instructionFieldsList.add(instructionfields);

        removeinstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructioncontainer.removeView(instructionlayout);
                instructionFieldsList.remove(instructionfields);
            }
        });

    }
}