-- ═══════════════════════════════════════════════════════════
-- Script: Datos de ejemplo (seed data)
-- Inserta 20 estudiantes de muestra para pruebas
-- ═══════════════════════════════════════════════════════════

USE ejemplo_colegio;
GO

INSERT INTO estudiante (nombre, correo, celular, direccion) VALUES
('Juan Pablo Ramírez', 'juan.ramirez@gmail.com', '3104567890', 'Calle 45 #12-34, Bogotá'),
('María Fernanda López', 'mariaf.lopez@hotmail.com', '3209876543', 'Carrera 15 #78-21, Medellín'),
('Carlos Andrés Gómez', 'cgomez@outlook.com', '3156789012', 'Avenida 68 #34-56, Bogotá'),
('Laura Catalina Pérez', 'laura.perez@gmail.com', '3018765432', 'Calle 80 #25-10, Cali'),
('Andrés Felipe Torres', 'aftorres@yahoo.com', '3112345678', 'Carrera 50 #12-89, Barranquilla'),
('Diana Marcela Ruiz', 'diana.ruiz@gmail.com', '3145678901', 'Calle 100 #15-67, Bogotá'),
('Sebastián Castro', 'scastro@hotmail.com', '3023456789', 'Carrera 7 #45-23, Bogotá'),
('Valentina Morales', 'vmorales@gmail.com', '3187654321', 'Calle 26 #89-12, Medellín'),
('Daniel Esteban Rojas', 'danielrojas@outlook.com', '3076543210', 'Avenida Caracas #56-78, Bogotá'),
('Camila Andrea Vargas', 'camila.vargas@gmail.com', '3134567890', 'Carrera 30 #67-45, Cali'),
('Mateo Alejandro Díaz', 'mateodiaz@yahoo.com', '3198765432', 'Calle 53 #20-15, Bogotá'),
('Isabella Restrepo', 'isabella.r@gmail.com', '3167890123', 'Carrera 43 #10-98, Medellín'),
('Santiago Herrera', 'sherrera@hotmail.com', '3045678901', 'Calle 72 #11-23, Bogotá'),
('Sofía Alejandra Mejía', 'sofia.mejia@gmail.com', '3123456789', 'Avenida 19 #100-45, Bogotá'),
('Nicolás Sánchez', 'nsanchez@outlook.com', '3056789012', 'Carrera 9 #78-34, Cali'),
('Gabriela Ortiz', 'gabriela.ortiz@gmail.com', '3189012345', 'Calle 116 #20-67, Bogotá'),
('Esteban Cárdenas', 'ecardenas@yahoo.com', '3092345678', 'Carrera 60 #45-12, Barranquilla'),
('Mariana Quintero', 'mquintero@gmail.com', '3178901234', 'Calle 34 #56-78, Medellín'),
('Felipe Augusto Niño', 'felipe.nino@hotmail.com', '3067890123', 'Avenida Boyacá #80-23, Bogotá'),
('Paula Andrea Bermúdez', 'paula.bermudez@gmail.com', '3145678902', 'Carrera 11 #93-45, Bogotá');
GO