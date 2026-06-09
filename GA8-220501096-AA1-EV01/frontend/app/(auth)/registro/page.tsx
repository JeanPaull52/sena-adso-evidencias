'use client'

import { useEffect, useState } from 'react'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { getNacionalidades, getRoles, registro } from '@/lib/api'
import { setSession } from '@/lib/auth'
import type { Nacionalidad, Rol } from '@/types'

const TIPOS_DOCUMENTO = ['CC', 'TI', 'CE', 'PA', 'RC', 'NIT']

const EMPTY = {
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
  IDRol: 0,
  NombreUsuario: '',
  Contrasena: '',
  ConfirmarContrasena: '',
}

export default function RegistroPage() {
  const router = useRouter()
  const [form, setForm] = useState(EMPTY)
  const [nacionalidades, setNacionalidades] = useState<Nacionalidad[]>([])
  const [roles, setRoles] = useState<Rol[]>([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    Promise.all([getNacionalidades(), getRoles()])
      .then(([nacs, rls]) => {
        setNacionalidades(nacs)
        setRoles(rls)
      })
      .catch(() => setError('Error cargando datos del formulario'))
  }, [])

  function handleChange(e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) {
    const { name, value } = e.target
    setForm((prev) => ({
      ...prev,
      [name]: name === 'IDNacionalidad' || name === 'IDRol' ? Number(value) : value,
    }))
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError('')

    if (form.Contrasena !== form.ConfirmarContrasena) {
      setError('Las contraseñas no coinciden')
      return
    }
    if (form.Contrasena.length < 8) {
      setError('La contraseña debe tener al menos 8 caracteres')
      return
    }

    setLoading(true)
    try {
      const { ConfirmarContrasena: _confirmar, ...dto } = form
      const usuario = await registro(dto)
      setSession(usuario)
      router.replace('/personas')
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Error inesperado')
    } finally {
      setLoading(false)
    }
  }

  const inputCls =
    'w-full border border-slate-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition'
  const selectCls =
    'w-full border border-slate-300 rounded-lg px-3 py-2 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition'

  return (
    <div className="w-full max-w-2xl">
      <div className="bg-white rounded-2xl shadow-md px-8 py-10">
        <h1 className="text-2xl font-bold text-slate-800 mb-1">Registro</h1>
        <p className="text-sm text-slate-500 mb-8">Crea tu cuenta en NotePlus Taller</p>

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm mb-6">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-8">
          {/* Identificación */}
          <section>
            <h2 className="text-xs font-semibold text-slate-400 uppercase tracking-wide mb-3">
              Identificación
            </h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">
                  Tipo de documento <span className="text-red-500">*</span>
                </label>
                <select name="TipoDocumento" value={form.TipoDocumento} onChange={handleChange} className={selectCls} required>
                  {TIPOS_DOCUMENTO.map((t) => <option key={t} value={t}>{t}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">
                  Número de documento <span className="text-red-500">*</span>
                </label>
                <input name="NumeroDocumento" value={form.NumeroDocumento} onChange={handleChange} className={inputCls} required />
              </div>
            </div>
          </section>

          {/* Nombres */}
          <section>
            <h2 className="text-xs font-semibold text-slate-400 uppercase tracking-wide mb-3">
              Nombres y apellidos
            </h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Primer nombre <span className="text-red-500">*</span></label>
                <input name="PrimerNombre" value={form.PrimerNombre} onChange={handleChange} className={inputCls} required />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Segundo nombre</label>
                <input name="SegundoNombre" value={form.SegundoNombre} onChange={handleChange} className={inputCls} />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Primer apellido <span className="text-red-500">*</span></label>
                <input name="PrimerApellido" value={form.PrimerApellido} onChange={handleChange} className={inputCls} required />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Segundo apellido</label>
                <input name="SegundoApellido" value={form.SegundoApellido} onChange={handleChange} className={inputCls} />
              </div>
            </div>
          </section>

          {/* Datos personales */}
          <section>
            <h2 className="text-xs font-semibold text-slate-400 uppercase tracking-wide mb-3">
              Datos personales
            </h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Fecha de nacimiento <span className="text-red-500">*</span></label>
                <input type="date" name="FechaNacimiento" value={form.FechaNacimiento} onChange={handleChange} className={inputCls} required />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Nacionalidad <span className="text-red-500">*</span></label>
                <select name="IDNacionalidad" value={form.IDNacionalidad} onChange={handleChange} className={selectCls} required>
                  <option value={0} disabled>Seleccione...</option>
                  {nacionalidades.map((n) => (
                    <option key={n.IDNacionalidad} value={n.IDNacionalidad}>{n.NombreNacionalidad}</option>
                  ))}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Correo electrónico <span className="text-red-500">*</span></label>
                <input type="email" name="CorreoElectronico" value={form.CorreoElectronico} onChange={handleChange} className={inputCls} required />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Celular</label>
                <input name="Celular" value={form.Celular} onChange={handleChange} className={inputCls} />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Teléfono fijo</label>
                <input name="TelefonoFijo" value={form.TelefonoFijo} onChange={handleChange} className={inputCls} />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Dirección de residencia</label>
                <input name="DireccionResidencia" value={form.DireccionResidencia} onChange={handleChange} className={inputCls} />
              </div>
            </div>
          </section>

          {/* Cuenta */}
          <section>
            <h2 className="text-xs font-semibold text-slate-400 uppercase tracking-wide mb-3">
              Datos de acceso
            </h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Rol <span className="text-red-500">*</span></label>
                <select name="IDRol" value={form.IDRol} onChange={handleChange} className={selectCls} required>
                  <option value={0} disabled>Seleccione...</option>
                  {roles.map((r) => (
                    <option key={r.IDRol} value={r.IDRol}>{r.NombreRol}</option>
                  ))}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Nombre de usuario <span className="text-red-500">*</span></label>
                <input name="NombreUsuario" value={form.NombreUsuario} onChange={handleChange} className={inputCls} autoComplete="username" required />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Contraseña <span className="text-red-500">*</span></label>
                <input type="password" name="Contrasena" value={form.Contrasena} onChange={handleChange} className={inputCls} autoComplete="new-password" required />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Confirmar contraseña <span className="text-red-500">*</span></label>
                <input type="password" name="ConfirmarContrasena" value={form.ConfirmarContrasena} onChange={handleChange} className={inputCls} autoComplete="new-password" required />
              </div>
            </div>
          </section>

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-blue-300 text-white font-medium py-2.5 rounded-lg transition-colors text-sm"
          >
            {loading ? 'Registrando...' : 'Crear cuenta'}
          </button>
        </form>

        <p className="mt-6 text-center text-sm text-slate-500">
          ¿Ya tienes cuenta?{' '}
          <Link href="/login" className="text-blue-600 hover:underline font-medium">
            Inicia sesión
          </Link>
        </p>
      </div>
    </div>
  )
}
