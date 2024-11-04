function confirmIngredient() {
    const ingredient = {
        id: parseInt(document.getElementById("id").value),
        name: document.getElementById("name").value,
        description: document.getElementById("description").value
    };

    fetch('/Projeto_war_exploded/update-ingredient', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(ingredient)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro na resposta do servidor: " + response.status);
            }
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                return response.json();
            } else {
                throw new Error("Resposta não está no formato JSON");
            }
        })
        .then(data => {
            changeText(data.status);
        })
        .catch(error => console.error("Erro ao enviar o ingrediente:", error));
}
function deleteIngredient() {
    const ingredient = parseInt(document.getElementById("id").value); // Obtém o ID

    // Verifica se o ID é válido
    if (isNaN(ingredient)) {
        console.error("ID do ingrediente não é válido.");
        return; // Interrompe se o ID não for válido
    }

    // Faz a requisição para excluir o ingrediente
    fetch(`/Projeto_war_exploded/delete-ingredient?id=${ingredient}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro na resposta do servidor: " + response.status);
            }
            return response.json(); // Espera resposta como JSON
        })
        .then(data => {
            changeText(data.status); // Atualiza a UI com o status da operação
        })
        .catch(error => console.error("Erro ao excluir o ingrediente:", error));
}


function changeText(status) {
    const h2 = document.getElementById("h2");

    // Define as classes com base no status
    if (status !== "Dados Alterados" && status!== "Ingrediente excluído com sucesso") {
        h2.classList.remove("success");
        h2.classList.add("error");
    } else {
        h2.classList.remove("error");
        h2.classList.add("success");
    }
    // Atualiza o texto do h2 com o status
    h2.textContent = status;
}