package dao;

//importação de bibliotecas java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeDAO {// Classe RecipeDao
    private PreparedStatement pstmt;
    public boolean connect(){
        try{
            // infromando qual driver de conexão será utilizado pelo driverManager
            Class.forName("org.postgresql.Driver");

            String usuario = System.getenv("usuario");
            String senha = System.getenv("senha");

            // criando a conexão com o BD
            Connection conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/", usuario,senha);
            return true;
        }catch(SQLException sql){
            sql.printStackTrace();
            return false;
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
            return false;
        }

    }

    public void disconnect(){
        Connection conn = null;
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();

            }

        }catch(SQLException sql){
            sql.printStackTrace();

        }
    }

    public ResultSet selectAll(){
        ResultSet rset = null;
        Connection conn = null;
        try{
            connect();
            pstmt = conn.prepareStatement("SELECT * FROM repice ORDER BY id");
            rset = pstmt.executeQuery();
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        disconnect();
        return rset;

    }
    public int insert(String name, int id, String instruction, int ingridient_id, String image_url, int restriction_id, String steps){
        Connection conn = null;

        try{
            String stepsFormatted = "";
            for (int i = 0; i < steps.length(); i++) {
                char valor= steps.charAt(i);
                if (Character.isDigit(valor) && valor!='1'){
                    String[] parts = steps.split("(?=\\d+\\.)");


                    for (int j = 0; j < parts.length; j++){
                        stepsFormatted += parts[i].trim();
                        if (i < parts.length - 1) {
                            stepsFormatted += ";\n";
                        }
                    }
                }
            }
            connect();
            pstmt= conn.prepareStatement("INSERT INTO RECIPE (NAME,ID,INSTRUCTION,INGRIDIENT_ID,IMAGE_URL, RESTRICTION_ID,STEPS) VALUES (?,?,?,?,?,?,?)");
            pstmt.setString(1,name);
            pstmt.setInt(2,id);
            pstmt.setString(3,instruction);
            pstmt.setInt(4,ingridient_id);
            pstmt.setString(5,image_url);
            pstmt.setInt(6,restriction_id);
            pstmt.setString(7,stepsFormatted);
            pstmt.execute();
            return pstmt.executeUpdate();

        }catch (SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally{
            disconnect();

        }
    }

    public int alterName (String newName, int id){
        Connection conn = null;

        try{
            connect();
            pstmt= conn.prepareStatement("UPDATE recipe SET name=? WHERE id= ?");
            pstmt.setString(1,newName);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            return 1;
        }catch(SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally {
            disconnect();
        }
    }

    public int alterInstruction (String newInstruction, int id){
        Connection conn = null;

        try{
            connect();
            pstmt= conn.prepareStatement("UPDATE recipe SET instruction=? WHERE id= ?");
            pstmt.setString(1,newInstruction);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            return 1;
        }catch(SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally {
            disconnect();
        }
    }

    public int alterIngridientId (int newIngridient_id, int id){
        Connection conn = null;

        try{
            connect();
            pstmt= conn.prepareStatement("UPDATE recipe SET ingridient_id=? WHERE id= ?");
            pstmt.setInt(1,newIngridient_id);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            return 1;
        }catch(SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally {
            disconnect();
        }
    }
    public int alterImageUrl (String newImageUrl, int id){
        Connection conn = null;

        try{
            connect();
            pstmt= conn.prepareStatement("UPDATE recipe SET image_url=? WHERE id= ?");
            pstmt.setString(1,newImageUrl);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            return 1;
        }catch(SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally {
            disconnect();
        }
    }

    public int alterRestrictionId (int newRestrictionId, int id){
        Connection conn = null;

        try{
            connect();
            pstmt= conn.prepareStatement("UPDATE recipe SET restriction_id=? WHERE id= ?");
            pstmt.setInt(1,newRestrictionId);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            return 1;
        }catch(SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally {
            disconnect();
        }
    }

    public int deleteRow(int id){
        Connection conn = null;

        try{
            connect();
            pstmt= conn.prepareStatement("DELETE FROM recipe where id=?");
            pstmt.setInt(1,id);
            pstmt.execute();
            return pstmt.executeUpdate();

        }catch (SQLException sql){
            sql.printStackTrace();
            return -1;
        }
        finally{
            disconnect();

        }
    }

}
