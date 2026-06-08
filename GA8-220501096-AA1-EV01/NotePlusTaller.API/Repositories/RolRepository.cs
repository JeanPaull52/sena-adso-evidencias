using Microsoft.EntityFrameworkCore;
using NotePlusTaller.API.Data;
using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Repositories;

public class RolRepository(AppDbContext context) : IRolRepository
{
    public async Task<IEnumerable<Rol>> GetAll() =>
        await context.Roles.OrderBy(r => r.NombreRol).ToListAsync();

    public async Task<Rol?> GetById(int id) =>
        await context.Roles.FindAsync(id);
}
