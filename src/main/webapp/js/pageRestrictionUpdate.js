function confirmRestriction() {
    const restriction = {
        type: document.getElementById("type").value,
        imageURL: document.getElementById("image_url").value,
        description: document.getElementById("description").value,
        id: parseInt(document.getElementById("id").value)
    };
    console.log(restriction)
    fetch('/Projeto_war_exploded/update-restriction', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(restriction)
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
        .catch(error => console.error("Erro ao enviar a restriçãp:", error));
}
function deleteRestriction() {
    const restriction = parseInt(document.getElementById("id").value); // Obtém o ID

    // Verifica se o ID é válido
    if (isNaN(restriction)) {
        console.error("ID da restrição não é válido.");
        return; // Interrompe se o ID não for válido
    }

    // Faz a requisição para excluir a restrição
    fetch(`/Projeto_war_exploded/delete-restriction?id=${restriction}`, {
        method: 'POST'
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
        .catch(error => console.error("Erro ao excluir a restrição:", error));
}


function changeText(status) {
    const h2 = document.getElementById("h2");

    // Define as classes com base no status
    if (status !== "Dados Alterados" && status !== "Restrição excluída com sucesso") {
        h2.classList.remove("success");
        h2.classList.add("error");
    } else {
        h2.classList.remove("error");
        h2.classList.add("success");
    }
    // Atualiza o texto do h2 com o status
    h2.textContent = status;
}