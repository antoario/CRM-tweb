import { Component, OnInit } from "@angular/core"
import { Router, RouterLink, RouterOutlet } from "@angular/router"
import { MatToolbar } from "@angular/material/toolbar"
import { DataRowOutlet } from "@angular/cdk/table"
import { MatButton, MatIconButton } from "@angular/material/button"
import { MatIcon } from "@angular/material/icon"
import { UserService } from "../../Services/user.service"
import { MatSidenavModule } from "@angular/material/sidenav"
import { ItemsMenuComponent } from "../subcomponents/items-menu/items-menu.component"
import { JsonPipe, NgIf } from "@angular/common"
import { CompanyDataService } from "../../Services/company-data.service"
import { MENUITEMS } from "../../menu-items"
import { User } from "../../types"
import { UserData } from "../../types/UserTypes"
import { map } from "rxjs"

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
    ItemsMenuComponent,
    JsonPipe,
    NgIf,
  ],
  templateUrl: "./layout.component.html",
  styleUrl: "./layout.component.scss",
})
export class LayoutComponent implements OnInit {
  user!: UserData

  constructor(
    private userService: UserService,
    private router: Router,
    private dataComp: CompanyDataService
  ) {}

  ngOnInit() {
    this.dataComp.getAllData().subscribe()
    this.userService
      .loadUser()
      .pipe(
        map((usr) => {
          if (usr) {
            this.userService.currUser.next(usr)
            this.user = usr
          }
        })
      )
      .subscribe()
  }

  logout() {
    this.userService.logout()
    this.router.navigate(["/login"])
  }

  MENUITEMS = MENUITEMS
}
