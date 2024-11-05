package dao;

import model.IngredientRecipe;

import java.sql.*;

public class IngredientRecipeDAO { // Classe Ingredient Recipe

    // Declaração dos Atributos
    private PreparedStatement pstmt;

    // Método que encontra o ingrediente Recipe pelo id
    public ResultSet selectById(int id) {
        ConnectionDB connectionDB = new ConnectionDB();
        IngredientRecipeDAO ingredientRecipeDAO = new IngredientRecipeDAO(); // Instância desnecessária

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient_recipe WHERE id = ?");
            pstmt.setInt(1, id);
            return pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Retorna null em caso de erro
        } finally {
            connectionDB.disconnect(); // Desconecta após a execução
        }
    }

    // Método que encontra o Ingredient Recipe pelo id da receita
    public ResultSet selectAll(int recipe_id) {
        ConnectionDB connectionDB = new ConnectionDB();

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient_recipe WHERE recipe_id = ?");
            // Selecionar os dados da restrição baseados no id colocado como parâmetro
            pstmt.setInt(1, recipe_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Verifica se há resultados
                pstmt = conn.prepareStatement("SELECT i.name AS ingredient_name, r.name AS recipe_name, ir.measure, ir.quantity, ir.id, r.id AS recipe_id " +
                        "FROM ingredient_recipe ir JOIN recipe r ON r.id = ir.recipe_id " +
                        "JOIN ingredient i ON i.id = ir.ingredient_id WHERE recipe_id = ? AND ir.is_deleted = 'false'");
                pstmt.setInt(1, recipe_id);
                return pstmt.executeQuery();

            } else {
                return null; // Não encontrou o recipe_id
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Retorna null em caso de erro
        } finally {
            connectionDB.disconnect(); // Desconecta após a execução
        }
    }

    // Método que remove dados da tabela Ingredient Recipe pelo id
    public int remove(IngredientRecipe ingredientRecipe) {
        // Utilizando a classe ConnectionDB para acessar os métodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient_recipe WHERE id = ?");
            pstmt.setInt(1, ingredientRecipe.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE ingredient_recipe SET is_deleted = 'true' WHERE ingredient_recipe.id = ?");
                // Os dados não serão removidos, apenas desativados e para isso, declaramos a coluna is_deleted como True
                pstmt.setInt(1, ingredientRecipe.getId());
                return pstmt.executeUpdate(); // Retorna o número de registros afetados
            } else {
                return 0; // Retorna 0 se o ID não for encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna -1 em caso de erro na execução
        } finally {
            connectionDB.disconnect(); // Desconecta após a execução
        }
    }

    // Método que atualiza os dados da tabela Ingredient pelo id
    public int update(IngredientRecipe ingredientRecipe) {
        // Utilizando a classe ConnectionDB para acessar os métodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient_recipe WHERE id = ?");
            pstmt.setInt(1, ingredientRecipe.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE ingredient_recipe SET measure = ?, quantity = ? WHERE id = ?");
                pstmt.setString(1, ingredientRecipe.getMeasure());
                pstmt.setInt(2, ingredientRecipe.getQuantity());
                pstmt.setInt(3, ingredientRecipe.getId());
                pstmt.executeUpdate(); // Executa a atualização

                // Atualiza a coluna is_updated para 'true'
                pstmt.close(); // Fecha o pstmt para a próxima operação
                // Atualiza a coluna is_updated para True, para sinalizar que a operação foi concluída
                pstmt = conn.prepareStatement("UPDATE ingredient_recipe SET is_updated = 'true' WHERE ingredient_recipe.id = ?");
                pstmt.setInt(1, ingredientRecipe.getId());
                return pstmt.executeUpdate(); // Retorna o número de registros afetados
            } else {
                return 0; // Retorna 0 se o ID não for encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna -1 em caso de erro na execução
        } finally {
            connectionDB.disconnect(); // Desconecta após a execução
        }
    }
     public ResultSet selectIdRecipe(String recipeName) {
         ConnectionDB connectionDB = new ConnectionDB();
         try {
             Connection conn = connectionDB.connect();
             pstmt = conn.prepareStatement("SELECT id FROM recipe WHERE name = ?");
             pstmt.setString(1, recipeName);
             return pstmt.executeQuery();
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }
    public ResultSet selectIdIngredient(String ingredientName) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT id FROM ingredient WHERE name = ?");
            pstmt.setString(1, ingredientName);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Método que insere um novo Ingredient Recipe
    public int insertIngredientRecipe(int ingredientId, int recipeId, String measure, int quantity) {
        ConnectionDB connectionDB = new ConnectionDB();

        try {
            Connection conn = connectionDB.connect();

            pstmt = conn.prepareStatement("INSERT INTO ingredient_recipe (ingredient_id, recipe_id, measure, quantity) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, ingredientId);
            pstmt.setInt(2, recipeId);
            pstmt.setString(3, measure);
            pstmt.setInt(4, quantity);

            return pstmt.executeUpdate(); // Retorna o número de registros afetados
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna -1 em caso de erro
        } finally {
            connectionDB.disconnect(); // Desconecta após a execução
        }
    }
} // Fim da classe IngredientRecipeDAO
