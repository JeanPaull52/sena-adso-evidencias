-- ============================================================
-- Proyecto: RestApiAuth
-- Base de datos: registro_estudiantes
-- Motor: MySQL
-- ------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

-- ------------------------------------------------------------
-- Creacion de la base de datos
-- ------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `registro_estudiantes`
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `registro_estudiantes`;

-- ------------------------------------------------------------
-- Tabla: usuarios
-- Almacena los usuarios que pueden autenticarse en el sistema.
-- La contrasena se guarda cifrada (password_hash).
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario`     INT NOT NULL AUTO_INCREMENT,
  `nombre_usuario` VARCHAR(50)  NOT NULL,
  `email`          VARCHAR(100) NOT NULL,
  `contrasena`     VARCHAR(255) NOT NULL,
  `activo`         TINYINT(1) DEFAULT 1,
  `creado_el`      TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `actualizado_el` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Tabla: sesiones
-- Guarda los tokens de las sesiones iniciadas por los usuarios.
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sesiones` (
  `id_sesion`  INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT DEFAULT NULL,
  `token`      VARCHAR(255) NOT NULL,
  `ip_address` VARCHAR(45) DEFAULT NULL,
  `inicio`     TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `fin`        TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id_sesion`),
  UNIQUE KEY `token` (`token`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `sesiones_ibfk_1` FOREIGN KEY (`id_usuario`)
    REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Tabla: estudiante
-- Almacena los estudiantes registrados desde el sistema.
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `estudiante` (
  `id_estudiante` INT NOT NULL AUTO_INCREMENT,
  `nombre`        VARCHAR(100) NOT NULL,
  `correo`        VARCHAR(100) NOT NULL,
  `celular`       VARCHAR(20),
  `direccion`     VARCHAR(200),
  PRIMARY KEY (`id_estudiante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================