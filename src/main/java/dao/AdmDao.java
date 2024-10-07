package dao;

import java.sql.*;

public class AdmDao {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    //Conectar ao banco de Dados
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

    //Desconectar do banco de dados
    public void desconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // select *
    public ResultSet selectAll() {
        try {
            connect();
            pstmt = conn.prepareStatement("SELECT * FROM Admin");
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            desconnect();
        }
    }
    public ResultSet searchForEmail(String  email) {
        try {
            connect();
            pstmt = conn.prepareStatement("SELECT * FROM Admin WHERE email = ?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        } finally {
            desconnect();
        }
    }
    public ResultSet searchForPassword(String  password) {
        try {
            connect();
            pstmt = conn.prepareStatement("SELECT * FROM Admin WHERE password = ?");
            pstmt.setString(1, password);
            ResultSet rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        } finally {
            desconnect();
        }
    }
}
