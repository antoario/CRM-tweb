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


-- Create the CRM-tweb database
CREATE SCHEMA CRM-tweb;
ALTER SCHEMA CRM-tweb OWNER TO jakarta;

SET default_tablespace = '';
SET default_table_access_method = heap;


-- Create the "Employees" table and change owner to 'jakarta'
CREATE TABLE Employees (
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(50),
    phone VARCHAR(15),
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);
ALTER TABLE CRM-tweb.Employees OWNER TO jakarta;


-- Create the "Customers" table and change owner to 'jakarta'
CREATE TABLE Customers (
    id serial PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    primary_contact VARCHAR(50),
    address TEXT,
    phone VARCHAR(15),
    email VARCHAR(100) UNIQUE NOT NULL,
    purchase_history TEXT[]
);
ALTER TABLE CRM-tweb.Customers OWNER TO jakarta;


-- Create the "Contacts" table and change owner to 'jakarta'
CREATE TABLE Contacts (
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    role VARCHAR(50),
    contact_info TEXT,
    preferences TEXT[],
    customer_id INT REFERENCES Customers(id)
);
ALTER TABLE CRM-tweb.Contacts OWNER TO jakarta;


-- Create the "Documents" table and change owner to 'jakarta'
CREATE TABLE Documents (
    id serial PRIMARY KEY,
    document_name VARCHAR(100) NOT NULL,
    document_type VARCHAR(50),
    document_url TEXT,
    customer_id INT REFERENCES Customers(id)
);
ALTER TABLE Documents OWNER TO jakarta;


-- Create the "Activities" table and change owner to 'jakarta'
CREATE TABLE Activities (
    id serial PRIMARY KEY,
    activity_type VARCHAR(50) NOT NULL,
    activity_date DATE,
    responsible INT REFERENCES Employees(id),
    customer_id INT REFERENCES Customers(id)
);
ALTER TABLE Activities OWNER TO jakarta;


-- Create the "Products_Services" table and change owner to 'jakarta'
CREATE TABLE Products_Services (
    id serial PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    product_code VARCHAR(20),
    description TEXT,
    price DECIMAL(10, 2),
    inventory INT
);
ALTER TABLE Products_Services OWNER TO jakarta;

-- Insert random data into the "Employees" table
INSERT INTO CRM-tweb.Employees (name, email, role, phone, username, password)
VALUES
    ('Marco Rossi', 'marco.rossi@email.com', 'Manager', '123-456-7890', 'marco123', 'password1'),
    ('Anna Bianchi', 'anna.bianchi@email.com', 'Sales Rep', '987-654-3210', 'anna456', 'password2'),
    ('Luca Verdi', 'luca.verdi@email.com', 'Support', '111-222-3333', 'luca789', 'password3'),
    ('Simona Ricci', 'simona.ricci@email.com', 'Manager', '555-444-3333', 'simona456', 'password4'),
    ('Francesco Conti', 'francesco.conti@email.com', 'Support', '999-888-7777', 'francesco123', 'password5'),
    ('Elena Ferrari', 'elena.ferrari@email.com', 'Sales Rep', '777-666-5555', 'elena789', 'password6'),
    ('Giovanni Moretti', 'giovanni.moretti@email.com', 'Manager', '888-777-6666', 'giovanni123', 'password7'),
    ('Maria Santoro', 'maria.santoro@email.com', 'Sales Rep', '555-333-2222', 'maria456', 'password8'),
    ('Francesca De Luca', 'francesca.deluca@email.com', 'Support', '333-111-9999', 'francesca789', 'password9'),
    ('Roberto Mancini', 'roberto.mancini@email.com', 'Manager', '777-888-9999', 'roberto123', 'password10'),
    ('Carla Pellegrini', 'carla.pellegrini@email.com', 'Sales Rep', '444-555-6666', 'carla456', 'password11'),
    ('Alessio Rinaldi', 'alessio.rinaldi@email.com', 'Support', '222-333-4444', 'alessio789', 'password12'),
    ('Laura Ferrara', 'laura.ferrara@email.com', 'Manager', '111-222-3333', 'laura123', 'password13'),
    ('Antonio Russo', 'antonio.russo@email.com', 'Sales Rep', '777-666-5555', 'antonio456', 'password14'),
    ('Giulia Bellini', 'giulia.bellini@email.com', 'Support', '333-444-5555', 'giulia789', 'password15');

