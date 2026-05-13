create table autor (
    id integer not null auto_increment,
    anio integer,
    nombre varchar(255),
    primary key (id)
);

INSERT INTO autor (nombre, anio) VALUES ('Miguel de Cervantes', 1870);
INSERT INTO autor (nombre, anio) VALUES ('JK Rowling', 1983);