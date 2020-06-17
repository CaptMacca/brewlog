import { Measurement } from '@app/model';

export class LoadMeasurements {
  static readonly type = '[Measurement] Load Measurements';
  constructor(public payload: Measurement[]) {}
}

export class DeleteMeasurement {
  static readonly type = '[Measurement] Delete Measurement';
  constructor(public payload: number) {}
}

export class RemoveMeasurement {
  static readonly type = '[Measurement] Remove Measurement';
  constructor(public payload: Measurement) {}
}

export class SaveMeasurements {
  static readonly type = '[Measurement] Save Measurements';
  constructor(public payload: Measurement[]) {
  }
}
