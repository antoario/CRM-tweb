import { Component } from "@angular/core"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"

@Component({
  selector: "app-contracts",
  standalone: true,
  imports: [CustomTableComponent, LayoutSingleComponent],
  templateUrl: "./contracts.component.html",
  styleUrl: "./contracts.component.scss",
})
export class ContractsComponent {}
