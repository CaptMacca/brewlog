import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '@env/environment';

import { Brew, NewBrewRequest } from '@app/model';

@Injectable()
export class BrewService {

  private brewsApi = environment.brewsApiUrl;

  constructor(private http: HttpClient) {}

  public getBrews(username: string): Observable<Brew[]> {
    const params = new HttpParams().append('username', username);
    const httpOptions = {
      headers: new HttpHeaders({
          Accept: 'application/json'
      }),
      params: params
    };
    return this.http.get<Brew[]>(this.brewsApi, httpOptions);
  }

  public getBrew(id: number): Observable<Brew> {
    const httpOptions = {
      headers: new HttpHeaders({
        Accept: 'application/json'
      })
    };
    return this.http.get<Brew>(this.brewsApi + '/' + id, httpOptions);
  }

  public deleteBrew(id: number): Observable<Brew[]> {
    return this.http.delete<Brew[]>(this.brewsApi + '/' + id);
  }

  public saveBrew(newBrew: NewBrewRequest): Observable<Brew> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<Brew>(this.brewsApi, newBrew, httpOptions);
  }

  public updateBrew(brew: Brew): Observable<Brew> {
    return this.http.put<Brew>(this.brewsApi, brew);
  }
}
