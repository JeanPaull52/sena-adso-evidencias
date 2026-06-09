# noteplus-taller

Proyecto Next.js 14 + TypeScript + Supabase para gestión de personas.

## Estructura

```
GA8-220501096-AA1-EV01/
├── backend/    → Next.js API Routes (puerto 3001)
└── frontend/   → Next.js UI con Tailwind CSS (puerto 3000)
```

## Configuración

1. Copia `.env.local.example` → `.env.local` en cada carpeta y completa los valores de Supabase.
2. `backend/.env.local` necesita `SUPABASE_SERVICE_ROLE_KEY` (clave de servicio, solo servidor).
3. `frontend/.env.local` necesita `NEXT_PUBLIC_SUPABASE_ANON_KEY` (clave pública).

## Arranque en desarrollo

```bash
# Terminal 1 — backend
cd backend && npm run dev   # http://localhost:3001

# Terminal 2 — frontend
cd frontend && npm run dev  # http://localhost:3000
```

## Tablas Supabase

| Tabla         | Columnas clave                                      |
|---------------|-----------------------------------------------------|
| Nacionalidad  | IDNacionalidad, NombreNacionalidad                  |
| Persona       | IDPersona, IDNacionalidad, TipoDocumento, ...       |
| Rol           | IDRol, NombreRol, Descripcion                       |
| Usuario       | IDUsuario, IDRol, IDPersona, NombreUsuario, ...     |
