import { inject, Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  RouterStateSnapshot,
} from '@angular/router';
import { User } from './types';
import { catchError, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private currentUser: User | null = null;

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post<any>(`${environment.apiUrl}/login`, body);
  }

  getCurrentUser() {
    return this.currentUser;
  }
}

@Injectable({
  providedIn: 'root',
})
class PermissionsService {
  canActivate(currentUser: UserService, userId: string): boolean {
    return true;
  }
  canMatch(currentUser: UserService): boolean {
    return true;
  }
}

export const canActivateTeam: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
) => {
  return inject(PermissionsService).canActivate(
    inject(UserService),
    route.params['id'],
  );
};
