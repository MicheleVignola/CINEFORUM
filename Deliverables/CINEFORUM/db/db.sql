DROP DATABASE IF EXISTS cineforum;

CREATE DATABASE cineforum;

DROP USER IF EXISTS 'cineadmin'@'localhost';
CREATE USER 'cineadmin'@'localhost' IDENTIFIED BY 'Cine.123';
GRANT ALL ON cineforum.* TO 'cineadmin'@'localhost';

USE cineforum;


-- Creazione tabelle
DROP TABLE IF EXISTS utente;
CREATE TABLE utente(
	Username CHAR(20) NOT NULL,
	Email CHAR(50) NOT NULL,
	Password CHAR(255) NOT NULL,
	Ruolo ENUM('registered', 'admin') NOT NULL DEFAULT 'registered',
	PRIMARY KEY(Username),
	UNIQUE(Email)
);

DROP TABLE IF EXISTS film;
CREATE TABLE film(
	CodiceFilm INT UNSIGNED NOT NULL AUTO_INCREMENT,
	Titolo VARCHAR(30) NOT NULL,
	Genere VARCHAR(20) NOT NULL,
	DataUscita DATE,
	Descrizione VARCHAR(255),
	Durata SMALLINT UNSIGNED,
	Immagine mediumblob DEFAULT NULL,
	PRIMARY KEY(CodiceFilm)
);

DROP TABLE IF EXISTS commento_film;
CREATE TABLE commento_film(
	Id_commento INT UNSIGNED NOT NULL AUTO_INCREMENT,
	Commento VARCHAR(255) NOT NULL,
	Orario DATETIME NOT NULL,
	Username CHAR(20) NOT NULL,
	CodiceFilm INT UNSIGNED NOT NULL,
	PRIMARY KEY(Id_commento),
	FOREIGN KEY (Username) REFERENCES utente(Username)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(CodiceFilm) REFERENCES film(CodiceFilm)
		ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS richiesta_film;
CREATE TABLE richiesta_film(
	Id_richiesta INT UNSIGNED NOT NULL AUTO_INCREMENT,
	Commento VARCHAR(255),
	Esito ENUM('in attesa', 'accettata', 'rifiutata') DEFAULT 'in attesa' NOT NULL,
	Username CHAR(20) NOT NULL,
	Titolo VARCHAR(30) NOT NULL,
	PRIMARY KEY(Id_richiesta),
	FOREIGN KEY (Username) REFERENCES utente(Username)
		ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS lista;
CREATE TABLE lista(
	CodiceFilm INT UNSIGNED NOT NULL,
	Username CHAR(20) NOT NULL,
	Voto TINYINT UNSIGNED NOT NULL,
	Categoria ENUM('visti', 'in programma') NOT NULL,
	PRIMARY KEY(CodiceFilm, Username),
	FOREIGN KEY (Username) REFERENCES utente(Username)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(CodiceFilm) REFERENCES film(CodiceFilm)
		ON DELETE CASCADE ON UPDATE CASCADE
);


insert into utente values ('vyncy98', 'vyncy98@gmail.com', '4fafee4c9557e135d879f7953ce1875602ab875308b20367f5f34e6898d46afc08e99d3fe71ff5abe9cd850f154af8691a4a05fc035a9498191fba10a8002ead', 'admin');
insert into utente values ('michele97', 'michele@gmail.com', '4fafee4c9557e135d879f7953ce1875602ab875308b20367f5f34e6898d46afc08e99d3fe71ff5abe9cd850f154af8691a4a05fc035a9498191fba10a8002ead', 'admin');
insert into utente values ('giuseppeandreozzi', 'giuseppe@gmail.com', '4fafee4c9557e135d879f7953ce1875602ab875308b20367f5f34e6898d46afc08e99d3fe71ff5abe9cd850f154af8691a4a05fc035a9498191fba10a8002ead', 'admin');


insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('National Lampoons', 'Comedy', '2020-05-27', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 127);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Cloudy', 'Fantasy', '2020-04-12', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 137);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Circle', 'Drama', '2020-11-07', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 144);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Little Sister', 'Comedy', '2019-12-19', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 87);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('The Oscar', 'Drama', '2020-07-13', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 115);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Korczak', 'Drama', '2020-09-16', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 147);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Confessions', 'Documentary', '2020-08-22', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 124);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Visual Acoustics', 'Documentary', '2020-01-07', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 81);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Sex and Death', 'Comedy', '2020-03-28', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 107);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Great Feeling', 'Comedy', '2019-12-20', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 76);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Into the Blue 2', 'Thriller', '2020-04-13', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 146);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Better Than Chocolate', 'Romance', '2020-02-12', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 140);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Monk and the Fish', 'Animation', '2020-11-27', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 112);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('They Made Me a Criminal', 'Drama', '2020-10-04', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 84);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Flatliners', 'Thriller', '2020-10-21', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 76);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Factory Girl', 'Drama', '2020-02-02', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 70);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Whisper of the Heart', 'Romance', '2020-06-19', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 94);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Treasure Island', 'Adventure', '2020-05-05', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 124);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Pulse (Kairo)', 'Thriller', '2020-07-24', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 74);
insert into film (Titolo, Genere, DataUscita, Descrizione, Durata) values ('Legend of Sanctuary', 'Animation', '2020-11-21', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent leo nibh, auctor et porttitor in, mollis ac justo. Morbi auctor ex vitae elementum tempus. Integer hendrerit volutpat leo et pharetra.', 61);

insert into commento_film (Commento, Orario, Username, CodiceFilm) values ('Film horror adatto ai soli adulti', '2020-01-01 00:00:00', 'vyncy98', 1);
insert into commento_film (Commento, Orario, Username, CodiceFilm) values ('Film horror adatto ai soli adulti', '2020-01-01 00:00:00', 'vyncy98', 2);

insert into lista (CodiceFilm, Username, Voto, Categoria) values (1, 'vyncy98', 8, 'in programma');
insert into lista (CodiceFilm, Username, Voto, Categoria) values (2, 'vyncy98', 3, 'in programma');
insert into lista (CodiceFilm, Username, Voto, Categoria) values (3, 'vyncy98', 8, 'visti');
insert into lista (CodiceFilm, Username, Voto, Categoria) values (4, 'vyncy98', 3, 'visti');

insert into richiesta_film(Commento, Esito, Username, Titolo) values ('Urgenteee', 'in attesa', 'vyncy98', 'Natale a Rio');