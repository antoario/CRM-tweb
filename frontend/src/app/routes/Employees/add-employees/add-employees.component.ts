import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from "@angular/core"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"
import {
  CustomForm,
  DateQuestion,
  Department,
  EmailQuestion,
  Employee,
  JustInfo,
  OptionSelect,
  Position,
  SelectForm,
  TextForm,
} from "../../../types/data"
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
import { CrudBaseDirective } from "../../../Components/crud-base.directive"

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
export class AddEmployeesComponent extends CrudBaseDirective<Employee> implements OnInit, AfterViewInit {
  override baseUrl = "employees"
  controls = new Map<keyof Employee | string, CustomForm<any>>()
  imageUrl =
    "https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg"

  override changeVal(val: Employee) {
    super.changeVal(val)
    this.imageUrl =
      val.url_image ??
      "https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg"
  }

  ngAfterViewInit() {
    this.userService.currUser.subscribe((usr) => {
      if (usr.role == ROLE.manager) {
        const control = this.formBuilderComponent.form.get("department_id")
        control?.disable()
        control?.setValue(usr.department_id)
        this.cdRef.detectChanges()
      }
    })
  }

  override ngOnInit() {
    super.ngOnInit()

    const selection = new SelectForm({
      order: 9,
      key: "department_id",
      label: "Department",
      required: true,
    })

    this.dataService.getDataWithAuth<Department[]>(`${environment.apiUrl}/departments`).subscribe((val) => {
      selection.setOptions(
        val.map((dep) => {
          return {
            key: dep.id,
            value: dep.name,
          }
        })
      )
    })
    this.controls
      .set(
        "personal",
        new JustInfo({
          order: 0,
          key: "personal_info",
          width: "100%",
          label: "Personal Information",
          subtext: "Add your personal information",
        })
      )
      .set(
        "url_image",
        new TextForm({
          order: -1,
          key: "url_image",
          label: "Image Profile",
        })
      )
      .set(
        "first_name",
        new TextForm({
          order: 1,
          key: "first_name",
          label: "First Name",
          required: true,
        })
      )
      .set(
        "last_name",
        new TextForm({
          order: 2,
          key: "last_name",
          label: "Lastname",
          required: true,
        })
      )
      .set(
        "email",
        new EmailQuestion({
          order: 7,
          key: "email",
          label: "Email",
          required: true,
        })
      )
      .set(
        "date_of_birth",
        new DateQuestion({
          order: 6,
          key: "date_of_birth",
          label: "Date of Birth",
          required: true,
        })
      )
      .set(
        "address",
        new TextForm({
          key: "address",
          label: "Address",
          order: 5,
        })
      )
      .set(
        "dep_info",
        new JustInfo({
          order: 8,
          key: "dep_info",
          width: "100%",
          label: "Assign to department",
          subtext: "Add your personal information. You can assign later",
        })
      )
      .set(
        "role",
        new SelectForm({
          label: "Role",
          key: "role",
          required: true,
          options: [
            { key: 1, value: "Manager" },
            { key: 2, value: "Employee" },
          ],
          order: 4,
        })
      )
      .set(
        "password",
        new TextForm({
          label: "Password",
          key: "password",
          required: true,
          order: 3,
        })
      )
      .set("department_id", selection)
  }
}
