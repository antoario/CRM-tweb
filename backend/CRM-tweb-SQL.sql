-- Creazione della tabella employees
CREATE TABLE employees (
                           id SERIAL PRIMARY KEY,
                           first_name VARCHAR(255) NOT NULL,
                           last_name VARCHAR(255) NOT NULL,
                           date_of_birth DATE NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE NOT NULL,
                           role INTEGER NOT NULL CHECK (role >= 0 AND role <= 2),
                           department_id INT NULL,
                           url_image VARCHAR(255) NULL
);

-- Creazione della tabella departments
CREATE TABLE departments (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             description TEXT NULL
);

-- Creazione della tabella positions
CREATE TABLE positions (
                           id SERIAL PRIMARY KEY,
                           title VARCHAR(255) NOT NULL,
                           description TEXT NULL,
                           level VARCHAR(50) NOT NULL,
                           department_id INT REFERENCES departments (id) ON DELETE SET NULL
);

-- Creazione della tabella projects
CREATE TABLE projects (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT NULL,
                          start_date DATE NOT NULL,
                          end_date DATE NULL,
                          department_id INT REFERENCES departments (id) ON DELETE SET NULL
);

-- Creazione della tabella benefits
CREATE TABLE benefits (
                          id SERIAL PRIMARY KEY,
                          description TEXT NULL,
                          value VARCHAR(255) NOT NULL,
                          employee_id INT REFERENCES employees (id) ON DELETE CASCADE
);

-- Creazione della tabella contracts
CREATE TABLE contracts (
                           id SERIAL PRIMARY KEY,
                           employee_id INT REFERENCES employees (id) ON DELETE CASCADE,
                           contract_type VARCHAR(255) NOT NULL,
                           start_date DATE NOT NULL,
                           end_date DATE NULL,
                           salary NUMERIC NOT NULL
);

-- Creazione della tabella employee_benefits
CREATE TABLE employee_benefits (
                                   id SERIAL PRIMARY KEY,
                                   employee_id INT NOT NULL REFERENCES employees (id) ON DELETE CASCADE,
                                   benefit_id INT NOT NULL REFERENCES benefits (id) ON DELETE CASCADE,
                                   UNIQUE (employee_id, benefit_id)
);

-- Aggiornamento delle tabelle per includere chiavi esterne
ALTER TABLE employees ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE SET NULL;

