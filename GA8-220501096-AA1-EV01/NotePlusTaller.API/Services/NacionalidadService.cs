using NotePlusTaller.API.Models;
using NotePlusTaller.API.Repositories;

namespace NotePlusTaller.API.Services;

public class NacionalidadService(INacionalidadRepository repository)
{
    public Task<IEnumerable<Nacionalidad>> GetAll() => repository.GetAll();

    public Task<Nacionalidad?> GetById(int id) => repository.GetById(id);

    public Task<Nacionalidad> Create(Nacionalidad nacionalidad) => repository.Create(nacionalidad);

    public Task<Nacionalidad?> Update(int id, Nacionalidad nacionalidad) => repository.Update(id, nacionalidad);

    public Task<bool> Delete(int id) => repository.Delete(id);
}
