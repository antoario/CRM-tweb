import { Component } from "@angular/core"
import { FormBuilderComponent } from "../../../Components/form-builder/form-builder.component"

@Component({
  selector: "app-add-employees",
  standalone: true,
  imports: [FormBuilderComponent],
  templateUrl: "./add-employees.component.html",
  styleUrl: "./add-employees.component.scss",
})
export class AddEmployeesComponent {
  handleFormSubmit(formValue: any) {
    console.log(formValue)
    // Qui puoi fare ciò che ti serve con i dati del form, come salvarli o inviarli ad un server
  }
}
