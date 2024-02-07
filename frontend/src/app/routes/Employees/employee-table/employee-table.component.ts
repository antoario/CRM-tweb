import { Component, OnInit } from "@angular/core"
import { DataService } from "../../../Services/data.service"
import { Employee } from "../../../types/data"
import { environment } from "../../../../environments/environment"
import { JsonPipe, NgOptimizedImage } from "@angular/common"
import { RouterLink } from "@angular/router"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"

@Component({
  selector: "app-employee-table",
  standalone: true,
  imports: [NgOptimizedImage, JsonPipe, RouterLink, LayoutSingleComponent],
  templateUrl: "./employee-table.component.html",
  styleUrl: "./employee-table.component.scss",
})
export class EmployeeTableComponent implements OnInit {
  employees: Employee[] = []
  notDef = "Not defined yet"

  constructor(private data: DataService) {}

  ngOnInit() {
    this.data.getDataWithAuth<Employee[]>(`${environment.apiUrl}/employees`).subscribe((val) => {
      this.employees = val
    })
  }
}
