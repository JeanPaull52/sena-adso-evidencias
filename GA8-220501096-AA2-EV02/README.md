# NotePlus Admin — App Android

**Autor:** Juan Pablo Quiroga
**Institución:** Servicio Nacional de Aprendizaje SENA
**Programa:** Tecnólogo en Análisis y Desarrollo de Software — Ficha 3118562
**Evidencia:** GA8-220501096-AA2-EV02

---

## Descripción

NotePlus Admin es una aplicación Android de gestión administrativa desarrollada en Kotlin. Permite a un administrador autenticarse y gestionar datos de docentes, personas, usuarios y roles almacenados localmente mediante una base de datos SQLite. La app demuestra el uso de múltiples layouts, persistencia local con Room y navegación entre pantallas con Jetpack Compose.

---

## Stack tecnológico

| Tecnología | Detalle |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose |
| Persistencia | Room (SQLite) |
| Arquitectura | MVVM (Model-View-ViewModel) |
| Navegación | Navigation Compose |
| Layouts | Column (LinearLayout), ConstraintLayout Compose |

---

## Estructura del proyecto

```
GA8-220501096-AA2-EV02/
└── app/
    └── src/main/java/com/example/noteplusadmin/
        ├── data/
        │   ├── dao/           # Interfaces DAO para acceso a datos (Room)
        │   ├── database/      # Configuración de AppDatabase (RoomDatabase)
        │   └── entities/      # Entidades/tablas de la base de datos
        ├── navigation/        # Grafo de navegación (NavGraph)
        ├── ui/
        │   ├── screens/       # Pantallas Compose de la aplicación
        │   └── theme/         # Colores, tipografía y tema Material 3
        └── viewmodel/         # ViewModels por entidad (MVVM)
```

---

## Tablas de la base de datos

### Nacionalidad
| Campo | Tipo | Descripción |
|---|---|---|
| id | Int (PK, autoincrement) | Identificador único |
| nombre | String | Nombre de la nacionalidad |

### Persona
| Campo | Tipo | Descripción |
|---|---|---|
| id | Int (PK, autoincrement) | Identificador único |
| nombres | String | Nombres de la persona |
| apellidos | String | Apellidos de la persona |
| correo | String | Correo electrónico |
| nacionalidadId | Int (FK) | Referencia a Nacionalidad |

### Rol
| Campo | Tipo | Descripción |
|---|---|---|
| id | Int (PK, autoincrement) | Identificador único |
| nombre | String | Nombre del rol (ej. Administrador) |

### Usuario
| Campo | Tipo | Descripción |
|---|---|---|
| id | Int (PK, autoincrement) | Identificador único |
| username | String | Nombre de usuario |
| password | String | Contraseña |
| rolId | Int (FK) | Referencia a Rol |
| personaId | Int (FK) | Referencia a Persona |

### Docente
| Campo | Tipo | Descripción |
|---|---|---|
| id | Int (PK, autoincrement) | Identificador único |
| especialidad | String | Área de especialidad |
| personaId | Int (FK) | Referencia a Persona |

---

## Funcionalidades

- **Login** — Autenticación con usuario y contraseña contra la base de datos local Room. Redirige al Dashboard si las credenciales son válidas.
- **Dashboard** — Pantalla principal con acceso a los módulos de gestión: Nacionalidades, Registrar Docente y Crear Persona.
- **Gestionar Nacionalidades** — Lista las nacionalidades registradas y permite agregar nuevas mediante un formulario.
- **Registrar Docente** — Formulario para registrar un docente asociado a una persona existente.
- **Crear Persona** — Formulario para registrar una persona con nombre, apellidos, correo y nacionalidad.

---

## Layouts implementados

### Linear Layout — `Column` (Compose)
Organiza los elementos de forma **vertical u horizontal** en secuencia. Los componentes se apilan uno tras otro según el eje definido. Usado en `LoginScreen`, `DashboardScreen` y formularios.

### Constraint Layout — `ConstraintLayout` (Compose)
Permite posicionar elementos **relativos entre sí** mediante restricciones (constraints), sin necesidad de anidar múltiples contenedores. Ofrece mayor control visual y mejor rendimiento en layouts complejos. Implementado en `LoginConstraintScreen`.

**Diferencia clave:** `Column`/`Row` es simple y lineal; `ConstraintLayout` permite layouts planos con posicionamiento relativo, equivalente al `ConstraintLayout` de XML en el sistema de vistas tradicional.

---

## Credenciales por defecto

La app precarga un usuario administrador al iniciar por primera vez:

| Campo | Valor |
|---|---|
| Usuario | `admin` |
| Contraseña | `admin2026#` |

---

## Cómo ejecutar el proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/juanquiroga/sena-adso-evidencias.git
   ```
2. Abrir Android Studio (Hedgehog o superior).
3. Seleccionar **File > Open** y navegar a la carpeta `GA8-220501096-AA2-EV02/`.
4. Esperar a que Gradle sincronice las dependencias.
5. Conectar un dispositivo físico o iniciar un emulador (API 26+).
6. Presionar **Run** (`Shift + F10`).

---

## Dependencias principales

| Librería | Versión |
|---|---|
| Room (runtime + KSP) | 2.6.1 |
| Compose BOM | 2024.06.00 |
| Navigation Compose | 2.7.7 |
| ConstraintLayout Compose | 1.0.1 |
| Lifecycle ViewModel Compose | 2.7.0 |
| KSP (Kotlin Symbol Processing) | 1.9.0-1.0.13 |
