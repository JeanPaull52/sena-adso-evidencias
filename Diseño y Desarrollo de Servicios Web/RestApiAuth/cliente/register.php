<?php
require_once('Rutas.php');
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nombre_usuario = htmlspecialchars($_POST["nombre_usuario"]);
    $email = filter_var($_POST["email"], FILTER_SANITIZE_EMAIL);
    $contrasena = $_POST["contrasena"];
    $rutas = new Rutas();
    
    // Crear JSON
    $data = json_encode(["nombre_usuario" => $nombre_usuario, "email" => $email, "contrasena" => $contrasena]);
    
    //DEPURACIÓN: Verificar JSON antes de enviarlo
    // echo "<pre>JSON Enviado al servidor:<br>";
    // echo $data;
    // echo "</pre>";
    
    $ch = curl_init($rutas->getRegisterApiUrl());
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, ["Content-Type: application/json"]);
    
    $res = curl_exec($ch);
    curl_close($ch);
    
    $response = json_decode($res, true);

    //DEPURACIÓN: Verificar respuesta del servidor
    // echo "<pre>Respuesta del servidor:<br>";
    // print_r($response);
    // echo "</pre>";

    if ($response && $response["status"] == "success") {
        header("Location: login.php");
        exit;
    } else {
        $error = isset($response["message"]) ? $response["message"] : "Error en el registro";
    }
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <body>
    <div class="tarjeta">
        <h2>Registro</h2>
        <form method="POST">
            <label>Nombre:</label>
            <input type="text" name="nombre_usuario" required>
            <label>Email:</label>
            <input type="email" name="email" required>
            <label>Contraseña:</label>
            <input type="password" name="contrasena" required>
            <button type="submit">Registrarse</button>
        </form>
        <?php if (isset($error)) : ?>
            <p class="mensaje mensaje-error"><?= $error ?></p>
        <?php endif; ?>
        <p><a href="login.php">¿Ya tienes cuenta? Inicia sesión</a></p>
    </div>
</body>
</body>
</html>
