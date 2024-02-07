import { Component, Input } from "@angular/core"
import { JsonPipe } from "@angular/common"
import { MatIconModule } from "@angular/material/icon"
import { RouterLink } from "@angular/router"

@Component({
  selector: "app-items-menu",
  standalone: true,
  imports: [JsonPipe, MatIconModule, RouterLink],
  templateUrl: "./items-menu.component.html",
  styleUrl: "./items-menu.component.scss",
})
export class ItemsMenuComponent {
  @Input() element!: { text: string; link: string; icon: string }
}
