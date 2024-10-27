package dao;
import model.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class IngredientDAO {

    private PreparedStatement pstmt;
    private ResultSet rs;

    public ResultSet selectAll() {
        ConnectionDB connectionDB= new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient");
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

    public ResultSet selectByName(String name) {
        ConnectionDB connectionDB= new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient where name = ?");
            pstmt.setString(1, name);
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

    public ResultSet selectById(int id){
        ConnectionDB connectionDB= new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE id = ? ");
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

    public int insert(int id, String name, String description) {
        ConnectionDB connectionDB= new ConnectionDB();
        int n = 0;
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                String sqlInserirConta = "INSERT INTO ingredient (id, name, description) VALUES (?,?,?)";
                pstmt = conn.prepareStatement(sqlInserirConta);
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, description);
                if (pstmt.executeUpdate() > 0) {
                    n = 1;
                    return n;
                } else {
                    n = -1;
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

    public int update(Ingredient ingredient) {
        ConnectionDB connectionDB= new ConnectionDB();
        int n = 0;
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE id = ?");
            pstmt.setInt(1, ingredient.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE ingredient SET id, name, description = ?,?,? WHERE id = ?");
                pstmt.setInt(1, ingredient.getId());
                pstmt.setString(2, ingredient.getName());
                pstmt.setString(3, ingredient.getDescription());


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


    public int remove(Ingredient ingredient) {
        ConnectionDB connectionDB= new ConnectionDB();

        int n = 0;
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("DELETE FROM ingredient WHERE name=?");
            pstmt.setString(1, ingredient.getName());
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
