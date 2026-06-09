FROM mcr.microsoft.com/dotnet/sdk:10.0 AS build
WORKDIR /src

COPY GA8-220501096-AA1-EV01/NotePlusTaller.Web/NotePlusTaller.Web.csproj GA8-220501096-AA1-EV01/NotePlusTaller.Web/
RUN dotnet restore GA8-220501096-AA1-EV01/NotePlusTaller.Web/NotePlusTaller.Web.csproj

COPY GA8-220501096-AA1-EV01/NotePlusTaller.Web/ GA8-220501096-AA1-EV01/NotePlusTaller.Web/
WORKDIR /src/GA8-220501096-AA1-EV01/NotePlusTaller.Web
RUN dotnet publish NotePlusTaller.Web.csproj -c Release -o /app/publish --no-restore

FROM nginx:alpine AS runtime
EXPOSE 8080
COPY GA8-220501096-AA1-EV01/NotePlusTaller.Web/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/publish/wwwroot /usr/share/nginx/html
