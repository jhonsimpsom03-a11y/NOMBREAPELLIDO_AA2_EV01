package co.sena.tienda.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    private final JdbcTemplate jdbc;
    public VentaController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT v.id_venta, v.id_cliente, c.nombre AS cliente, v.fecha, v.total FROM ventas v JOIN clientes c ON c.id_cliente=v.id_cliente ORDER BY v.id_venta DESC");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Map<String, Object> crear(@RequestBody Map<String, Object> d) {
        int cliente = entero(d, "id_cliente");
        LocalDate fecha;
        BigDecimal total;
        try {
            fecha = LocalDate.parse(d.get("fecha").toString());
            total = new BigDecimal(d.get("total").toString());
            if (total.signum() < 0) throw new Exception();
        } catch (Exception e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fecha o total no válido"); }
        if (jdbc.queryForList("SELECT id_cliente FROM clientes WHERE id_cliente=?", cliente).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente seleccionado no existe");
        jdbc.update("INSERT INTO ventas (id_cliente, fecha, total) VALUES (?, ?, ?)", cliente, fecha, total);
        return jdbc.queryForMap("SELECT v.id_venta, v.id_cliente, c.nombre AS cliente, v.fecha, v.total FROM ventas v JOIN clientes c ON c.id_cliente=v.id_cliente WHERE v.id_venta=LAST_INSERT_ID()");
    }

    @PutMapping("/{id}")
    public Map<String, Object> actualizar(@PathVariable int id, @RequestBody Map<String, Object> d) {
        int cliente = entero(d, "id_cliente");
        LocalDate fecha;
        BigDecimal total;
        try {
            fecha = LocalDate.parse(d.get("fecha").toString());
            total = new BigDecimal(d.get("total").toString());
            if (total.signum() < 0) throw new Exception();
        } catch (Exception e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fecha o total no válido"); }
        int n = jdbc.update("UPDATE ventas SET id_cliente=?, fecha=?, total=? WHERE id_venta=?", cliente, fecha, total, id);
        if (n == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada");
        return jdbc.queryForMap("SELECT v.id_venta, v.id_cliente, c.nombre AS cliente, v.fecha, v.total FROM ventas v JOIN clientes c ON c.id_cliente=v.id_cliente WHERE v.id_venta=?", id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public Map<String, String> eliminar(@PathVariable int id) {
        if (jdbc.queryForList("SELECT id_venta FROM ventas WHERE id_venta=?", id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada");
        jdbc.update("DELETE FROM detalle_ventas WHERE id_venta=?", id);
        jdbc.update("DELETE FROM ventas WHERE id_venta=?", id);
        return Map.of("mensaje", "Venta y sus detalles eliminados");
    }

    private int entero(Map<String,Object> d, String k) {
        try { return Integer.parseInt(d.get(k).toString()); }
        catch (Exception e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo " + k + " debe ser numérico"); }
    }
}
