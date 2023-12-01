import { inject, Injectable } from '@angular/core'
import { environment } from '../environments/environment'
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from '@angular/router'
import { User } from './types'
import { BehaviorSubject, map, Observable } from 'rxjs'
import { HttpClient } from '@angular/common/http'

@Injectable()
export class UserService {
  private currentUser: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null)

  constructor(private http: HttpClient) {
    this.loadUserFromToken()
  }

  private loadUserFromToken() {
    const token = localStorage.getItem('authToken')
    if (token) {
      // Qui puoi inviare una richiesta al server per validare il token
      // e ottenere i dettagli dell'utente, oppure semplicemente impostare l'utente
      // basandoti sul token se la tua app lo permette
      this.http.post<User>(`${environment.apiUrl}/validateToken`, { token }).subscribe({
        next: (user) => this.currentUser.next(user),
        error: () => localStorage.removeItem('authToken'),
        complete: () => console.info('complete'),
      })
    }
  }

  login(email: string, password: string): Observable<User> {
    const body = { email, password }
    return this.http.post<User>(`${environment.apiUrl}/login`, body).pipe(
      map((res) => {
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
    localStorage.removeItem('authToken')
    this.currentUser.next(null)
  }
}

@Injectable({
  providedIn: 'root',
})
class PermissionsService {
  canActivate(currentUser: UserService): boolean {
    // Qui puoi utilizzare currentUser.isLoggedIn() per verificare se l'utente è loggato
    return currentUser.checkLogged()
  }
}

export const canActivateIfLoggedIn: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  return inject(PermissionsService).canActivate(inject(UserService))
}
