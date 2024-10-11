package controller;
import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "administrador", value = "/login-adm")
public class Adm extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdmDAO admdao= new AdmDAO();
        ResultSet rsEmail = null;
        ResultSet rsPassword = null;

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String passReturn="";

            rsEmail = admdao.searchForEmail(email);
            if (rsEmail != null && rsEmail.next()) {

                while (rsEmail.next()){
                    passReturn=rsEmail.getString("password");

                }
                if (passReturn.equals(password)){
                req.getRequestDispatcher("indexAdm.html").forward(req, resp);
                }else {
                    req.getRequestDispatcher("/errorPageEmail.html").forward(req, resp);
                }

            } else {
                req.getRequestDispatcher("/errorPageEmail.html").forward(req, resp);
            }

        } catch (Exception d) {
            req.setAttribute("erro", d.getMessage());
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
        } finally {
            try {
                if (rsEmail != null) rsEmail.close();
                if (rsPassword != null) rsPassword.close();
                admdao.desconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
