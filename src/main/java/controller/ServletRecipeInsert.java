package controller;

// Importações necessárias para manipulação do servlet e acesso ao DAO e ao modelo Recipe
import dao.RecipeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import model.Recipe;

import java.io.IOException;

// Mapeamento do servlet para a URL "/insert-recipe"
@WebServlet("/insert-recipe")
public class ServletRecipeInsert extends HttpServlet {

    // Sobrescreve o método doPost para tratar requisições HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém os parâmetros do formulário enviados para o servlet: nome, URL da imagem, etapas e descrição
        String name = request.getParameter("name");
        String imageUrl = request.getParameter("image_url");
        String steps = request.getParameter("steps");
        String description = request.getParameter("description");

        // Instancia o DAO para interagir com o banco de dados e o modelo Recipe para armazenar os dados
        RecipeDAO recipeDAO = new RecipeDAO();
        Recipe recipe = new Recipe(name, steps, imageUrl, description);

        // Insere a nova receita no banco de dados e armazena o resultado
        int result = recipeDAO.insert(recipe);

        // Se a inserção for bem-sucedida, redireciona para "pageRecipe.jsp"
        if (result >= 1) {
            response.sendRedirect("pages/pageRecipe.jsp");
        } else {
            // Se ocorrer erro, responde com um JSON indicando o status de erro
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }
}
