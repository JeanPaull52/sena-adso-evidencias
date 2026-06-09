using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using NotePlusTaller.API.Models;
using NotePlusTaller.API.Services;

namespace NotePlusTaller.API.Controllers;

[AllowAnonymous]
[ApiController]
[Route("api/[controller]")]
public class NacionalidadesController(NacionalidadService service) : ControllerBase
{
    [HttpGet]
    public async Task<IActionResult> GetAll() =>
        Ok(await service.GetAll());

    [HttpGet("{id:int}")]
    public async Task<IActionResult> GetById(int id)
    {
        var nacionalidad = await service.GetById(id);
        return nacionalidad is null ? NotFound() : Ok(nacionalidad);
    }

    [HttpPost]
    public async Task<IActionResult> Create([FromBody] Nacionalidad nacionalidad)
    {
        var creada = await service.Create(nacionalidad);
        return CreatedAtAction(nameof(GetById), new { id = creada.IDNacionalidad }, creada);
    }

    [HttpPut("{id:int}")]
    public async Task<IActionResult> Update(int id, [FromBody] Nacionalidad nacionalidad)
    {
        var actualizada = await service.Update(id, nacionalidad);
        return actualizada is null ? NotFound() : Ok(actualizada);
    }

    [HttpDelete("{id:int}")]
    public async Task<IActionResult> Delete(int id)
    {
        var eliminada = await service.Delete(id);
        return eliminada ? NoContent() : NotFound();
    }
}
