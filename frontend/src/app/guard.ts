import { inject, Injectable } from "@angular/core"
import { environment } from "../environments/environment"
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from "@angular/router"
import { User } from "./types"
import { BehaviorSubject, map, Observable } from "rxjs"
import { HttpClient } from "@angular/common/http"

@Injectable({ providedIn: "root" })
export class UserService {
  private currentUser: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null)

  constructor(private http: HttpClient) {
    this.loadUserFromToken()
  }

  private loadUserFromToken() {
    const token = localStorage.getItem("authToken")
    if (token) {
      // Qui puoi inviare una richiesta al server per validare il token
      // e ottenere i dettagli dell'utente, oppure semplicemente impostare l'utente
      // basandoti sul token se la tua app lo permette
      this.http.post<User>(`${environment.apiUrl}/validateToken`, { token }).subscribe({
        next: (user) => this.currentUser.next(user),
        error: () => null,
        complete: () => console.info("complete"),
      })
    }
  }

  login(email: string, password: string): Observable<User> {
    const body = { email, password }
    return this.http.post<User>(`${environment.apiUrl}/login`, body).pipe(
      map((res) => {
        localStorage.setItem("authToken", res.token)
        this.currentUser.next(res)
        return res
      })
    )
  }

  getCurrentUser() {
    return this.currentUser
  }

  getCurrentUserVal(): User | null {
    return this.currentUser.getValue()
  }

  checkLogged() {
    return true
  }

  logout() {
    localStorage.removeItem("authToken")
    this.currentUser.next(null)
  }
}

@Injectable({ providedIn: "root" })
export class PermissionsService {
  getToken(): string | null {
    return localStorage.getItem("authToken")
  }

  canActivate(): boolean {
    return !!this.getToken()
  }
  canMatch(): boolean {
    return !!this.getToken()
  }
}

export const canActivateIfLoggedIn: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => inject(PermissionsService).canActivate()
