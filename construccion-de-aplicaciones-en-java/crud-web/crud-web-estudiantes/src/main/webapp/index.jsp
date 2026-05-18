<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sistema de Gestión de Estudiantes</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Sistema de Gestión de Estudiantes</h1>
    <p>Bienvenido al sistema. Selecciona una opción:</p>
    <p>
        <li><a href="estudiantes/listar">Ver lista de estudiantes</a></li>
        <li><a href="estudiantes/crear">Registrar nuevo estudiante</a></li>
    </p>
    </div>
</body>
</html>