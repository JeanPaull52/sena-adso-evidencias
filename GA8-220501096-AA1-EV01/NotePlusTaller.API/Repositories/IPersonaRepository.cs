using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Repositories;

public interface IPersonaRepository
{
    Task<IEnumerable<Persona>> GetAll();
    Task<Persona?> GetById(int id);
    Task<Persona?> GetByNumeroDocumento(string numeroDocumento);
    Task<Persona> Create(Persona persona);
    Task<Persona?> Update(int id, Persona persona);
    Task<bool> Delete(int id);
}
