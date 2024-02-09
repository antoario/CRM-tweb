import { Injectable } from "@angular/core"
import { BehaviorSubject, map, merge, Observable } from "rxjs"
import { Department, Position, Projects } from "../types/data"
import { DataService } from "./data.service"
import { environment } from "../../environments/environment"

@Injectable({
  providedIn: "root",
})
export class CompanyDataService {
  positions$: BehaviorSubject<Map<string, Position>> = new BehaviorSubject<Map<string, Position>>(new Map())
  departments$: BehaviorSubject<Map<string, Department>> = new BehaviorSubject<Map<string, Department>>(
    new Map()
  )
  projects$: BehaviorSubject<Map<string, Projects>> = new BehaviorSubject<Map<string, Projects>>(new Map())

  constructor(private data: DataService) {}

  public getPositions(): Observable<Map<string, Position>> {
    return this.data
      .getDataWithAuth<Position[]>(`${environment.apiUrl}/positions`)
      .pipe(map((pos) => this.manageData(pos, this.positions$)))
  }

  public getDepartments(): Observable<Map<string, Department>> {
    return this.data
      .getDataWithAuth<Department[]>(`${environment.apiUrl}/departments`)
      .pipe(map((pos) => this.manageData(pos, this.departments$)))
  }

  public getProjects(): Observable<Map<string, Projects>> {
    return this.data
      .getDataWithAuth<Projects[]>(`${environment.apiUrl}/projects`)
      .pipe(map((pos) => this.manageData(pos, this.projects$)))
  }

  getAllData() {
    return merge(this.getDepartments(), this.getProjects(), this.getPositions())
  }

  private manageData<
    T extends {
      id: string
    },
  >(arr: T[], sub: BehaviorSubject<Map<string, T>>): Map<string, T> {
    const map: Map<string, T> = new Map()
    for (const i of arr) {
      map.set(i.id, i)
    }
    sub.next(map)
    return map
  }
}
