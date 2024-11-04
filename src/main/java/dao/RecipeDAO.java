package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Ingredient;
import model.Recipe;

public class RecipeDAO { // Classe RecipeDAO

//  Declaração dos Atributos
    private PreparedStatement pstmt;

//  Metodo que seleciona todos os registros da tabela Recipe
    public ResultSet selectAll(){
        ConnectionDB connectionDB= new ConnectionDB();
        try{
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe where is_deleted='false' ORDER BY name");
            return pstmt.executeQuery();
        }catch(SQLException sql){
            sql.printStackTrace();
            return null;
        }finally{
            connectionDB.disconnect();
        }
    }
    public ResultSet selectByName(Recipe recipe) {

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB= new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe where name = ?");
            pstmt.setString(1, recipe.getName());
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }
//  Metodo que insere dados na tabela Recipe
    public int insert(Recipe recipe){

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB= new ConnectionDB();
        try{
            String stepsFormatted = "";
            for (int i = 0; i < recipe.getSteps().length(); i++) {
                char valor= recipe.getSteps().charAt(i);
                if (Character.isDigit(valor) && valor!='1'){
                    String[] parts = recipe.getSteps().split("(?=\\d+\\.)");

                    for (int j = 0; j < parts.length; j++){
                        stepsFormatted += parts[i].trim();
                        if (i < parts.length - 1) {
                            stepsFormatted += ";\n";
                        }
                    }
                }
            }
            Connection conn= connectionDB.connect();
            pstmt= conn.prepareStatement("INSERT INTO RECIPE (NAME,ID,STEPS,IMAGE_URL,STEPS) VALUES (?,?,?,?,?)");
            pstmt.setString(1,recipe.getName());
            pstmt.setInt(2,recipe.getId());
            pstmt.setString(3,recipe.getImage_url());
            pstmt.setString(4,recipe.getSteps());
            pstmt.setString(5,stepsFormatted);
            pstmt.execute();
            return pstmt.executeUpdate();

        }catch (SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally{
            connectionDB.disconnect();

        }
    }

//  Metodo que atualiza os dados da tabela Recipe pelo id
    public int update(Recipe recipe) {

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB= new ConnectionDB();
        ResultSet rs;
        int n = 0;
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe WHERE id = ?");
            pstmt.setInt(1, recipe.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE recipe SET name=?, image_url=?, steps = ?, description=? WHERE id = ?");
                pstmt.setString(1, recipe.getName());
                pstmt.setString(2, recipe.getImage_url());
                pstmt.setString(3, recipe.getSteps());
                pstmt.setString(4, recipe.getDescription());
                pstmt.setInt(5, recipe.getId());
                pstmt.executeUpdate();

                // Atualiza a coluna is_updated para 'true'
                pstmt.close();  // Fecha o pstmt para a próxima operação
                //Atualiza a coluna is_updated para True, para sinalizar qeu a operação foi concluída
                pstmt = conn.prepareStatement("update recipe set is_updated ='true' where id =?");
                pstmt.setInt(1, recipe.getId());
                return pstmt.executeUpdate();
            } else {
                    return 0;
                }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

//  Metodo que remove os dados da tabela Recipe pelo id
    public int remove(Recipe recipe){

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB= new ConnectionDB();
        try{
            Connection conn= connectionDB.connect();
            pstmt= conn.prepareStatement("select * from ingredient where id=?");
            pstmt.setInt(1,recipe.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("update ingredient set is_deleted = 'true' where id=?");
                pstmt.setInt(1,recipe.getId());
                return pstmt.executeUpdate();
            } else {
                return 0;
            }
        }catch (SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally{
            connectionDB.disconnect();
        }
    }
}//Fim da classe RecipeDAO