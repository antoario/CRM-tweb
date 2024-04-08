import { Component, OnInit } from "@angular/core"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { Columns } from "../../types"
import { CompanyDataService } from "../../Services/company-data.service"

@Component({
  selector: "app-contracts",
  standalone: true,
  imports: [CustomTableComponent, LayoutSingleComponent],
  templateUrl: "./contracts.component.html",
  styleUrl: "./contracts.component.scss",
})
export class ContractsComponent implements OnInit {
  columns: Columns[] = [
    { key: "title", label: "Title" },
    { key: "description", label: "Description" },
    { key: "actions", label: "Description" },
  ]
  columnsDefs = ["title", "description", "actions"]
  data: any[] = []

  constructor(private companyDataService: CompanyDataService) {}

  ngOnInit() {
    this.companyDataService.getContracts().subscribe((val) => {
      this.data = Array.from(val, ([, value]) => value)
    })
  }
}
