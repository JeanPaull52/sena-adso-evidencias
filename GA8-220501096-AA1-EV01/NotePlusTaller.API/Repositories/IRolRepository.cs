using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Repositories;

public interface IRolRepository
{
    Task<IEnumerable<Rol>> GetAll();
    Task<Rol?> GetById(int id);
}
