using System.Net.Http.Json;
using NotePlusTaller.Web.Models;

namespace NotePlusTaller.Web.Services;

public class ApiNacionalidadService(HttpClient http)
{
    public async Task<List<NacionalidadModel>> GetAll()
    {
        try { return await http.GetFromJsonAsync<List<NacionalidadModel>>("api/nacionalidades") ?? []; }
        catch { return []; }
    }

    public async Task<NacionalidadModel?> GetById(int id)
    {
        try { return await http.GetFromJsonAsync<NacionalidadModel>($"api/nacionalidades/{id}"); }
        catch { return null; }
    }
}
