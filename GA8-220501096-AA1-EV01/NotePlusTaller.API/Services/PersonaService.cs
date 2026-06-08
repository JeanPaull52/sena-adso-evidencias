using NotePlusTaller.API.DTOs;
using NotePlusTaller.API.Models;
using NotePlusTaller.API.Repositories;

namespace NotePlusTaller.API.Services;

public class PersonaService(IPersonaRepository repository)
{
    public Task<IEnumerable<Persona>> GetAll() => repository.GetAll();

    public Task<Persona?> GetById(int id) => repository.GetById(id);

    public Task<Persona?> GetByNumeroDocumento(string numeroDocumento) =>
        repository.GetByNumeroDocumento(numeroDocumento);

    public Task<Persona> Create(CrearPersonaDto dto)
    {
        var persona = new Persona
        {
            IDNacionalidad = dto.IDNacionalidad,
            TipoDocumento = dto.TipoDocumento,
            NumeroDocumento = dto.NumeroDocumento,
            PrimerNombre = dto.PrimerNombre,
            SegundoNombre = dto.SegundoNombre,
            PrimerApellido = dto.PrimerApellido,
            SegundoApellido = dto.SegundoApellido,
            FechaNacimiento = dto.FechaNacimiento,
            TelefonoFijo = dto.TelefonoFijo,
            Celular = dto.Celular,
            CorreoElectronico = dto.CorreoElectronico,
            DireccionResidencia = dto.DireccionResidencia
        };
        return repository.Create(persona);
    }

    public Task<Persona?> Update(int id, ActualizarPersonaDto dto)
    {
        var persona = new Persona
        {
            IDNacionalidad = dto.IDNacionalidad,
            TipoDocumento = dto.TipoDocumento,
            NumeroDocumento = dto.NumeroDocumento,
            PrimerNombre = dto.PrimerNombre,
            SegundoNombre = dto.SegundoNombre,
            PrimerApellido = dto.PrimerApellido,
            SegundoApellido = dto.SegundoApellido,
            FechaNacimiento = dto.FechaNacimiento,
            TelefonoFijo = dto.TelefonoFijo,
            Celular = dto.Celular,
            CorreoElectronico = dto.CorreoElectronico,
            DireccionResidencia = dto.DireccionResidencia,
            Estado = dto.Estado
        };
        return repository.Update(id, persona);
    }

    public Task<bool> Delete(int id) => repository.Delete(id);

    public static PersonaResponseDto ToDto(Persona p) => new()
    {
        IDPersona = p.IDPersona,
        IDNacionalidad = p.IDNacionalidad,
        NombreNacionalidad = p.Nacionalidad?.NombreNacionalidad ?? string.Empty,
        TipoDocumento = p.TipoDocumento,
        NumeroDocumento = p.NumeroDocumento,
        PrimerNombre = p.PrimerNombre,
        SegundoNombre = p.SegundoNombre,
        PrimerApellido = p.PrimerApellido,
        SegundoApellido = p.SegundoApellido,
        FechaNacimiento = p.FechaNacimiento,
        TelefonoFijo = p.TelefonoFijo,
        Celular = p.Celular,
        CorreoElectronico = p.CorreoElectronico,
        DireccionResidencia = p.DireccionResidencia,
        Estado = p.Estado
    };
}
