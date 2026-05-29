<?php

//Validamos primero que exista una sesión activa.
session_start();
require_once('Rutas.php');

if (!isset($_SESSION['usuario'])) {
    header("Location: login.php");
    exit;
}

$rutas = new Rutas();

$ch = curl_init($rutas->getListarEstudiantesApiUrl());
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
$res = curl_exec($ch);
curl_close($ch);

$response = json_decode($res, true);
$estudiantes = ($response && $response["status"] == "success") ? $response["message"] : [];
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Estudiantes</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <body>
    <div class="tarjeta tarjeta-ancha">
        <h2>Lista de Estudiantes</h2>

        <?php if (count($estudiantes) > 0) : ?>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Celular</th>
                    <th>Dirección</th>
                </tr>
                <?php foreach ($estudiantes as $estudiante) : ?>
                    <tr>
                        <td><?= htmlspecialchars($estudiante["id_estudiante"]) ?></td>
                        <td><?= htmlspecialchars($estudiante["nombre"]) ?></td>
                        <td><?= htmlspecialchars($estudiante["correo"]) ?></td>
                        <td><?= htmlspecialchars($estudiante["celular"]) ?></td>
                        <td><?= htmlspecialchars($estudiante["direccion"]) ?></td>
                    </tr>
                <?php endforeach; ?>
            </table>
        <?php else : ?>
            <p>No hay estudiantes registrados todavía.</p>
        <?php endif; ?>

        <p>
            <a href="crear_estudiante.php">Registrar nuevo estudiante</a> |
            <a href="index.php">Volver al inicio</a>
        </p>
    </div>
</body>
</body>
</html>