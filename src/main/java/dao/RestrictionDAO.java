package dao;


import java.sql.*;

public class RestrictionDAO {
//    Return -1 = caiu no catch
//    Return 0 = caiu no else

    private PreparedStatement pstmt;

    public boolean connect() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BancoLets", "postgres", "Minyoongi2");
//            Conexão com o banco
            return true;
//            Se a conexão der certo, retorna true
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//    Método para conectar

    public void disconnect() {
        Connection conn = null;

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
//            Se a conexão for diferente de null, então, ela tive sido aberta e for diferente de fechada, nós fechamos a conexão
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar todos os registros
    public ResultSet selectAll() {
        Connection conn = null;

        try {
            connect();
            pstmt = conn.prepareStatement("select * from restriction");
//            Selecionando tudo de restrição
            return pstmt.executeQuery(); // Correto para consulta
//            executando o comando
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            disconnect();
        }
    }

    // Adicionar todos os registros
    public int insert(String type, String img_url, String description) {
        Connection conn = null;

        try {
            connect();
//            Como os dados são adiconados com id serial, apenas adicionamos os dados, sem verificar se o id existe
//                Se não possuem dados com esse código, inserimos os valores dados como parâmetro com o id
//            Como is_deleted e is_updated tem default false, não adicionamos eles aqui
            pstmt = conn.prepareStatement("insert into restriction (type, image_url, description) values (?, ?, ?)");
            pstmt.setString(1, type);
            pstmt.setString(2, img_url);
            pstmt.setString(3, description);
            return pstmt.executeUpdate();  // Correto para INSERT
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            disconnect();
        }
    }
//      Métodos de alterar

    // Alterar o type
    public int alterType(int id, String type) {
        Connection conn = null;

        try {
            connect();
            pstmt = conn.prepareStatement("select type from restriction where id=?");
//            Selecionando os types relacionados ao id
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
//            Executando o select
            if (rs.next()) {
//          Caso o select retorne algo, fazemos o update
                pstmt = conn.prepareStatement("update restriction set type=? where id=?");
                pstmt.setString(1, type);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                pstmt = conn.prepareStatement("update restriction set is_updated=? where id=?");
                pstmt.setBoolean(1,true);
                pstmt.setInt(2,id);
                return pstmt.executeUpdate();  // Correto para UPDATE
//                Executamos o código

            } else {
                return 0; // Registro não encontrado
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            disconnect();
        }
    }

    // Alterar URL da imagem
    public int alterImageURL(int id, String image_url) {
        Connection conn = null;

        try {
            connect();
            pstmt = conn.prepareStatement("select image_url from restriction where id=?");
//            Selecionamos a url da restrição onde o id é o mesmo do parâmetro
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
//            Executamos o select
            if (rs.next()) {
//                Se o id existir, ele faz o update
                pstmt = conn.prepareStatement("update restriction set image_url=? where id=?");
                pstmt.setString(1, image_url);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                pstmt = conn.prepareStatement("update restriction set is_updated=? where id=?");
                pstmt.setBoolean(1,true);
                pstmt.setInt(2,id);
                return pstmt.executeUpdate();
//                Executando o código
            } else {
                return 0;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            disconnect();
        }
    }

    // Alterar descrição
    public int alterDescription(int id, String description) {
        Connection conn = null;

        try {
            connect();
//            Fazemos o connect
            pstmt = conn.prepareStatement("select description from restriction where id=?");
//          Selecionamos a descrição dos dados da restrição onde o id é igual ao parâmetro
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
//           executamos o select
            if (rs.next()) {
//                Se existem dados relacionados a isso e o select retornou, fazemos o update da restrição
                pstmt = conn.prepareStatement("update restriction set description=? where id=?");
                pstmt.setString(1, description);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                pstmt = conn.prepareStatement("update restriction set is_updated=? where id=?");
                pstmt.setBoolean(1,true);
                pstmt.setInt(2,id);
                return pstmt.executeUpdate();
//              Executamos o código
            } else {
                return 0;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            disconnect();
        }
    }

//    Métodos de remover

    public int removeRestriction(int id) {
        Connection conn = null;

        try {
            connect();
            pstmt = conn.prepareStatement("select * from restriction where id=?");
//            Select da descrição onde o id é igual ao parâmetro dado
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
//            Executamos o select
            if (rs.next()) {
//                Se o relect retorna algo, ele faz o delete de restrição baseado o id;


                pstmt = conn.prepareStatement("update user_restriction set is_deleted=? where restriction_id=?");
                pstmt.setBoolean(1,true);
                pstmt.setInt(2,id);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement("update ingredient_restriction set is_deleted=? where restriction_id=?");
                pstmt.setBoolean(1,true);
                pstmt.setInt(2,id);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement("update recipe_restriction set is_deleted=? where restriction_id=?");
                pstmt.setBoolean(1,true);
                pstmt.setInt(2,id);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement("UPDATE restriction set is_deleted=? where id=?");
                pstmt.setBoolean(1,true);
                pstmt.setInt(2,id);
                return pstmt.executeUpdate();

//                Executamos o código
            } else {
                return 0;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            disconnect();
        }
    }

}
