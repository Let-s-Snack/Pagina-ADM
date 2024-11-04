package dao;

import model.Adm;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class AdmDAO { // Classe AdmDAO

    // Declaração dos Atributos
    private PreparedStatement pstmt; // Prepara instruções SQL para execução
    private ResultSet rs; // Armazena os resultados de consultas SQL

    // Método que seleciona todos os registros da tabela ADM
    public ResultSet selectAll() {

        // Utilizando a classe ConnectionDB para acessar os métodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect(); // Conexão com o banco de dados
            pstmt = conn.prepareStatement("SELECT * FROM admin where is_deleted='false' order by name");
            return pstmt.executeQuery(); // Retorna os registros da tabela
        } catch (SQLException sqle) {
            sqle.printStackTrace(); // Exibe erros de SQL
            return null;
        } finally {
            connectionDB.disconnect(); // Desconecta do banco de dados
        }
    }

    // Método que busca registros pelo email do administrador
    public ResultSet searchForEmail(Adm adm) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM Admin WHERE email = ?");
            pstmt.setString(1, adm.getEmail()); // Define o email na consulta
            rs = pstmt.executeQuery();
            return rs; // Retorna o resultado da consulta
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

    // Método que valida o login de administrador pelo email e senha
    public int selectByLogin(String email, String password) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            String sql = "SELECT * FROM admin WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String passHash = rs.getString("password"); // Obtém o hash da senha
                if (password.equals(passHash)) { // Comparação direta de senha (não recomendada)
                    return 1; // Login bem-sucedido
                }
                else {
                    return 0; // Senha incorreta
                }
            } else {
                return 0; // Email não encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Erro durante a execução
        } finally {
            connectionDB.disconnect();
        }
    }

    // Método que insere um novo registro de administrador
    public int insert(Adm adm) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("insert into admin (email, password, name) values (?,?,?)");
            pstmt.setString(1, adm.getEmail()); // Define o email
            String passHash = hashPassword(adm.getPassword()); // Gera o hash da senha
            pstmt.setString(2, passHash); // Define o hash da senha
            pstmt.setString(3, adm.getName()); // Define o nome
            if (pstmt.executeUpdate() > 0) {
                n = 1; // Inserção bem-sucedida
            } else {
                n = -1; // Falha na inserção
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

    // Método que atualiza um registro de administrador
    public int update(Adm adm) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("UPDATE admin SET email=?, password=?, name=? WHERE email=?");
            pstmt.setString(1, adm.getEmail());
            pstmt.setString(2, adm.getPassword());
            pstmt.setString(3, adm.getName());
            pstmt.setString(4, adm.getEmail());
            pstmt.executeUpdate();

            pstmt.close(); // Fecha o pstmt para a próxima operação
            // Atualiza a coluna is_updated para 'true' para sinalizar que a operação foi concluída
            pstmt = conn.prepareStatement("update admin set is_updated='true' where email=?");
            pstmt.setString(1, adm.getEmail());
            if (pstmt.executeUpdate() > 0) {
                n = 1; // Atualização bem sucedida
            } else {
                n = -1; // Falha na atualização
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

    // Método que marca um registro de administrador como excluído (soft delete)
    public int remove(Adm adm) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("UPDATE admin SET is_deleted=true WHERE email=?");
            pstmt.setString(1, adm.getEmail());
            if (pstmt.executeUpdate() > 0) {
                n = 1; // Remoção bem-sucedida
            } else {
                n = -1; // Falha na remoção
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

    // Método que gera o hash da senha usando BCrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Método que verifica a senha fornecida com o hash armazenado
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
} // Fim da classe AdmDAO
