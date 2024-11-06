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
            recipe = mapper.readValue(request.getReader(), Recipe.class);
            System.out.println("Dados da receita recebidos para atualização:");
            System.out.println("ID: " + recipe.getId());
            System.out.println("Nome: " + recipe.getName());
            System.out.println("URL da Imagem: " + recipe.getImage_url());
            System.out.println("Descrição: " + recipe.getDescription());
            System.out.println("Passos: " + recipe.getSteps());

        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"Erro ao processar JSON\"}");
            return;
        }

        // Cria instância do DAO e tenta atualizar
        System.out.println(recipe.getDescription()+" "+recipe.getImage_url()+" "+recipe.getName()+" "+recipe.getSteps());
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