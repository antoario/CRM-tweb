import { Component, OnInit, ViewChild } from "@angular/core"
import { GenericTableComponent } from "../../../Components/generic-table/generic-table.component"
import {
  CustomForm,
  JustInfo,
  Project,
  TextForm,
} from "../../../types/data"
import { DataService } from "../../../Services/data.service"
import { environment } from "../../../../environments/environment"
import { LayoutSingleComponent } from "../../../Components/layout-single/layout-single.component"
import { RouterLink } from "@angular/router"

@Component({
  selector: "app-add-project",
  standalone: true,
  imports: [GenericTableComponent, LayoutSingleComponent, RouterLink],
  templateUrl: "./add-project.component.html",
  styleUrl: "./add-project.component.scss",
})
export class AddProjectComponent implements OnInit {
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
  @ViewChild(GenericTableComponent) genericTable!: GenericTableComponent
  project !: Project;

  constructor(private dataService: DataService) {}

  ngOnInit() {
    const id = ""
    this.dataService.getDataWithAuth<Project>(`${environment.apiUrl}/project?id=${id}`).subscribe(val => {
      this.project = val
    })
  }

  save() {
    this.genericTable.handleFormSubmit()
  }

  checkValid(valid: boolean) {
    this.valid = valid
  }
}
