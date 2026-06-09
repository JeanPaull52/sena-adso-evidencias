import { NextResponse } from 'next/server'
import { supabase } from '@/lib/supabase'
import type { Rol } from '@/types'

export async function GET() {
  const { data, error } = await supabase
    .from('Rol')
    .select('*')
    .order('NombreRol')

  if (error) {
    return NextResponse.json({ error: error.message }, { status: 500 })
  }

  return NextResponse.json(data as Rol[])
}
