import { AfterViewInit, Component, OnInit } from "@angular/core"
import { GenericTableComponent } from "../../../Components/generic-table/generic-table.component"
import {
  CustomForm,
  DateQuestion,
  Department,
  JustInfo,
  Project,
  SelectForm,
  TextForm,
} from "../../../types/data"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"
import { RouterLink } from "@angular/router"
import { CrudBaseDirective } from "../../../Components/crud-base.directive"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { environment } from "../../../../environments/environment"
import { ROLE } from "../../../types"

@Component({
  selector: "app-add-project",
  standalone: true,
  imports: [GenericTableComponent, LayoutSingleComponent, RouterLink, FormBuilderComponent],
  templateUrl: "./add-project.component.html",
  styleUrl: "./add-project.component.scss",
})
export class AddProjectComponent extends CrudBaseDirective<Project> implements OnInit, AfterViewInit {
  project = new Map<keyof Project, CustomForm<any>>()

  ngAfterViewInit() {
    this.userService.currUser.subscribe((usr) => {
      if (usr.role == ROLE.manager) {
        const control = this.formBuilderComponent.form.get("department_id")
        control?.disable()
        control?.setValue(usr.department_id)
        this.cdRef.detectChanges()
      }
    })
  }

  override ngOnInit() {
    super.ngOnInit()
    this.getData()

    const selectForm = new SelectForm({
      order: 4,
      key: "department_id",
      label: "Department",
    })

    this.dataService.getDataWithAuth<Department[]>(`${environment.apiUrl}/departments/`).subscribe((val) => {
      const arr = val.map((emp) => {
        return {
          key: emp.id,
          value: `${emp.name}`,
        }
      })
      selectForm.setOptions(arr)
    })

    this.project
      .set(
        "name",
        new TextForm({
          order: 1,
          key: "name",
          label: "Title",
        })
      )
      .set(
        "description",
        new TextForm({
          order: 2,
          key: "description",
          label: "Description",
        })
      )
      .set(
        "start_date",
        new DateQuestion({
          order: 3,
          key: "start_date",
          label: "Start Date",
        })
      )
      .set("department_id", selectForm)
  }

  override baseUrl = "projects"
}
