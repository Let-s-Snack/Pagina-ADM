package controller;
import dao.RestrictionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import model.Restriction;

import java.io.IOException;

@WebServlet("/delete-restriction")
public class ServletRestrictionDelete extends HttpServlet {
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
        Restriction restriction;
        try {
            restriction = new Restriction(Integer.parseInt(idParam));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"ID inválido\"}");
            return;
        }

        // Tenta excluir o restrição usando o DAO
        RestrictionDAO restrictionDAO = new RestrictionDAO();
        int result = restrictionDAO.remove(restriction);
        System.out.println("Resultado da atualização: " + result);

        // Retorna uma resposta JSON de sucesso ou erro
        if (result>=1) {
            response.getWriter().write("{\"status\":\"Restrição excluída com sucesso\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro ao excluir Restrição\"}");
        }
    }
}