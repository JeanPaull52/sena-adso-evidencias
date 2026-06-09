import { NextResponse } from 'next/server'
import { supabase } from '@/lib/supabase'
import type { ActualizarPersonaDto, Persona } from '@/types'

interface Params {
  params: { id: string }
}

export async function GET(_request: Request, { params }: Params) {
  const id = Number(params.id)

  if (isNaN(id)) {
    return NextResponse.json({ error: 'ID inválido' }, { status: 400 })
  }

  const { data, error } = await supabase
    .from('Persona')
    .select(`
      *,
      Nacionalidad ( IDNacionalidad, NombreNacionalidad )
    `)
    .eq('IDPersona', id)
    .single()

  if (error) {
    const status = error.code === 'PGRST116' ? 404 : 500
    const message = status === 404 ? 'Persona no encontrada' : error.message
    return NextResponse.json({ error: message }, { status })
  }

  return NextResponse.json(data)
}

export async function PUT(request: Request, { params }: Params) {
  const id = Number(params.id)

  if (isNaN(id)) {
    return NextResponse.json({ error: 'ID inválido' }, { status: 400 })
  }

  const body: ActualizarPersonaDto = await request.json()

  if (Object.keys(body).length === 0) {
    return NextResponse.json(
      { error: 'Se debe enviar al menos un campo para actualizar' },
      { status: 400 }
    )
  }

  const { data, error } = await supabase
    .from('Persona')
    .update(body)
    .eq('IDPersona', id)
    .select()
    .single()

  if (error) {
    const status = error.code === 'PGRST116' ? 404 : 500
    const message = status === 404 ? 'Persona no encontrada' : error.message
    return NextResponse.json({ error: message }, { status })
  }

  return NextResponse.json(data as Persona)
}

export async function DELETE(_request: Request, { params }: Params) {
  const id = Number(params.id)

  if (isNaN(id)) {
    return NextResponse.json({ error: 'ID inválido' }, { status: 400 })
  }

  // Baja lógica: se marca Estado = false en lugar de eliminar el registro
  const { data, error } = await supabase
    .from('Persona')
    .update({ Estado: false })
    .eq('IDPersona', id)
    .select()
    .single()

  if (error) {
    const status = error.code === 'PGRST116' ? 404 : 500
    const message = status === 404 ? 'Persona no encontrada' : error.message
    return NextResponse.json({ error: message }, { status })
  }

  return NextResponse.json(data as Persona)
}
