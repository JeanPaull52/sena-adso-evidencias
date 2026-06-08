using NotePlusTaller.API.Models;
using NotePlusTaller.API.Repositories;

namespace NotePlusTaller.API.Services;

public class RolService(IRolRepository repository)
{
    public Task<IEnumerable<Rol>> GetAll() => repository.GetAll();

    public Task<Rol?> GetById(int id) => repository.GetById(id);
}
