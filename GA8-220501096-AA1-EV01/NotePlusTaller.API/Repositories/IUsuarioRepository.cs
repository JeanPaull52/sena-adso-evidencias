using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Repositories;

public interface IUsuarioRepository
{
    Task<Usuario?> GetByNombreUsuario(string nombreUsuario);
    Task<Usuario> Create(Usuario usuario);
    Task<bool> ValidarCredenciales(string nombreUsuario, string contrasena);
}
