-- ═══════════════════════════════════════════════════════════
-- Script: Creación de la base de datos y tabla estudiante
-- BD: ejemplo_colegio
-- ═══════════════════════════════════════════════════════════

-- Crear la base de datos si no existe
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'ejemplo_colegio')
BEGIN
    CREATE DATABASE ejemplo_colegio;
END
GO

USE ejemplo_colegio;
GO

-- Crear la tabla estudiante
CREATE TABLE estudiante (
    id_estudiante INT IDENTITY(1,1) PRIMARY KEY,
    nombre        VARCHAR(50) NOT NULL,
    correo        VARCHAR(50) NOT NULL,
    celular       VARCHAR(20) NOT NULL,
    direccion     VARCHAR(50) NOT NULL
);
GO