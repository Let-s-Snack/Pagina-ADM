package dao;

import model.IngredientRecipe;

import java.sql.*;

public class IngredientRecipeDAO {

    private PreparedStatement pstmt;

    public ResultSet selectById(int id) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient_recipe WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();

        }
    }

    public ResultSet selectAll(int recipe_id) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient_recipe WHERE recipe_id = ?");
            pstmt.setInt(1, recipe_id);
            rs = pstmt.executeQuery();

            // Verifique se o resultado possui dados, mas não feche o ResultSet
            if (rs.isBeforeFirst()) {
                // Feche o PreparedStatement para preparar o próximo
                pstmt.close();
                pstmt = conn.prepareStatement(
                        "SELECT i.name AS ingredient_name, r.name AS recipe_name, ir.measure, ir.quantity, ir.id, r.id AS recipe_id " +
                                "FROM ingredient_recipe ir " +
                                "JOIN recipe r ON r.id = ir.recipe_id " +
                                "JOIN ingredient i ON i.id = ir.ingredient_id " +
                                "WHERE ir.recipe_id = ? AND ir.is_deleted = 'false'"
                );
                pstmt.setInt(1, recipe_id);
                return pstmt.executeQuery(); // Retorne diretamente o novo ResultSet
            } else {
                return null; // Retorne null se não houver dados
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }


    public int remove(IngredientRecipe ingredientRecipe) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient_recipe WHERE id = ?");
            pstmt.setInt(1, ingredientRecipe.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rs.close();
                pstmt.close();
                pstmt = conn.prepareStatement("UPDATE ingredient_recipe SET is_deleted = 'true' WHERE ingredient_recipe.id = ?");
                pstmt.setInt(1, ingredientRecipe.getId());
                return pstmt.executeUpdate();
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();

        }
    }

    public int update(IngredientRecipe ingredientRecipe) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient_recipe WHERE id = ?");
            pstmt.setInt(1, ingredientRecipe.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rs.close();
                pstmt.close();
                pstmt = conn.prepareStatement("UPDATE ingredient_recipe SET measure = ?, quantity = ? WHERE id = ?");
                pstmt.setString(1, ingredientRecipe.getMeasure());
                pstmt.setInt(2, ingredientRecipe.getQuantity());
                pstmt.setInt(3, ingredientRecipe.getId());
                pstmt.executeUpdate();

                pstmt.close();
                pstmt = conn.prepareStatement("UPDATE ingredient_recipe SET is_updated = 'true' WHERE id = ?");
                pstmt.setInt(1, ingredientRecipe.getId());
                return pstmt.executeUpdate();
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();

        }
    }

    public ResultSet selectIdRecipe(String recipeName) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT id FROM recipe WHERE name = ?");
            pstmt.setString(1, recipeName);
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionDB.disconnect();

        }
    }

    public ResultSet selectIdIngredient(String ingredientName) {
        ConnectionDB connectionDB = new ConnectionDB();
        ResultSet rs = null;

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT id FROM ingredient WHERE name = ?");
            pstmt.setString(1, ingredientName);
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionDB.disconnect();

        }
    }

    public int insertIngredientRecipe(int ingredientId, int recipeId, String measure, int quantity) {
        ConnectionDB connectionDB = new ConnectionDB();

        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("INSERT INTO ingredient_recipe (ingredient_id, recipe_id, measure, quantity) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, ingredientId);
            pstmt.setInt(2, recipeId);
            pstmt.setString(3, measure);
            pstmt.setInt(4, quantity);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();
        }
    }

}