import { Injectable } from "@angular/core"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { UserService } from "./user.service"
import { Observable, of } from "rxjs"

@Injectable({
  providedIn: "root",
})
export class DataService {
  constructor(private http: HttpClient) {}

  addData(url: string, data: any): Observable<any> {
    const token = this.getToken()
    if (!token) return of(null)
    return this.http.post(url, data, { headers: this.buildHeader(token) })
  }

  private getToken(): string {
    const token = localStorage.getItem("token")

    if (token) return token
    return ""
  }

  updateData(url: string, data: any): Observable<any> {
    return this.http.patch(url, data, { headers: this.buildHeader(this.getToken()) })
  }

  putData<T>(url: string, data: T, isNew = false) {
    return this.http.patch(url, data, { headers: this.buildHeader(this.getToken()) })
  }

  removeData(url: string): Observable<any> {
    return this.http.delete(url, { headers: this.buildHeader(this.getToken()) })
  }

  getDataWithAuth<T>(url: string): Observable<T> {
    return this.http.get<T>(url, { headers: this.buildHeader(this.getToken()) })
  }

  private buildHeader(token: string) {
    return new HttpHeaders({ Authorization: token })
  }
}
