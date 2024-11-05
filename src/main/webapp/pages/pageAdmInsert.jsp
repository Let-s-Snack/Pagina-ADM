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
    <title>Adicionar - Adm</title>
</head>

<body>
<header>
    <div class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </div>
</header>
<h1>ADICIONAR ADMINISTRADOR</h1>
<h2 class="" id="h2"></h2>
<div class="container">

    <form action="${pageContext.request.contextPath}/insert-adm" method="post">

        <label for="name">NOME</label>
        <input type="text" id="name" name="name" placeholder="Digite o nome">


        <label for="email">E-MAIL</label>
        <input id="email" type="text" name="email" placeholder="Digite o e-mail">

        <label for="password">SENHA</label>
        <input id="password" type="text" name="password" placeholder="Digite a senha">
        <div class="buttons">
            <a href="pageAdm.jsp"><button type="button" class="button">Voltar</button></a>
            <button type="submit" class="button">Confirmar</button>
        </div>
    </form>

</div>
</body>
</html>
