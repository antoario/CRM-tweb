import { UserService } from "../../Services/user.service"
import { UserData } from "../../types/UserTypes"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { ROLE } from "../../types"
import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { MatButtonModule } from "@angular/material/button"
import { MatIconModule } from "@angular/material/icon"
import { MatListModule } from "@angular/material/list"
import { MatSidenavModule } from "@angular/material/sidenav"
import { MatToolbarModule } from "@angular/material/toolbar"
import { RouterLink, RouterOutlet } from "@angular/router"

@Component({
  selector: "app-logged-home",
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    RouterOutlet,
    RouterLink,
    LayoutSingleComponent,
  ],
  templateUrl: "./logged-home.component.html",
  styleUrl: "./logged-home.component.scss",
})
export class LoggedHomeComponent {
  user: UserData | null = null

  constructor(private userService: UserService) {
    this.userService.loadUser().subscribe((usr) => {
      if (usr) this.user = usr
    })
  }

  protected readonly ROLE = ROLE
}
