package controller;

// Importações necessárias para o funcionamento do servlet e manipulação de requisições HTTP
import dao.IngredientRecipeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

// Mapeamento do servlet para a URL "/insert-ingredientRecipe"
@WebServlet("/insert-ingredientRecipe")
public class ServletIngredientRecipeInsert extends HttpServlet {

    // Sobrescreve o método doPost para tratar requisições HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém os parâmetros "ingredient", "recipe", "measure", "quantity" e "recipe_id" do formulário enviado
        String ingredient = request.getParameter("ingredient");
        String recipe = request.getParameter("recipe");
        String measure = request.getParameter("measure");
        String quantityStr = request.getParameter("quantity");
        int quantity=Integer.parseInt(quantityStr);
        System.out.println("Ingredient: '" + ingredient + "'");
        System.out.println("Recipe: '" + recipe + "'");
        System.out.println("Measure: '" + measure + "'");
        System.out.println("Quantity: '" + quantityStr + "'");
        try {
            System.out.println("Ingredient: '" + ingredient + "'");
            System.out.println("Recipe: '" + recipe + "'");
            System.out.println("Measure: '" + measure + "'");
            System.out.println("Quantity: '" + quantityStr + "'");

            // Instancia o DAO para manipulação de dados
            IngredientRecipeDAO ingredientRecipeDAO = new IngredientRecipeDAO();

            int recipeId=-1;
            int ingredientId=-1;
            ResultSet rs= ingredientRecipeDAO.selectIdRecipe(recipe) ;
            recipeId = rs.getInt("recipe_id"); // substitua "id_recipe" pelo nome real da coluna
            ResultSet rsIngredient= ingredientRecipeDAO.selectIdIngredient(ingredient);
            ingredientId = rsIngredient.getInt("ingredient_id");
            // Insere o relacionamento ingrediente-receita no banco de dados e armazena o resultado
            int result = ingredientRecipeDAO.insertIngredientRecipe(ingredientId, recipeId, measure, quantity);

            // Se a inserção for bem-sucedida, redireciona
            if (result >= 1) {
                response.sendRedirect("pages/pageIngredientRecipe.jsp?recipe_id=" + recipeId);
            } else {
                response.getWriter().write("{\"status\":\"Erro na inserção.\"}");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"status\":\"Erro: Formato de quantidade ou ID inválido.\"}");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
