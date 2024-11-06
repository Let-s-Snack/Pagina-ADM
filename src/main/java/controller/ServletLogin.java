package controller;

// Importações necessárias para o funcionamento do servlet
import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Mapeamento do servlet para a URL "/login-adm" e nomeando o servlet como "administrador"
@WebServlet(name = "administrador", value = "/login-adm")
public class ServletLogin extends HttpServlet {

    // Método doPost para tratar requisições HTTP POST
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Cria uma instância de AdmDAO para manipulação de dados de administrador
        AdmDAO admdao = new AdmDAO();

        try {
            // Obtém os parâmetros "email" e "password" enviados pelo formulário
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // Chama o método selectByLogin no DAO para verificar o login
            int loginResult = admdao.selectByLogin(email, password);

            // Se o login for bem-sucedido (retorno 1), redireciona para "indexAdm.html"
            if (loginResult == 1) {
                req.getRequestDispatcher("indexAdm.html").forward(req, resp);
            }
            // Se o login falhar (retorno 0), define uma mensagem de erro e redireciona para "indexError.html"
            else if (loginResult == 0) {
                req.setAttribute("errorMessage", "Email ou senha incorretos.");
                req.getRequestDispatcher("indexError.html").forward(req, resp);
            }
            // Se ocorrer algum outro problema no login, define uma mensagem de erro genérica e redireciona para "indexError.html"
            else {
                req.setAttribute("errorMessage", "Erro no sistema. Tente novamente mais tarde.");
                req.getRequestDispatcher("indexError.html").forward(req, resp);
            }

        } catch (Exception e) {
            // Em caso de exceção inesperada, define uma mensagem de erro com o detalhe da exceção e redireciona para "indexError.html"
            req.setAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
            req.getRequestDispatcher("indexError.html").forward(req, resp);
        }
    }
}
