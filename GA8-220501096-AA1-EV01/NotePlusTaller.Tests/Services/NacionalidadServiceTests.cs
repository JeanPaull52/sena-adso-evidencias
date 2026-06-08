using Moq;
using NotePlusTaller.API.Models;
using NotePlusTaller.API.Repositories;
using NotePlusTaller.API.Services;

namespace NotePlusTaller.Tests.Services;

public class NacionalidadServiceTests
{
    private readonly Mock<INacionalidadRepository> _repo;
    private readonly NacionalidadService _sut;

    public NacionalidadServiceTests()
    {
        _repo = new Mock<INacionalidadRepository>();
        _sut  = new NacionalidadService(_repo.Object);
    }

    // -----------------------------------------------------------------------
    // GetAll
    // -----------------------------------------------------------------------

    [Fact]
    public async Task GetAll_DebeRetornarListaDeNacionalidades()
    {
        // Arrange
        var datos = new List<Nacionalidad>
        {
            new() { IDNacionalidad = 1, NombreNacionalidad = "Colombiana" },
            new() { IDNacionalidad = 2, NombreNacionalidad = "Venezolana" },
            new() { IDNacionalidad = 3, NombreNacionalidad = "Ecuatoriana" }
        };
        _repo.Setup(r => r.GetAll()).ReturnsAsync(datos);

        // Act
        var resultado = (await _sut.GetAll()).ToList();

        // Assert
        Assert.Equal(3, resultado.Count);
        Assert.Contains(resultado, n => n.NombreNacionalidad == "Colombiana");
        _repo.Verify(r => r.GetAll(), Times.Once);
    }

    // -----------------------------------------------------------------------
    // GetById
    // -----------------------------------------------------------------------

    [Fact]
    public async Task GetById_DebeRetornarNacionalidadExistente()
    {
        // Arrange
        var esperado = new Nacionalidad { IDNacionalidad = 1, NombreNacionalidad = "Colombiana" };
        _repo.Setup(r => r.GetById(1)).ReturnsAsync(esperado);

        // Act
        var resultado = await _sut.GetById(1);

        // Assert
        Assert.NotNull(resultado);
        Assert.Equal(1, resultado.IDNacionalidad);
        Assert.Equal("Colombiana", resultado.NombreNacionalidad);
        _repo.Verify(r => r.GetById(1), Times.Once);
    }

    [Fact]
    public async Task GetById_DebeRetornarNullSiNoExiste()
    {
        // Arrange
        _repo.Setup(r => r.GetById(999)).ReturnsAsync((Nacionalidad?)null);

        // Act
        var resultado = await _sut.GetById(999);

        // Assert
        Assert.Null(resultado);
        _repo.Verify(r => r.GetById(999), Times.Once);
    }
}
