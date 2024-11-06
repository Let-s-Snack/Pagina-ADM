package dao;

import model.Adm;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class AdmDAO { // Classe AdmDAO

    // Declaração dos atributos
    private PreparedStatement pstmt;
    private ResultSet rs;

    // Método que seleciona todos os registros da tabela ADM
    public ResultSet selectAll() {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            // Conecta ao banco de dados e executa a consulta
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM admin where is_deleted='false'");
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            // Fecha a conexão
            connectionDB.disconnect();
        }
    }

    // Método para buscar um administrador pelo e-mail
    public ResultSet searchForEmail(Adm adm) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM Admin WHERE email = ?");
            pstmt.setString(1, adm.getEmail());
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

    // Método para verificar o login com hash de senha
    public int selectByLogin(String email, String password) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            String sql = "SELECT * FROM admin WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Verifica o hash da senha usando BCrypt
                String passHash = rs.getString("password");
                if (checkPassword(password, passHash)) {
                    return 1; // Sucesso
                } else {
                    return 0; // Senha incorreta
                }
            } else {
                return 0; // Email não encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Erro no banco de dados
        } finally {
            connectionDB.disconnect();
        }
    }

    // Método para inserir um novo administrador
    public int insert(Adm adm) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("INSERT INTO admin (email, password, name) VALUES (?, ?, ?)");
            pstmt.setString(1, adm.getEmail());
            String passHash = hashPassword(adm.getPassword()); // Hash da senha
            pstmt.setString(2, passHash);
            pstmt.setString(3, adm.getName());
            n = pstmt.executeUpdate() > 0 ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

    // Método para atualizar informações de um administrador
    public int update(Adm adm) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("UPDATE admin SET email = ?, password = ?, name = ? WHERE email = ?");
            pstmt.setString(1, adm.getEmail());
            String passHash = hashPassword(adm.getPassword());
            pstmt.setString(2, passHash);
            pstmt.setString(3, adm.getName());
            pstmt.setString(4, adm.getEmail());
            n = pstmt.executeUpdate() > 0 ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

    // Método para marcar um administrador como excluído
    public int remove(Adm adm) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("UPDATE admin SET is_deleted = 'true' WHERE email = ?");
            pstmt.setString(1, adm.getEmail());
            n = pstmt.executeUpdate() > 0 ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

    // Métodos utilitários para hash e verificação de senha usando BCrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

} // Fim da classe AdmDAO
