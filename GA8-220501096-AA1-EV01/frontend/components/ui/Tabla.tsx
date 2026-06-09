import React from 'react'

export interface ColumnaTabla<T> {
  encabezado: string
  render: (fila: T) => React.ReactNode
}

interface TablaProps<T> {
  columnas: ColumnaTabla<T>[]
  datos: T[]
  keyField: keyof T
  onEditar?: (fila: T) => void
  onEliminar?: (fila: T) => void
  cargando?: boolean
  mensajeVacio?: string
}

export default function Tabla<T>({
  columnas,
  datos,
  keyField,
  onEditar,
  onEliminar,
  cargando = false,
  mensajeVacio = 'No hay datos disponibles.',
}: TablaProps<T>) {
  const tieneAcciones = onEditar || onEliminar

  if (cargando) {
    return (
      <div className="flex justify-center py-12">
        <span className="text-slate-500 text-sm">Cargando...</span>
      </div>
    )
  }

  return (
    <div className="overflow-x-auto rounded-lg border border-slate-200">
      <table className="w-full text-sm text-left">
        <thead className="bg-slate-100 text-slate-700 uppercase text-xs">
          <tr>
            {columnas.map((col, i) => (
              <th key={i} className="px-4 py-3 font-semibold">
                {col.encabezado}
              </th>
            ))}
            {tieneAcciones && (
              <th className="px-4 py-3 font-semibold text-right">Acciones</th>
            )}
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-slate-100">
          {datos.length === 0 ? (
            <tr>
              <td
                colSpan={columnas.length + (tieneAcciones ? 1 : 0)}
                className="px-4 py-8 text-center text-slate-400"
              >
                {mensajeVacio}
              </td>
            </tr>
          ) : (
            datos.map((fila) => (
              <tr
                key={String(fila[keyField])}
                className="hover:bg-slate-50 transition-colors"
              >
                {columnas.map((col, i) => (
                  <td key={i} className="px-4 py-3 text-slate-700">
                    {col.render(fila)}
                  </td>
                ))}
                {tieneAcciones && (
                  <td className="px-4 py-3 text-right space-x-2 whitespace-nowrap">
                    {onEditar && (
                      <button
                        onClick={() => onEditar(fila)}
                        className="text-blue-600 hover:text-blue-800 font-medium transition-colors"
                      >
                        Editar
                      </button>
                    )}
                    {onEliminar && (
                      <button
                        onClick={() => onEliminar(fila)}
                        className="text-red-600 hover:text-red-800 font-medium transition-colors"
                      >
                        Eliminar
                      </button>
                    )}
                  </td>
                )}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  )
}
