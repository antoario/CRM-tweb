import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from "@angular/core"
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
import { Observable, of, Subscription, switchMap, tap } from "rxjs"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import { NgOptimizedImage } from "@angular/common"
import { ActivatedRoute, Router, RouterLink } from "@angular/router"
import { DataService } from "../../../Services/data.service"
import { Dialog } from "@angular/cdk/dialog"
import { CompanyDataService } from "../../../Services/company-data.service"
import { environment } from "../../../../environments/environment"
import { ConfirmationDialogComponent } from "../../../Components/confirmation-dialog/confirmation-dialog.component"

@Component({
  selector: "app-view-department",
  standalone: true,
  imports: [FormBuilderComponent, NgOptimizedImage, RouterLink],
  templateUrl: "./view-department.component.html",
  styleUrl: "./view-department.component.scss",
})
export class ViewDepartmentComponent implements OnInit, OnDestroy {
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

  isNew = true
  isValid = false
  idEmp: string = ""
  showSaved = 0
  subscription = new Subscription()
  department!: Department
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
    this.idEmp = this.active.snapshot.params["id"] ?? ""
    this.initializeForm()
  }

  private initializeForm() {
    if (this.idEmp) {
      this.data
        .getDataWithAuth<Department>(`${environment.apiUrl}/departments?id=${this.idEmp}`)
        .pipe(
          tap((val) => {
            this.department = val
            this.formBuilderComponent.form.patchValue(val)
            this.isNew = false
          })
        )
        .subscribe()
    }
  }

  changeVal(val: Department) {
    this.isValid = this.formBuilderComponent.form.valid
    this.department = {
      ...this.department,
      ...this.formBuilderComponent.form.value,
    }
  }

  deleteEmployee() {
    const dialogRef = this.dialog.open<string>(ConfirmationDialogComponent, {
      width: "250px",
    })

    dialogRef.closed
      .pipe(
        switchMap((val) => {
          if (val) {
            this.router.navigate(["/departments"])
            return this.data.removeData(`${environment.apiUrl}/departments/${this.idEmp}`)
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
      sub = this.data.addData(`${environment.apiUrl}/departments`, this.department)
    } else {
      sub = this.data.updateData(`${environment.apiUrl}/departments`, this.department)
    }

    console.log(this.department)
    sub.subscribe((val) => {
      if (val["id"]) {
        this.router.navigate(["/departments", val["id"]], { replaceUrl: true })
        this.isNew = false
      }
      this.showSaved = 1
      this.isValid = false
      setTimeout(() => {
        this.showSaved = 0
      }, 1000)
    })
  }

  private populateSelectOptions<T>(
    formKey: keyof Department,
    data$: Observable<Map<string, T>>,
    valueKey: keyof T
  ) {
    this.subscription.add(
      data$.subscribe((el) => {
        const options: OptionSelect[] = Array.from(el.entries()).map(([key, val]) => ({
          key,
          value: val[valueKey] as string,
        }))
        const selectForm = this.departments.get(formKey) as SelectForm
        selectForm.setOptions(options)
      })
    )
  }
}
