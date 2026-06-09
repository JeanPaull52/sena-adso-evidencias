FROM mcr.microsoft.com/dotnet/sdk:10.0 AS build
WORKDIR /src

COPY NotePlusTaller.Web/NotePlusTaller.Web.csproj NotePlusTaller.Web/
RUN dotnet restore NotePlusTaller.Web/NotePlusTaller.Web.csproj

COPY NotePlusTaller.Web/ NotePlusTaller.Web/
WORKDIR /src/NotePlusTaller.Web
RUN dotnet publish NotePlusTaller.Web.csproj -c Release -o /app/publish --no-restore

FROM nginx:alpine AS runtime
EXPOSE 8080
COPY NotePlusTaller.Web/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/publish/wwwroot /usr/share/nginx/html
