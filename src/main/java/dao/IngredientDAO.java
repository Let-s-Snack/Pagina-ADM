package dao;
import model.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientDAO { // Classe responsável pelas operações de banco de dados para a tabela Ingredient

    // Declaração dos atributos de PreparedStatement e ResultSet
    private PreparedStatement pstmt;
    private ResultSet rs;

    // Métodos de seleção (Select)

    // Método que seleciona todos os registros da tabela Ingredient
    public ResultSet selectAll() {
        // Instancia a classe ConnectionDB para conectar e desconectar do banco
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect(); // Estabelece a conexão com o banco de dados
            // Prepara a consulta SQL para obter ingredientes que não estão excluídos (is_deleted = 'false')
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE is_deleted='false' ORDER BY name");
            return pstmt.executeQuery(); // Executa a consulta e retorna o resultado
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect(); // Desconecta do banco de dados ao final da execução
        }
    }

    // Método que seleciona um ingrediente pelo nome
    public ResultSet selectByName(Ingredient ingredient) {
        ConnectionDB connectionDB = new ConnectionDB(); // Instancia a classe para conectar e desconectar
        try {
            Connection conn = connectionDB.connect();
            // Prepara a consulta SQL para buscar um ingrediente pelo nome
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE name = ?");
            pstmt.setString(1, ingredient.getName()); // Define o parâmetro "nome" da consulta
            return pstmt.executeQuery(); // Executa a consulta e retorna o resultado
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

    // Método que insere um novo ingrediente na tabela Ingredient
    public int insert(Ingredient ingredient) {
        ConnectionDB connectionDB = new ConnectionDB();
        int n = 0; // Variável para armazenar o resultado da operação (sucesso ou falha)
        try {
            Connection conn = connectionDB.connect();
            // Prepara a consulta SQL para verificar se o ID já existe
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE id = ?");
            pstmt.setInt(1, ingredient.getId());
            rs = pstmt.executeQuery();
            if (!rs.next()) { // Verifica se não há um ingrediente com o mesmo ID
                // Prepara a instrução para inserir um novo registro de ingrediente
                pstmt = conn.prepareStatement("INSERT INTO ingredient (name, description) VALUES (?, ?)");
                pstmt.setString(1, ingredient.getName()); // Define o nome do ingrediente
                pstmt.setString(2, ingredient.getDescription()); // Define a descrição do ingrediente
                if (pstmt.executeUpdate() > 0) { // Executa a inserção
                    n = 1; // Retorna 1 para inserção bem-sucedida
                    return n;
                } else {
                    n = -1; // Retorna -1 para falha na inserção
                    return n;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
            return n;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

    // Método que marca um ingrediente como excluído (soft delete)
    public int remove(Ingredient ingredient) {
        ConnectionDB connectionDB = new ConnectionDB(); // Instancia a classe para conectar e desconectar
        try {
            Connection conn = connectionDB.connect();
            // Prepara a consulta para verificar se o ingrediente com o ID especificado existe
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE id = ?");
            pstmt.setInt(1, ingredient.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Verifica se o ingrediente foi encontrado
                // Prepara a instrução SQL para marcar o ingrediente como excluído
                pstmt = conn.prepareStatement("UPDATE ingredient SET is_deleted = 'true' WHERE id = ?");
                pstmt.setInt(1, ingredient.getId()); // Define o ID do ingrediente a ser excluído
                return pstmt.executeUpdate(); // Executa a atualização e retorna o número de registros afetados
            } else {
                return 0; // Retorna 0 caso o ingrediente não seja encontrado
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return -1; // Retorna -1 em caso de erro na execução
        } finally {
            connectionDB.disconnect();
        }
    }

    // Método que atualiza os dados de um ingrediente
    public int update(Ingredient ingredient) {
        ConnectionDB connectionDB = new ConnectionDB(); // Instancia a classe para conectar e desconectar
        try {
            Connection conn = connectionDB.connect();
            // Prepara a consulta para verificar se o ingrediente existe pelo ID
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE id = ?");
            pstmt.setInt(1, ingredient.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) { // Verifica se o ingrediente existe
                pstmt.close(); // Fecha o pstmt para a próxima operação
                // Prepara a instrução SQL para atualizar nome e descrição do ingrediente
                pstmt = conn.prepareStatement("UPDATE ingredient SET name = ?, description = ? WHERE id = ?");
                pstmt.setString(1, ingredient.getName()); // Define o novo nome
                pstmt.setString(2, ingredient.getDescription()); // Define a nova descrição
                pstmt.setInt(3, ingredient.getId()); // Define o ID do ingrediente a ser atualizado
                pstmt.executeUpdate(); // Executa a atualização

                // Atualiza a coluna is_updated para 'true' na tabela de relacionamento com receitas
                pstmt.close(); // Fecha o pstmt para a próxima operação
                pstmt = conn.prepareStatement("UPDATE ingredient SET is_updated = 'true' FROM ingredient_recipe WHERE ingredient.id = ingredient_recipe.ingredient_id AND ingredient_recipe.id = ?");
                pstmt.setInt(1, ingredient.getId()); // Define o ID do ingrediente relacionado
                return pstmt.executeUpdate(); // Executa a atualização e retorna o número de registros afetados
            } else {
                return 0; // Retorna 0 caso o ingrediente não seja encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna -1 em caso de erro na execução
        } finally {
            connectionDB.disconnect(); // Desconecta do banco de dados ao final da execução
        }
    }
} // Fim da classe IngredientDAO
