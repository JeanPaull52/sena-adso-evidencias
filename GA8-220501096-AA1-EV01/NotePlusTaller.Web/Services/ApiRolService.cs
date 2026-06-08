using System.Net.Http.Json;
using NotePlusTaller.Web.Models;

namespace NotePlusTaller.Web.Services;

public class ApiRolService(HttpClient http)
{
    public async Task<List<RolModel>> GetAll()
    {
        try { return await http.GetFromJsonAsync<List<RolModel>>("api/roles") ?? []; }
        catch { return []; }
    }

    public async Task<RolModel?> GetById(int id)
    {
        try { return await http.GetFromJsonAsync<RolModel>($"api/roles/{id}"); }
        catch { return null; }
    }
}
