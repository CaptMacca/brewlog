import { BrewService } from '@app/brew/services/brew.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '@env/environment';
import { TestBed } from '@angular/core/testing';
import { mockBrew, mockBrews } from '@app/brew/spec/mock.brews.components';
import { Brew, CreateBrew } from '@app/brew/model';
import { mockRecipe } from '@app/recipe/spec/mock.recipe.components';

describe('BrewService', () => {

  let httpTestingController: HttpTestingController;
  let service: BrewService;
  const brewApi = environment.brewsApiUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        BrewService
      ]
    });

    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(BrewService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should create', () => {
    expect(service).toBeTruthy();
  })

  it('should get a brew', (done) => {
    service.getBrew(mockBrew.id).subscribe(data => {
      expect(data).toEqual(mockBrew);
      done();
    });
    const testRequest = httpTestingController.expectOne(brewApi + '/' + mockBrew.id);
    expect(testRequest.request.method).toBe('GET');
    testRequest.flush(mockBrew);
  });

  it('should get brews for a user', (done) => {
    service.getBrews('user').subscribe(data => {
      expect(data).toEqual(mockBrews);
      done();
    });
    const testRequest = httpTestingController.expectOne(brewApi + '?username=user');
    expect(testRequest.request.method).toBe('GET');
    testRequest.flush(mockBrews);
  });

  it('should get the 5 recent brews for a user', (done) => {
    service.getTop5Recent('user').subscribe(data => {
      expect(data).toEqual(mockBrews);
      done();
    });
    const testRequest = httpTestingController.expectOne(brewApi + '/top5?username=user');
    expect(testRequest.request.method).toBe('GET');
    testRequest.flush(mockBrews);
  });

  it('should get brew notes', (done) => {
    service.getBrewNotes(mockBrew.id).subscribe(data => {
      expect(data).toEqual('my notes');
      done();
    });
    const testRequest = httpTestingController.expectOne(brewApi + '/' + mockBrew.id + '/notes');
    expect(testRequest.request.method).toBe('GET');
    testRequest.flush('my notes');
  });

  it('should get brew tasting notes', (done) => {
    service.getBrewTastingNotes(mockBrew.id).subscribe(data => {
      expect(data).toEqual('my tasting notes');
      done();
    });
    const testRequest = httpTestingController.expectOne(brewApi + '/' + mockBrew.id + '/tastingnotes');
    expect(testRequest.request.method).toBe('GET');
    testRequest.flush('my tasting notes');
  });

  it('should delete a brew', (done) => {
    service.deleteBrew(mockBrew.id).subscribe(
      () => {done()},
      error => {fail(error.message)}
    );
    const testRequest = httpTestingController.expectOne(brewApi + '/' + mockBrew.id);
    expect(testRequest.request.method).toBe('DELETE');
    testRequest.flush(null);
  })

  it('should create a brew', (done) => {
    const newBrew: CreateBrew = {
      brew: mockBrew,
      recipe: mockRecipe
    };
    service.saveBrew(newBrew).subscribe(data => {
      expect(data).toEqual(mockBrew);
      done();
    });
    const testRequest = httpTestingController.expectOne(brewApi);
    expect(testRequest.request.method).toBe('POST');
    expect(testRequest.request.body).toEqual(newBrew);
    testRequest.flush(mockBrew);
  });

  it('should update a brew', (done) => {
    const updateBrew = {
      ...mockBrew,
      estimatedAbv: '5.5',
      measurements: [{
        id: 1,
        measurementDate: new Date(),
        value: 1034,
        versionId: 1
      }]
    };
    service.updateBrew(updateBrew.id, updateBrew).subscribe(data => {
      expect(data).toEqual(updateBrew);
      done();
    });
    const testRequest = httpTestingController.expectOne(brewApi + '/' + updateBrew.id);
    expect(testRequest.request.method).toBe('PUT');
    expect(testRequest.request.body).toEqual(updateBrew);
    testRequest.flush(updateBrew);
  })

});
