package com.example.thecookbook.Classes;

import android.widget.Button;
import android.widget.EditText;

public class IngredientFieldsClass {
    private EditText IngredientName;
    private EditText IngredientQuantity;


    public IngredientFieldsClass(EditText ingredientName, EditText ingredientQuantity) {
        IngredientName = ingredientName;
        IngredientQuantity = ingredientQuantity;
    }

    public EditText getIngredientName() {
        return IngredientName;
    }

    public void setIngredientName(EditText ingredientName) {
        IngredientName = ingredientName;
    }

    public EditText getIngredientQuantity() {
        return IngredientQuantity;
    }

    public void setIngredientQuantity(EditText ingredientQuantity) {
        IngredientQuantity = ingredientQuantity;
    }
}
