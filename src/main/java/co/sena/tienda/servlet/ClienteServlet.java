package co.sena.tienda.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "clienteServlet", urlPatterns = "/clientes-servlet")
public class ClienteServlet extends HttpServlet {
    private JdbcTemplate jdbc;

    @Override
    public void init() throws ServletException {
        WebApplicationContext context =
            WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.jdbc = context.getBean(JdbcTemplate.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String buscar = request.getParameter("buscar");
        List<Map<String, Object>> clientes;

        if (buscar != null && !buscar.isBlank()) {
            String filtro = "%" + buscar.trim() + "%";
            clientes = jdbc.queryForList(
                "SELECT id_cliente, nombre, telefono, correo, direccion " +
                "FROM clientes WHERE nombre LIKE ? OR correo LIKE ? ORDER BY id_cliente",
                filtro, filtro);
        } else {
            clientes = jdbc.queryForList(
                "SELECT id_cliente, nombre, telefono, correo, direccion " +
                "FROM clientes ORDER BY id_cliente");
        }

        request.setAttribute("clientes", clientes);
        request.setAttribute("buscar", buscar == null ? "" : buscar);
        request.setAttribute("mensaje", request.getParameter("mensaje"));
        request.getRequestDispatcher("/WEB-INF/jsp/clientes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nombre = limpiar(request.getParameter("nombre"));
        String telefono = limpiar(request.getParameter("telefono"));
        String correo = limpiar(request.getParameter("correo"));
        String direccion = limpiar(request.getParameter("direccion"));

        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || direccion.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                "Todos los campos del cliente son obligatorios.");
            return;
        }

        jdbc.update(
            "INSERT INTO clientes (nombre, telefono, correo, direccion) VALUES (?, ?, ?, ?)",
            nombre, telefono, correo, direccion);

        response.sendRedirect(request.getContextPath()
            + "/clientes-servlet?mensaje=" + URLEncoder.encode(
                "Cliente registrado correctamente", StandardCharsets.UTF_8));
    }

    private String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
