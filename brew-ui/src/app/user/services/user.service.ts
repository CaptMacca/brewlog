import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '@env/environment';
import { Observable } from 'rxjs';
import { UserDetails, UserPassword } from '@app/user/model';
import { UserRegistration } from '@app/user/model/user-registration';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private signupUrl = environment.signupUrl;
  private userRegistrationUrl = environment.userRegistrationUrl;
  private updatePasswordUrl = environment.updatePasswordUrl;

  constructor(private readonly http: HttpClient) { }

  getCurrentUserDetails(): Observable<UserDetails> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<UserDetails>(this.userRegistrationUrl, httpOptions);
  }

  updateUserDetails(username: String, userDetails: UserDetails): Observable<UserDetails> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<UserDetails>(this.userRegistrationUrl + '/' + username, userDetails, httpOptions);
  }

  updatePassword(userPassword: UserPassword): Observable<UserPassword> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<UserPassword>(this.updatePasswordUrl, userPassword, httpOptions);
  }

  signUp(info: UserRegistration): Observable<string> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

}
