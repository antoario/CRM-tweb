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
import { JsonPipe, NgForOf, NgSwitch, NgSwitchCase } from "@angular/common"
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
  ],
  templateUrl: "./form-builder.component.html",
  styleUrl: "./form-builder.component.scss",
})
export class FormBuilderComponent implements OnInit, OnDestroy {
  @Input() controls: CustomForm<any>[] = []
  @Output() formSubmit: EventEmitter<any> = new EventEmitter<any>()
  @Output() formChange: EventEmitter<any> = new EventEmitter<any>()
  form!: FormGroup
  subscription!: Subscription

  addControl(control: CustomForm<any>) {
    this.form.addControl(control.key, this.generateControl(control))
    this.controls.push(control)
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

  toFormGroup(questions: CustomForm<any>[]) {
    const group: any = {}

    questions.forEach((question) => {
      group[question.key] = this.generateControl(question)
    })
    return new FormGroup(group)
  }

  private generateControl<T>(control: CustomForm<any>): FormControl<T | null> {
    const newFormControl = new FormControl<T>(control.value || "")
    if (control.type == "email") newFormControl.addValidators(Validators.email)
    if (control.required) newFormControl.addValidators(Validators.required)
    return newFormControl
  }

  onSubmit() {
    this.formSubmit.emit(this.form.value) // Emetti i valori del form al submit
  }
}
