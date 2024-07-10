
DROP DATABASE IF EXISTS hackWeb;

-- Crear la base de datos hackWebuser_profileuser_profileuser_profileuser_profile

CREATE DATABASE hackWeb;

-- Usar la base de datos hackWeb
USE hackWeb;

-- Crear tabla user_type
CREATE TABLE user_type (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE country (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- Crear tabla user
CREATE TABLE user (
	id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL,
    password VARCHAR(255) NOT NULL,
    registration_date DATE NOT NULL,
    user_type_id INT,
    FOREIGN KEY (user_type_id) REFERENCES user_type(id)
);

CREATE TABLE password_reset_token(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255),
    user_id INT,
    expiry_date DATE,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE user_profile (
    id INT PRIMARY KEY,
    first_name VARCHAR(50) ,
    last_name VARCHAR(50),
    age INT,
    profile_photo VARCHAR(255),
    country_id INT,
    FOREIGN KEY (id) REFERENCES user(id),
    FOREIGN KEY(country_id) REFERENCES country(id)
);

CREATE TABLE type_attack (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE attack (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    difficulty VARCHAR(50) NOT NULL,
    type_attack_id INT NOT NULL,
    description VARCHAR(10000),
    posted_date DATE,
    question VARCHAR(255),
    answer VARCHAR(255),
    docker_image_name  VARCHAR(50) UNIQUE NOT NULL,
	FOREIGN KEY (type_attack_id) references type_attack(id)
);

CREATE TABLE user_attack(
	user_id INT NOT NULL,
    attack_id INT NOT NULL,
    saved boolean,
    completed boolean,
    PRIMARY KEY (user_id,attack_id),
    FOREIGN KEY(user_id) references user(id),
	FOREIGN KEY(attack_id) references attack(id)
);

CREATE TABLE video (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    difficulty VARCHAR(50) NOT NULL,
    video_file VARCHAR(255) UNIQUE,
    type ENUM('PRE', 'SOLUTION') NOT NULL,
    attack_id INT,
    type_attack_id INT,
    FOREIGN KEY(type_attack_id) references type_attack(id),
    FOREIGN KEY(attack_id) references attack(id)
    );

CREATE TABLE user_video(
	user_id INT NOT NULL,
    video_id INT NOT NULL,
    saved tinyint(1),
    completed tinyint(1),
    PRIMARY KEY(user_id, video_id),
    FOREIGN KEY(user_id) references user(id),
    FOREIGN KEY(video_id) references video(id)
);


CREATE TABLE container_info(
	id INT NOT NULL AUTO_INCREMENT,
    container_id VARCHAR(255) NOT NULL,
    web_sockify_port INT ,
    container_port INT  NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    user_id INT,
    attack_id INT,

    PRIMARY KEY(id),
    FOREIGN KEY(user_id) references user(id),
    FOREIGN KEY(attack_id) references attack(id)
);


-- Introducimos roles
INSERT INTO user_type (name) VALUES ("Admin");
INSERT INTO user_type (name) VALUES ("Student");


-- Introducimos Tipos de ataques
INSERT INTO hackweb.type_attack (id, name) VALUES
(1, 'Sql Injection'),
(2, 'Path traversal'),
(3, 'XSS');

-- Introducimos ataques
INSERT INTO hackweb.attack (id, title, difficulty, type_attack_id, question, answer, docker_image_name) VALUES
(1, 'The first sql attack', 'Easy', 1,'Introduce the flag', '3e023bdebbbd2a68d7898d9a6f3e0f45b5dc8eebd1f1bfb5cd8d3c842c9cf073', 'sqli-lab'),
(2, 'The second sql attack', 'Medium', 1, 'How many columns are there in the table product', 'f8b7b3a7c6f7e2d0a4a6c8cfa3a88b0e4e4f5c6e9a2b7e5d6c9f8e7a9d3c4a8b', 'sqli-lab2'),
(3, 'The third sql attack', 'Hard', 1, 'Introduce the flag', 'd7a5b3e1f2c4a6d8e0c9b4f5a7c8d9b2e1a3f4c5b6d8a9e0f1b2c3d4e5f6a7b8', 'sqli-lab3'),
(4, 'The final sql attack', 'Medium', 1, 'Introduce the flag', 'a4c5d6e7b8a9d0c1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4d5', 'sqli-lab4');

INSERT INTO hackweb.attack (id, title, difficulty, type_attack_id, question, answer, description, docker_image_name) VALUES
(5, 'Basic XSS attack', 'Easy', 3, 'Introduce el flag', '$2b$12$O4s8yJdV81rPQoq6xUQ9iOnz7b7RnksD58g1EmUGkO4i.CdP4S2Bi', 'Este laboratorio está diseñado para practicar un ejemplo básico del ataque de Cross-Site Scripting (XSS). En este entorno controlado, un administrador se logueará automáticamente cada 2 minutos. El objetivo del ejercicio es explotar una vulnerabilidad XSS en la aplicación web para obtener acceso a la cuenta del administrador.', 'vnc-lab');


-- Introducimos usuarios
INSERT INTO hackweb.user (id, email, is_active, password, registration_date, user_type_id) VALUES
(1, 'test@gmail.com', 1, '$2a$10$NqLlFiR.OzplBL0.M794BeXnyUl/cJEgLtHKlPbuVcAmCBf0DI3Ni', '2024-06-12', 1),
(2, 'egarmar1@teleco.upv.es', 1, '$2a$10$E420hHUt.4JfNNz.kJqt7OOTMPJMg8AXTpgGmpbMWv7JXuhJsIFu', '2024-06-12', 2),
(3, 'adsf@f.com', 1, '$2a$10$yfA22NM5Y47e5ieyUbAXueQJZsNfKba6J7lWZ1hNq6ffPDlpJ9G', '2024-06-12', 2),
(4, 'errgar2001@gmail.com', 1, '$2a$10$eDvc62s90uqZzZsNjHszhUonvTbnCEaBabkJ/0ksEKyO2TTNDOW', '2024-06-12', 2);

-- Introducimos user_profiles
INSERT INTO hackweb.user_profile (id) VALUES
(1),
(2),
(3),
(4);

-- Introducimos user_attacks
INSERT INTO hackweb.user_attack (user_id, attack_id, saved, completed) VALUES
(1, 1, 1, 1),
(1, 2, 1, 0),
(2, 2, 0, 1),
(3, 2, 1, 1),
(4, 2, 1, 0);

-- Introducimos videos
INSERT INTO hackweb.video (title, difficulty, video_file, type, attack_id, type_attack_id) VALUES
("SQLI1 pre video", "Easy", "previoSQLI1.mp4", 'PRE', 1, 1),
("SQLI1 solution video", "Easy", "solutionSQLI1.mp4", 'SOLUTION', 1, 1);

-- Introducimos user_video
INSERT into user_video (user_id, video_id, saved, completed) VALUES
(1,1,1,0);
