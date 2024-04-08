import { Component, Inject } from "@angular/core"
import { DIALOG_DATA, DialogRef } from "@angular/cdk/dialog"

@Component({
  selector: "app-confirmation-dialog",
  standalone: true,
  imports: [],
  templateUrl: "./confirmation-dialog.component.html",
  styleUrl: "./confirmation-dialog.component.scss",
})
export class ConfirmationDialogComponent {
  constructor(
    public dialogRef: DialogRef<boolean>,
    @Inject(DIALOG_DATA) public data: any
  ) {}
}
