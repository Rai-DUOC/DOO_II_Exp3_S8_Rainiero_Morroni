CREATE DATABASE Cine_DB;
USE Cine_DB;


CREATE TABLE Cartelera (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    director VARCHAR(50) NOT NULL,
    anio INT NOT NULL,
    duracion INT NOT NULL,
    genero ENUM('Acción', 'Comedia', 'Drama', 'Ciencia Ficción', 'Terror', 'Suspenso') NOT NULL
);