use panaderia_db

CREATE DATABASE IF NOT EXISTS panaderia_db;
USE panaderia_db;

CREATE TABLE usuarios (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          correo VARCHAR(100) NOT NULL UNIQUE,
                          password VARCHAR(100) NOT NULL,
                          rol VARCHAR(20) NOT NULL CHECK (rol IN ('ADMIN','CAJERO','REPORTES'))
);

CREATE TABLE producto (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          precio DECIMAL(10,2) NOT NULL,
                          stock INT NOT NULL
);

CREATE TABLE venta (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       id_usuario INT NOT NULL,
                       id_producto INT NOT NULL,
                       cantidad INT NOT NULL,
                       fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
                       FOREIGN KEY (id_producto) REFERENCES producto(id)
);

-- Usuarios de prueba (uno por cada rol, para probar el login)
INSERT INTO usuarios (nombre, correo, password, rol) VALUES
                                                         ('Admin Principal', 'admin@panshito.com', 'admin123', 'ADMIN'),
                                                         ('Cajero Uno', 'cajero@panshito.com', 'cajero123', 'CAJERO'),
                                                         ('Reportes Uno', 'reportes@panshito.com', 'reportes123', 'REPORTES');

-- Productos de prueba
INSERT INTO producto (nombre, precio, stock) VALUES
                                                 ('Pan de Yuca', 0.50, 100),
                                                 ('Torta de Chocolate', 15.00, 10),
                                                 ('Empanada de Verde', 1.20, 50);


