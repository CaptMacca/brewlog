import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';
import { Measurement } from '@app/model';

@Injectable()
export class MeasurementService {

  private measurementApi = environment.measurementApiUrl;

  constructor(private http: HttpClient) {}


  saveMeasurements(measurements: Measurement[]): Observable<Measurement[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<Measurement[]>(this.measurementApi, measurements, httpOptions);
  }

  deleteMeasurement(measurementId: number): Observable<Measurement> {
    return this.http.delete<Measurement>(this.measurementApi + '/' + measurementId.toString());
  }
}
