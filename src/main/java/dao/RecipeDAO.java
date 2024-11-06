package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Recipe;

public class RecipeDAO { // Classe RecipeDAO

    // Declaração dos Atributos
    private PreparedStatement pstmt;

    // Metodo que seleciona todos os registros da tabela Recipe
    public ResultSet selectAll(){
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;
        try{
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe where is_deleted='false' ORDER BY name");
            rs = pstmt.executeQuery();
            return rs;
        } catch(SQLException sql){
            sql.printStackTrace();
            return null;
        } finally{
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionDB.disconnect(); // A desconexão deve ocorrer após a operação com o ResultSet.
        }
    }

    public ResultSet selectByName(Recipe recipe) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe where name = ?");
            pstmt.setString(1, recipe.getName());
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionDB.disconnect(); // A desconexão deve ocorrer após a operação com o ResultSet.
        }
    }

    // Metodo que insere dados na tabela Recipe
    public int insert(Recipe recipe) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            String stepsFormatted = ""; // String formatada
            String[] parts = recipe.getSteps().split("(?=\\d+\\.)"); // Divide os passos quando encontra um número seguido de ponto.

            for (int j = 0; j < parts.length; j++) {
                stepsFormatted += parts[j].trim();
                if (j < parts.length - 1) {
                    stepsFormatted += ";\n";
                }
            }

            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("INSERT INTO RECIPE (NAME, IMAGE_URL, STEPS,DESCRIPTION) VALUES (?,?, ?, ?)");
            pstmt.setString(1, recipe.getName());
            pstmt.setString(2, recipe.getImage_url());
            pstmt.setString(3, stepsFormatted);
            pstmt.setString(4, recipe.getDescription());

            if (validateUrl(recipe.getImage_url())) {
                return pstmt.executeUpdate();
            }
            return 0;

        } catch (SQLException sql) {
            sql.printStackTrace();
            return -1;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionDB.disconnect();
        }
    }

    // Metodo que atualiza os dados da tabela Recipe pelo id
    public int update(Recipe recipe) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;
        int n = 0;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe WHERE id = ?");
            pstmt.setInt(1, recipe.getId());
            rs = pstmt.executeQuery();
            pstmt.close(); // Fecha o pstmt para a próxima operação

            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE recipe SET name=?, image_url=?, steps=?, description=?, is_updated='true' WHERE id = ?");
                pstmt.setString(1, recipe.getName());
                pstmt.setString(2, recipe.getImage_url());
                pstmt.setString(3, recipe.getSteps());
                pstmt.setString(4, recipe.getDescription());
                pstmt.setInt(5, recipe.getId());

                if (validateUrl(recipe.getImage_url())) {
                    return pstmt.executeUpdate();
                }
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionDB.disconnect();
        }
        return n;
    }

    // Metodo que remove os dados da tabela Recipe pelo id
    public int remove(Recipe recipe) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe WHERE id=?");
            pstmt.setInt(1, recipe.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE recipe SET is_deleted = 'true' WHERE id=?");
                pstmt.setInt(1, recipe.getId());
                return pstmt.executeUpdate();
            } else {
                return 0;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionDB.disconnect();
        }
    }

    public static boolean validateUrl(String url) {
        String regex = ".(https://firebasestorage\\.googleapis).";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
} // Fim da classe RecipeDAO