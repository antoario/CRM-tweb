import { Component, OnInit } from "@angular/core"
import { CdkCell } from "@angular/cdk/table"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { Columns } from "../../types"
import { CompanyDataService } from "../../Services/company-data.service"

@Component({
  selector: "app-positions",
  standalone: true,
  imports: [CdkCell, LayoutSingleComponent, CustomTableComponent],
  templateUrl: "./positions.component.html",
  styleUrl: "./positions.component.scss",
})
export class PositionsComponent implements OnInit {
  columns: Columns[] = [
    { key: "title", label: "Title" },
    { key: "description", label: "Description" },
  ]
  columnsDefs = ["title", "description", "actions"]
  data: any[] = []

  constructor(private companyDataService: CompanyDataService) {}

  ngOnInit() {
    this.companyDataService.getPositions().subscribe((val) => {
      this.data = Array.from(val, ([, value]) => value)
    })
  }
}
