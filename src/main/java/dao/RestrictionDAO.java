package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Ingredient;
import model.Recipe;
import model.Restriction;

public class RestrictionDAO { //Classe RestrictionDAO

//  Declaração dos Atributos
    private PreparedStatement pstmt;
    private ResultSet rs;

//  Metodo que busca todos os registros da tabela Restriction
    public ResultSet selectAll() {

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("select * from restriction where is_deleted='false' order by type");
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }

    //  Metodo que encontra Ingredient pelo nome
    public ResultSet selectByType(Restriction restriction) {

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB= new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction where type = ?");
            pstmt.setString(1, restriction.getType());
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            connectionDB.disconnect();
        }
    }
    public int id (){
        ConnectionDB connectionDB= new ConnectionDB();
        try{
            Connection conn= connectionDB.connect();
            pstmt=conn.prepareStatement("select id+1 as new_id from recipe order by id desc limit 1");
            rs=pstmt.executeQuery();
            return rs.getInt("new_id");
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
//  Metodo que insere registros na tabela Restriction
    public int insert(Restriction restriction) {

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("insert into restriction (id,type, image_url, description) values (?,?, ?, ?)");
            pstmt.setInt(1, id());
            pstmt.setString(2, restriction.getType());
            pstmt.setString(3, restriction.getImageURL());
            pstmt.setString(4, restriction.getDescription());
            return pstmt.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            connectionDB.disconnect();
        }
    }
//  Metodo que atualiza os dados da tabela Restriction pelo id
    public int update(Restriction restriction) {
        int n = 0;

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn= connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction WHERE id = ?");
            pstmt.setInt(1, restriction.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE restriction SET type = ?, image_url = ?, description = ? WHERE id = ?");
                pstmt.setString(1, restriction.getType());
                pstmt.setString(2, restriction.getImageURL());
                pstmt.setString(3, restriction.getDescription());
                pstmt.setInt(4, restriction.getId());
                pstmt.executeUpdate();

                // Atualiza a coluna is_updated para 'true'
                pstmt.close();  // Fecha o pstmt para a próxima operação
                //Atualiza a coluna is_updated para True, para sinalizar qeu a operação foi concluída
                pstmt = conn.prepareStatement("update restriction set is_updated ='true' where id =?");
                pstmt.setInt(1, restriction.getId());
                return pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1;
        } finally {
            connectionDB.disconnect();
        }
        return n;
    }

//  Metodo que remove os dados da tabela Restriction pelo id
public int remove(Restriction restriction){

//      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
    ConnectionDB connectionDB= new ConnectionDB();
    try{
        Connection conn= connectionDB.connect();
        pstmt= conn.prepareStatement("select * from restriction where id=?");
        pstmt.setInt(1,restriction.getId());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            pstmt = conn.prepareStatement("update restriction set is_deleted = 'true' where id=?");
            pstmt.setInt(1,restriction.getId());
            return pstmt.executeUpdate();
        } else {
            return 0;
        }
    }catch (SQLException sql){
        sql.printStackTrace();
        return -1;
    }
    finally{
        connectionDB.disconnect();
    }
}

}