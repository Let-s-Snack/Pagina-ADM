package controller;

// Importações necessárias para o funcionamento do servlet e manipulação de requisições HTTP
import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Adm;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

// Mapeamento do servlet para a URL "/insert-adm"
@WebServlet("/insert-adm")
public class ServletAdmInsert extends HttpServlet {

    // Sobrescreve o método doPost para tratar requisições HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém os parâmetros de "name", "email" e "password" do formulário enviado
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Instancia o DAO para manipulação de dados e um objeto Adm com os dados recebidos
        AdmDAO admDAO = new AdmDAO();
        Adm adm = new Adm(email, password, name);

        // Insere o objeto Adm no banco de dados e armazena o resultado da operação
        int result = admDAO.insert(adm);

        // Se a inserção for bem-sucedida, redireciona para a página "pageAdm.jsp"
        if (result >= 1) {
            response.sendRedirect("pages/pageAdm.jsp");
        } else {
            // Se ocorrer erro, envia uma resposta JSON com status de erro
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }
}
