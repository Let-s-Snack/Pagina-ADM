package controller;

import dao.AdmDAO;
import dao.ConnectionDB;
import dao.IngredientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ingredient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ingredient", value = "/update-ingredient")
public class ServletIngredient extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        IngredientDAO ingredientDAO = new IngredientDAO();

        try {
            if ("update".equals(action)) {
                Ingredient ingredient = new Ingredient(name, description);
                ingredientDAO.update(ingredient);
                response.sendRedirect("pageIngredient.jsp?message=Ingredient updated successfully");
            } else if ("delete".equals(action)) {
                Ingredient ingredient = new Ingredient(name);
                ingredientDAO.remove(ingredient);
                response.sendRedirect("pageIngredient.jsp?message=Ingredient deleted successfully");
            } else {
                response.sendRedirect("pageIngredient.jsp?error=Invalid action");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pageIngredient.jsp?error=Operation failed");
        }
    }
}
