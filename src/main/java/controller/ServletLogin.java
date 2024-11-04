package controller;

import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "administrador", value = "/login-adm")
public class ServletLogin extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdmDAO admdao = new AdmDAO();

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            int loginResult = admdao.selectByLogin(email, password);

            if (loginResult == 1) {
                req.getRequestDispatcher("indexAdm.html").forward(req, resp);
            } else if (loginResult == 0) {
                req.setAttribute("errorMessage", "Email ou senha incorretos.");
                req.getRequestDispatcher("indexError.html").forward(req, resp);
            } else {
                req.setAttribute("errorMessage", "Erro no sistema. Tente novamente mais tarde.");
                req.getRequestDispatcher("indexError.html").forward(req, resp);
            }

        } catch (Exception e) {
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.getRequestDispatcher("indexError.html").forward(req, resp);
        }
    }
}
