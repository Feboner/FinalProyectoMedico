create table libro (
    id integer not null auto_increment,
    anio integer,
    titulo varchar(255),
    autor_id integer,
    primary key (id)
);

INSERT INTO libro (anio, titulo, autor_id) VALUES (1930, 'Quijote de la Mancha', 1);
INSERT INTO libro (anio, titulo, autor_id) VALUES (1995, 'Harry Potter I', 2);
INSERT INTO libro (anio, titulo, autor_id) VALUES (1998, 'Harry Potter II', 2);
