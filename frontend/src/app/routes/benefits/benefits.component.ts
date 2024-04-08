import { Component, OnInit } from "@angular/core"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { CompanyDataService } from "../../Services/company-data.service"
import { Columns } from "../../types"

@Component({
  selector: "app-benefits",
  standalone: true,
  imports: [CustomTableComponent, LayoutSingleComponent],
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
