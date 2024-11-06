<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.RecipeDAO" %>
<%@ page import="model.Recipe" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/pageRecipeUpdate.css">
    <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
    <script src="../js/pageRecipeUpdate.js"></script>
    <title>Alterar - Receita</title>
</head>

<body>
<header>
    <div class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </div>
</header>
<h1>ALTERAR RECEITA</h1>
<h2 class="" id="h2"></h2>
<div class="container">
    <%
        RecipeDAO recipeDAO = new RecipeDAO();
        String nameFromUrl = request.getParameter("name");
        ResultSet rs = recipeDAO.selectByName(new Recipe(nameFromUrl));
        if (rs.next()) { // Move o cursor para a primeira linha
    %>
    <form action="update-recipe" method="post" >
        <div class="idRecipe">
            <h3>ID:</h3>
            <h4 class="idDisplay"><%= rs.getString("id") %></h4>
            <input type="hidden" id="id" name="id" value="<%= rs.getString("id") %>">
        </div>

        <label for="name">NOME</label>
        <input type="text" id="name" name="name" value="<%=request.getParameter("name")%>">

        <label for="image_url">IMAGEM URL</label>
        <input id="image_url" type="text" name="image_url" value="<%= rs.getString("image_url") %>">

        <label for="steps">PASSOS</label>
        <input id="steps" type="text" name="steps" value="<%= rs.getString("steps") %>">

        <label for="description">DESCRIÇÃO</label>
        <input id="description" type="text" name="description" value="<%= rs.getString("description") %>">

        <div class="buttons">
            <a href="pageRecipe.jsp"><button type="button" class="button">Voltar</button></a>
            <button type="button" class="button" onclick="confirmRecipe()">Confirmar</button>
            <a href="pageRecipe.jsp"><button type="button" class="button" onclick="deleteRecipe()" >Excluir</button></a>
        </div>
    </form>
    <%
        }
    %>

</div>
</body>
</html>
