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
CREATE TABLE Users (
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(50),
    phone VARCHAR(15),
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);
ALTER TABLE CRM-tweb.Users OWNER TO jakarta;


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
CREATE TABLE Providers (
    id serial PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    primary_contact VARCHAR(50),
    address TEXT,
    phone VARCHAR(15),
    email VARCHAR(100) UNIQUE NOT NULL,
    supply_history TEXT[]
);
ALTER TABLE CRM-tweb.Providers OWNER TO jakarta;


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

-- Insert data into the "Employees" table
INSERT INTO CRM-tweb.Users (name, email, role, phone, username, password)
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

-- Insert data into the "Customers" table
INSERT INTO CRM-tweb.Customers (company_name, primary_contact, address, phone, email, purchase_history)
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

-- Insert data into the "Contacts" table
INSERT INTO CRM_tweb.Providers (company_name, primary_contact, address, phone, email, supply_history)
VALUES
    ('SupplyTech Ltd', 'Marco Rossi', '456 Oak St, Milan', '555-123-4567', 'marco@supplytech.com', ARRAY['2023-10-05', '2023-08-15']),
    ('Global Supplies', 'Laura Bianchi', '789 Elm St, Rome', '333-987-6543', 'laura@globalsupplies.com', ARRAY['2023-09-20']),
    ('Innovative Solutions', 'Antonio Ferrari', '123 Pine St, Venice', '222-444-8888', 'antonio@innovativesolutions.com', ARRAY['2023-10-10']),
    ('Supply Dynamics', 'Giulia Moretti', '567 Birch St, Florence', '111-222-3333', 'giulia@supplydynamics.com', ARRAY['2023-11-01']),
    ('Tech Supplies Co.', 'Roberto De Luca', '678 Maple St, Naples', '777-555-4444', 'roberto@techsuppliesco.com', ARRAY['2023-09-12']),
    ('Future Supplies', 'Elena Rinaldi', '789 Oak St, Turin', '888-777-6666', 'elena@futuresupplies.com', ARRAY['2023-09-25']),
    ('Global Innovations', 'Giovanni Vitale', '456 Elm St, Bologna', '999-888-7777', 'giovanni@globalinnovations.com', ARRAY['2023-10-20']),
    ('Smart Suppliers', 'Sara Barbieri', '123 Pine St, Florence', '444-555-6666', 'sara@smartsuppliers.com', ARRAY['2023-08-28']),
    ('Dynamic Tech', 'Luca Pellegrini', '345 Oak St, Rome', '555-666-7777', 'luca@dynamictech.com', ARRAY['2023-07-15']),
    ('Innovate Supplies', 'Sofia Ferrara', '567 Elm St, Venice', '333-444-5555', 'sofia@innovatesupplies.com', ARRAY['2023-09-05']),
    ('Global Dynamics', 'Alessio De Luca', '678 Birch St, Naples', '777-888-9999', 'alessio@globaldynamics.com', ARRAY['2023-11-10']),
    ('Tech Co.', 'Carla Ferrari', '456 Pine St, Florence', '111-222-3333', 'carla@techco.com', ARRAY['2023-10-15']),
    ('Future Innovations', 'Marco Bianchi', '123 Maple St, Turin', '555-333-4444', 'marco@futureinnovations.com', ARRAY['2023-09-18']),
    ('Dynamic Corporation', 'Sara Rinaldi', '567 Elm St, Rome', '888-777-6666', 'sara@dynamiccorp.com', ARRAY['2023-09-30']),
    ('Innovate Ltd', 'Giovanni Ferrara', '789 Oak St, Venice', '222-111-3333', 'giovanni@innovateltd.com', ARRAY['2023-09-22']);

-- Insert data into the "Documents" table
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

-- Insert data into the "Activities" table
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

-- Insert data into the "Products_Services" table
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

-- Primary key for the Users table
ALTER TABLE ONLY CRM-tweb.Users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);

-- Primary key for the Customers table
ALTER TABLE ONLY CRM-tweb.Customers
    ADD CONSTRAINT customers_pk PRIMARY KEY (id);

-- Primary key for the Providers table
ALTER TABLE ONLY CRM-tweb.Providers
    ADD CONSTRAINT providers_pk PRIMARY KEY (id);

-- Primary key for the Documents table
ALTER TABLE ONLY CRM-tweb.Documents
    ADD CONSTRAINT documents_pk PRIMARY KEY (id);

-- Primary key for the Activities table
ALTER TABLE ONLY CRM-tweb.Activities
    ADD CONSTRAINT activities_pk PRIMARY KEY (id);

-- Primary key for the Products_Services table
ALTER TABLE ONLY CRM-tweb.Products_Services
    ADD CONSTRAINT products_services_pk PRIMARY KEY (id);

-- Foreign key for the relationship between Activities and Users
ALTER TABLE ONLY CRM-tweb.Activities
    ADD CONSTRAINT fk_activities_responsible_user
    FOREIGN KEY (responsible)
    REFERENCES CRM-tweb.Users(id);

-- Foreign key for the relationship between Activities and Customers
ALTER TABLE ONLY CRM-tweb.Activities
    ADD CONSTRAINT fk_activities_customer
    FOREIGN KEY (customer_id)
    REFERENCES CRM-tweb.Customers(id);

-- Foreign key for the relationship between Documents and Customers
ALTER TABLE ONLY CRM-tweb.Documents
    ADD CONSTRAINT fk_documents_customer
    FOREIGN KEY (customer_id)
    REFERENCES CRM-tweb.Customers(id);

-- Foreign key for the relationship between Documents and Users
ALTER TABLE ONLY CRM-tweb.Documents
    ADD CONSTRAINT fk_documents_created_by
    FOREIGN KEY (created_by)
    REFERENCES CRM-tweb.Users(id);

-- Foreign key for the relationship between Activities and Products_Services
ALTER TABLE ONLY CRM-tweb.Activities
    ADD CONSTRAINT fk_activities_product_service
    FOREIGN KEY (product_service_id)
    REFERENCES CRM-tweb.Products_Services(id);
