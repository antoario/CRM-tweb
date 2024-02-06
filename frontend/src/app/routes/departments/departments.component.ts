import { Component } from "@angular/core"
import { DepartmentsService } from "../../Services/deparments.service"

@Component({
  selector: "app-departments",
  standalone: true,
  imports: [],
  templateUrl: "./departments.component.html",
  styleUrl: "./departments.component.scss",
})
export class DepartmentsComponent {
  constructor(private departmentsService: DepartmentsService) {}
}
