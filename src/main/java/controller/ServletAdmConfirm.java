package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Adm;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/update-adm")
public class ServletAdmConfirm extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configura o ObjectMapper para ler JSON
        ObjectMapper mapper = new ObjectMapper();
        Adm adm;

        // Define o Content-Type como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Tenta mapear o JSON para o objeto Ingredient
        try {
            System.out.println("Lendo JSON do request...");
            adm = mapper.readValue(request.getReader(), Adm.class);
            System.out.println("JSON recebido e mapeado com sucesso: " + adm);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"Erro ao processar JSON\"}");
            return;
        }

        // Cria instância do DAO e tenta atualizar
        AdmDAO admDAO = new AdmDAO();
        int result = admDAO.update(adm);
        System.out.println("Resultado da atualização: " + result);

        // Retorna uma resposta JSON de sucesso ou erro
        if (result >= 1) {
            response.getWriter().write("{\"status\":\"Dados Alterados\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }

}