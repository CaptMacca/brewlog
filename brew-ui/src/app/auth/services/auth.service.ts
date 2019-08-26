import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';

import { JwtResponse } from '@app/auth/model/jwt-response';
import { AuthLoginInfo } from '@app/auth/model/login-info';
import { SignUpInfo } from '@app/auth/model/signup-info';

import { JwtHelperService } from '@auth0/angular-jwt';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class AuthService {

  private loginUrl = 'http://localhost:8080/api/auth/signin';
  private signupUrl = 'http://localhost:8080/api/auth/signup';

  constructor(private http: HttpClient, private store: Store) {}

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignUpInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

  isAuthenticated(token): boolean {
    const helper = new JwtHelperService();
    return !helper.isTokenExpired(token);
  }

}
