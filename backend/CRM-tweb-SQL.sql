-- Initialization
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;



-- Create the CRMtweb database
CREATE SCHEMA CRMtweb;
ALTER SCHEMA CRMtweb OWNER TO admin;

SET default_tablespace = '';
SET default_table_access_method = heap;



-- Create the users table and create ID sequence
CREATE TABLE CRMtweb.users (
    id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);
ALTER TABLE CRMtweb.users OWNER TO admin;

CREATE SEQUENCE CRMtweb.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.users_id_seq OWNER TO admin;
ALTER SEQUENCE CRMtweb.users_id_seq OWNED BY CRMtweb.users.id;



-- Create the customers table and create ID sequence
CREATE TABLE CRMtweb.customers (
    id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    country varchar(30)
);
ALTER TABLE CRMtweb.customers OWNER TO admin;


CREATE SEQUENCE CRMtweb.customers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.customers_id_seq OWNER TO admin;
ALTER SEQUENCE CRMtweb.customers_id_seq OWNED BY CRMtweb.customers.id;



-- Create the providers table and create ID sequence
CREATE TABLE CRMtweb.providers (
    id INTEGER PRIMARY KEY NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    primary_contact VARCHAR(50) NOT NULL,
    address VARCHAR(100),
    country VARCHAR(30)
);
ALTER TABLE CRMtweb.providers OWNER TO admin;

CREATE SEQUENCE CRMtweb.providers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.providers_id_seq OWNER TO admin;
ALTER SEQUENCE CRMtweb.providers_id_seq OWNED BY CRMtweb.providers.id;



-- Create the products table and create ID sequence
CREATE TABLE CRMtweb.products (
    id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    shortname VARCHAR(20) NOT NULL,
    description VARCHAR(200),
    price FLOAT NOT NULL
);
ALTER TABLE CRMtweb.products OWNER TO admin;

CREATE SEQUENCE CRMtweb.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.products_id_seq OWNER TO admin;
ALTER SEQUENCE CRMtweb.products_id_seq OWNED BY CRMtweb.products.id;



-- Create the sales table and create ID sequence
CREATE TABLE CRMtweb.sales (
    id INTEGER PRIMARY KEY NOT NULL,
    id_customer INTEGER REFERENCES CRMtweb.customers(id),
    id_product INTEGER REFERENCES CRMtweb.products(id),
    quantity INTEGER,
    date DATE NOT NULL
);
ALTER TABLE CRMtweb.sales OWNER TO admin;

CREATE SEQUENCE CRMtweb.sales_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.sales_id_seq OWNER TO admin;
ALTER SEQUENCE CRMtweb.sales_id_seq OWNED BY CRMtweb.sales.id;



-- Create the customers_activities table and create ID sequence
CREATE TABLE CRMtweb.customers_activities (
    id INTEGER PRIMARY KEY NOT NULL,
    type VARCHAR(50) NOT NULL,
    date DATE,
    responsible INT REFERENCES CRMtweb.users(id),
    customer_id INT REFERENCES CRMtweb.customers(id)
);
ALTER TABLE CRMtweb.customers_activities OWNER TO admin;

CREATE SEQUENCE CRMtweb.customers_activities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.customers_activities_id_seq OWNER TO admin;
ALTER SEQUENCE CRMtweb.customers_activities_id_seq OWNED BY CRMtweb.customers_activities.id;



-- Create the providers_activities table and create ID sequence
CREATE TABLE CRMtweb.providers_activities (
    id INTEGER PRIMARY KEY NOT NULL,
    type VARCHAR(50) NOT NULL,
    date DATE,
    responsible INT REFERENCES CRMtweb.users(id),
    provider_id INT REFERENCES CRMtweb.providers(id)
);
ALTER TABLE CRMtweb.providers_activities OWNER TO admin;

CREATE SEQUENCE CRMtweb.providers_activities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.providers_activities_id_seq OWNER TO admin;
ALTER SEQUENCE CRMtweb.providers_activities_id_seq OWNED BY CRMtweb.providers_activities.id;

-- Insert data into the users table
INSERT INTO CRMtweb.users VALUES
    (1, 'Marco', 'Rossi', 'marco.rossi@email.com', '123-456-7890', 'password1', 'Manager'),
    (2, 'Anna', 'Bianchi', 'anna.bianchi@email.com', '987-654-3210', 'password2','Manager'),
    (3, 'Luca', 'Verdi', 'luca.verdi@email.com', '111-222-3333', 'password3', 'Support'),
    (4, 'Simona', 'Ricci', 'simona.ricci@email.com', '555-444-3333', 'password4', 'Support'),
    (5, 'Francesco', 'Conti', 'francesco.conti@email.com', '999-888-7777', 'password5', 'Support'),
    (6, 'Giovanni', 'Moretti', 'giovanni.moretti@email.com', '888-777-6666', 'password6', 'Tester'),
    (7, 'Maria', 'Santoro', 'maria.santoro@email.com', '555-333-2222', 'password7', 'Tester'),
    (8, 'Francesca', 'De Luca', 'francesca.deluca@email.com', '333-111-9999', 'password8', 'Tester');

