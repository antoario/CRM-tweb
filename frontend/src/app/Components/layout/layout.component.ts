import { Component } from "@angular/core"
import { RouterLink, RouterOutlet } from "@angular/router"
import { MatToolbar } from "@angular/material/toolbar"
import { DataRowOutlet } from "@angular/cdk/table"

@Component({
  selector: "app-layout",
  standalone: true,
  imports: [RouterOutlet, MatToolbar, DataRowOutlet, RouterLink],
  templateUrl: "./layout.component.html",
  styleUrl: "./layout.component.scss",
})
export class LayoutComponent {}
