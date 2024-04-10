import { Component, OnInit } from "@angular/core"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { CompanyDataService } from "../../Services/company-data.service"
import { Columns } from "../../types"
import { RouterLink } from "@angular/router"
import { UserService } from "../../Services/user.service"
import { switchMap, tap } from "rxjs"
import { DataService } from "../../Services/data.service"
import { Project } from "../../types/data"
import { environment } from "../../../environments/environment"

@Component({
  selector: "app-project",
  standalone: true,
  imports: [CustomTableComponent, LayoutSingleComponent, RouterLink],
  templateUrl: "./project.component.html",
  styleUrl: "./project.component.scss",
})
export class ProjectComponent implements OnInit {
  data: any[] = []
  columns: Columns[] = [
    { key: "id", label: "id" },
    { key: "name", label: "Name" },
  ]
  columnsDefs = ["id", "name", "actions"]

  constructor(
    private companyDataService: CompanyDataService,
    private userService: UserService,
    private dataService: DataService
  ) {}

  ngOnInit() {
    this.userService.currUser
      .pipe(
        switchMap((usr) => {
          if (usr.role > 0) {
            console.log(usr.department_id)
            return this.dataService.getDataWithAuth<Project[]>(
              `${environment.apiUrl}/projects?role=${usr.department_id}`
            )
          } else {
            return this.dataService.getDataWithAuth<Project[]>(`${environment.apiUrl}/projects`)
          }
        })
      )
      .subscribe((val) => {
        this.data = val
      })
  }
}
