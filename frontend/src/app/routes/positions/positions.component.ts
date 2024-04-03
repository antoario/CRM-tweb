import { Component } from "@angular/core"
import { CdkCell } from "@angular/cdk/table"
import { LayoutSingleComponent } from "../../Components/layout-single/layout-single.component"
import { CustomTableComponent } from "../../Components/custom-table/custom-table.component"

@Component({
  selector: "app-positions",
  standalone: true,
  imports: [CdkCell, LayoutSingleComponent, CustomTableComponent],
  templateUrl: "./positions.component.html",
  styleUrl: "./positions.component.scss",
})
export class PositionsComponent {}
