using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using NotePlusTaller.API.Services;

namespace NotePlusTaller.API.Controllers;

[AllowAnonymous]
[ApiController]
[Route("api/[controller]")]
public class RolesController(RolService service) : ControllerBase
{
    [HttpGet]
    public async Task<IActionResult> GetAll() =>
        Ok(await service.GetAll());

    [HttpGet("{id:int}")]
    public async Task<IActionResult> GetById(int id)
    {
        var rol = await service.GetById(id);
        return rol is null ? NotFound() : Ok(rol);
    }
}
