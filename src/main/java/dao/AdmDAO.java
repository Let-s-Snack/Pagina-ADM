package dao;

import java.sql.*;

public class AdmDAO {

    private PreparedStatement pstmt;
    private ResultSet rs;

    public ResultSet searchForEmail(String  email) {
        ConnectionDB connectionDB= new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM Admin WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }
    public ResultSet searchForPassword(String  email) {
        ConnectionDB connectionDB= new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT password FROM Admin WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }
}
