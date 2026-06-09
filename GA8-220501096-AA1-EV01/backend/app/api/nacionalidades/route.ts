import { NextResponse } from 'next/server'
import { supabase } from '@/lib/supabase'
import type { Nacionalidad } from '@/types'

export async function GET() {
  const { data, error } = await supabase
    .from('Nacionalidad')
    .select('*')
    .order('NombreNacionalidad')

  if (error) {
    return NextResponse.json({ error: error.message }, { status: 500 })
  }

  return NextResponse.json(data as Nacionalidad[])
}

export async function POST(request: Request) {
  const body: Pick<Nacionalidad, 'NombreNacionalidad'> = await request.json()

  if (!body.NombreNacionalidad?.trim()) {
    return NextResponse.json(
      { error: 'NombreNacionalidad es requerido' },
      { status: 400 }
    )
  }

  const { data, error } = await supabase
    .from('Nacionalidad')
    .insert({ NombreNacionalidad: body.NombreNacionalidad.trim() })
    .select()
    .single()

  if (error) {
    return NextResponse.json({ error: error.message }, { status: 500 })
  }

  return NextResponse.json(data as Nacionalidad, { status: 201 })
}
