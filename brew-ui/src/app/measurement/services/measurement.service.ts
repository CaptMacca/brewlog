import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '@env/environment';

import { Measurement } from '@app/model';

@Injectable()
export class MeasurementService {
  // Import the server url from the environment
  private measurementApi = environment.measurementApiUrl;

  constructor(private http: HttpClient) {}

  public getMeasurement(measurementId: number): Observable<Measurement> {
    const httpOptions = {
      headers: new HttpHeaders({
        Accept: 'application/json'
      })
    };
    return this.http.get<Measurement>(this.measurementApi + '/' + measurementId, httpOptions);
  }

  public saveMeasurement(measurement: Measurement): Observable<Measurement> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<Measurement>(this.measurementApi, measurement, httpOptions);
  }

  public updateMeasurement(measurement: Measurement): Observable<Measurement> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<Measurement>(this.measurementApi, measurement, httpOptions);
  }

  public deleteMeasurement(measurementId: number) {
    return this.http.delete(this.measurementApi + '/' + measurementId.toString());
  }
}
