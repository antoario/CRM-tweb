import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from "@angular/core"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { CustomForm, Department, Employee, optionSelect, SelectForm } from "../../../types/data"
import { addEmployee } from "../../../forms/Employees"
import { DataService } from "../../../Services/data.service"
import { environment } from "../../../../environments/environment"
import { MatButtonModule } from "@angular/material/button"
import { ActivatedRoute, Router, RouterLink } from "@angular/router"
import { JsonPipe, NgForOf, NgOptimizedImage } from "@angular/common"
import { ReactiveFormsModule } from "@angular/forms"
import { finalize, map, of, switchMap, tap } from "rxjs"

@Component({
  selector: "app-add-employees",
  standalone: true,
  imports: [
    FormBuilderComponent,
    MatButtonModule,
    RouterLink,
    NgForOf,
    ReactiveFormsModule,
    NgOptimizedImage,
    JsonPipe,
  ],
  templateUrl: "./add-employees.component.html",
  styleUrl: "./add-employees.component.scss",
})
export class AddEmployeesComponent implements OnInit {
  addEmployee: Map<string, CustomForm<any>> = new Map()
  new = true
  isValid = false
  idEmp: string = ""
  @ViewChild(FormBuilderComponent) formBuilderComponent!: FormBuilderComponent
  @ViewChild("imageElement") imageElement!: ElementRef<HTMLImageElement>

  constructor(
    private data: DataService,
    private active: ActivatedRoute
  ) {}

  ngOnInit() {
    for (const i of addEmployee) {
      this.addEmployee.set(i.key, i)
    }
    const options: optionSelect[] = []
    this.idEmp = this.active.snapshot.params["id"] ?? ""
    this.data
      .getDataWithAuth<Department[]>(`${environment.apiUrl}/getDepartments`)
      .pipe(
        tap((val) => {
          for (const i of val) {
            options.push({ key: i.department_id, value: i.name })
          }

          const selection = this.addEmployee.get("department_id") as SelectForm
          selection.setOptions(options)
          console.log(this.formBuilderComponent.form)
        }),
        switchMap(() =>
          this.active.snapshot.params["id"]
            ? this.data
                .getDataWithAuth<Employee>(
                  `${environment.apiUrl}/employees/${this.active.snapshot.params["id"]}`
                )
                .pipe(
                  tap((val) => {
                    this.formBuilderComponent.form.patchValue(val)
                    this.new = false
                  })
                )
            : of(null)
        )
      )
      .subscribe()
  }

  changeVal(val: Employee) {
    this.isValid = this.formBuilderComponent.form.valid
    this.imageElement.nativeElement.src = val.img_url || ""
  }

  handleFormSubmit() {
    if (this.new) {
      this.data
        .addData(`${environment.apiUrl}/employees`, this.formBuilderComponent.form.value)
        .subscribe()
    } else {
      this.data
        .updateData(
          `${environment.apiUrl}/employees/${this.idEmp}`,
          this.formBuilderComponent.form.value
        )
        .subscribe()
    }
  }
}
