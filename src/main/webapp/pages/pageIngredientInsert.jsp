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
    <script src="../js/pageIngredientUpdate.js"></script>
    <title>Adicionar - Ingrediente</title>
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

    <form action="${pageContext.request.contextPath}/insert-ingredient" method="post">

        <label for="name">NOME</label>
        <input type="text" id="name" name="name" placeholder="Digite o nome do ingrediente">

        <label for="description">DESCRIÇÃO</label>
        <input id="description" type="text" name="description" placeholder="Digite a descrição do ingrediente">

        <div class="buttons">
            <a href="pageIngredient.jsp"><button type="button" class="button">Voltar</button></a>
            <button type="submit" class="button">Confirmar</button>
        </div>
    </form>

</div>
</body>
</html>
