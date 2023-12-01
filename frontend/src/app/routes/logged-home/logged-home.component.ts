import { Component } from '@angular/core'
import { CommonModule } from '@angular/common'
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon'
import { MatListModule } from '@angular/material/list'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatToolbarModule } from '@angular/material/toolbar'
import { RouterLink, RouterOutlet } from '@angular/router'
import { routesLogged } from '../../app.routes'
import { UserService } from '../../guard'
import { log } from '@angular-devkit/build-angular/src/builders/ssr-dev-server'

@Component({
  selector: 'app-logged-home',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    RouterOutlet,
    RouterLink,
  ],
  templateUrl: './logged-home.component.html',
  styleUrl: './logged-home.component.scss',
})
export class LoggedHomeComponent {
  protected readonly routes = routesLogged

  constructor(userService: UserService) {
    // if(userService.getCurrentUserVal())
  }
}
