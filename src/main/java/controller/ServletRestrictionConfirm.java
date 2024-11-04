package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RestrictionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import model.Restriction;

import java.io.IOException;

@WebServlet("/update-restriction")
public class ServletRestrictionConfirm extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configura o ObjectMapper para ler JSON
        ObjectMapper mapper = new ObjectMapper();
        Restriction restriction;

        // Define o Content-Type como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Tenta mapear o JSON para o objeto Restrição
        try {
            System.out.println("Lendo JSON do request...");
            System.out.println(request.getReader().toString());
            restriction = mapper.readValue(request.getReader(), Restriction.class);
            System.out.println("JSON recebido e mapeado com sucesso: " + restriction);
            System.out.println(restriction.getId());
            System.out.println(restriction.getType());
            System.out.println(restriction.getDescription());
            System.out.println(restriction.getImageURL());



        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"Erro ao processar JSON\"}");
            return;
        }

        // Cria instância do DAO e tenta atualizar
        RestrictionDAO restrictionDAO = new RestrictionDAO();
        int result = restrictionDAO.update(restriction);
        System.out.println("Resultado da atualização: " + result);

        // Retorna uma resposta JSON de sucesso ou erro
        if (result >= 1) {
            response.getWriter().write("{\"status\":\"Dados Alterados\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }

}