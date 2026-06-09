using System.ComponentModel.DataAnnotations;

namespace NotePlusTaller.Web.Models;

public class LoginModel
{
    [Required(ErrorMessage = "El nombre de usuario es obligatorio.")]
    public string NombreUsuario { get; set; } = string.Empty;

    [Required(ErrorMessage = "La contraseña es obligatoria.")]
    public string Contrasena { get; set; } = string.Empty;
}

public class RegistroModel
{
    [Required(ErrorMessage = "La persona es obligatoria.")]
    [Range(1, int.MaxValue, ErrorMessage = "Ingrese un ID de persona válido.")]
    public int IDPersona { get; set; }

    [Required(ErrorMessage = "El rol es obligatorio.")]
    [Range(1, int.MaxValue, ErrorMessage = "Seleccione un rol.")]
    public int IDRol { get; set; }

    [Required(ErrorMessage = "El nombre de usuario es obligatorio.")]
    [MaxLength(100)]
    public string NombreUsuario { get; set; } = string.Empty;

    [Required(ErrorMessage = "La contraseña es obligatoria.")]
    [MinLength(8, ErrorMessage = "La contraseña debe tener al menos 8 caracteres.")]
    public string Contrasena { get; set; } = string.Empty;
}

public class RegistroCompletoModel
{
    // ── Persona ──────────────────────────────────────────────────────────────
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

    [Required(ErrorMessage = "El primer apellido es obligatorio.")]
    [MaxLength(100)]
    public string PrimerApellido { get; set; } = string.Empty;

    [Required(ErrorMessage = "El celular es obligatorio.")]
    [MaxLength(20)]
    public string Celular { get; set; } = string.Empty;

    [Required(ErrorMessage = "El correo electrónico es obligatorio.")]
    [EmailAddress(ErrorMessage = "El correo no es válido.")]
    [MaxLength(200)]
    public string CorreoElectronico { get; set; } = string.Empty;

    // ── Usuario ───────────────────────────────────────────────────────────────
    [Required(ErrorMessage = "El rol es obligatorio.")]
    [Range(1, int.MaxValue, ErrorMessage = "Seleccione un rol.")]
    public int IDRol { get; set; }

    [Required(ErrorMessage = "El nombre de usuario es obligatorio.")]
    [MaxLength(100)]
    public string NombreUsuario { get; set; } = string.Empty;

    [Required(ErrorMessage = "La contraseña es obligatoria.")]
    [MinLength(8, ErrorMessage = "La contraseña debe tener al menos 8 caracteres.")]
    public string Contrasena { get; set; } = string.Empty;
}

public class UsuarioSession
{
    public int IDUsuario { get; set; }
    public string NombreUsuario { get; set; } = string.Empty;
    public string NombreRol { get; set; } = string.Empty;
    public string NombreCompleto { get; set; } = string.Empty;
    public string CorreoElectronico { get; set; } = string.Empty;
}

public class ApiErrorResponse
{
    public string? Mensaje { get; set; }
}
