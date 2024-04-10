import { Component } from "@angular/core"
import { GenericTableComponent } from "../../../Components/generic-table/generic-table.component"
import { CustomForm, DateQuestion, JustInfo, Project, TextForm } from "../../../types/data"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"
import { RouterLink } from "@angular/router"
import { CrudBaseDirective } from "../../../Components/crud-base.directive"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"

@Component({
  selector: "app-add-project",
  standalone: true,
  imports: [GenericTableComponent, LayoutSingleComponent, RouterLink, FormBuilderComponent],
  templateUrl: "./add-project.component.html",
  styleUrl: "./add-project.component.scss",
})
export class AddProjectComponent extends CrudBaseDirective<Project> {
  project = new Map<keyof Project, CustomForm<any>>()
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

  override baseUrl = "projects"
}
