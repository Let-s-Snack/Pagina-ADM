package controller;
import dao.IngredientRecipeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import model.IngredientRecipe;

import java.io.IOException;

@WebServlet("/delete-ingredientRecipe")
public class ServletIngredientRecipeDelete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Define o Content-Type como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obtém o ID do ingrediente da requisição
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"ID do ingrediente não fornecido\"}");
            return;
        }
        IngredientRecipe ingredientRecipe;
        try {
            ingredientRecipe = new IngredientRecipe(Integer.parseInt(idParam));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"ID inválido\"}");
            return;
        }

        // Tenta excluir o ingrediente usando o DAO
        IngredientRecipeDAO ingredientRecipeDAO = new IngredientRecipeDAO();
        int result = ingredientRecipeDAO.remove(ingredientRecipe); // Implementação do método de exclusão

        // Retorna uma resposta JSON de sucesso ou erro
        if (result>=1) {
            response.getWriter().write("{\"status\":\"Ingrediente excluído com sucesso\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro ao excluir ingrediente\"}");
        }
    }
}