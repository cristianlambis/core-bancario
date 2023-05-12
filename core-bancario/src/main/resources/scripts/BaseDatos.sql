CREATE TABLE persona (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    edad INTEGER NOT NULL,
    identificacion VARCHAR(20) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE cliente (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cliente_id VARCHAR(50) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    persona_id BIGINT NOT NULL,
    FOREIGN KEY (persona_id) REFERENCES persona (id)
);

CREATE TABLE cuenta (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(12, 2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente (id)
);

CREATE TABLE movimiento (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(12, 2) NOT NULL,
    saldo DECIMAL(12, 2) NOT NULL,
    cuenta_id BIGINT NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta (id)
);
