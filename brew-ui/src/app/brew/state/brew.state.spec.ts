import { BrewService } from '@app/brew/services/brew.service';
import { NgxsModule, Store } from '@ngxs/store';
import { TestBed } from '@angular/core/testing';
import { AuthState } from '@app/auth/state/auth.state';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { AuthService } from '@app/auth/services/auth.service';
import { mockAuthService, mockRecipe, mockRecipes } from '@app/recipe/spec/mock.recipe.components';
import { mockBrew, mockBrews } from '@app/brew/spec/mock.brews.components';
import { LoadBrew, LoadBrews, LoadRecent5Brews, RemoveBrew, RemoveMeasurement, SaveBrew, UpdateBrew } from '@app/brew/state/brew.actions';
import { of } from 'rxjs';
import { BrewState } from '@app/brew/state/brew.state';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { Brew, CreateBrew, Measurement } from '@app/brew/model';

describe('BrewState', () => {

  let store: Store;
  let brewService: BrewService;
  let recipeService: RecipeService;
  let measurementService: MeasurementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        NgxsModule.forRoot([AuthState, RecipeState, BrewState]),
        HttpClientTestingModule,
      ],
      providers: [
        BrewService,
        RecipeService,
        MeasurementService,
        {
          provide: AuthService,
          useValue: mockAuthService
        },
      ]
    });

    recipeService = TestBed.inject(RecipeService);
    brewService = TestBed.inject(BrewService);
    measurementService = TestBed.inject(MeasurementService);
    store = TestBed.inject(Store);
    // Setup mock state
    store.reset( {
      ...store.snapshot(),
      auth: {
        loggedIn: true,
        accessToken: 'token',
        username: 'user',
        authorities: []
      },
      recipes: {
        recipe: mockRecipe,
        recipes: mockRecipes,
        top5Recipes: mockRecipes
      },
      brews: {
        brew: mockBrew,
        brews: mockBrews,
        recent5Brews: mockBrews
      }
    });
  });

  it('should load a brew', () => {
    const getBrewSpy = spyOn(brewService, 'getBrew').and.returnValue(of(mockBrew));
    const getBrewNotesSpy = spyOn(brewService, 'getBrewNotes').and.returnValue(of(''));
    const getTastingNoteSpy = spyOn(brewService, 'getBrewTastingNotes').and.returnValue(of(''));
    store.dispatch(new LoadBrew(mockBrew.id));
    const brew = store.selectSnapshot(BrewState.getBrew);
    expect(getBrewSpy).toHaveBeenCalled();
    expect(getBrewNotesSpy).toHaveBeenCalled();
    expect(getTastingNoteSpy).toHaveBeenCalled();
    expect(brew).toBeTruthy();
    expect(brew.id).toEqual(mockBrew.id);
  });

  it('should load brews for a user', () => {
    const getBrewsSpy = spyOn(brewService, 'getBrews').and.returnValue(of(mockBrews));
    store.dispatch(new LoadBrews('user'));
    const brews = store.selectSnapshot(BrewState.getBrews);
    expect(getBrewsSpy).toHaveBeenCalled();
    expect(brews).toBeTruthy();
    expect(brews.length).toEqual(mockBrews.length);
  });

  it('should load the recent 5 brews for a user', () => {
    const getBrewsSpy = spyOn(brewService, 'getTop5Recent').and.returnValue(of(mockBrews));
    store.dispatch(new LoadRecent5Brews('user'));
    const brews = store.selectSnapshot(BrewState.getRecent5Brews);
    expect(getBrewsSpy).toHaveBeenCalled();
    expect(brews).toBeTruthy();
    expect(brews.length).toEqual(mockBrews.length);
  });

  it('should remove a brew', () => {
    const deleteBrewSpy = spyOn(brewService, 'deleteBrew').and.returnValue(of(null));
    store.dispatch(new RemoveBrew(mockBrews[0]));
    const brews = store.selectSnapshot(BrewState.getBrews);
    expect(deleteBrewSpy).toHaveBeenCalled();
    expect(brews.length).toEqual(mockBrews.length - 1);
  });

  it('should create a brew', () => {
    const newBrew = {
      ...mockBrew,
      id: 6
    };
    const createBrewSpy = spyOn(brewService, 'saveBrew').and.returnValue(of(newBrew));
    const createBrew: CreateBrew = {
      recipe: mockRecipe,
      brew: newBrew
    }
    store.dispatch(new SaveBrew(createBrew));
    const savedBrew = store.selectSnapshot(BrewState.getBrew);
    const savedBrews = store.selectSnapshot(BrewState.getBrews);
    expect(createBrewSpy).toHaveBeenCalled();
    expect(savedBrew).toBeTruthy();
    expect(savedBrew.id).toEqual(6);
    expect(savedBrew).toEqual(newBrew);
    expect(savedBrews.length).toEqual(mockBrews.length + 1);
  });

  describe('Update brew', () => {
    const newBrewDate = new Date('21 May 2020 00:00:00 GMT');
    it('should update a brew attribute', () => {
      const update: Brew = {
        ...mockBrew,
        brewDate: newBrewDate
      };
      const updateBrewSpy = spyOn(brewService, 'updateBrew').and.returnValue(of(update));
      store.dispatch(new UpdateBrew(update));
      const updateBrew = store.selectSnapshot(BrewState.getBrew);
      expect(updateBrewSpy).toHaveBeenCalled();
      expect(updateBrew).toBeTruthy();
      expect(updateBrew.brewDate).toEqual(newBrewDate);
    });

    it('should update a brew measurement', () => {
      const measurement: Measurement = {
        id: 1,
        measurementDate: new Date(),
        value: 1034,
        versionId: 1
      };
      const update: Brew = {
        ...mockBrew,
        measurements: [
          measurement
        ]
      };
      const updateBrewSpy = spyOn(brewService, 'updateBrew').and.returnValue(of(update));
      store.dispatch(new UpdateBrew(update));
      const updateBrew = store.selectSnapshot(BrewState.getBrew);
      const brews = store.selectSnapshot(BrewState.getBrews);
      expect(updateBrewSpy).toHaveBeenCalled();
      expect(updateBrew).toBeTruthy();
      expect(updateBrew.measurements.length).toEqual(1);
      expect(brews[0].measurements.length).toEqual(1);
    });

    it('should remove a measurement', () => {
      const brewWithMeasurements = {
        ...mockBrew,
        measurements: [{
          id: 1,
          measurementDate: new Date(),
          value: 1034,
          versionId: 1
        }]
      };
      mockBrews[0] = brewWithMeasurements;
      store.reset({
        ...store.snapshot(),
        brews: {
          brew: brewWithMeasurements,
          brews: mockBrews,
        }
      });
      const updateBrewSpy = spyOn(brewService, 'updateBrew').and.returnValue(of(brewWithMeasurements));
      // Remove the measurement
      brewWithMeasurements.measurements.pop();
      store.dispatch(new UpdateBrew(brewWithMeasurements));
      const updatedBrew = store.selectSnapshot(BrewState.getBrew);
      const updatedBrews = store.selectSnapshot(BrewState.getBrews);
      expect(updateBrewSpy).toHaveBeenCalled();
      expect(updatedBrew).toBeTruthy();
      expect(updatedBrews).toBeTruthy();
      expect(updatedBrew.measurements.length).toEqual(0);
      expect(updatedBrews[0].measurements.length).toEqual(0);
    });
  });

});
