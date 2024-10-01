import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try (Connection conn= DriverManager.getConnection("jdbc:postgresql://pg-lets-snack-lets-snack.k.aivencloud.com:18692/db-lets-snack-1o", "avnadmin","AVNS_1FznTa8oi0sxh3iE4Nm")){
            String email= req.getParameter("email");
            String password=req.getParameter("password");

            PreparedStatement pstmt= conn.prepareStatement("select * from admin where email=? and password=?");
            pstmt.setString(1,email);
            pstmt.setString(2,password);
            if (pstmt.execute()){
                req.getRequestDispatcher("indexAdm.html").forward(req,resp);
            }

        }catch (SQLException se){
            se.printStackTrace();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
