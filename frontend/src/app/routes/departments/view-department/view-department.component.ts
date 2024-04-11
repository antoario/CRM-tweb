import { Component, ElementRef, OnInit, ViewChild } from "@angular/core"
import { CustomForm, Department, Employee, SelectForm, TextForm } from "../../../types/data"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { NgOptimizedImage } from "@angular/common"
import { RouterLink } from "@angular/router"
import { CrudBaseDirective } from "../../../Components/crud-base.directive"
import { environment } from "../../../../environments/environment"
import { filter } from "rxjs"

@Component({
  selector: "app-view-department",
  standalone: true,
  imports: [FormBuilderComponent, NgOptimizedImage, RouterLink],
  templateUrl: "./view-department.component.html",
  styleUrl: "./view-department.component.scss",
})
export class ViewDepartmentComponent extends CrudBaseDirective<Department> implements OnInit {
  departments = new Map<keyof Department, CustomForm<any>>()

  override ngOnInit(): void {
    super.ngOnInit()

    this.departments
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
  }

  override baseUrl = "departments"
  @ViewChild("imageElement") imageElement!: ElementRef<HTMLImageElement>
}
