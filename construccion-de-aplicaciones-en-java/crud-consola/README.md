# CRUD de Estudiantes en Java + SQL Server

Aplicación de consola en Java que implementa las operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre una base de datos SQL Server, utilizando JDBC.

## Tecnologías

- Java 25 (Eclipse Temurin)
- SQL Server con autenticación SQL
- Driver JDBC `mssql-jdbc-13.4.0.jre11`

## Estructura

- `src/` — código fuente Java
- `lib/` — driver JDBC
- `database/` — scripts SQL para crear la BD y datos de ejemplo
- `docs/` — documentación del proceso

## Cómo ejecutar

1. Ejecuta los scripts de `database/` en orden (`01-create-database.sql`, `02-data.sql`).
2. Abre la carpeta en VS Code.
3. Agrega `lib/mssql-jdbc-13.4.0.jre11.jar` a las Referenced Libraries.
4. Configura tu contraseña de SQL Server en `src/Conexion.java` (línea con `TU_PASSWORD_AQUI`).
5. Ejecuta `Main.java`.

## Funcionalidades

- Listar todos los estudiantes
- Buscar por ID
- Insertar nuevo estudiante
- Actualizar estudiante (con verificación previa)
- Eliminar estudiante (con confirmación)

## Autor

Juan Pablo Quiroga Q.