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

@WebServlet("/insert-ingredient")
public class ServletIngredientInsert extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        IngredientDAO ingredientDAO=new IngredientDAO();
        Ingredient ingredient = new Ingredient(name, description);
        int result= ingredientDAO.insert(ingredient);

        if (result >= 1) {
            response.sendRedirect("pageIngredient.jsp");
        } else {
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }

}