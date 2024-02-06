import { Component, EventEmitter, Input, Output } from "@angular/core"
import { FormField } from "../../types/forms"
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms"
import { addEmployee } from "../../forms/Employees"
import { JsonPipe, NgForOf, NgSwitch, NgSwitchCase } from "@angular/common"
import { MatFormField, MatFormFieldModule } from "@angular/material/form-field"
import { MatInputModule } from "@angular/material/input"
import { MatButton } from "@angular/material/button"
import { MatOption, MatSelect } from "@angular/material/select"

@Component({
  selector: "app-form-builder",
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgSwitch,
    NgForOf,
    NgSwitchCase,
    JsonPipe,
    MatFormField,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButton,
    MatSelect,
    MatOption,
  ],
  templateUrl: "./form-builder.component.html",
  styleUrl: "./form-builder.component.scss",
})
export class FormBuilderComponent {
  @Input() controls: FormField[] = addEmployee
  @Output() formSubmit: EventEmitter<any> = new EventEmitter<any>()
  form!: FormGroup

  ngOnInit() {
    this.form = this.toFormGroup(this.controls)
  }

  toFormGroup(questions: FormField[]) {
    const group: any = {}

    questions.forEach((question) => {
      console.log(question.type)
      const newFormControl = new FormControl(question.value || "")
      if (question.type == "email") newFormControl.addValidators(Validators.email)
      if (question.required) newFormControl.addValidators(Validators.required)

      group[question.key] = newFormControl
    })
    return new FormGroup(group)
  }

  onSubmit() {
    this.formSubmit.emit(this.form.value) // Emetti i valori del form al submit
  }
}
