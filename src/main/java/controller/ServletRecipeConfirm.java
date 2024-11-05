package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RecipeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import model.Recipe;

import java.io.IOException;

@WebServlet("/update-recipe")
public class ServletRecipeConfirm extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configura o ObjectMapper para ler JSON
        ObjectMapper mapper = new ObjectMapper();
        Recipe recipe;

        // Define o Content-Type como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Tenta mapear o JSON para o objeto Receita
        try {
            System.out.println("Lendo JSON do request...");
            System.out.println(request.getReader().toString());
            recipe = mapper.readValue(request.getReader(), Recipe.class);
            System.out.println("JSON recebido e mapeado com sucesso: " + recipe);

        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"Erro ao processar JSON\"}");
            return;
        }

        // Cria instância do DAO e tenta atualizar
        RecipeDAO recipeDAO = new RecipeDAO();
        int result = recipeDAO.update(recipe);
        System.out.println("Resultado da atualização: " + result);

        // Retorna uma resposta JSON de sucesso ou erro
        if (result >= 1) {
            response.getWriter().write("{\"status\":\"Dados Alterados\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }

}