namespace NotePlusTaller.API.Models;

public class Nacionalidad
{
    public int IDNacionalidad { get; set; }
    public string NombreNacionalidad { get; set; } = string.Empty;

    public ICollection<Persona> Personas { get; set; } = [];
}
