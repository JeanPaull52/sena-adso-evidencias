'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import Link from 'next/link'
import { getNacionalidades, crearPersona } from '@/lib/api'
import PersonaForm from '@/components/forms/PersonaForm'
import type { CrearPersonaDto, Nacionalidad } from '@/types'

export default function CrearPersonaPage() {
  const router = useRouter()
  const [nacionalidades, setNacionalidades] = useState<Nacionalidad[]>([])
  const [loadingData, setLoadingData] = useState(true)
  const [errorData, setErrorData] = useState('')

  useEffect(() => {
    getNacionalidades()
      .then(setNacionalidades)
      .catch(() => setErrorData('Error cargando nacionalidades'))
      .finally(() => setLoadingData(false))
  }, [])

  async function handleSubmit(data: CrearPersonaDto) {
    await crearPersona(data)
    router.push('/personas')
  }

  if (loadingData) {
    return (
      <div className="flex justify-center py-20">
        <span className="text-slate-500 text-sm">Cargando...</span>
      </div>
    )
  }

  return (
    <div className="max-w-3xl">
      <div className="flex items-center gap-3 mb-8">
        <Link href="/personas" className="text-slate-400 hover:text-slate-600 transition-colors">
          ← Personas
        </Link>
        <span className="text-slate-300">/</span>
        <h1 className="text-2xl font-bold text-slate-800">Nueva persona</h1>
      </div>

      {errorData && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm mb-6">
          {errorData}
        </div>
      )}

      <div className="bg-white rounded-2xl shadow-sm border border-slate-200 p-8">
        <PersonaForm
          nacionalidades={nacionalidades}
          onSubmit={handleSubmit}
          submitLabel="Crear persona"
        />
      </div>
    </div>
  )
}
