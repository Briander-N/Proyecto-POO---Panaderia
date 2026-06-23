

CREATE TABLE usuarios(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    correo VARCHAR(100),
    password VARCHAR(100),
    rol VARCHAR(20)
);

CREATE TABLE productos(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    categoria VARCHAR(50),
    precio NUMERIC(10,2),
    stock INT
);

CREATE TABLE ventas(
    id SERIAL PRIMARY KEY,

    usuario_id INT NOT NULL,

    producto_id INT NOT NULL,

    cantidad INT NOT NULL,

    total NUMERIC(10,2),

    FOREIGN KEY(usuario_id)
        REFERENCES usuarios(id),

    FOREIGN KEY(producto_id)
        REFERENCES productos(id)
);



