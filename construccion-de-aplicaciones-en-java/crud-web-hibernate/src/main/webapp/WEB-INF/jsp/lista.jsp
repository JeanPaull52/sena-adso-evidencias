<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.juanpablo.sena.crud.model.Estudiante" %>

<%
    List<Estudiante> estudiantes = (List<Estudiante>) request.getAttribute("estudiantes");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Lista de Estudiantes</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

    <div class="container">

        <h1>Lista de Estudiantes</h1>

        <p>
            <a href="<%= request.getContextPath() %>/" class="btn">Volver al inicio</a>
            <a href="<%= request.getContextPath() %>/estudiantes/crear" class="btn btn-success">Registrar nuevo estudiante</a>
        </p>

        <% if (estudiantes == null || estudiantes.isEmpty()) { %>
            <p>No hay estudiantes registrados.</p>
        <% } else { %>

            <p class="total">Total: <%= estudiantes.size() %> estudiantes</p>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Celular</th>
                        <th>Dirección</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Estudiante e : estudiantes) { %>
                        <tr>
                            <td><%= e.getIdEstudiante() %></td>
                            <td><%= e.getNombre() %></td>
                            <td><%= e.getCorreo() %></td>
                            <td><%= e.getCelular() %></td>
                            <td><%= e.getDireccion() %></td>
                            <td class="acciones">
                                <a href="<%= request.getContextPath() %>/estudiantes/editar?id=<%= e.getIdEstudiante() %>">Editar</a>
                                <a href="<%= request.getContextPath() %>/estudiantes/eliminar?id=<%= e.getIdEstudiante() %>"onclick="return confirm('¿Está seguro de eliminar al estudiante <%= e.getNombre() %>?');">Eliminar</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

        <% } %>

    </div>

</body>
</html>