'use client'

import { useState } from 'react'
import type { CrearPersonaDto, Nacionalidad } from '@/types'

const TIPOS_DOCUMENTO = ['CC', 'TI', 'CE', 'PA', 'RC', 'NIT']

type FormData = CrearPersonaDto

interface PersonaFormProps {
  initialData?: Partial<FormData>
  nacionalidades: Nacionalidad[]
  onSubmit: (data: FormData) => Promise<void>
  submitLabel?: string
}

const EMPTY: FormData = {
  IDNacionalidad: 0,
  TipoDocumento: 'CC',
  NumeroDocumento: '',
  PrimerNombre: '',
  SegundoNombre: '',
  PrimerApellido: '',
  SegundoApellido: '',
  FechaNacimiento: '',
  TelefonoFijo: '',
  Celular: '',
  CorreoElectronico: '',
  DireccionResidencia: '',
  Estado: true,
}

export default function PersonaForm({
  initialData,
  nacionalidades,
  onSubmit,
  submitLabel = 'Guardar',
}: PersonaFormProps) {
  const [form, setForm] = useState<FormData>({ ...EMPTY, ...initialData })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  function handleChange(
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) {
    const { name, value, type } = e.target
    setForm((prev) => ({
      ...prev,
      [name]:
        type === 'checkbox'
          ? (e.target as HTMLInputElement).checked
          : name === 'IDNacionalidad'
          ? Number(value)
          : value,
    }))
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await onSubmit(form)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Error inesperado')
    } finally {
      setLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded text-sm">
          {error}
        </div>
      )}

      {/* Identificación */}
      <section>
        <h3 className="text-sm font-semibold text-slate-500 uppercase mb-3">
          Identificación
        </h3>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <Field label="Tipo de documento" required>
            <select
              name="TipoDocumento"
              value={form.TipoDocumento}
              onChange={handleChange}
              className={selectCls}
              required
            >
              {TIPOS_DOCUMENTO.map((t) => (
                <option key={t} value={t}>
                  {t}
                </option>
              ))}
            </select>
          </Field>
          <Field label="Número de documento" required>
            <input
              name="NumeroDocumento"
              value={form.NumeroDocumento}
              onChange={handleChange}
              className={inputCls}
              required
            />
          </Field>
        </div>
      </section>

      {/* Nombres y apellidos */}
      <section>
        <h3 className="text-sm font-semibold text-slate-500 uppercase mb-3">
          Nombres y apellidos
        </h3>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <Field label="Primer nombre" required>
            <input
              name="PrimerNombre"
              value={form.PrimerNombre}
              onChange={handleChange}
              className={inputCls}
              required
            />
          </Field>
          <Field label="Segundo nombre">
            <input
              name="SegundoNombre"
              value={form.SegundoNombre ?? ''}
              onChange={handleChange}
              className={inputCls}
            />
          </Field>
          <Field label="Primer apellido" required>
            <input
              name="PrimerApellido"
              value={form.PrimerApellido}
              onChange={handleChange}
              className={inputCls}
              required
            />
          </Field>
          <Field label="Segundo apellido">
            <input
              name="SegundoApellido"
              value={form.SegundoApellido ?? ''}
              onChange={handleChange}
              className={inputCls}
            />
          </Field>
        </div>
      </section>

      {/* Datos personales */}
      <section>
        <h3 className="text-sm font-semibold text-slate-500 uppercase mb-3">
          Datos personales
        </h3>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <Field label="Fecha de nacimiento" required>
            <input
              type="date"
              name="FechaNacimiento"
              value={form.FechaNacimiento}
              onChange={handleChange}
              className={inputCls}
              required
            />
          </Field>
          <Field label="Nacionalidad" required>
            <select
              name="IDNacionalidad"
              value={form.IDNacionalidad}
              onChange={handleChange}
              className={selectCls}
              required
            >
              <option value={0} disabled>
                Seleccione...
              </option>
              {nacionalidades.map((n) => (
                <option key={n.IDNacionalidad} value={n.IDNacionalidad}>
                  {n.NombreNacionalidad}
                </option>
              ))}
            </select>
          </Field>
          <Field label="Correo electrónico" required>
            <input
              type="email"
              name="CorreoElectronico"
              value={form.CorreoElectronico}
              onChange={handleChange}
              className={inputCls}
              required
            />
          </Field>
          <Field label="Dirección de residencia">
            <input
              name="DireccionResidencia"
              value={form.DireccionResidencia ?? ''}
              onChange={handleChange}
              className={inputCls}
            />
          </Field>
          <Field label="Teléfono fijo">
            <input
              name="TelefonoFijo"
              value={form.TelefonoFijo ?? ''}
              onChange={handleChange}
              className={inputCls}
            />
          </Field>
          <Field label="Celular">
            <input
              name="Celular"
              value={form.Celular ?? ''}
              onChange={handleChange}
              className={inputCls}
            />
          </Field>
        </div>
      </section>

      {/* Estado */}
      <div className="flex items-center gap-2">
        <input
          id="Estado"
          type="checkbox"
          name="Estado"
          checked={form.Estado}
          onChange={handleChange}
          className="w-4 h-4 text-blue-600 rounded border-slate-300"
        />
        <label htmlFor="Estado" className="text-sm text-slate-700">
          Persona activa
        </label>
      </div>

      <button
        type="submit"
        disabled={loading}
        className="w-full sm:w-auto bg-blue-600 hover:bg-blue-700 disabled:bg-blue-300 text-white font-medium px-6 py-2.5 rounded-lg transition-colors"
      >
        {loading ? 'Guardando...' : submitLabel}
      </button>
    </form>
  )
}

// ─── Helpers de estilo ────────────────────────────────────────────────────────

const inputCls =
  'w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition'

const selectCls =
  'w-full border border-slate-300 rounded-lg px-3 py-2 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition'

function Field({
  label,
  required,
  children,
}: {
  label: string
  required?: boolean
  children: React.ReactNode
}) {
  return (
    <div className="flex flex-col gap-1">
      <label className="text-sm font-medium text-slate-700">
        {label}
        {required && <span className="text-red-500 ml-0.5">*</span>}
      </label>
      {children}
    </div>
  )
}
