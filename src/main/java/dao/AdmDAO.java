package dao;

import model.Adm;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class AdmDAO { // Classe AdmDAO

    // Método que seleciona todos os registros da tabela ADM
    public ResultSet selectAll() {
        ConnectionDB connectionDB = new ConnectionDB();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM admin WHERE is_deleted='false'");
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();

        }
    }

    // Método para buscar um administrador pelo e-mail
    public ResultSet searchForEmail(Adm adm) {
        ConnectionDB connectionDB = new ConnectionDB();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM admin WHERE email = ?");
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
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM admin WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String passHash = rs.getString("password");
                return checkPassword(password, passHash) ? 1 : 0;
            }
            return 0; // Email não encontrado
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Erro no banco de dados
        } finally {
            connectionDB.disconnect();

        }
    }

    // Método para inserir um novo administrador
    public int insert(Adm adm) {
        ConnectionDB connectionDB = new ConnectionDB();
        PreparedStatement pstmt = null;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("INSERT INTO admin (email, password, name) VALUES (?, ?, ?)");
            pstmt.setString(1, adm.getEmail());
            pstmt.setString(2, hashPassword(adm.getPassword()));
            pstmt.setString(3, adm.getName());
            return pstmt.executeUpdate() > 0 ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();

        }
    }

    // Método para atualizar informações de um administrador
    public int update(Adm adm) {
        ConnectionDB connectionDB = new ConnectionDB();
        PreparedStatement pstmt = null;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("UPDATE admin SET email = ?, password = ?, name = ? WHERE email = ?");
            pstmt.setString(1, adm.getEmail());
            pstmt.setString(2, hashPassword(adm.getPassword()));
            pstmt.setString(3, adm.getName());
            pstmt.setString(4, adm.getEmail());
            return pstmt.executeUpdate() > 0 ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();

        }
    }

    // Método para marcar um administrador como excluído
    public int remove(Adm adm) {
        ConnectionDB connectionDB = new ConnectionDB();
        PreparedStatement pstmt = null;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("UPDATE admin SET is_deleted = 'true' WHERE email = ?");
            pstmt.setString(1, adm.getEmail());
            return pstmt.executeUpdate() > 0 ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();

        }
    }

    // Métodos utilitários para hash e verificação de senha usando BCrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

} // Fim da classe AdmDAO
