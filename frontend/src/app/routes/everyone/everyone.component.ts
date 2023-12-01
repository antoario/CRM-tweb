import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { MatButtonModule } from "@angular/material/button"
import { UserService } from "../../guard"
import { RouterLink } from "@angular/router"

@Component({
  selector: "app-everyone",
  standalone: true,
  imports: [CommonModule, MatButtonModule, RouterLink],
  templateUrl: "./everyone.component.html",
  styleUrl: "./everyone.component.scss",
})
export class EveryoneComponent {
  constructor(private userService: UserService) {}
  login() {
    this.userService.login("johndoe@example.com", "idjwois").subscribe()
  }
}
