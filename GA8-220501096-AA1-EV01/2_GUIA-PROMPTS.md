# Guía de Prompts — NotePlus Taller

Documentación de los prompts utilizados con **Claude Code** (Anthropic) para generar el proyecto de forma asistida por IA.

> **Herramienta usada:** Claude Code CLI — `claude-sonnet-4-6`  
> **Repositorio:** https://github.com/JeanPaull52/sena-adso-evidencias  
> **Directorio de trabajo:** `GA8-220501096-AA1-EV01/`

---

## Fase 1 — Estructura del proyecto

**Objetivo:** Crear los dos proyectos Next.js base con sus carpetas y dependencias, sin lógica de negocio.

**Prompt usado:**

> Crea un proyecto Next.js 14 con TypeScript llamado "noteplus-taller" con la siguiente estructura:
>
> - backend/ → Next.js API routes (solo el servidor)
> - frontend/ → Next.js con páginas de UI
>
> El proyecto gestiona personas usando Supabase como base de datos con estas tablas:
> - Nacionalidad (IDNacionalidad, NombreNacionalidad)
> - Persona (IDPersona, IDNacionalidad, TipoDocumento, NumeroDocumento, PrimerNombre, SegundoNombre, PrimerApellido, SegundoApellido, FechaNacimiento, TelefonoFijo, Celular, CorreoElectronico, DireccionResidencia, Estado)
> - Rol (IDRol, NombreRol, Descripcion)
> - Usuario (IDUsuario, IDRol, IDPersona, NombreUsuario, ContrasenaHash, FechaCreacion, Estado)
>
> Por ahora solo crea la estructura de carpetas y los proyectos Next.js base con sus dependencias. No escribas lógica todavía.

**Resultado obtenido:**
- `backend/` creado con `npx create-next-app@14 --typescript`
- `frontend/` creado con `npx create-next-app@14 --typescript --tailwind`
- Estructura de carpetas del dominio creada en ambos proyectos
- `types/index.ts` y `lib/supabase.ts` con placeholders
- `package.json` del backend configurado con puerto `3001`
- `.env.local` con placeholders documentados en cada proyecto

---

## Fase 2 — Backend completo

**Objetivo:** Implementar todas las API routes con lógica real conectada a Supabase.

**Prompt usado:**

> Desarrolla el backend completo en la carpeta backend/:
>
> 1. Configura lib/supabase.ts con el cliente de Supabase usando las variables de entorno NEXT_PUBLIC_SUPABASE_URL y SUPABASE_SERVICE_ROLE_KEY
>
> 2. Define todos los tipos TypeScript en types/index.ts para: Nacionalidad, Persona, Rol, Usuario, y los DTOs: CrearPersonaDto, ActualizarPersonaDto, LoginDto, RegistroDto, UsuarioResponseDto
>
> 3. Implementa las API routes completas:
>    - GET/POST /api/nacionalidades
>    - GET /api/nacionalidades/[id]
>    - GET/POST /api/personas
>    - GET/PUT/DELETE /api/personas/[id]
>    - GET /api/roles
>    - POST /api/auth/login → valida credenciales y retorna usuario
>    - POST /api/auth/registro → crea Persona + Usuario con contraseña hasheada con bcrypt
>
> 4. Configura next.config.ts para que corra en puerto 3001
>
> 5. Agrega las dependencias necesarias: @supabase/supabase-js, bcryptjs, @types/bcryptjs
>
> 6. Completa el archivo .env.local con placeholders claros para NEXT_PUBLIC_SUPABASE_URL, SUPABASE_SERVICE_ROLE_KEY y NEXT_PUBLIC_SUPABASE_ANON_KEY

