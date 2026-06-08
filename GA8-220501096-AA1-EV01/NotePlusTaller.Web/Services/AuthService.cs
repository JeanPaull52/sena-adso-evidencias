using System.Net;
using System.Net.Http.Json;
using System.Text.Json;
using NotePlusTaller.Web.Auth;
using NotePlusTaller.Web.Models;

namespace NotePlusTaller.Web.Services;

public class AuthService(HttpClient http, LocalStorageService storage, CustomAuthStateProvider authProvider)
{
    private const string SessionKey = "noteplus_session";

    public async Task<(bool exito, string? error)> Login(LoginModel model)
    {
        try
        {
            var response = await http.PostAsJsonAsync("api/auth/login", model);

            if (response.StatusCode == HttpStatusCode.Unauthorized)
                return (false, "Usuario o contraseña incorrectos.");

            if (!response.IsSuccessStatusCode)
                return (false, "Error al conectar con el servidor.");

            var usuario = await response.Content.ReadFromJsonAsync<UsuarioSession>();
            if (usuario is null) return (false, "Respuesta inválida del servidor.");

            await storage.SetItem(SessionKey, JsonSerializer.Serialize(usuario));
            authProvider.NotifyUserAuthentication(usuario);
            return (true, null);
        }
        catch (Exception ex) { return (false, $"Error de conexión: {ex.Message}"); }
    }

    public async Task<(bool exito, string? error)> Registro(RegistroModel model)
    {
        try
        {
            var response = await http.PostAsJsonAsync("api/auth/registro", model);
            if (response.IsSuccessStatusCode) return (true, null);

            if (response.StatusCode == HttpStatusCode.Conflict)
            {
                var err = await response.Content.ReadFromJsonAsync<ApiErrorResponse>();
                return (false, err?.Mensaje ?? "El nombre de usuario ya está en uso.");
            }
            return (false, $"Error {(int)response.StatusCode}");
        }
        catch (Exception ex) { return (false, $"Error de conexión: {ex.Message}"); }
    }

    public async Task Logout()
    {
        await storage.RemoveItem(SessionKey);
        authProvider.NotifyUserLogout();
    }

    public async Task<UsuarioSession?> GetCurrentSession()
    {
        try
        {
            var json = await storage.GetItem(SessionKey);
            return string.IsNullOrWhiteSpace(json)
                ? null
                : JsonSerializer.Deserialize<UsuarioSession>(json);
        }
        catch { return null; }
    }
}
