using NotePlusTaller.API.DTOs;
using NotePlusTaller.API.Models;
using NotePlusTaller.API.Repositories;

namespace NotePlusTaller.API.Services;

public class AuthService(IUsuarioRepository usuarioRepository)
{
    public async Task<UsuarioResponseDto?> Login(LoginDto dto)
    {
        var credencialesValidas = await usuarioRepository.ValidarCredenciales(dto.NombreUsuario, dto.Contrasena);
        if (!credencialesValidas) return null;

        var usuario = await usuarioRepository.GetByNombreUsuario(dto.NombreUsuario);
        return usuario is null ? null : ToDto(usuario);
    }

    public async Task<(UsuarioResponseDto? resultado, string? error)> Registro(RegistroDto dto)
    {
        var existente = await usuarioRepository.GetByNombreUsuario(dto.NombreUsuario);
        if (existente is not null)
            return (null, "El nombre de usuario ya está en uso.");

        var usuario = new Usuario
        {
            IDPersona = dto.IDPersona,
            IDRol = dto.IDRol,
            NombreUsuario = dto.NombreUsuario,
            ContrasenaHash = BCrypt.Net.BCrypt.HashPassword(dto.Contrasena)
        };

        var creado = await usuarioRepository.Create(usuario);
        return (ToDto(creado), null);
    }

    private static UsuarioResponseDto ToDto(Usuario u) => new()
    {
        IDUsuario = u.IDUsuario,
        NombreUsuario = u.NombreUsuario,
        NombreRol = u.Rol?.NombreRol ?? string.Empty,
        NombreCompleto = u.Persona is null
            ? string.Empty
            : $"{u.Persona.PrimerNombre} {u.Persona.PrimerApellido}".Trim(),
        CorreoElectronico = u.Persona?.CorreoElectronico ?? string.Empty,
        FechaCreacion = u.FechaCreacion,
        Estado = u.Estado
    };
}
