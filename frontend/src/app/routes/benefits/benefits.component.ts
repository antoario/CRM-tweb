import { Component, OnInit } from "@angular/core"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { CompanyDataService } from "../../Services/company-data.service"
import { Columns } from "../../types"
import { DataRowOutlet } from "@angular/cdk/table"
import { RouterLink } from "@angular/router"

@Component({
  selector: "app-benefits",
  standalone: true,
  imports: [CustomTableComponent, LayoutSingleComponent, DataRowOutlet, RouterLink],
  templateUrl: "./benefits.component.html",
  styleUrl: "./benefits.component.scss",
})
export class BenefitsComponent implements OnInit {
  data: any[] = []
  columns: Columns[] = [
    { key: "description", label: "Description" },
    { key: "value", label: "Value" },
  ]
  columnsDefs = ["description", "value", "actions"]

  ngOnInit() {
    this.companyDataService.getBenefits().subscribe((val) => {
      this.data = Array.from(val, ([, value]) => value)
    })
  }

  constructor(private companyDataService: CompanyDataService) {}
}
