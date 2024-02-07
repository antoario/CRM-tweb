import { Injectable } from "@angular/core"
import { BehaviorSubject, catchError, map, Observable, of, tap } from "rxjs"
import { UserData, UserSession } from "../types/UserTypes"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { environment } from "../../environments/environment"

@Injectable({
  providedIn: "root",
})
export class UserService {
  currUser: BehaviorSubject<UserData | null> = new BehaviorSubject<UserData | null>(null)
  currToken: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null)

  constructor(private http: HttpClient) {}

  public checkLogin(): Observable<boolean> {
    const token = localStorage.getItem("token")
    if (!token) return of(false)

    return this.http.post<boolean>(`${environment.apiUrl}/validateToken`, { token }).pipe(
      map((val) => {
        if (!val) {
          this.currUser.next(null)
          this.currToken.next(null)
          localStorage.removeItem("token")
          return false
        }
        this.currToken.next(token)
        return true
      })
    )
  }

  public getToken(): string {
    const token = localStorage.getItem("token")
    if (!token) throw "token not valid"
    return token
  }

  public doLogin(emailPass: { email: string; password: string }): Observable<boolean | UserSession> {
    return this.http.post<UserSession | false>(`${environment.apiUrl}/login`, emailPass).pipe(
      tap((response) => {
        if (response) {
          if (response.token) {
            this.currUser.next(response.user)
            this.currToken.next(response.token)
            localStorage.setItem("token", response.token)
          } else {
            throw -2
          }
        }
      }),
      catchError(() => {
        return of(false)
      })
    )
  }

  public loadUser() {
    const token = this.currToken.getValue()
    if (!token) return of(null)
    const headers = new HttpHeaders({ authentication: token })
    return this.http.post<UserData | null>(`${environment.apiUrl}/me`, {}, { headers }).pipe(
      tap((user) => {
        if (!user) throw false
        this.currUser.next(user)
      })
    )
  }

  getUser() {
    return this.currUser.getValue()
  }

  logout() {
    this.currUser.next(null)
    this.currToken.next(null)
    localStorage.removeItem("token")
  }
}
