CREATE TABLE employees
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(255)        NOT NULL,
    last_name     VARCHAR(255)        NOT NULL,
    date_of_birth DATE                NOT NULL,
    password      VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    role          INTEGER             NOT NULL CHECK (role >= 0 AND role <= 2),
    department_id INT                 NULL, -- Questo sarà aggiornato dopo la creazione di departments
    url_image     VARCHAR(255)        NULL
);

CREATE TABLE departments
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    manager_id  INT          NULL -- Cambiato in INT, modificato dopo che employees è stato creato
);

-- Creazione delle altre tabelle che dipendono da Departments o Employees
CREATE TABLE positions
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT,
    level         VARCHAR(50),
    department_id INT          REFERENCES departments (id) ON DELETE SET NULL
);

CREATE TABLE projects
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description   TEXT,
    start_date    DATE,
    end_date      DATE,
    department_id INT          REFERENCES departments (id) ON DELETE SET NULL
);

CREATE TABLE benefits
(
    id          SERIAL PRIMARY KEY,
    description TEXT,
    value       VARCHAR(255),
    employee_id INT REFERENCES employees (id) ON DELETE CASCADE
);

CREATE TABLE contracts
(
    id            SERIAL PRIMARY KEY,
    employee_id   INT REFERENCES employees (id) ON DELETE CASCADE,
    contract_type VARCHAR(255) NOT NULL,
    start_date    DATE,
    end_date      DATE,
    salary        NUMERIC
);

CREATE TABLE employee_benefits
(
    id          SERIAL PRIMARY KEY,
    employee_id INT NOT NULL REFERENCES employees (id) ON DELETE CASCADE,
    benefit_id  INT NOT NULL REFERENCES benefits (id) ON DELETE CASCADE,
    UNIQUE (employee_id, benefit_id)
);

-- Aggiornamento delle tabelle per includere chiavi esterne
ALTER TABLE departments
    ADD CONSTRAINT fk_manager_id FOREIGN KEY (manager_id) REFERENCES employees (id) ON DELETE SET NULL;
ALTER TABLE employees
    ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE SET NULL;

-- Inserimento dei dati nella tabella Employees
INSERT INTO employees (first_name, last_name, date_of_birth, password, email, role, department_id, url_image)
VALUES ('Mario', 'Rossi', '1980-05-15', 'password123', 'mario.rossi@example.com', 2, NULL, 'http://www.test-img.com'),
       ('Luca', 'Bianchi', '1975-08-23', 'password123', 'luca.bianchi@example.com', 1, NULL, 'http://www.test-img.com'),
       ('Sofia', 'Verdi', '1988-12-30', 'password123', 'sofia.verdi@example.com', 0, NULL, 'http://www.test-img.com');

-- Inserimento dei dati nella tabella Departments
-- Assumeremo di aggiornare manager_id dopo aver inserito questi dati,
-- poiché abbiamo bisogno degli ID effettivi degli employees
INSERT INTO departments (name, description, manager_id)
VALUES ('HR', 'Human Resources Department', NULL),        -- Aggiorna manager_id dopo
       ('IT', 'Information Technology Department', NULL), -- Aggiorna manager_id dopo
       ('Finance', 'Finance Department', NULL);

-- Aggiornamenti per manager_id possono essere fatti dopo aver ottenuto gli ID effettivi, ad esempio:
-- UPDATE departments SET manager_id = (SELECT id FROM employees WHERE email = 'luca.bianchi@example.com') WHERE name = 'HR';

-- Positions
INSERT INTO positions (title, description, level, department_id)
VALUES ('HR Manager', 'Manages HR department', 'Senior', 1), -- Assumi ID di department dopo inserimento
       ('IT Support Specialist', 'Provides IT support', 'Junior', 2);

-- Projects
INSERT INTO projects (name, description, start_date, end_date, department_id)
VALUES ('Website Redesign', 'Complete redesign of corporate website', '2023-01-01', '2023-06-01', 2);

-- Benefits
-- Nota: Aggiorna employee_id con l'ID effettivo dopo l'inserimento
INSERT INTO benefits (description, value, employee_id)
VALUES ('Health Insurance', 'Standard', 1), -- Assumi ID di employee dopo inserimento
       ('Annual Bonus', '10%', 2);

-- Contracts
-- Nota: Aggiorna employee_id con l'ID effettivo dopo l'inserimento
INSERT INTO contracts (employee_id, contract_type, start_date, end_date, salary)
VALUES (1, 'Permanent', '2023-01-01', NULL, 55000),
       (2, 'Temporary', '2023-01-01', '2023-12-31', 45000);

-- employee_benefits
-- Nota: Aggiorna employee_id e benefit_id con gli ID effettivi dopo l'inserimento
INSERT INTO employee_benefits (employee_id, benefit_id)
VALUES (1, 1),
       (2, 2);
