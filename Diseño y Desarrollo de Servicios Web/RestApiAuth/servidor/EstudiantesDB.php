<?php
require_once __DIR__ . "/config/database.php";

class EstudiantesDB {
    private $conn;

    public function __construct() {
        $database = new Database();
        $this->conn = $database->getConnection();
    }

    // CREAR: inserta un estudiante nuevo en la tabla
    public function crearEstudiante($nombre, $correo, $celular, $direccion) {
        try {
            $query = "INSERT INTO estudiante (nombre, correo, celular, direccion) VALUES (:nombre, :correo, :celular, :direccion)";
            $stmt = $this->conn->prepare($query);
            $stmt->bindParam(":nombre", $nombre);
            $stmt->bindParam(":correo", $correo);
            $stmt->bindParam(":celular", $celular);
            $stmt->bindParam(":direccion", $direccion);
            return $stmt->execute();
        } catch (PDOException $e) {
            return false;
        }
    }

    // LISTAR: devuelve todos los estudiantes como un array
    public function listarEstudiantes() {
        $query = "SELECT id_estudiante, nombre, correo, celular, direccion FROM estudiante ORDER BY id_estudiante DESC";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC); 
          //Trae todas las filas del resultado de la query y las guarda en un arreglo donde cada elementos es una fila con sus columnas "nombradas"
    }
}
?>