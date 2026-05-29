<?php
require_once "EstudiantesDB.php";

class EstudiantesAPI {
    private $db;

    public function __construct() {
        $this->db = new EstudiantesDB();
    }

    public function API() {
        header("Content-Type: application/json");
        $method = $_SERVER["REQUEST_METHOD"];
        $action = $_GET["action"] ?? "";

        if ($method === "POST" && $action === "crear") {
            $this->crearEstudiante();
        } elseif ($method === "GET" && $action === "listar") {
            $this->listarEstudiantes();
        } else {
            $this->response(405, "error", "Método o acción no permitidos");
        }
    }

    private function crearEstudiante() {
        $datos = json_decode(file_get_contents("php://input"), true);

        if (!$datos || !isset($datos["nombre"], $datos["correo"], $datos["celular"], $datos["direccion"])) {
            $this->response(400, "error", "Datos incompletos: todos los campos son obligatorios");
            return;
        }

        $exito = $this->db->crearEstudiante($datos["nombre"], $datos["correo"], $datos["celular"], $datos["direccion"]);

        if ($exito) {
            $this->response(201, "success", "Estudiante registrado correctamente");
        } else {
            $this->response(500, "error", "No se pudo registrar el estudiante");
        }
    }

    private function listarEstudiantes() {
        $estudiantes = $this->db->listarEstudiantes();
        $this->response(200, "success", $estudiantes);
    }

    private function response($code, $status, $message) {
        http_response_code($code);
        echo json_encode(["status" => $status, "message" => $message], JSON_PRETTY_PRINT);
    }
}

$api = new EstudiantesAPI();
$api->API();
?>