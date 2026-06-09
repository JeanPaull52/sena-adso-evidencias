import { NextResponse } from 'next/server'
import { supabase } from '@/lib/supabase'
import type { Nacionalidad } from '@/types'

interface Params {
  params: { id: string }
}

export async function GET(_request: Request, { params }: Params) {
  const id = Number(params.id)

  if (isNaN(id)) {
    return NextResponse.json({ error: 'ID inválido' }, { status: 400 })
  }

  const { data, error } = await supabase
    .from('Nacionalidad')
    .select('*')
    .eq('IDNacionalidad', id)
    .single()

  if (error) {
    const status = error.code === 'PGRST116' ? 404 : 500
    const message = status === 404 ? 'Nacionalidad no encontrada' : error.message
    return NextResponse.json({ error: message }, { status })
  }

  return NextResponse.json(data as Nacionalidad)
}
