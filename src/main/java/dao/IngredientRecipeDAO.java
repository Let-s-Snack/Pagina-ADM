package dao;


import java.sql.*;

public class IngredientRecipeDAO {
    private PreparedStatement pstmt;

    //método para selecionar toda a tabela
    public ResultSet selectAll() {
        ConnectionDB connectionDB= new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("select * from ingredient_recipe");
            //Selecionando tudo de restrição
            return pstmt.executeQuery(); // Correto para consulta
            // executando o comando
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;

        } finally {
            connectionDB.disconnect();
        }
    }

    //encontra  a ingrediente_recipe peolo id
    public ResultSet searchRecipeForID(int recipe_id ) {
        ConnectionDB connectionDB= new ConnectionDB();

        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("select * from ingredient_recipe  where recipe_id=?");
            // Selecionar os dados da restrição baseados no id colocado cmo parâmetro
            pstmt.setInt(1, recipe_id);
            ResultSet rs = pstmt.executeQuery();
            // Se o select for true, fazemos o a operção
            if (rs.next()) {
                pstmt = conn.prepareStatement("select ingredient.name , ingredient.id , ingredient.description , ingredient.transaction_made from ingredient join  ingredient_recipe on ingredient.id = ingredient_recipe.ingredient_id   where recipe_id =?");
                pstmt.setInt(1, recipe_id);
                return pstmt.executeQuery();

            } else {
                return null; //Registro não encontrado
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }finally{
             connectionDB.disconnect();
        }

    }
    //deleta pelo id
    public int  deleteRecipeForID(int id ) {
        ConnectionDB connectionDB= new ConnectionDB();

        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("select * from ingredient_recipe  where id=?");
            // Selecionar os dados da restrição baseados no id colocado cmo parâmetro
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("update ingredient_recipe set is_deleted ='true' where ingredient_recipe.id =?");
                pstmt.setInt(1, id);
                return pstmt.executeUpdate();
            } else {
                return 0 ;
                //não encontrou o id, retorna 0
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1 ;
        }finally{
             connectionDB.disconnect();
        }
    }
    //atualiza pelo id
    public int  updateRecipeForID(int id ) {
        ConnectionDB connectionDB= new ConnectionDB();

        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("select * from ingredient_recipe  where id=?");
            // Selecionar os dados da restrição baseados no id colocado cmo parâmetro
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Se o select for true, fazemos o a operação
            if (rs.next()) {
                pstmt = conn.prepareStatement("update ingredient_recipe set is_updated ='true' where ingredient_recipe.id =?");
                pstmt.setInt(1, id);
                return pstmt.executeUpdate();
            } else {
                return 0 ;
                //não encontrou o id
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1 ;
        }finally{
             connectionDB.disconnect();
        }

    }
}