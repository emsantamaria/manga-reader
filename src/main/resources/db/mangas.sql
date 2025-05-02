DROP TABLE if EXISTS manga ;

DROP TABLE if EXISTS manga_generos ;

CREATE TABLE manga(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    categoria TEXT NOT NULL,
    numCapitulos INTEGER,
    autor TEXT NOT NULL,
    dibujante TEXT NOT NULL,
    enlace TEXT NOT NULL,
    direccionImagen TEXT NOT NULL
);

create TABLE manga_generos(
    id INTEGER,
    genero TEXT NOT NULL
);