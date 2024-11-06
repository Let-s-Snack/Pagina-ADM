package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Restriction;

public class RestrictionDAO { // Classe RestrictionDAO

    // Declaração dos Atributos
    private PreparedStatement pstmt;
    private ResultSet rs;

    // Método que busca todos os registros da tabela Restriction
    public ResultSet selectAll() {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction WHERE is_deleted='false' ORDER BY type");
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();

        }
    }

    // Método que encontra Restriction pelo tipo
    public ResultSet selectByType(Restriction restriction) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction WHERE type = ?");
            pstmt.setString(1, restriction.getType());
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();

        }
    }

    // Método que insere registros na tabela Restriction
    public int insert(Restriction restriction) {
        ConnectionDB connectionDB = new ConnectionDB();
        int n = 0;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("INSERT INTO restriction (type, image_url, description) VALUES (?, ?, ?)");
            pstmt.setString(1, restriction.getType());
            pstmt.setString(2, restriction.getImageURL());
            pstmt.setString(3, restriction.getDescription());
            if (validateUrl(restriction.getImageURL())) {
                n = pstmt.executeUpdate();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();

        }
        return n;
    }

    // Método que atualiza os dados da tabela Restriction pelo id
    public int update(Restriction restriction) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction WHERE id = ?");
            pstmt.setInt(1, restriction.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pstmt.close();
                pstmt = conn.prepareStatement("UPDATE restriction SET type = ?, image_url = ?, description = ? WHERE id = ?");
                pstmt.setString(1, restriction.getType());
                pstmt.setString(2, restriction.getImageURL());
                pstmt.setString(3, restriction.getDescription());
                pstmt.setInt(4, restriction.getId());
                if (validateUrl(restriction.getImageURL())) {
                    n = pstmt.executeUpdate();
                }
            } else {
                n = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();

        }
        return n;
    }

    // Método que remove os dados da tabela Restriction pelo id
    public int remove(Restriction restriction) {
        ConnectionDB connectionDB = new ConnectionDB();
        int result = 0;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction WHERE id=?");
            pstmt.setInt(1, restriction.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pstmt.close();
                pstmt = conn.prepareStatement("UPDATE restriction SET is_deleted = 'true' WHERE id=?");
                pstmt.setInt(1, restriction.getId());
                result = pstmt.executeUpdate();
            } else {
                result = 0;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            result = -1;
        } finally {
            connectionDB.disconnect();
        }
        return result;
    }

    // Método para validar a URL
    public static boolean validateUrl(String url) {
        String regex = "https://firebasestorage\\.googleapis\\..*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
} // Fim da classe RestrictionDAO