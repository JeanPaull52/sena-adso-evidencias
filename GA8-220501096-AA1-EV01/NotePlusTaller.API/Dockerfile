FROM mcr.microsoft.com/dotnet/sdk:10.0 AS build
WORKDIR /src

COPY NotePlusTaller.API/NotePlusTaller.API.csproj NotePlusTaller.API/
RUN dotnet restore NotePlusTaller.API/NotePlusTaller.API.csproj

COPY NotePlusTaller.API/ NotePlusTaller.API/
WORKDIR /src/NotePlusTaller.API
RUN dotnet publish NotePlusTaller.API.csproj -c Release -o /app/publish --no-restore

FROM mcr.microsoft.com/dotnet/aspnet:10.0 AS runtime
WORKDIR /app
EXPOSE 8080
ENV ASPNETCORE_URLS=http://+:8080
COPY --from=build /app/publish .
ENTRYPOINT ["dotnet", "NotePlusTaller.API.dll"]
