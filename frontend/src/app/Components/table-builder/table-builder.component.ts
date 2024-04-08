import { Component, Input, OnInit } from "@angular/core"
import { CdkTableModule } from "@angular/cdk/table"
import { JsonPipe, KeyValuePipe } from "@angular/common"

@Component({
  selector: "app-table-builder",
  standalone: true,
  imports: [CdkTableModule, JsonPipe, KeyValuePipe],
  templateUrl: "./table-builder.component.html",
  styleUrl: "./table-builder.component.scss",
})
export class TableBuilderComponent implements OnInit {
  @Input() columns: { key: string; label: string }[] = []
  @Input() dataSource: object[] = []
  columnsDefs: string[] = []

  ngOnInit() {
    for (const col of this.columns) this.columnsDefs.push(col.key)
  }
}
