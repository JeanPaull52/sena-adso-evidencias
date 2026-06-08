namespace NotePlusTaller.API.Models;

public class Usuario
{
    public int IDUsuario { get; set; }
    public int IDRol { get; set; }
    public int IDPersona { get; set; }
    public string NombreUsuario { get; set; } = string.Empty;
    public string ContrasenaHash { get; set; } = string.Empty;
    public DateTime FechaCreacion { get; set; }
    public bool Estado { get; set; }

    public Rol Rol { get; set; } = null!;
    public Persona Persona { get; set; } = null!;
}
