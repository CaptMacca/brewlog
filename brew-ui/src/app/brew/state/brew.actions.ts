import { Brew } from '@app/model';

export class LoadAllBrews {
  static readonly type = '[Brew] Load All Brews';
}

export class LoadBrews {
  static readonly type = '[Brew] Load Brews';
  constructor(public payload: Brew[]) {}
}

export class LoadBrew {
  static readonly type = '[Brew] Load Brew';
  constructor(public payload: Brew) {}
}

export class NewBrew {
  static readonly type = '[Brew] New Brew';
  constructor(public payload: Brew) {}
}

export class RemoveBrew {
  static readonly type = '[Brew] Remove Brew';
  constructor(public payload: Brew) {}
}

export class SaveBrew {
  static readonly type = '[Brew] Save Brew';
  constructor(public payload: Brew) {}
}

export class UpdateBrew {
  static readonly type = '[Brew] Update Brew';
  constructor(public payload: Brew) {}
}
