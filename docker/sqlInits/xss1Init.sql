
DROP DATABASE IF EXISTS xss1Web;

-- Crear la base de datos hackWebuser_profileuser_profileuser_profileuser_profile

CREATE DATABASE xss1Web;

-- Usar la base de datos hackWeb
USE xss1Web;

-- Crear tabla product
CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(500),
    description VARCHAR(500),
     price DECIMAL(19, 2),
    available BOOLEAN
);

CREATE TABLE user_type (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE user (
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL,
    password VARCHAR(255) NOT NULL,
    registration_date DATE ,
    user_type_id INT,
    FOREIGN KEY (user_type_id) REFERENCES user_type(id)
);

-- Introducimos roles
INSERT INTO user_type (name) VALUES ("Student");
INSERT INTO user_type (name) VALUES ("Admin");


-- Introducimos usuarios
INSERT INTO user (id, username, is_active, password, registration_date, user_type_id) VALUES
(1, 'user', 1, '$2a$10$NqLlFiR.OzplBL0.M794BeXnyUl/cJEgLtHKlPbuVcAmCBf0DI3Ni', '2024-06-12', 1), -- Su password es asdfasdf
(2, 'admin', 1, '$2a$10$YrDjNt3LHeOzTb9PVXNEVuvE2.gTJpd0BuuZBIQ6mIXgkeTECkE.S', '2024-06-12', 2); -- Su password es Admin123$


-- Introducimos products
INSERT INTO product (name, description, price, available) VALUES
('Laptop', 'A high-performance laptop for gaming and work.', 1299.99, true),
('Smartphone', 'Latest model smartphone with advanced features.', 999.99, true),
('Headphones', 'Noise-cancelling over-ear headphones.', 199.99, true),
('Smartwatch', 'A smartwatch with health tracking features.', 299.99, true),
('Tablet', 'A lightweight tablet with a high-resolution display.', 499.99, true),
('Camera', 'A digital camera with 4K video recording.', 599.99, false),
('Printer', 'A wireless all-in-one printer.', 149.99, false),
('Monitor', 'A 27-inch 4K UHD monitor.', 349.99, false),
('Keyboard', 'A mechanical keyboard with RGB backlighting.', 89.99, false),
('Flag', '3e023bdebbbd2a68d7898d9a6f3e0f45b5dc8eebd1f1bfb5cd8d3c842c9cf073', 49.99, false);


