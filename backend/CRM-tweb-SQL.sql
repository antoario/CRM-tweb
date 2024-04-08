-- Creazione della tabella Departments
CREATE TABLE departments (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    manager_id VARCHAR(255) REFERENCES employees(id) -- Assicurati che esista una tabella employees da cui fare riferimento
);

-- Creazione della tabella Employees
CREATE TABLE employees (
    id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role INTEGER NOT NULL CHECK (role >= 0 AND role <= 2),
    department_id VARCHAR(255) REFERENCES departments(id)
);

-- Creazione della tabella Positions
CREATE TABLE positions (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    level VARCHAR(50),
    department_id VARCHAR(255) REFERENCES departments(id)
);

-- Creazione della tabella Projects
CREATE TABLE projects (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    department_id VARCHAR(255) REFERENCES departments(id)
);

-- Creazione della tabella Benefits
CREATE TABLE benefits (
    id VARCHAR(255) PRIMARY KEY,
    description TEXT,
    value VARCHAR(255),
    employee_id VARCHAR(255) REFERENCES employees(id)
);

-- Creazione della tabella Contracts
CREATE TABLE contracts (
    id VARCHAR(255) PRIMARY KEY,
    employee_id VARCHAR(255) REFERENCES employees(id),
    contract_type VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    salary NUMERIC
);

-- Creazione della tabella associativa employee_benefits
CREATE TABLE employee_benefits (
    id SERIAL PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL REFERENCES employees(id),
    benefit_id VARCHAR(255) NOT NULL REFERENCES benefits(id),
    UNIQUE(employee_id, benefit_id) -- Assicura che la coppia impiegato-benefit sia unica
);
