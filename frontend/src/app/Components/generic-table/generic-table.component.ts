import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from "@angular/core"
import { CustomForm, Employee } from "../../types/data"
import { Subscription, tap } from "rxjs"
import { FormBuilderComponent } from "../form-builder/form-builder.component"
import { DataService } from "../../Services/data.service"
import { ActivatedRoute, Router } from "@angular/router"
import { Dialog } from "@angular/cdk/dialog"
import { CompanyDataService } from "../../Services/company-data.service"
import { environment } from "../../../environments/environment"

@Component({
  selector: "app-generic-table",
  standalone: true,
  imports: [FormBuilderComponent],
  templateUrl: "./generic-table.component.html",
  styleUrl: "./generic-table.component.scss",
})
export class GenericTableComponent implements OnInit {
  @Input() formBuilder: CustomForm<any>[] = []
  @Input() url: string = ""
  @Output() isNewChange: EventEmitter<any> = new EventEmitter()
  @Output() isValidChange: EventEmitter<any> = new EventEmitter()
  isNew = true
  isValid = false
  idVal = ""
  subscription = new Subscription()
  formData: Map<string, CustomForm<any>> = new Map()
  @ViewChild(FormBuilderComponent) formBuilderComponent!: FormBuilderComponent
  @Output() open: EventEmitter<any> = new EventEmitter()

  constructor(
    private data: DataService,
    private active: ActivatedRoute,
    private router: Router,
    public dialog: Dialog,
    private compData: CompanyDataService
  ) {}

  ngOnInit() {
    for (const i of this.formBuilder) {
      this.formData.set(i.key, i)
    }
    this.idVal = this.active.snapshot.params["id"] ?? ""
    if (this.idVal) this.populateForm()
  }

  populateForm() {
    this.data
      .getDataWithAuth<Employee>(`${environment.apiUrl}/${this.url}/${this.idVal}`)
      .pipe(
        tap((val) => {
          this.formBuilderComponent.form.patchValue(val)
          this.isNew = false
        })
      )
      .subscribe()
  }

  handleFormSubmit() {
    let sub
    if (this.isNew) {
      sub = this.data.addData(`${environment.apiUrl}/${this.url}`, this.formBuilderComponent.form.value)
    } else {
      sub = this.data.updateData(
        `${environment.apiUrl}/${this.url}/${this.idVal}`,
        this.formBuilderComponent.form.value
      )
    }

    sub.subscribe((val) => {
      if (val["id"]) {
        this.router.navigate([`/${this.url}`, val["id"]], { replaceUrl: true })
        this.isNew = false
        this.isNewChange.emit(this.isNew)
      }
      this.isValid = false
      this.isValidChange.emit(this.isValid)
    })
  }

  changeVal(val: any) {
    this.isValidChange.emit(this.formBuilderComponent.form.valid && this.formBuilderComponent.form.dirty)
  }
}
