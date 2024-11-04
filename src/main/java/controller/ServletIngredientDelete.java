package controller;

import dao.IngredientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ingredient;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/delete-ingredient")
public class ServletIngredientDelete extends HttpServlet {
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
        Ingredient ingredient;
        try {
            ingredient = new Ingredient(Integer.parseInt(idParam));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"ID inválido\"}");
            return;
        }

        // Tenta excluir o ingrediente usando o DAO
        IngredientDAO ingredientDAO = new IngredientDAO();
        int result = ingredientDAO.remove(ingredient); // Implementação do método de exclusão

        // Retorna uma resposta JSON de sucesso ou erro
        if (result>=1) {
            response.getWriter().write("{\"status\":\"Ingrediente excluído com sucesso\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro ao excluir ingrediente\"}");
        }
    }
}