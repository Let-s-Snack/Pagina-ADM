<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="dao.RecipeDAO" %>
<%@ page import="dao.ConnectionDB" %><%--
  Created by IntelliJ IDEA.
  User: beatrizmarioti-ieg
  Date: 29/10/2024
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Receitas</title>
    <link rel="stylesheet" href="../css/pageRecipe.css">
    <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
</head>
<body>
<header>
    <a href="../indexAdm.html" class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </a>
</header>
<div class="container">
    <h1>CRUD Banco de Dados</h1>
    <table class="table">
        <thead>
        <tr>
            <th>NOME</th>
            <th>URL IMAGEM</th>
            <th>PASSOS</th>
            <th>DESCRIÇÃO</th>
            <th>VER</th>
            <th>AÇÃO</th>
        </tr>
        </thead>
        <tbody>
        <%
            RecipeDAO recipeDAO=new RecipeDAO();
            ResultSet rs = recipeDAO.selectAll();
//            String recipeId = request.getParameter("recipe_id"); // ou o valor que você usa para obtê-lo
//            session.setAttribute("recipe_id", recipeId);
            try{
                while (rs.next()){
        %>
        <tr>
            <td><%=rs.getString("name")%></td>
            <td><%=rs.getString("image_url")%></td>
            <td><%=rs.getString("steps")%></td>
            <td><%=rs.getString("description")%></td>
            <td>
                <a href="pageIngredientRecipe.jsp?recipe_id=<%= rs.getInt("id") %>" class="buttonIngredient" >
                    <p class="show">VER INGREDIENTE</p>
                </a>
            </td>
            <td>
                <a href="pageRecipeUpdate.jsp?name=<%= rs.getString("name") %>">
                    <img src="../assets/edit.png" alt="Editar" class="edit">
                </a>
            </td>
        </tr>
        <%

                }
            }catch (SQLException se){
        %>
        <h1>ERRO</h1>
        <%}
        %>
        </tbody>
    </table>

    <div class="buttons">
        <a href="../indexAdm.html"
        ><button class="btn-back">Voltar</button></a>
        <a href="pageRecipeInsert.jsp"><button class="btn-add">Adicionar</button></a>
    </div>
</div>

</body>
</html>
