import { Component, ElementRef, ViewChild } from "@angular/core"
import { CustomForm, Department, Employee, OptionSelect, Position, SelectForm } from "../../../types/data"
import { Observable, of, Subscription, switchMap, tap } from "rxjs"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { DataService } from "../../../Services/data.service"
import { ActivatedRoute, Router } from "@angular/router"
import { Dialog } from "@angular/cdk/dialog"
import { CompanyDataService } from "../../../Services/company-data.service"
import { environment } from "../../../../environments/environment"
import { ConfirmationDialogComponent } from "../../../Components/confirmation-dialog/confirmation-dialog.component"
import { formBenefits } from "../formBenefits"

@Component({
  selector: "app-single-benefit",
  standalone: true,
  imports: [],
  templateUrl: "./single-benefit.component.html",
  styleUrl: "./single-benefit.component.scss",
})
export class SingleBenefitComponent {
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
    private compData: CompanyDataService
  ) {}

  ngOnDestroy() {
    this.subscription.unsubscribe()
  }

  ngOnInit() {
    for (const i of formBenefits) {
      this.addEmployee.set(i.key, i)
    }
    this.idEmp = this.active.snapshot.params["id"] ?? ""
    this.initializeForm()
    this.populateSelectOptions<Position>("position_id", this.compData.positions$, "title")
    this.populateSelectOptions<Department>("department_id", this.compData.departments$, "name")
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
    console.log(val)
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
