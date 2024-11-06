package dao;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB { //Classe ConnectionDB

    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    //  Metodo connect

//  Conecta com o banco e será utilizados nos DAOs
    public Connection connect(){
        try{
//          Criação do objeto Properties
             var props = new Properties();

 //          Definindo caminho para o arquivo .env
             var envFile = Paths.get("C:\\envs\\projeto\\.env");

 //          Criando inputStream para ler o conteúdo do arquivo .env
             var inputStream = Files.newInputStream(envFile);

 //          Carrega os dados do arquivo .env para o props
             props.load(inputStream);
            String user = (String) props.get("USER");
            String password = (String) props.get("PASSWORD");
            Class.forName("org.postgresql.Driver");

//          Criando a conexão com o BD
            conn= DriverManager.getConnection("jdbc:postgresql://pg-lets-snack-lets-snack.k.aivencloud.com:18692/db-lets-snack-1o",user,password);
            return conn;

        }catch(SQLException sql){
            sql.printStackTrace();
            return null;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

//  Metodo disconnect

//  Encerra a conexão com o banco e será utilizado nos DAOs
    public void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}//Fim da classe ConnectionDB
