'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import Link from 'next/link'
import { getPersonas, eliminarPersona } from '@/lib/api'
import Tabla, { type ColumnaTabla } from '@/components/ui/Tabla'
import type { Persona } from '@/types'

export default function PersonasPage() {
  const router = useRouter()
  const [personas, setPersonas] = useState<Persona[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  async function cargar() {
    setLoading(true)
    try {
      const data = await getPersonas()
      setPersonas(data)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Error cargando personas')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { cargar() }, [])

  async function handleEliminar(persona: Persona) {
    if (!confirm(`¿Desactivar a ${persona.PrimerNombre} ${persona.PrimerApellido}?`)) return
    try {
      await eliminarPersona(persona.IDPersona)
      await cargar()
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Error al eliminar')
    }
  }

  const columnas: ColumnaTabla<Persona>[] = [
    {
      encabezado: 'Documento',
      render: (p) => `${p.TipoDocumento} ${p.NumeroDocumento}`,
    },
    {
      encabezado: 'Nombre completo',
      render: (p) =>
        [p.PrimerNombre, p.SegundoNombre, p.PrimerApellido, p.SegundoApellido]
          .filter(Boolean)
          .join(' '),
    },
    {
      encabezado: 'Correo',
      render: (p) => p.CorreoElectronico,
    },
    {
      encabezado: 'Celular',
      render: (p) => p.Celular ?? '—',
    },
    {
      encabezado: 'Estado',
      render: (p) => (
        <span
          className={`inline-block px-2 py-0.5 rounded-full text-xs font-medium ${
            p.Estado
              ? 'bg-green-100 text-green-700'
              : 'bg-red-100 text-red-600'
          }`}
        >
          {p.Estado ? 'Activo' : 'Inactivo'}
        </span>
      ),
    },
  ]

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl font-bold text-slate-800">Personas</h1>
          <p className="text-sm text-slate-500 mt-1">
            {personas.length} registro{personas.length !== 1 ? 's' : ''} en total
          </p>
        </div>
        <Link
          href="/personas/crear"
          className="bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-4 py-2 rounded-lg transition-colors"
        >
          + Nueva persona
        </Link>
      </div>

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm mb-6">
          {error}
        </div>
      )}

      <Tabla
        columnas={columnas}
        datos={personas}
        keyField="IDPersona"
        cargando={loading}
        mensajeVacio="No hay personas registradas."
        onEditar={(p) => router.push(`/personas/${p.IDPersona}/editar`)}
        onEliminar={handleEliminar}
      />
    </div>
  )
}
