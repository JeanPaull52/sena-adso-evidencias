// ─── Entidades de base de datos ───────────────────────────────────────────────

export interface Nacionalidad {
  IDNacionalidad: number
  NombreNacionalidad: string
}

export interface Persona {
  IDPersona: number
  IDNacionalidad: number
  TipoDocumento: string
  NumeroDocumento: string
  PrimerNombre: string
  SegundoNombre?: string
  PrimerApellido: string
  SegundoApellido?: string
  FechaNacimiento: string
  TelefonoFijo?: string
  Celular?: string
  CorreoElectronico: string
  DireccionResidencia?: string
  Estado: boolean
}

export interface Rol {
  IDRol: number
  NombreRol: string
  Descripcion?: string
}

export interface Usuario {
  IDUsuario: number
  IDRol: number
  IDPersona: number
  NombreUsuario: string
  ContrasenaHash: string
  FechaCreacion: string
  Estado: boolean
}

// ─── DTOs ─────────────────────────────────────────────────────────────────────

export type CrearPersonaDto = Omit<Persona, 'IDPersona'>

export type ActualizarPersonaDto = Partial<Omit<Persona, 'IDPersona'>>

export interface LoginDto {
  NombreUsuario: string
  Contrasena: string
}

export interface RegistroDto {
  // Datos de persona
  IDNacionalidad: number
  TipoDocumento: string
  NumeroDocumento: string
  PrimerNombre: string
  SegundoNombre?: string
  PrimerApellido: string
  SegundoApellido?: string
  FechaNacimiento: string
  TelefonoFijo?: string
  Celular?: string
  CorreoElectronico: string
  DireccionResidencia?: string
  // Datos de usuario
  IDRol: number
  NombreUsuario: string
  Contrasena: string
}

export interface UsuarioResponseDto {
  IDUsuario: number
  IDRol: number
  IDPersona: number
  NombreUsuario: string
  FechaCreacion: string
  Estado: boolean
  Persona?: Persona
  Rol?: Rol
}
