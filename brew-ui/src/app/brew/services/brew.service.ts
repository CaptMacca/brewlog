import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '@env/environment';

import { Brew, CreateBrew } from '@app/brew/model';

@Injectable()
export class BrewService {
  private readonly brewsApi = environment.brewsApiUrl;

  constructor(private readonly http: HttpClient) {}

  getBrews(username: string): Observable<Brew[]> {
    const params = new HttpParams().append('username', username);
    const httpOptions = {
      headers: new HttpHeaders({
        Accept: 'application/json'
      }),
      params: params
    };
    return this.http.get<Brew[]>(this.brewsApi, httpOptions);
  }

  getTop5Recent(username: string): Observable<Brew[]> {
    const params = new HttpParams().set('username', username);
    return this.http.get<Brew[]>(this.brewsApi + '/top5', {
      headers: new HttpHeaders({
        Accept: 'application/json'
      }),
      params: params
    });
  }

  getBrew(id: number): Observable<Brew> {
    const httpOptions = {
      headers: new HttpHeaders({
        Accept: 'application/json'
      })
    };
    return this.http.get<Brew>(this.brewsApi + '/' + id, httpOptions);
  }

  getBrewNotes(id: number): Observable<string> {
    return this.http.get(this.brewsApi + '/' + id + '/notes', { responseType: 'text'});
  }

  getBrewTastingNotes(id: number): Observable<string> {
    return this.http.get(this.brewsApi + '/' + id + '/tastingnotes', { responseType: 'text'});
  }

  deleteBrew(id: number): Observable<Brew[]> {
    return this.http.delete<Brew[]>(this.brewsApi + '/' + id);
  }

  saveBrew(createBrew: CreateBrew): Observable<Brew> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<Brew>(this.brewsApi, createBrew, httpOptions);
  }

  updateBrew(id: number, brew: Brew): Observable<Brew> {
    return this.http.put<Brew>(this.brewsApi + '/' + id, brew);
  }
}
