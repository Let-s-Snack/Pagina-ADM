package model;

public class Adm { //Classe Adm

//  Declaração dos Atributos
    private String email;
    private String password;
    private String name;

//  Construtor
    public Adm(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

//  Construtor para a página de ADM

    public Adm(String email) {
        this.email = email;

    }
    public Adm() {}

//  Getters e Setters

//  Getters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

//  Setters
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

//  ToString
    @Override
    public String toString() {
        return "Name: " + name + "Email: " + email + "Password: " + password + "\n";
    }

}//Fim da classe Adm
