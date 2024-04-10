import { Directive, EventEmitter, Output } from "@angular/core"
import { DataService } from "../Services/data.service"
import { Observable, of, Subscription, switchMap, tap } from "rxjs"
import { environment } from "../../environments/environment"
import { FormBuilderComponent } from "./form-builder/form-builder.component"
import { ConfirmationDialogComponent } from "./confirmation-dialog/confirmation-dialog.component"
import { Dialog } from "@angular/cdk/dialog"
import { Router } from "@angular/router"

export interface CreateConfig<T> {
  url: string
  isNew: boolean
  idEl: string | number
  data: T
}

@Directive()
export class CrudBaseDirective<T> {
  currData!: T
  baseUrl!: string
  isNew!: boolean
  idEl!: string | null | undefined
  formBuilderComponent!: FormBuilderComponent
  @Output() showSavedChange = new EventEmitter<number>()
  @Output() isValidChange = new EventEmitter<boolean>()
  dataService!: DataService
  dialog!: Dialog
  router!: Router

  constructor() {}

  private _showSaved: number = 0

  get showSaved(): number {
    return this._showSaved
  }

  set showSaved(value: number) {
    this._showSaved = value
    this.showSavedChange.emit(this._showSaved)
  }

  private _isValid = false

  get isValid(): boolean {
    return this._isValid
  }

  set isValid(value: boolean) {
    this._isValid = value
    this.isValidChange.emit(this._isValid)
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
      ...val,
      ...this.formBuilderComponent.form.value,
    }
  }

  handleFormSubmit(): Subscription {
    if (!this.baseUrl) throw Error("you have to init base URL")
    const sub: Observable<any> = this.isNew
      ? this.dataService.addData(this.getUrl(), this.currData)
      : this.dataService.updateData(this.getUrl(), this.currData)

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
