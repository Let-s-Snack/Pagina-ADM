package controller;
import dao.RecipeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import model.Recipe;

import java.io.IOException;
// Mapeamento do servlet para a URL "/delete-recipe"
@WebServlet("/delete-recipe")
public class ServletRecipeDelete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Define o Content-Type como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obtém o ID do ingrediente da requisição
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.getWriter().write("{\"status\":\"ID da Receita não fornecido\"}");
            return;
        }
        Recipe recipe;
        try {
            recipe = new Recipe(Integer.parseInt(idParam));
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"status\":\"ID inválido\"}");
            return;
        }

        // Tenta excluir o restrição usando o DAO
        RecipeDAO recipeDAO = new RecipeDAO();
        int result = recipeDAO.remove(recipe);
        System.out.println("Resultado da atualização: " + result);

        // Retorna uma resposta JSON de sucesso ou erro
        if (result>=1) {
            response.getWriter().write("{\"status\":\"Receita excluída com sucesso\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro ao excluir Restrição\"}");
        }
    }
}