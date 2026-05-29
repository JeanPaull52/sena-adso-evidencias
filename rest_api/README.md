# REST API Libros

Aplicación en Node.js con Express que implementa una REST API para la gestión de un catálogo de libros. Muestra los endpoints HTTP GET, POST, PUT y DELETE para realizar las operaciones CRUD (Create, Read, Update, Delete) sobre los registros, utilizando un archivo JSON como almacenamiento de datos.

## Tecnologías utilizadas

- Node.js v22.
- Express 5.
- Nodemon (entorno de desarrollo).
- body-parser.
- Postman (pruebas de la API).
- JSON (archivo plano como base de datos).

## Características

- Listado de todos los libros registrados.
- Consulta de un libro por su ID (Identifier).
- Creación de nuevos libros.
- Actualización parcial de libros existentes.
- Eliminación de libros.
- Manejo de errores con código 404 Not Found cuando se solicita un recurso inexistente.

## Estructura del proyecto

rest_api/
├── README.md
├── package.json          -> Configuración del proyecto y dependencias
├── package-lock.json     -> Versiones exactas de las dependencias instaladas
├── .gitignore            -> Archivos y carpetas excluidos del repositorio
├── index.js              -> Punto de entrada y definición de los endpoints
└── db.json               -> Base de datos en formato JSON

## Endpoints disponibles

| Método | Ruta            | Descripción                      |
|--------|-----------------|----------------------------------|
| GET    | /               | Mensaje de bienvenida            |
| GET    | /books          | Lista todos los libros           |
| GET    | /books/:id      | Consulta un libro por su ID      |
| POST   | /books          | Crea un nuevo libro              |
| PUT    | /books/:id      | Actualiza un libro existente     |
| DELETE | /books/:id      | Elimina un libro                 |

URL base: `http://localhost:3000`

## Instalación y ejecución

### Requisitos previos

- Node.js v18 o superior.
- npm (Node Package Manager, viene incluido con Node.js).
- Postman (para realizar las pruebas de los endpoints).

### Pasos

1. Instalar Node.js
   Descargar desde https://nodejs.org e instalar con las opciones por defecto. Para verificar la instalación, en la terminal ejecutamos `node --version` y `npm --version`.
2. Ubicar el proyecto
   Descomprimir o clonar el proyecto en la ruta deseada del equipo.
3. Instalar las dependencias
   Abrimos una terminal dentro de la carpeta del proyecto y ejecutamos `npm install`. Esto crea la carpeta `node_modules` con todas las librerías necesarias.
4. Iniciar el servidor
   En la misma terminal ejecutamos `npm run dev`. En la consola debe aparecer el mensaje `Server listening on port 3000`.
5. Probar la API
   Se ingresa desde el navegador o desde Postman a: `http://localhost:3000/`

### Detener el servidor

Para detener la API y liberar el puerto 3000, en la terminal donde se está ejecutando se presiona `Ctrl + C`.

## Configuración de la base de datos

El proyecto no requiere un motor de base de datos externo. Toda la información se almacena en el archivo `db.json` ubicado en la raíz del proyecto.

### Estructura del archivo db.json

El archivo contiene un objeto con la propiedad `books`, que es un arreglo de libros. Cada libro tiene los campos `id`, `name`, `author` y `year`:

```json
{
  "books": [
    {
      "id": 1,
      "name": "Los 7 hábitos de la gente altamente efectiva",
      "author": "Stephen R. Covey",
      "year": 1989
    }
  ]
}
```

### Funcionamiento

Cada vez que se ejecuta una petición que lee información (GET), la API abre el archivo `db.json` y devuelve los datos solicitados. Cuando se ejecuta una petición que modifica información (POST, PUT o DELETE), la API actualiza el contenido del archivo para persistir los cambios.

## Autor

Juan Pablo Quiroga — Tecnólogo en Análisis y Desarrollo de Software — SENA (Servicio Nacional de Aprendizaje).