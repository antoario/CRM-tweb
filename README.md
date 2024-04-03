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
   - `Employee ID` (primary key)
   - `First Name`
   - `Last Name`
   - `Date of Birth`
   - `Email`
   - `Department ID` (foreign key linking to the Departments table)

2. Departments
   - `Department ID` (primary key)
   - `Department Name`
   - `Description`
   - `Manager` (could be an employee ID or a name)

3. Positions
   - `Position ID` (primary key)
   - `Position Title`
   - `Description`
   - `Level` (e.g., Junior, Senior)
   - `Department ID` (foreign key)

4. Projects
   - `Project ID` (primary key)
   - `Project Name`
   - `Description`
   - `Start Date`
   - `End Date`
   - `Department ID` (foreign key to link projects to specific departments)

5. Contracts
   - `Contract ID` (primary key)
   - `Employee ID` (foreign key)
   - `Contract Type` (e.g., indefinite term, fixed term, part-time)
   - `Start Date`
   - `End Date`
   - `Salary`

6. Benefits (or Additional Compensation)
   - `Benefit ID` (primary key)
   - `Description`
   - `Value` (could be a monetary amount or a qualitative description, like "company gym" or "meal vouchers")
   - `Employee ID` (foreign key)

<!-- TODO add er image for design schema -->

