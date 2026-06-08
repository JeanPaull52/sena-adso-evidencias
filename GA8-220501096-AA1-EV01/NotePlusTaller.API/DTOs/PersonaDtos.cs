using System.ComponentModel.DataAnnotations;

namespace NotePlusTaller.API.DTOs;

public class CrearPersonaDto
{
    [Required]
    public int IDNacionalidad { get; set; }

    [Required, MaxLength(20)]
    public string TipoDocumento { get; set; } = string.Empty;

    [Required, MaxLength(20)]
    public string NumeroDocumento { get; set; } = string.Empty;

    [Required, MaxLength(100)]
    public string PrimerNombre { get; set; } = string.Empty;

    [MaxLength(100)]
    public string? SegundoNombre { get; set; }

    [Required, MaxLength(100)]
    public string PrimerApellido { get; set; } = string.Empty;

    [MaxLength(100)]
    public string? SegundoApellido { get; set; }

    [Required]
    public DateOnly FechaNacimiento { get; set; }

    [MaxLength(20)]
    public string? TelefonoFijo { get; set; }

    [MaxLength(20)]
    public string? Celular { get; set; }

    [Required, EmailAddress, MaxLength(200)]
    public string CorreoElectronico { get; set; } = string.Empty;

    [MaxLength(300)]
    public string? DireccionResidencia { get; set; }
}

public class ActualizarPersonaDto
{
    [Required]
    public int IDNacionalidad { get; set; }

    [Required, MaxLength(20)]
    public string TipoDocumento { get; set; } = string.Empty;

    [Required, MaxLength(20)]
    public string NumeroDocumento { get; set; } = string.Empty;

    [Required, MaxLength(100)]
    public string PrimerNombre { get; set; } = string.Empty;

    [MaxLength(100)]
    public string? SegundoNombre { get; set; }

    [Required, MaxLength(100)]
    public string PrimerApellido { get; set; } = string.Empty;

    [MaxLength(100)]
    public string? SegundoApellido { get; set; }

    [Required]
    public DateOnly FechaNacimiento { get; set; }

    [MaxLength(20)]
    public string? TelefonoFijo { get; set; }

    [MaxLength(20)]
    public string? Celular { get; set; }

    [Required, EmailAddress, MaxLength(200)]
    public string CorreoElectronico { get; set; } = string.Empty;

    [MaxLength(300)]
    public string? DireccionResidencia { get; set; }

    public bool Estado { get; set; }
}

public class PersonaResponseDto
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
}
