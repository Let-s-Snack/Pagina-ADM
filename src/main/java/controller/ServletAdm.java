package controller;
import dao.AdmDAO;
import dao.ConnectionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "administrador", value = "/login-adm")
public class ServletAdm extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdmDAO admdao= new AdmDAO();
        ResultSet rsEmail = null;
        ResultSet rsPassword = null;
        ConnectionDB connectDB = new ConnectionDB();

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String passReturn="";


            rsEmail = admdao.searchForEmail(email);
            if (rsEmail != null && rsEmail.next()) {
                passReturn = rsEmail.getString("password");

                if (passReturn.equals(password)){
                req.getRequestDispatcher("indexAdm.html").forward(req, resp);
                }else {
                    req.getRequestDispatcher("indexError.html").forward(req, resp);
                }

            } else {
                req.getRequestDispatcher("indexError.html").forward(req, resp);
            }

        } catch (Exception d) {
            req.setAttribute("erro", d.getMessage());
            req.getRequestDispatcher("indexError.html").forward(req, resp);
        } finally {
            try {
                if (rsEmail != null) rsEmail.close();
                if (rsPassword != null) rsPassword.close();
                connectDB.disconnect();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
