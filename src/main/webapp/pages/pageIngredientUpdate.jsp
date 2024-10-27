<%@ page import="model.Ingredient" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.IngredientDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/pageIngredientUpdate.css">
    <link rel="shortcut icon" href="../assets/logo.png" type="image/x-icon" />
    <title>Alterar - Ingrediente</title>
</head>
<script>
    function submitForm(action) {
        const form = document.getElementById("ingredientForm");
        const inputAction = document.createElement("input");
        inputAction.type = "hidden";
        inputAction.name = "action";
        inputAction.value = action; // Set the action value (update or delete)
        form.appendChild(inputAction); // Add the hidden input to the form
        form.submit(); // Submit the form
    }
</script>
<body>
<header>
    <div class="box_logo">
        <img class="logoApp" src="../assets/logo.png" alt="Logo do App" />
        <p>Let's Snack</p>
    </div>
</header>
<h1>ALTERAR INGREDIENTE</h1>

<div class="container">
    <%
        IngredientDAO ingredientDAO = new IngredientDAO();
        String nameFromUrl = request.getParameter("name");
        ResultSet rs = ingredientDAO.selectByName(nameFromUrl);
        if (rs.next()) { // Move o cursor para a primeira linha
    %>
    <form action="update-ingredient" method="post" >
        <label for="name">NOME</label>
        <input type="text" id="name" name="name" value="<%=request.getParameter("name")%>">

        <label for="description">DESCRIÇÃO</label>
        <input id="description" type="text" name="description" value="<%= rs.getString("description") %>">

        <div class="buttons">
            <a href="pageIngredient.jsp"><button type="button" class="button">Voltar</button></a>
            <button type="button" class="button" onclick="submitForm('update')">Confirmar</button>
            <button type="button" class="button" onclick="submitForm('delete')">Excluir</button>

        </div>
    </form>
    <%
        }
    %>

</div>
</body>
</html>
