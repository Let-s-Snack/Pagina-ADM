package model;

public class Ingredient { //Classe Ingredient

//  Declaração dos Atributos
    private int id;
    private String name;
    private String description;

//    Construtor padrão
    public Ingredient() {}

//  Construtor
    public Ingredient( String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Ingredient(int id) {
        this.id = id;
    }

    public Ingredient( int id, String name, String description) {
        this.id=id;
        this.name = name;
        this.description = description;
    }

//  Construtor para retornar o nome
    public Ingredient(String name) {
        this.name = name;
    }

//  Getters e Setters

    //Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

//  Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

//  ToString
    @Override
    public String toString() {
        return "Nome:" + name + "Descrição'" + description;
    }

}//Fim da classe Ingredient
