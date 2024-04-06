import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from "@angular/core"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { CustomForm, Department, Employee, OptionSelect, Position, SelectForm } from "../../../types/data"
import { addEmployee } from "../../../forms/Employees"
import { DataService } from "../../../Services/data.service"
import { environment } from "../../../../environments/environment"
import { MatButtonModule } from "@angular/material/button"
import { ActivatedRoute, Router, RouterLink } from "@angular/router"
import { JsonPipe, NgForOf, NgOptimizedImage } from "@angular/common"
import { ReactiveFormsModule } from "@angular/forms"
import { Observable, of, Subscription, switchMap, tap } from "rxjs"
import { OverlayModule } from "@angular/cdk/overlay"
import { Dialog } from "@angular/cdk/dialog"
import { ConfirmationDialogComponent } from "../../../Components/confirmation-dialog/confirmation-dialog.component"
import { CompanyDataService } from "../../../Services/company-data.service"
import { UserService } from "../../../Services/user.service"
import { ROLE } from "../../../types"

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
    OverlayModule,
  ],
  templateUrl: "./add-employees.component.html",
  styleUrl: "./add-employees.component.scss",
})
export class AddEmployeesComponent implements OnInit, OnDestroy {
  loading = false
  addEmployee: Map<string, CustomForm<any>> = new Map()
  isNew = true
  isValid = false
  idEmp: string = ""
  showSaved = 0
  subscription = new Subscription()
  departments: Map<string, Department> = new Map()
  @ViewChild(FormBuilderComponent) formBuilderComponent!: FormBuilderComponent
  @ViewChild("imageElement") imageElement!: ElementRef<HTMLImageElement>

  constructor(
    private data: DataService,
    private active: ActivatedRoute,
    private router: Router,
    public dialog: Dialog,
    private compData: CompanyDataService,
    private userService: UserService
  ) {}

  ngOnDestroy() {
    this.subscription.unsubscribe()
  }

  ngOnInit() {
    for (const i of addEmployee) {
      this.addEmployee.set(i.key, i)
    }
    this.subscription.add(
      this.userService.currUser.subscribe((val) => {
        this.addEmployee.set(
          "department_id",
          new SelectForm({
            order: 5,
            key: "department_id",
            label: "Department",
            blocked: val ? val?.role >= ROLE.manager : false,
          })
        )
        this.populateSelectOptions<Department>("department_id", this.compData.departments$, "name")
        this.loading = true
      })
    )
    this.idEmp = this.active.snapshot.params["id"] ?? ""

    this.initializeForm()
    this.populateSelectOptions<Position>("position_id", this.compData.positions$, "title")
  }

  private initializeForm() {
    if (this.idEmp) {
      this.data
        .getDataWithAuth<Employee>(`${environment.apiUrl}/employees/${this.idEmp}`)
        .pipe(
          tap((val) => {
            this.formBuilderComponent.form.patchValue(val)
            this.isNew = false
          })
        )
        .subscribe()
    }
  }

  changeVal(val: Employee) {
    this.isValid = this.formBuilderComponent.form.valid
    this.imageElement.nativeElement.src = val.img_url || ""
  }

  deleteEmployee() {
    const dialogRef = this.dialog.open<string>(ConfirmationDialogComponent, {
      width: "250px",
    })

    dialogRef.closed
      .pipe(
        switchMap((val) => {
          if (val) {
            this.router.navigate(["/employees"])
            return this.data.removeData(`${environment.apiUrl}/employees/${this.idEmp}`)
          } else {
            return of(null)
          }
        })
      )
      .subscribe()
  }

  handleFormSubmit() {
    let sub
    if (this.isNew) {
      sub = this.data.addData(`${environment.apiUrl}/employees`, this.formBuilderComponent.form.value)
    } else {
      sub = this.data.updateData(
        `${environment.apiUrl}/employees/${this.idEmp}`,
        this.formBuilderComponent.form.value
      )
    }

    sub.subscribe((val) => {
      if (val["id"]) {
        this.router.navigate(["/employees", val["id"]], { replaceUrl: true })
        this.isNew = false
      }
      this.showSaved = 1
      this.isValid = false
      setTimeout(() => {
        this.showSaved = 0
      }, 1000)
    })
  }

  private populateSelectOptions<T>(formKey: string, data$: Observable<Map<string, T>>, valueKey: keyof T) {
    this.subscription.add(
      data$.subscribe((el) => {
        const options: OptionSelect[] = Array.from(el.entries()).map(([key, val]) => ({
          key,
          value: val[valueKey] as string,
        }))
        const selectForm = this.addEmployee.get(formKey) as SelectForm
        selectForm.setOptions(options)
      })
    )
  }
}
