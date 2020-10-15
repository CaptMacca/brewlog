import { Brew, CreateBrew, Measurement } from '@app/brew/model';

export class LoadAllBrews {
  static readonly type = '[Brew] Load All Brews';
}

export class LoadBrews {
  static readonly type = '[Brew] Load Brews';
  constructor(public payload: string) {}
}

export class LoadRecent5Brews {
  static readonly type = '[Brew] Load Recent 5 Brews';
  constructor(public payload: string) {}
}

export class LoadBrew {
  static readonly type = '[Brew] Load Brew';
  constructor(public payload: number) {}
}

export class RemoveBrew {
  static readonly type = '[Brew] Remove Brew';
  constructor(public payload: Brew) {}
}

export class SaveBrew {
  static readonly type = '[Brew] Save Brew';
  constructor(public payload: CreateBrew) {}
}

export class UpdateBrew {
  static readonly type = '[Brew] Update Brew';
  constructor(public payload: Brew) {}
}

export class SetSavingBrew {
  static readonly type = '[Brew] Set Saving Brew';
  constructor(public payload: boolean) {}
}

export class RemoveMeasurement {
  static readonly type = '[Brew] Remove Measurement';
  constructor(public payload: Measurement) {}
}
