-- Insertar Roles
INSERT INTO roles (nombre_rol) VALUES ('Administrador');
INSERT INTO roles (nombre_rol) VALUES ('Moderador');
INSERT INTO roles (nombre_rol) VALUES ('Usuario');

-- Insertar Niveles
INSERT INTO niveles (nombre, descripcion, puntos, icono) VALUES ('Principiante', 'Nivel inicial para nuevos usuarios', 0, '⭐');
INSERT INTO niveles (nombre, descripcion, puntos, icono) VALUES ('Intermedio', 'Has ganado suficientes puntos', 500, '⭐⭐');
INSERT INTO niveles (nombre, descripcion, puntos, icono) VALUES ('Avanzado', 'Eres un usuario experimentado', 1500, '⭐⭐⭐');
INSERT INTO niveles (nombre, descripcion, puntos, icono) VALUES ('Experto', 'Máximo nivel alcanzado', 3000, '⭐⭐⭐⭐');

-- Insertar Productos
INSERT INTO productos (nombre, descripcion, puntos) VALUES ('Badge Programador', 'Badge especial para programadores destacados', 100);
INSERT INTO productos (nombre, descripcion, puntos) VALUES ('Badge Comunidad', 'Reconocimiento por participación en comunidades', 150);
INSERT INTO productos (nombre, descripcion, puntos) VALUES ('Certificado Digital', 'Certificado de finalización de retos', 200);
INSERT INTO productos (nombre, descripcion, puntos) VALUES ('Título Especial', 'Título personalizado en el perfil', 300);

-- Insertar Retos
INSERT INTO retos (nombre, descripcion, puntos) VALUES ('Reto JavaScript', 'Completa 10 desafíos de JavaScript', 250);
INSERT INTO retos (nombre, descripcion, puntos) VALUES ('Reto Python', 'Aprende Python resolviendo problemas', 300);

-- Insertar Noticias
INSERT INTO noticias (nombre, autor, descripcion) VALUES (
  'Bienvenida a la Plataforma',
  'Admin',
  'Nos complace anunciar el lanzamiento oficial de nuestra plataforma de gamificación.'
);

-- Insertar Comunidades
INSERT INTO comunidades (nombre, descripcion, imagen, rol_id) VALUES ('Comunidad Backend', 'Comunidad para desarrolladores backend', NULL, 1);
INSERT INTO comunidades (nombre, descripcion, imagen, rol_id) VALUES ('Comunidad Frontend', 'Comunidad para desarrolladores frontend', NULL, 2);
INSERT INTO comunidades (nombre, descripcion, imagen, rol_id) VALUES ('Comunidad Fullstack', 'Comunidad para desarrolladores fullstack', NULL, 3);

-- Insertar Usuarios (2 de prueba)
INSERT INTO usuarios (contraseña, admin, usuario, nombre, apellidos, correo, imagen, descripcion, edad, nivel_id, puntos, puntos_experiencia)
VALUES ('Pass1234', TRUE,  'admin',  'Admin',  'Principal', 'admin@example.com',  NULL, 'Usuario administrador del sistema', '1990-01-01', 4, 3000, 3000);
INSERT INTO usuarios (contraseña, admin, usuario, nombre, apellidos, correo, imagen, descripcion, edad, nivel_id, puntos, puntos_experiencia)
VALUES ('Pass1234', FALSE, 'user1',  'Laura',  'García',   'laura@example.com',  NULL, 'Usuario orientado a frontend',      '1995-05-10', 2,  500,  600);

-- Relación Usuario-Comunidad
INSERT INTO usuario_comunidad (usuario_id, comunidad_id) VALUES
  (1, 1), -- admin en Comunidad Backend
  (2, 2); -- user1 en Comunidad Frontend

-- Insertar Eventos (sin fecha para evitar problemas de formato)
INSERT INTO eventos (nombre, ubicacion, descripcion, imagen, comunidad_id)
VALUES ('Lanzamiento Plataforma', 'Online', 'Evento de presentación de la plataforma', NULL, 1);
INSERT INTO eventos (nombre, ubicacion, descripcion, imagen, comunidad_id)
VALUES ('Meetup Frontend',        'Madrid', 'Encuentro para desarrolladores frontend', NULL, 2);
INSERT INTO eventos (nombre, ubicacion, descripcion, imagen, comunidad_id)
VALUES ('Taller Fullstack',       'Barcelona', 'Taller práctico fullstack',           NULL, 3);

-- Relación Usuario-Evento
INSERT INTO usuario_evento (evento_id, usuario_id) VALUES
  (1, 1),
  (1, 2),
  (2, 2);

-- Relación Usuario-Producto
INSERT INTO usuario_producto (producto_id, usuario_id) VALUES
  (1, 1), -- admin tiene Badge Programador
  (2, 2); -- user1 tiene Badge Comunidad

-- Relación Usuario-Reto
INSERT INTO usuario_reto (reto_id, usuario_id) VALUES
  (1, 2);
