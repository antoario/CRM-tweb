import { Routes } from "@angular/router"

import { LoginComponent } from "./routes/login/login.component"
import { LayoutComponent } from "./Components/layout/layout.component"
import { guardGuard } from "./guard.guard"
import { LoggedHomeComponent } from "./routes/logged-home/logged-home.component"
import { DepartmentsComponent } from "./routes/departments/departments.component"
import { AddEmployeesComponent } from "./routes/Employees/add-employees/add-employees.component"
import { EmployeeTableComponent } from "./routes/Employees/employee-table/employee-table.component"

export const routes: Routes = [
  {
    path: "",
    component: LayoutComponent,
    canActivate: [guardGuard],
    children: [
      { path: "", component: LoggedHomeComponent },
      { path: "departments", component: DepartmentsComponent },
      {
        path: "employees",
        children: [
          { path: "add", component: AddEmployeesComponent },
          { path: "", component: EmployeeTableComponent },
          { path: ":id", component: AddEmployeesComponent },
        ],
      },
    ],
  },
  { path: "login", component: LoginComponent },
]
