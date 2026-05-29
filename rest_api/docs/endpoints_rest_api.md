# Documentación de Endpoints — REST API de Libros

**Autor:** Juan Pablo Quiroga Q.
**Proyecto:** REST API 
**URL base:** `http://localhost:3000`

---

## ¿Qué es un endpoint?

Un **endpoint** (punto de acceso o punto final) es la combinación de una **ruta** (la dirección dentro de la API) y un **método HTTP** (Hypertext Transfer Protocol) que define **una operación concreta** que la API puede realizar. Cada endpoint es como una "puerta" distinta hacia la API: cada una recibe un tipo de petición y devuelve una respuesta.

Un endpoint se identifica siempre por dos cosas juntas:

1. **El método HTTP:** indica la intención de la operación (GET = leer, POST = crear, PUT = actualizar, DELETE = eliminar).
2. **La ruta:** la dirección a la que se hace la petición (por ejemplo `/books` o `/books/1`).

Por eso `GET /books` y `POST /books` son **dos endpoints diferentes** aunque compartan la misma ruta: el método cambia la operación.

---

## Tabla resumen de endpoints

| # | Método HTTP | Ruta | Operación (CRUD) | Descripción | Requiere body |
|---|-------------|------|------------------|-------------|---------------|
| 1 | GET | `/` | — | Mensaje de bienvenida de la API | No |
| 2 | GET | `/books` | Read (leer) | Devuelve todos los libros | No |
| 3 | GET | `/books/:id` | Read (leer) | Devuelve un libro según su ID (Identifier) | No |
| 4 | POST | `/books` | Create (crear) | Crea un nuevo libro | Sí (JSON) |
| 5 | PUT | `/books/:id` | Update (actualizar) | Actualiza un libro existente según su ID | Sí (JSON) |
| 6 | DELETE | `/books/:id` | Delete (eliminar) | Elimina un libro según su ID | No |


---

## Detalle de cada endpoint

### 1. GET `/` — Bienvenida
- **URL completa:** `http://localhost:3000/`
- **Qué hace:** responde con un texto de bienvenida.
  ```
  Welcome to my first API with Node js!
  ```
- **Código de estado esperado:** `200 OK`

### 2. GET `/books` — Listar todos los libros
- **URL completa:** `http://localhost:3000/books`
- **Qué hace:** lee el archivo `db.json` y devuelve el arreglo completo de libros.
- **Respuesta esperada (JSON):**
  ```json
  [
    { "id": 1, "name": "Los 7 hábitos de la gente altamente efectiva", "author": "Stephen R. Covey", "year": 1989 },
    { "id": 2, "name": "Padre rico, padre pobre", "author": "Robert Kiyosaki", "year": 1997 },
    { "id": 3, "title": "Breve historia del tiempo", "author": "Stephen Hawking", "year": 2001 }
  ]
  ```
- **Código de estado esperado:** `200 OK`

### 3. GET `/books/:id` — Obtener un libro por ID
- **URL de ejemplo:** `http://localhost:3000/books/2`
- **Parámetro de ruta:** `:id` → el identificador numérico del libro.
- **Qué hace:** busca en `db.json` el libro cuyo `id` coincide y lo devuelve.
- **Respuesta esperada para el ID 2 (JSON):**
  ```json
  { "id": 2, "name": "Padre rico, padre pobre", "author": "Robert Kiyosaki", "year": 1997 }
  ```
- **Código de estado esperado:** `200 OK`

### 4. POST `/books` — Crear un libro
- **URL completa:** `http://localhost:3000/books`
- **Header requerido:** `Content-Type: application/json`
- **Body (JSON) de ejemplo:**
  ```json
  {
    "name": "El principito",
    "author": "Antoine de Saint-Exupéry",
    "year": 1943
  }
  ```
- **Qué hace:** crea un nuevo libro asignándole un `id` automático y lo guarda en `db.json`.
- **Respuesta esperada (JSON):**
  ```json
  { "id": 4, "name": "El principito", "author": "Antoine de Saint-Exupéry", "year": 1943 }
  ```
- **Código de estado esperado:** `200 OK`

### 5. PUT `/books/:id` — Actualizar un libro
- **URL de ejemplo:** `http://localhost:3000/books/2`
- **Header requerido:** `Content-Type: application/json`
- **Body (JSON) de ejemplo (solo los campos a cambiar):**
  ```json
  { "year": 2005 }
  ```
- **Qué hace:** combina los datos existentes del libro con los nuevos y guarda los cambios.
- **Respuesta esperada (libro existente):**
  ```json
  { "message": "Book updated successfully" }
  ```
- **Respuesta si el ID no existe:**
  ```json
  { "message": "Book not found" }
  ```
- **Códigos de estado:** `200 OK` si existe, `404 Not Found` si no existe.

### 6. DELETE `/books/:id` — Eliminar un libro
- **URL de ejemplo:** `http://localhost:3000/books/3`
- **Qué hace:** elimina del `db.json` el libro con el `id` indicado.
- **Respuesta esperada (libro existente):**
  ```json
  { "message": "Book deleted successfully" }
  ```
- **Respuesta si el ID no existe:**
  ```json
  { "message": "Book not found" }
  ```
- **Códigos de estado:** `200 OK` si existe, `404 Not Found` si no existe.

---