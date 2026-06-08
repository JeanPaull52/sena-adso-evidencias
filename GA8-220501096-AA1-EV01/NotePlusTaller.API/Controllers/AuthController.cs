using Microsoft.AspNetCore.Mvc;
using NotePlusTaller.API.DTOs;
using NotePlusTaller.API.Services;

namespace NotePlusTaller.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AuthController(AuthService authService) : ControllerBase
{
    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] LoginDto dto)
    {
        var usuario = await authService.Login(dto);
        if (usuario is null)
            return Unauthorized(new { mensaje = "Credenciales incorrectas." });
        return Ok(usuario);
    }

    [HttpPost("registro")]
    public async Task<IActionResult> Registro([FromBody] RegistroDto dto)
    {
        var (resultado, error) = await authService.Registro(dto);
        if (error is not null)
            return Conflict(new { mensaje = error });
        return CreatedAtAction(null, resultado);
    }
}
