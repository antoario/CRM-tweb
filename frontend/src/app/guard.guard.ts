import { CanActivateFn, Router } from "@angular/router"
import { inject } from "@angular/core"
import { UserService } from "./Services/user.service"
import { catchError, Observable, of, tap } from "rxjs"

export const guardGuard: CanActivateFn = (): Observable<boolean> => {
  const userService = inject(UserService)
  const router = inject(Router)

  // Supponendo che checkLogin ritorni un Observable<boolean>
  return userService.checkLogin().pipe(
    catchError((err) => {
      console.error("error in server", err)
      return of(false)
    }),
    tap((isLoggedIn) => {
      return isLoggedIn || router.navigate(["/login"])
    })
  )
}
