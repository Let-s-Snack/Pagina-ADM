package controller;

// Importações necessárias para manipulação do servlet e acesso ao DAO e ao modelo Restriction
import dao.RestrictionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import model.Restriction;

import java.io.IOException;

// Mapeamento do servlet para a URL "/insert-restriction"
@WebServlet("/insert-restriction")
public class ServletRestrictionInsert extends HttpServlet {

    // Sobrescreve o método doPost para tratar requisições HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém os parâmetros do formulário enviados para o servlet: tipo, URL da imagem e descrição
        String type = request.getParameter("type");
        String imageUrl = request.getParameter("image_url");
        String description = request.getParameter("description");

        // Instancia o DAO para interagir com o banco de dados e o modelo Restriction para armazenar os dados
        RestrictionDAO restrictionDAO = new RestrictionDAO();
        Restriction restriction = new Restriction(type, imageUrl, description);

        // Insere a nova restrição no banco de dados e armazena o resultado
        int result = restrictionDAO.insert(restriction);

        // Se a inserção for bem-sucedida, redireciona para "pageRestriction.jsp"
        if (result >= 1) {
            response.sendRedirect("pages/pageRestriction.jsp");
        } else {
            // Se ocorrer erro, responde com um JSON indicando o status de erro
            response.getWriter().write("{\"status\":\"Erro\"}");
        }
    }
}
