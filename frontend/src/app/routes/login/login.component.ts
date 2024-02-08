import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { MatFormFieldModule } from "@angular/material/form-field"
import { MatInputModule } from "@angular/material/input"
import { MatIconModule } from "@angular/material/icon"
import { LayoutComponent } from "../../Components/layout/layout.component"
import { MatButton } from "@angular/material/button"
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms"
import { MatOption, MatSelect, MatSelectChange } from "@angular/material/select"
import { UserService } from "../../Services/user.service"
import { Router } from "@angular/router"

@Component({
  selector: "app-login",
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    LayoutComponent,
    MatButton,
    ReactiveFormsModule,
    MatSelect,
    MatOption,
    FormsModule,
  ],
  templateUrl: "./login.component.html",
  styleUrl: "./login.component.scss",
})
export class LoginComponent {
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  loginForm = this.formBuilder.group({
    email: ["", [Validators.required, Validators.email]],
    password: ["", Validators.required],
  })

  public setDefinedUser(selected: MatSelectChange) {
    switch (selected.value) {
      case "superAdmin":
        this.loginForm.setValue({ email: "johndoe@example.com", password: "abcdefg" })
        break
      case "admin":
        this.loginForm.setValue({ email: "due@gmail.com", password: "abcdef" })
        break
      case "user":
        this.loginForm.setValue({ email: "tre@gmail.com", password: "abcdef" })
        break
    }
  }

  public login() {
    if (this.loginForm.value.email && this.loginForm.value.password)
      this.userService
        .doLogin(this.loginForm.value as { email: string; password: string })
        .subscribe((val) => {
          if (val) {
            this.router.navigate(["/"])
          }
        })
  }
}
