package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://pg-lets-snack-lets-snack.k.aivencloud.com:18692/db-lets-snack-1o", "avnadmin", "AVNS_1FznTa8oi0sxh3iE4Nm");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    public ResultSet selectAll() {
        try {
            connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient");
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            disconnect();
        }
    }

    public ResultSet selectById(int id){
        try {
            connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE id = ? ");
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            disconnect();
        }
    }

    public int insert(int id, String name, String description) {
        int n = 0;
        try {
            connect();
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
            disconnect();
        }
        return n;
    }

    public int update(String column, String newValue, int id) {
        int n = 0;
        try {
            connect();
            pstmt = conn.prepareStatement("SELECT * FROM ingredient WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE ingredient SET " + column + " = ? WHERE id = ?");
                pstmt.setString(1, newValue);
                pstmt.setInt(2, id);

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
            disconnect();
        }
        return n;
    }


    public int updateName(int id, String name) {return update("name", name, id);}
    public int updateDescription(int id, String description) {return update("description", description,id);}

    public int remove(String column, int id) {
        int n = 0;
        try {
            connect();
            pstmt = conn.prepareStatement("SELECT " + column + " FROM ingredient WHERE id=?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("DELETE FROM ingredient WHERE id=?");
                pstmt.setInt(1, id);

                if (pstmt.executeUpdate() > 0) {
                    n = 1;
                } else {
                    n = -1;
                }
            } else {
                n = -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            disconnect();
        }
        return n;
    }

    public int removeId(int id) {return remove("id", id);}
    public int removeName(int id) {return remove("name", id);}
    public int removeRestriction(int id) {return remove("description", id);}

}
