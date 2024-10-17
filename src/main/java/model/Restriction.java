package model;

public class Restriction {

    //    Colunas das tabelas
    private String type;

    private String imageURL;

    private int id;

    private String description;

//  Construtor

    public Restriction(String type, String imageURL, int id, String description) {
        this.type = type;
        this.imageURL = imageURL;
        this.id = id;
        this.description = description;
    }

    //    Get
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

    //    Set
    public void setType(String type) {
        this.type = type;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    ToString (como vai aparecer na página de adm)

    @Override
    public String toString() {
        return "id: "+ this.id + "Restriction" + this.type + "\nURL da imagem "+ this.imageURL + "Descripção: " + this.description;
    }

}
