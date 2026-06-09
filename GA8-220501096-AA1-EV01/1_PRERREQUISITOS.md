# Prerrequisitos — NotePlus Taller

Requisitos para correr el proyecto localmente y desplegarlo en producción.

---

## 1. Herramientas locales

### Node.js 18 o superior

El proyecto usa la API de `fetch` nativa y sintaxis ES2022. Versión mínima: **Node.js 18 LTS**.

Verificar versión instalada:

```bash
node --version   # debe mostrar v18.x.x o superior
npm --version    # viene incluido con Node
```

Descargar desde: https://nodejs.org

---

### npm (incluido con Node.js)

Se usa npm como gestor de paquetes. No se requiere yarn ni pnpm.

```bash
npm --version   # 9.x o superior
```

---

### Git

Para clonar el repositorio y hacer seguimiento de cambios.

```bash
git --version
```

Descargar desde: https://git-scm.com

---

## 2. Servicios en la nube

### Cuenta en Supabase

Supabase es la base de datos PostgreSQL administrada que usa el proyecto.

1. Crear cuenta gratuita en https://supabase.com
2. Crear un nuevo proyecto
3. Anotar las tres claves que necesita el proyecto (ver sección Variables de entorno)

El plan gratuito de Supabase es suficiente para desarrollo y pruebas.

---

### Cuenta en Vercel

Para desplegar backend y frontend en producción.

1. Crear cuenta gratuita en https://vercel.com (se puede usar la cuenta de GitHub)
2. El plan Hobby (gratuito) permite múltiples proyectos

---

## 3. Clonar el repositorio

```bash
git clone https://github.com/JeanPaull52/sena-adso-evidencias.git
cd sena-adso-evidencias/GA8-220501096-AA1-EV01
```

---

## 4. Variables de entorno

Cada proyecto necesita su propio archivo `.env.local`. Estos archivos **no se suben al repositorio** (están en `.gitignore`).

### backend/.env.local

```env
# URL del proyecto Supabase
# Supabase Dashboard → Settings → API → Project URL
NEXT_PUBLIC_SUPABASE_URL=https://xxxxxxxxxxxxxxxxxxxx.supabase.co

# Clave pública anon (segura para exponer en cliente)
# Supabase Dashboard → Settings → API → anon / public
NEXT_PUBLIC_SUPABASE_ANON_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Clave de servicio — SOLO para el backend, NUNCA en el frontend
# Supabase Dashboard → Settings → API → service_role
SUPABASE_SERVICE_ROLE_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# URL del frontend (para cabeceras CORS)
FRONTEND_URL=http://localhost:3000
```

### frontend/.env.local

```env
# URL del proyecto Supabase
NEXT_PUBLIC_SUPABASE_URL=https://xxxxxxxxxxxxxxxxxxxx.supabase.co

# Clave pública anon
NEXT_PUBLIC_SUPABASE_ANON_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# URL del backend Next.js
# En desarrollo: http://localhost:3001
# En producción: URL del backend en Vercel
NEXT_PUBLIC_API_URL=http://localhost:3001
```

---

## 5. Instalar dependencias

```bash
# Instalar dependencias del backend
cd backend
npm install

# Instalar dependencias del frontend
cd ../frontend
npm install
```

---

## 6. Resumen de dependencias principales

### Backend

| Paquete | Versión | Uso |
|---|---|---|
| `next` | 14.x | Framework API Routes |
| `@supabase/supabase-js` | 2.x | Cliente PostgreSQL |
| `bcryptjs` | 3.x | Hash de contraseñas |
| `typescript` | 5.x | Tipado estático |

### Frontend

| Paquete | Versión | Uso |
|---|---|---|
| `next` | 14.x | Framework React |
| `tailwindcss` | 3.x | Estilos utilitarios |
| `@supabase/supabase-js` | 2.x | Cliente PostgreSQL |
| `typescript` | 5.x | Tipado estático |

---

## 7. Verificar que todo funciona

```bash
# Verificar build del backend (sin errores)
cd backend && npm run build

# Verificar build del frontend (sin errores)
cd ../frontend && npm run build
```

Ambos comandos deben terminar con `✓ Generating static pages` sin errores de TypeScript ni ESLint.
