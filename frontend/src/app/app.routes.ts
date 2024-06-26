import { Routes } from "@angular/router"

import { LoginComponent } from "./routes/login/login.component"
import { LayoutComponent } from "./Components/layout/layout.component"
import { guardGuard } from "./guard.guard"
import { LoggedHomeComponent } from "./routes/logged-home/logged-home.component"
import { DepartmentsComponent } from "./routes/departments/departments.component"
import { AddEmployeesComponent } from "./routes/Employees/add-employees/add-employees.component"
import { EmployeeTableComponent } from "./routes/Employees/employee-table/employee-table.component"
import { ViewDepartmentComponent } from "./routes/departments/view-department/view-department.component"
import { PositionsComponent } from "./routes/positions/positions.component"
import { ContractsComponent } from "./routes/contracts/contracts.component"
import { BenefitsComponent } from "./routes/benefits/benefits.component"
import { ProjectComponent } from "./routes/project/project.component"
import { SingleBenefitComponent } from "./routes/benefits/single-benefit/single-benefit.component"
import { AddProjectComponent } from "./routes/project/add-project/add-project.component"
import { PositionSingleComponent } from "./routes/positions/position-single/position-single.component"

export const routes: Routes = [
  {
    path: "",
    component: LayoutComponent,
    canActivate: [guardGuard],
    children: [
      { path: "", component: LoggedHomeComponent },
      {
        path: "departments",
        children: [
          { path: "add", component: ViewDepartmentComponent },
          { path: "", component: DepartmentsComponent },
          { path: ":id", component: ViewDepartmentComponent },
        ],
      },
      {
        path: "employees",
        children: [
          { path: "add", component: AddEmployeesComponent },
          { path: "", component: EmployeeTableComponent },
          { path: ":id", component: AddEmployeesComponent },
        ],
      },
      {
        path: "positions",
        children: [
          { path: "", component: PositionsComponent },
          { path: "add", component: PositionSingleComponent },
          { path: ":id", component: PositionSingleComponent },
        ],
      },
      {
        path: "contracts",
        children: [{ path: "", component: ContractsComponent }],
      },
      {
        path: "benefits",
        children: [
          { path: "", component: BenefitsComponent },
          { path: "add", component: SingleBenefitComponent },
          { path: ":id", component: SingleBenefitComponent },
        ],
      },
      {
        path: "projects",
        children: [
          { path: "", component: ProjectComponent },
          { path: "add", component: AddProjectComponent },
          { path: ":id", component: AddProjectComponent },
        ],
      },
    ],
  },
  { path: "login", component: LoginComponent },
]
