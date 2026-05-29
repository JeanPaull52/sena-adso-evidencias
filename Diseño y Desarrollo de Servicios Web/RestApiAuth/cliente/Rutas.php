<?php
class Rutas {

    protected $urlBase = "http://localhost/RestApiAuth"; // URL BASE DEL FRONT -> http://localhost:80, http://localhost:81 ,http://localhost:90

    /**
     * retorna la url base de tu proyecto / aplicación
     */


    public function getUrlBase() {
        return $this->urlBase;
    }

    public function getUrlFront() {
        return $this->urlBase."/cliente"; // URL / DIRECTORIO DE FRONT
    }

    public function getUrlApi()
    {
        return $this->urlBase.'/servidor'; // URL / DIRECTORIO DE TU API
    }


    // AQUI VAN RUTAS API 

    public function getRegisterApiUrl()
    {
        return $this->getUrlApi() .'/UsuariosAPI.php?action=register';
    }
    public function getloginApiUrl()
    {
        return $this->getUrlApi() .'/UsuariosAPI.php?action=login';
    }

    public function getlogoutApiUrl(string $token): string
    {
        return $this->getUrlApi() .'/UsuariosAPI.php?action=logout&token="'.$token;
    }

    // AQUI VAN RUTAS DE ESTUDIANTES

    public function getCrearEstudianteApiUrl()
    {
        return $this->getUrlApi() . '/EstudiantesAPI.php?action=crear';
    }

    public function getListarEstudiantesApiUrl()
    {
        return $this->getUrlApi() . '/EstudiantesAPI.php?action=listar';
    }
}

?>