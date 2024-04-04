import { Component, OnInit } from "@angular/core"
import { GenericTableComponent } from "../../../Components/generic-table/generic-table.component"
import { CustomForm, Department, JustInfo, OptionSelect, SelectForm, TextForm } from "../../../types/data"
import { DataService } from "../../../Services/data.service"
import { environment } from "../../../../environments/environment"

@Component({
  selector: "app-add-project",
  standalone: true,
  imports: [GenericTableComponent],
  templateUrl: "./add-project.component.html",
  styleUrl: "./add-project.component.scss",
})
export class AddProjectComponent implements OnInit {
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
      key: "name",
      label: "Title",
    }),
    new TextForm({
      order: 2,
      key: "description",
      label: "Description",
    }),
  ]
  loading = false

  constructor(private dataService: DataService) {}

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
}
