import type {
  ActualizarPersonaDto,
  CrearPersonaDto,
  LoginDto,
  Nacionalidad,
  Persona,
  RegistroDto,
  Rol,
  UsuarioResponseDto,
} from '@/types'

const BASE_URL =
  process.env.NEXT_PUBLIC_API_URL ?? 'http://localhost:3001'

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const res = await fetch(`${BASE_URL}${path}`, {
    headers: { 'Content-Type': 'application/json', ...options?.headers },
    ...options,
  })
  const json = await res.json()
  if (!res.ok) {
    throw new Error((json as { error?: string }).error ?? 'Error en la solicitud')
  }
  return json as T
}

// ─── Nacionalidades ───────────────────────────────────────────────────────────

export const getNacionalidades = () =>
  request<Nacionalidad[]>('/api/nacionalidades')

export const getNacionalidad = (id: number) =>
  request<Nacionalidad>(`/api/nacionalidades/${id}`)

export const crearNacionalidad = (nombre: string) =>
  request<Nacionalidad>('/api/nacionalidades', {
    method: 'POST',
    body: JSON.stringify({ NombreNacionalidad: nombre }),
  })

// ─── Personas ─────────────────────────────────────────────────────────────────

export const getPersonas = () =>
  request<Persona[]>('/api/personas')

export const getPersona = (id: number) =>
  request<Persona>(`/api/personas/${id}`)

export const crearPersona = (dto: CrearPersonaDto) =>
  request<Persona>('/api/personas', {
    method: 'POST',
    body: JSON.stringify(dto),
  })

export const actualizarPersona = (id: number, dto: ActualizarPersonaDto) =>
  request<Persona>(`/api/personas/${id}`, {
    method: 'PUT',
    body: JSON.stringify(dto),
  })

export const eliminarPersona = (id: number) =>
  request<Persona>(`/api/personas/${id}`, { method: 'DELETE' })

// ─── Roles ────────────────────────────────────────────────────────────────────

export const getRoles = () =>
  request<Rol[]>('/api/roles')

// ─── Auth ─────────────────────────────────────────────────────────────────────

export const login = (dto: LoginDto) =>
  request<UsuarioResponseDto>('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify(dto),
  })

export const registro = (dto: RegistroDto) =>
  request<UsuarioResponseDto>('/api/auth/registro', {
    method: 'POST',
    body: JSON.stringify(dto),
  })
