import { Component } from "@angular/core"
import { RouterLink, RouterOutlet } from "@angular/router"
import { FormBuilderComponent } from "../form-builder/form-builder.component"
import { NgOptimizedImage } from "@angular/common"

@Component({
  selector: "app-layout-single",
  standalone: true,
  imports: [RouterOutlet, FormBuilderComponent, NgOptimizedImage, RouterLink],
  templateUrl: "./layout-single.component.html",
  styleUrl: "./layout-single.component.scss",
})
export class LayoutSingleComponent {}
