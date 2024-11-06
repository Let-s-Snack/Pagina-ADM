package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Recipe;

public class RecipeDAO { // Classe RecipeDAO

    //  Declaração dos Atributos
    private PreparedStatement pstmt;

    //  Metodo que seleciona todos os registros da tabela Recipe
    public ResultSet selectAll(){
        ConnectionDB connectionDB = new ConnectionDB();
        try{
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe where is_deleted='false' ORDER BY name");
            return pstmt.executeQuery();
        }catch(SQLException sql){
            sql.printStackTrace();
            return null;
        }finally{
            connectionDB.disconnect(); // A desconexão deve ocorrer após a operação com o ResultSet.
        }
    }

    public ResultSet selectByName(Recipe recipe) {
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe where name = ?");
            pstmt.setString(1, recipe.getName());
            return pstmt.executeQuery(); // Considerar retornar um objeto Recipe ao invés de ResultSet.
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect(); // A desconexão deve ocorrer após a operação com o ResultSet.
        }
    }

    //  Metodo que insere dados na tabela Recipe
    public int insert(Recipe recipe) {
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            String stepsFormatted = ""; //  String formatada

            for (int i = 0; i < recipe.getSteps().length(); i++) {
                // pega todos os caracteres
                char valor= recipe.getSteps().charAt(i);
                if (Character.isDigit(valor) && valor!='1'){
                    // se for número, coloca um ponto logo em seguida
                    String[] parts = recipe.getSteps().split("(?=\\d+\\.)");
                    // se estiver no final, coloca ;
                    for (int j = 0; j < parts.length; j++){
                        stepsFormatted += parts[i].trim();
                        if (i < parts.length - 1) {
                            stepsFormatted += ";\n";
                        }
                    }
                }
            }

            Connection conn = connectionDB.connect();
            // Corrigir: "STEPS" aparece duas vezes na instrução SQL, isso pode causar confusão.
            pstmt = conn.prepareStatement("INSERT INTO RECIPE (NAME, IMAGE_URL, STEPS, STEPS_FORMATTED) VALUES (?, ?, ?, ?)"); // Corrigir nome da coluna
            pstmt.setString(1, recipe.getName());
            pstmt.setString(2, recipe.getImage_url());
            pstmt.setString(3, recipe.getSteps());
            pstmt.setString(4, stepsFormatted); // Garantir que este campo exista na tabela.

            // Verifica se o formato da URL é válida
            if (validateUrl(recipe.getImage_url())) {
                return pstmt.executeUpdate(); // Remover o execute() que não é necessário.
            }
            return 0; // Retornar 0 se a URL não for válida.

        } catch (SQLException sql) {
            sql.printStackTrace();
            return -1; // Considerar lançar uma exceção ao invés de retornar -1.
        } finally {
            connectionDB.disconnect(); // A desconexão deve ocorrer após a operação com o PreparedStatement.
        }
    }

    //  Metodo que atualiza os dados da tabela Recipe pelo id
    public int update(Recipe recipe) {
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs;
        int n = 0;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM recipe WHERE id = ?");
            pstmt.setInt(1, recipe.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE recipe SET name=?, image_url=?, steps=?, description=? WHERE id = ?");
                pstmt.setString(1, recipe.getName());
                pstmt.setString(2, recipe.getImage_url());
                pstmt.setString(3, recipe.getSteps());
                pstmt.setString(4, recipe.getDescription());
                pstmt.setInt(5, recipe.getId());

                // Verifica se o formato da URL é válida
                if (validateUrl(recipe.getImage_url())) {
                    pstmt.executeUpdate(); // Remover o execute() que não é necessário.
                }

                // Atualiza a coluna is_updated para 'true' (sinaliza que a operação foi concluída)
                pstmt.close();  // Fecha o pstmt para a próxima operação
                pstmt = conn.prepareStatement("update recipe set is_updated ='true' where id =?");
                pstmt.setInt(1, recipe.getId());
                return pstmt.executeUpdate();
            } else {
                return 0; // Retornar 0 se não encontrar a receita.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1; // Considerar lançar uma exceção ao invés de retornar -1.
        } finally {
            connectionDB.disconnect(); // A desconexão deve ocorrer após a operação com o PreparedStatement.
        }
        return n;
    }

    //  Metodo que remove os dados da tabela Recipe pelo id
    public int remove(Recipe recipe) {
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("select * from recipe where id=?"); // Corrigir: estava referenciando 'ingredient'
            pstmt.setInt(1, recipe.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("update recipe set is_deleted = 'true' where id=?"); // Corrigir para 'recipe'
                pstmt.setInt(1, recipe.getId());
                return pstmt.executeUpdate();
            } else {
                return 0; // Retornar 0 se a receita não for encontrada.
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return -1; // Considerar lançar uma exceção ao invés de retornar -1.
        } finally {
            connectionDB.disconnect(); // A desconexão deve ocorrer após a operação com o PreparedStatement.
        }
    }

    public static boolean validateUrl(String url) {
        String regex = ".\\.(png|jpeg|PNG|JPEG|jpg)."; // definindo o regex que valida os dados
        Pattern pattern = Pattern.compile(regex); // transformando o regex em um padrão
        Matcher matcher = pattern.matcher(url); // comparando o padrão com a URL
        return matcher.matches(); // retorna true para correta e false para incorreta
    }
} // Fim da classe RecipeDAO