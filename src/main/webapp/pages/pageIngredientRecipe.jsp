<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.IngredientRecipeDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ingredientes da Receita</title>
    <link rel="stylesheet" href="../css/pageAdm.css">
    <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
</head>
<body>
<header>
    <a href="pageRecipe.jsp" class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </a>
</header>
<div class="container">
    <h1>Ingredientes da Receita</h1>
    <table class="table">
        <thead>
        <tr>
            <th>INGREDIENTE</th>
            <th>RECEITA</th>
            <th>MEDIDA</th>
            <th>QUANTIDADE</th>
            <th>AÇÃO</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Obtém o recipe_id da URL
            int recipeId = Integer.parseInt(request.getParameter("recipe_id"));

            // Instancia o DAO e busca os ingredientes da receita pelo ID
            IngredientRecipeDAO ingredientRecipeDAO = new IngredientRecipeDAO();
            ResultSet rs = ingredientRecipeDAO.selectAll(recipeId);

            try {
                // Itera sobre o ResultSet e exibe os dados na tabela
                while (rs != null && rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("ingredient_name") %></td>
            <td><%= rs.getString("recipe_name") %></td>
            <td><%= rs.getString("measure") %></td>
            <td><%= rs.getInt("quantity") %></td>
            <td>
                <a href="pageIngredientRecipeUpdate.jsp?id=<%= rs.getInt("id") %>">
                    <img src="../assets/edit.png" alt="Editar" class="edit">
                </a>
            </td>
        </tr>
        <%
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        %>
        </tbody>
    </table>

    <div class="buttons">
        <a href="pageRecipe.jsp"><button class="btn-back">Voltar</button></a>
        <a href="pageIngredientRecipeInsert.jsp"><button class="btn-add">Adicionar</button></a>
    </div>
</div>

</body>
</html>
