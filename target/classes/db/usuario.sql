DROP TABLE if EXISTS usuario;

DROP table IF EXISTS usuario_genero;

DROP TABLE if EXISTS usuario_mangas;

create TABLE usuario(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    contrasenia TEXT NOT NULL,
    email TEXT NOT NULL
);
create TABLE usuario_genero(
    id INTEGER,
    genero TEXT NOT NULL
);
create TABLE usuario_mangas(
    id INTEGER,
    genero1 TEXT NOT NULL,
    genero2 TEXT NOT NULL,
    genero3 TEXT NOT NULL,
    nombre TEXT NOT NULL,
    categoria TEXT NOT NULL,
    numCapitulos INTEGER,
    autor TEXT NOT NULL,
    dibujante TEXT NOT NULL,
    enlace TEXT NOT NULL,
    direccionImagen TEXT NOT NULL
);

insert INTO usuario(nombre,contrasenia,email)VALUES
('ernesto','ernesto','ernesto'),
('pedrito','pedrito','pedrito'),
('goku','goku','goku'),
('gg','gg','gg');

insert INTO usuario_genero(id, genero)VALUES
(1,'Shonen'),
(1,'Seinen'),
(1,'Romance'),
(2,'Accion'),
(2,'Romance'),
(2,'Seinen'),
(3,'Horror'),
(3,'Romance'),
(3,'Accion'),
(3,'Shonen'),
(4,'Seinen'),
(4,'Romance'),
(4,'Accion');

insert into usuario_mangas(id,genero1,genero2,genero3,nombre,categoria,numCapitulos,autor,dibujante,enlace,direccionImagen)values
(3,'Accion','Shonen','Combates','dragon ball super','En publicacion',104,'Akira Toriyama','Toyotaro','https://mangaplus.shueisha.co.jp/titles/200025','src/main/resources/images/dragonBall.jpeg'),
(4,'Seinen','Accion Oscura','Accion','berserk','En publicacion',374,'Kentaro Miura','Kentaro Miura','https://mangalector.com/manga/berserk','src/main/resources/images7berserk.jpg'),
(4,'Seinen','Accion','Accion Oscura','claymore','Concluida',155,'Norihiro Yagi','Norihiro Yagi','https://mangaplus.shueisha.co.jp/titles/200005','src/main/resources/images/claymore.jpg'),
(4,'Seinen','Deportes','Drama','slam dunk','Concluida',278,'Takehiko Inue','Takehiko Inue','https://esmanga.net/manga/slam-dunk','src/main/resources/images/slamDunk.jpg');
