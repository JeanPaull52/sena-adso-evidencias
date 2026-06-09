#!/bin/bash
set -e

cd GA8-220501096-AA1-EV01
dotnet publish NotePlusTaller.Web/NotePlusTaller.Web.csproj -c Release -o ../vercel_output
cp -r vercel_output/wwwroot/* ../public/
