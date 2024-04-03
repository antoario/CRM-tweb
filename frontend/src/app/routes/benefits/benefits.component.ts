import { Component } from "@angular/core"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"

@Component({
  selector: "app-benefits",
  standalone: true,
  imports: [CustomTableComponent, LayoutSingleComponent],
  templateUrl: "./benefits.component.html",
  styleUrl: "./benefits.component.scss",
})
export class BenefitsComponent {}
