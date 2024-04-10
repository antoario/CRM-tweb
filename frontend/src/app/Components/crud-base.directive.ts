import { Directive, OnInit, ViewChild } from "@angular/core"
import { DataService } from "../Services/data.service"
import { Observable, of, Subscription, switchMap, tap } from "rxjs"
import { environment } from "../../environments/environment"
import { FormBuilderComponent } from "./form-builder/form-builder.component"
import { ConfirmationDialogComponent } from "./confirmation-dialog/confirmation-dialog.component"
import { Dialog } from "@angular/cdk/dialog"
import { ActivatedRoute, Router } from "@angular/router"
import { UserService } from "../Services/user.service"

@Directive()
export class CrudBaseDirective<T> implements OnInit {
  currData!: T
  baseUrl!: string
  isNew!: boolean
  idEl!: string | null | undefined
  @ViewChild(FormBuilderComponent) formBuilderComponent!: FormBuilderComponent
  isValid: boolean = false
  showSaved: number = 0

  constructor(
    public dataService: DataService,
    private active: ActivatedRoute,
    private router: Router,
    private dialog: Dialog,
    public userService: UserService
  ) {}

  ngOnInit(): void {
    this.init()
    this.getData()
  }

  getData(): Subscription {
    if (this.isNew) return of(null).subscribe()
    return this.dataService
      .getDataWithAuth<T>(this.getUrl(this.idEl))
      .pipe(
        tap((val) => {
          this.currData = val
          // TODO we can generalize this
          this.formBuilderComponent.form.patchValue(val as any)
        })
      )
      .subscribe()
  }

  changeVal(val: T) {
    this.isValid = this.formBuilderComponent.form.valid
    this.currData = {
      ...this.currData,
      ...val,
    }
  }

  init() {
    this.idEl = this.active.snapshot.params["id"] ?? false
    this.isNew = !this.idEl
  }

  handleFormSubmit(): Subscription {
    if (!this.baseUrl) throw Error("you have to init base URL")
    const sub: Observable<any> = this.isNew
      ? this.dataService.addData(this.getUrl(), this.currData)
      : this.dataService.updateData(this.getUrl(), this.currData)

    console.log(this.currData)

    return sub
      .pipe(
        tap((val: { code: number; data: { id: number; message: string } }) => {
          if (val.code != -1) {
            this.showSaved = 1
            this.isValid = false
            setTimeout(() => {
              this.showSaved = 0
            }, 1000)
            if (this.isNew) {
              this.router.navigate([`/${this.baseUrl}`, val.data.id], { replaceUrl: true })
              this.isNew = false
            }
          } else {
            console.error(val.data)
          }
        })
      )
      .subscribe()
  }

  deleteEmployee() {
    const dialogRef = this.dialog.open<string>(ConfirmationDialogComponent, {
      width: "250px",
    })

    dialogRef.closed
      .pipe(
        switchMap((val) => {
          if (val) {
            this.router.navigate([`/${this.baseUrl}`])
            return this.dataService.removeData(`${environment.apiUrl}/${this.baseUrl}?id=${this.idEl}`)
          } else {
            return of(null)
          }
        })
      )
      .subscribe()
  }

  private getUrl(id: string | null = null) {
    return `${environment.apiUrl}/${this.baseUrl}${id ? `?id=${id}` : ""}`
  }
}
