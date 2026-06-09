# Guía de Configuración — Supabase

Pasos para crear el proyecto en Supabase y configurar las tablas de la base de datos.

---

## 1. Crear cuenta y proyecto

1. Ir a https://supabase.com y crear una cuenta gratuita (se puede usar cuenta de GitHub)
2. En el dashboard, hacer clic en **New project**
3. Completar los datos:
   - **Name:** `noteplus-taller`
   - **Database Password:** elegir una contraseña segura y guardarla
   - **Region:** seleccionar la más cercana (South America - São Paulo)
4. Hacer clic en **Create new project** y esperar ~2 minutos mientras se aprovisiona

---

## 2. Obtener las claves de API

Una vez creado el proyecto:

1. Ir a **Settings** (icono de engranaje en la barra lateral)
2. Seleccionar **API**
3. Copiar y guardar los siguientes valores:

| Campo en Supabase | Variable de entorno | Usar en |
|---|---|---|
| Project URL | `NEXT_PUBLIC_SUPABASE_URL` | backend y frontend |
| `anon` / `public` key | `NEXT_PUBLIC_SUPABASE_ANON_KEY` | backend y frontend |
| `service_role` key | `SUPABASE_SERVICE_ROLE_KEY` | **solo backend** |

> La clave `service_role` omite todas las políticas de Row Level Security (RLS). Nunca exponerla en el cliente/frontend.

---

## 3. Crear las tablas

Ir a **SQL Editor** en la barra lateral de Supabase y ejecutar el siguiente script en orden.

### 3.1 Tabla Nacionalidad

```sql
CREATE TABLE "Nacionalidad" (
  "IDNacionalidad" SERIAL PRIMARY KEY,
  "NombreNacionalidad" VARCHAR(100) NOT NULL UNIQUE
);
```

### 3.2 Tabla Rol

```sql
CREATE TABLE "Rol" (
  "IDRol" SERIAL PRIMARY KEY,
  "NombreRol" VARCHAR(50) NOT NULL UNIQUE,
  "Descripcion" VARCHAR(200)
);
```

### 3.3 Tabla Persona

```sql
CREATE TABLE "Persona" (
  "IDPersona"          SERIAL PRIMARY KEY,
  "IDNacionalidad"     INTEGER NOT NULL
                         REFERENCES "Nacionalidad"("IDNacionalidad"),
  "TipoDocumento"      VARCHAR(10) NOT NULL
                         CHECK ("TipoDocumento" IN ('CC','TI','CE','PA','RC','NIT')),
  "NumeroDocumento"    VARCHAR(20) NOT NULL UNIQUE,
  "PrimerNombre"       VARCHAR(50) NOT NULL,
  "SegundoNombre"      VARCHAR(50),
  "PrimerApellido"     VARCHAR(50) NOT NULL,
  "SegundoApellido"    VARCHAR(50),
  "FechaNacimiento"    DATE NOT NULL,
  "TelefonoFijo"       VARCHAR(20),
  "Celular"            VARCHAR(20),
  "CorreoElectronico"  VARCHAR(100) NOT NULL UNIQUE,
  "DireccionResidencia" VARCHAR(200),
  "Estado"             BOOLEAN NOT NULL DEFAULT TRUE
);
```

### 3.4 Tabla Usuario

```sql
CREATE TABLE "Usuario" (
  "IDUsuario"     SERIAL PRIMARY KEY,
  "IDRol"         INTEGER NOT NULL REFERENCES "Rol"("IDRol"),
  "IDPersona"     INTEGER NOT NULL REFERENCES "Persona"("IDPersona"),
  "NombreUsuario" VARCHAR(50) NOT NULL UNIQUE,
  "ContrasenaHash" VARCHAR(255) NOT NULL,
  "FechaCreacion" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  "Estado"        BOOLEAN NOT NULL DEFAULT TRUE
);
```

---

## 4. Insertar datos de prueba

```sql
-- Nacionalidades de prueba
INSERT INTO "Nacionalidad" ("NombreNacionalidad") VALUES
  ('Colombiana'),
  ('Venezolana'),
  ('Ecuatoriana'),
  ('Peruana'),
  ('Mexicana');

-- Roles del sistema
INSERT INTO "Rol" ("NombreRol", "Descripcion") VALUES
  ('Administrador', 'Acceso total al sistema'),
  ('Usuario',       'Acceso limitado al sistema');
```

> Las personas y usuarios de prueba se crean mediante el formulario de **Registro** en la aplicación, que hashea la contraseña con bcrypt antes de almacenarla.

---

## 5. Configurar Row Level Security (RLS)

Por defecto, Supabase activa RLS en todas las tablas nuevas. El backend usa la clave `service_role` que omite RLS, por lo que en desarrollo no es necesario configurar políticas.

Para producción, se recomienda activar RLS y crear políticas restrictivas. El siguiente ejemplo desactiva RLS (solo para desarrollo rápido):

```sql
-- Solo para desarrollo — no usar en producción
ALTER TABLE "Nacionalidad" DISABLE ROW LEVEL SECURITY;
ALTER TABLE "Persona"      DISABLE ROW LEVEL SECURITY;
ALTER TABLE "Rol"          DISABLE ROW LEVEL SECURITY;
ALTER TABLE "Usuario"      DISABLE ROW LEVEL SECURITY;
```

---

## 6. Verificar la estructura

Ir a **Table Editor** en Supabase y confirmar que aparecen las cuatro tablas:

```
Nacionalidad  →  IDNacionalidad, NombreNacionalidad
Persona       →  IDPersona, IDNacionalidad, TipoDocumento, ...
Rol           →  IDRol, NombreRol, Descripcion
Usuario       →  IDUsuario, IDRol, IDPersona, NombreUsuario, ContrasenaHash, ...
```

---

## 7. Diagrama de relaciones

```
Nacionalidad ──< Persona ──< Usuario
                              │
                  Rol ────────┘
```

| Relación | Tipo |
|---|---|
| Persona → Nacionalidad | Many-to-One (FK: IDNacionalidad) |
| Usuario → Persona | One-to-One (FK: IDPersona) |
| Usuario → Rol | Many-to-One (FK: IDRol) |

---

## 8. Probar la conexión

Una vez configuradas las variables de entorno en `backend/.env.local`, arrancar el backend y probar:

```bash
cd backend
npm run dev

# En otra terminal — debe devolver el JSON con las nacionalidades
curl http://localhost:3001/api/nacionalidades

# Debe devolver los dos roles
curl http://localhost:3001/api/roles
```
