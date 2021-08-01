import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BrewService } from '@app/brew/services/brew.service';
import { environment } from '@env/environment';
import { TestBed } from '@angular/core/testing';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { Measurement } from '@app/brew/model';

describe('MeasurementService', () => {

  let httpTestingController: HttpTestingController;
  let service: MeasurementService;
  const measurmeentApi = environment.measurementApiUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        MeasurementService
      ]
    });

    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(MeasurementService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should save a list of measurements', (done) => {
    const measurement: Measurement = {
      id: 1,
      measurementDate: new Date(),
      value: 1023,
      versionId: 1
    };
    const measurement2: Measurement = {
      ...measurement,
      id: 2
    };
    const measurements = [measurement, measurement2];
    service.saveMeasurements(measurements).subscribe(data => {
      expect(data).toEqual(measurements);
      done();
    });
    const testRequest = httpTestingController.expectOne(measurmeentApi);
    expect(testRequest.request.method).toEqual('POST');
    testRequest.flush(measurements);
  });

  it('should delete a measurement', (done) => {
    const measurement: Measurement = {
      id: 1,
      measurementDate: new Date(),
      value: 1023,
      versionId: 1
    }
    service.deleteMeasurement(measurement.id).subscribe(
      () => {done()},
      error => {fail(error.message)},
    );
    const testRequest = httpTestingController.expectOne(measurmeentApi + '/' + measurement.id);
    expect(testRequest.request.method).toEqual('DELETE');
    testRequest.flush(measurement.id);
  })
});
