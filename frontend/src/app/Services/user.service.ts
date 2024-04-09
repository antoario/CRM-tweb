import { Injectable } from "@angular/core"
import { BehaviorSubject, catchError, map, Observable, of, ReplaySubject, tap } from "rxjs"
import { UserData, UserSession } from "../types/UserTypes"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { environment } from "../../environments/environment"
import { DataService } from "./data.service"

@Injectable({
  providedIn: "root",
})
export class UserService {
  currUser: ReplaySubject<UserData> = new ReplaySubject(1)
  currToken: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null)

  constructor(
    private http: HttpClient,
    private dataService: DataService
  ) {}

  public checkLogin(): Observable<boolean> {
    const token = localStorage.getItem("token")
    if (!token) return of(false)

    return this.http.post<boolean>(`${environment.apiUrl}/validateToken`, { token }).pipe(
      map((val) => {
        if (!val) {
          // this.currUser.next(null)
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

  public loadUser(): Observable<UserData | null> {
    const token = this.currToken.getValue()
    if (!token) return of(null)
    return this.dataService.getDataWithAuth<UserData>(`${environment.apiUrl}/me`)
  }

  getUser() {
    // return this.currUser.getValue()
  }

  logout() {
    // this.currUser.next(null)
    this.currToken.next(null)
    localStorage.removeItem("token")
  }
}
