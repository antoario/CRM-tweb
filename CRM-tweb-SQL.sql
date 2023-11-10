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
ALTER SCHEMA CRMtweb OWNER TO jakarta;

SET default_tablespace = '';
SET default_table_access_method     = heap;



-- Create the users table, change owner to 'jakarta' and create ID sequence
CREATE TABLE CRMtweb.users (
    id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL,
    phone VARCHAR(15),
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);
ALTER TABLE CRMtweb.users OWNER TO jakarta;

CREATE SEQUENCE CRMtweb.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.users_id_seq OWNER TO jakarta;
ALTER SEQUENCE CRMtweb.users_id_seq OWNED BY CRMtweb.users.id;



-- Create the customers table, change owner to 'jakarta' and create ID sequence
CREATE TABLE CRMtweb.customers (
    id INTEGER PRIMARY KEY NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    primary_contact VARCHAR(50) NOT NULL,
    address TEXT,
    phone VARCHAR(15),
    email VARCHAR(100) UNIQUE NOT NULL,
    purchase_history TEXT[]
);
ALTER TABLE CRMtweb.customers OWNER TO jakarta;

CREATE SEQUENCE CRMtweb.customers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.customers_id_seq OWNER TO jakarta;
ALTER SEQUENCE CRMtweb.customers_id_seq OWNED BY CRMtweb.customers.id;



-- Create the providers table, change owner to 'jakarta' and create ID sequence
CREATE TABLE CRMtweb.providers (
    id INTEGER PRIMARY KEY NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    primary_contact VARCHAR(50) NOT NULL,
    address TEXT,
    phone VARCHAR(15),
    email VARCHAR(100) UNIQUE NOT NULL,
    supply_history TEXT[]
);
ALTER TABLE CRMtweb.providers OWNER TO jakarta;

CREATE SEQUENCE CRMtweb.providers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.providers_id_seq OWNER TO jakarta;
ALTER SEQUENCE CRMtweb.providers_id_seq OWNED BY CRMtweb.providers.id;



-- Create the customers_documents table, change owner to 'jakarta' and create ID sequence
CREATE TABLE CRMtweb.customers_documents (
    id INTEGER PRIMARY KEY NOT NULL,
    document_name VARCHAR(100) NOT NULL,
    document_type VARCHAR(50),
    document_url TEXT NOT NULL,
    customer_id INT REFERENCES CRMtweb.customers(id),
	created_by INTEGER REFERENCES CRMtweb.users(id)
);
ALTER TABLE CRMtweb.customers_documents OWNER TO jakarta;

CREATE SEQUENCE CRMtweb.customers_documents_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.customers_documents_id_seq OWNER TO jakarta;
ALTER SEQUENCE CRMtweb.customers_documents_id_seq OWNED BY CRMtweb.customers_documents.id;



-- Create the providers_documents table, change owner to 'jakarta' and create ID sequence
CREATE TABLE CRMtweb.providers_documents (
    id INTEGER PRIMARY KEY NOT NULL,
    document_name VARCHAR(100) NOT NULL,
    document_type VARCHAR(50),
    document_url TEXT NOT NULL,
    providers_id INT REFERENCES CRMtweb.providers(id),
	created_by INTEGER REFERENCES CRMtweb.users(id)
);
ALTER TABLE CRMtweb.providers_documents OWNER TO jakarta;

CREATE SEQUENCE CRMtweb.providers_documents_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.providers_documents_id_seq OWNER TO jakarta;
ALTER SEQUENCE CRMtweb.providers_documents_id_seq OWNED BY CRMtweb.providers_documents.id;



-- Create the customers_activities table, change owner to 'jakarta' and create ID sequence
CREATE TABLE CRMtweb.customers_activities (
    id INTEGER PRIMARY KEY NOT NULL,
    activity_type VARCHAR(50) NOT NULL,
    activity_date DATE,
    responsible INT REFERENCES CRMtweb.users(id),
    customer_id INT REFERENCES CRMtweb.customers(id)
);
ALTER TABLE CRMtweb.customers_activities OWNER TO jakarta;

CREATE SEQUENCE CRMtweb.customers_activities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.customers_activities_id_seq OWNER TO jakarta;
ALTER SEQUENCE CRMtweb.customers_activities_id_seq OWNED BY CRMtweb.customers_activities.id;



-- Create the providersactivities table, change owner to 'jakarta' and create ID sequence
CREATE TABLE CRMtweb.providers_activities (
    id INTEGER PRIMARY KEY NOT NULL,
    activity_type VARCHAR(50) NOT NULL,
    activity_date DATE,
    responsible INT REFERENCES CRMtweb.users(id),
    provider_id INT REFERENCES CRMtweb.providers(id)
);
ALTER TABLE CRMtweb.providers_activities OWNER TO jakarta;

