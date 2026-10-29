# Sistema de Gestión de Ventas - Tienda de celulares

Proyecto Java con Spring Boot 3, Maven, JDBC (`JdbcTemplate`) y MariaDB. Esta entrega conserva el CRUD REST y las páginas HTML/CSS/JavaScript de la versión anterior y añade un servlet Java con una vista JSP para demostrar el procesamiento de formularios mediante GET y POST.

## Requisitos
- JDK 17 o superior.
- Maven 3.6 o superior.
- MariaDB/MySQL iniciado.
- Base de datos `tienda_celulares` importada desde `tienda_celulares.sql`.

## Configurar la base de datos
Por defecto la aplicación intenta conectarse a `jdbc:mariadb://localhost:3306/tienda_celulares`, con usuario `root` y contraseña vacía. Si tu configuración es diferente, define estas variables en PowerShell antes de iniciar la aplicación:

```powershell
$env:DB_URL="jdbc:mariadb://localhost:3306/tienda_celulares"
$env:DB_USER="root"
$env:DB_PASSWORD="TU_CLAVE"
```

No guardes contraseñas reales en el proyecto ni en Git.

## Ejecutar
Abre PowerShell dentro de la carpeta que contiene `pom.xml` y ejecuta:

```powershell
mvn clean package
mvn spring-boot:run
```

Abre `http://localhost:8080`. No abras los archivos HTML directamente con doble clic; deben servirse desde Spring Boot para que las llamadas a `/api/...` funcionen.

## Módulo de servlet y JSP añadido para EV02
- `GET /clientes-servlet`: consulta todos los clientes.
- `GET /clientes-servlet?buscar=texto`: filtra por nombre o correo.
- `POST /clientes-servlet`: registra un cliente con nombre, teléfono, correo y dirección.
- Vista JSP: `src/main/webapp/WEB-INF/jsp/clientes.jsp`.
- Servlet: `src/main/java/co/sena/tienda/servlet/ClienteServlet.java`.

La vista JSP está ubicada bajo `WEB-INF`, por lo que se accede a ella a través del servlet y no directamente desde el navegador.

## API REST conservada
- `/api/clientes`: GET, POST, PUT y DELETE.
- `/api/productos`: GET, POST, PUT y DELETE.
- `/api/ventas`: GET, POST, PUT y DELETE.

El proyecto conserva las páginas `index.html`, `clientes.html`, `productos.html`, `ventas.html`, `script.js` y `style.css`, además de los controladores y el script SQL existentes.

## Estructura principal
```text
tienda_celulares/
├── pom.xml
├── tienda_celulares.sql
├── src/main/java/co/sena/tienda/
│   ├── TiendaCelularesApplication.java
│   ├── controller/
│   └── servlet/ClienteServlet.java
├── src/main/resources/static/
│   └── páginas HTML, JavaScript y CSS existentes
└── src/main/webapp/WEB-INF/jsp/clientes.jsp
```
