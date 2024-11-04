<%@ page import="model.Restriction" %>
<%@ page import="dao.RestrictionDAO" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: beatrizmarioti-ieg
  Date: 30/10/2024
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/pageRestrinctionUpdate.css">
    <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
    <script src="../js/pageRestrictionUpdate.js"></script>
    <title>Alterar - Restrição</title></head>
<body>
<header>
    <div class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </div>
</header>
<h1>ALTERAR RESTRIÇÃO</h1>
<h2 class="" id="h2"></h2>
<div class="container">
    <%
        RestrictionDAO restrictionDAO = new RestrictionDAO();
        String typeURL = request.getParameter("type");
        ResultSet rs = restrictionDAO.selectByType(new Restriction(typeURL));
        if (rs.next()) { // Move o cursor para a primeira linha
    %>
    <form action="update-restriction" method="post" >
        <div class="idRestriction">
            <h3>ID:</h3>
            <h4 class="idDisplay"><%= rs.getString("id") %></h4>
            <input type="hidden" id="id" name="id" value="<%= rs.getString("id") %>">
        </div>

        <label for="type">TIPO</label>
        <input type="text" id="type" name="type" value="<%=request.getParameter("type")%>">

        <label for="image_url">IMAGEM URL</label>
        <input id="image_url" type="text" name="image_url" value="<%= rs.getString("image_url") %>">

        <label for="description">DESCRIÇÃO</label>
        <input id="description" type="text" name="description" value="<%= rs.getString("description") %>">

        <div class="buttons">
            <a href="pageRestriction.jsp"><button type="button" class="button">Voltar</button></a>
            <button type="button" class="button" onclick="confirmRestriction()">Confirmar</button>
            <a href="pageRestriction.jsp"><button type="button" class="button" onclick="deleteRestriction()" >Excluir</button></a>
        </div>
    </form>
    <%
        }
    %>

</div>
</body>
</html>