-- Insert data into the customers table
INSERT INTO CRMtweb.customers VALUES
    (1, 'Giovanni', 'Rossi', 'giovanni.rossi@example.com', '123456789', 'Italy'),
    (2, 'Francesca', 'Russo', 'francesca.russo@example.com', '987654321', 'Italy'),
    (3, 'Alessandro', 'Ferrari', 'alessandro.ferrari@example.com', '555555555', 'Italy'),
    (4, 'Maria', 'Bianchi', 'maria.bianchi@example.com', '666666666', 'USA'),
    (5, 'Luca', 'Romano', 'luca.romano@example.com', '777777777', 'USA'),
    (6, 'Laura', 'Marini', 'laura.marini@example.com', '888888888', 'USA'),
    (7, 'Roberto', 'Conti', 'roberto.conti@example.com', '999999999', 'Germany'),
    (8, 'Anna', 'Ferri', 'anna.ferri@example.com', '111111111', 'Germany'),
    (9, 'Marco', 'De Luca', 'marco.deluca@example.com', '222222222', 'Germany'),
    (10, 'Giulia', 'Ricci', 'giulia.ricci@example.com', '333333333', 'France'),
    (11, 'Paolo', 'Santoro', 'paolo.santoro@example.com', '444444444', 'France'),
    (12, 'Silvia', 'Lombardi', 'silvia.lombardi@example.com', '555555555', 'France'),
    (13, 'Davide', 'Barbieri', 'davide.barbieri@example.com', '666666666', 'Spain'),
    (14, 'Elena', 'Mancini', 'elena.mancini@example.com', '777777777', 'Spain'),
    (15, 'Simone', 'Martini', 'simone.martini@example.com', '888888888', 'Spain'),
    (16, 'Valentina', 'Fabbri', 'valentina.fabbri@example.com', '999999999', 'Switzerland'),
    (17, 'Andrea', 'Galli', 'andrea.galli@example.com', '111111111', 'Switzerland'),
    (18, 'Chiara', 'Costa', 'chiara.costa@example.com', '222222222', 'Switzerland'),
    (19, 'Pietro', 'Moretti', 'pietro.moretti@example.com', '333333333', 'Italy'),
    (20, 'Sara', 'Caruso', 'sara.caruso@example.com', '444444444', 'Switzerland');

-- Insert data into the providers table
INSERT INTO CRMtweb.providers VALUES
    (1, 'Azienda Italiana', 'info@aziendaitaliana.com', '123456789', 'Mario Rossi', 'Via Roma, 1', 'Italy'),
    (2, 'Italia SRL', 'info@italiasrl.com', '987654321', 'Laura Bianchi', 'Via Milano, 2', 'Italy'),
    (3, 'Produzioni Napoli', 'info@produzioninapoli.it', '555555555', 'Luigi De Luca', 'Via Napoli, 3', 'Italy'),
    (4, 'Moda Roma', 'info@modaroma.com', '666666666', 'Giulia Ricci', 'Via Roma, 4', 'Italy'),
    (5, 'Gusto Italiano', 'info@gustoitaliano.it', '777777777', 'Antonio Ferrari', 'Via Firenze, 5', 'USA'),
    (6, 'Dolce Vita', 'info@dolcevita.com', '888888888', 'Isabella Taylor', 'Via Venezia, 6', 'USA'),
    (7, 'Tech Innovativa', 'info@techinnovativa.it', '999999999', 'Roberto Conti', 'Via Torino, 7', 'USA'),
    (8, 'Artigianato Siciliano', 'info@artigianatosiciliano.it', '111111111', 'Elena Mancini', 'Via Palermo, 8', 'France'),
    (9, 'Motori Italiani', 'info@motoriitaliani.com', '222222222', 'Davide Barbieri', 'Via Bologna, 9', 'France'),
    (10, 'Viaggi Bella Italia', 'info@viaggibellaitalia.it', '333333333', 'Valentina Fabbri', 'Via Florence, 10', 'France');

-- Insert data into the products table
INSERT INTO CRMtweb.products VALUES
    (1, 'Product 1', 'P1', 'Description for Product 1', 19.99),
    (2, 'Product 2', 'P2', 'Description for Product 2', 29.99),
    (3, 'Product 3', 'P3', 'Description for Product 3', 39.99),
    (4, 'Product 4', 'P4', 'Description for Product 4', 49.99),
    (5, 'Product 5', 'P5', 'Description for Product 5', 59.99),
    (6, 'Product 6', 'P6', 'Description for Product 6', 69.99),
    (7, 'Product 7', 'P7', 'Description for Product 7', 79.99),
    (8, 'Product 8', 'P8', 'Description for Product 8', 89.99),
    (9, 'Product 9', 'P9', 'Description for Product 9', 99.99),
    (10, 'Product 10', 'P10', 'Description for Product 10', 109.99),
    (11, 'Product 11', 'P11', 'Description for Product 11', 119.99),
    (12, 'Product 12', 'P12', 'Description for Product 12', 129.99),
    (13, 'Product 13', 'P13', 'Description for Product 13', 139.99),
    (14, 'Product 14', 'P14', 'Description for Product 14', 149.99),
    (15, 'Product 15', 'P15', 'Description for Product 15', 159.99),
    (16, 'Product 16', 'P16', 'Description for Product 16', 169.99),
    (17, 'Product 17', 'P17', 'Description for Product 17', 179.99),
    (18, 'Product 18', 'P18', 'Description for Product 18', 189.99),
    (19, 'Product 19', 'P19', 'Description for Product 19', 199.99),
    (20, 'Product 20', 'P20', 'Description for Product 20', 209.99);

