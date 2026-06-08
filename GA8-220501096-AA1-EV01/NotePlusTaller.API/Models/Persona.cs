namespace NotePlusTaller.API.Models;

public class Persona
{
    public int IDPersona { get; set; }
    public int IDNacionalidad { get; set; }
    public string TipoDocumento { get; set; } = string.Empty;
    public string NumeroDocumento { get; set; } = string.Empty;
    public string PrimerNombre { get; set; } = string.Empty;
    public string? SegundoNombre { get; set; }
    public string PrimerApellido { get; set; } = string.Empty;
    public string? SegundoApellido { get; set; }
    public DateOnly FechaNacimiento { get; set; }
    public string? TelefonoFijo { get; set; }
    public string? Celular { get; set; }
    public string CorreoElectronico { get; set; } = string.Empty;
    public string? DireccionResidencia { get; set; }
    public bool Estado { get; set; }

    public Nacionalidad Nacionalidad { get; set; } = null!;
    public Usuario? Usuario { get; set; }
}
