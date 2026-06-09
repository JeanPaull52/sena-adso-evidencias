import { NextResponse } from 'next/server'
import bcrypt from 'bcryptjs'
import { supabase } from '@/lib/supabase'
import type { Persona, RegistroDto, UsuarioResponseDto } from '@/types'

const BCRYPT_ROUNDS = 12

export async function POST(request: Request) {
  const body: RegistroDto = await request.json()

  // Validaciones básicas
  const camposRequeridos: (keyof RegistroDto)[] = [
    'IDNacionalidad',
    'TipoDocumento',
    'NumeroDocumento',
    'PrimerNombre',
    'PrimerApellido',
    'FechaNacimiento',
    'CorreoElectronico',
    'IDRol',
    'NombreUsuario',
    'Contrasena',
  ]

  for (const campo of camposRequeridos) {
    if (body[campo] === undefined || body[campo] === null || body[campo] === '') {
      return NextResponse.json(
        { error: `El campo ${campo} es requerido` },
        { status: 400 }
      )
    }
  }

  if (body.Contrasena.length < 8) {
    return NextResponse.json(
      { error: 'La contraseña debe tener al menos 8 caracteres' },
      { status: 400 }
    )
  }

  // Verificar que el NombreUsuario no exista
  const { data: usuarioExistente } = await supabase
    .from('Usuario')
    .select('IDUsuario')
    .eq('NombreUsuario', body.NombreUsuario.trim())
    .single()

  if (usuarioExistente) {
    return NextResponse.json(
      { error: 'El nombre de usuario ya está en uso' },
      { status: 409 }
    )
  }

  // 1. Crear Persona
  const personaPayload: Omit<Persona, 'IDPersona'> = {
    IDNacionalidad: body.IDNacionalidad,
    TipoDocumento: body.TipoDocumento,
    NumeroDocumento: body.NumeroDocumento,
    PrimerNombre: body.PrimerNombre,
    SegundoNombre: body.SegundoNombre,
    PrimerApellido: body.PrimerApellido,
    SegundoApellido: body.SegundoApellido,
    FechaNacimiento: body.FechaNacimiento,
    TelefonoFijo: body.TelefonoFijo,
    Celular: body.Celular,
    CorreoElectronico: body.CorreoElectronico,
    DireccionResidencia: body.DireccionResidencia,
    Estado: true,
  }

  const { data: persona, error: errorPersona } = await supabase
    .from('Persona')
    .insert(personaPayload)
    .select()
    .single()

  if (errorPersona) {
    if (errorPersona.code === '23505') {
      return NextResponse.json(
        { error: 'Ya existe una persona con ese número de documento o correo' },
        { status: 409 }
      )
    }
    return NextResponse.json({ error: errorPersona.message }, { status: 500 })
  }

  // 2. Crear Usuario con contraseña hasheada
  const contrasenaHash = await bcrypt.hash(body.Contrasena, BCRYPT_ROUNDS)

  const { data: usuario, error: errorUsuario } = await supabase
    .from('Usuario')
    .insert({
      IDRol: body.IDRol,
      IDPersona: persona.IDPersona,
      NombreUsuario: body.NombreUsuario.trim(),
      ContrasenaHash: contrasenaHash,
      FechaCreacion: new Date().toISOString(),
      Estado: true,
    })
    .select(`
      *,
      Persona ( * ),
      Rol ( * )
    `)
    .single()

  if (errorUsuario) {
    // Revertir la persona creada si el usuario falla
    await supabase
      .from('Persona')
      .delete()
      .eq('IDPersona', persona.IDPersona)

    return NextResponse.json({ error: errorUsuario.message }, { status: 500 })
  }

  const { ContrasenaHash: _hash, ...rest } = usuario
  const response: UsuarioResponseDto = rest

  return NextResponse.json(response, { status: 201 })
}
