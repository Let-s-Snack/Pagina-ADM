package controller;
import java.sql.*;

public class ServletIngredientRecipe {
    int id;
    int ingredient_id;

    int recipe_id;

    String measure;
    int quantity;
    boolean transaction_made;

    public ServletIngredientRecipe(int id , int ingredient_id,int recipe_id,String measure, int quantity, boolean transaction_made){
        this.id=id;
        this.ingredient_id=ingredient_id;
        this.recipe_id=recipe_id;
        this.measure=measure;
        this.quantity=quantity;
        this.transaction_made=transaction_made;
    }

    public int getId() {
        return id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public String getMeasure() {
        return measure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTransaction_made(boolean transaction_made) {
        this.transaction_made = transaction_made;
    }

    @Override
    public String toString() {
        return "Id:"+id+"\n Ingrediente_id: " +id+ "\nRecipe_id: "+recipe_id+"\nMeasure: "+measure+"Quantidade: "+quantity+"\nTransição: ";
    }
}