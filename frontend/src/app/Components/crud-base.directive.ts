import { Directive, OnInit, ViewChild } from "@angular/core"
import { DataService } from "../Services/data.service"
import { Observable, of, Subscription, switchMap, tap } from "rxjs"
import { environment } from "../../environments/environment"
import { FormBuilderComponent } from "./form-builder/form-builder.component"
import { ConfirmationDialogComponent } from "./confirmation-dialog/confirmation-dialog.component"
import { Dialog } from "@angular/cdk/dialog"
import { ActivatedRoute, Router } from "@angular/router"

export interface CreateConfig<T> {
  url: string
  isNew: boolean
  idEl: string | number
  data: T
}

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
    private dialog: Dialog
  ) {}

  ngOnInit(): void {
    this.init()
    this.getData()
  }

  create(conf: CreateConfig<T>) {
    this.baseUrl = conf.url
    this.isNew = conf.isNew
    this.currData = conf.data
  }

  getData(): Subscription {
    return this.dataService
      .getDataWithAuth<T>(this.getUrl(this.idEl))
      .pipe(
        tap((val) => {
          this.currData = val
          console.log(this.currData)
          // TODO we can generalize this
          this.formBuilderComponent.form.patchValue(val as any)
          this.isNew = false
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
    this.idEl = this.active.snapshot.params["id"] ?? ""
  }

  handleFormSubmit(): Subscription {
    if (!this.baseUrl) throw Error("you have to init base URL")
    const sub: Observable<any> = this.isNew
      ? this.dataService.addData(this.getUrl(), this.currData)
      : this.dataService.updateData(this.getUrl(), this.currData)

    console.log(this.currData)

    return sub
      .pipe(
        tap(() => {
          this.showSaved = 1
          this.isValid = false
          setTimeout(() => {
            this.showSaved = 0
          }, 1000)
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
            return this.dataService.removeData(`${environment.apiUrl}/${this.baseUrl}/${this.idEl}`)
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
