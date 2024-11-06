package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Restriction;

public class RestrictionDAO { // Classe RestrictionDAO

    //  Declaração dos Atributos
    private PreparedStatement pstmt;
    private ResultSet rs;

    //  Metodo que busca todos os registros da tabela Restriction
    public ResultSet selectAll() {
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("select * from restriction where is_deleted='false' order by type");
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null; // Considerar lançar uma exceção ao invés de retornar null.
        } finally {
            connectionDB.disconnect(); // Desconectar após a operação.
        }
    }

    //  Metodo que encontra Restriction pelo tipo
    public ResultSet selectByType(Restriction restriction) {
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction where type = ?");
            pstmt.setString(1, restriction.getType());
            return pstmt.executeQuery();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null; // Considerar lançar uma exceção ao invés de retornar null.
        } finally {
            connectionDB.disconnect(); // Desconectar após a operação.
        }
    }

    //  Metodo que insere registros na tabela Restriction
    public int insert(Restriction restriction) {
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        int n = 0;
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("insert into restriction (type, image_url, description) values (?, ?, ?)");
            pstmt.setString(1, restriction.getType());
            pstmt.setString(2, restriction.getImageURL());
            pstmt.setString(3, restriction.getDescription());
            if (validateUrl(restriction.getImageURL())) {
                // Verifica se o formato da URL é válida
                n = pstmt.executeUpdate(); // Considere verificar se n > 0 para confirmar a inserção.
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1; // Considere lançar uma exceção ao invés de retornar -1.
        } finally {
            connectionDB.disconnect(); // Desconectar após a operação.
        }
        return n; // Retorna o número de linhas afetadas pela operação.
    }

    //  Metodo que atualiza os dados da tabela Restriction pelo id
    public int update(Restriction restriction) {
        int n = 0;
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("SELECT * FROM restriction WHERE id = ?");
            pstmt.setInt(1, restriction.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("UPDATE restriction SET type = ?, image_url = ?, description = ? WHERE id = ?");
                pstmt.setString(1, restriction.getType());
                pstmt.setString(2, restriction.getImageURL());
                pstmt.setString(3, restriction.getDescription());
                pstmt.setInt(4, restriction.getId());
                if (validateUrl(restriction.getImageURL())) {
                    // Verifica se o formato da URL é válida
                    pstmt.executeUpdate(); // Considere verificar se a atualização foi bem-sucedida.
                }

                // Atualiza a coluna is_updated para 'true'
                pstmt.close();  // Fecha o pstmt para a próxima operação
                // Atualiza a coluna is_updated para True, para sinalizar que a operação foi concluída
                pstmt = conn.prepareStatement("update restriction set is_updated ='true' where id = ?");
                pstmt.setInt(1, restriction.getId());
                return pstmt.executeUpdate(); // Considere retornar um valor de confirmação.
            } else {
                return 0; // Retornar 0 se a restrição não for encontrada.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            n = -1; // Considere lançar uma exceção ao invés de retornar -1.
        } finally {
            connectionDB.disconnect(); // Desconectar após a operação.
        }
        return n; // Retornar o valor da variável n, que pode ser -1 se houver erro.
    }

    //  Metodo que remove os dados da tabela Restriction pelo id
    public int remove(Restriction restriction) {
        //      Utilizando a classe ConnectionDB para acessar os metodos de conectar e desconectar
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            Connection conn = connectionDB.connect();
            pstmt = conn.prepareStatement("select * from restriction where id=?");
            pstmt.setInt(1, restriction.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = conn.prepareStatement("update restriction set is_deleted = 'true' where id=?");
                pstmt.setInt(1, restriction.getId());
                return pstmt.executeUpdate(); // Considere retornar um valor de confirmação.
            } else {
                return 0; // Retornar 0 se a restrição não for encontrada.
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return -1; // Considere lançar uma exceção ao invés de retornar -1.
        } finally {
            connectionDB.disconnect(); // Desconectar após a operação.
        }
    }

    // Método para validar a URL
    public static boolean validateUrl(String url) {
        String regex = ".\\.(png|jpeg|PNG|JPEG|jpg)."; // Definindo o regex que valida os dados
        Pattern pattern = Pattern.compile(regex); // Transformando o regex em um padrão que vai servir de base para o matcher
        Matcher matcher = pattern.matcher(url); // Comparando o padrão com o parâmetro (a url)
        return matcher.matches(); // Retorna a comparação, true para correta e false para incorreta
    }
} // Fim da classe RestrictionDAO