CREATE SEQUENCE CRMtweb.providers_activities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.providers_activities_id_seq OWNER TO jakarta;
ALTER SEQUENCE CRMtweb.providers_activities_id_seq OWNED BY CRMtweb.providers_activities.id;


-- Create the products_services table, change owner to 'jakarta' and create ID sequence
CREATE TABLE CRMtweb.products_services (
    id INTEGER PRIMARY KEY NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    product_code VARCHAR(20),
    description TEXT,
    price DECIMAL(10, 2),
    inventory INT
);
ALTER TABLE CRMtweb.products_services OWNER TO jakarta;

CREATE SEQUENCE CRMtweb.products_services_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CRMtweb.products_services_id_seq OWNER TO jakarta;
ALTER SEQUENCE CRMtweb.products_services_id_seq OWNED BY CRMtweb.products_services.id;


-- Insert data into the users table
INSERT INTO CRMtweb.users VALUES
    (1, 'Marco Rossi', 'marco.rossi@email.com', 'Manager', '123-456-7890', 'marco123', 'password1'),
    (2, 'Anna Bianchi', 'anna.bianchi@email.com', 'Manager', '987-654-3210', 'anna456', 'password2'),
    (3, 'Luca Verdi', 'luca.verdi@email.com', 'Support', '111-222-3333', 'luca789', 'password3'),
    (4, 'Simona Ricci', 'simona.ricci@email.com', 'Support', '555-444-3333', 'simona456', 'password4'),
    (5, 'Francesco Conti', 'francesco.conti@email.com', 'Sales Rep', '999-888-7777', 'francesco123', 'password5'),
    (6, 'Elena Ferrari', 'elena.ferrari@email.com', 'Sales Rep', '777-666-5555', 'elena789', 'password6'),
    (7, 'Giovanni Moretti', 'giovanni.moretti@email.com', 'Tester', '888-777-6666', 'giovanni123', 'password7'),
    (8, 'Maria Santoro', 'maria.santoro@email.com', 'Tester', '555-333-2222', 'maria456', 'password8'),
    (9, 'Francesca De Luca', 'francesca.deluca@email.com', 'Tester', '333-111-9999', 'francesca789', 'password9');

-- Insert data into the customers table
INSERT INTO CRMtweb.customers VALUES
    (1, 'ABC Company', 'Maria Esposito', '123 Main St, Milan', '555-123-4567', 'info@abc.com', ARRAY['2023-10-05', '2023-08-15']),
    (2, 'XYZ Corporation', 'Roberto Russo', '456 Elm St, Rome', '333-987-6543', 'contact@xyzcorp.com', ARRAY['2023-09-20']),
    (3, 'LMN Ltd', 'Laura Ferrari', '789 Oak St, Venice', '222-444-8888', 'sales@lmn-ltd.com', ARRAY['2023-10-10']),
    (4, 'PQR Enterprises', 'Giovanni De Luca', '345 Pine St, Florence', '111-222-3333', 'info@pqrenterprises.com', ARRAY['2023-11-01']),
    (5, 'DEF Incorporated', 'Carla Moretti', '567 Birch St, Naples', '777-555-4444', 'contact@definc.com', ARRAY['2023-09-12']),
    (6, 'GHI Ltd', 'Alessio Bianchi', '678 Maple St, Turin', '888-777-6666', 'sales@ghi-ltd.com', ARRAY['2023-09-25']),
    (7, 'JKL Solutions', 'Luigi Rinaldi', '456 Oak St, Bologna', '999-888-7777', 'info@jklsolutions.com', ARRAY['2023-10-20']),
    (8, 'MNO Enterprises', 'Paolo Vitale', '789 Pine St, Florence', '444-555-6666', 'contact@mnoent.com', ARRAY['2023-08-28']),
    (9, 'UVW Co.', 'Sofia Barbieri', '123 Elm St, Rome', '555-666-7777', 'info@uvwco.com', ARRAY['2023-07-15']),
    (10, 'XYZ Ltd', 'Roberto Pellegrini', '345 Oak St, Venice', '333-444-5555', 'sales@xyzltd.com', ARRAY['2023-09-05']),
    (11, 'ABC Enterprises', 'Carla De Luca', '678 Birch St, Naples', '777-888-9999', 'contact@abcent.com', ARRAY['2023-11-10']),
    (12, 'LMN Solutions', 'Giovanni Ferrari', '456 Pine St, Florence', '111-222-3333', 'info@lmnsolutions.com', ARRAY['2023-10-15']),
    (13, 'DEF Co.', 'Sara Bianchi', '123 Maple St, Turin', '555-333-4444', 'sales@defco.com', ARRAY['2023-09-18']),
    (14, 'JKL Corporation', 'Alessio Rinaldi', '567 Elm St, Rome', '888-777-6666', 'contact@jklcorp.com', ARRAY['2023-09-30']),
    (15, 'MNO Ltd', 'Laura Ferrara', '789 Oak St, Venice', '222-111-3333', 'info@mnoltd.com', ARRAY['2023-09-22']);