-- Insert random data into the "Customers" table
INSERT INTO Customers (company_name, primary_contact, address, phone, email, purchase_history)
VALUES
    ('ABC Company', 'Maria Esposito', '123 Main St, Milan', '555-123-4567', 'info@abc.com', ARRAY['2023-10-05', '2023-08-15']),
    ('XYZ Corporation', 'Roberto Russo', '456 Elm St, Rome', '333-987-6543', 'contact@xyzcorp.com', ARRAY['2023-09-20']),
    ('LMN Ltd', 'Laura Ferrari', '789 Oak St, Venice', '222-444-8888', 'sales@lmn-ltd.com', ARRAY['2023-10-10']),
    ('PQR Enterprises', 'Giovanni De Luca', '345 Pine St, Florence', '111-222-3333', 'info@pqrenterprises.com', ARRAY['2023-11-01']),
    ('DEF Incorporated', 'Carla Moretti', '567 Birch St, Naples', '777-555-4444', 'contact@definc.com', ARRAY['2023-09-12']),
    ('GHI Ltd', 'Alessio Bianchi', '678 Maple St, Turin', '888-777-6666', 'sales@ghi-ltd.com', ARRAY['2023-09-25']),
    ('JKL Solutions', 'Luigi Rinaldi', '456 Oak St, Bologna', '999-888-7777', 'info@jklsolutions.com', ARRAY['2023-10-20']),
    ('MNO Enterprises', 'Paolo Vitale', '789 Pine St, Florence', '444-555-6666', 'contact@mnoent.com', ARRAY['2023-08-28']),
    ('UVW Co.', 'Sofia Barbieri', '123 Elm St, Rome', '555-666-7777', 'info@uvwco.com', ARRAY['2023-07-15']),
    ('XYZ Ltd', 'Roberto Pellegrini', '345 Oak St, Venice', '333-444-5555', 'sales@xyzltd.com', ARRAY['2023-09-05']),
    ('ABC Enterprises', 'Carla De Luca', '678 Birch St, Naples', '777-888-9999', 'contact@abcent.com', ARRAY['2023-11-10']),
    ('LMN Solutions', 'Giovanni Ferrari', '456 Pine St, Florence', '111-222-3333', 'info@lmnsolutions.com', ARRAY['2023-10-15']),
    ('DEF Co.', 'Sara Bianchi', '123 Maple St, Turin', '555-333-4444', 'sales@defco.com', ARRAY['2023-09-18']),
    ('JKL Corporation', 'Alessio Rinaldi', '567 Elm St, Rome', '888-777-6666', 'contact@jklcorp.com', ARRAY['2023-09-30']),
    ('MNO Ltd', 'Laura Ferrara', '789 Oak St, Venice', '222-111-3333', 'info@mnoltd.com', ARRAY['2023-09-22']);

-- Insert random data into the "Contacts" table
INSERT INTO Contacts (name, role, contact_info, preferences, customer_id)
VALUES
    ('Giorgio Moretti', 'Purchasing Manager', 'giorgio.moretti@email.com, 555-987-1234', ARRAY['Email'], 1),
    ('Sara Costa', 'Sales Representative', 'sara.costa@email.com, 333-555-7777', ARRAY['Phone'], 2),
    ('Luigi Bellini', 'Account Manager', 'luigi.bellini@email.com, 111-222-3333', ARRAY['Email', 'Phone'], 3),
    ('Valentina Mancini', 'Purchasing Manager', 'valentina.mancini@email.com, 777-888-9999', ARRAY['Email', 'Phone'], 4),
    ('Roberto Pellegrini', 'Sales Representative', 'roberto.pellegrini@email.com, 444-555-6666', ARRAY['Email'], 5),
    ('Elisa Rinaldi', 'Account Manager', 'elisa.rinaldi@email.com, 222-333-4444', ARRAY['Phone'], 6),
    ('Giovanni Ferrara', 'Purchasing Manager', 'giovanni.ferrara@email.com, 333-444-5555', ARRAY['Email', 'Phone'], 7),
    ('Maria Vitale', 'Sales Representative', 'maria.vitale@email.com, 555-666-7777', ARRAY['Email'], 8),
    ('Carla Barbieri', 'Account Manager', 'carla.barbieri@email.com, 777-888-9999', ARRAY['Phone'], 9),
    ('Sofia Russo', 'Purchasing Manager', 'sofia.russo@email.com, 222-111-3333', ARRAY['Email', 'Phone'], 10),
    ('Roberto Conti', 'Sales Representative', 'roberto.conti@email.com, 555-333-4444', ARRAY['Email'], 11),
    ('Alessio Ferrari', 'Account Manager', 'alessio.ferrari@email.com, 111-222-3333', ARRAY['Phone'], 12),
    ('Laura Bianchi', 'Purchasing Manager', 'laura.bianchi@email.com, 333-444-5555', ARRAY['Email', 'Phone'], 13),
    ('Antonio Rinaldi', 'Sales Representative', 'antonio.rinaldi@email.com, 555-666-7777', ARRAY['Email'], 14),
    ('Giulia Pellegrini', 'Account Manager', 'giulia.pellegrini@email.com, 777-888-9999', ARRAY['Phone'], 15);

