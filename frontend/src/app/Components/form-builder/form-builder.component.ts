import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
} from "@angular/core"
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms"
import { JsonPipe, KeyValuePipe, NgForOf, NgSwitch, NgSwitchCase } from "@angular/common"
import { MatFormField, MatFormFieldModule } from "@angular/material/form-field"
import { MatInputModule } from "@angular/material/input"
import { MatButton } from "@angular/material/button"
import { MatOption, MatSelect } from "@angular/material/select"
import { CustomForm, TextForm } from "../../types/data"
import { finalize, Subscription } from "rxjs"

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
    KeyValuePipe,
  ],
  templateUrl: "./form-builder.component.html",
  styleUrl: "./form-builder.component.scss",
})
export class FormBuilderComponent implements OnInit, OnDestroy {
  @Input() controls: Map<string, CustomForm<any>> = new Map()
  @Output() formSubmit: EventEmitter<any> = new EventEmitter<any>()
  @Output() formChange: EventEmitter<any> = new EventEmitter<any>()
  @Input() formGroupPass?: any | FormGroup
  form!: FormGroup
  subscription!: Subscription

  addControl(control: CustomForm<any>) {
    this.form.addControl(control.key, this.generateControl(control))
    this.controls.set(control.key, control)
  }

  ngOnInit() {
    this.form = this.toFormGroup(this.controls)

    this.subscription = this.form.valueChanges.subscribe(() => {
      this.formChange.emit(this.form.value)
    })
  }

  ngOnDestroy() {
    this.subscription.unsubscribe()
  }

  toFormGroup(questions: Map<string, CustomForm<any>>) {
    const group: any = {}

    for (const [key, control] of questions) {
      if (control.type == "info") continue
      if (control.type === "group" && control.subForm) {
        group[control.key] = this.toFormGroup(control.subForm)
      } else {
        group[control.key] = this.generateControl(control)
      }
    }

    console.log(group)
    return new FormGroup(group)
  }

  private generateControl<T>(control: CustomForm<any>) {
    const newFormControl = new FormControl<T>(control.value || "")
    if (control.type == "email") newFormControl.addValidators(Validators.email)
    if (control.required) newFormControl.addValidators(Validators.required)
    return newFormControl
  }

  onSubmit() {
    this.formSubmit.emit(this.form.value) // Emetti i valori del form al submit
  }
}
