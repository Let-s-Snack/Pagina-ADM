package model;

public class IngredientRecipe { //Classe Ingredient Recipe

//  Declaração dos Atributos
    private int id;
    private int ingredient_id;
    private int recipe_id;
    private String measure;
    private int quantity;
    private boolean transaction_made;

    public IngredientRecipe() {}

    public IngredientRecipe(String measure, int quantity,int id) {
        this.measure = measure;
        this.quantity = quantity;
        this.id = id;
    }

    //  Construtor
    public IngredientRecipe(int id , int ingredient_id, int recipe_id, String measure, int quantity, boolean transaction_made){
        this.id=id;
        this.ingredient_id=ingredient_id;
        this.recipe_id=recipe_id;
        this.measure=measure;
        this.quantity=quantity;
        this.transaction_made=transaction_made;
    }
    public IngredientRecipe(int id){
        this.id=id;
    }



//  Getters e Setters

//  Getters
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

//  Setters
    public void setMeasure(String measure) {
        this.measure = measure;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setTransaction_made(boolean transaction_made) {
        this.transaction_made = transaction_made;
    }

//  ToString
    @Override
    public String toString() {
        return "Id:"+id+"\n Ingrediente_id: " +id+ "\nRecipe_id: "+recipe_id+"\nMeasure: "+measure+"Quantidade: "+quantity+"\nTransição: ";}

}//Fim da classe Ingredient Recipe