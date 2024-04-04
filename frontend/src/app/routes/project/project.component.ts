import { Component, OnInit } from "@angular/core"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { CompanyDataService } from "../../Services/company-data.service"
import { Columns } from "../../types"
import { RouterLink } from "@angular/router"

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

  constructor(private companyDataService: CompanyDataService) {}

  ngOnInit() {
    this.companyDataService.getProjects().subscribe((val) => {
      this.data = Array.from(val, ([, value]) => value)
    })
  }
}
