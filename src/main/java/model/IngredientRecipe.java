//package model;
//import dao.AdmDao;
//import dao.IngredientRecipeDao;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.InputMismatchException;
//
//@WebServlet(name = "administrador", value = "/login-adm")
//public class IngredientRecipe extends HttpServlet {
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        IngredientRecipeDao ingredientRecipeDao= new IngredientRecipeDao();
//        try {
//            String email = req.getParameter("email");
//            String password = req.getParameter("password");
//
//            req.setAttribute("email", email);
//            req.setAttribute("password", password);
////comentar
//            if (admdao.searchForEmail(email)!=null && admdao.searchForPassword(password)!=null){
//                req.getRequestDispatcher("indexAdm.html").forward(req, resp);
//            }else{
//                req.getRequestDispatcher("errorPageEmail.jsp").forward(req,resp);
//            }
//
//        }catch (Exception d){
//            req.setAttribute("erro",d.getMessage());
//            req.getRequestDispatcher("errorPage.jsp").forward(req,resp);
//        }
//    }
//}
