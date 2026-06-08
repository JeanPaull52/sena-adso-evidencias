using Moq;
using NotePlusTaller.API.DTOs;
using NotePlusTaller.API.Models;
using NotePlusTaller.API.Repositories;
using NotePlusTaller.API.Services;

namespace NotePlusTaller.Tests.Services;

public class PersonaServiceTests
{
    private readonly Mock<IPersonaRepository> _repo;
    private readonly PersonaService _sut;

    // Datos de apoyo reutilizables en las pruebas
    private static readonly Nacionalidad _nac = new() { IDNacionalidad = 1, NombreNacionalidad = "Colombiana" };

    private static Persona BuildPersona(int id = 1) => new()
    {
        IDPersona        = id,
        IDNacionalidad   = 1,
        TipoDocumento    = "CC",
        NumeroDocumento  = $"1000{id:D4}",
        PrimerNombre     = "Juan",
        PrimerApellido   = "Pérez",
        FechaNacimiento  = new DateOnly(1990, 6, 15),
        CorreoElectronico = $"juan{id}@test.com",
        Estado           = true,
        Nacionalidad     = _nac
    };

    public PersonaServiceTests()
    {
        _repo = new Mock<IPersonaRepository>();
        _sut  = new PersonaService(_repo.Object);
    }

    // -----------------------------------------------------------------------
    // GetAll
    // -----------------------------------------------------------------------

    [Fact]
    public async Task GetAll_DebeRetornarListaDePersonas()
    {
        // Arrange
        var datos = new List<Persona> { BuildPersona(1), BuildPersona(2) };
        _repo.Setup(r => r.GetAll()).ReturnsAsync(datos);

        // Act
        var resultado = (await _sut.GetAll()).ToList();

        // Assert
        Assert.Equal(2, resultado.Count);
        Assert.All(resultado, p => Assert.True(p.Estado));
        _repo.Verify(r => r.GetAll(), Times.Once);
    }

    // -----------------------------------------------------------------------
    // GetById
    // -----------------------------------------------------------------------

    [Fact]
    public async Task GetById_DebeRetornarPersonaExistente()
    {
        // Arrange
        var persona = BuildPersona(1);
        _repo.Setup(r => r.GetById(1)).ReturnsAsync(persona);

        // Act
        var resultado = await _sut.GetById(1);

        // Assert
        Assert.NotNull(resultado);
        Assert.Equal(1, resultado.IDPersona);
        Assert.Equal("Juan", resultado.PrimerNombre);
        Assert.Equal("Pérez", resultado.PrimerApellido);
        _repo.Verify(r => r.GetById(1), Times.Once);
    }

    // -----------------------------------------------------------------------
    // Create
    // -----------------------------------------------------------------------

    [Fact]
    public async Task Crear_DebeCrearPersonaCorrectamente()
    {
        // Arrange
        var dto = new CrearPersonaDto
        {
            IDNacionalidad    = 1,
            TipoDocumento     = "CC",
            NumeroDocumento   = "10000001",
            PrimerNombre      = "Juan",
            PrimerApellido    = "Pérez",
            FechaNacimiento   = new DateOnly(1990, 6, 15),
            CorreoElectronico = "juan@test.com"
        };

        // El repositorio devuelve la persona ya persistida (con ID asignado)
        _repo.Setup(r => r.Create(It.IsAny<Persona>()))
             .ReturnsAsync((Persona p) =>
             {
                 p.IDPersona   = 1;
                 p.Estado      = true;
                 p.Nacionalidad = _nac;
                 return p;
             });

        // Act
        var resultado = await _sut.Create(dto);

        // Assert
        Assert.NotNull(resultado);
        Assert.Equal(1, resultado.IDPersona);
        Assert.Equal("Juan", resultado.PrimerNombre);
        Assert.Equal("10000001", resultado.NumeroDocumento);
        Assert.True(resultado.Estado);

        // Verifica que el repositorio recibió una Persona construida desde el DTO
        _repo.Verify(r => r.Create(It.Is<Persona>(p =>
            p.NumeroDocumento   == "10000001"     &&
            p.CorreoElectronico == "juan@test.com" &&
            p.IDNacionalidad    == 1
        )), Times.Once);
    }

    // -----------------------------------------------------------------------
    // Delete
    // -----------------------------------------------------------------------

    [Fact]
    public async Task Eliminar_DebeEliminarPersonaExistente()
    {
        // Arrange
        _repo.Setup(r => r.Delete(1)).ReturnsAsync(true);

        // Act
        var resultado = await _sut.Delete(1);

        // Assert
        Assert.True(resultado);
        _repo.Verify(r => r.Delete(1), Times.Once);
    }

    [Fact]
    public async Task Eliminar_DebeRetornarFalseSiPersonaNoExiste()
    {
        // Arrange
        _repo.Setup(r => r.Delete(999)).ReturnsAsync(false);

        // Act
        var resultado = await _sut.Delete(999);

        // Assert
        Assert.False(resultado);
    }
}
