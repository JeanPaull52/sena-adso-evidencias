using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using NotePlusTaller.API.DTOs;
using NotePlusTaller.API.Services;

namespace NotePlusTaller.API.Controllers;

[AllowAnonymous]
[ApiController]
[Route("api/[controller]")]
public class PersonasController(PersonaService service) : ControllerBase
{
    [HttpGet]
    public async Task<IActionResult> GetAll()
    {
        var personas = await service.GetAll();
        return Ok(personas.Select(PersonaService.ToDto));
    }

    [HttpGet("{id:int}")]
    public async Task<IActionResult> GetById(int id)
    {
        var persona = await service.GetById(id);
        return persona is null ? NotFound() : Ok(PersonaService.ToDto(persona));
    }

    [HttpGet("documento/{numeroDocumento}")]
    public async Task<IActionResult> GetByNumeroDocumento(string numeroDocumento)
    {
        var persona = await service.GetByNumeroDocumento(numeroDocumento);
        return persona is null ? NotFound() : Ok(PersonaService.ToDto(persona));
    }

    [HttpPost]
    public async Task<IActionResult> Create([FromBody] CrearPersonaDto dto)
    {
        var creada = await service.Create(dto);
        return CreatedAtAction(nameof(GetById), new { id = creada.IDPersona }, PersonaService.ToDto(creada));
    }

    [HttpPut("{id:int}")]
    public async Task<IActionResult> Update(int id, [FromBody] ActualizarPersonaDto dto)
    {
        var actualizada = await service.Update(id, dto);
        return actualizada is null ? NotFound() : Ok(PersonaService.ToDto(actualizada));
    }

    [HttpDelete("{id:int}")]
    public async Task<IActionResult> Delete(int id)
    {
        var eliminada = await service.Delete(id);
        return eliminada ? NoContent() : NotFound();
    }
}
