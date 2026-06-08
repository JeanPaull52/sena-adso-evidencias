using Microsoft.EntityFrameworkCore;
using NotePlusTaller.API.Data;
using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Repositories;

public class UsuarioRepository(AppDbContext context) : IUsuarioRepository
{
    public async Task<Usuario?> GetByNombreUsuario(string nombreUsuario) =>
        await context.Usuarios
            .Include(u => u.Rol)
            .Include(u => u.Persona)
            .FirstOrDefaultAsync(u => u.NombreUsuario == nombreUsuario);

    public async Task<Usuario> Create(Usuario usuario)
    {
        usuario.FechaCreacion = DateTime.UtcNow;
        usuario.Estado = true;
        context.Usuarios.Add(usuario);
        await context.SaveChangesAsync();
        await context.Entry(usuario).Reference(u => u.Rol).LoadAsync();
        await context.Entry(usuario).Reference(u => u.Persona).LoadAsync();
        return usuario;
    }

    public async Task<bool> ValidarCredenciales(string nombreUsuario, string contrasena)
    {
        var usuario = await context.Usuarios
            .FirstOrDefaultAsync(u => u.NombreUsuario == nombreUsuario && u.Estado);
        if (usuario is null) return false;
        return BCrypt.Net.BCrypt.Verify(contrasena, usuario.ContrasenaHash);
    }
}
