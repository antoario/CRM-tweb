import { Component, Input } from "@angular/core"
import {
  CdkCell,
  CdkCellDef,
  CdkColumnDef,
  CdkHeaderCell,
  CdkHeaderCellDef,
  CdkHeaderRow,
  CdkHeaderRowDef,
  CdkRow,
  CdkRowDef,
  CdkTable,
} from "@angular/cdk/table"
import { RouterLink } from "@angular/router"
import { Columns } from "../../types"

@Component({
  selector: "app-custom-table",
  standalone: true,
  imports: [
    CdkTable,
    CdkColumnDef,
    CdkHeaderCell,
    CdkCell,
    CdkCellDef,
    CdkHeaderCellDef,
    RouterLink,
    CdkHeaderRowDef,
    CdkHeaderRow,
    CdkRow,
    CdkRowDef,
  ],
  templateUrl: "./custom-table.component.html",
  styleUrl: "./custom-table.component.scss",
})
export class CustomTableComponent {
  @Input() data: any[] = []
  @Input() columnsDefs: string[] = []
  @Input() columns: Columns[] = []
}
