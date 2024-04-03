import { Component, OnInit } from "@angular/core"
import { CompanyDataService } from "../../Services/company-data.service"
import { Department } from "../../types/data"
import { JsonPipe, KeyValuePipe } from "@angular/common"
import { TableBuilderComponent } from "../../Components/table-builder/table-builder.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { CdkCell, CdkCellDef, CdkHeaderCell, CdkTableModule } from "@angular/cdk/table"
import { RouterLink } from "@angular/router"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"

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
    { key: "name", label: "Name" },
    { key: "description", label: "Description" },
  ]
  columnsDefs: string[] = ["name", "description", "actions"]

  constructor(public compData: CompanyDataService) {}

  ngOnInit() {
    this.compData.getDepartments().subscribe((val) => {
      this.departments = Array.from(val.entries()).map(([, val]) => val)
    })
  }
}
