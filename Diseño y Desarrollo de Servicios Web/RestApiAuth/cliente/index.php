<?php
session_start();
require_once "Rutas.php";
$rutas = new Rutas();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <body>
    <div class="tarjeta">
        <h1>Bienvenido al sistema</h1>
        <?php if (isset($_SESSION['usuario'])) : ?>
            <p>Hola, <?= htmlspecialchars($_SESSION['usuario']) ?> | 
                <a href="logout.php">Cerrar sesión</a>
            </p>
            <p>
                <a href="crear_estudiante.php">Registrar estudiante</a> |
                <a href="listar_estudiantes.php">Ver lista de estudiantes</a>
            </p>
        <?php else : ?>
            <p><a href="login.php">Iniciar sesión</a> | <a href="register.php">Registrarse</a></p>
        <?php endif; ?>
    </div>
</body>
</body>
</html>
