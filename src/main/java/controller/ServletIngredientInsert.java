package controller;

// Importações necessárias para o funcionamento do servlet e manipulação de requisições HTTP
import dao.IngredientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ingredient;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

// Mapeamento do servlet para a URL "/insert-ingredient"
@WebServlet("/insert-ingredient")
public class ServletIngredientInsert extends HttpServlet {

    // Sobrescreve o método doPost para tratar requisições HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém os parâmetros "name" e "description" enviados pelo formulário
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Instancia o DAO para manipulação de dados e um objeto Ingredient com os dados recebidos
        IngredientDAO ingredientDAO = new IngredientDAO();
        Ingredient ingredient = new Ingredient(name, description);

        // Insere o objeto Ingredient no banco de dados e armazena o resultado da operação
        int result = ingredientDAO.insert(ingredient);

        // Se a inserção for bem-sucedida, redireciona para a página "pageIngredient.jsp"
        if (result >= 1) {
            response.sendRedirect("pages/pageIngredient.jsp");
        } else {
            // Se ocorrer erro, envia uma resposta JSON com status de erro
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }
}
