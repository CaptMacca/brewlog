import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '@env/environment';
import { Observable } from 'rxjs';
import { UserDetails } from '@app/model';
import { UserPassword } from '@app/user/model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userRegistrationUrl = environment.userRegistrationUrl;
  private updatePasswordUrl = environment.updatePasswordUrl;

  constructor(private readonly http: HttpClient) { }

  getCurrentUserDetails(): Observable<UserDetails> {
    return this.http.get<UserDetails>(this.userRegistrationUrl, httpOptions);
  }

  updateUserDetails(username: String, userDetails: UserDetails): Observable<UserDetails> {
    return this.http.put<UserDetails>(this.userRegistrationUrl + '/' + username, userDetails, httpOptions);
  }

  updatePassword(userPassword: UserPassword): Observable<UserPassword> {
    return this.http.post<UserPassword>(this.updatePasswordUrl, userPassword, httpOptions);
  }
}
