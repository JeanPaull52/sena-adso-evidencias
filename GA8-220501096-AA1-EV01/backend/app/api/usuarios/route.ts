import { NextResponse } from 'next/server'
import { supabase } from '@/lib/supabase'

export async function GET() {
  const { data, error } = await supabase
    .from('Usuario')
    .select(`
      IDUsuario,
      IDRol,
      IDPersona,
      NombreUsuario,
      FechaCreacion,
      Estado,
      Persona ( * ),
      Rol ( * )
    `)
    .order('NombreUsuario')

  if (error) {
    return NextResponse.json({ error: error.message }, { status: 500 })
  }

  return NextResponse.json(data)
}
