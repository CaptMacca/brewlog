import { Measurement } from '@app/model';

export class NewMeasurement {
  static readonly type = '[Measurement] New Measurement';
}

export class LoadMeasurement {
  static readonly type = '[Measurement] Load Measurement';
  constructor(public payload: Measurement) {}
}

export class LoadMeasurements {
  static readonly type = '[Measurement] Load Measurements';
  constructor(public payload: Measurement[]) {}
}

export class RemoveMeasurement {
  static readonly type = '[Measurement] Remove Measurement';
  constructor(public payload: Measurement) {}
}

export class AddMeasurement {
  static readonly type = '[Measurement] Add Measurement';
  constructor(public payload: Measurement) {}
}

export class UpdateMeasurement {
  static readonly type = '[Measurement] Update Measurement';
  constructor(public payload: Measurement) {}
}
