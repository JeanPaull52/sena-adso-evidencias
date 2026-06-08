using System.ComponentModel.DataAnnotations;

namespace NotePlusTaller.API.DTOs;

public class LoginDto
{
    [Required, MaxLength(100)]
    public string NombreUsuario { get; set; } = string.Empty;

    [Required]
    public string Contrasena { get; set; } = string.Empty;
}

public class RegistroDto
{
    [Required]
    public int IDPersona { get; set; }

    [Required]
    public int IDRol { get; set; }

    [Required, MaxLength(100)]
    public string NombreUsuario { get; set; } = string.Empty;

    [Required, MinLength(8)]
    public string Contrasena { get; set; } = string.Empty;
}

public class UsuarioResponseDto
{
    public int IDUsuario { get; set; }
    public string NombreUsuario { get; set; } = string.Empty;
    public string NombreRol { get; set; } = string.Empty;
    public string NombreCompleto { get; set; } = string.Empty;
    public string CorreoElectronico { get; set; } = string.Empty;
    public DateTime FechaCreacion { get; set; }
    public bool Estado { get; set; }
}
