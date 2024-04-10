import { Component, OnInit } from "@angular/core"
import { Benefit, CustomForm, Employee, SelectForm, TextForm } from "../../../types/data"
import { CrudBaseDirective } from "../../../Components/crud-base.directive"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { RouterLink } from "@angular/router"
import { environment } from "../../../../environments/environment"

@Component({
  selector: "app-single-benefit",
  templateUrl: "./single-benefit.component.html",
  styleUrls: ["./single-benefit.component.scss"],
  imports: [FormBuilderComponent, RouterLink],
  standalone: true,
})
export class SingleBenefitComponent extends CrudBaseDirective<Benefit> implements OnInit {
  benefits = new Map<string, CustomForm<any>>()

  override ngOnInit() {
    super.ngOnInit()

    const selectionForm = new SelectForm({
      key: "employee_id",
      label: "Employee",
      order: 2,
      width: "100%",
    })

    this.dataService.getDataWithAuth<Employee[]>(`${environment.apiUrl}/employees`).subscribe((val) => {
      const arr = val.map((emp) => {
        return {
          key: emp.id,
          value: `${emp.first_name} ${emp.last_name}`,
        }
      })
      selectionForm.setOptions(arr)
    })

    this.benefits
      .set(
        "description",
        new TextForm({
          order: 2,
          label: "Description",
          key: "description",
          width: "100%",
        })
      )
      .set(
        "value",
        new TextForm({
          key: "value",
          required: true,
          label: "Value",
          width: "100%",
          order: 1,
        })
      )
      .set("employee_id", selectionForm)
  }

  override baseUrl = "benefits"
}
