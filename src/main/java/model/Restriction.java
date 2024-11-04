package model;

public class Restriction { //Classe Restriction

//  Declaração dos Atributos
    private String type;
    private String imageURL;
    private int id;
    private String description;


    public Restriction() {}

    public Restriction(String type, String imageURL, String description, int id) {
        this.type = type;
        this.imageURL = imageURL;
        this.id = id;
        this.description = description;
    }
    public Restriction(String type) {
        this.type = type;
    }
    public Restriction(int id) {
        this.id = id;
    }

//Getters e Setters

//Getters
    public String getType() {
        return this.type;
    }
    public String getImageURL() {
        return this.imageURL;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return this.id;
    }

//  Setters
    public void setType(String type) {
        this.type = type;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public void setDescription(String description) {
        this.description = description;
    }


//  ToString
    @Override
    public String toString() {
        return "id: "+ this.id + "Restriction" + this.type + "\nURL da imagem "+ this.imageURL + "Descripção: " + this.description;}

}//Fim da classe Recipe
