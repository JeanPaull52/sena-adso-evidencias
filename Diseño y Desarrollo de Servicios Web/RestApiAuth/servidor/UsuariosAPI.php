<?php
require_once "UsuariosDB.php";

class UsuariosAPI {
    private $db;

    public function __construct() {
        $this->db = new UsuariosDB();
    }

    public function API() {
        header("Content-Type: application/json");
        $method = $_SERVER["REQUEST_METHOD"];
        
        switch ($method) {
            case "POST":
                if ($_GET["action"] == "register") {
                    $this->registrarUsuario();
                } elseif ($_GET["action"] == "login") {
                    $this->iniciarSesion();
                }
                break;
            case "DELETE":
                if ($_GET["action"] == "logout") {
                    $this->cerrarSesion();
                }
                break;
            default:
                $this->response(405, "error", "Método no permitido");
                break;
        }
    }

    private function registrarUsuario() {

        try {
            // Leer y decodificar datos del cuerpo de la petición
            $datos = json_decode(file_get_contents("php://input"), true);
    
            // DEPURACIÓN: Verificar si los datos llegan correctamente
            file_put_contents("debug.log", print_r($datos, true));
    
            if (!$datos) {
                throw new Exception("Error al decodificar JSON o JSON vacío");
            }
    
            // Validar que los datos necesarios están presentes
            if (!isset($datos["nombre_usuario"], $datos["email"], $datos["contrasena"])) {
                throw new Exception("Datos incompletos: Se requieren nombre_usuario, email y contrasena");
            }
    
            // Intentar registrar al usuario en la base de datos
            $registroExitoso = $this->db->registrarUsuario($datos["nombre_usuario"], $datos["email"], $datos["contrasena"]);
            if (!$registroExitoso) {
                return $this->response(200, "error", "El usuario ya se encuentra registrado");
            }
    
            // Respuesta exitosa
            return $this->response(201, "success", "Usuario registrado");
    
        } catch (Exception $e) {
            var_dump($e);

            // Manejo de errores y respuesta adecuada
        return $this->response(500, "error", $e->getMessage());
        }
    }
    
    private function iniciarSesion() {
        $datos = json_decode(file_get_contents("php://input"));
        if (!$datos || !isset($datos->email, $datos->contrasena)) {
            $this->response(400, "error", "Datos incompletos");
            return;
        }

        $id_usuario = $this->db->iniciarSesion($datos->email, $datos->contrasena);
        if ($id_usuario) {
            $token = bin2hex(random_bytes(32));
            $this->db->guardarSesion($id_usuario, $token);
            $this->response(200, "success", message: ["token" => $token]);
        } else {
            $this->response(401, "error", "Credenciales incorrectas");
        }
    }

    private function cerrarSesion() {
        $token = $_GET["token"] ?? "";
        if ($token && $this->db->cerrarSesion($token)) {
            $this->response(200, "success", "Sesión cerrada");
        } else {
            $this->response(400, "error", "Error al cerrar sesión");
        }
    }

    private function response($code, $status, $message) {
        http_response_code($code);
        echo json_encode(["status" => $status, "message" => $message], JSON_PRETTY_PRINT);
    }
}

$api = new UsuariosAPI();
$api->API();
?>