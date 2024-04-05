# CRM-tweb

This project is designed to develop a streamlined software solution for HR management. The objective is to establish a user-friendly platform for efficient HR operations.

## Settings up

### Docker

For setting up the application, we use Docker (`compose.yaml`) with these 3 containers:

- `Angular` frontend
- `Adminer` for managing `SQL` and running queries
- `Tomcat` backend

To build the project, you have to run

```bash
docker-compose build
docker-compose up
```

This will start the services and run the following services:

db (Postgres)
[adminer](localhost:5050) port 5050
[angular](localhost:4300) port 4300
[tomcat](localhost:8080) port 8080

> You have to run the containers (obviously).

If you make any edits in your project and you want to update the Backend, you can run

```bash
docker-compose down
docker-compose up --build
```

### Tomcat

With Docker, Tomcat is ready to use.

Local Development
If you prefer running Tomcat on your machine instead of in a Docker container, you have to update context.xml, which is located in conf/context.xml, and replace it with:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<!-- The contents of this file will be loaded for each web application -->
<Context>

    <!-- Default set of monitored resources. If one of these changes, the    -->
    <!-- web application will be reloaded.                                   -->
    <WatchedResource>WEB-INF/web.xml</WatchedResource>
    <WatchedResource>WEB-INF/tomcat-web.xml</WatchedResource>
    <WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>

    <!-- Uncomment this to enable session persistence across Tomcat restarts -->
    <!--
    <Manager pathname="SESSIONS.ser" />
    -->

    <Resource name="jdbc/postgres" auth="Container"
              type="javax.sql.DataSource" driverClassName="org.postgresql.Driver"
              url="jdbc:postgresql://localhost:5432/postgres"
              username="admin" password="admin"
              maxTotal="20" maxIdle="10" maxWaitMillis="-1"/>
</Context>
```

## Angular

For Angular, we are using Google Material Components, which are very useful for managing the data received from the backend.

## Tables and Key Fields

1. Employees
   - **id**: string; // primary key
   - **first_name**: string;
   - **last_name**: string;
   - **date_of_birth**: Date;
   - **password**: string;
   - **email**: string;
   - **role**: number; // form 0 to 2
   - **id_departments**: string; // foreign key linking to the departments table

2. Departments
   - **id**: string; // primary key
   - **department_name**: string;
   - **description**: string;
   - **id_manager**: string; // could be an employee ID

3. Positions
   - **id**: string; // primary key
   - **position_title**: string;
   - **description**: string;
   - **level**: string; // e.g., Junior, Senior
   - **id_department**: string; // foreign key

4. Projects
   - **id**: string; // primary key
   - **position_title**: string;
   - **description**: string;
   - **level**: string; // e.g., Junior, Senior
   - **id_department**: string; // foreign key

5. Contracts
   - **id**: string; // primary key
   - **project_name**: string;
   - **description**: string;
   - **start_date**: Date;
   - **end_date**: Date;
   - **id_department**: string; // foreign key to link projects to specific departments

6. Benefits (or Additional Compensation)
   - **id**: string; // primary key
   - **employee_id**: string; // foreign key
   - **contract_type**: string; // e.g., indefinite term, fixed term, part-time
   - **start_date**: Date;
   - **end_date**: Date;
   - **salary**: number;

7. employee_benefits just an associative table


<!-- TODO add er image for design schema -->

## The roles of users

There are 3 possible roles in this system with unique capabilities:

- **superAdmin**. Who got this role has the opportunity to manage all departments and employees. This role is saved as **0**
- **manager**. Is someone can manage and see only his department add/remove the projects, employees and soo. This role is saved as **1**
- **employee**. He can only see the department and his job. This role is saved as **2**

### SuperAdmin

Only the superAdmin can add the benefits

### Manager

The managerial role enables the addition and management of employees within a department.

This process involves both the frontend and backend systems. The frontend sends a token to the backend, which then verifies the user associated with that token. If the user possesses the appropriate access rights, the backend provides information specific to the relevant department.

Additionally, the backend is designed to disregard the department field in data submissions from users holding a managerial role.

Furthermore, the frontend is configured to conceal department selection options when a manager attempts to add new employees, benefits, or other related items.

Managers are also empowered to assign benefits to employees.

## Login

When a user (employee) login the server check email and password and responde with a json

```json

```
