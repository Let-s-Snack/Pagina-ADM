//importação de bibliotecas java
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Restriction;

public class RestrictionDAO {
//    Return -1 = caiu no catch
//    Return 0 = caiu no else
    private PreparedStatement pstmt;
    private ResultSet rs;

    // Buscar todos os registros
    public ResultSet selectAll() {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("select * from restriction");
//            Selecionando tudo de restrição
            return pstmt.executeQuery(); // Correto para consulta
//            executando o comando
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

    // Adicionar todos os registros
    public int insert(Restriction restriction) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
//            Como os dados são adiconados com id serial, apenas adicionamos os dados, sem verificar se o id existe
//            Se não possuem dados com esse código, inserimos os valores dados como parâmetro com o id
//            Como is_deleted e is_updated tem default false, não adicionamos eles aqui
            pstmt = conn.prepareStatement("insert into restriction (type, image_url, description) values (?, ?, ?)");
            pstmt.setString(1, restriction.getType());
            pstmt.setString(2, restriction.getImageURL());
            pstmt.setString(3, restriction.getDescription());
            return pstmt.executeUpdate();  // Correto para INSERT
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();
        }
    }
//      Métodos de alterar

    // Alterar o type
    public int update(Restriction restriction) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction WHERE id = ?");
            pstmt.setInt(1, restriction.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE ingredient SET type, image_url, description = ?,?,? WHERE id = ?");
                pstmt.setString(1, restriction.getType());
                pstmt.setString(2, restriction.getImageURL());
                pstmt.setString(3, restriction.getDescription());
                if (pstmt.executeUpdate() > 0) {
                    n = 1;
                } else {
                    n = -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

//    Método de remover

    public int remove(int id) {
        int n = 0;
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("DELETE FROM ingredient WHERE id=?");
            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() > 0) {
                n = 1;
            } else {
                n = -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

}