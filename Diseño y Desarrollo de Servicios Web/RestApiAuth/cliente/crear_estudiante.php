<?php
session_start();
require_once('Rutas.php');

if (!isset($_SESSION['usuario'])) {
    header("Location: login.php");
    exit;
}

$rutas = new Rutas();
$mensaje = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nombre    = htmlspecialchars($_POST["nombre"]);
    $correo    = filter_var($_POST["correo"], FILTER_SANITIZE_EMAIL);
    $celular   = htmlspecialchars($_POST["celular"]);
    $direccion = htmlspecialchars($_POST["direccion"]);

    $data = json_encode([
        "nombre"    => $nombre,
        "correo"    => $correo,
        "celular"   => $celular,
        "direccion" => $direccion
    ]);

    $ch = curl_init($rutas->getCrearEstudianteApiUrl());
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, ["Content-Type: application/json"]);

    $res = curl_exec($ch);
    curl_close($ch);

    $response = json_decode($res, true);
    //echo "<pre>RESPUESTA DE LA API: "; var_dump($res); echo "</pre>";
      //Validación en caso de errores. Muestra en pantalla la respuesta de la API al procesar el evento.

    if ($response && $response["status"] == "success") {
        $mensaje = "Estudiante registrado correctamente";
    } else {
        $mensaje = isset($response["message"]) ? $response["message"] : "Error al registrar";
    }
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar Estudiante</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <body>
    <div class="tarjeta">
        <h2>Registrar Estudiante</h2>
        <form method="POST">
            <label>Nombre:</label>
            <input type="text" name="nombre" required>
            <label>Correo:</label>
            <input type="email" name="correo" required>
            <label>Celular:</label>
            <input type="text" name="celular" required>
            <label>Dirección:</label>
            <input type="text" name="direccion" required>
            <button type="submit">Guardar</button>
        </form>
        <?php if ($mensaje) : ?>
            <p class="mensaje <?= $mensaje == 'Estudiante registrado correctamente' ? '' : 'mensaje-error' ?>">
                <?= $mensaje ?>
            </p>
        <?php endif; ?>
        <p><a href="listar_estudiantes.php">Ver lista de estudiantes</a> | <a href="index.php">Volver al inicio</a></p>
    </div>
</body>
</body>
</html>