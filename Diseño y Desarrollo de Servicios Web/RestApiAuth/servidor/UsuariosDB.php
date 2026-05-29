<?php
require_once __DIR__ . "/config/database.php";

class UsuariosDB {
    private $conn;

    public function __construct() {
        $database = new Database();
        $this->conn = $database->getConnection();
    }

    public function usuarioExiste($email) {
        $query = "SELECT id_usuario FROM usuarios WHERE email = :email";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":email", $email);
        $stmt->execute();
        return $stmt->rowCount() > 0;
    }
    
    public function registrarUsuario($nombre_usuario, $email, $contrasena) {
        try {
            $hashed_password = password_hash($contrasena, PASSWORD_DEFAULT);
            $query = "INSERT INTO usuarios (nombre_usuario, email, contrasena) VALUES (:nombre_usuario, :email, :contrasena)";
            $stmt = $this->conn->prepare($query);
            $stmt->bindParam(":nombre_usuario", $nombre_usuario);
            $stmt->bindParam(":email", $email);
            $stmt->bindParam(":contrasena", $hashed_password);
    
            //DEPURACIÓN: Verificar si la consulta SQL se ejecuta correctamente
            if (!$stmt->execute()) {
                throw new Exception("Error en la consulta SQL: " . implode(" ", $stmt->errorInfo()));
            }
    
            return true;
    
        } catch (Exception $e) {
            file_put_contents("debug.log", "Error en registrarUsuario: " . $e->getMessage() . "\n", FILE_APPEND);
            return false;
        }
    }
    

    public function iniciarSesion($email, $contrasena) {
        $query = "SELECT id_usuario, nombre_usuario, contrasena FROM usuarios WHERE email = :email";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":email", $email);
        $stmt->execute();
        $usuario = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if ($usuario && password_verify($contrasena, $usuario["contrasena"])) {
            return $usuario["id_usuario"];
        }
        return false;
    }

    public function guardarSesion($id_usuario, $token) {
        $query = "INSERT INTO sesiones (id_usuario, token) VALUES (:id_usuario, :token)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":id_usuario", $id_usuario);
        $stmt->bindParam(":token", $token);
        return $stmt->execute();
    }

    public function cerrarSesion($token) {
        $query = "DELETE FROM sesiones WHERE token = :token";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":token", $token);
        return $stmt->execute();
    }
}
?>