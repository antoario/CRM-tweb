import { Component, ElementRef, ViewChild } from "@angular/core"
import { CustomForm, Department, TextForm } from "../../../types/data"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { NgOptimizedImage } from "@angular/common"
import { RouterLink } from "@angular/router"
import { CrudBaseDirective } from "../../../Components/crud-base.directive"

@Component({
  selector: "app-view-department",
  standalone: true,
  imports: [FormBuilderComponent, NgOptimizedImage, RouterLink],
  templateUrl: "./view-department.component.html",
  styleUrl: "./view-department.component.scss",
})
export class ViewDepartmentComponent extends CrudBaseDirective<Department> {
  departments: Map<keyof Department, CustomForm<any>> = new Map()
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
      "name",
      new TextForm({
        key: "name",
        required: true,
        label: "Name",
        width: "100%",
        order: 1,
      })
    )

  override baseUrl = "departments"
  @ViewChild("imageElement") imageElement!: ElementRef<HTMLImageElement>
}
