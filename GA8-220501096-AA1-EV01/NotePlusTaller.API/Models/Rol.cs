namespace NotePlusTaller.API.Models;

public class Rol
{
    public int IDRol { get; set; }
    public string NombreRol { get; set; } = string.Empty;
    public string? Descripcion { get; set; }

    public ICollection<Usuario> Usuarios { get; set; } = [];
}
