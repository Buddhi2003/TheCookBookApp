package com.example.thecookbook.Classes;

public class IngredientClass {
    private int IngredientNo;
    private String IngredientName;
    private String IngredientQuantity;

    public IngredientClass(){
    }

    public IngredientClass(int ingredientNo, String ingredientName, String ingredientQuantity) {
        IngredientNo = ingredientNo;
        IngredientName = ingredientName;
        IngredientQuantity = ingredientQuantity;
    }

    public IngredientClass(String ingredientName, String ingredientQuantity) {
        IngredientName = ingredientName;
        IngredientQuantity = ingredientQuantity;
    }

    public int getIngredientNo() {
        return IngredientNo;
    }

    public void setIngredientNo(int ingredientNo) {
        IngredientNo = ingredientNo;
    }

    public String getIngredientName() {
        return IngredientName;
    }

    public void setIngredientName(String ingredientName) {
        IngredientName = ingredientName;
    }

    public String getIngredientQuantity() {
        return IngredientQuantity;
    }

    public void setIngredientQuantity(String ingredientQuantity) {
        IngredientQuantity = ingredientQuantity;
    }
}
