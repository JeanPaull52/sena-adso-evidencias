using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using NotePlusTaller.Web;
using NotePlusTaller.Web.Auth;
using NotePlusTaller.Web.Services;

var builder = WebAssemblyHostBuilder.CreateDefault(args);
builder.RootComponents.Add<App>("#app");
builder.RootComponents.Add<HeadOutlet>("head::after");

var apiBaseUrl = builder.Configuration["ApiBaseUrl"] ?? "http://localhost:5000";

builder.Services.AddScoped(_ => new HttpClient { BaseAddress = new Uri(apiBaseUrl) });

// Auth
builder.Services.AddScoped<LocalStorageService>();
builder.Services.AddScoped<CustomAuthStateProvider>();
builder.Services.AddScoped<AuthenticationStateProvider>(sp =>
    sp.GetRequiredService<CustomAuthStateProvider>());
builder.Services.AddAuthorizationCore();

// Servicios de API
builder.Services.AddScoped<AuthService>();
builder.Services.AddScoped<ApiNacionalidadService>();
builder.Services.AddScoped<ApiPersonaService>();
builder.Services.AddScoped<ApiRolService>();

await builder.Build().RunAsync();
