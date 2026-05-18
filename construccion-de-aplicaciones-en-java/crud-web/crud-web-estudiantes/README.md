# CRUD Web de Estudiantes con Servlets y JSP

Aplicación web para la gestión de información de estudiantes, desarrollada con Java Servlets, JSP y SQL Server. Permite crear, consultar, actualizar y eliminar registros (CRUD completo) desde una interfaz web.

## Tecnologías utilizadas

- **Java 17** (compatible con JDK 21+)
- **Maven 3.9** para gestión de dependencias y empaquetado
- **Jakarta Servlets 5.0** para los controladores
- **JSP 3.0** para las vistas
- **Apache Tomcat 10.1** como servidor de aplicaciones
- **SQL Server 2022** como base de datos
- **mssql-jdbc 12.8.1** como driver JDBC
- **HTML5 + CSS3** para la presentación

## Características

- Listar todos los estudiantes en una tabla con información completa
- Registrar nuevos estudiantes mediante formulario validado
- Editar la información de estudiantes existentes
- Eliminar estudiantes con confirmación previa para prevenir borrados accidentales
- Implementación del patrón **MVC** (Model-View-Controller)
- Aplicación del patrón **POST-Redirect-GET** para evitar envíos duplicados de formularios
- Interfaz con estilos CSS personalizados

## Estructura del proyecto

```
crud-web-estudiantes/
├── pom.xml                                       (configuración Maven)
├── .gitignore
└── src/main/
    ├── java/com/juanpablo/sena/crud/
    │   ├── dao/
    │   │   └── EstudianteDAO.java                (acceso a datos)
    │   ├── model/
    │   │   └── Estudiante.java                   (entidad)
    │   ├── servlet/
    │   │   ├── ListarEstudiantesServlet.java     (READ)
    │   │   ├── CrearEstudianteServlet.java       (CREATE)
    │   │   ├── EditarEstudianteServlet.java      (UPDATE)
    │   │   └── EliminarEstudianteServlet.java    (DELETE)
    │   └── util/
    │       └── Conexion.java                     (conexión a BD)
    └── webapp/
        ├── css/
        │   └── styles.css                        (estilos)
        ├── WEB-INF/
        │   ├── jsp/
        │   │   ├── lista.jsp                     (vista del listado)
        │   │   └── formulario.jsp                (vista del formulario)
        │   └── web.xml                           (configuración web)
        └── index.jsp                             (página de inicio)
```

## Requisitos previos

- JDK 17 o superior (probado con JDK 21 LTS)
- Apache Tomcat 10.1
- SQL Server 2019 o superior con autenticación SQL habilitada
- Maven 3.9 o superior
- Cliente para ejecutar scripts SQL (SQL Server Management Studio, Azure Data Studio, etc.)

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/JeanPaull52/sena-adso-evidencias.git
   ```

2. Navegar a la carpeta del proyecto:
   ```bash
   cd sena-adso-evidencias/construccion-de-aplicaciones-en-java/crud-web/crud-web-estudiantes
   ```

3. Construir el proyecto con Maven:
   ```bash
   mvn clean package
   ```

   Esto generará el archivo `target/crud-web-estudiantes.war`.

## Configuración de la base de datos

1. Crear la base de datos ejecutando el script ubicado en la primera evidencia:
   ```
   ../../crud-consola/database/01-create-database.sql
   ```

2. Cargar datos de ejemplo:
   ```
   ../../crud-consola/database/02-data.sql
   ```

3. Configurar las credenciales de conexión en `Conexion.java`:
   ```java
   private static final String USUARIO = "tu_usuario";
   private static final String PASSWORD = "tu_password";
   ```

   Reemplazar `tu_usuario` y `tu_password` por las credenciales reales de SQL Server.

## Ejecución

1. Iniciar Apache Tomcat 10.1.

2. Desplegar el archivo `target/crud-web-estudiantes.war` en Tomcat. Puede hacerse:
   - Copiando el archivo a `Tomcat/webapps/`, o
   - Usando la interfaz **Manager App** de Tomcat (`http://localhost:8080/manager`), o
   - Desde un IDE con plugin de servidor.

3. Acceder a la aplicación en el navegador:
   ```
   http://localhost:8080/crud-web-estudiantes/
   ```

## Uso

Desde la página de inicio se puede acceder a:

- **Ver lista de estudiantes**: muestra una tabla con todos los registros y permite editar o eliminar cada uno.
- **Registrar nuevo estudiante**: formulario para crear un nuevo registro.

Las operaciones de edición y eliminación están accesibles desde la columna "Acciones" del listado.

## Autor

**Juan Pablo Ramírez**
Aprendiz SENA - Análisis y Desarrollo de Software (ADSO)
Ficha: 3118562
GitHub: [@JeanPaull52](https://github.com/JeanPaull52)

## Licencia

Este proyecto se desarrolla con fines académicos como parte del programa de formación del SENA.
