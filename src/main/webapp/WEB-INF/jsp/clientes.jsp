<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clientes - Tienda de celulares</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 28px; background: #f3f5f9; color: #202633; }
        main { max-width: 1050px; margin: auto; }
        section { background: white; padding: 22px; margin: 18px 0; border-radius: 12px; box-shadow: 0 2px 10px #00000010; }
        h1, h2 { margin-top: 0; }
        form { display: flex; flex-wrap: wrap; gap: 10px; align-items: end; }
        label { display: flex; flex-direction: column; gap: 6px; flex: 1 1 190px; }
        input { padding: 10px; border: 1px solid #cbd2df; border-radius: 7px; font-size: 15px; }
        button { padding: 11px 16px; border: 0; border-radius: 7px; background: #2458c6; color: white; cursor: pointer; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td { padding: 11px; border-bottom: 1px solid #e5e8ef; text-align: left; }
        th { background: #edf1f8; }
        .notice { padding: 12px; background: #e5f8ec; border-radius: 8px; color: #176b36; }
        .small { color: #5d6678; font-size: 14px; }
        .table-wrap { overflow-x: auto; }
    </style>
</head>
<body>
<main>
    <h1>Módulo de clientes</h1>
    <p class="small">Formulario HTML procesado mediante un servlet Java y una vista JSP.</p>

    <c:if test="${not empty mensaje}">
        <p class="notice"><c:out value="${mensaje}"/></p>
    </c:if>

    <section>
        <h2>Registrar cliente (POST)</h2>
        <form method="post" action="${pageContext.request.contextPath}/clientes-servlet">
            <label>Nombre
                <input type="text" name="nombre" maxlength="100" required>
            </label>
            <label>Teléfono
                <input type="text" name="telefono" maxlength="20" required>
            </label>
            <label>Correo electrónico
                <input type="email" name="correo" maxlength="100" required>
            </label>
            <label>Dirección
                <input type="text" name="direccion" maxlength="150" required>
            </label>
            <button type="submit">Guardar cliente</button>
        </form>
    </section>

    <section>
        <h2>Consultar clientes (GET)</h2>
        <form method="get" action="${pageContext.request.contextPath}/clientes-servlet">
            <label>Buscar por nombre o correo
                <input type="search" name="buscar" value="<c:out value='${buscar}'/>" placeholder="Ej. Ana o correo@ejemplo.com">
            </label>
            <button type="submit">Buscar</button>
            <a href="${pageContext.request.contextPath}/clientes-servlet">Mostrar todos</a>
        </form>

        <div class="table-wrap">
            <table>
                <thead>
                    <tr><th>ID</th><th>Nombre</th><th>Teléfono</th><th>Correo</th><th>Dirección</th></tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty clientes}">
                            <tr><td colspan="5">No se encontraron clientes.</td></tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="cliente" items="${clientes}">
                                <tr>
                                    <td><c:out value="${cliente.id_cliente}"/></td>
                                    <td><c:out value="${cliente.nombre}"/></td>
                                    <td><c:out value="${cliente.telefono}"/></td>
                                    <td><c:out value="${cliente.correo}"/></td>
                                    <td><c:out value="${cliente.direccion}"/></td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </section>
    <p><a href="/">Volver al inicio del sistema</a></p>
</main>
</body>
</html>
