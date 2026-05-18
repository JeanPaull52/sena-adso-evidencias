<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.juanpablo.sena.crud.model.Estudiante" %>

<%
    Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");
    boolean esEdicion = (estudiante != null);

    String titulo = esEdicion ? "Editar estudiante" : "Registrar nuevo estudiante";
    String accion = esEdicion
        ? request.getContextPath() + "/estudiantes/editar"
        : request.getContextPath() + "/estudiantes/crear";

    String idValue       = esEdicion ? String.valueOf(estudiante.getIdEstudiante()) : "";
    String nombreValue   = esEdicion ? estudiante.getNombre()    : "";
    String correoValue   = esEdicion ? estudiante.getCorreo()    : "";
    String celularValue  = esEdicion ? estudiante.getCelular()   : "";
    String direccionValue= esEdicion ? estudiante.getDireccion() : "";
%>

<!DOCTYPE html>
<html>
<head>
    <title><%= titulo %></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

    <div class="container">

        <h1><%= titulo %></h1>

        <p>
            <a href="<%= request.getContextPath() %>/" class="btn">Volver al inicio</a>
            <a href="<%= request.getContextPath() %>/estudiantes/listar" class="btn">Ver listado</a>
        </p>

        <form action="<%= accion %>" method="POST">

            <% if (esEdicion) { %>
                <input type="hidden" name="id" value="<%= idValue %>">
            <% } %>

            <p>
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="<%= nombreValue %>" required>
            </p>

            <p>
                <label for="correo">Correo:</label>
                <input type="email" id="correo" name="correo" value="<%= correoValue %>" required>
            </p>

            <p>
                <label for="celular">Celular:</label>
                <input type="text" id="celular" name="celular" value="<%= celularValue %>" required>
            </p>

            <p>
                <label for="direccion">Dirección:</label>
                <input type="text" id="direccion" name="direccion" value="<%= direccionValue %>" required>
            </p>

            <p>
                <button type="submit"><%= esEdicion ? "Actualizar" : "Guardar" %></button>
            </p>

        </form>

    </div>

</body>
</html>