-- Insert data into the providers table
INSERT INTO CRMtweb.providers VALUES
    (1, 'SupplyTech Ltd', 'Marco Rossi', '456 Oak St, Milan', '555-123-4567', 'marco@supplytech.com', ARRAY['2023-10-05', '2023-08-15']),
    (2, 'Global Supplies', 'Laura Bianchi', '789 Elm St, Rome', '333-987-6543', 'laura@globalsupplies.com', ARRAY['2023-09-20']),
    (3, 'Innovative Solutions', 'Antonio Ferrari', '123 Pine St, Venice', '222-444-8888', 'antonio@innovativesolutions.com', ARRAY['2023-10-10']),
    (4, 'Supply Dynamics', 'Giulia Moretti', '567 Birch St, Florence', '111-222-3333', 'giulia@supplydynamics.com', ARRAY['2023-11-01']),
    (5, 'Tech Supplies Co.', 'Roberto De Luca', '678 Maple St, Naples', '777-555-4444', 'roberto@techsuppliesco.com', ARRAY['2023-09-12']),
    (6, 'Future Supplies', 'Elena Rinaldi', '789 Oak St, Turin', '888-777-6666', 'elena@futuresupplies.com', ARRAY['2023-09-25']),
    (7, 'Global Innovations', 'Giovanni Vitale', '456 Elm St, Bologna', '999-888-7777', 'giovanni@globalinnovations.com', ARRAY['2023-10-20']),
    (8, 'Smart Suppliers', 'Sara Barbieri', '123 Pine St, Florence', '444-555-6666', 'sara@smartsuppliers.com', ARRAY['2023-08-28']),
    (9, 'Dynamic Tech', 'Luca Pellegrini', '345 Oak St, Rome', '555-666-7777', 'luca@dynamictech.com', ARRAY['2023-07-15']),
    (10, 'Innovate Supplies', 'Sofia Ferrara', '567 Elm St, Venice', '333-444-5555', 'sofia@innovatesupplies.com', ARRAY['2023-09-05']),
    (11, 'Global Dynamics', 'Alessio De Luca', '678 Birch St, Naples', '777-888-9999', 'alessio@globaldynamics.com', ARRAY['2023-11-10']),
    (12, 'Tech Co.', 'Carla Ferrari', '456 Pine St, Florence', '111-222-3333', 'carla@techco.com', ARRAY['2023-10-15']),
    (13, 'Future Innovations', 'Marco Bianchi', '123 Maple St, Turin', '555-333-4444', 'marco@futureinnovations.com', ARRAY['2023-09-18']),
    (14, 'Dynamic Corporation', 'Sara Rinaldi', '567 Elm St, Rome', '888-777-6666', 'sara@dynamiccorp.com', ARRAY['2023-09-30']),
    (15, 'Innovate Ltd', 'Giovanni Ferrara', '789 Oak St, Venice', '222-111-3333', 'giovanni@innovateltd.com', ARRAY['2023-09-22']);

-- Insert data into the customers_documents table
INSERT INTO CRMtweb.customers_documents VALUES
    (1, 'Contract 1', 'Contract', 'http://example.com/costumercontract1.pdf', 1, 5),
    (2, 'Proposal 1', 'Proposal', 'http://example.com/customerproposal1.pdf', 2, 6),
    (3, 'Invoice 1', 'Invoice', 'http://example.com/customerinvoice1.pdf', 3, 4),
    (4, 'Contract 2', 'Contract', 'http://example.com/customercontract2.pdf', 4, 3),
    (5, 'Proposal 2', 'Proposal', 'http://example.com/customerproposal2.pdf', 5, 6),
    (6, 'Invoice 2', 'Invoice', 'http://example.com/customerinvoice2.pdf', 6, 5),
    (7, 'Contract 3', 'Contract', 'http://example.com/customercontract3.pdf', 7, 3),
    (8, 'Proposal 3', 'Proposal', 'http://example.com/customerproposal3.pdf', 8, 4),
    (9, 'Invoice 3', 'Invoice', 'http://example.com/customerinvoice3.pdf', 9, 4),
    (10, 'Contract 4', 'Contract', 'http://example.com/customercontract4.pdf', 10, 5);

