-- Create Departments Table
CREATE TABLE departments (
    id SERIAL PRIMARY KEY,
    department_name VARCHAR(255) NOT NULL,
    description TEXT,
    id_manager VARCHAR(255)  -- could be modified to an Employee ID if referencing the Employees table
);

-- Insert sample data into Departments
INSERT INTO departments (department_name, description, id_manager) VALUES
('Human Resources', 'Handles company recruitment, policy, and employee relations.', 'John Doe'),
('IT', 'Responsible for managing technology and computer systems.', 'Jane Smith'),
('Marketing', 'Focuses on advertising and promoting the company''s products or services.', 'Emily Johnson');

-- Create Employees Table
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role INTEGER NOT NULL CHECK (role >= 0 AND role <= 2),
    id_departments INTEGER REFERENCES departments(id)
);

-- Insert sample data into Employees with roles
INSERT INTO employees (first_name, last_name, date_of_birth, password, email, role, id_departments) VALUES
('John', 'Doe', '1980-01-01', 'password123', 'johndoe@example.com', 0, 1),
('Jane', 'Smith', '1985-02-15', 'password123', 'janesmith@example.com', 1, 2),
('Emily', 'Johnson', '1990-03-22', 'password123', 'emilyjohnson@example.com', 2, 3);

-- Create Positions Table
CREATE TABLE positions (
    id SERIAL PRIMARY KEY,
    position_title VARCHAR(255) NOT NULL,
    description TEXT,
    level VARCHAR(50),
    id_department INTEGER REFERENCES departments(id)
);

-- Insert sample data into Positions
INSERT INTO positions (position_title, description, level, id_department) VALUES
('HR Manager', 'Oversees HR department, policy development and compliance.', 'Senior', 1),
('Software Developer', 'Develops and maintains company software.', 'Junior', 2),
('Marketing Coordinator', 'Assists in the development and implementation of marketing plans.', 'Junior', 3);

-- Create Projects Table
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    id_department INTEGER REFERENCES departments(id)
);

-- Insert sample data into Projects
INSERT INTO projects (project_name, description, start_date, end_date, id_department) VALUES
('Website Redesign', 'Complete overhaul of the corporate website.', '2023-01-01', '2023-06-01', 2),
('Employee Onboarding System', 'Develop a new system to improve the onboarding process.', '2023-02-15', '2023-08-15', 1);

-- Create Contracts Table
CREATE TABLE contracts (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER REFERENCES employees(id),
    contract_type VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    salary DECIMAL(10,2)
);

-- Insert sample data into Contracts
INSERT INTO contracts (employee_id, contract_type, start_date, end_date, salary) VALUES
(1, 'Indefinite Term', '2021-01-01', NULL, 60000),
(2, 'Fixed Term', '2022-01-01', '2024-01-01', 50000);

-- Create Benefits Table
CREATE TABLE benefits (
    id SERIAL PRIMARY KEY,
    description TEXT,
    value VARCHAR(255),
    employee_id INTEGER REFERENCES employees(id)
);

-- Insert sample data into Benefits
INSERT INTO benefits (description, value, employee_id) VALUES
('Health Insurance', 'Company provided', 1),
('Gym Membership', 'Access to company gym', 2);

CREATE TABLE employee_benefits (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employees(id),
    benefit_id INTEGER NOT NULL REFERENCES benefits(id),
    UNIQUE(employee_id, benefit_id)  -- Assicura che la coppia impiegato-benefit sia unica
);

INSERT INTO employee_benefits (employee_id, benefit_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 2);
