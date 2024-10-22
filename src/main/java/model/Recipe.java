package model;

public class Recipe {// Classe Receitas

    // declaração dos atributos
    private String name;
    private int id;
    private String steps;
    private int ingridient_id;
    private String image_url;
    private int restriction_id;
    private boolean transaction_made;


    // Construtor
    public Recipe(String name, int id, String steps, int ingridient_id, String image_url, int restriction_id, boolean transaction_made ){
        this.name =name;
        this.id = id;
        this.ingridient_id = ingridient_id;
        this.image_url = image_url;
        this.restriction_id = restriction_id;
        this.transaction_made = transaction_made;
        this.steps=steps;
    }

    // Construtor para a página de ADM
    public Recipe(String name, String instruction, String image_url ){
        this.name=name;
        this.steps = instruction;
        this.image_url = image_url;

    }
    // getters e setters
    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public String getSteps() {
        return this.steps;
    }

    public int getIngridient_id() {
        return this.ingridient_id;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public int getRestriction_id() {
        return this.restriction_id;
    }

    public boolean getTransactionMade(){
        return this.transaction_made;
    }

    public void setName (String name){
        this.name=name;
    }
    public void setImage_url (String image_url){
        this.image_url=image_url;
    }

    //to string
    public String toString(){
        return "\tName: " + this.name +  "\nImage url: "+ this.image_url;
    }
}// Fim da classe Receitas
