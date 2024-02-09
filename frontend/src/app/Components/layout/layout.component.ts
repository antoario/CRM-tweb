import { Component, OnInit } from "@angular/core"
import { Router, RouterLink, RouterOutlet } from "@angular/router"
import { MatToolbar } from "@angular/material/toolbar"
import { DataRowOutlet } from "@angular/cdk/table"
import { MatButton, MatIconButton } from "@angular/material/button"
import { MatIcon } from "@angular/material/icon"
import { UserService } from "../../Services/user.service"
import { MatSidenavModule } from "@angular/material/sidenav"
import { ItemsMenuComponent } from "../subcomponents/items-menu/items-menu.component"
import { JsonPipe } from "@angular/common"
import { CompanyDataService } from "../../Services/company-data.service"

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
  ],
  templateUrl: "./layout.component.html",
  styleUrl: "./layout.component.scss",
})
export class LayoutComponent implements OnInit {
  constructor(
    private userService: UserService,
    private router: Router,
    private dataComp: CompanyDataService
  ) {}

  ngOnInit() {
    this.dataComp.getAllData().subscribe()
  }

  logout() {
    this.userService.logout()
    this.router.navigate(["/login"])
  }
}
