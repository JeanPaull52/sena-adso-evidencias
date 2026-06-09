import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

const ALLOWED_ORIGINS = [
  'http://localhost:3000',
  process.env.FRONTEND_URL,
].filter(Boolean) as string[]

export function middleware(request: NextRequest) {
  const origin = request.headers.get('origin') ?? ''
  const allowedOrigin = ALLOWED_ORIGINS.includes(origin)
    ? origin
    : ALLOWED_ORIGINS[0]

  const corsHeaders: Record<string, string> = {
    'Access-Control-Allow-Origin': allowedOrigin,
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    'Access-Control-Allow-Headers': 'Content-Type, Authorization',
  }

  // Preflight: el browser envía OPTIONS antes del fetch real
  if (request.method === 'OPTIONS') {
    return new NextResponse(null, { status: 204, headers: corsHeaders })
  }

  const response = NextResponse.next()
  Object.entries(corsHeaders).forEach(([key, val]) => response.headers.set(key, val))
  return response
}

export const config = {
  matcher: '/api/:path*',
}
