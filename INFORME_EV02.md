# Informe técnico — AA2-EV02

## Objetivo
Ampliar el proyecto existente de gestión de una tienda de celulares para incluir un ejemplo funcional de formularios HTML procesados con un servlet Java y una vista JSP, sin reemplazar el CRUD REST que ya estaba desarrollado.

## Elementos conservados
- Controladores REST de clientes, productos y ventas.
- Formularios HTML existentes y archivos CSS/JavaScript.
- Script de estructura y datos `tienda_celulares.sql`.
- Configuración por variables de entorno para la conexión a MariaDB.
- Historial y configuración del repositorio Git que venía dentro del proyecto.

## Elementos añadidos o adaptados
- `ClienteServlet.java`, que extiende `HttpServlet`, implementa `doGet()` y `doPost()`, consulta clientes y registra nuevos clientes con consultas parametrizadas.
- `clientes.jsp`, con formulario GET de búsqueda y formulario POST de registro; los datos mostrados se escapan mediante JSTL.
- Dependencias Maven para empaquetado WAR, JSP/Jasper y JSTL.
- Clase de aplicación preparada para ejecución Spring Boot y despliegue WAR.
- Documentación de ejecución y rutas.

## Rutas para la comprobación
| Método | Ruta | Función |
|---|---|---|
| GET | `/clientes-servlet` | Mostrar clientes |
| GET | `/clientes-servlet?buscar=Ana` | Buscar por nombre o correo |
| POST | `/clientes-servlet` | Registrar cliente |
| GET/POST/PUT/DELETE | `/api/clientes` | CRUD REST conservado |
| GET/POST/PUT/DELETE | `/api/productos` | CRUD REST conservado |
| GET/POST/PUT/DELETE | `/api/ventas` | CRUD REST conservado |

## Lista de comprobación en el equipo
- [ ] `mvn clean package` termina sin errores.
- [ ] La aplicación inicia con MariaDB activa y credenciales correctas.
- [ ] La página principal sigue cargando.
- [ ] El servlet muestra clientes existentes.
- [ ] El formulario POST registra un cliente.
- [ ] La búsqueda GET filtra clientes.
- [ ] Los formularios CRUD REST existentes siguen funcionando.

**Estado:** los archivos del proyecto están preparados. Las pruebas de compilación y conexión deben ejecutarse en el equipo de desarrollo; no se marcan como aprobadas desde este entorno.