**Resultado obtenido:**
- `bcryptjs` instalado con 12 rondas de salt
- `lib/supabase.ts` con validación de env vars al arranque
- `types/index.ts` con entidades + 5 DTOs
- 9 API routes implementadas con manejo de errores y códigos HTTP correctos
- DELETE de persona hace baja lógica (`Estado = false`) en vez de eliminar el registro
- Registro crea Persona primero y hace rollback si falla la creación de Usuario
- `next.config.mjs` reemplazó al `.ts` (Next.js 14 no soporta `.ts` para config)

---

## Fase 3 — Frontend completo

**Objetivo:** Implementar todas las páginas de UI con sesión, formularios y llamadas al backend.

**Prompt usado:**

> Desarrolla el frontend completo en la carpeta frontend/:
>
> 1. Configura lib/supabase.ts con el cliente usando NEXT_PUBLIC_SUPABASE_URL y NEXT_PUBLIC_SUPABASE_ANON_KEY
>
> 2. Crea los tipos TypeScript en types/index.ts iguales a los del backend (sin ContrasenaHash)
>
> 3. Implementa las páginas:
>    - app/(auth)/login/page.tsx → formulario de login con NombreUsuario y Contraseña
>    - app/(auth)/registro/page.tsx → formulario completo con datos de Persona y Usuario, dropdown de Nacionalidad y Rol
>    - app/(dashboard)/personas/page.tsx → tabla con listado de personas, botones editar y eliminar
>    - app/(dashboard)/personas/crear/page.tsx → formulario crear persona
>    - app/(dashboard)/personas/[id]/editar/page.tsx → formulario editar persona
>    - app/(dashboard)/nacionalidades/page.tsx → listado de nacionalidades
>    - app/page.tsx → redirecciona a /login
>
> 4. Crea componentes en components/:
>    - layout/Navbar.tsx → barra de navegación con nombre de usuario y botón logout
>    - ui/Tabla.tsx → componente reutilizable de tabla
>    - forms/PersonaForm.tsx → formulario reutilizable para crear/editar persona
>
> 5. Maneja la sesión del usuario con localStorage
>
> 6. El frontend consume el backend en http://localhost:3001 en desarrollo
>
> 7. Completa .env.local con los placeholders de NEXT_PUBLIC_SUPABASE_URL, NEXT_PUBLIC_SUPABASE_ANON_KEY y NEXT_PUBLIC_API_URL

**Resultado obtenido:**
- `lib/api.ts` centraliza todas las llamadas HTTP al backend
- `lib/auth.ts` maneja sesión con `localStorage` (key: `noteplus_session`)
- Dashboard layout verifica sesión en `useEffect` y redirige a `/login` si no hay sesión
- `Tabla<T>` implementada con TypeScript genérico
- `PersonaForm` reutilizable con modo crear/editar mediante `initialData`
- Formulario de registro con validación de contraseñas y confirmación

---

## Fase 4 — Correcciones CORS y builds

**Objetivo:** Corregir errores de compilación y resolver el problema de CORS que impedía las llamadas del frontend al backend.

### Corrección de builds

**Prompt usado:**

> Verifica que tanto el backend como el frontend compilan sin errores y córrelos localmente:
> 1. Instala las dependencias en backend/ con npm install
> 2. Instala las dependencias en frontend/ con npm install
> 3. Verifica que ambos proyectos compilan con npm run build
> 4. Muestra cualquier error que encuentres y corrígelo

**Errores encontrados y corregidos:**

| Archivo | Error | Solución |
|---|---|---|
| `backend/next.config.ts` | Next.js 14 no soporta `.ts` | Renombrar a `.mjs` |
| `.eslintrc.json` (ambos) | Variables `_hash`, `_nac` etc. marcadas como no usadas | Regla `varsIgnorePattern: "^_"` |
| `api/nacionalidades/route.ts` | Import `CrearPersonaDto` no usado | Eliminado |
| `components/forms/PersonaForm.tsx` | Import `ActualizarPersonaDto` no usado | Eliminado |

### Corrección de CORS

**Prompt usado:**