-- Insert data into the providers_documents table
INSERT INTO CRMtweb.providers_documents VALUES
    (1, 'Contract 1', 'Contract', 'http://example.com/providercostumercontract1.pdf', 1, 3),
    (2, 'Proposal 1', 'Proposal', 'http://example.com/providerproposal1.pdf', 2, 4),
    (3, 'Invoice 1', 'Invoice', 'http://example.com/providerinvoice1.pdf', 3, 4),
    (4, 'Contract 2', 'Contract', 'http://example.com/providercontract2.pdf', 4, 3),
    (5, 'Proposal 2', 'Proposal', 'http://example.com/providerproposal2.pdf', 5, 3),
    (6, 'Invoice 2', 'Invoice', 'http://example.com/providerinvoice2.pdf', 6, 3),
    (7, 'Contract 3', 'Contract', 'http://example.com/providercontract3.pdf', 7, 4),
    (8, 'Proposal 3', 'Proposal', 'http://example.com/providerproposal3.pdf', 8, 4),
    (9, 'Invoice 3', 'Invoice', 'http://example.com/providerinvoice3.pdf', 9, 3),
    (10, 'Contract 4', 'Contract', 'http://example.com/providercontract4.pdf', 10, 3);

-- Insert data into the customers_activities table
INSERT INTO CRMtweb.customers_activities VALUES
    (1, 'Meeting', '2023-10-01', 1, 1),
    (2, 'Call', '2023-09-15', 2, 2),
    (3, 'Email', '2023-08-25', 3, 3),
    (4, 'Meeting', '2023-10-05', 4, 4),
    (5, 'Call', '2023-09-20', 5, 5),
    (6, 'Email', '2023-08-30', 6, 6),
    (7, 'Meeting', '2023-10-10', 7, 7),
    (8, 'Call', '2023-09-25', 8, 8),
    (9, 'Email', '2023-08-15', 9, 9),
    (10, 'Meeting', '2023-10-15', 1, 10);

-- Insert data into the providers_activities table
INSERT INTO CRMtweb.providers_activities VALUES
    (1, 'Meeting', '2023-10-01', 1, 1),
    (2, 'Call', '2023-09-15', 2, 2),
    (3, 'Email', '2023-08-25', 3, 3),
    (4, 'Meeting', '2023-10-05', 4, 4),
    (5, 'Call', '2023-09-20', 5, 5),
    (6, 'Email', '2023-08-30', 6, 6),
    (7, 'Meeting', '2023-10-10', 7, 7),
    (8, 'Call', '2023-09-25', 8, 8),
    (9, 'Email', '2023-08-15', 9, 9),
    (10, 'Meeting', '2023-10-15', 1, 10);

-- Insert data into the products_services table
INSERT INTO CRMtweb.products_services VALUES
    (1, 'Product A', 'PA001', 'High-quality product A', 99.99, 50),
    (2, 'Service X', 'SX123', 'Premium service X', 199.99, 100),
    (3, 'Product B', 'PB002', 'Affordable product B', 49.99, 75),
    (4, 'Service Y', 'SY456', 'Advanced service Y', 299.99, 30),
    (5, 'Product C', 'PC003', 'Versatile product C', 79.99, 60),
    (6, 'Service Z', 'SZ789', 'Professional service Z', 249.99, 45),
    (7, 'Product D', 'PD004', 'Innovative product D', 149.99, 40),
    (8, 'Service W', 'SW789', 'Flexible service W', 159.99, 90),
    (9, 'Product E', 'PE005', 'Economical product E', 69.99, 55),
    (10, 'Service V', 'SV001', 'Compact service V', 119.99, 80),
    (11, 'Product F', 'PF006', 'Stylish product F', 89.99, 65),
    (12, 'Service U', 'SU002', 'Efficient service U', 169.99, 20),
    (13, 'Product G', 'PG007', 'Robust product G', 129.99, 35),
    (14, 'Service T', 'ST003', 'Reliable service T', 179.99, 70),
    (15, 'Product H', 'PH008', 'Durable product H', 109.99, 25);

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

-- Foreign key for the relationship between customers and customers_documents
ALTER TABLE ONLY CRMtweb.customers_documents
    ADD CONSTRAINT fk_customers_documents_customers
    FOREIGN KEY (customer_id)
    REFERENCES CRMtweb.customers(id);

-- Foreign key for the relationship between providers and providers_documents
ALTER TABLE ONLY CRMtweb.providers_documents
    ADD CONSTRAINT fk_providers_documents_providers
    FOREIGN KEY (providers_id)
    REFERENCES CRMtweb.providers(id);

-- Foreign key for the relationship between users and customers_documents
ALTER TABLE ONLY CRMtweb.customers_documents
    ADD CONSTRAINT fk_customers_documents_users
    FOREIGN KEY (created_by)
    REFERENCES CRMtweb.users(id);

-- Foreign key for the relationship between users and providers_documents
ALTER TABLE ONLY CRMtweb.providers_documents
    ADD CONSTRAINT fk_providers_documents_users
    FOREIGN KEY (created_by)
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

