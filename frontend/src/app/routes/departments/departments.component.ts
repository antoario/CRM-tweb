import { Component, OnInit } from "@angular/core"
import { CompanyDataService } from "../../Services/company-data.service"
import { Department } from "../../types/data"
import { JsonPipe, KeyValuePipe } from "@angular/common"
import { TableBuilderComponent } from "../../Components/table-builder/table-builder.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { CdkCell, CdkCellDef, CdkHeaderCell, CdkTableModule } from "@angular/cdk/table"
import { RouterLink } from "@angular/router"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { UserService } from "../../Services/user.service"
import { concatWith, map, switchMap } from "rxjs"
import { ROLE } from "../../types"
import { DataService } from "../../Services/data.service"
import { environment } from "../../../environments/environment"

@Component({
  selector: "app-departments",
  standalone: true,
  imports: [
    JsonPipe,
    KeyValuePipe,
    TableBuilderComponent,
    LayoutSingleComponent,
    CdkCell,
    CdkCellDef,
    CdkHeaderCell,
    CdkTableModule,
    RouterLink,
    CustomTableComponent,
  ],
  templateUrl: "./departments.component.html",
  styleUrl: "./departments.component.scss",
})
export class DepartmentsComponent implements OnInit {
  departments: Department[] = []
  columns: { key: string; label: string }[] = [
    { key: "id", label: "ID" },
    {
      key: "name",
      label: "Name",
    },
    { key: "description", label: "Description" },
  ]
  columnsDefs: string[] = ["name", "description", "actions"]
  userId = -1

  constructor(
    public compData: CompanyDataService,
    private userService: UserService,
    private dataService: DataService
  ) {}

  ngOnInit() {
    this.userService.currUser
      .pipe(
        switchMap((val) => {
          if (val.role == ROLE.manager) {
            return this.dataService.getDataWithAuth<Department[]>(
              `${environment.apiUrl}/departments?id=${val.role}`
            )
          }
          return this.dataService.getDataWithAuth<Department[]>(`${environment.apiUrl}/departments`)
        }),
        map((dep) => {
          console.log(dep)
          this.departments = dep
        })
      )
      .subscribe()
  }
}
