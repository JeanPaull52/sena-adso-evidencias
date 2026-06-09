import type { UsuarioResponseDto } from '@/types'

const SESSION_KEY = 'noteplus_session'

export function getSession(): UsuarioResponseDto | null {
  if (typeof window === 'undefined') return null
  try {
    const raw = localStorage.getItem(SESSION_KEY)
    return raw ? (JSON.parse(raw) as UsuarioResponseDto) : null
  } catch {
    return null
  }
}

export function setSession(user: UsuarioResponseDto): void {
  localStorage.setItem(SESSION_KEY, JSON.stringify(user))
}

export function clearSession(): void {
  localStorage.removeItem(SESSION_KEY)
}
