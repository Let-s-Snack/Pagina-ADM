package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.IngredientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ingredient;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/update-ingredient")
public class ServletIngredientConfirm extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configura o ObjectMapper para ler JSON
        ObjectMapper mapper = new ObjectMapper();
        Ingredient ingredient;

        // Define o Content-Type como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Tenta mapear o JSON para o objeto Ingredient
        try {
            System.out.println("Lendo JSON do request...");
            ingredient = mapper.readValue(request.getReader(), Ingredient.class);
            System.out.println("JSON recebido e mapeado com sucesso: " + ingredient);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"Erro ao processar JSON\"}");
            return;
        }

        // Cria instância do DAO e tenta atualizar
        IngredientDAO ingredientDAO = new IngredientDAO();
        int result = ingredientDAO.update(ingredient);
        System.out.println("Resultado da atualização: " + result);

        // Retorna uma resposta JSON de sucesso ou erro
        if (result >= 1) {
            response.getWriter().write("{\"status\":\"Dados Alterados\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }

}