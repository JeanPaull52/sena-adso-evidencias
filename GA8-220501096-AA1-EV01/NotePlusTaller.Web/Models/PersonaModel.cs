using System.ComponentModel.DataAnnotations;

namespace NotePlusTaller.Web.Models;

public class PersonaModel
{
    public int IDPersona { get; set; }
    public int IDNacionalidad { get; set; }
    public string NombreNacionalidad { get; set; } = string.Empty;
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

    public string NombreCompleto =>
        string.Join(" ", new[] { PrimerNombre, SegundoNombre, PrimerApellido, SegundoApellido }
            .Where(s => !string.IsNullOrWhiteSpace(s)));
}

public class CrearPersonaModel
{
    [Required(ErrorMessage = "La nacionalidad es obligatoria.")]
    [Range(1, int.MaxValue, ErrorMessage = "Seleccione una nacionalidad.")]
    public int IDNacionalidad { get; set; }

    [Required(ErrorMessage = "El tipo de documento es obligatorio.")]
    public string TipoDocumento { get; set; } = string.Empty;

    [Required(ErrorMessage = "El número de documento es obligatorio.")]
    [MaxLength(20)]
    public string NumeroDocumento { get; set; } = string.Empty;

    [Required(ErrorMessage = "El primer nombre es obligatorio.")]
    [MaxLength(100)]
    public string PrimerNombre { get; set; } = string.Empty;

    [MaxLength(100)]
    public string? SegundoNombre { get; set; }

    [Required(ErrorMessage = "El primer apellido es obligatorio.")]
    [MaxLength(100)]
    public string PrimerApellido { get; set; } = string.Empty;

    [MaxLength(100)]
    public string? SegundoApellido { get; set; }

    [Required(ErrorMessage = "La fecha de nacimiento es obligatoria.")]
    public DateOnly FechaNacimiento { get; set; } = DateOnly.FromDateTime(DateTime.Today.AddYears(-18));

    [MaxLength(20)]
    public string? TelefonoFijo { get; set; }

    [MaxLength(20)]
    public string? Celular { get; set; }

    [Required(ErrorMessage = "El correo electrónico es obligatorio.")]
    [EmailAddress(ErrorMessage = "El correo no es válido.")]
    [MaxLength(200)]
    public string CorreoElectronico { get; set; } = string.Empty;

    [MaxLength(300)]
    public string? DireccionResidencia { get; set; }
}

public class ActualizarPersonaModel : CrearPersonaModel
{
    public bool Estado { get; set; } = true;
}
