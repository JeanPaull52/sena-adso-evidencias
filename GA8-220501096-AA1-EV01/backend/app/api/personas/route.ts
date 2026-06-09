import { NextResponse } from 'next/server'
import { supabase } from '@/lib/supabase'
import type { CrearPersonaDto, Persona } from '@/types'

export async function GET() {
  const { data, error } = await supabase
    .from('Persona')
    .select(`
      *,
      Nacionalidad ( IDNacionalidad, NombreNacionalidad )
    `)
    .order('PrimerApellido')

  if (error) {
    return NextResponse.json({ error: error.message }, { status: 500 })
  }

  return NextResponse.json(data)
}

export async function POST(request: Request) {
  const body: CrearPersonaDto = await request.json()

  const camposRequeridos: (keyof CrearPersonaDto)[] = [
    'IDNacionalidad',
    'TipoDocumento',
    'NumeroDocumento',
    'PrimerNombre',
    'PrimerApellido',
    'FechaNacimiento',
    'CorreoElectronico',
  ]

  for (const campo of camposRequeridos) {
    if (body[campo] === undefined || body[campo] === null || body[campo] === '') {
      return NextResponse.json(
        { error: `El campo ${campo} es requerido` },
        { status: 400 }
      )
    }
  }

  const { data, error } = await supabase
    .from('Persona')
    .insert({ ...body, Estado: body.Estado ?? true })
    .select()
    .single()

  if (error) {
    // Violación de unicidad en NumeroDocumento
    if (error.code === '23505') {
      return NextResponse.json(
        { error: 'Ya existe una persona con ese número de documento' },
        { status: 409 }
      )
    }
    return NextResponse.json({ error: error.message }, { status: 500 })
  }

  return NextResponse.json(data as Persona, { status: 201 })
}
