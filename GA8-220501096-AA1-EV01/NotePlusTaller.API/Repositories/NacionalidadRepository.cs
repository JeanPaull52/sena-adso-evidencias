using Microsoft.EntityFrameworkCore;
using NotePlusTaller.API.Data;
using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Repositories;

public class NacionalidadRepository(AppDbContext context) : INacionalidadRepository
{
    public async Task<IEnumerable<Nacionalidad>> GetAll() =>
        await context.Nacionalidades.OrderBy(n => n.NombreNacionalidad).ToListAsync();

    public async Task<Nacionalidad?> GetById(int id) =>
        await context.Nacionalidades.FindAsync(id);

    public async Task<Nacionalidad> Create(Nacionalidad nacionalidad)
    {
        context.Nacionalidades.Add(nacionalidad);
        await context.SaveChangesAsync();
        return nacionalidad;
    }

    public async Task<Nacionalidad?> Update(int id, Nacionalidad nacionalidad)
    {
        var existing = await context.Nacionalidades.FindAsync(id);
        if (existing is null) return null;

        existing.NombreNacionalidad = nacionalidad.NombreNacionalidad;
        await context.SaveChangesAsync();
        return existing;
    }

    public async Task<bool> Delete(int id)
    {
        var existing = await context.Nacionalidades.FindAsync(id);
        if (existing is null) return false;

        context.Nacionalidades.Remove(existing);
        await context.SaveChangesAsync();
        return true;
    }
}
