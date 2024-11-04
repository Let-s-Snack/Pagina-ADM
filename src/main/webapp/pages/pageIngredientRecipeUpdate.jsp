<%@ page import="model.Ingredient" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.IngredientDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.IngredientRecipe" %>
<%@ page import="dao.IngredientRecipeDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/pageIngredientUpdate.css">
    <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
    <script src="../js/pageIngredientRecipeUpdate.js"></script>
    <title>Alterar - Ingrediente</title>
</head>

<body>
<header>
    <div class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </div>
</header>
<h1>ALTERAR INGREDIENTE</h1>
<h2 class="" id="h2"></h2>
<div class="container">
    <%
        IngredientRecipeDAO ingredientRecipeDAO = new IngredientRecipeDAO();
        String idUrl = request.getParameter("id");
        System.out.println("Id url: "+idUrl);
        ResultSet rs = ingredientRecipeDAO.selectById(Integer.parseInt(idUrl) );
        if (rs.next()) { // Move o cursor para a primeira linha
    %>
    <form action="update-ingredientRecipe" method="post" >
        <div class="idIngredient">
            <h3>ID:</h3>
            <h4 class="idDisplay"><%= rs.getString("id") %></h4>
            <input type="hidden" id="id" name="id" value="<%= rs.getString("id") %>">
        </div>

        <label for="measure">MEDIDA</label>
        <input type="text" id="measure" name="measure" value="<%=rs.getString("measure")%>">

        <label for="quantity">QUANTIDADE</label>
        <input id="quantity" type="text" name="quantity" value="<%= rs.getString("quantity") %>">

        <div class="buttons">
            <a href="pageIngredientRecipe.jsp?recipe_id=<%= rs.getString("recipe_id") %>">
                <button type="button" class="button">Voltar</button></a>
            <button type="button" class="button" onclick="confirmIngredientRecipe()">Confirmar</button>
            <a href="pageIngredientRecipe.jsp"><button type="button" class="button" onclick="deleteIngredientRecipe()" >Excluir</button></a>
        </div>
    </form>
    <%
        }
    %>

</div>
</body>
</html>