-- Insert random data into the "Documents" table
INSERT INTO Documents (document_name, document_type, document_url, customer_id)
VALUES
    ('Contract 1', 'Contract', 'http://example.com/contract1.pdf', 1),
    ('Proposal 1', 'Proposal', 'http://example.com/proposal1.pdf', 2),
    ('Invoice 1', 'Invoice', 'http://example.com/invoice1.pdf', 3),
    ('Contract 2', 'Contract', 'http://example.com/contract2.pdf', 4),
    ('Proposal 2', 'Proposal', 'http://example.com/proposal2.pdf', 5),
    ('Invoice 2', 'Invoice', 'http://example.com/invoice2.pdf', 6),
    ('Contract 3', 'Contract', 'http://example.com/contract3.pdf', 7),
    ('Proposal 3', 'Proposal', 'http://example.com/proposal3.pdf', 8),
    ('Invoice 3', 'Invoice', 'http://example.com/invoice3.pdf', 9),
    ('Contract 4', 'Contract', 'http://example.com/contract4.pdf', 10),
    ('Proposal 4', 'Proposal', 'http://example.com/proposal4.pdf', 11),
    ('Invoice 4', 'Invoice', 'http://example.com/invoice4.pdf', 12),
    ('Contract 5', 'Contract', 'http://example.com/contract5.pdf', 13),
    ('Proposal 5', 'Proposal', 'http://example.com/proposal5.pdf', 14),
    ('Invoice 5', 'Invoice', 'http://example.com/invoice5.pdf', 15);

-- Insert random data into the "Activities" table
INSERT INTO Activities (activity_type, activity_date, responsible, customer_id)
VALUES
    ('Meeting', '2023-10-01', 1, 1),
    ('Call', '2023-09-15', 2, 2),
    ('Email', '2023-08-25', 3, 3),
    ('Meeting', '2023-10-05', 4, 4),
    ('Call', '2023-09-20', 5, 5),
    ('Email', '2023-08-30', 6, 6),
    ('Meeting', '2023-10-10', 7, 7),
    ('Call', '2023-09-25', 8, 8),
    ('Email', '2023-08-15', 9, 9),
    ('Meeting', '2023-10-15', 10, 10),
    ('Call', '2023-09-30', 11, 11),
    ('Email', '2023-08-28', 12, 12),
    ('Meeting', '2023-10-20', 13, 13),
    ('Call', '2023-09-18', 14, 14),
    ('Email', '2023-09-22', 15, 15);

-- Insert random data into the "Products_Services" table
INSERT INTO Products_Services (product_name, product_code, description, price, inventory)
VALUES
    ('Product A', 'PA001', 'High-quality product A', 99.99, 50),
    ('Service X', 'SX123', 'Premium service X', 199.99, 100),
    ('Product B', 'PB002', 'Affordable product B', 49.99, 75),
    ('Service Y', 'SY456', 'Advanced service Y', 299.99, 30),
    ('Product C', 'PC003', 'Versatile product C', 79.99, 60),
    ('Service Z', 'SZ789', 'Professional service Z', 249.99, 45),
    ('Product D', 'PD004', 'Innovative product D', 149.99, 40),
    ('Service W', 'SW789', 'Flexible service W', 159.99, 90),
    ('Product E', 'PE005', 'Economical product E', 69.99, 55),
    ('Service V', 'SV001', 'Compact service V', 119.99, 80),
    ('Product F', 'PF006', 'Stylish product F', 89.99, 65),
    ('Service U', 'SU002', 'Efficient service U', 169.99, 20),
    ('Product G', 'PG007', 'Robust product G', 129.99, 35),
    ('Service T', 'ST003', 'Reliable service T', 179.99, 70),
    ('Product H', 'PH008', 'Durable product H', 109.99, 25);

