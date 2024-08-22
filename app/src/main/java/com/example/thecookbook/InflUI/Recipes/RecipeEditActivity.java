package com.example.thecookbook.InflUI.Recipes;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thecookbook.Classes.IngredientClass;
import com.example.thecookbook.Classes.IngredientFieldsClass;
import com.example.thecookbook.Classes.InstructionClass;
import com.example.thecookbook.Classes.InstructionFieldsClass;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.testRecipeAdd;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class RecipeEditActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 98;
    RecipeClass recipe;
    private DBHelper dbHelper;
    private Uri imagepath;
    private Bitmap imagetostore;
    int RecipeID;
    EditText RecipeName,Description,Origin,VidLink;
    ShapeableImageView RecipeImage,BgImage,Backbutton;
    Button UpdateRecipe,AddIngredientedit,AddInstructionedit;
    Spinner Category,Subtype;
    LinearLayout ingredientcontainer,instructioncontainer;
    List<IngredientFieldsClass> ingredientFieldsList;
    List<InstructionFieldsClass> instructionFieldsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_edit);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();
        Intent intent = getIntent();
        RecipeID = intent.getIntExtra("RecipeID",0);
        BgImage=(ShapeableImageView) findViewById(R.id.bgimagerecipeedit);
        Backbutton=(ShapeableImageView) findViewById(R.id.backbuttonedit);

        RecipeName=(EditText)findViewById(R.id.editname);
        Description=(EditText)findViewById(R.id.editdescription);
        Category=(Spinner)findViewById(R.id.editcategory);
        Origin=(EditText)findViewById(R.id.editorigin);
        Subtype=(Spinner) findViewById(R.id.subtypeedit);
        VidLink=(EditText)findViewById(R.id.editvidid);
        RecipeImage=(ShapeableImageView) findViewById(R.id.recipeimgedit);
        UpdateRecipe=(Button) findViewById(R.id.updateRecipe);
        AddIngredientedit=(Button) findViewById(R.id.add_ingredient_edit);
        AddInstructionedit=(Button) findViewById(R.id.add_instructions_edit);
        instructioncontainer= (LinearLayout) findViewById(R.id.instructions_container_edit);
        ingredientcontainer=(LinearLayout) findViewById(R.id.ingredients_container_edit);
        ingredientFieldsList=new ArrayList<>();
        instructionFieldsList=new ArrayList<>();
        String[] SubTypes = {"Basic","Premium"};
        String[] Categories ={"Veg ","Non-Veg "};

        ArrayAdapter<String> adaptersubs = new ArrayAdapter<>(this,R.layout.spinner_item, SubTypes);
        Subtype.setAdapter(adaptersubs);
        Subtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        setRecipedetails();
        RecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        AddIngredientedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddnewIngredient();

            }
        });
        AddInstructionedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddnewInstruction();

            }
        });
        UpdateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRecipedetails();
            }
        });
        BgImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (BgImage.getDrawable() != null) {
                    Blurry.with(RecipeEditActivity.this)
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

    private void setRecipedetails(){
        recipe = dbHelper.getRecipeDetails(RecipeID);
        RecipeName.setText(recipe.getRecipeName());
        Description.setText(recipe.getDescription());

        if(recipe.getCategory().equals("Veg")){
            Category.setSelection(0);
        }else{
            Category.setSelection(1);
        }
        Origin.setText(recipe.getOrigin());
        if(recipe.getSubType().equals("Basic")){
            Subtype.setSelection(0);
        }else{
            Subtype.setSelection(1);
        }
        VidLink.setText(recipe.getVideo());
        imagetostore=recipe.getRecipeImage();
        RecipeImage.setImageBitmap(imagetostore);
        List<IngredientClass> existingIngredients = dbHelper.getIngredients(RecipeID);
        for (IngredientClass ingredient : existingIngredients) {
            addExistingIngredient(ingredient.getIngredientName(), ingredient.getIngredientQuantity());
        }

        List<InstructionClass> existingInstructions = dbHelper.getInstructions(RecipeID);
        for (InstructionClass instruction : existingInstructions) {
            addExistingInstruction(instruction.getInstructionDetail(), instruction.getTime());
        }
    }



    private void addExistingIngredient(String ingredientName, String ingredientQuantity) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View ingredientlayout = inflater.inflate(R.layout.editingredientsitem, ingredientcontainer, false);

        EditText IngredientName = ingredientlayout.findViewById(R.id.EditIngredientName);
        EditText IngredientQuantity = ingredientlayout.findViewById(R.id.EditIngredientQuantity);
        ImageView removeingredient = ingredientlayout.findViewById(R.id.RemoveIngredientEdit);

        IngredientName.setText(ingredientName);
        IngredientQuantity.setText(ingredientQuantity);

        ingredientcontainer.addView(ingredientlayout);
        IngredientFieldsClass ingredientfields = new IngredientFieldsClass(IngredientName, IngredientQuantity);
        ingredientFieldsList.add(ingredientfields);

        removeingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientcontainer.removeView(ingredientlayout);
                ingredientFieldsList.remove(ingredientfields);
            }
        });
    }
    private void addExistingInstruction(String instructionDetail, int time) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View instructionlayout = inflater.inflate(R.layout.editinstructionitem, instructioncontainer, false);

        EditText InstructionDetail = instructionlayout.findViewById(R.id.InstructionDetailedit);
        EditText InstructionTime = instructionlayout.findViewById(R.id.InstructionTimeedit);
        ImageView removeinstruction = instructionlayout.findViewById(R.id.RemoveInstructionedit);

        InstructionDetail.setText(instructionDetail);
        InstructionTime.setText(String.valueOf(time));

        instructioncontainer.addView(instructionlayout);
        InstructionFieldsClass instructionfields = new InstructionFieldsClass(InstructionDetail, InstructionTime);
        instructionFieldsList.add(instructionfields);

        removeinstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructioncontainer.removeView(instructionlayout);
                instructionFieldsList.remove(instructionfields);
            }
        });
    }
    private void AddnewIngredient() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View ingredientlayout = inflater.inflate(R.layout.editingredientsitem, ingredientcontainer, false);

        EditText IngredientName = ingredientlayout.findViewById(R.id.EditIngredientName);
        EditText IngredientQuantity = ingredientlayout.findViewById(R.id.EditIngredientQuantity);
        ImageView removeingredient = ingredientlayout.findViewById(R.id.RemoveIngredientEdit);

        ingredientcontainer.addView(ingredientlayout);
        IngredientFieldsClass ingredientfields = new IngredientFieldsClass(IngredientName, IngredientQuantity);
        ingredientFieldsList.add(ingredientfields);

        removeingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientcontainer.removeView(ingredientlayout);
                ingredientFieldsList.remove(ingredientfields);
            }
        });
    }
    private void AddnewInstruction(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View instructionlayout = inflater.inflate(R.layout.editinstructionitem, instructioncontainer, false);
        EditText InstructionDetail = instructionlayout.findViewById(R.id.InstructionDetailedit);
        EditText InstructionTime = instructionlayout.findViewById(R.id.InstructionTimeedit);
        ImageView removeinstruction = instructionlayout.findViewById(R.id.RemoveInstructionedit);

        instructioncontainer.addView(instructionlayout);
        InstructionFieldsClass instructionfields = new InstructionFieldsClass(InstructionDetail, InstructionTime);
        instructionFieldsList.add(instructionfields);

        removeinstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructioncontainer.removeView(instructionlayout);
                instructionFieldsList.remove(instructionfields);
            }
        });

    }
    private void UpdateRecipedetails(){
        if(RecipeName.getText().toString().isEmpty() || Description.getText().toString().isEmpty() || Origin.getText().toString().isEmpty()  || RecipeImage.getDrawable()==null || VidLink.getText().toString().isEmpty()||imagetostore==null){
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
            }for (int i=0;i<instructionFieldsList.size();i++){
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
            }
            else {
                RecipeClass recipe = new RecipeClass(RecipeName.getText().toString(),Description.getText().toString(),Category.getSelectedItem().toString(),Origin.getText().toString(),RecipeID,Subtype.getSelectedItem().toString(),VidLink.getText().toString(),imagetostore);
                if (dbHelper.updateRecipe(recipe,RecipeID)&&dbHelper.updateIngredients(ingredients,RecipeID)&&dbHelper.updateInstructions(instructions,RecipeID)){
                        Toast.makeText(getApplicationContext(), "Recipe updated successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Recipe update failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}