> El formulario de registro muestra "Error cargando datos del formulario". Los dropdowns de Nacionalidad y Rol no cargan.
>
> Verifica en el componente de registro (app/(auth)/registro/page.tsx) que las llamadas al backend usan correctamente NEXT_PUBLIC_API_URL=http://localhost:3001. Muestra el código actual donde se cargan nacionalidades y roles y corrígelo si es necesario.

**Diagnóstico:**  
El código era correcto. El problema real era que el backend no tenía cabeceras CORS. El browser en `localhost:3000` bloqueaba las peticiones a `localhost:3001` con un `TypeError: Failed to fetch` silencioso, que el `catch` del `useEffect` convertía en el mensaje genérico.

**Solución:**  
Crear `backend/middleware.ts` que intercepta todas las rutas `/api/*` y añade:
- `Access-Control-Allow-Origin: http://localhost:3000`
- `Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS`
- `Access-Control-Allow-Headers: Content-Type, Authorization`
- Respuesta `204` para preflight `OPTIONS`

---

## Fase 5 — Despliegue en Vercel

**Objetivo:** Preparar ambos proyectos para producción con archivos de configuración de Vercel y commit del código.

**Prompt usado:**

> Prepara el proyecto para despliegue en Vercel:
>
> 1. En backend/ crea un archivo vercel.json con la configuración para desplegar las API routes de Next.js en Vercel
>
> 2. En frontend/ crea un archivo vercel.json con la configuración para desplegar el frontend de Next.js en Vercel
>
> 3. Asegúrate de que ambos proyectos tienen un .gitignore que excluya .env.local y node_modules
>
> 4. Haz commit y push de todo el proyecto con el mensaje: "feat: implementación completa NotePlus Taller - backend Next.js, frontend Next.js y pruebas"

**Resultado obtenido:**
- `backend/vercel.json` y `frontend/vercel.json` creados con configuración de framework Next.js
- `.gitignore` verificado: `.env*.local` excluido en ambos proyectos
- Commit `67936c0` con 182 archivos cambiados (58 nuevos, 123 del proyecto C# anterior eliminados)
- Push a rama `main` del repositorio `JeanPaull52/sena-adso-evidencias`
- Ninguna credencial ni `.env.local` incluida en el historial de git

---

## Resumen de archivos generados por Claude Code

| Categoría | Archivos |
|---|---|
| Backend API Routes | `app/api/auth/login/route.ts`, `app/api/auth/registro/route.ts`, `app/api/nacionalidades/route.ts`, `app/api/nacionalidades/[id]/route.ts`, `app/api/personas/route.ts`, `app/api/personas/[id]/route.ts`, `app/api/roles/route.ts`, `app/api/usuarios/route.ts` |
| Backend infra | `lib/supabase.ts`, `middleware.ts`, `types/index.ts`, `next.config.mjs`, `vercel.json` |
| Frontend páginas | `app/page.tsx`, `app/(auth)/login/page.tsx`, `app/(auth)/registro/page.tsx`, `app/(dashboard)/personas/page.tsx`, `app/(dashboard)/personas/crear/page.tsx`, `app/(dashboard)/personas/[id]/editar/page.tsx`, `app/(dashboard)/nacionalidades/page.tsx` |
| Frontend layouts | `app/layout.tsx`, `app/(auth)/layout.tsx`, `app/(dashboard)/layout.tsx` |
| Frontend componentes | `components/layout/Navbar.tsx`, `components/ui/Tabla.tsx`, `components/forms/PersonaForm.tsx` |
| Frontend infra | `lib/api.ts`, `lib/auth.ts`, `lib/supabase.ts`, `types/index.ts`, `vercel.json` |
| Documentación | `README.md`, `1_PRERREQUISITOS.md`, `2_GUIA-PROMPTS.md`, `3_GUIA-SUPABASE.md`, `4_GUIA-VERCEL.md` |
