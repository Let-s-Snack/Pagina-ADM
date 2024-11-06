<%@ page import="model.Ingredient" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.IngredientDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.IngredientRecipe" %>
<%@ page import="dao.IngredientRecipeDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="../css/pageRecipeUpdate.css">
  <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
  <script src="../js/pageIngredientRecipeUpdate.js"></script>
  <title>Adicionar - Ingrediente da Receita</title>
</head>

<body>
<header>
  <div class="box_logo">
    <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
    <p>Let's Snack</p>
  </div>
</header>
<h1>ADICIONAR INGREDIENTE</h1>
<h2 class="" id="h2"></h2>
<div class="container">
  <%
    // Obtém o recipe_id da URL
    String recipeId="";
    recipeId = request.getParameter("recipe_id");
    request.getSession().setAttribute("recipe_id", recipeId);

  %>
  <form action="${pageContext.request.contextPath}/insert-ingredientRecipe" method="post">
    <input type="hidden" name="recipe_id" value="<%= recipeId %>">

    <label for="ingredient">INGREDIENTE</label>
    <input type="text" id="ingredient" name="ingredient" placeholder="Digite o nome do ingrediente">

    <label for="recipe">RECEITA</label>
    <input id="recipe" type="text" name="recipe" placeholder="Digite o nome da receita">

    <label for="measure">MEDIDA</label>
    <input id="measure" type="text" name="measure" placeholder="Digite a medida do ingrediente">

    <label for="quantity">QUANTIDADE</label>
    <input id="quantity" type="text" name="quantity" placeholder="Digite a quantidade do ingrediente">

    <div class="buttons">
      <button type="button" class="button" onclick="window.history.back()">Voltar</button>
      <button type="submit" class="button">Confirmar</button>
    </div>
  </form>


</div>
</body>
</html>
