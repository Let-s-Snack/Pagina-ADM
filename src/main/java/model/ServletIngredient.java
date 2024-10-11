package model;

public class ServletIngredient {
    private int id;
    private String name;
    private String description;

    public ServletIngredient() {
    }

    public ServletIngredient(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Nome:" + name + "Descrição'" + description;
    }
}
