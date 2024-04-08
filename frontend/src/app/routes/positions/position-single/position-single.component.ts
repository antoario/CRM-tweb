import { Component, ViewChild } from "@angular/core"
import { CustomForm, Department, JustInfo, OptionSelect, SelectForm, TextForm } from "../../../types/data"
import { GenericTableComponent } from "../../../Components/generic-table/generic-table.component"
import { DataService } from "../../../Services/data.service"
import { environment } from "../../../../environments/environment"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"
import { RouterLink } from "@angular/router"

@Component({
  selector: "app-position-single",
  standalone: true,
  imports: [GenericTableComponent, LayoutSingleComponent, RouterLink],
  templateUrl: "./position-single.component.html",
  styleUrl: "./position-single.component.scss",
})
export class PositionSingleComponent {
  valid = false
  selectionLevel = new SelectForm()
  controls: CustomForm<any>[] = [
    new JustInfo({
      key: "first info",
      label: "Insert the info of project",
      subtext: "Lorem ciao madre",
      width: "100%",
      order: 0,
    }),
    new TextForm({
      order: 1,
      key: "title",
      label: "Title",
    }),
    new TextForm({
      order: 2,
      key: "description",
      label: "Description",
    }),
    this.selectionLevel,
  ]
  loading = false
  @ViewChild(GenericTableComponent) genericTable!: GenericTableComponent

  constructor(private dataService: DataService) {
    this.selectionLevel.setOptions([
      { key: "junior", value: "Junior" },
      { key: "senior", value: "Senior" },
      { key: "hippy", value: "Mega hippy ☮️" },
    ])
  }

  ngOnInit() {
    this.dataService.getDataWithAuth<Department[]>(`${environment.apiUrl}/departments`).subscribe((val) => {
      const selectDepartment = new SelectForm({
        key: "department_id",
        label: "Department",
        required: true,
        order: 3,
      })
      const options: OptionSelect[] = val.map((op) => {
        return { key: op.id, value: op.name }
      })
      selectDepartment.setOptions(options)
      this.controls.push(selectDepartment)
      this.loading = true
    })
  }

  save() {
    this.genericTable.handleFormSubmit()
  }

  checkValid(valid: boolean) {
    this.valid = valid
  }
}
