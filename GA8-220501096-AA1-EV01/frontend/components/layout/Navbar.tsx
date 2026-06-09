'use client'

import { useEffect, useState } from 'react'
import Link from 'next/link'
import { usePathname, useRouter } from 'next/navigation'
import { getSession, clearSession } from '@/lib/auth'
import type { UsuarioResponseDto } from '@/types'

const NAV_LINKS = [
  { href: '/personas', label: 'Personas' },
  { href: '/nacionalidades', label: 'Nacionalidades' },
]

export default function Navbar() {
  const router = useRouter()
  const pathname = usePathname()
  const [usuario, setUsuario] = useState<UsuarioResponseDto | null>(null)

  useEffect(() => {
    setUsuario(getSession())
  }, [])

  function handleLogout() {
    clearSession()
    router.replace('/login')
  }

  return (
    <header className="bg-slate-800 text-white shadow-md">
      <div className="container mx-auto px-4 max-w-6xl flex items-center justify-between h-16">
        <div className="flex items-center gap-8">
          <span className="font-bold text-lg tracking-tight">NotePlus</span>
          <nav className="flex gap-1">
            {NAV_LINKS.map(({ href, label }) => {
              const active = pathname.startsWith(href)
              return (
                <Link
                  key={href}
                  href={href}
                  className={`px-3 py-2 rounded text-sm font-medium transition-colors ${
                    active
                      ? 'bg-slate-600 text-white'
                      : 'text-slate-300 hover:bg-slate-700 hover:text-white'
                  }`}
                >
                  {label}
                </Link>
              )
            })}
          </nav>
        </div>

        <div className="flex items-center gap-4">
          {usuario && (
            <span className="text-sm text-slate-300">
              {usuario.NombreUsuario}
            </span>
          )}
          <button
            onClick={handleLogout}
            className="text-sm bg-slate-700 hover:bg-slate-600 px-3 py-1.5 rounded transition-colors"
          >
            Cerrar sesión
          </button>
        </div>
      </div>
    </header>
  )
}
