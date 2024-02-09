import { Component, OnDestroy, OnInit } from "@angular/core"
import { DataService } from "../../../Services/data.service"
import { Department, Employee, Position } from "../../../types/data"
import { environment } from "../../../../environments/environment"
import { JsonPipe, KeyValuePipe, NgOptimizedImage } from "@angular/common"
import { RouterLink } from "@angular/router"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"
import { CompanyDataService } from "../../../Services/company-data.service"
import { Subscription } from "rxjs"

@Component({
  selector: "app-employee-table",
  standalone: true,
  imports: [NgOptimizedImage, JsonPipe, RouterLink, LayoutSingleComponent, KeyValuePipe],
  templateUrl: "./employee-table.component.html",
  styleUrl: "./employee-table.component.scss",
})
export class EmployeeTableComponent implements OnInit, OnDestroy {
  employees: Employee[] = []
  notDef = "Not defined yet"
  subscription = new Subscription()
  position: Map<string, Position> = new Map()
  departments: Map<string, Department> = new Map()

  constructor(
    private data: DataService,
    private compData: CompanyDataService
  ) {}

  ngOnDestroy() {
    this.subscription.unsubscribe()
  }

  ngOnInit() {
    this.subscription.add(
      this.compData.positions$.subscribe((el) => {
        this.position = el
      })
    )
    this.subscription.add(
      this.compData.departments$.subscribe((el) => {
        this.departments = el
      })
    )

    this.data.getDataWithAuth<Employee[]>(`${environment.apiUrl}/employees`).subscribe((val) => {
      this.employees = val
    })
  }
}
