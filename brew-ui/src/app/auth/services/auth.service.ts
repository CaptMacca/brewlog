import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';

import { JwtResponse } from '@app/auth/model/jwt-response';
import { AuthLoginInfo } from '@app/auth/model/auth-login-info';

import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '@env/environment';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class AuthService {

  private loginUrl = environment.loginUrl;

  constructor(private readonly http: HttpClient) {}

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  isAuthenticated(token): boolean {
    const helper = new JwtHelperService();
    return !helper.isTokenExpired(token);
  }

}
