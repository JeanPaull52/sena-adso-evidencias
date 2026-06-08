using Moq;
using NotePlusTaller.API.DTOs;
using NotePlusTaller.API.Models;
using NotePlusTaller.API.Repositories;
using NotePlusTaller.API.Services;

namespace NotePlusTaller.Tests.Services;

public class AuthServiceTests
{
    private readonly Mock<IUsuarioRepository> _repo;
    private readonly AuthService _sut;

    // Persona y Rol de apoyo para los usuarios de prueba
    private static readonly Rol    _rol    = new() { IDRol = 1, NombreRol = "Administrador" };
    private static readonly Persona _persona = new()
    {
        IDPersona        = 1,
        TipoDocumento    = "CC",
        NumeroDocumento  = "10000001",
        PrimerNombre     = "Juan",
        PrimerApellido   = "Pérez",
        FechaNacimiento  = new DateOnly(1990, 1, 1),
        CorreoElectronico = "juan@test.com",
        Nacionalidad     = new Nacionalidad { NombreNacionalidad = "Colombiana" }
    };

    public AuthServiceTests()
    {
        _repo = new Mock<IUsuarioRepository>();
        _sut  = new AuthService(_repo.Object);
    }

    // -----------------------------------------------------------------------
    // Login
    // -----------------------------------------------------------------------

    [Fact]
    public async Task Login_DebeRetornarUsuarioConCredencialesValidas()
    {
        // Arrange
        var dto = new LoginDto { NombreUsuario = "jperez", Contrasena = "Password123" };

        var usuario = new Usuario
        {
            IDUsuario    = 1,
            NombreUsuario = "jperez",
            ContrasenaHash = "hash_no_evaluado_aqui",  // ValidarCredenciales está mockeado
            Estado       = true,
            IDRol        = 1,
            Rol          = _rol,
            IDPersona    = 1,
            Persona      = _persona,
            FechaCreacion = DateTime.UtcNow
        };

        _repo.Setup(r => r.ValidarCredenciales("jperez", "Password123")).ReturnsAsync(true);
        _repo.Setup(r => r.GetByNombreUsuario("jperez")).ReturnsAsync(usuario);

        // Act
        var resultado = await _sut.Login(dto);

        // Assert
        Assert.NotNull(resultado);
        Assert.Equal("jperez", resultado.NombreUsuario);
        Assert.Equal("Administrador", resultado.NombreRol);
        Assert.Equal("Juan Pérez", resultado.NombreCompleto);
        Assert.Equal("juan@test.com", resultado.CorreoElectronico);

        _repo.Verify(r => r.ValidarCredenciales("jperez", "Password123"), Times.Once);
        _repo.Verify(r => r.GetByNombreUsuario("jperez"), Times.Once);
    }

    [Fact]
    public async Task Login_DebeRetornarNullConCredencialesInvalidas()
    {
        // Arrange
        var dto = new LoginDto { NombreUsuario = "jperez", Contrasena = "Incorrecta" };
        _repo.Setup(r => r.ValidarCredenciales("jperez", "Incorrecta")).ReturnsAsync(false);

        // Act
        var resultado = await _sut.Login(dto);

        // Assert
        Assert.Null(resultado);

        // Si las credenciales son inválidas, no debe consultar el usuario
        _repo.Verify(r => r.GetByNombreUsuario(It.IsAny<string>()), Times.Never);
    }

    // -----------------------------------------------------------------------
    // Registro
    // -----------------------------------------------------------------------

    [Fact]
    public async Task Registro_DebeCrearUsuarioNuevo()
    {
        // Arrange
        var dto = new RegistroDto
        {
            IDPersona    = 1,
            IDRol        = 1,
            NombreUsuario = "nuevo_usuario",
            Contrasena   = "Password123"
        };

        // El usuario no existe aún
        _repo.Setup(r => r.GetByNombreUsuario("nuevo_usuario")).ReturnsAsync((Usuario?)null);

        // El repositorio persiste y devuelve el usuario creado (con navegación)
        _repo.Setup(r => r.Create(It.IsAny<Usuario>()))
             .ReturnsAsync((Usuario u) => new Usuario
             {
                 IDUsuario     = 42,
                 NombreUsuario = u.NombreUsuario,
                 ContrasenaHash = u.ContrasenaHash,
                 IDPersona     = u.IDPersona,
                 IDRol         = u.IDRol,
                 FechaCreacion = DateTime.UtcNow,
                 Estado        = true,
                 Rol           = _rol,
                 Persona       = _persona
             });

        // Act
        var (resultado, error) = await _sut.Registro(dto);

        // Assert
        Assert.NotNull(resultado);
        Assert.Null(error);
        Assert.Equal(42, resultado.IDUsuario);
        Assert.Equal("nuevo_usuario", resultado.NombreUsuario);
        Assert.Equal("Administrador", resultado.NombreRol);

        // Verifica que la contraseña llegó hasheada (no en texto plano)
        _repo.Verify(r => r.Create(It.Is<Usuario>(u =>
            u.NombreUsuario   == "nuevo_usuario" &&
            u.ContrasenaHash  != "Password123"   &&   // nunca en texto plano
            !string.IsNullOrEmpty(u.ContrasenaHash)
        )), Times.Once);
    }

    [Fact]
    public async Task Registro_DebeRetornarErrorSiUsuarioYaExiste()
    {
        // Arrange
        var dto = new RegistroDto
        {
            IDPersona    = 1,
            IDRol        = 1,
            NombreUsuario = "ya_existe",
            Contrasena   = "Password123"
        };

        var usuarioExistente = new Usuario
        {
            IDUsuario    = 5,
            NombreUsuario = "ya_existe",
            Estado       = true,
            Rol          = _rol,
            Persona      = _persona
        };

        _repo.Setup(r => r.GetByNombreUsuario("ya_existe")).ReturnsAsync(usuarioExistente);

        // Act
        var (resultado, error) = await _sut.Registro(dto);

        // Assert
        Assert.Null(resultado);
        Assert.NotNull(error);
        Assert.Contains("en uso", error, StringComparison.OrdinalIgnoreCase);

        // No debe llamar a Create si el usuario ya existe
        _repo.Verify(r => r.Create(It.IsAny<Usuario>()), Times.Never);
    }
}
