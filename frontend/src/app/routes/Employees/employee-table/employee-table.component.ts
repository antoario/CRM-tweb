import { Component, OnDestroy, OnInit } from "@angular/core"
import { DataService } from "../../../Services/data.service"
import { Department, Employee, Position } from "../../../types/data"
import { environment } from "../../../../environments/environment"
import { JsonPipe, KeyValuePipe, NgOptimizedImage } from "@angular/common"
import { RouterLink } from "@angular/router"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"
import { CompanyDataService } from "../../../Services/company-data.service"
import { Subscription, switchMap } from "rxjs"
import { ROLE } from "../../../types"
import { UserService } from "../../../Services/user.service"

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
  departments: Map<any, Department> = new Map()

  constructor(
    private data: DataService,
    private compData: CompanyDataService,
    private userService: UserService
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

    this.data.getDataWithAuth<Department[]>(`${environment.apiUrl}/departments`).subscribe((val) => {
      val.map((dep) => {
        this.departments.set(dep.id, dep)
      })
    })

    this.data.getDataWithAuth<Department[]>(`${environment.apiUrl}/departments`).subscribe((val) => {
      val.map((dep) => {
        this.departments.set(dep.id, dep)
      })
    })

    this.userService.currUser
      .pipe(
        switchMap((val) => {
          if (val.role == ROLE.manager) {
            return this.data.getDataWithAuth<Employee[]>(`${environment.apiUrl}/employees?role=1`)
          } else {
            return this.data.getDataWithAuth<Employee[]>(`${environment.apiUrl}/employees`)
          }
        })
      )
      .subscribe((val) => {
        this.employees = val
      })
  }

  protected readonly ROLE = ROLE
}
