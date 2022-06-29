import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/conexion", initParams = {@WebInitParam(name = "dbUrl", value = "jdbc:postgresql://localhost:5432/bootcamp_market"),
        @WebInitParam(name = "dbUser", value = "postgres"),
        @WebInitParam(name = "dbPassword", value = "postgres"),
})

public class BD extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(config.getInitParameter("dbUrl"), config.getInitParameter("dbUser"), config.getInitParameter("dbPassword"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            Statement stmt = connection.createStatement();
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            ResultSet rs = stmt
                    .executeQuery("select a.nombre, apellido, count(b.cliente_id) Cantidad_factura from cliente a " +
                            "inner join factura b " +
                            "on a.id=b.cliente_id " +
                            "group by a.nombre, a.apellido " +
                            "order by Cantidad_factura desc;");
            out.println("<html>");
            out.println("<body>");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int cantidad = rs.getInt("Cantidad_factura");

                out.println("<p>NOMBRE = " + nombre + "</p>");
                out.println("<p>APELLIDO = " + apellido + "</p>");
                out.println("<p>CANTIDAD FACTURA = " + cantidad + "</p>");

            }
            out.println("</body>");
            out.println("</html>");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }


    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
