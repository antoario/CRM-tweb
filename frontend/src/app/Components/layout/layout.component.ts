import { Component } from "@angular/core"
import { Router, RouterLink, RouterOutlet } from "@angular/router"
import { MatToolbar } from "@angular/material/toolbar"
import { DataRowOutlet } from "@angular/cdk/table"
import { MatButton, MatIconButton } from "@angular/material/button"
import { MatIcon } from "@angular/material/icon"
import { UserService } from "../../Services/user.service"
import { MatDrawer, MatDrawerContainer, MatSidenavModule } from "@angular/material/sidenav"

@Component({
  selector: "app-layout",
  standalone: true,
  imports: [
    RouterOutlet,
    MatToolbar,
    DataRowOutlet,
    RouterLink,
    MatButton,
    MatIconButton,
    MatIcon,
    MatSidenavModule,
  ],
  templateUrl: "./layout.component.html",
  styleUrl: "./layout.component.scss",
})
export class LayoutComponent {
  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  logout() {
    this.userService.logout()
    this.router.navigate(["/login"])
  }
}
