package controller;

public class ServletAdm { //Classe Adm
    //declaração dos Atributo
    private String email;
    private String password;
    private String name;
    private int id;

    //Construtor

    public ServletAdm(String email, String password, String name, int id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.id = id;
    }

    //Construtor para pag. de adm
    public ServletAdm(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    //Getters e Setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }

    //To String
    @Override
    public String toString() {
        return "Name: " + name + "Email: " + email + "Password: " + password + "\n";
    }
    //final da classe de Adm
}
