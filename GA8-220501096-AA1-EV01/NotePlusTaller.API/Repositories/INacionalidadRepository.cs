using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Repositories;

public interface INacionalidadRepository
{
    Task<IEnumerable<Nacionalidad>> GetAll();
    Task<Nacionalidad?> GetById(int id);
    Task<Nacionalidad> Create(Nacionalidad nacionalidad);
    Task<Nacionalidad?> Update(int id, Nacionalidad nacionalidad);
    Task<bool> Delete(int id);
}
