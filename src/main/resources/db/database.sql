
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
    id INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    difficulty VARCHAR(50) NOT NULL,
    type_attack_id INT,
    description VARCHAR(10000),
    posted_date DATE,
    pre_video_id INT,
    solution_video_id INT,
	FOREIGN KEY (type_attack_id) references type_attack(id),
    FOREIGN KEY (pre_video_id) references attack(id),
    FOREIGN KEY (solution_video_id) references attack(id)

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
    attack_id INT,
    type_attack_id INT,
    FOREIGN KEY(attack_id) references attack(id),
    FOREIGN KEY(type_attack_id) references type_attack(id)
    );

CREATE TABLE user_video(
	user_id INT NOT NULL,
    video_id INT NOT NULL,
    saved tinyint(1),
    completed tinyint(1),
    PRIMARY KEY(user_id, video_id)
    FOREIGN KEY(user_id) references user(id),
    FOREIGN KEY(vide_id) references video(id)
);
-- Introducimos roles
INSERT INTO user_type (name) VALUES ("Admin");
INSERT INTO user_type (name) VALUES ("Student");


-- Introducimos Tipos de ataques
INSERT INTO hackweb.type_attack (id, name) VALUES
(1, 'Sql Injection'),
(2, 'Path traversal');


-- Introducimos ataques
INSERT INTO hackweb.attack (id, title, difficulty, type_attack_id, pre_video_id, solution_video_id) VALUES
(1, 'The first sql attack', 'Easy', 1, 1, 1),
(2, 'The second sql attack', 'Medium', 1, 1, null),
(3, 'The third sql attack', 'Hard', 1, null, null),
(4, 'The final sql attack', 'Medium', 1, null, null);


-- Introducimos usuarios
INSERT INTO hackweb.user (id, email, is_active, password, registration_date, user_type_id) VALUES
(1, 'test@gmail.com', 1, '$2a$10$NqLlFiR.OzplBL0.M794BeXnyUl/cJEgLtHKlPbuVcAmCBf0DI3Ni', '2024-06-12', 2),
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
INSERT INTO hackweb.video (title, difficulty, video_file, attack_id, type_attack_id) VALUES
("The first SQL video file", "Easy", "video.mp4", 1, 1);

-- Introducimos user_video
INSERT into user_video (user_id, video_id, saved, completed) VALUES
(1,1,1,0);

