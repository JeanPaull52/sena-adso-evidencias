import { NextResponse } from 'next/server'
import bcrypt from 'bcryptjs'
import { supabase } from '@/lib/supabase'
import type { LoginDto, UsuarioResponseDto } from '@/types'

export async function POST(request: Request) {
  const body: LoginDto = await request.json()

  if (!body.NombreUsuario?.trim() || !body.Contrasena) {
    return NextResponse.json(
      { error: 'NombreUsuario y Contrasena son requeridos' },
      { status: 400 }
    )
  }

  // Traer usuario junto con sus relaciones para armar la respuesta
  const { data: usuario, error } = await supabase
    .from('Usuario')
    .select(`
      *,
      Persona ( * ),
      Rol ( * )
    `)
    .eq('NombreUsuario', body.NombreUsuario.trim())
    .eq('Estado', true)
    .single()

  if (error || !usuario) {
    // Misma respuesta para usuario inexistente y contraseña incorrecta
    return NextResponse.json(
      { error: 'Credenciales inválidas' },
      { status: 401 }
    )
  }

  const contrasenaValida = await bcrypt.compare(
    body.Contrasena,
    usuario.ContrasenaHash
  )

  if (!contrasenaValida) {
    return NextResponse.json(
      { error: 'Credenciales inválidas' },
      { status: 401 }
    )
  }

  // Excluir hash de la respuesta
  const { ContrasenaHash: _hash, ...rest } = usuario
  const response: UsuarioResponseDto = rest

  return NextResponse.json(response)
}
