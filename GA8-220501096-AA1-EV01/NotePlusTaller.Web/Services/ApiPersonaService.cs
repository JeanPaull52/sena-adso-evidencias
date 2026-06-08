using System.Net.Http.Json;
using NotePlusTaller.Web.Models;

namespace NotePlusTaller.Web.Services;

public class ApiPersonaService(HttpClient http)
{
    public async Task<List<PersonaModel>> GetAll()
    {
        try { return await http.GetFromJsonAsync<List<PersonaModel>>("api/personas") ?? []; }
        catch { return []; }
    }

    public async Task<PersonaModel?> GetById(int id)
    {
        try { return await http.GetFromJsonAsync<PersonaModel>($"api/personas/{id}"); }
        catch { return null; }
    }

    public async Task<(PersonaModel? resultado, string? error)> Crear(CrearPersonaModel model)
    {
        try
        {
            var response = await http.PostAsJsonAsync("api/personas", model);
            if (response.IsSuccessStatusCode)
                return (await response.Content.ReadFromJsonAsync<PersonaModel>(), null);
            var err = await response.Content.ReadFromJsonAsync<ApiErrorResponse>();
            return (null, err?.Mensaje ?? $"Error {(int)response.StatusCode}");
        }
        catch (Exception ex) { return (null, ex.Message); }
    }

    public async Task<(PersonaModel? resultado, string? error)> Actualizar(int id, ActualizarPersonaModel model)
    {
        try
        {
            var response = await http.PutAsJsonAsync($"api/personas/{id}", model);
            if (response.IsSuccessStatusCode)
                return (await response.Content.ReadFromJsonAsync<PersonaModel>(), null);
            var err = await response.Content.ReadFromJsonAsync<ApiErrorResponse>();
            return (null, err?.Mensaje ?? $"Error {(int)response.StatusCode}");
        }
        catch (Exception ex) { return (null, ex.Message); }
    }

    public async Task<(bool exito, string? error)> Eliminar(int id)
    {
        try
        {
            var response = await http.DeleteAsync($"api/personas/{id}");
            return response.IsSuccessStatusCode ? (true, null) : (false, $"Error {(int)response.StatusCode}");
        }
        catch (Exception ex) { return (false, ex.Message); }
    }
}
