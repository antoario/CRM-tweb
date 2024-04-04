import { Component, ElementRef, ViewChild } from "@angular/core"
import {
  CustomForm,
  Department,
  Employee,
  JustInfo,
  OptionSelect,
  Position,
  SelectForm,
  TextForm,
} from "../../../types/data"
import { GenericTableComponent } from "../../../Components/generic-table/generic-table.component"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"
import { RouterLink } from "@angular/router"
import { DataService } from "../../../Services/data.service"
import { environment } from "../../../../environments/environment"

@Component({
  selector: "app-single-benefit",
  standalone: true,
  imports: [GenericTableComponent, LayoutSingleComponent, RouterLink],
  templateUrl: "./single-benefit.component.html",
  styleUrl: "./single-benefit.component.scss",
})
export class SingleBenefitComponent {
  valid = false
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
      key: "description",
      label: "Title",
    }),
    new TextForm({
      order: 2,
      key: "value",
      label: "Description",
    }),
  ]
  @ViewChild(GenericTableComponent) genericTable!: GenericTableComponent

  constructor(private dataService: DataService) {}

  save() {
    this.genericTable.handleFormSubmit()
  }

  checkValid(valid: boolean) {
    this.valid = valid
  }
}
