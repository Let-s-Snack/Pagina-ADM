package controller;
import dao.AdmDAO;
import dao.IngredientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Adm;
import model.Ingredient;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/delete-adm")
public class ServletAdmDelete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Define o Content-Type como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obtém o ID do ingrediente da requisição
        String email = request.getParameter("email");
        if (email == null || email.isEmpty()) {
            response.getWriter().write("{\"status\":\"ID do adm não fornecido\"}");
            return;
        }
        Adm adm;
        try {
            adm = new Adm(email);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"ID inválido\"}");
            return;
        }

        // Tenta excluir o ingrediente usando o DAO
        AdmDAO admDAO = new AdmDAO();
        int result = admDAO.remove(adm); // Implementação do método de exclusão

        // Retorna uma resposta JSON de sucesso ou erro
        if (result>=1) {
            response.getWriter().write("{\"status\":\"Adm excluído com sucesso\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro ao excluir o adm\"}");
        }
    }
}