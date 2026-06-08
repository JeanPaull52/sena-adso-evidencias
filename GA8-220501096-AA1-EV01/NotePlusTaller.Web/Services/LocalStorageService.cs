using Microsoft.JSInterop;

namespace NotePlusTaller.Web.Services;

public class LocalStorageService(IJSRuntime js)
{
    public async Task SetItem(string key, string value) =>
        await js.InvokeVoidAsync("localStorage.setItem", key, value);

    public async Task<string?> GetItem(string key) =>
        await js.InvokeAsync<string?>("localStorage.getItem", key);

    public async Task RemoveItem(string key) =>
        await js.InvokeVoidAsync("localStorage.removeItem", key);
}
