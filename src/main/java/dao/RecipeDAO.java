//importação de bibliotecas java
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Recipe;

public class RecipeDAO {// Classe RecipeDao
    private PreparedStatement pstmt;

    public ResultSet selectAll(){
        ResultSet rset = null;
        ConnectionDB connectionDB= new ConnectionDB();
        try{
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM repice ORDER BY id");
            rset = pstmt.executeQuery();
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        connectionDB.disconnect();
        return rset;

    }
    public int insert(Recipe recipe){
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

    public int update(Recipe recipe) {
        ConnectionDB connectionDB= new ConnectionDB();
        ResultSet rs;
        int n = 0;
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe WHERE id = ?");
            pstmt.setInt(1, recipe.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE recipe SET name, image_url, steps = ?,?,? WHERE id = ?");
                pstmt.setString(1, recipe.getName());
                pstmt.setString(2, recipe.getImage_url());
                pstmt.setString(3, recipe.getSteps());
                if (pstmt.executeUpdate() > 0) {
                    n = 1;
                } else {
                    n = -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

    public int remove(int id){
        ConnectionDB connectionDB= new ConnectionDB();
        try{
            Connection conn= connectionDB.connect();
            pstmt= conn.prepareStatement("DELETE FROM recipe where id=?");
            pstmt.setInt(1,id);
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

}