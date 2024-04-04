import { Component, Input, OnInit, ViewChild } from "@angular/core"
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
  isNew = true
  isValid = false
  idVal = ""
  showSaved = 0
  subscription = new Subscription()
  formData: Map<string, CustomForm<any>> = new Map()

  @ViewChild(FormBuilderComponent) formBuilderComponent!: FormBuilderComponent

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
}
