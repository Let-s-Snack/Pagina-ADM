package dao;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB {
    public Connection connect(){
        try{
            var props = new Properties();
            var envFile = Paths.get("C:\\envs\\projeto\\.env");
            var inputStream = Files.newInputStream(envFile);
            props.load(inputStream);

            String user = (String) props.get("USER");
            String password = (String) props.get("PASSWORD");
            Class.forName("org.postgresql.Driver");

            // criando a conexão com o BD
            java.sql.Connection conn= DriverManager.getConnection("jdbc:postgresql://pg-lets-snack-lets-snack.k.aivencloud.com:18692/db-lets-snack-1o",user,password);
            return conn;
        }catch(SQLException sql){
            sql.printStackTrace();
            return null;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    public void disconnect() {
        Connection conn= null;

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
