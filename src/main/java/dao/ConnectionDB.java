package dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public Connection connect(){
        try{
            // infromando qual driver de conexão será utilizado pelo driverManager
            Class.forName("org.postgresql.Driver");
            String projectRoot = System.getProperty("user.dir");

            // Define o caminho absoluto do arquivo .env
            String envPath = Paths.get(projectRoot, ".env").toString();
            Dotenv dotenv = Dotenv.load();



            // Pegar as variáveis do .env
            String user = dotenv.get("USER");
            String password = dotenv.get("PASSWORD");
            // criando a conexão com o BD
            java.sql.Connection conn= DriverManager.getConnection("jdbc:postgresql://pg-lets-snack-lets-snack.k.aivencloud.com:18692/db-lets-snack-1o", user,password);
            return conn;
        }catch(SQLException sql){
            sql.printStackTrace();
            return null;
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
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
