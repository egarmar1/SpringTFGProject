
DROP DATABASE IF EXISTS sqliWeb;

-- Crear la base de datos hackWebuser_profileuser_profileuser_profileuser_profile

CREATE DATABASE sqliWeb;

-- Usar la base de datos hackWeb
USE sqliWeb;

-- Crear tabla product
CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    description VARCHAR(255),
     price DECIMAL(19, 2),
    available BOOLEAN
);

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


