<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.RestrictionDAO" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: beatrizmarioti-ieg
  Date: 29/10/2024
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restrições</title>
    <link rel="stylesheet" href="../css/pageAdm.css">
    <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
</head>
<body>
<header>
    <div class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </div>
</header>
<div class="container">
    <h1>CRUD Banco de Dados</h1>
    <table class="table">
        <thead>
        <tr>
            <th>TIPO</th>
            <th>URL IMAGEM</th>
            <th>DESCRIÇÃO</th>
            <th>AÇÃO</th>
        </tr>
        </thead>
        <tbody>
        <%
            RestrictionDAO restrictionDAO=new RestrictionDAO();
            ResultSet rs = restrictionDAO.selectAll();
            try{
                while (rs.next()){
        %>
        <tr>
            <td><%=rs.getString("type")%></td>
            <td><%=rs.getString("image_url")%></td>
            <td><%=rs.getString("description")%></td>
            <td>
                <a href="pageRestrictionUpdate.jsp?type=<%= rs.getString("type") %>">
                    <img src="../assets/edit.png" alt="Editar" class="edit">
                </a>
            </td>
        </tr>
        <%
            }}catch (SQLException se){
        %>
        <h1>ERRO</h1>
        <%}%>
        </tbody>
    </table>

    <div class="buttons">
        <a href="../indexAdm.html"
        ><button class="btn-back">Voltar</button></a>
        <button class="btn-add">Adicionar</button>
    </div>
</div>

</body>
</html>
