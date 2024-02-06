-- Create Departments Table
CREATE TABLE Departments (
    Department_ID SERIAL PRIMARY KEY,
    Department_Name VARCHAR(255) NOT NULL,
    Description TEXT,
    Manager VARCHAR(255)  -- could be modified to an Employee ID if referencing the Employees table
);

-- Insert sample data into Departments
INSERT INTO Departments (Department_Name, Description, Manager) VALUES
('Human Resources', 'Handles company recruitment, policy, and employee relations.', 'John Doe'),
('IT', 'Responsible for managing technology and computer systems.', 'Jane Smith'),
('Marketing', 'Focuses on advertising and promoting the company''s products or services.', 'Emily Johnson');

-- Create Employees Table
CREATE TABLE Employees (
    Employee_ID SERIAL PRIMARY KEY,
    First_Name VARCHAR(255) NOT NULL,
    Last_Name VARCHAR(255) NOT NULL,
    Date_of_Birth DATE NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Department_ID INTEGER REFERENCES Departments(Department_ID)
);

-- Insert sample data into Employees
INSERT INTO Employees (First_Name, Last_Name, Date_of_Birth, Email, Department_ID) VALUES
('John', 'Doe', '1980-01-01', 'johndoe@example.com', 1),
('Jane', 'Smith', '1985-02-15', 'janesmith@example.com', 2),
('Emily', 'Johnson', '1990-03-22', 'emilyjohnson@example.com', 3);

-- Create Positions Table
CREATE TABLE Positions (
    Position_ID SERIAL PRIMARY KEY,
    Position_Title VARCHAR(255) NOT NULL,
    Description TEXT,
    Level VARCHAR(50),
    Department_ID INTEGER REFERENCES Departments(Department_ID)
);

-- Insert sample data into Positions
INSERT INTO Positions (Position_Title, Description, Level, Department_ID) VALUES
('HR Manager', 'Oversees HR department, policy development and compliance.', 'Senior', 1),
('Software Developer', 'Develops and maintains company software.', 'Junior', 2),
('Marketing Coordinator', 'Assists in the development and implementation of marketing plans.', 'Junior', 3);

-- Create Projects Table
CREATE TABLE Projects (
    Project_ID SERIAL PRIMARY KEY,
    Project_Name VARCHAR(255) NOT NULL,
    Description TEXT,
    Start_Date DATE,
    End_Date DATE,
    Department_ID INTEGER REFERENCES Departments(Department_ID)
);

-- Insert sample data into Projects
INSERT INTO Projects (Project_Name, Description, Start_Date, End_Date, Department_ID) VALUES
('Website Redesign', 'Complete overhaul of the corporate website.', '2023-01-01', '2023-06-01', 2),
('Employee Onboarding System', 'Develop a new system to improve the onboarding process.', '2023-02-15', '2023-08-15', 1);

-- Create Contracts Table
CREATE TABLE Contracts (
    Contract_ID SERIAL PRIMARY KEY,
    Employee_ID INTEGER REFERENCES Employees(Employee_ID),
    Contract_Type VARCHAR(255) NOT NULL,
    Start_Date DATE,
    End_Date DATE,
    Salary DECIMAL(10,2)
);

-- Insert sample data into Contracts
INSERT INTO Contracts (Employee_ID, Contract_Type, Start_Date, End_Date, Salary) VALUES
(1, 'Indefinite Term', '2021-01-01', NULL, 60000),
(2, 'Fixed Term', '2022-01-01', '2024-01-01', 50000);

-- Create Benefits Table
CREATE TABLE Benefits (
    Benefit_ID SERIAL PRIMARY KEY,
    Description TEXT,
    Value VARCHAR(255),
    Employee_ID INTEGER REFERENCES Employees(Employee_ID)
);

-- Insert sample data into Benefits
INSERT INTO Benefits (Description, Value, Employee_ID) VALUES
('Health Insurance', 'Company provided', 1),
('Gym Membership', 'Access to company gym', 2);
