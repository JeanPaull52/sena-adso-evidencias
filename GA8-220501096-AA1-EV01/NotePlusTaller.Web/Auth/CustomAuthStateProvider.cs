using System.Security.Claims;
using System.Text.Json;
using Microsoft.AspNetCore.Components.Authorization;
using NotePlusTaller.Web.Models;
using NotePlusTaller.Web.Services;

namespace NotePlusTaller.Web.Auth;

public class CustomAuthStateProvider(LocalStorageService storage) : AuthenticationStateProvider
{
    private static readonly AuthenticationState _anonymous =
        new(new ClaimsPrincipal(new ClaimsIdentity()));

    private const string SessionKey = "noteplus_session";

    public override async Task<AuthenticationState> GetAuthenticationStateAsync()
    {
        try
        {
            var json = await storage.GetItem(SessionKey);
            if (string.IsNullOrWhiteSpace(json)) return _anonymous;

            var session = JsonSerializer.Deserialize<UsuarioSession>(json);
            if (session is null) return _anonymous;

            return BuildState(session);
        }
        catch { return _anonymous; }
    }

    public void NotifyUserAuthentication(UsuarioSession session)
        => NotifyAuthenticationStateChanged(Task.FromResult(BuildState(session)));

    public void NotifyUserLogout()
        => NotifyAuthenticationStateChanged(Task.FromResult(_anonymous));

    private static AuthenticationState BuildState(UsuarioSession session)
    {
        var claims = new[]
        {
            new Claim(ClaimTypes.NameIdentifier, session.IDUsuario.ToString()),
            new Claim(ClaimTypes.Name, session.NombreUsuario),
            new Claim(ClaimTypes.Role, session.NombreRol),
            new Claim(ClaimTypes.Email, session.CorreoElectronico),
            new Claim("NombreCompleto", session.NombreCompleto)
        };
        var identity = new ClaimsIdentity(claims, "localStorage");
        return new AuthenticationState(new ClaimsPrincipal(identity));
    }
}
