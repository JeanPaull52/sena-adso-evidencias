'use client'

import { useEffect, useState } from 'react'
import { getNacionalidades, crearNacionalidad } from '@/lib/api'
import Tabla, { type ColumnaTabla } from '@/components/ui/Tabla'
import type { Nacionalidad } from '@/types'

export default function NacionalidadesPage() {
  const [nacionalidades, setNacionalidades] = useState<Nacionalidad[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [nuevoNombre, setNuevoNombre] = useState('')
  const [creando, setCreando] = useState(false)
  const [mostrarForm, setMostrarForm] = useState(false)

  async function cargar() {
    setLoading(true)
    try {
      const data = await getNacionalidades()
      setNacionalidades(data)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Error cargando nacionalidades')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { cargar() }, [])

  async function handleCrear(e: React.FormEvent) {
    e.preventDefault()
    if (!nuevoNombre.trim()) return
    setCreando(true)
    setError('')
    try {
      await crearNacionalidad(nuevoNombre.trim())
      setNuevoNombre('')
      setMostrarForm(false)
      await cargar()
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Error creando nacionalidad')
    } finally {
      setCreando(false)
    }
  }

  const columnas: ColumnaTabla<Nacionalidad>[] = [
    { encabezado: 'ID', render: (n) => n.IDNacionalidad },
    { encabezado: 'Nombre', render: (n) => n.NombreNacionalidad },
  ]

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl font-bold text-slate-800">Nacionalidades</h1>
          <p className="text-sm text-slate-500 mt-1">
            {nacionalidades.length} registro{nacionalidades.length !== 1 ? 's' : ''} en total
          </p>
        </div>
        <button
          onClick={() => setMostrarForm((v) => !v)}
          className="bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-4 py-2 rounded-lg transition-colors"
        >
          {mostrarForm ? 'Cancelar' : '+ Nueva nacionalidad'}
        </button>
      </div>

      {mostrarForm && (
        <form
          onSubmit={handleCrear}
          className="bg-white border border-slate-200 rounded-xl p-5 mb-6 flex gap-3 items-end"
        >
          <div className="flex-1">
            <label className="block text-sm font-medium text-slate-700 mb-1">
              Nombre de la nacionalidad
            </label>
            <input
              value={nuevoNombre}
              onChange={(e) => setNuevoNombre(e.target.value)}
              placeholder="Ej: Colombiana"
              required
              className="w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
            />
          </div>
          <button
            type="submit"
            disabled={creando}
            className="bg-blue-600 hover:bg-blue-700 disabled:bg-blue-300 text-white text-sm font-medium px-4 py-2 rounded-lg transition-colors"
          >
            {creando ? 'Guardando...' : 'Guardar'}
          </button>
        </form>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm mb-6">
          {error}
        </div>
      )}

      <Tabla
        columnas={columnas}
        datos={nacionalidades}
        keyField="IDNacionalidad"
        cargando={loading}
        mensajeVacio="No hay nacionalidades registradas."
      />
    </div>
  )
}
