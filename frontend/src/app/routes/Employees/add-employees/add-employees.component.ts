import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from "@angular/core"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { CustomForm, Department, Employee, OptionSelect, optionSelect, SelectForm } from "../../../types/data"
import { addEmployee } from "../../../forms/Employees"
import { DataService } from "../../../Services/data.service"
import { environment } from "../../../../environments/environment"
import { MatButtonModule } from "@angular/material/button"
import { ActivatedRoute, Router, RouterLink } from "@angular/router"
import { JsonPipe, NgForOf, NgOptimizedImage } from "@angular/common"
import { ReactiveFormsModule } from "@angular/forms"
import { of, Subscription, switchMap, tap } from "rxjs"
import { DomPortal } from "@angular/cdk/portal"
import { OverlayModule } from "@angular/cdk/overlay"
import { Dialog } from "@angular/cdk/dialog"
import { ConfirmationDialogComponent } from "../../../Components/confirmation-dialog/confirmation-dialog.component"
import { CompanyDataService } from "../../../Services/company-data.service"

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
export class AddEmployeesComponent implements OnInit, AfterViewInit, OnDestroy {
  addEmployee: Map<string, CustomForm<any>> = new Map()
  new = true
  isValid = false
  idEmp: string = ""
  domPortal!: DomPortal<any>
  showSaved = 0
  subscription = new Subscription()
  departments: Map<string, Department> = new Map()
  @ViewChild(FormBuilderComponent) formBuilderComponent!: FormBuilderComponent
  @ViewChild("imageElement") imageElement!: ElementRef<HTMLImageElement>
  @ViewChild("domPortalContent") domPortalContent!: ElementRef<HTMLElement>

  constructor(
    private data: DataService,
    private active: ActivatedRoute,
    private router: Router,
    public dialog: Dialog,
    private compData: CompanyDataService
  ) {}

  ngAfterViewInit() {
    this.domPortal = new DomPortal(this.domPortalContent)
  }

  ngOnDestroy() {
    this.subscription.unsubscribe()
  }

  ngOnInit() {
    for (const i of addEmployee) {
      this.addEmployee.set(i.key, i)
    }
    const options: optionSelect[] = []
    this.idEmp = this.active.snapshot.params["id"] ?? ""
    this.data
      .getDataWithAuth<Department[]>(`${environment.apiUrl}/getDepartments`)
      .pipe(
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

    this.subscription.add(
      this.compData.positions$.subscribe((el) => {
        const arr: OptionSelect[] = []
        const selection = this.addEmployee.get("position_id") as SelectForm
        for (const [key, val] of el) {
          arr.push({ key, value: val.title })
        }
        selection.setOptions(arr)
      })
    )
    this.subscription.add(
      this.compData.departments$.subscribe((el) => {
        const arr: OptionSelect[] = []
        const selection = this.addEmployee.get("department_id") as SelectForm
        for (const [key, val] of el) {
          arr.push({ key, value: val.name })
        }
        selection.setOptions(arr)
      })
    )
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
    if (this.new) {
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
        this.new = false
      }
      this.showSaved = 1
      this.isValid = false
      setTimeout(() => {
        this.showSaved = 0
      }, 1000)
    })
  }
}
