

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(urlPatterns = "/formulario", initParams = {@WebInitParam(name = "dbUrl", value = "jdbc:postgresql://localhost:5432/bootcamp_market"),
        @WebInitParam(name = "dbUser", value = "postgres"),
        @WebInitParam(name = "dbPassword", value = "postgres"),
})

public class InsertarClientes extends HttpServlet {

    Connection connection;

    public void init(ServletConfig config) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(config.getInitParameter("dbUrl"), config.getInitParameter("dbUser"), config.getInitParameter("dbPassword"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            Statement stmt = connection.createStatement();
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            String id = req.getParameter("id");
            String nombre = req.getParameter("nombre");
            String apellido = req.getParameter("apellido");
            String ci = req.getParameter("ci");
            String tel = req.getParameter("tel");
            String consulta = "INSERT INTO cliente VALUES ('" + id + "','" + nombre + "','" + apellido + "','" + ci + "', '" + tel + "');";
            ResultSet rs = stmt.executeQuery(consulta);
            out.println("<html>");
            out.println("<body>");

            out.println("</body>");
            out.println("</html>");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
}