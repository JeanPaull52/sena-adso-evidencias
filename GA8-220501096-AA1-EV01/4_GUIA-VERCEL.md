# Guía de Despliegue — Vercel

Pasos para desplegar el backend y el frontend en Vercel de forma independiente.

> El proyecto se despliega como **dos proyectos separados** en Vercel:
> - Uno para `backend/` (API Routes)
> - Uno para `frontend/` (UI)

---

## Requisitos previos

- Cuenta en Vercel: https://vercel.com
- Proyecto en Supabase con las tablas creadas (ver `3_GUIA-SUPABASE.md`)
- Repositorio en GitHub: https://github.com/JeanPaull52/sena-adso-evidencias

---

## Parte 1 — Desplegar el Backend

### 1.1 Importar el repositorio

1. Ir a https://vercel.com/new
2. Seleccionar **Import Git Repository**
3. Buscar y seleccionar `JeanPaull52/sena-adso-evidencias`
4. Hacer clic en **Import**

### 1.2 Configurar el proyecto backend

En la pantalla de configuración **antes de hacer deploy**:

| Campo | Valor |
|---|---|
| **Project Name** | `noteplus-taller-api` |
| **Framework Preset** | Next.js (se detecta automáticamente) |
| **Root Directory** | `GA8-220501096-AA1-EV01/backend` |
| **Build Command** | `npm run build` (valor por defecto) |
| **Output Directory** | `.next` (valor por defecto) |
| **Install Command** | `npm install` (valor por defecto) |

> El campo **Root Directory** es clave. Hacer clic en **Edit** junto a él e ingresar la ruta exacta.

### 1.3 Variables de entorno del backend

Expandir la sección **Environment Variables** y agregar:

| Variable | Valor | Dónde obtenerla |
|---|---|---|
| `NEXT_PUBLIC_SUPABASE_URL` | `https://xxxx.supabase.co` | Supabase → Settings → API → Project URL |
| `NEXT_PUBLIC_SUPABASE_ANON_KEY` | `eyJhbGci...` | Supabase → Settings → API → anon key |
| `SUPABASE_SERVICE_ROLE_KEY` | `eyJhbGci...` | Supabase → Settings → API → service_role key |
| `FRONTEND_URL` | `https://noteplus-taller-web.vercel.app` | URL del frontend (completar después del deploy del frontend) |

> Si el frontend aún no está desplegado, ingresar `http://localhost:3000` como valor temporal de `FRONTEND_URL` y actualizarlo luego.

### 1.4 Hacer deploy

Hacer clic en **Deploy**. El build tarda ~1-2 minutos.

Al finalizar, Vercel muestra la URL del backend. Ejemplo:

```
https://sena-adso-evidencias.vercel.app
```

### 1.5 Verificar el backend desplegado

```bash
# Debe responder con el JSON de roles
curl https://sena-adso-evidencias.vercel.app/api/roles

# Debe responder con el JSON de nacionalidades
curl https://sena-adso-evidencias.vercel.app/api/nacionalidades
```

---

## Parte 2 — Desplegar el Frontend

### 2.1 Crear segundo proyecto en Vercel

1. Ir a https://vercel.com/new
2. Seleccionar el mismo repositorio `JeanPaull52/sena-adso-evidencias`
3. Hacer clic en **Import**

### 2.2 Configurar el proyecto frontend

| Campo | Valor |
|---|---|
| **Project Name** | `noteplus-taller-web` |
| **Framework Preset** | Next.js |
| **Root Directory** | `GA8-220501096-AA1-EV01/frontend` |
| **Build Command** | `npm run build` |
| **Output Directory** | `.next` |

### 2.3 Variables de entorno del frontend

| Variable | Valor |
|---|---|
| `NEXT_PUBLIC_SUPABASE_URL` | `https://xxxx.supabase.co` |
| `NEXT_PUBLIC_SUPABASE_ANON_KEY` | `eyJhbGci...` (anon key) |
| `NEXT_PUBLIC_API_URL` | `https://sena-adso-evidencias.vercel.app` |

> `NEXT_PUBLIC_API_URL` debe apuntar a la URL del **backend ya desplegado** en el paso anterior.

### 2.4 Hacer deploy

Hacer clic en **Deploy**.

Al finalizar:

```
https://noteplus-taller-web.vercel.app
```

---

## Parte 3 — Actualizar CORS en el backend

Una vez desplegado el frontend, actualizar la variable `FRONTEND_URL` en el proyecto backend de Vercel para que el middleware de CORS permita el dominio de producción.

1. Ir al dashboard de Vercel → proyecto `noteplus-taller-api`
2. **Settings** → **Environment Variables**
3. Editar `FRONTEND_URL` y cambiar el valor a:
   ```
   https://noteplus-taller-web.vercel.app
   ```
4. Hacer clic en **Save**
5. Ir a **Deployments** → hacer clic en los tres puntos del último deployment → **Redeploy**

---

## Parte 4 — Verificar el despliegue completo

### Prueba de endpoints del backend

```bash
# Listado de nacionalidades
curl https://sena-adso-evidencias.vercel.app/api/nacionalidades

# Listado de roles
curl https://sena-adso-evidencias.vercel.app/api/roles

# Login con credenciales incorrectas — debe devolver 401
curl -X POST https://sena-adso-evidencias.vercel.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"NombreUsuario":"test","Contrasena":"wrong"}'
```

### Prueba del frontend

1. Abrir https://noteplus-taller-web.vercel.app
2. Debe redirigir automáticamente a `/login`
3. Ir a `/registro` y crear una cuenta de prueba
4. Iniciar sesión y verificar que cargan los listados de personas y nacionalidades

---

## Referencia rápida — Variables de entorno

### Backend (`GA8-220501096-AA1-EV01/backend`)

```env
NEXT_PUBLIC_SUPABASE_URL=https://xxxx.supabase.co
NEXT_PUBLIC_SUPABASE_ANON_KEY=eyJhbGci...
SUPABASE_SERVICE_ROLE_KEY=eyJhbGci...
FRONTEND_URL=https://noteplus-taller-web.vercel.app
```

### Frontend (`GA8-220501096-AA1-EV01/frontend`)

```env
NEXT_PUBLIC_SUPABASE_URL=https://xxxx.supabase.co
NEXT_PUBLIC_SUPABASE_ANON_KEY=eyJhbGci...
NEXT_PUBLIC_API_URL=https://sena-adso-evidencias.vercel.app
```

---

## Redeployment automático

Vercel redeploye automáticamente ambos proyectos cada vez que se hace push a la rama `main` del repositorio. No se requiere ninguna acción manual después del despliegue inicial.

```bash
# Cualquier cambio en el código y push activa un nuevo deploy
git add .
git commit -m "fix: descripción del cambio"
git push origin main
```
