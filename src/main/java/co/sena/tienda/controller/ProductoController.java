package co.sena.tienda.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final JdbcTemplate jdbc;
    public ProductoController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return jdbc.queryForList("SELECT ID AS id, MODELO AS modelo, MARCA AS marca, ALMACENAMIENTO AS almacenamiento, COLOR AS color, PRECIO AS precio, STOCK AS stock FROM productos ORDER BY ID");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> crear(@RequestBody Map<String, Object> d) {
        validar(d);
        jdbc.update("INSERT INTO productos (MODELO, MARCA, ALMACENAMIENTO, COLOR, PRECIO, STOCK) VALUES (?, ?, ?, ?, ?, ?)",
            s(d,"modelo"), s(d,"marca"), s(d,"almacenamiento"), s(d,"color"), new BigDecimal(s(d,"precio")), Integer.parseInt(s(d,"stock")));
        return jdbc.queryForMap("SELECT ID AS id, MODELO AS modelo, MARCA AS marca, ALMACENAMIENTO AS almacenamiento, COLOR AS color, PRECIO AS precio, STOCK AS stock FROM productos WHERE ID=LAST_INSERT_ID()");
    }

    @PutMapping("/{id}")
    public Map<String, Object> actualizar(@PathVariable int id, @RequestBody Map<String, Object> d) {
        validar(d);
        int n = jdbc.update("UPDATE productos SET MODELO=?, MARCA=?, ALMACENAMIENTO=?, COLOR=?, PRECIO=?, STOCK=? WHERE ID=?",
            s(d,"modelo"), s(d,"marca"), s(d,"almacenamiento"), s(d,"color"), new BigDecimal(s(d,"precio")), Integer.parseInt(s(d,"stock")), id);
        if (n == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        return jdbc.queryForMap("SELECT ID AS id, MODELO AS modelo, MARCA AS marca, ALMACENAMIENTO AS almacenamiento, COLOR AS color, PRECIO AS precio, STOCK AS stock FROM productos WHERE ID=?", id);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable int id) {
        try {
            int n = jdbc.update("DELETE FROM productos WHERE ID=?", id);
            if (n == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
            return Map.of("mensaje", "Producto eliminado");
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar un producto que aparece en detalles de ventas.");
        }
    }

    private void validar(Map<String,Object> d) {
        for (String k : List.of("modelo","marca","almacenamiento","color","precio","stock")) {
            if (d.get(k) == null || d.get(k).toString().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo " + k + " es obligatorio");
        }
        try {
            if (new BigDecimal(s(d,"precio")).signum() < 0 || Integer.parseInt(s(d,"stock")) < 0) throw new Exception();
        } catch (Exception e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precio o stock no válido"); }
    }
    private String s(Map<String,Object> d, String k) { return d.get(k).toString().trim(); }
}