-- Inserimento dei dati nelle tabelle
INSERT INTO employees (first_name, last_name, date_of_birth, password, email, role, department_id, url_image) VALUES
                                                                                                                  ('Mario', 'Rossi', '1980-05-15', 'password123', 'mario.rossi@example.com', 2, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Luca', 'Bianchi', '1975-08-23', 'password123', 'luca.bianchi@example.com', 1, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Sofia', 'Verdi', '1988-12-30', 'password123', 'sofia.verdi@example.com', 0, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Giuseppe', 'Ricci', '1990-03-20', 'password123', 'giuseppe.ricci@example.com', 1, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Elena', 'Conti', '1985-07-10', 'password123', 'elena.conti@example.com', 0, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Alessandro', 'Gallo', '1982-11-05', 'password123', 'alessandro.gallo@example.com', 2, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Giovanna', 'Marini', '1993-02-15', 'password123', 'giovanna.marini@example.com', 1, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Fabio', 'Moretti', '1978-09-25', 'password123', 'fabio.moretti@example.com', 2, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Chiara', 'Ferrari', '1987-06-30', 'password123', 'chiara.ferrari@example.com', 0, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Simone', 'Barbieri', '1991-04-18', 'password123', 'simone.barbieri@example.com', 1, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Francesca', 'Mazza', '1980-08-12', 'password123', 'francesca.mazza@example.com', 2, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Marco', 'Fontana', '1976-01-05', 'password123', 'marco.fontana@example.com', 0, NULL, 'http://www.test-img.com'),
                                                                                                                  ('Laura', 'Rinaldi', '1984-12-08', 'password123', 'laura.rinaldi@example.com', 1, NULL, 'http://www.test-img.com');

INSERT INTO departments (name, description) VALUES
                                                ('HR', 'Human Resources Department'),
                                                ('IT', 'Information Technology Department'),
                                                ('Finance', 'Finance Department'),
                                                ('Sales', 'Sales Department'),
                                                ('Marketing', 'Marketing Department'),
                                                ('Operations', 'Operations Department'),
                                                ('Legal', 'Legal Department'),
                                                ('Customer Service', 'Customer Service Department');

INSERT INTO positions (title, description, level, department_id) VALUES
                                                                     ('HR Manager', 'Manages HR department', 'Senior', 1),
                                                                     ('IT Support Specialist', 'Provides IT support', 'Junior', 2),
                                                                     ('Sales Manager', 'Manages sales team', 'Senior', 4),
                                                                     ('Marketing Coordinator', 'Coordinates marketing activities', 'Junior', 5),
                                                                     ('Operations Director', 'Directs operations department', 'Senior', 3),
                                                                     ('Legal Counsel', 'Provides legal advice', 'Senior', 7),
                                                                     ('Customer Service Representative', 'Handles customer inquiries', 'Entry', 8);

INSERT INTO projects (name, description, start_date, end_date, department_id) VALUES
                                                                                  ('Website Redesign', 'Complete redesign of corporate website', '2023-01-01', '2023-06-01', 2),
                                                                                  ('New Product Launch', 'Launch of new product line', '2023-04-01', '2023-10-01', 4),
                                                                                  ('Market Research Initiative', 'Conducts market research', '2023-03-15', '2023-07-15', 5),
                                                                                  ('Legal Compliance Audit', 'Audit for legal compliance', '2023-05-01', '2023-08-01', 7),
                                                                                  ('Customer Feedback Analysis', 'Analysis of customer feedback', '2023-02-01', '2023-06-01', 8),
                                                                                  ('Sales Training Program', 'Training program for sales team', '2023-06-01', '2023-12-01', 4);

INSERT INTO benefits (description, value, employee_id) VALUES
                                                           ('Health Insurance', 'Standard', 1),
                                                           ('Annual Bonus', '10%', 2),
                                                           ('Dental Insurance', 'Standard', 3),
                                                           ('Gym Membership', 'Company-sponsored gym membership', 4),
                                                           ('Paid Time Off', 'Vacation and sick leave', 5),
                                                           ('401(k) Matching', 'Matching contribution to retirement plan', 6),
                                                           ('Education Assistance', 'Tuition reimbursement', 7),
                                                           ('Flexible Spending Account', 'Pre-tax savings account for medical expenses', 8),
                                                           ('Stock Options', 'Company stock options', 9),
                                                           ('Health Savings Account', 'Tax-advantaged savings account for medical expenses', 10),
                                                           ('Life Insurance', 'Life insurance coverage', 11),
                                                           ('Childcare Assistance', 'Subsidized childcare services', 12),
                                                           ('Dental Insurance', 'Standard', 13),
                                                           ('Gym Membership', 'Company-sponsored gym membership', 1),
                                                           ('Paid Time Off', 'Vacation and sick leave', 2),
                                                           ('401(k) Matching', 'Matching contribution to retirement plan', 3),
                                                           ('Education Assistance', 'Tuition reimbursement', 4),
                                                           ('Flexible Spending Account', 'Pre-tax savings account for medical expenses', 5),
                                                           ('Stock Options', 'Company stock options', 6),
                                                           ('Health Savings Account', 'Tax-advantaged savings account for medical expenses', 7),
                                                           ('Life Insurance', 'Life insurance coverage', 8),
                                                           ('Childcare Assistance', 'Subsidized childcare services', 9),
                                                           ('Dental Insurance', 'Standard', 10);

INSERT INTO contracts (employee_id, contract_type, start_date, end_date, salary) VALUES
                                                                                     (1, 'Permanent', '2023-01-01', NULL, 55000),
                                                                                     (2, 'Temporary', '2023-01-01', '2023-12-31', 45000),
                                                                                     (3, 'Permanent', '2023-01-01', NULL, 50000),
                                                                                     (4, 'Permanent', '2023-01-01', NULL, 60000),
                                                                                     (5, 'Temporary', '2023-01-01', '2023-12-31', 48000),
                                                                                     (6, 'Permanent', '2023-01-01', NULL, 55000),
                                                                                     (7, 'Permanent', '2023-01-01', NULL, 58000),
                                                                                     (8, 'Temporary', '2023-01-01', '2023-12-31', 50000),
                                                                                     (9, 'Permanent', '2023-01-01', NULL, 62000),
                                                                                     (10, 'Permanent', '2023-01-01', NULL, 59000),
                                                                                     (11, 'Temporary', '2023-01-01', '2023-12-31', 52000),
                                                                                     (12, 'Permanent', '2023-01-01', NULL, 58000),
                                                                                     (13, 'Temporary', '2023-01-01', '2023-12-31', 54000);

INSERT INTO employee_benefits (employee_id, benefit_id) VALUES
                                                            (1, 2),
                                                            (2, 4),
                                                            (3, 1),
                                                            (4, 3),
                                                            (5, 5),
                                                            (6, 6),
                                                            (7, 7),
                                                            (8, 8),
                                                            (9, 9),
                                                            (10, 10),
                                                            (11, 11),
                                                            (12, 12),
                                                            (13, 13);
