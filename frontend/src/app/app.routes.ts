import { Routes } from "@angular/router"

import { LoginComponent } from "./routes/login/login.component"
import { LayoutComponent } from "./Components/layout/layout.component"
import { guardGuard } from "./guard.guard"
import { LoggedHomeComponent } from "./routes/logged-home/logged-home.component"

export const routes: Routes = [
  {
    path: "",
    component: LayoutComponent,
    canActivate: [guardGuard],
    children: [{ path: "", component: LoggedHomeComponent }],
  },
  { path: "login", component: LoginComponent },
]
