# RestApiAuth

Aplicación en PHP nativo para manejo de conceptos REST API que gestiona endpoints POST y GET para autenticación, creación y consulta mediante los servicios de login, registro de usuarios y los módulos para registro y listado de estudiantes.

## Tecnologías utilizadas

- PHP 8.2.12 (cli).
- PDO (PHP Data Objects).
- MySQL.exe  Ver 15.1 Distrib 10.4.32-MariaDB, for Win64 (AMD64) .
- cURL 
- XAMPP
- HTML y CSS

## Características

- Inicio y cierre de sesión con validación de campos y autenticación.
- Registro de usuarios.
- Registro de estudiantes.
- Listado de estudiantes.

## Estructura del proyecto

RestApiAuth/
├── README.md
├── cliente/
│   ├── index.php              -> Página de inicio
│   ├── login.php              -> Inicio de sesión
│   ├── register.php           -> Registro de usuarios
│   ├── logout.php             -> Cierre de sesión
│   ├── crear_estudiante.php   -> Formulario de estudiantes
│   ├── listar_estudiantes.php -> Listado de estudiantes
│   ├── Rutas.php              -> Centraliza las URLs
│   └── estilos.css            -> Estilos de las páginas
└── servidor/
    ├── index.php              -> Punto de entrada informativo
    ├── UsuariosAPI.php        -> API de autenticación
    ├── UsuariosDB.php         -> Acceso a datos de usuarios
    ├── EstudiantesAPI.php     -> API de estudiantes
    ├── EstudiantesDB.php      -> Acceso a datos de estudiantes
    ├── config/
    │   └── database.php       -> Configuración de la conexión
    └── sql/
        ├── script_bd_agenda.sql      -> Script de la base de datos
        ├── esquema.php
        └── MODELO_BD_AGENDA_PARA_API.png

## Instalación y ejecución

### Requisitos previos

- XAMPP (incluye Apache, MySQL y phpMyAdmin).
- Navegador web.

### Pasos

1. Instalar XAMPP
   Descargar desde https://www.apachefriends.org e instalar con las opciones por defecto.
2. Ubicar el proyecto
   Copiar la carpeta entera del proyecto en la ruta `C:\xampp\htdocs\`.
3. Iniciar los servicios
   En el panel de control de XAMPP iniciamos Apahce y MySQl. Ambos deben estar marcados en verde.
4. Configuración de la base de datos
   Se explica más abajo en el apartado de "Configuración de la base de datos".
5. Abrir el proyecto
   Se ingresa por medio del navegador a: `http://localhost/RestApiAuth/cliente/index.php`
   
## Configuración de la base de datos

   El proyecto utiliza una base de datos MySQL que se llama `registro_estudiantes`.

### Importar la base de datos

1. Con Apache y MySQL ejecutandose ingresamos a: `http://localhost/phpmyadmin`
2. Seleccionamos el archivo `servidor/sql/script_bd_agenda.sql`.
3. Damos en la opción de "Importar".

   El scritp crea automáticamente la base de datos `registro_estudiantes` con sus tres tablas (usuarios, sesiones y estudiante).

### Configurar la conexión

   Los datos de conexión se encuentran en `servidor/config/database.php`. Hay que verificar que la información coincida con la istalación local de XAMPP.

   Teniendo en cuenta que en una instalación estandar de XAMPP, el usuario `root` no tiene contraseña, el campo de contraseña se debe dejar vacío.

##  Autor

    Juan Pablo Quiroga Tecnólogo en Análisis y Desarrollo de Software — SENA (Servicio Nacional de Aprendizaje).