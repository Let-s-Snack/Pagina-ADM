package model;
import dao.AdmDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

@WebServlet(name = "administrador", value = "/login-adm")
public class Adm extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdmDao admdao= new AdmDao();
        ResultSet rsEmail = null;
        ResultSet rsPassword = null;

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            rsEmail = admdao.searchForEmail(email);
            rsPassword = admdao.searchForPassword(password);

            if (rsEmail != null && rsEmail.next() && rsPassword != null && rsPassword.next()) {
                req.getRequestDispatcher("indexAdm.html").forward(req, resp);
            } else {
                req.getRequestDispatcher("errorPageEmail.jsp").forward(req, resp);
            }

        } catch (Exception d) {
            req.setAttribute("erro", d.getMessage());
            req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
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
