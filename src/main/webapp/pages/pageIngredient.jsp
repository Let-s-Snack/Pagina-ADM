<%@ page import="model.Ingredient" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.IngredientDAO" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: beatrizmarioti-ieg
  Date: 18/10/2024
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ingrediente</title>
    <link rel="stylesheet" href="../css/pageIngredient.css">
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
                <th>DESCRIÇÃO</th>
                <th>AÇÃO</th>
            </tr>
            </thead>
            <tbody>
            <%
                IngredientDAO ingredientDAO=new IngredientDAO();
                ResultSet rs = ingredientDAO.selectAll();
                try{
                    while (rs.next()){
            %>
            <tr>
                <td><%=rs.getString("name")%></td>
                <td><%=rs.getString("description")%></td>
                <td>
                    <a href="pageIngredientUpdate.jsp?name=<%= rs.getString("name") %>">
                        <img src="../assets/edit.png" alt="Editar" class="edit">
                    </a>
                </td>
            </tr>
            <%
                    }
                }catch (SQLException se){
            %>
            <h1>ERRO</h1>x
            <%}%>
            </tbody>
        </table>

        <div class="buttons">
            <a href="../indexAdm.html"><button class="btn-back">Voltar</button></a>
            <a href="pageIngredientInsert.jsp"><button class="btn-add">Adicionar</button></a>
        </div>
    </div>

</body>
</html>
