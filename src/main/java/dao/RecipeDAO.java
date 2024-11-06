package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Recipe;
import org.postgresql.util.PSQLException;

public class RecipeDAO { // Classe RecipeDAO

    // Declaração dos Atributos
    private PreparedStatement pstmt;
    private ResultSet rs;

    // Metodo que seleciona todos os registros da tabela Recipe
    public ResultSet selectAll(){
        ConnectionDB connectionDB = new ConnectionDB();
        try{
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe where is_deleted='false' ORDER BY name");
            rs = pstmt.executeQuery();
            return rs;
        } catch(SQLException sql){
            sql.printStackTrace();
            return null;
        } finally{
            connectionDB.disconnect();
            // A desconexão deve ocorrer após a operação com o ResultSet.
        }
    }

    public ResultSet selectByName(Recipe recipe) {
        ConnectionDB connectionDB = new ConnectionDB();
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
            connectionDB.disconnect();
            // A desconexão deve ocorrer após a operação com o ResultSet.
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
            connectionDB.disconnect();

        }
    }

    // Metodo que atualiza os dados da tabela Recipe pelo id
    public int update(Recipe recipe) {
        ConnectionDB connectionDB = new ConnectionDB();
        int n = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = connectionDB.connect();

            // Primeiro, verifica se o registro existe
            pstmt = conn.prepareStatement("SELECT * FROM recipe WHERE id = ?");
            pstmt.setInt(1, recipe.getId());
            rs = pstmt.executeQuery();

            // Verifica se há um registro com o ID fornecido
            if (rs.next()) {
                System.out.println("Registro encontrado para o ID: " + recipe.getId());

                // Primeira atualização: Define is_updated como true
                pstmt.close(); // Fecha o primeiro pstmt
                pstmt = conn.prepareStatement("UPDATE recipe SET is_updated = true WHERE id = ?");
                pstmt.setInt(1, recipe.getId());
                int updateResult = pstmt.executeUpdate();
                pstmt.close();

                if (updateResult > 0) {
                    System.out.println("primeira atualização certa");
                }
                else {
                    System.out.println("falhou na primeira atualização");
                }

                // Segunda atualização: Só executa se a primeira atualização foi bem sucedida
                if (validateUrl(recipe.getImage_url())) {
                    System.out.println("URL válido para atualização.");
                    pstmt = conn.prepareStatement("UPDATE recipe SET name=?, image_url=?, steps=?, description=? WHERE id = ?");
                    pstmt.setString(1, recipe.getName());
                    pstmt.setString(2, recipe.getImage_url());
                    pstmt.setString(3, recipe.getSteps());
                    pstmt.setString(4, recipe.getDescription());
                    pstmt.setInt(5, recipe.getId());
                    n = pstmt.executeUpdate();
                    System.out.println("Segunda atualização concluída.");
                } else {
                    System.out.println("URL inválido.");
                }
            } else {
                System.out.println("Nenhum registro encontrado para o ID: " + recipe.getId());
                n = 0; // Retorna 0 se o registro não existir
            }
        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
            e.printStackTrace();
            n = -1;
        } finally {
            // Libera recursos
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
            connectionDB.disconnect();

        }
    }

    public static boolean validateUrl(String url) {
        String regex = "^https://firebasestorage\\.googleapis\\.com/.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
} // Fim da classe RecipeDAO