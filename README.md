# Sistema de Gestión de Ventas - Tienda de celulares

Proyecto basado en las páginas HTML/CSS/JavaScript originales, con backend Java,
Spring Boot, Maven y acceso a la base de datos mediante JDBC (`JdbcTemplate`).

## Requisitos
- JDK 17 o superior
- Maven
- MariaDB/MySQL iniciado y base `tienda_celulares` importada
- Conexión local configurada en variables de entorno

## Ejecutar
En PowerShell, desde la carpeta que contiene `pom.xml`:

```powershell
$env:DB_URL="jdbc:mariadb://localhost:3306/tienda_celulares"
$env:DB_USER="root"
$env:DB_PASSWORD="TU_CLAVE"
mvn spring-boot:run
```

Abre http://localhost:8080

No abras los HTML directamente con doble clic: deben servirse desde Spring Boot
para que las llamadas `/api/...` lleguen al backend.

## API REST
- `GET /api/clientes`, `POST /api/clientes`, `PUT /api/clientes/{id}`, `DELETE /api/clientes/{id}`
- `GET /api/productos`, `POST /api/productos`, `PUT /api/productos/{id}`, `DELETE /api/productos/{id}`
- `GET /api/ventas`, `POST /api/ventas`, `PUT /api/ventas/{id}`, `DELETE /api/ventas/{id}`

Las ventas permiten administrar la cabecera (`id_cliente`, `fecha`, `total`).
Al eliminar una venta también se eliminan sus filas de `detalle_ventas`.
La eliminación de clientes/productos vinculados a ventas se bloquea para respetar
las claves foráneas.

## Seguridad de configuración
No guardar credenciales reales en el repositorio. Usa variables de entorno.
