import { Brew, Measurement } from '@app/brew/model';
import { BrewState, BrewStateModel } from '@app/brew/state/brew.state';
import { Injectable } from '@angular/core';
import { State } from '@ngxs/store';
import { BrewService } from '@app/brew/services/brew.service';
import { Observable, of } from 'rxjs';
import { MeasurementService } from '@app/brew/services/measurement.service';

export const mockBrew: Brew = {
  id: 1,
  brewDate: new Date(),
  estimatedBottleVolume: '19L',
  estimatedFermentVolume: '21L',
  estimatedOriginalGravity: '1050',
  estimatedPreboilGravity: '1045',
  estimatedFinalGravity: '1014',
  fermenterVol: 21,
  recipeName: 'My Recipe 1',
  recipeId: 1,
  score: 5,
  measurements: [],
  notes: undefined,
  spargeVol: 12,
  totalWater: 31,
  tastingNotes: undefined,
  measuredBottleVolume: 0,
  measuredFermentVolume: 0,
  measuredFinalGravity: 0,
  measuredOriginalGravity: 0,
  measuredPreboilGravity: 0,
  user: undefined,
  versionId: 1
}

export const mockBrews: Brew[] = [
  {
    ...mockBrew
  },
  {
    ...mockBrew,
    id: 2,
    recipeId: 2,
    recipeName: 'My Recipe 2',
  },
  {
    ...mockBrew,
    id: 3,
    recipeId: 3,
    recipeName: 'My Recipe 3',
  },
  {
    ...mockBrew,
    id: 4,
    recipeId: 4,
    recipeName: 'My Recipe 4',
  },
  {
    ...mockBrew,
    id: 5,
    recipeId: 5,
    recipeName: 'My Recipe 5',
  }
];

@State<BrewStateModel>({
  name: 'brews',
  defaults: {
    brew: mockBrew,
    brews: mockBrews,
    recent5Brews: mockBrews,
    initialBrewForm: undefined,
    brewForm: undefined,
    savingBrew: false,
    loadingBrews: false
  }
})
@Injectable()
export class MockBrewState extends BrewState {
}

@Injectable()
export class MockBrewService extends BrewService {
  getBrews(username: string): Observable<Brew[]> {
    return of(mockBrews)
  }
  getBrew(id: number): Observable<Brew> {
    return of(mockBrew)
  }
  deleteBrew(id: number): Observable<Brew[]> {
    return of(mockBrews)
  }
}

@Injectable()
export class MockMeasurementService extends MeasurementService {
  deleteMeasurement(measurementId: number): Observable<Measurement> {
    return super.deleteMeasurement(measurementId);
  }
}
