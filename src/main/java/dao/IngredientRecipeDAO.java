package dao;


import model.IngredientRecipe;

import java.sql.*;

public class IngredientRecipeDAO { //Classe Ingredient Recipe

//  Declaração dos Atributos
    private PreparedStatement pstmt;

//    Método que encontra o ingrediente Recipe pelo id
    public ResultSet selectById(int id){
        ConnectionDB connectionDB = new ConnectionDB();
        IngredientRecipeDAO ingredientRecipeDAO = new IngredientRecipeDAO();

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("select * from ingredient_recipe where id= ?");
            pstmt.setInt(1, id);
            return pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

//  Metodo que encontra o Ingredient Recipe pelo id da receita
    public ResultSet selectAll(int recipe_id ) {
        ConnectionDB connectionDB = new ConnectionDB();

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("select * from ingredient_recipe  where recipe_id=?");
    //            Selecionar os dados da restrição baseados no id colocado cmo parâmetro
            pstmt.setInt(1, recipe_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("select i.name as ingredient_name , r.name as recipe_name, ir.measure , ir.quantity, ir.id,  r.id as recipe_id  from ingredient_recipe ir  join recipe r  on  r.id = ir.recipe_id join ingredient i  on i.id = ir.ingredient_id where recipe_id =? and ir.is_deleted='false' ");
                pstmt.setInt(1, recipe_id);
                return pstmt.executeQuery();

            } else {
                return null;
                //não encontrou o recipe_id

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }
//  Metodo que remove dados da tabela Ingredient Recipe pelo id
    public int  remove(IngredientRecipe ingredientRecipe) {

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB= new ConnectionDB();

        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("select * from ingredient_recipe  where id=?");
            pstmt.setInt(1, ingredientRecipe.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("update ingredient_recipe set is_deleted ='true' where ingredient_recipe.id =?");
//              Os dados não serão removidos, apenas desativados e para isso, declaramos a coluna is_deleted como True

                pstmt.setInt(1, ingredientRecipe.getId());
                return pstmt.executeUpdate();
            } else {
                return 0 ;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1 ;
        }finally{
             connectionDB.disconnect();
        }
    }

//  Metodo que atualiza os dados da tabela Ingredient pelo id
    public int  update(IngredientRecipe ingredientRecipe) {

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB= new ConnectionDB();

        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("select * from ingredient_recipe  where id=?");
            pstmt.setInt(1, ingredientRecipe.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("update ingredient_recipe set measure=?, quantity = ? where id = ?");
                pstmt.setString(1, ingredientRecipe.getMeasure());
                pstmt.setInt(2, ingredientRecipe.getQuantity());
                pstmt.setInt(3,ingredientRecipe.getId());
                pstmt.executeUpdate();

                // Atualiza a coluna is_updated para 'true'
                pstmt.close();  // Fecha o pstmt para a próxima operação
                //Atualiza a coluna is_updated para True, para sinalizar qeu a operação foi concluída
//              Atualiza a coluna is_updated para True, para sinalizar qeu a operação foi concluída
                pstmt = conn.prepareStatement("update ingredient_recipe set is_updated ='true' where ingredient_recipe.id =?");
                pstmt.setInt(1, ingredientRecipe.getId());
                return pstmt.executeUpdate();
            } else {
                return 0 ;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1 ;
        }finally{
             connectionDB.disconnect();
        }

    }
}//Fim da classe Ingredient RecipeDAO