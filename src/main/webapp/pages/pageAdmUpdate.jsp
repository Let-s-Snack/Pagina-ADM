<%@ page import="dao.AdmDAO" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="model.Adm" %><%--
  Created by IntelliJ IDEA.
  User: beatrizmarioti-ieg
  Date: 30/10/2024
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/pageAdmUpdate.css">
    <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
    <script src="../js/pageAdmUpdate.js"></script>
    <title>Alterar - Administrador</title></head>
<body>
<header>
    <div class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </div>
</header>
<h1>ALTERAR ADMINISTRADOR</h1>
<h2 class="" id="h2"></h2>
<div class="container">
    <%
        AdmDAO admDAO = new AdmDAO();
        String emailUrl = request.getParameter("email");
        ResultSet rs = admDAO.searchForEmail(new Adm(emailUrl));
        if (rs.next()) { // Move o cursor para a primeira linha
    %>
    <form action="update-adm" method="post" >

        <label for="name">NOME</label>
        <input type="text" id="name" name="name" value="<%=rs.getString("name")%>">


        <label for="email">E-MAIL</label>
        <input id="email" type="text" name="email" value="<%= rs.getString("email") %>">

        <label for="password">SENHA</label>
        <input id="password" type="text" name="password" value="<%= rs.getString("password") %>">

        <div class="buttons">
            <a href="pageAdm.jsp"><button type="button" class="button">Voltar</button></a>
            <button type="button" class="button" onclick="confirmAdm()">Confirmar</button>
            <a href="pageAdm.jsp"><button type="button" class="button" onclick="deleteAdm()" >Excluir</button></a>
        </div>
    </form>
    <%
        }
    %>

</div>
</body>
</html>
