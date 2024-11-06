<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.AdmDAO" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: beatrizmarioti-ieg
  Date: 29/10/2024
  Time: 22:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administradores</title>
    <link rel="stylesheet" href="../css/pageAdm.css">
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
            <th>E-MAIL</th>
            <th>SENHA</th>
            <th>AÇÃO</th>
        </tr>
        </thead>
        <tbody>
        <%
            AdmDAO admDAO=new AdmDAO();
            ResultSet rs = admDAO.selectAll();
            try{
                while (rs.next()){
        %>
        <tr>
            <td><%=rs.getString("name")%></td>
            <td><%=rs.getString("email")%></td>
            <td><%=rs.getString("password")%></td>
            <td>
                <a href="pageAdmUpdate.jsp?email=<%= rs.getString("email") %>">
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
        <a href="pageAdmInsert.jsp"><button class="btn-add">Adicionar</button></a>
    </div>
</div>

</body>
</html>
