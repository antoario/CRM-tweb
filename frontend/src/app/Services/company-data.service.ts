import { Injectable } from "@angular/core"
import { BehaviorSubject, map, merge, Observable } from "rxjs"
import { Benefit, Contract, Department, Position, Project } from "../types/data"
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
  projects$: BehaviorSubject<Map<string, Project>> = new BehaviorSubject<Map<string, Project>>(new Map())
  benefits$: BehaviorSubject<Map<string, Benefit>> = new BehaviorSubject<Map<string, Benefit>>(new Map())
  contracts$: BehaviorSubject<Map<string, Contract>> = new BehaviorSubject<Map<string, Contract>>(new Map())

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

  public getProjects(): Observable<Map<string, Project>> {
    return this.getObservable(`${environment.apiUrl}/projects`, this.projects$)
  }

  public getBenefits(): Observable<Map<string, Benefit>> {
    return this.getObservable(`${environment.apiUrl}/benefits`, this.benefits$)
  }

  public getContracts(): Observable<Map<string, Contract>> {
    return this.getObservable<Contract>(`${environment.apiUrl}/contracts`, this.contracts$)
  }

  private getObservable<T extends { id: string }>(url: string, subject: BehaviorSubject<Map<string, T>>) {
    return this.data.getDataWithAuth<T[]>(url).pipe(map((pos) => this.manageData(pos, subject)))
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
