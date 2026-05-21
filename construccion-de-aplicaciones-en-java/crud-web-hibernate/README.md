# CRUD Web de Estudiantes con Hibernate

Aplicación web Java que implementa un CRUD (Create, Read, Update, Delete) de estudiantes utilizando **Servlets**, **JSP** y el framework **Hibernate** como capa de persistencia sobre **SQL Server**.

Este proyecto es la evolución de un CRUD previo basado en JDBC puro, migrado ahora para que toda la comunicación con la base de datos se realice mediante un ORM (Object-Relational Mapping).

---

## Objetivo

Demostrar el uso del framework Hibernate en una aplicación web Java, reemplazando la gestión manual de conexiones y consultas SQL por un mecanismo declarativo basado en anotaciones JPA (Java Persistence API).

---

## Tecnologías utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje de programación |
| Apache Tomcat | 10.1 | Servidor de aplicaciones |
| Hibernate ORM | 6.4.4.Final | Framework de persistencia |
| Jakarta Servlet API | 5.0.0 | Manejo de peticiones HTTP |
| Jakarta JSP API | 3.0.0 | Vistas dinámicas |
| SQL Server | — | Motor de base de datos |
| Maven | — | Gestión de dependencias y build |

---

## Estructura del proyecto

```
crud-web-hibernate/
├── src/
│   └── main/
│       ├── java/com/juanpablo/sena/crud/
│       │   ├── dao/
│       │   │   └── EstudianteDAO.java        # Capa de acceso a datos con Hibernate
│       │   ├── model/
│       │   │   └── Estudiante.java           # Entidad JPA
│       │   ├── servlet/
│       │   │   ├── CrearEstudianteServlet.java
│       │   │   ├── EditarEstudianteServlet.java
│       │   │   ├── EliminarEstudianteServlet.java
│       │   │   └── ListarEstudiantesServlet.java
│       │   └── util/
│       │       ├── HibernateUtil.java        # Singleton del SessionFactory
│       │       └── PruebaConexion.java       # Test aislado de conexión
│       ├── resources/
│       │   └── hibernate.cfg.xml             # Configuración de Hibernate
│       └── webapp/
│           ├── WEB-INF/jsp/                  # Vistas (lista, formulario)
│           ├── css/
│           └── index.jsp
└── pom.xml                                   # Dependencias Maven
```

---

## Base de datos

El proyecto utiliza la base de datos `ejemplo_colegio` en SQL Server. La tabla principal es:

**Tabla `estudiante`**

| Columna | Tipo | Restricciones |
|---------|------|---------------|
| `id_estudiante` | INT | PK, IDENTITY |
| `nombre` | VARCHAR(100) | NOT NULL |
| `correo` | VARCHAR(100) | NOT NULL |
| `celular` | VARCHAR(20) | — |
| `direccion` | VARCHAR(200) | — |

---

## Configuración previa

### 1. Requisitos

- JDK 17 o superior
- Apache Tomcat 10.1.x
- SQL Server con la base de datos `ejemplo_colegio` creada
- Maven

### 2. Ajustar credenciales de la base de datos

Editar `src/main/resources/hibernate.cfg.xml` y reemplazar los valores de conexión:

```xml
<property name="hibernate.connection.url">
    jdbc:sqlserver://localhost:1433;databaseName=ejemplo_colegio;encrypt=true;trustServerCertificate=true
</property>
<property name="hibernate.connection.username">tu_usuario</property>
<property name="hibernate.connection.password">tu_contraseña</property>
```

---

## Funcionalidades

- Listar todos los estudiantes
- Crear un nuevo estudiante
- Editar un estudiante existente
- Eliminar un estudiante
- Visualización en consola del SQL generado automáticamente por Hibernate

---

## Conceptos aplicados

- **ORM (Object-Relational Mapping):** mapeo automático entre clases Java y tablas SQL mediante Hibernate.
- **JPA (Java Persistence API):** anotaciones estándar (`@Entity`, `@Table`, `@Id`, `@Column`, `@GeneratedValue`) para definir el mapeo.
- **Patrón Singleton:** una única instancia de `SessionFactory` compartida durante toda la vida de la aplicación, encapsulada en `HibernateUtil`.
- **Patrón DAO (Data Access Object):** separación clara de la lógica de acceso a datos del resto de la aplicación.
- **HQL (Hibernate Query Language):** consultas orientadas a objetos en lugar de SQL nativo.
- **Transacciones:** uso de `Transaction` para garantizar atomicidad en operaciones de escritura.

---

## Autor

**Juan Pablo Quiroga**
Tecnólogo en Análisis y Desarrollo de Software — SENA (Servicio Nacional de Aprendizaje)

---
