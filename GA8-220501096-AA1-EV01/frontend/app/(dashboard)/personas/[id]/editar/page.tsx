'use client'

import { useEffect, useState } from 'react'
import { useParams, useRouter } from 'next/navigation'
import Link from 'next/link'
import { getPersona, getNacionalidades, actualizarPersona } from '@/lib/api'
import PersonaForm from '@/components/forms/PersonaForm'
import type { ActualizarPersonaDto, Nacionalidad, Persona } from '@/types'

export default function EditarPersonaPage() {
  const params = useParams()
  const router = useRouter()
  const id = Number(params.id)

  const [persona, setPersona] = useState<Persona | null>(null)
  const [nacionalidades, setNacionalidades] = useState<Nacionalidad[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    Promise.all([getPersona(id), getNacionalidades()])
      .then(([p, nacs]) => {
        setPersona(p)
        setNacionalidades(nacs)
      })
      .catch((err) => setError(err instanceof Error ? err.message : 'Error cargando datos'))
      .finally(() => setLoading(false))
  }, [id])

  async function handleSubmit(data: ActualizarPersonaDto) {
    await actualizarPersona(id, data)
    router.push('/personas')
  }

  if (loading) {
    return (
      <div className="flex justify-center py-20">
        <span className="text-slate-500 text-sm">Cargando...</span>
      </div>
    )
  }

  if (error || !persona) {
    return (
      <div className="max-w-3xl">
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
          {error || 'Persona no encontrada'}
        </div>
        <Link href="/personas" className="mt-4 inline-block text-sm text-blue-600 hover:underline">
          ← Volver a personas
        </Link>
      </div>
    )
  }

  // Excluir IDPersona y el objeto anidado Nacionalidad del initialData
  const { IDPersona: _id, Nacionalidad: _nac, ...initialData } = persona

  return (
    <div className="max-w-3xl">
      <div className="flex items-center gap-3 mb-8">
        <Link href="/personas" className="text-slate-400 hover:text-slate-600 transition-colors">
          ← Personas
        </Link>
        <span className="text-slate-300">/</span>
        <h1 className="text-2xl font-bold text-slate-800">
          Editar — {persona.PrimerNombre} {persona.PrimerApellido}
        </h1>
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-slate-200 p-8">
        <PersonaForm
          initialData={initialData}
          nacionalidades={nacionalidades}
          onSubmit={handleSubmit}
          submitLabel="Guardar cambios"
        />
      </div>
    </div>
  )
}
