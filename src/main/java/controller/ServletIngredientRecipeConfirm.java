package controller;

// Importações necessárias, incluindo ObjectMapper para manipulação de JSON e DAO para atualizar dados
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.IngredientRecipeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import model.IngredientRecipe;

import java.io.IOException;

// Mapeamento do servlet para a URL "/update-ingredientRecipe"
@WebServlet("/update-ingredientRecipe")
public class ServletIngredientRecipeConfirm extends HttpServlet {

    // Sobrescreve o método doPost para tratar requisições HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configura o ObjectMapper para converter JSON em objetos Java
        ObjectMapper mapper = new ObjectMapper();
        IngredientRecipe ingredientRecipe;

        // Define o Content-Type da resposta como JSON e o encoding como UTF-8
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Tenta ler e mapear o JSON da requisição para um objeto IngredientRecipe
        try {
            System.out.println("Lendo JSON do request...");
            ingredientRecipe = mapper.readValue(request.getReader(), IngredientRecipe.class);
            System.out.println("JSON recebido e mapeado com sucesso: " + ingredientRecipe);
        } catch (IOException e) {
            // Em caso de erro no processamento do JSON, define o status como 400 (BAD REQUEST) e retorna uma mensagem de erro
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"Erro ao processar JSON\"}");
            return;
        }

        // Cria uma instância de IngredientRecipeDAO e tenta atualizar os dados
        IngredientRecipeDAO ingredientRecipeDAO = new IngredientRecipeDAO();
        int result = ingredientRecipeDAO.update(ingredientRecipe);
        System.out.println("Resultado da atualização: " + result);

        // Retorna uma resposta JSON indicando sucesso ou erro com base no resultado da atualização
        if (result >= 1) {
            response.getWriter().write("{\"status\":\"Dados Alterados\"}");
        } else {
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }
}
