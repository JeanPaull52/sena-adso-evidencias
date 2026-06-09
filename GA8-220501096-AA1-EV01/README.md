# NotePlus Taller — GA8-220501096-AA1-EV01

Taller con tablas del proyecto NotePlus con Next.js 14, TypeScript y Supabase como base de datos en la nube.

---

## Autor

**Juan Pablo Quiroga**  
Aprendiz SENA — ADSO  
Ficha: 3118562

---

## URLs de producción

| Servicio | URL |
|---|---|
| Backend (API) | https://sena-adso-evidencias.vercel.app |
| Frontend (UI) | https://noteplus-taller-web.vercel.app |

---

## Stack tecnológico

| Capa | Tecnología | Versión |
|---|---|---|
| Lenguaje | TypeScript | 5.x |
| Framework backend | Next.js (App Router, API Routes) | 14.x |
| Framework frontend | Next.js (App Router, Pages) | 14.x |
| Estilos | Tailwind CSS | 3.x |
| Base de datos | Supabase (PostgreSQL) | — |
| ORM / cliente | @supabase/supabase-js | 2.x |
| Hashing contraseñas | bcryptjs | 3.x |
| Despliegue | Vercel | — |
| Runtime | Node.js | 18+ |

---

## Estructura de carpetas

```
GA8-220501096-AA1-EV01/
│
├── backend/                          → Next.js API Routes (puerto 3001)
│   ├── app/
│   │   └── api/
│   │       ├── auth/
│   │       │   ├── login/route.ts    → POST /api/auth/login
│   │       │   └── registro/route.ts → POST /api/auth/registro
│   │       ├── nacionalidades/
│   │       │   ├── route.ts          → GET /POST /api/nacionalidades
│   │       │   └── [id]/route.ts     → GET /api/nacionalidades/:id
│   │       ├── personas/
│   │       │   ├── route.ts          → GET /POST /api/personas
│   │       │   └── [id]/route.ts     → GET /PUT /DELETE /api/personas/:id
│   │       ├── roles/route.ts        → GET /api/roles
│   │       └── usuarios/route.ts     → GET /api/usuarios
│   ├── lib/
│   │   └── supabase.ts               → cliente Supabase (service role)
│   ├── middleware.ts                 → cabeceras CORS para todas las rutas /api/*
│   ├── types/index.ts                → interfaces TypeScript del dominio
│   └── vercel.json                   → configuración de despliegue
│
├── frontend/                         → Next.js UI (puerto 3000)
│   ├── app/
│   │   ├── (auth)/
│   │   │   ├── login/page.tsx        → /login
│   │   │   └── registro/page.tsx     → /registro
│   │   └── (dashboard)/
│   │       ├── personas/
│   │       │   ├── page.tsx          → /personas (listado)
│   │       │   ├── crear/page.tsx    → /personas/crear
│   │       │   └── [id]/editar/      → /personas/:id/editar
│   │       └── nacionalidades/page.tsx → /nacionalidades
│   ├── components/
│   │   ├── forms/PersonaForm.tsx     → formulario crear/editar reutilizable
│   │   ├── layout/Navbar.tsx         → barra de navegación con logout
│   │   └── ui/Tabla.tsx              → tabla genérica con acciones
│   ├── lib/
│   │   ├── api.ts                    → cliente HTTP hacia el backend
│   │   ├── auth.ts                   → sesión en localStorage
│   │   └── supabase.ts               → cliente Supabase (anon key)
│   ├── types/index.ts                → interfaces TypeScript
│   └── vercel.json                   → configuración de despliegue
│
├── README.md                         → este archivo
├── 1_PRERREQUISITOS.md
├── 2_GUIA-PROMPTS.md
├── 3_GUIA-SUPABASE.md
└── 4_GUIA-VERCEL.md
```

---

## Arranque en desarrollo

```bash
# Terminal 1 — backend en puerto 3001
cd backend
npm install
npm run dev

# Terminal 2 — frontend en puerto 3000
cd frontend
npm install
npm run dev
```

Abrir http://localhost:3000 en el navegador.

---

## Documentación adicional

| Archivo | Contenido |
|---|---|
| `1_PRERREQUISITOS.md` | Requisitos e instalación del entorno local |
| `2_GUIA-PROMPTS.md` | Prompts usados con Claude Code para generar el proyecto |
| `3_GUIA-SUPABASE.md` | Configuración de Supabase y SQL de las tablas |
| `4_GUIA-VERCEL.md` | Despliegue en Vercel paso a paso |
