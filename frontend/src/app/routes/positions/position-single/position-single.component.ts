import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core"
import {
  CustomForm,
  Department,
  JustInfo,
  OptionSelect,
  Position,
  SelectForm,
  TextForm,
} from "../../../types/data"
import { GenericTableComponent } from "../../../Components/generic-table/generic-table.component"
import { DataService } from "../../../Services/data.service"
import { environment } from "../../../../environments/environment"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"
import { RouterLink } from "@angular/router"
import { CrudBaseDirective } from "../../../Components/crud-base.directive"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { NgOptimizedImage } from "@angular/common"
import { ROLE } from "../../../types"

@Component({
  selector: "app-position-single",
  standalone: true,
  imports: [GenericTableComponent, LayoutSingleComponent, RouterLink, FormBuilderComponent, NgOptimizedImage],
  templateUrl: "./position-single.component.html",
  styleUrl: "./position-single.component.scss",
})
export class PositionSingleComponent extends CrudBaseDirective<Position> implements OnInit, AfterViewInit {
  controls = new Map<keyof Position | string, CustomForm<any>>()
  override baseUrl = "positions"

  ngAfterViewInit() {
    this.userService.currUser.subscribe((usr) => {
      if (usr.role == ROLE.manager) {
        const control = this.formBuilderComponent.form.get("department_id")
        control?.disable()
        control?.setValue(usr.department_id, {})

        this.cdRef.detectChanges()
      }
    })
  }

  override ngOnInit() {
    super.ngOnInit()

    const selectDepartment = new SelectForm({
      key: "department_id",
      label: "Department",
      required: true,
      order: 3,
    })

    this.dataService.getDataWithAuth<Department[]>(`${environment.apiUrl}/departments`).subscribe((val) => {
      selectDepartment.setOptions(
        val.map((dep) => {
          return {
            key: dep.id,
            value: dep.name,
          }
        })
      )
    })

    const selectionLevel = new SelectForm({
      key: "level",
      label: "Level",
      required: true,
      order: 3,
    })

    selectionLevel.setOptions([
      { key: "junior", value: "Junior" },
      { key: "senior", value: "Senior" },
      { key: "hippy", value: "Mega hippy ☮️" },
    ])

    this.controls
      .set(
        "title",
        new TextForm({
          order: 1,
          key: "title",
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
      .set(selectionLevel.key, selectionLevel)
      .set(selectDepartment.key, selectDepartment)
  }
}
