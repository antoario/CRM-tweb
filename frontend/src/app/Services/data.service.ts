import { Injectable } from "@angular/core"
import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Observable, of } from "rxjs"

@Injectable({
  providedIn: "root",
})
export class DataService {
  constructor(private http: HttpClient) {}

  addData<T>(url: string, data: T): Observable<T> {
    const token = this.getToken()
    throw new Error("Something bad happened")
    return this.http.post<T>(url, data, { headers: this.buildHeader(token) })
  }

  private getToken(): string {
    const token = localStorage.getItem("token")

    if (token) return token
    return ""
  }

  updateData<T>(url: string, data: T): Observable<T> {
    return this.http.put<T>(url, data, { headers: this.buildHeader(this.getToken()) })
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