-- Insert data into the sales table
INSERT INTO CRMtweb.sales VALUES
     (1, 1, 1, 10, '2023-01-01'),
     (2, 2, 2, 15, '2023-01-02'),
     (3, 3, 3, 8, '2023-01-03'),
     (4, 4, 4, 20, '2023-01-04'),
     (5, 5, 5, 12, '2023-01-05'),
     (6, 6, 6, 18, '2023-01-06'),
     (7, 7, 7, 25, '2023-01-07'),
     (8, 8, 8, 30, '2023-01-08'),
     (9, 9, 9, 5, '2023-01-09'),
     (10, 10, 10, 22, '2023-01-10'),
     (11, 11, 11, 17, '2023-01-11'),
     (12, 12, 12, 14, '2023-01-12'),
     (13, 13, 13, 9, '2023-01-13'),
     (14, 14, 14, 28, '2023-01-14'),
     (15, 15, 15, 16, '2023-01-15'),
     (16, 16, 16, 19, '2023-01-16'),
     (17, 17, 17, 23, '2023-01-17'),
     (18, 18, 18, 11, '2023-01-18'),
     (19, 19, 19, 7, '2023-01-19'),
     (20, 20, 20, 13, '2023-01-20');

-- Insert data into the customers_activities table
INSERT INTO CRMtweb.customers_activities VALUES
    (1, 'Meeting', '2023-10-01', 1, 1),
    (2, 'Call', '2023-09-15', 2, 2),
    (3, 'Email', '2023-08-25', 6, 3),
    (4, 'Meeting', '2023-10-05', 7, 4),
    (5, 'Call', '2023-09-20', 8, 5),
    (6, 'Email', '2023-08-30', 1, 6),
    (7, 'Meeting', '2023-10-10', 2, 7),
    (8, 'Call', '2023-09-25', 6, 8),
    (9, 'Email', '2023-08-15', 7, 9),
    (10, 'Meeting', '2023-10-15', 8, 10);

-- Insert data into the providers_activities table
INSERT INTO CRMtweb.providers_activities VALUES
    (1, 'Meeting', '2023-10-01', 1, 1),
    (2, 'Call', '2023-09-15', 2, 2),
    (3, 'Email', '2023-08-25', 6, 3),
    (4, 'Meeting', '2023-10-05', 7, 4),
    (5, 'Call', '2023-09-20', 8, 5),
    (6, 'Email', '2023-08-30', 1, 6),
    (7, 'Meeting', '2023-10-10', 2, 7),
    (8, 'Call', '2023-09-25', 6, 8),
    (9, 'Email', '2023-08-15', 7, 9),
    (10, 'Meeting', '2023-10-15', 8, 10);



-- Foreign key for the relationship between sales and products
ALTER TABLE ONLY CRMtweb.sales
    ADD CONSTRAINT fk_sales_users
        FOREIGN KEY (id_product)
            REFERENCES CRMtweb.products(id);

-- Foreign key for the relationship between sales and customers
ALTER TABLE ONLY CRMtweb.sales
    ADD CONSTRAINT fk_customers_users
        FOREIGN KEY (id_customer)
            REFERENCES CRMtweb.customers(id);

-- Foreign key for the relationship between users and customers_activities
ALTER TABLE ONLY CRMtweb.customers_activities
    ADD CONSTRAINT fk_customers_activities_users
        FOREIGN KEY (responsible)
            REFERENCES CRMtweb.users(id);

-- Foreign key for the relationship between users and providers_activities
ALTER TABLE ONLY CRMtweb.providers_activities
    ADD CONSTRAINT fk_providers_activities_users
        FOREIGN KEY (responsible)
            REFERENCES CRMtweb.users(id);

-- Foreign key for the relationship between customers and customers_activities
ALTER TABLE ONLY CRMtweb.customers_activities
    ADD CONSTRAINT fk_customers_activities_customers
    FOREIGN KEY (customer_id)
    REFERENCES CRMtweb.customers(id);

-- Foreign key for the relationship between providers and providers_activities
ALTER TABLE ONLY CRMtweb.providers_activities
    ADD CONSTRAINT fk_providers_activities_providers
    FOREIGN KEY (provider_id)
    REFERENCES CRMtweb.providers(id);