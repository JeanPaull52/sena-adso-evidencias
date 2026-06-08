using Microsoft.EntityFrameworkCore;
using NotePlusTaller.API.Data;
using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Repositories;

public class PersonaRepository(AppDbContext context) : IPersonaRepository
{
    public async Task<IEnumerable<Persona>> GetAll() =>
        await context.Personas
            .Include(p => p.Nacionalidad)
            .Where(p => p.Estado)
            .OrderBy(p => p.PrimerApellido).ThenBy(p => p.PrimerNombre)
            .ToListAsync();

    public async Task<Persona?> GetById(int id) =>
        await context.Personas
            .Include(p => p.Nacionalidad)
            .FirstOrDefaultAsync(p => p.IDPersona == id);

    public async Task<Persona?> GetByNumeroDocumento(string numeroDocumento) =>
        await context.Personas
            .Include(p => p.Nacionalidad)
            .FirstOrDefaultAsync(p => p.NumeroDocumento == numeroDocumento);

    public async Task<Persona> Create(Persona persona)
    {
        persona.Estado = true;
        context.Personas.Add(persona);
        await context.SaveChangesAsync();
        await context.Entry(persona).Reference(p => p.Nacionalidad).LoadAsync();
        return persona;
    }

    public async Task<Persona?> Update(int id, Persona persona)
    {
        var existing = await context.Personas
            .Include(p => p.Nacionalidad)
            .FirstOrDefaultAsync(p => p.IDPersona == id);
        if (existing is null) return null;

        existing.IDNacionalidad = persona.IDNacionalidad;
        existing.TipoDocumento = persona.TipoDocumento;
        existing.NumeroDocumento = persona.NumeroDocumento;
        existing.PrimerNombre = persona.PrimerNombre;
        existing.SegundoNombre = persona.SegundoNombre;
        existing.PrimerApellido = persona.PrimerApellido;
        existing.SegundoApellido = persona.SegundoApellido;
        existing.FechaNacimiento = persona.FechaNacimiento;
        existing.TelefonoFijo = persona.TelefonoFijo;
        existing.Celular = persona.Celular;
        existing.CorreoElectronico = persona.CorreoElectronico;
        existing.DireccionResidencia = persona.DireccionResidencia;
        existing.Estado = persona.Estado;

        await context.SaveChangesAsync();
        await context.Entry(existing).Reference(p => p.Nacionalidad).LoadAsync();
        return existing;
    }

    public async Task<bool> Delete(int id)
    {
        var existing = await context.Personas.FindAsync(id);
        if (existing is null) return false;

        existing.Estado = false;
        await context.SaveChangesAsync();
        return true;
    }
}
